package ac.sparky.addon.discord.listeners;

import ac.sparky.addon.discord.Plugin;
import ac.sparky.addon.discord.util.TimeUtil;
import ac.sparky.api.events.SparkyPunishEvent;
import ac.sparky.api.events.SparkyViolationEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class SparkyListeners implements Listener {

    private final Map<UUID, Long> playerMap = new WeakHashMap<>();
    private final long timeout = TimeUnit.SECONDS.toMillis(5);

    @EventHandler
    public void onFlag(SparkyViolationEvent event) {

        if (event.isCancelled() || !Plugin.getInstance().getConfigValues().isViolations()) return;

        if (Plugin.getInstance().getConfigValues().isAntiRateLimit()) {
            long now = System.currentTimeMillis();
            UUID uuid = event.getPlayer().getUniqueId();

            if (this.playerMap.containsKey(uuid) && (now - this.playerMap.get(uuid)) < this.timeout) {
                return;
            }

            this.playerMap.put(uuid, now);
        }

        String checkName = event.getCheckName();
        String checkType = event.getCheckType();
        String description = event.getDescription();
        String debug = event.getDebug();
        int violations = event.getViolation();
        boolean experimental = event.isExperimental();

        Plugin.getInstance().getDiscordWebHookHandler().sendViolation(event.getPlayer(),
                checkName, checkType, description, debug, experimental, violations);
    }

    @EventHandler
    public void onPunish(SparkyPunishEvent event) {

        if (event.isCancelled() || !Plugin.getInstance().getConfigValues().isPunishments()) return;

        String banID = event.getBanID();
        String checkName = event.getCheckName();
        String checkType = event.getCheckType();

        Plugin.getInstance().getDiscordWebHookHandler().sendPunishment(event.getPlayer(), banID, checkName, checkType);
    }
}
