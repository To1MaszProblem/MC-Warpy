package pl.to1maszproblem.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import pl.to1maszproblem.module.ItemMenu;
import pl.to1maszproblem.module.Warp;
import pl.to1maszproblem.utils.ItemBuilder;
import pl.to1maszproblem.utils.TextUtil;

import java.util.Arrays;
import java.util.List;

@Getter
public class Configuration extends OkaeriConfig {
    private int menuRows = 3;
    private int teleportTime = 5;
    private String warpMenuTitle = "&8Wybierz warp!";
    private List<Warp> warps = Arrays.asList(
            new Warp("spawn", new ItemBuilder(Material.DIAMOND)
                    .setName("&aSpawn")
                    .addLore(" ")
                    .addLore(TextUtil.fixColor("&7Kliknij aby zostać &aprzeteleportowanm&7!")).build(),
                    new Location(Bukkit.getWorld("world"), 0.0, 100.0, 0.0), "false",11),
            new Warp("pvp", new ItemBuilder(Material.DIAMOND_SWORD)
                    .setName("&cStrefa pvp")
                    .addLore(" ")
                    .addLore(TextUtil.fixColor("&7Kliknij aby zostać &aprzeteleportowanm&7!")).build(),
                    new Location(Bukkit.getWorld("world"), 10.0, 100.0, 10.0), "false",15));
    private List<ItemMenu> items = Arrays.asList(
            new ItemMenu(new ItemBuilder(Material.PAPER).setName("&cJak działają warpy?").addLores(" ", "&7Wybierz warp do którego chcesz być przeteleportowany!").build(), 13));

}
