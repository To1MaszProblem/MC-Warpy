package pl.to1maszproblem.factory;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.to1maszproblem.module.Teleport;

import java.util.HashMap;
import java.util.Map;

public class TeleportFactory {
    private final Map<Player, Teleport> teleports = new HashMap();

    public void addTeleport(Player player, Location location, int time) {
            this.teleports.put(player, new Teleport(player.getLocation(), time, location));
    }

    public boolean containsTeleport(Player player) {
        return this.teleports.containsKey(player);
    }

    public void removeTeleport(Player player) {
        this.teleports.remove(player);
    }

    public Map<Player, Teleport> getTeleports() {
        return this.teleports;
    }
}