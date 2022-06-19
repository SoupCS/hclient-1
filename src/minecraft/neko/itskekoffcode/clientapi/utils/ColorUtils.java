package neko.itskekoffcode.clientapi.utils;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Random;

public class ColorUtils {
    public ColorUtils() {
    }

    public static String BLACK = "\2470";
    public static String DARK_BLUE = "\2471";
    public static String DARK_GREEN = "\2472";
    public static String DARK_AQUA = "\2473";
    public static String DARK_RED = "\2474";
    public static String DARK_PURPLE = "\2475";
    public static String GOLD = "\2476";
    public static String GRAY = "\2477";
    public static String DARK_GRAY = "\2478";
    public static String BLUE = "\2479";
    public static String GREEN = "\247a";
    public static String AQUA = "\247b";
    public static String RED = "\247c";
    public static String LIGHT_PURPLE = "\247d";
    public static String YELLOW = "\247e";
    public static String WHITE = "\247f";
    public static String RANDOM = "\247k";
    public static String BOLD = "\247l";
    public static String STRIKETHROUGH = "\247m";
    public static String UNDERLINE = "\247n";
    public static String ITALIC = "\247o";
    public static String RESET = "\247r";

    public static int getRandomColor() {
        char[] letters = "012345678".toCharArray();
        String color = "0x";
        for (int i = 0; i < 6; i++)
            color += letters[new Random().nextInt(letters.length)];
        return Integer.decode(color);
    }

