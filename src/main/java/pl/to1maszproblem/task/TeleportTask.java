package pl.to1maszproblem.task;

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
                TextUtil.sendMessage(this.messageConfiguration.getTeleportCanceledChatType(), player, this.messageConfiguration.getTeleportCanceled());
                this.main.getTeleportFactory().removeTeleport(player);
                return true;
            }
            TextUtil.sendMessage(this.messageConfiguration.getTimeChatType(), player, this.messageConfiguration.getTimeToTeleport().replace("[time]", String.valueOf(teleport.getTime())));
            if (teleport.getTime() <= 0) {
                TextUtil.sendMessage(this.messageConfiguration.getTeleportedChatType(), player, this.messageConfiguration.getTeleported().replace("[warp]", findWarpByLocation(teleport.getTo()).getName()));
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
