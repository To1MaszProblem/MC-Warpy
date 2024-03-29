package pl.to1maszproblem.commands;

import com.google.common.collect.ImmutableMultimap;
import net.wesjd.anvilgui.AnvilGUI;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WarpCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) (new WarpMenu()).openInventory(player);

            if (args.length == 1) {
                if (!player.hasPermission("warps.list")) {
                    TextUtil.sendMessage(player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4warps.info&8)");
                    return false;
                }
                if (args[0].equalsIgnoreCase("list")) {
                    TextUtil.sendMessage(player, "&fAktualne warpy na serwerze:");
                    Main.getInstance().getConfiguration().getWarps().forEach(warp -> TextUtil.sendMessage(player, "&7- &f" + warp.getName() + " &6X:&f" + warp.getLocation().getX() + " &6Y:&f" + warp.getLocation().getY() + " &6Z:&f" + warp.getLocation().getZ()));
                }
                if (!player.hasPermission("warps.reload")) {
                    TextUtil.sendMessage(player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4warps.reload&8)");
                    return false;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    TextUtil.sendMessage(player, "&aPrzeładowano config!");
                    Main.getInstance().getConfiguration().load();
                    Main.getInstance().getMessageConfiguration().load();
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("tp")) {
                    String name = args[1];
                    Optional<Warp> warps = Main.getInstance().getConfiguration().getWarps().stream()
                            .filter(warp -> warp.getName().equalsIgnoreCase(name))
                            .findFirst();
                    if (warps.isPresent()) {
                        Warp warp = warps.get();
                        if (player.hasPermission("warps.admin")) { player.teleportAsync(warp.getLocation()); return false; }
                        Main.getInstance().getTeleportFactory().addTeleport(player,
                                warp.getLocation(),
                                Main.getInstance().getConfiguration().getTeleportTime());
                    }
                }
                if (!player.hasPermission("warps.create")) {
                    TextUtil.sendMessage(player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4warps.create&8)");
                    return false;
                }
                if (args[0].equalsIgnoreCase("create")) {
                    String name = args[1];
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (item.getType() == Material.AIR) {
                        TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getHoldItemToCreate());
                        return false;
                    }
                    if (Main.getInstance().getConfiguration().getWarps().stream().anyMatch(warp -> warp.getName().toLowerCase().equalsIgnoreCase(name))) {
                        TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getWarpDoesntExist());
                        return false;
                    }

                    int slot = findSlot(WarpMenu.getInventory(player));
                    Main.getInstance().getConfiguration().getWarps().forEach(warp -> {
                    new AnvilGUI.Builder()
                            .text("Wpisz false jeżeli nie chcesz permisji")
                            .title("Podaj permisje")
                            .plugin(Main.getInstance())
                            .itemLeft(new ItemStack(Material.PAPER))
                            .onClick((slot1, message) -> {
                                if (slot1 != AnvilGUI.Slot.OUTPUT) {
                                    return Collections.emptyList();
                                }
                                String permission = message.getText();

                                Main.getInstance().getConfiguration().getWarps().add(new Warp(name, item, player.getLocation(), permission, slot));
                                TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getCreatedWarp().replace("[warp-name]", name));
                                Main.getInstance().getConfiguration().save();
                                return List.of(AnvilGUI.ResponseAction.run(player::closeInventory));
                            }).open(player);
                    });
                }
                if (!player.hasPermission("warps.delete")) {
                    TextUtil.sendMessage(player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4warps.delete&8)");
                    return false;
                }
                if (args[0].equalsIgnoreCase("delete")) {
                    String name = args[1];
                    List<Warp> warps = Main.getInstance().getConfiguration().getWarps();

                    Optional<Warp> warpsToRemove = warps.stream()
                            .filter(collection -> collection.getName().equalsIgnoreCase(name))
                            .findFirst();
                    if (warpsToRemove.isPresent()) {
                        warps.remove(warpsToRemove.get());
                        Main.getInstance().getConfiguration().save();
                        TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getDeletedWarp().replace("[warp-name]", name));
                    } else {
                        TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getWarpDoesntExist());
                    }
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("tp")) return Main.getInstance().getConfiguration().getWarps().stream().map(warp -> warp.getName().toLowerCase()).toList();
        if (sender.hasPermission("warps.admin") && args.length == 1) return Set.of("create", "delete", "info", "reload").stream().toList();
        if (sender.hasPermission("warps.admin") && args.length == 2 && args[0].equalsIgnoreCase("delete")) return Main.getInstance().getConfiguration().getWarps().stream().map(warp -> warp.getName().toLowerCase()).toList();
        return null;
    }
    private int findSlot(Inventory inventory) {
        return inventory.firstEmpty();
    }
}
