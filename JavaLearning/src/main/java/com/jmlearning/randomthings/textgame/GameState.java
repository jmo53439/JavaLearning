package com.jmlearning.randomthings.textgame;

import com.jmlearning.randomthings.textgame.creatures.player.Player;

import java.io.*;
import java.util.Scanner;

public class GameState {
    
    public static final Scanner scanner = new Scanner(System.in);
    
    public static void saveState(Player player) {
        
        if(player.getName().equals("")) {
        
            System.out.print("Enter your Name: ");
            String name = scanner.nextLine();
            name = name.replaceAll("\\s+", "");
            player.setName(name);
        }
        
        try {
            
            File userData = new File("users/" + player.getName() + ".txt");
            userData.getParentFile().mkdirs();
            PrintWriter writer = new PrintWriter(userData);
            String encrypted = EncryptAndDecrypt.encrypt(player.getData());
            writer.println(encrypted);
            writer.close();
            System.out.println("Your data was saved. You may now safely close the console.");
        }
        catch(FileNotFoundException fex) {
            
            System.out.println("File could not be written. Data was not saved");
        }
    }
    
    public static void loadState(Player player) throws FileNotFoundException, IOException {
    
        BufferedReader reader = new BufferedReader(
                new FileReader("users/" + player.getName() + ".txt"));
        String[] data = EncryptAndDecrypt.decrypt(reader.readLine()).split(" ");
        reader.close();
        
        String name = data[0];
        boolean hasSword = Boolean.parseBoolean(data[1]);
        boolean hasArmor = Boolean.parseBoolean(data[2]);
        int enemiesDefeated = Integer.parseInt(data[3]);
        int health = Integer.parseInt(data[4]);
        int numberOfPotions = Integer.parseInt(data[5]);
        int coins = Integer.parseInt(data[6]);
        
        player.setName(name);
        
        if(hasSword)
            player.addSword("wood");
        
        if(hasArmor)
            player.addArmor("leather");
    
        player.setEnemiesDefeated(enemiesDefeated);
        player.setHealth(health);
        player.setNumberOfPotions(numberOfPotions);
        player.getPouch().setCoins(coins);
    }
}
