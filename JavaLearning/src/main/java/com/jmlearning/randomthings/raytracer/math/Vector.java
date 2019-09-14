package com.jmlearning.randomthings.raytracer.math;

public class Vector {
    
    public double x;
    public double y;
    public double z;
    
    public Vector() {
    
        set(0.0, 0.0, 0.0);
    }
    
    public Vector(double x, double y, double z) {
    
        set(x, y, z);
    }
    
    public Vector(Vector other) {
    
        set(other);
    }
    
    public Vector set(double x, double y, double z) {
        
        this.x = x;
        this.y = y;
        this.z = z;
        
        return this;
    }
    
    public Vector set(Vector other) {
        
        return set(other.x, other.y, other.z);
    }
    
    public double length() {
        
        return Math.sqrt(lenSquared());
    }
    
    public double lenSquared() {
        
        return dot(this);
    }
    
    public Vector normalize() {
        return mul(1.0 / length());
    }
    
    public Vector add(Vector other) {
        return add(other.x, other.y, other.z);
    }
    
    public Vector add(double x, double y, double z) {
        return set(this.x + x, this.y + y, this.z + z);
    }
    
    public Vector sub(Vector other) {
        return sub(other.x, other.y, other.z);
    }
    
    public Vector sub(double x, double y, double z) {
        return set(this.x - x, this.y - y, this.z - z);
    }
    
    public Vector mul(double s) {
        return set(x * s, y * s, z * s);
    }
    
    public double dot(Vector other) {
        return dot(other.x, other.y, other.z);
    }
    
    public double dot(double x, double y, double z) {
        return this.x * x + this.y * y + this.z * z;
    }
    
    public static double distSquared(Vector a, Vector b) {
        
        double x = a.x - b.x;
        double y = a.y - b.y;
        double z = a.z - b.z;
        
        return x * x + y * y + z * z;
    }
}
