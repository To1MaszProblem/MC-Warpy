package pl.to1maszproblem.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import pl.to1maszproblem.module.ItemMenu;
import pl.to1maszproblem.module.Warp;
import pl.to1maszproblem.utils.ItemBuilder;

import java.util.Arrays;
import java.util.List;

@Getter
public class Configuration extends OkaeriConfig {
    private int menuRows = 1;
    private int teleportTime = 5;
    private String warpMenuTitle = "&8Wybierz warp!";
    private List<Warp> warps = Arrays.asList(
            new Warp("spawn", new ItemBuilder(Material.DIAMOND_SHOVEL).addLore(" ", "&7Kliknij aby zostać &aprzeteleportowanm&7!").build(),
                    new Location(Bukkit.getWorld("world"), 0.0, 100.0, 0.0), 0),
            new Warp("pvp", new ItemBuilder(Material.DIAMOND_SHOVEL).addLore(" ", "&7Kliknij aby zostać &aprzeteleportowanm&7!").build(),
                    new Location(Bukkit.getWorld("world"), 10.0, 100.0, 10.0), 1));
    private List<ItemMenu> items = Arrays.asList(
            new ItemMenu(new ItemBuilder(Material.PAPER).setName("&cJak działają warpy?").addLore(" ", "&7Wybierz warp do którego chcesz być przeteleportowany!").build(), 8));

}
