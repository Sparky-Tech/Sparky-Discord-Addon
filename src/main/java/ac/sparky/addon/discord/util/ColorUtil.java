package ac.sparky.addon.discord.util;

import java.awt.*;

public class ColorUtil {

    private static Class<?> colorClass;

    static {
        try {
            colorClass = Class.forName("java.awt.Color");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Color colorFromString(String color) {

        try {
            return (Color) colorClass.getField(color).get(null);
        } catch (Exception ignored) {
        }

        return Color.WHITE;
    }

    public static int getIntFromColor(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        red = (red << 16) & 0x00FF0000;
        green = (green << 8) & 0x0000FF00;
        blue = blue & 0x000000FF;
        return 0xFF000000 | red | green | blue;
    }
}
