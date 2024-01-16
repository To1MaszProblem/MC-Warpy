package pl.to1maszproblem.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.to1maszproblem.Main;
import pl.to1maszproblem.utils.TextUtil;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory != null && event.getView().getTitle().equals(TextUtil.fixColor(Main.getInstance().getConfiguration().getWarpMenuTitle()))) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            Main.getInstance().getConfiguration().getWarps().forEach(warp -> {
                if (clickedItem != null && clickedItem.getType() != null && clickedItem.isSimilar(warp.getItem())) {
                    player.closeInventory();
                            Main.getInstance().getTeleportFactory().addTeleport(player,
                                    warp.getLocation(),
                                    Main.getInstance().getConfiguration().getTeleportTime());
                }
            });
        }
    }
}
