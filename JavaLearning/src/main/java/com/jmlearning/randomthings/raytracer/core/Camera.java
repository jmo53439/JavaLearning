package com.jmlearning.randomthings.raytracer.core;

import com.jmlearning.randomthings.raytracer.math.Matrix;
import com.jmlearning.randomthings.raytracer.math.Vector;

public class Camera {
    
    public final double distance;
    public final double fov;
    
    private Matrix transform;
    
    public Camera(double distance, double fov) {
        
        this.distance = distance;
        this.fov = fov;
        
        transform = new Matrix();
    }
    
    public Vector transform(Vector v) {
    
        double x = v.x * transform.e00 + v.y * transform.e10 + v.z * transform.e20;
        double y = v.x * transform.e01 + v.y * transform.e11 + v.z * transform.e21;
        double z = v.x * transform.e02 + v.y * transform.e12 + v.z * transform.e22;
        
        return v.set(x, y, z);
    }
    
    public void rotate(double aboutX, double aboutY) {
        
        transform.rotationAboutX(aboutX)
                .concat(transform, new Matrix().rotationAboutY(aboutY));
    }
}
