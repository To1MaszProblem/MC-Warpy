package pl.to1maszproblem.listener;

import com.google.common.collect.ImmutableMultimap;
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
                    if(!warp.getPermission().equals("false")) {
                        if (!player.hasPermission(warp.getPermission())) {
                            Main.getInstance().getMessageConfiguration().getTeleportPermission().addPlaceholder(ImmutableMultimap.of("[warp]", warp.getName()));
                            return;
                        }
                    }

                    player.closeInventory();
                    if (player.hasPermission("warps.admin")) { player.teleportAsync(warp.getLocation()); return; }
                            Main.getInstance().getTeleportFactory().addTeleport(player,
                                    warp.getLocation(),
                                    Main.getInstance().getConfiguration().getTeleportTime());
                }
            });
        }
    }
}
