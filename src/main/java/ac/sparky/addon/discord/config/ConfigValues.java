package ac.sparky.addon.discord.config;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConfigValues {
    private String webHook;
    private boolean violations;
    private boolean punishments;
    private String violationColor;
    private String punishmentColor;
    private boolean antiRateLimit;
}
