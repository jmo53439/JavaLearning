package com.jmlearning.randomthings.tilegame.gfx;

import java.awt.image.BufferedImage;

public class Assets {
    
    private static final int width = 32, height = 32;
    public static BufferedImage player, dirt, grass, stone, tree;
    public static BufferedImage[] playerUp, playerDown, playerLeft, playerRight;
    
    public static void init() {
        
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
        player = sheet.crop(width * 4, 0, width, height);
        dirt = sheet.crop(width, 0, width, height);
        grass = sheet.crop(width * 2, 0, width, height);
        stone = sheet.crop(width * 3, 0, width, height);
        tree = sheet.crop(0, 0, width, height);
        
        playerUp = new BufferedImage[2];
        playerDown = new BufferedImage[2];
        playerLeft = new BufferedImage[2];
        playerRight = new BufferedImage[2];
        
        playerUp[0] = sheet.crop(width * 6, 0, width, height);
        playerUp[1] = sheet.crop(width * 7, 0, width, height);
        
        playerDown[0] = sheet.crop(width * 4, 0, width, height);
        playerDown[1] = sheet.crop(width * 5, 0, width, height);
        
        playerLeft[0] = sheet.crop(width * 6, height, width, height);
        playerLeft[1] = sheet.crop(width * 7, height, width, height);
        
        playerRight[0] = sheet.crop(width * 4, height, width, height);
        playerRight[1] = sheet.crop(width * 5, height, width, height);
    }
}
