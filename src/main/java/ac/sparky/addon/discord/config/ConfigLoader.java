package ac.sparky.addon.discord.config;

import ac.sparky.addon.discord.Plugin;

public class ConfigLoader {

    public void load() {
        Plugin.getInstance().getConfig().options().copyDefaults(true);
        Plugin.getInstance().saveConfig();

        Plugin.getInstance().getConfigValues().setWebHook(Plugin.getInstance().getConfig()
                .getString("Discord.WebHook"));

        Plugin.getInstance().getConfigValues().setViolations(Plugin.getInstance().getConfig()
                .getBoolean("Discord.Types.Violations"));

        Plugin.getInstance().getConfigValues().setPunishments(Plugin.getInstance().getConfig()
                .getBoolean("Discord.Types.Punishments"));

        Plugin.getInstance().getConfigValues().setViolationColor(Plugin.getInstance().getConfig()
                .getString("Discord.Violations.Color"));

        Plugin.getInstance().getConfigValues().setPunishmentColor(Plugin.getInstance().getConfig()
                .getString("Discord.Punishments.Color"));

        Plugin.getInstance().getConfigValues().setAntiRateLimit(Plugin.getInstance().getConfig()
                .getBoolean("Discord.Violations.AntiRateLimit"));
    }
}
