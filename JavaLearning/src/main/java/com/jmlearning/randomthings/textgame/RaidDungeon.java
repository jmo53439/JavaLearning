package com.jmlearning.randomthings.textgame;

import com.jmlearning.randomthings.textgame.creatures.enemies.*;
import com.jmlearning.randomthings.textgame.creatures.player.Player;
import com.jmlearning.randomthings.textgame.creatures.player.Pouch;
import com.jmlearning.randomthings.textgame.wordinput.Confirmation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RaidDungeon {
    
    public static final int ATK = 1;
    public static final long DELAY = 2000;
    public static final int EXIT = 5;
    public static final int MAX_COINS_DROPPED = 30;
    public static final int RUN = 3;
    public static final int ESCAPE_PENALTY = 5;
    public static final int VISIT_STORE = 4;
    public static final int USE_POTION = 2;
    public static final Random RANDOM = new Random();
    public static final Scanner SCANNER = new Scanner(System.in);
    public static final int UNDEFINED = 6;
    
    public static void main(String[] args) {
    
        Player player = new Player();
        Pouch pouch = player.getPouch();
        
        int armorDropRate = 10;
        int healthPotionDropRate = 50;
        int swordDropRate = 10;
        boolean running = true;
        boolean ranAway = false;
        
        clear();
        
        System.out.println("\fWelcome to the Raid Dungeon");
        System.out.print("Would you like to load your previous game?");
        String loadGameState = SCANNER.nextLine();
        
        if(Confirmation.isConfirmation(loadGameState)) {
        
            System.out.print("\nWhat is your name? ");
            String name = SCANNER.nextLine();
            name = name.replaceAll("\\s+", "");
            player.setName(name);
            
            try {
                
                GameState.loadState(player);
            }
            catch(FileNotFoundException e) {
                
                System.out.println("\nYour saved game was not found. Starting a new game...");
                delay();
            }
            catch(IOException e) {
                
                System.out.println("Input from the keyboard could not be read. Please restart the game.");
            }
        }
        
        while(running) {
    
            Enemy baddie = null;
            List<Class<? extends Enemy>> list = Arrays.asList(
        
                    Bat.class,
                    Slime.class,
                    Snake.class,
                    Spider.class,
                    // TIER 2
                    Ghost.class,
                    Goblin.class,
                    Skeleton.class,
                    Werewolf.class,
                    // TIER 3
                    Vampire.class,
                    Warrior.class,
                    Witch.class,
                    Zombie.class,
                    // TIER 4
                    Dragon.class,
                    Giant.class
            );
            
            try {
                
                baddie = list.get(new Random().nextInt(list.size())).newInstance();
            }
            catch(Exception e) {
            
            }
            
            while(baddie.getHealth() > 0) {
                
                printStatistics(player, baddie);
                startBattle();
                
                int choice;
                
                try {
                    
                    choice = Integer.parseInt(SCANNER.nextLine());
                }
                catch(NumberFormatException e) {
                    
                    System.out.println("Invalid Command, please try again.");
                    continue;
                }
                
                switch(choice) {
                    
                    case ATK:
                        ranAway = false;
                        int playerAtk = player.atk();
                        int enemyAtk = baddie.atk();
                        
                        System.out.println("\nYou Dealt: " + playerAtk + " Damage.");
                        System.out.println("You Took: " + enemyAtk + " Damage.");
    
                        baddie.takeDmg(playerAtk);
                        player.takeDmg(enemyAtk);
                        
                        delay();
                        
                        break;
                        
                    case USE_POTION:
                        if(player.health() > player.FULL_HP - player.POTION_HEALING) {
                            
                            System.out.println("\nYou are Healthy, and do not need a Potion.");
                            RaidDungeon.delay();
                            
                            break;
                        }
                        
                        if(player.getPotions() > 0) {
    
                            player.usePotion();
                            System.out.println("\nYou drank the Potion. Health Restored by: " +
                                    Player.POTION_HEALING + " HP");
                            System.out.println("Current HP: " + player.health());
                        }
                        else {
                            
                            System.out.println("You are out of Potions.");
                            System.out.println("Current HP: " + player.health());
                        }
                        
                        delay();
                        
                        break;
                        
                    case RUN:
                        if(player.getPouch().getCoins() > ESCAPE_PENALTY) {
                            
                            System.out.println("\n" + ESCAPE_PENALTY +
                                    " Coins were stolen by the " + baddie.getName());
                            pouch.removeCoins(ESCAPE_PENALTY);
                        }
                        else {
                            
                            System.out.println("\nThe enemy did " + ESCAPE_PENALTY +
                                    " Damage before you managed to escape.");
                            player.takeDmg(ESCAPE_PENALTY);
                        }
                        
                        System.out.println("\nYou successfully ran away.");
                        delay();
                        baddie.takeDmg(baddie.getHealth());
                        ranAway = true;
                        
                        break;
                        
                    case VISIT_STORE:
                        clear();
                        Shop.printStore(player);
                        
                        break;
                        
                    case EXIT:
                        clear();
                        System.out.println("\fExiting Game...");
                        System.out.print("Would you like to save your progress?");
                        
                        if(Confirmation.isConfirmation(SCANNER.nextLine()))
                            GameState.saveState(player);
                        
                        running = false;
                        
                        return;
                        
                    case UNDEFINED:
                        System.out.println("Please choose one of the Menu Items");
                        delay();
                        
                        break;
                }
                
                if(player.health() <= 0) {
                
                    System.out.println("\nHmm..You died. Do Better?");
                    System.out.print("Would you like to respawn? ");
                    String continueGame = SCANNER.nextLine();
                    
                    if(Confirmation.isConfirmation(continueGame)) {
                        
                        running = true;
                        player.reset();
                    }
                    else {
                        
                        System.out.println("\nProgram Terminated.");
                        baddie.takeDmg(baddie.getHealth());
                        running = false;
                        
                        return;
                    }
                }
            }
            
            if(!ranAway) {
    
                player.incrementEnemiesDefeated();
                pouch.addCoins(RANDOM.nextInt(MAX_COINS_DROPPED));
                
                if(RANDOM.nextInt(100) < swordDropRate) {
                    
                    if(player.hasSword()) {
                        
                        System.out.println("\nThe " + baddie.getName() +
                                " dropped a sword, but you already have one.");
                    }
                    else {
    
                        player.addSword("metal");
                        System.out.println("\nThe " + baddie.getName() +
                                " dropped a " + player.getSword().getName() +
                                ".\nYour attack damage has now increased by " +
                                player.getSword().getDmgIncrease() + ".");
                    }
                    
                    delay();
                }
                else if(RANDOM.nextInt(100) < armorDropRate) {
                    
                    if(player.hasArmor()) {
                        
                        System.out.println("\nThe " + baddie.getName() +
                                " dropped some armor, but you already have some.");
                    }
                    else {
    
                        player.addArmor("iron");
                        System.out.println("\nThe " + baddie.getName() +
                                " dropped " + player.getArmor().name() +
                                ".\nYour damage taken has now decreased by " +
                                player.getArmor().dmgMitigated() + ".");
                    }
                    
                    delay();
                }
                else if(RANDOM.nextInt(100) < healthPotionDropRate) {
    
                    player.addPotion(1);
                    System.out.println("\nThe " + baddie.getName() + " dropped a health potion.");
                    delay();
                }
            }
        }
    }
    
    public static void printStatistics(Player player, Enemy baddie) {
        
        clear();
        System.out.println("\f# A " + baddie.getName() + " appeared #");
        System.out.println("\n# You have " + player.health() + " HP #");
        System.out.println("# Enemy has " + baddie.getHealth() + " HP #");
        System.out.println("# Potions Left: " + player.getPotions() + " #");
        System.out.println("# Pouch has: " + player.getPouch().getCoins() + " Coins #");
        System.out.println("# Enemies Killed: " + player.enemiesDefeated() + " #");
        
        if(player.hasSword()) {
            
            System.out.println("\n# Sword Type: " + player.getSword().getName() +
                    " | Durability: " + player.getSword().getDurabilityPoints() + " #");
        }
        
        if(player.hasArmor()) {
            
            System.out.println("\n# Armor Type: " + player.getArmor().name() +
                    " | Durability: " + player.getArmor().durabilityPoints() + " #");
        }
    }
    
    public static void startBattle() {
    
        System.out.println("\n1. Attack");
        System.out.println("2. Use Potion");
        System.out.println("3. Run!");
        System.out.println("4. Visit Store");
        System.out.println("5. Exit Game");
        System.out.print("\nChoice? ");
    }
    
    public static void delay() {
        
        try {
            
            Thread.sleep(DELAY);
        }
        catch(InterruptedException e) {
            
            clear();
            
            System.out.println("\fThe game experienced an Interrupted Exception.");
            System.out.println("The game state was not saved");
            System.out.println("Please restart the game.");
            
            System.exit(0);
        }
    }
    
    public static void clear() {
    
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
