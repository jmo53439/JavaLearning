package com.jmlearning.randomthings.raytracer.objects;

import com.jmlearning.randomthings.raytracer.core.Color;
import com.jmlearning.randomthings.raytracer.math.Vector;

public class Light {
    
    public final Vector position;
    public final Color color;
    
    public Light(double x, double y, double z, Color c) {
        
        position = new Vector(x, y, z);
        color = c;
    }
}
