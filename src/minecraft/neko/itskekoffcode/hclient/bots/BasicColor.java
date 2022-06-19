package neko.itskekoffcode.hclient.bots;

import java.util.*;

public class BasicColor
{
    public static final BasicColor TRANSPARENT;
    public static Map<Integer, BasicColor> colors;
    final int r;
    final int g;
    final int b;

    private static BasicColor toCol(final int r, final int g, final int b) {
        return new BasicColor(r, g, b);
    }

    public BasicColor(final int r, final int g, final int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int shaded(final byte shaderB) {
        double shader = 0.0;
        switch (shaderB) {
            case 0: {
                shader = 0.71;
                break;
            }
            case 1: {
                shader = 0.85;
                break;
            }
            case 2: {
                shader = 1.0;
                break;
            }
            case 3: {
                shader = 0.53;
                break;
            }
        }
        return 0xFF000000 | this.toInt(this.r, shader) << 16 | this.toInt(this.g, shader) << 8 | this.toInt(this.b, shader);
    }

    private int toInt(final int c, final double shader) {
        return (int)Math.round(c * shader);
    }

    static {
        TRANSPARENT = new BasicColor(0, 0, 0) {
            @Override
            public int shaded(final byte b) {
                return 0;
            }
        };
        (BasicColor.colors = new HashMap<Integer, BasicColor>()).put(0, BasicColor.TRANSPARENT);
        BasicColor.colors.put(1, toCol(127, 178, 56));
        BasicColor.colors.put(2, toCol(247, 233, 163));
        BasicColor.colors.put(3, toCol(199, 199, 199));
        BasicColor.colors.put(4, toCol(255, 0, 0));
        BasicColor.colors.put(5, toCol(160, 160, 255));
        BasicColor.colors.put(6, toCol(167, 167, 167));
        BasicColor.colors.put(7, toCol(0, 124, 0));
        BasicColor.colors.put(8, toCol(255, 255, 255));
        BasicColor.colors.put(9, toCol(164, 168, 184));
        BasicColor.colors.put(10, toCol(151, 109, 77));
        BasicColor.colors.put(11, toCol(112, 112, 112));
        BasicColor.colors.put(12, toCol(64, 64, 255));
        BasicColor.colors.put(13, toCol(143, 119, 72));
        BasicColor.colors.put(14, toCol(255, 252, 245));
        BasicColor.colors.put(15, toCol(216, 127, 51));
        BasicColor.colors.put(16, toCol(178, 76, 216));
        BasicColor.colors.put(17, toCol(102, 153, 216));
        BasicColor.colors.put(18, toCol(229, 229, 51));
        BasicColor.colors.put(19, toCol(127, 204, 25));
        BasicColor.colors.put(20, toCol(242, 127, 165));
        BasicColor.colors.put(21, toCol(76, 76, 76));
        BasicColor.colors.put(22, toCol(153, 153, 153));
        BasicColor.colors.put(23, toCol(76, 127, 153));
        BasicColor.colors.put(24, toCol(127, 63, 178));
        BasicColor.colors.put(25, toCol(51, 76, 178));
        BasicColor.colors.put(26, toCol(102, 76, 51));
        BasicColor.colors.put(27, toCol(102, 127, 51));
        BasicColor.colors.put(28, toCol(153, 51, 51));
        BasicColor.colors.put(29, toCol(25, 25, 25));
        BasicColor.colors.put(30, toCol(250, 238, 77));
        BasicColor.colors.put(31, toCol(92, 219, 213));
        BasicColor.colors.put(32, toCol(74, 128, 255));
        BasicColor.colors.put(33, toCol(0, 217, 58));
        BasicColor.colors.put(34, toCol(129, 86, 49));
        BasicColor.colors.put(35, toCol(112, 2, 0));
        BasicColor.colors.put(36, toCol(209, 177, 161));
        BasicColor.colors.put(37, toCol(159, 82, 36));
        BasicColor.colors.put(38, toCol(149, 87, 108));
        BasicColor.colors.put(39, toCol(112, 108, 138));
        BasicColor.colors.put(40, toCol(186, 133, 36));
        BasicColor.colors.put(41, toCol(103, 117, 53));
        BasicColor.colors.put(42, toCol(160, 77, 78));
        BasicColor.colors.put(43, toCol(57, 41, 35));
        BasicColor.colors.put(44, toCol(135, 107, 98));
        BasicColor.colors.put(45, toCol(87, 92, 92));
        BasicColor.colors.put(46, toCol(122, 73, 88));
        BasicColor.colors.put(47, toCol(76, 62, 92));
        BasicColor.colors.put(48, toCol(76, 50, 35));
        BasicColor.colors.put(49, toCol(76, 82, 42));
        BasicColor.colors.put(50, toCol(142, 60, 46));
        BasicColor.colors.put(51, toCol(37, 22, 16));
        BasicColor.colors.put(52, toCol(189, 48, 49));
        BasicColor.colors.put(53, toCol(148, 63, 97));
        BasicColor.colors.put(54, toCol(92, 25, 29));
        BasicColor.colors.put(55, toCol(22, 126, 134));
        BasicColor.colors.put(56, toCol(58, 142, 140));
        BasicColor.colors.put(57, toCol(86, 44, 62));
        BasicColor.colors.put(58, toCol(20, 180, 133));
    }
}

