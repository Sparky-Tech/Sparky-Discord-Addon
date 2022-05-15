package ac.sparky.addon.discord.webhook;

import ac.sparky.addon.discord.Plugin;
import ac.sparky.addon.discord.util.ColorUtil;
import ac.sparky.addon.discord.util.TimeUtil;
import ac.sparky.api.SparkyAPI;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.entity.Player;

@Getter
public class DiscordWebHookHandler {
    private WebhookClient client;

    private int violationColor, punishmentColor;

    public void setup(String URL) {
        this.client = WebhookClient.withUrl(URL);

        this.violationColor = ColorUtil.getIntFromColor(ColorUtil.colorFromString(Plugin.getInstance()
                .getConfigValues().getViolationColor()));
        this.punishmentColor = ColorUtil.getIntFromColor(ColorUtil.colorFromString(Plugin.getInstance()
                .getConfigValues().getPunishmentColor()));
    }

    public void sendViolation(Player player, String checkName, String checkType, String description,
                              String debug, boolean experimental, int violations) {

        if (this.client == null) return;

        Plugin.getInstance().getExecutorService().execute(() -> {

            int ping = SparkyAPI.getTransactionPing(player);

            String headUrl = String.format("https://minotar.net/avatar/%s/64", player.getUniqueId().toString());

            WebhookEmbed webhookEmbed = new WebhookEmbedBuilder()
                    .setTitle(new WebhookEmbed.EmbedTitle("Violation | " + player.getName()
                            + " | (x" + violations + ")" + (experimental ? " (*)" : ""), null))
                    .setDescription("**Check Information**\n" + checkName + " (" + checkType + ") - " + description
                            + "\n**Ping**\n" + ping + "ms\n" + "**Info**\n" + debug)
                    .setThumbnailUrl(headUrl)
                    .setFooter(new WebhookEmbed.EmbedFooter(TimeUtil.getSystemTime()
                            + " | v" + SparkyAPI.getVersion(), null)).setColor(this.violationColor)
                    .build();

            this.client.send(webhookEmbed);
        });
    }

    public void sendPunishment(Player player, String banID, String checkName, String checkType) {
        if (this.client == null) return;

        Plugin.getInstance().getExecutorService().execute(() -> {

            int ping = SparkyAPI.getTransactionPing(player);

            String headUrl = String.format("https://minotar.net/avatar/%s/64", player.getUniqueId().toString());

            WebhookEmbed webhookEmbed = new WebhookEmbedBuilder()
                    .setTitle(new WebhookEmbed.EmbedTitle("Punishment | " + player.getName(), null))
                    .setDescription("**Check Information**\n" + checkName
                            + " (" + checkType + ")\n**Punishment ID**\n" + banID + "\n**Ping**\n" + ping + "ms")
                    .setThumbnailUrl(headUrl)
                    .setFooter(new WebhookEmbed.EmbedFooter(TimeUtil.getSystemTime()
                            + " | v" + SparkyAPI.getVersion(), null))
                    .setColor(this.punishmentColor)
                    .build();

            this.client.send(webhookEmbed);
        });
    }
}
