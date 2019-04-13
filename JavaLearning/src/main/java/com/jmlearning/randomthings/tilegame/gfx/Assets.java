package com.jmlearning.randomthings.tilegame.gfx;

import java.awt.image.BufferedImage;

public class Assets {
    
    private static final int width = 32, height = 32;
    public static BufferedImage player, dirt, grass, stone, tree;
    public static BufferedImage[] playerUp, playerDown, playerLeft, playerRight;
    public static BufferedImage[] zombieUp, zombieDown, zombieLeft, zombieRight;
    public static BufferedImage[] btnStart;
    
    public static void init() {
        
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
        
        player = sheet.crop(width * 4, 0, width, height);
        dirt = sheet.crop(width, 0, width, height);
        grass = sheet.crop(width * 2, 0, width, height);
        stone = sheet.crop(width * 3, 0, width, height);
        tree = sheet.crop(0, 0, width, height);
    
        btnStart = new BufferedImage[2];
        btnStart[0] = sheet.crop(width * 6, height * 4, width * 2, height);
        btnStart[1] = sheet.crop(width * 6, height * 5, width * 2, height);
    
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
        
        zombieUp = new BufferedImage[2];
        zombieDown = new BufferedImage[2];
        zombieLeft = new BufferedImage[2];
        zombieRight = new BufferedImage[2];
        
        zombieUp[0] = sheet.crop(width * 6, height * 2, width, height);
        zombieUp[1] = sheet.crop(width * 7, height * 2, width, height);
        
        zombieDown[0] = sheet.crop(width * 4, height * 2, width, height);
        zombieDown[1] = sheet.crop(width * 5, height * 2, width, height);
        
        zombieLeft[0] = sheet.crop(width * 6, height * 3, width, height);
        zombieLeft[1] = sheet.crop(width * 7, height * 3, width, height);
        
        zombieRight[0] = sheet.crop(width * 4, height * 3, width, height);
        zombieRight[1] = sheet.crop(width * 5, height * 3, width, height);
    }
}
