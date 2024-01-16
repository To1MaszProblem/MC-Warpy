package pl.to1maszproblem.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.to1maszproblem.Main;
import pl.to1maszproblem.utils.ItemBuilder;
import pl.to1maszproblem.utils.TextUtil;

public class WarpMenu {

    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, Main.getInstance().getConfiguration().getMenuRows() * 9,
                TextUtil.fixColor(Main.getInstance().getConfiguration().getWarpMenuTitle()));

        Main.getInstance().getConfiguration().getWarps().forEach(warp -> {
            int slot = warp.getSlot();
            if (slot >= 0) {
                ItemStack warpItem = new ItemBuilder(warp.getItem()).setName("&a" + warp.getName()).addLores(TextUtil.fixColor(" "), TextUtil.fixColor("&7Kliknij aby zostać &aprzeteleportowanm&7!")).build();
                inventory.setItem(slot, warpItem);
            }
        });

        Main.getInstance().getConfiguration().getItems().forEach(itemMenu -> {
            int slot = itemMenu.getSlot();
            if (slot >= 0 && slot < inventory.getSize()) {
                inventory.setItem(slot, itemMenu.getItemStack());
            }
        });

        player.openInventory(inventory);
    }


    public static Inventory getInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, Main.getInstance().getConfiguration().getMenuRows() * 9,
                TextUtil.fixColor(Main.getInstance().getConfiguration().getWarpMenuTitle()));

        Main.getInstance().getConfiguration().getWarps().forEach(warp -> {
            inventory.setItem(warp.getSlot(), new ItemBuilder(warp.getItem().getType()).setName(warp.getName()).build());
        });

        Main.getInstance().getConfiguration().getItems().forEach(itemMenu ->
                inventory.setItem(itemMenu.getSlot(), itemMenu.getItemStack()));

        return inventory;
    }
}