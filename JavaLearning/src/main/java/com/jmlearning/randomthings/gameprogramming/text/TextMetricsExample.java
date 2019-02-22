package com.jmlearning.randomthings.gamingprogramming.text;

import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;
import com.jmlearning.randomthings.gamingprogramming.utils.Utility;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class TextMetricsExample extends SimpleFramework {

    public TextMetricsExample() {

        appWidth = 640;
        appHeight = 480;
        appSleep = 10L;
        appTitle = "Text Metrics Example";
    }

    @Override
    protected void initialize() {

        super.initialize();
    }

    @Override
    protected void render(Graphics g) {

        super.render(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40);
        g2d.setFont(font);
        g2d.setColor(Color.GREEN);
        String str = "Um, Meow?";
        int x = 50;
        int y = 50;
        g2d.drawString(str, x, y);

        // floating point values
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout t1 = new TextLayout(str, font, frc);
        int newY = y + (int)(t1.getAscent() + t1.getDescent() + t1.getLeading());
        g2d.drawString(str, x, newY);

        // center everything
        g2d.setColor(Color.GRAY);
        int sw = canvas.getWidth();
        int sh = canvas.getHeight();
        int cx = sw / 2;
        int cy = sh / 2;
        g2d.drawLine(0, cy, sw, cy);
        g2d.drawLine(cx, 0, cx, sh);

        String center = "~Should Center~";
        int stringWidth = g2d.getFontMetrics().stringWidth(center);
        float dy = g2d.getFontMetrics().getLineMetrics(
                center, g2d).getBaselineOffsets()[Font.CENTER_BASELINE];
        g2d.drawString(center, cx - stringWidth / 2, cy - dy);

        // draw pixel
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x - 1, y - 1, 3, 3);
        ArrayList<String> console = new ArrayList <>();
        console.add("Baseline: " + t1.getBaseline());
        float[] baselineOffsets = t1.getBaselineOffsets();
        console.add("Baseline-Offset[ ROMAN ]: " + baselineOffsets[Font.ROMAN_BASELINE]);
        console.add("Baseline-Offset[ CENTER ]: " + baselineOffsets[Font.CENTER_BASELINE]);
        console.add("Baseline-Offset[ HANGING ]: " + baselineOffsets[Font.HANGING_BASELINE]);
        console.add("Ascent: " + t1.getAscent());
        console.add("Descent: " + t1.getDescent());
        console.add("Leading: " + t1.getLeading());
        console.add("Advance: " + t1.getAdvance());
        console.add("Visible-Advance: " + t1.getVisibleAdvance());
        console.add("Bounds: " + toString(t1.getBounds()));

        Font propFont = new Font("Courier New", Font.BOLD, 14);
        g2d.setFont(propFont);
        int xLeft = x;
        int xRight = xLeft + (int) t1.getVisibleAdvance();

        // draw baseline
        g2d.setColor(Color.WHITE);
        int baselineY = y + (int) baselineOffsets[Font.ROMAN_BASELINE];
        g2d.drawLine(xLeft, baselineY, xRight, baselineY);
        g2d.drawString("Roman Baseline", xRight, baselineY);

        // draw center
        g2d.setColor(Color.BLUE);
        int centerY = y + (int) baselineOffsets[Font.CENTER_BASELINE];
        g2d.drawLine(xLeft, centerY, xRight, centerY);
        g2d.drawString("Center Baseline", xRight, centerY);

        // draw hanging
        g2d.setColor(Color.GRAY);
        int hangingY = y + (int) baselineOffsets[Font.HANGING_BASELINE];
        g2d.drawLine(xLeft, hangingY, xRight, hangingY);
        g2d.drawString("Hanging Baseline", xRight, hangingY);

        // draw ascent
        g2d.setColor(Color.YELLOW);
        int propY = y - (int) t1.getAscent();
        g2d.drawLine(xLeft, propY, xRight, propY);
        TextLayout temp = new TextLayout("Hanging Baseline",
                propFont, g2d.getFontRenderContext());
        g2d.drawString("Ascent", xRight + temp.getVisibleAdvance(), propY);

        // draw descent
        g2d.setColor(Color.RED);
        propY = y + (int) t1.getDescent();
        g2d.drawLine(xLeft, propY, xRight, propY);
        g2d.drawString("Descent", xRight, propY);

        // draw leading
        g2d.setColor(Color.GREEN);
        propY = y + (int) t1.getDescent() + (int) t1.getLeading();
        g2d.drawLine(xLeft, propY, xRight, propY);
        temp = new TextLayout("Descent", propFont, g2d.getFontRenderContext());
        g2d.drawString("Leading", xRight + temp.getVisibleAdvance(), propY);

        // draw console output
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setFont(new Font("Courier New", Font.BOLD, 12));
        Utility.drawString(g2d, 20, 300, console);
    }

    private String toString(Rectangle2D r) {

        return "[ x=\" + r.getX() +" +
                " \", y=\" + r.getY() + \", w=\" + r.getWidth()\n" +
                "\t\t\t\t+ \", h=\" + r.getHeight() + \" ]";
    }

    public static void main(String[] args) {

        launchApp(new TextMetricsExample());
    }
}