    public static int getColor(Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int getColor(int brightness) {
        return getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int brightness, int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }

    public static int getColor(int red, int green, int blue) {
        return getColor(red, green, blue, 255);
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        color |= blue;
        return color;
    }

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = 0.003921569F * c.getRed();
        float g = 0.003921569F * c.getGreen();
        float b = 0.003921569F * c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }

    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        Color color = null;
        if (fractions != null) {
            if (colors != null) {
                if (fractions.length == colors.length) {
                    int[] indicies = getFractionIndicies(fractions, progress);

                    if (indicies[0] < 0 || indicies[0] >= fractions.length || indicies[1] < 0 || indicies[1] >= fractions.length) {
                        return colors[0];
                    }
                    float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
                    Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};

                    float max = range[1] - range[0];
                    float value = progress - range[0];
                    float weight = value / max;

                    color = blend(colorRange[0], colorRange[1], 1f - weight);
                } else {
                    throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
                }
            } else {
                throw new IllegalArgumentException("Colours can't be null");
            }
        } else {
            throw new IllegalArgumentException("Fractions can't be null");
        }
        return color;
    }

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float) ratio;
        float ir = (float) 1.0 - r;

        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);

        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;

        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }
        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }

        Color color = null;
        try {
            color = new Color(red, green, blue);
        } catch (IllegalArgumentException exp) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            System.out.println(nf.format(red) + "; " + nf.format(green) + "; " + nf.format(blue));
            exp.printStackTrace();
        }
        return color;
    }

    public static Color getHealthColor(float health, float maxHealth) {
        float[] fractions = {0.0F, 0.5F, 1.0F};
        Color[] colors = {new Color(108, 0, 0), new Color(255, 51, 0), Color.GREEN};
        float progress = health / maxHealth;
        return blendColors(fractions, colors, progress).brighter();
    }

    public static int[] getFractionIndicies(float[] fractions, float progress) {
        int[] range = new int[2];

        int startPoint = 0;
        while (startPoint < fractions.length && fractions[startPoint] <= progress) {
            startPoint++;
        }

        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }

        range[0] = startPoint - 1;
        range[1] = startPoint;

        return range;
    }

    public static Color rainbow(long time, float count, float fade) {
        float hue = ((float) time + (1.0F + count) * 2.0E8F) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
        Color c = new Color((int) color);
        return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade, c.getAlpha() / 255.0F);
    }

    public static int rainbow(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + delay) / 16);
        rainbow %= 360.0D;
        return Color.getHSBColor((float) (rainbow / 360.0D), saturation, brightness).getRGB();
    }

    public static Color getColorWithOpacity(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static int rainbow(int delay, long index) {
        double rainbowState = Math.ceil((double)(System.currentTimeMillis() + index + (long)delay)) / 15.0D;
        return Color.getHSBColor((float)((rainbowState %= 360.0D) / 360.0D), 0.4F, 1.0F).getRGB();
    }

    public static Color fade(Color color) {
        return fade(color, 2, 100);
    }

    public static int color(int n, int n2, int n3, int n4) {
        return (new Color(n, n2, n3, n4)).getRGB();
    }

    public static Color getGradientOffset(Color color1, Color color2, double offset) {
        double inverse_percent;
        int redPart;
        if (offset > 1.0D) {
            inverse_percent = offset % 1.0D;
            redPart = (int)offset;
            offset = redPart % 2 == 0 ? inverse_percent : 1.0D - inverse_percent;
        }

        inverse_percent = 1.0D - offset;
        redPart = (int)((double)color1.getRed() * inverse_percent + (double)color2.getRed() * offset);
        int greenPart = (int)((double)color1.getGreen() * inverse_percent + (double)color2.getGreen() * offset);
        int bluePart = (int)((double)color1.getBlue() * inverse_percent + (double)color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart);
    }

    public static Color fade(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + (float)index / (float)count * 2.0F) % 2.0F - 1.0F);
        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color getHealthColor(EntityLivingBase entityLivingBase) {
        float health = entityLivingBase.getHealth();
        float[] fractions = new float[]{0.0F, 0.15F, 0.55F, 0.7F, 0.9F};
        Color[] colors = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
        float progress = health / entityLivingBase.getMaxHealth();
        return health >= 0.0F ? blendColors(fractions, colors, progress).brighter() : colors[0];
    }


    public static int astolfo(int delay, float offset) {
        float speed = 3000.0F;

        float hue;
        for(hue = Math.abs((float)(System.currentTimeMillis() % (long)delay) + -offset / 21.0F * 2.0F); hue > speed; hue -= speed) {
        }

        if ((double)(hue /= speed) > 0.5D) {
            hue = 0.5F - (hue - 0.5F);
        }

        return Color.HSBtoRGB(hue += 0.5F, 0.5F, 1.0F);
    }

    public static int Yellowastolfo(int delay, float offset) {
        float speed = 2900.0F;

        float hue;
        for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + ((float)(-delay) - offset) * 9.0F; hue > speed; hue -= speed) {
        }

        if ((double)(hue /= speed) > 0.6D) {
            hue = 0.6F - (hue - 0.6F);
        }

        return Color.HSBtoRGB(hue += 0.6F, 0.5F, 1.0F);
    }

    public static Color Yellowastolfo1(int delay, float offset) {
        float speed = 2900.0F;

        float hue;
        for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + ((float)delay - offset) * 9.0F; hue > speed; hue -= speed) {
        }

        if ((double)(hue /= speed) > 0.6D) {
            hue = 0.6F - (hue - 0.6F);
        }

        return new Color(hue += 0.6F, 0.5F, 1.0F);
    }

    public static int getRainbow() {
        return rainbow(10000L, 1.0F).getRGB();
    }

    public static int transparency(int color, double alpha) {
        Color c = new Color(color);
        float r = 0.003921569F * (float)c.getRed();
        float g = 0.003921569F * (float)c.getGreen();
        float b = 0.003921569F * (float)c.getBlue();
        return (new Color(r, g, b, (float)alpha)).getRGB();
    }

    public static Color rainbow(long offset, float fade) {
        float hue = (float)(System.nanoTime() + offset) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0F, 1.0F)), 16);
        Color c = new Color((int)color);
        return new Color((float)c.getRed() / 255.0F * fade, (float)c.getGreen() / 255.0F * fade, (float)c.getBlue() / 255.0F * fade, (float)c.getAlpha() / 255.0F);
    }

    public static float[] getRGBA(int color) {
        float a = (float)(color >> 24 & 255) / 255.0F;
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;
        return new float[]{r, g, b, a};
    }

    public static int intFromHex(String hex) {
        try {
            return hex.equalsIgnoreCase("rainbow") ? rainbow(0L, 1.0F).getRGB() : Integer.parseInt(hex, 16);
        } catch (NumberFormatException var2) {
            return -1;
        }
    }

    public static String hexFromInt(int color) {
        return hexFromInt(new Color(color));
    }

    public static String hexFromInt(Color color) {
        return Integer.toHexString(color.getRGB()).substring(2);
    }

    public static Color darker(Color color, double fraction) {
        int red = (int)Math.round((double)color.getRed() * (1.0D - fraction));
        int green = (int)Math.round((double)color.getGreen() * (1.0D - fraction));
        int blue = (int)Math.round((double)color.getBlue() * (1.0D - fraction));
        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }

        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }

        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }

        int alpha = color.getAlpha();
        return new Color(red, green, blue, alpha);
    }

    public static Color lighter(Color color, double fraction) {
        int red = (int)Math.round((double)color.getRed() * (1.0D + fraction));
        int green = (int)Math.round((double)color.getGreen() * (1.0D + fraction));
        int blue = (int)Math.round((double)color.getBlue() * (1.0D + fraction));
        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }

        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }

        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }

        int alpha = color.getAlpha();
        return new Color(red, green, blue, alpha);
    }

    public static String getHexName(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        String rHex = Integer.toString(r, 16);
        String gHex = Integer.toString(g, 16);
        String bHex = Integer.toString(b, 16);
        return (rHex.length() == 2 ? rHex : "0" + rHex) + (gHex.length() == 2 ? gHex : "0" + gHex) + (bHex.length() == 2 ? bHex : "0" + bHex);
    }

    public static double colorDistance(double r1, double g1, double b1, double r2, double g2, double b2) {
        double a = r2 - r1;
        double b3 = g2 - g1;
        double c = b2 - b1;
        return Math.sqrt(a * a + b3 * b3 + c * c);
    }

    public static double colorDistance(double[] color1, double[] color2) {
        return colorDistance(color1[0], color1[1], color1[2], color2[0], color2[1], color2[2]);
    }

    public static double colorDistance(Color color1, Color color2) {
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        return colorDistance(rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2]);
    }

    public static boolean isDark(double r, double g, double b) {
        double dWhite = colorDistance(r, g, b, 1.0D, 1.0D, 1.0D);
        double dBlack = colorDistance(r, g, b, 0.0D, 0.0D, 0.0D);
        return dBlack < dWhite;
    }

    public static boolean isDark(Color color) {
        float r = (float)color.getRed() / 255.0F;
        float g = (float)color.getGreen() / 255.0F;
        float b = (float)color.getBlue() / 255.0F;
        return isDark(r, g, b);
    }

    public static void setColor(Color c) {
        GL11.glColor4f((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
    }

    public static Color TwoColoreffect(Color cl1, Color cl2, double speed) {
        double thing = speed / 4.0D % 1.0D;
        float val = MathUtils.clamp((float)Math.sin(18.84955592153876D * thing) / 2.0F + 0.5F, 0.0F, 1.0F);
        return new Color(MathUtils.lerp((float)cl1.getRed() / 255.0F, (float)cl2.getRed() / 255.0F, val), MathUtils.lerp((float)cl1.getGreen() / 255.0F, (float)cl2.getGreen() / 255.0F, val), MathUtils.lerp((float)cl1.getBlue() / 255.0F, (float)cl2.getBlue() / 255.0F, val));
    }


    public static int getTeamColor(Entity entityIn) {
        int i = entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("пїЅf[пїЅcRпїЅf]пїЅc" + entityIn.getName()) ? getColor(new Color(255, 60, 60)) : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("пїЅf[пїЅ9BпїЅf]пїЅ9" + entityIn.getName()) ? getColor(new Color(60, 60, 255)) : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("пїЅf[пїЅeYпїЅf]пїЅe" + entityIn.getName()) ? getColor(new Color(255, 255, 60)) : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("пїЅf[пїЅaGпїЅf]пїЅa" + entityIn.getName()) ? getColor(new Color(60, 255, 60)) : getColor(new Color(255, 255, 255)))));
        return i;
    }

    public static Color astolfoColors1(int yOffset, int yTotal) {
        float speed = 2900.0F;

        float hue;
        for(hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (float)((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
        }

        if ((double)(hue /= speed) > 0.5D) {
            hue = 0.5F - (hue - 0.5F);
        }

        return new Color(hue += 0.5F, 0.5F, 1.0F);
    }

    public static Color rainbowEffect(final long offset, final float fade) {
        final float hue = (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        final Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade, c.getAlpha() / 255.0f);
    }

    public static int toARGB(final int r, final int g, final int b, final int a) {
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }

    public static void rainbowGL11() {
        final float x = System.currentTimeMillis() % 15000L / 4000.0f;
        final float red = 0.5f + 0.5f * MathHelper.sin(x * 3.1415927f);
        final float green = 0.5f + 0.5f * MathHelper.sin((x + 1.3333334f) * 3.1415927f);
        final float blue = 0.5f + 0.5f * MathHelper.sin((x + 2.6666667f) * 3.1415927f);
        GL11.glColor4d(red, green, blue, 255.0);
    }

    public static Color rainbowColor(final long offset, final int speed, final float fade) {
        final double millis = (System.currentTimeMillis() + offset) % (10000L / speed) / (10000.0f / speed);
        final Color c = Color.getHSBColor((float)millis, 0.8f, 0.8f);
        return new Color(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, fade);
    }

    public static Color rainbowColor(final long offset, final float fade) {
        final float huge = (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(huge, 1.0f, 1.0f)), 16);
        final Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, fade);
    }

    public static Color astolfoColors(int yOffset, int yTotal) {
        float speed = 2900F;
        float hue = (float) (System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) * 9);
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return new Color(hue, 0.5f, 1F);
    }

    public static int getRealStringLength(String str, int virtualStrLen) {
        char chr;
        for(int i=0; i<virtualStrLen && i<str.length(); i++) {
            chr = str.charAt(i);
            if(chr == '\u00a7') {
                i++;
                virtualStrLen+=2;
                continue;
            }
        }
        if(virtualStrLen >= str.length()) virtualStrLen = str.length();
        return virtualStrLen;
    }


    public static String stringToRainbow(String parString)
    {
        int stringLength = parString.length();
        if (stringLength < 1)
        {
            return "";
        }
        String outputString = "";
        ChatFormatting[] colorChar =
                {
                        ChatFormatting.RED,
                        ChatFormatting.GOLD,
                        ChatFormatting.YELLOW,
                        ChatFormatting.GREEN,
                        ChatFormatting.AQUA,
                        ChatFormatting.BLACK,
                        ChatFormatting.LIGHT_PURPLE,
                        ChatFormatting.DARK_PURPLE
                };
        for (int i = 0; i < stringLength; i++)
        {
            outputString = outputString+colorChar[i%8]+parString.charAt(i);
        }
        return outputString;
    }


    public static int fadeColor(int startColor, int endColor, float progress) {
        if (progress > 1) {
            progress = 1 - progress % 1;
        }
        return fade(startColor, endColor, progress);
    }

    public static int fade(int startColor, int endColor, float progress) {
        float invert = 1.0f - progress;
        int r = (int) ((startColor >> 16 & 0xFF) * invert + (endColor >> 16 & 0xFF) * progress);
        int g = (int) ((startColor >> 8 & 0xFF) * invert + (endColor >> 8 & 0xFF) * progress);
        int b = (int) ((startColor & 0xFF) * invert + (endColor & 0xFF) * progress);
        int a = (int) ((startColor >> 24 & 0xFF) * invert + (endColor >> 24 & 0xFF) * progress);
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }

    public static Color rainbowCol(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((double)((System.currentTimeMillis() + (long)delay) / 16L));
        return Color.getHSBColor((float)((rainbow %= 360.0D) / 360.0D), saturation, brightness);
    }

    public static int rainbowNew(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((double)((System.currentTimeMillis() + (long)delay) / 16L));
        return Color.getHSBColor((float)((rainbow %= 360.0D) / 360.0D), saturation, brightness).getRGB();
    }
}
