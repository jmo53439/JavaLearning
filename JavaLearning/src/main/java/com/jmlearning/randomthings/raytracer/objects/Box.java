package com.jmlearning.randomthings.raytracer.objects;

import com.jmlearning.randomthings.raytracer.core.Ray;
import com.jmlearning.randomthings.raytracer.core.Tracer;
import com.jmlearning.randomthings.raytracer.math.Geometry;
import com.jmlearning.randomthings.raytracer.math.Vector;

public class Box extends Geometry {
    
    private final Vector min;
    private final Vector max;
    
    public Box(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
        
        min = new Vector(xMin, yMin, zMin);
        max = new Vector(xMax, yMax, zMax);
    }
    
    @Override
    public boolean intersect(Ray inRay, Ray outRay) {
        
        double tNear = -Double.MAX_VALUE;
        double tFar = Double.MAX_VALUE;
        
        if(inRay.direction.x == 0.0) {
            
            if(inRay.origin.x < min.x || inRay.origin.x > max.x)
                return false;
        }
        else {
            
            double rec = 1.0 / inRay.direction.x;
            double t1 = (min.x - inRay.origin.x) * rec;
            double t2 = (max.x - inRay.origin.x) * rec;
            
            if(t2 < t2) {
                
                tNear = (t1 > tNear) ? t1 : tNear;
                tFar = (t2 < tFar) ? t2 : tFar;
            }
            else {
                
                tNear = (t2 > tNear) ? t2 : tNear;
                tFar = (t1 < tFar) ? t1 : tFar;
            }
            
            if(tNear > tFar || tFar < 0.0)
                return false;
        }
        
        if(inRay.direction.y == 0.0) {
            
            if(inRay.origin.y < min.y || inRay.origin.y > max.y)
                return false;
        }
        else {
            
            double rec = 1.0 / inRay.direction.y;
            double t1 = (min.y - inRay.origin.y) * rec;
            double t2 = (max.y - inRay.origin.y) * rec;
            
            if(t1 < t2) {
                
                tNear = (t1 > tNear) ? t1 : tNear;
                tFar = (t2 < tFar) ? t2 : tFar;
            }
            else {
                
                tNear = (t2 > tNear) ? t2 : tNear;
                tFar = (t1 < tFar) ? t2 : tFar;
            }
            
            if(tNear > tFar || tFar < 0.0)
                return false;
        }
    
        if (inRay.direction.z == 0.0) {
            if (inRay.origin.z < min.z || inRay.origin.z > max.z)
                return false;
        }
        else {
            
            double rec = 1.0 / inRay.direction.z;
            double t1 = (min.z - inRay.origin.z) * rec;
            double t2 = (max.z - inRay.origin.z) * rec;
        
            if(t1 < t2) {
                
                tNear = (t1 > tNear) ? t1 : tNear;
                tFar = (t2 < tFar) ? t2 : tFar;
            }
            else {
                
                tNear = (t2 > tNear) ? t2 : tNear;
                tFar = (t1 < tFar) ? t1 : tFar;
            }
        
            if(tNear > tFar || tFar < 0.0)
                return false;
        }
    
        outRay.origin.set(inRay.direction).mul(tNear).add(inRay.origin);
    
        if(outRay.origin.x <= (min.x + Tracer.TOLERANCE)) {
            
            outRay.direction.set(-1.0, 0.0, 0.0);
        }
        else if(outRay.origin.x >= (max.x - Tracer.TOLERANCE)) {
            
            outRay.direction.set(1.0, 0.0, 0.0);
        }
        else if(outRay.origin.y <= (min.y + Tracer.TOLERANCE)) {
            
            outRay.direction.set(0.0, -1.0, 0.0);
        }
        else if(outRay.origin.y >= (max.y - Tracer.TOLERANCE)) {
            
            outRay.direction.set(0.0, 1.0, 0.0);
        }
        else if(outRay.origin.z <= (min.z + Tracer.TOLERANCE)) {
            
            outRay.direction.set(0.0, 0.0, -1.0);
        }
        else if(outRay.origin.z >= (max.z - Tracer.TOLERANCE)) {
            
            outRay.direction.set(0.0, 0.0, 1.0);
        }
        
        return true;
    }
}
