package com.jmlearning.randomthings.raytracer.core;

public class Color {
    
    private static final double ONE_OVER_256 = 1.0 / 256.0;
    
    public static final Color WHITE = new Color(1.0, 1.0, 1.0);
    public static final Color BLACK = new Color(0.0, 0.0, 0.0);
    
    public final double red;
    public final double green;
    public final double blue;
    
    public Color(int rgb) {
        
        this(ONE_OVER_256 * (0xFF & rgb >> 0x10),
                ONE_OVER_256 * (0xFF & rgb >> 0x08),
                ONE_OVER_256 * (0xFF & rgb));
    }
    
    public Color(double red, double green, double blue) {
    
        this.red = Math.max(0.0, Math.min(1.0, red));
        this.green = Math.max(0.0, Math.min(1.0, green));
        this.blue = Math.max(0.0, Math.min(1.0, blue));
    }
}
