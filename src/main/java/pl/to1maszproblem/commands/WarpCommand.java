package pl.to1maszproblem.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.to1maszproblem.Main;
import pl.to1maszproblem.menu.WarpMenu;
import pl.to1maszproblem.module.Warp;
import pl.to1maszproblem.utils.TextUtil;

import java.util.List;
import java.util.Set;

public class WarpCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("warps.warp")) {
                TextUtil.sendMessage("CHAT", player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4warps.warp&8)");
                return false;
            }
            if (args.length == 0) (new WarpMenu()).openInventory(player);

            if (!player.hasPermission("warps.list")) {
                TextUtil.sendMessage("CHAT", player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4warps.info&8)");
                return false;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    TextUtil.sendMessage("CHAT", player, "&fAktualne warpy na serwerze:");
                    Main.getInstance().getConfiguration().getWarps().forEach(warp -> TextUtil.sendMessage("CHAT", player, "&7- &f" + warp.getName() + " &6X:&f" + warp.getLocation().getX() + " &6Y:&f" + warp.getLocation().getY() + " &6Z:&f" + warp.getLocation().getZ()));
                }
            } else if (args.length == 2) {
                if (!player.hasPermission("warps.create")) {
                    TextUtil.sendMessage("CHAT", player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4warps.create&8)");
                    return false;
                }
                if (args[0].equalsIgnoreCase("create")) {
                    String name = args[1];
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (item.getType() == Material.AIR) {
                        TextUtil.sendMessage("CHAT", player, Main.getInstance().getMessageConfiguration().getHoldItemToCreate());
                        return false;
                    }
                    if (Main.getInstance().getConfiguration().getWarps().stream().anyMatch(warp -> warp.getName().toLowerCase().equalsIgnoreCase(name))) {
                        TextUtil.sendMessage("CHAT", player, Main.getInstance().getMessageConfiguration().getWarpDoesntExist());
                        return false;
                    }
                    int slot = findSlot(player.getInventory());
                    if (slot != -1) {
                        player.sendMessage("Znaleziono wolny slot na pozycji: " + slot);
                    } else {
                        player.sendMessage("Brak wolnych slotów.");
                    }
                    Main.getInstance().getConfiguration().getWarps().add(new Warp(name, item, player.getLocation(), slot));
                    TextUtil.sendMessage("CHAT", player, Main.getInstance().getMessageConfiguration().getCreatedWarp().replace("[warp-name]", name));
                    Main.getInstance().getConfiguration().save();
                }
                if (!player.hasPermission("warps.delete")) {
                    TextUtil.sendMessage("CHAT", player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4warps.delete&8)");
                    return false;
                }
                if (args[0].equalsIgnoreCase("delete")) {
                    String name = args[1];
                    if (Main.getInstance().getConfiguration().getWarps().stream().noneMatch(warp -> warp.getName().toLowerCase().equals(args[0].toLowerCase()))) {
                        TextUtil.sendMessage("CHAT", player, Main.getInstance().getMessageConfiguration().getWarpDoesntExist());
                        return false;
                    }
                    Main.getInstance().getConfiguration().getWarps().remove(name);
                    Main.getInstance().getConfiguration().load();
                    TextUtil.sendMessage("CHAT", player, Main.getInstance().getMessageConfiguration().getDeletedWarp().replace("[warp-name]", name));
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("warp.admin")) if (args.length == 1) return Set.of("create", "delete", "info").stream().toList();
        return null;
    }
    private int findSlot(Inventory inventory) {
        return inventory.firstEmpty();
    }
}
