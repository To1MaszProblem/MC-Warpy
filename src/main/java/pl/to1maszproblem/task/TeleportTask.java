package pl.to1maszproblem.task;

import com.google.common.collect.ImmutableMultimap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.to1maszproblem.Main;
import pl.to1maszproblem.configuration.MessageConfiguration;
import pl.to1maszproblem.module.Teleport;
import pl.to1maszproblem.module.Warp;
import pl.to1maszproblem.utils.TextUtil;

public class TeleportTask implements Runnable {
    private final MessageConfiguration messageConfiguration;

    private final Main main;

    public TeleportTask(MessageConfiguration messageConfiguration, Main main) {
        this.messageConfiguration = messageConfiguration;
        this.main = main;
    }

    public void run() {
        this.main.getTeleportFactory().getTeleports().entrySet().removeIf(entry -> {
            Player player = (Player)entry.getKey();
            if (player == null)
                return false;
            Teleport teleport = (Teleport)entry.getValue();
            if (player.getLocation().distance(teleport.getFrom()) > 0.5D) {
                Main.getInstance().getMessageConfiguration().getTeleportCanceled().send(player);
                this.main.getTeleportFactory().removeTeleport(player);
                return true;
            }
            Main.getInstance().getMessageConfiguration().getTimeToTeleport().addPlaceholder(ImmutableMultimap.of("[time]", String.valueOf(teleport.getTime()))).send(player);
            if (teleport.getTime() <= 0) {
                Main.getInstance().getMessageConfiguration().getTeleported().addPlaceholder(ImmutableMultimap.of("[warp]", findWarpByLocation(teleport.getTo()).getName())).send(player);
                player.teleportAsync(teleport.getTo());
                this.main.getTeleportFactory().removeTeleport(player);
            } else {
                teleport.setTime(teleport.getTime() - 1);
            }
            return false;
        });
    }
    public Warp findWarpByLocation(Location location) {
        return this.main.getConfiguration().getWarps().stream().filter(warp -> warp.getLocation().equals(location)).findFirst().orElse(null);
    }
}
