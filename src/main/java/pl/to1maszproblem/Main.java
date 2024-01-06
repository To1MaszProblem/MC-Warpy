package pl.to1maszproblem;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pl.to1maszproblem.commands.WarpCommand;
import pl.to1maszproblem.configuration.Configuration;
import pl.to1maszproblem.configuration.MessageConfiguration;
import pl.to1maszproblem.factory.TeleportFactory;
import pl.to1maszproblem.module.sterialize.WarpSterializer;
import pl.to1maszproblem.task.TeleportTask;

public final class Main extends JavaPlugin {

    @Getter public static Main instance;
    @Getter private Configuration configuration;
    @Getter private MessageConfiguration messageConfiguration;
    @Getter private TeleportFactory teleportFactory;
    @Getter private static InventoryManager invManager;

    @Override
    public void onEnable() {
        instance = this;
        this.teleportFactory = new TeleportFactory();
        messageConfiguration = new MessageConfiguration();
        getCommand("warp").setExecutor(new WarpCommand());
        configuration = ConfigManager.create(Configuration.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(this.getDataFolder() + "/config.yml");
            it.withSerdesPack(registry -> registry.register(new WarpSterializer()));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        messageConfiguration = ConfigManager.create(MessageConfiguration.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(this.getDataFolder() + "/messages.yml");
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
        Bukkit.getScheduler().runTaskTimer(this, (Runnable)new TeleportTask(this.messageConfiguration, this), 0L, 20L);
        invManager = new InventoryManager(this);
        invManager.init();
    }
}
