package com.jmlearning.randomthings.raytracer.core;

import com.jmlearning.randomthings.raytracer.math.Geometry;
import com.jmlearning.randomthings.raytracer.math.Vector;
import com.jmlearning.randomthings.raytracer.objects.Light;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tracer {
    
    public static final double TOLERANCE = 0.000001;
    
    private static final int MAX_RECURSION = 4;
    private static final double REFLECTIVITY = 0.10;
    
    private final ExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private final List<Callable<Object>> todo = new ArrayList<>();
    
    private final int[] pixels;
    private final int width;
    private final int height;
    
    public Tracer(int[] pixels, int width, int height) {
        
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }
    
    public void render(Camera camera, Scene scene) {
    
        int xStart = width >> 1;
        int yStart = height >> 1;
        
        todo.clear();
        
        for(int y = 0; y < height; y++) {
            
            int yFinal = y;
            
            todo.add(Executors.callable(() -> {
            
                Ray ray = new Ray();
                int offset = yFinal * width;
                camera.transform(ray.origin.set(0.0, 0.0, 1.0)).mul(-camera.distance);
                
                for(int wx = -xStart; wx < xStart; wx++) {
    
                    camera.transform(ray.direction.set(wx, yStart - yFinal, camera.fov)).normalize();
                    pixels[offset++] = traceRay(scene, ray, 0);
                }
            }));
        }
        
        try {
            
            executor.invokeAll(todo);
        }
        catch(InterruptedException ie) {
            
            ie.printStackTrace();
        }
    }
    
    private int traceRay(Scene scene, Ray ray, int level) {
    
        Ray hitRay = new Ray();
    
        Geometry intersected = scene.intersect(ray, hitRay);
        
        if(intersected != null) {
            
            Material material = intersected.getMaterial();
            
            Vector kd = new Vector();
            Vector ks = new Vector();
            Vector kr = new Vector();
            
            for(Light light : scene.lights) {
                
                Vector toLight = new Vector(light.position).sub(hitRay.origin);
                boolean shadowed = scene.isShadowed(toLight, hitRay.origin);
                
                if(!shadowed) {
                    
                    toLight.normalize();
                    
                    double intensity = toLight.dot(hitRay.direction);
                    
                    if(intensity > 0.0) {
                        
                        intensity *= 1.0 - REFLECTIVITY;
                        
                        Color diff = material.computeDiffuse(hitRay);
                        
                        kd.add(intensity * diff.red * light.color.red,
                                intensity * diff.green * light.color.green,
                                intensity * diff.blue * light.color.blue);
                        
                        double specular = specularity(ray, hitRay, toLight);
                        
                        if(specular > 0.0) {
                            
                            specular = Math.pow(Math.min(specular, 1.0), material.computeShininess(hitRay));
                            Color spec = material.computeSpecular(hitRay);
                            
                            ks.add(specular * spec.red * light.color.red,
                                    specular * spec.green * light.color.green,
                                    specular * spec.blue * light.color.blue);
                        }
                    }
                }
            }
            
            if(level <= MAX_RECURSION) {
                
                Ray result = new Ray();
                
                result.direction.set(hitRay.direction)
                        .mul(-2.0 * ray.direction.dot(hitRay.direction))
                        .add(ray.direction);
    
                result.origin.set(result.direction).mul(TOLERANCE).add(hitRay.origin);
                
                Color color = new Color(traceRay(scene, result, level + 1));
                
                kr.add(REFLECTIVITY * color.red, REFLECTIVITY * color.green, REFLECTIVITY * color.blue);
            }
            
            return shade(scene.ambient.red * material.ambient.red + kd.x + ks.x + kr.x, 0x10) |
                    shade(scene.ambient.green * material.ambient.green + kd.y + ks.y + kr.y, 0x08) |
                    shade(scene.ambient.blue * material.ambient.blue + kd.z + ks.z + kr.z, 0x00);
        }
        
        return 0x000000;
    }
    
    private static double specularity(Ray ray, Ray hitRay, Vector toLight) {
        
        double vx = ray.origin.x - hitRay.origin.x;
        double vy = ray.origin.y - hitRay.origin.y;
        double vz = ray.origin.z - hitRay.origin.z;
        
        double ol = Math.sqrt(vx * vx + vy * vy + vz * vz);
        
        vx += toLight.x * ol;
        vy += toLight.y * ol;
        vz += toLight.z * ol;
        
        double d = vx * hitRay.direction.x + vy * hitRay.direction.y + vz * hitRay.direction.z;
        return d > 0.0 ? d / Math.sqrt(vx * vx + vy * vy + vz * vz) : 0.0;
    }
    
    private static int shade(double v, int shift) {
        
        return (int)(Math.max(0.0, Math.min(v, 1.0)) * 255.0) << shift;
    }
}
