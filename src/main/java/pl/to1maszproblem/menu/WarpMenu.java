package pl.to1maszproblem.menu;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.entity.Player;
import pl.to1maszproblem.Main;
import pl.to1maszproblem.utils.ItemBuilder;
import pl.to1maszproblem.utils.TextUtil;

public class WarpMenu implements InventoryProvider {
    public void init(Player player, InventoryContents contents) {
        Main.getInstance().getConfiguration().getWarps().forEach(warp -> {
            contents.set(SlotPos.of(warp.getSlot() / 9, warp.getSlot() % 9),
                    ClickableItem.of(new ItemBuilder(warp.getItem().getType()).setName(warp.getName()).build(), false, event -> {
                        player.closeInventory();
                        Main.getInstance().getTeleportFactory().addTeleport(player, warp.getLocation(), Main.getInstance().getConfiguration().getTeleportTime());
                    }));
        });
        Main.getInstance().getConfiguration().getItems().forEach(itemMenu -> contents.set(SlotPos.of(itemMenu.getSlot() / 9, itemMenu.getSlot() % 9), ClickableItem.empty(itemMenu.getItemStack(), false)));
    }

    public void openInventory(Player player) {
        SmartInventory.builder()
                .provider(this)
                .size(Main.getInstance().getConfiguration().getMenuRows(), 9)
                .title(TextUtil.fixColor(Main.getInstance().getConfiguration().getWarpMenuTitle()))
                .build()
                .open(player);
    }
}