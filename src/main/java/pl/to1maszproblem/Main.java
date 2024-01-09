package pl.to1maszproblem;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.to1maszproblem.commands.WarpCommand;
import pl.to1maszproblem.configuration.Configuration;
import pl.to1maszproblem.configuration.MessageConfiguration;
import pl.to1maszproblem.factory.TeleportFactory;
import pl.to1maszproblem.listener.InventoryClickListener;
import pl.to1maszproblem.menu.WarpMenu;
import pl.to1maszproblem.module.sterialize.WarpSterializer;
import pl.to1maszproblem.task.TeleportTask;

public final class Main extends JavaPlugin {

    @Getter public static Main instance;
    @Getter private Configuration configuration;
    @Getter private MessageConfiguration messageConfiguration;
    @Getter private TeleportFactory teleportFactory;
    @Getter private WarpMenu warpMenu;

    @Override
    public void onEnable() {
        instance = this;
        this.teleportFactory = new TeleportFactory();
        this.warpMenu = new WarpMenu();
        messageConfiguration = new MessageConfiguration();
        getCommand("warp").setExecutor(new WarpCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
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
    }
}
