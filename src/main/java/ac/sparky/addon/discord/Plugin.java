package ac.sparky.addon.discord;

import ac.sparky.addon.discord.config.ConfigLoader;
import ac.sparky.addon.discord.config.ConfigValues;
import ac.sparky.addon.discord.listeners.SparkyListeners;
import ac.sparky.addon.discord.webhook.DiscordWebHookHandler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class Plugin extends JavaPlugin {
    @Getter private static Plugin instance;

    private final DiscordWebHookHandler discordWebHookHandler = new DiscordWebHookHandler();
    private final ConfigValues configValues = new ConfigValues();
    private final ConfigLoader configLoader = new ConfigLoader();
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Override
    public void onEnable() {
        instance = this;
        this.configLoader.load();

        if (this.configValues.getWebHook().equalsIgnoreCase("DISCORD_WEB_HOOK_URL")) {
            this.getLogger().warning("Please setup a discord webhook url in the config.yml");
            return;
        }

        this.discordWebHookHandler.setup(this.configValues.getWebHook());
        getServer().getPluginManager().registerEvents(new SparkyListeners(), this);
    }

    @Override
    public void onDisable() {
        this.executorService.shutdownNow();

        if (!this.configValues.getWebHook().equalsIgnoreCase("DISCORD_WEB_HOOK_URL")) {
            this.discordWebHookHandler.getClient().close();
        }
    }
}
