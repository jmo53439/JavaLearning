package com.jmlearning.randomthings.gamingprogramming.text;

import com.jmlearning.randomthings.gamingprogramming.utils.SimpleFramework;

import java.awt.*;

public class BoxedTextSolution extends SimpleFramework {

    public BoxedTextSolution() {

        appWidth = 640;
        appHeight = 640;
        appSleep = 10L;
        appTitle = "Boxed Text Problem";
        appBackground = Color.WHITE;
        appFPSColor = Color.BLACK;
    }

    @Override
    protected void initialize() {

        super.initialize();
    }

    @Override
    protected void render(Graphics g) {

        super.render(g);

        // set font
        Font font = new Font("Arial", Font.PLAIN, 24);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();

        int x = 20;
        int y = 50;

        // draw top
        String str = "Draw the Top Line";
        g.setColor(Color.DARK_GRAY);
        g.drawString(str, x, y);
        int width = 100;
        g.setColor(Color.RED);
        g.drawLine(x, y, x + width, y);

        // calculate string width
        y += 40;
        str = "Calculate Correct Width";
        g.setColor(Color.DARK_GRAY);
        g.drawString(str, x, y);
        width = fm.stringWidth(str);
        g.setColor(Color.GREEN);
        g.drawLine(x, y, x + width, y);

        // ascent to offset y
        y += 40;
        g.setColor(Color.DARK_GRAY);
        str = "Offset Text with the Ascent";
        g.drawString(str, x, y + fm.getAscent());
        width = fm.stringWidth(str);
        g.setColor(Color.BLUE);
        g.drawLine(x, y, x + width, y);

        // ascent + descent + leading + height
        y += 40;
        g.setColor(Color.DARK_GRAY);
        str = "Calculate Height of the Font";
        g.drawString(str, x, y + fm.getAscent());
        width = fm.stringWidth(str);
        g.setColor(Color.BLUE);
        g.drawLine(x, y, x + width, y);
        int height = fm.getAscent() + fm.getDescent() + fm.getLeading();
        g.drawLine(x, y + height, x + width, y + height);

        // box text
        y += 40;
        g.setColor(Color.DARK_GRAY);
        str = "Subaru WRX or Mitsubishi Evolution";
        g.drawString(str, x, y + fm.getAscent());
        width = fm.stringWidth(str);
        g.setColor(Color.BLUE);
        height = fm.getAscent() + fm.getDescent() + fm.getLeading();
        g.drawRect(x, y, width, height);
    }

    public static void main(String[] args) {

        launchApp(new BoxedTextSolution());
    }
}
