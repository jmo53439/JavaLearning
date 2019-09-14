package com.jmlearning.randomthings.raytracer.math;

import com.jmlearning.randomthings.raytracer.core.Material;
import com.jmlearning.randomthings.raytracer.core.Ray;

public abstract class Geometry {
    
    private Material material;
    
    protected Geometry() {
    
    }
    
    public Material getMaterial() {
        
        return material;
    }
    
    public void setMaterial(Material m) {
        
        material = (m != null) ? m : Material.DEFAULT;
    }
    
    public abstract boolean intersect(Ray inRay, Ray outRay);
}
