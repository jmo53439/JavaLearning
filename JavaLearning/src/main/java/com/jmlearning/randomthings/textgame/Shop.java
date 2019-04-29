package com.jmlearning.randomthings.textgame;

import com.jmlearning.randomthings.textgame.creatures.player.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Shop {
    
    public static final String[] ITEM = {"Sword", "Armor", "Potions"};
    public static final int[] PRICE = {100, 50, 10};
    
    public static void printStore(Player player) {
        
        System.out.println("\fWelcome to The-Store.\n");
        System.out.println("You have " + player.getPouch().getCoins() + " coins.\n");
        
        for(int i = 0; i < ITEM.length; i++) {
        
            System.out.println(i + 1 + ". " + ITEM[i] + ", Price: " + PRICE[i]);
        }
        
        System.out.print("\nWhat would you like to buy? ");
        getInput(player);
    }
    
    public static void getInput(Player player) {
    
        Scanner scanner = new Scanner(System.in);
        
        int itemIndex;
        
        try {
            
            itemIndex = scanner.nextInt() - 1;
            
            if(itemIndex < 0 || itemIndex >= ITEM.length)
                return;
        }
        catch(InputMismatchException e) {
            
            System.out.println("\nExiting Store...");
            RaidDungeon.delay();
            
            return;
        }
        
        buyItem(player, itemIndex);
    }
    
    public static void buyItem(Player player, int itemIndex) {
        
        if(player.getPouch().getCoins() < PRICE[itemIndex]) {
        
            System.out.println("\nUm, you dont have enough loot for " +
                    ITEM[itemIndex] + ". Please get more money right meow and come back.");
            RaidDungeon.delay();
            printStore(player);
            
            return;
        }
        
        switch(itemIndex) {
            
            case 0:
                player.addSword("wood");
                break;
                
            case 1:
                player.addArmor("leather");
                break;
                
            case 2:
                player.addPotion(1);
                break;
        }
    
        player.getPouch().removeCoins(PRICE[itemIndex]);
        System.out.println("\nYou purchased: " + ITEM[itemIndex] + ", for " +
                PRICE[itemIndex] + " coins.");
        RaidDungeon.delay();
    }
}
