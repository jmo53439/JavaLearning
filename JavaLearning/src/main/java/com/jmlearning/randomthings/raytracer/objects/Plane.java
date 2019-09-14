package com.jmlearning.randomthings.raytracer.objects;

import com.jmlearning.randomthings.raytracer.core.Ray;
import com.jmlearning.randomthings.raytracer.math.Geometry;
import com.jmlearning.randomthings.raytracer.math.Vector;

public class Plane extends Geometry {
    
    private final Vector normal;
    private final double distance;
    
    public Plane(double a, double b, double c, double d) {
        
        normal = new Vector(a, b, c).normalize();
        distance = d;
    }
    
    @Override
    public boolean intersect(Ray inRay, Ray outRay) {
        
        double vd = normal.dot(inRay.direction);
        
        if(vd != 0.0) {
            
            double t = (distance - normal.dot(inRay.origin)) / vd;
            
            if(t > 0.0) {
    
                outRay.origin.set(inRay.direction).mul(t).add(inRay.origin);
                outRay.direction.set(normal);
                
                return true;
            }
        }
        
        return false;
    }
}
