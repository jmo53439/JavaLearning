package com.jmlearning.simplegames.simplepoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {

    private Player p;
    private Deck cards;
    private double chips;
    private double bet;
    private boolean isHandValid;

    public Game(String[] checkValidHand) {

        p = new Player();
        cards = new Deck();
        isHandValid = true;

        for(int i = 0; i < 5; i++) {

            String suit = checkValidHand[i].substring(0, 1);
            int suitValue;

            String cardNumber = checkValidHand[i].substring(1);

            if(suit.equals("c")) {

                suitValue = 0;
            }
            else if(suit.equals("d")) {

                suitValue = 1;
            }
            else if(suit.equals("h")) {

                suitValue = 2;
            }
            else {

                suitValue = 3;
            }

            int cardValue = Integer.parseInt(cardNumber);

            Card card = new Card(suitValue, cardValue);
            p.addCard(card);
        }
    }

    public Game() {

        p = new Player();
        cards = new Deck();
        isHandValid = false;
    }

    public void play() {

        if(isHandValid == false) {

            Scanner scanner = new Scanner(System.in);
            cards.shuffle();

            System.out.println("Play? " + "(YES-1 NO-2)");

            String playInput = scanner.next();
            int playResponse = Integer.parseInt(playInput);

            while(playResponse == 1) {

                System.out.println("You have " + p.getChips() + " chips. Bet? (YES-1 NO-2)");

                String betInput = scanner.next();
                int betResponse = Integer.parseInt(betInput);

                double chipAmountResponse = 0.0;

                if(betResponse == 1) {

                    System.out.println("How many chips? " + " Valid Amounts: 1 - 5");

                    String chipInput = scanner.next();
                    chipAmountResponse = Double.parseDouble(chipInput);
                    p.bets(chipAmountResponse);
                }

                for(int i = 0; i < 5; i++) {

                    p.addCard(cards.deal());
                }

                ArrayList<Card> hand = p.getHand();
                Collections.sort(hand);
                System.out.println("");
                System.out.println("Initial Hand: " + hand);
                System.out.println("");

                redraw();
                Collections.sort(hand);
                System.out.println("");
                System.out.println("New Hand: " + hand);
                System.out.println("");

                double payout = verifyHand(hand);

                if(payout != 0)
                    p.winnings(betResponse);

                System.out.println("Your total chips is now: " + p.getChips() + " Chips");
                System.out.println("");
                System.out.println("Would you like to play again? " + "(YES-1 NO-2)");

                String playAgainResponse = scanner.next();
                playResponse = Integer.parseInt(playAgainResponse);

                if(playResponse == 1)
                    p.newHand();
            }
        }
        else {

            verifyHand(p.getHand());
        }
    }

    private double verifyHand(ArrayList<Card> hand) {

        double handPayout = 0.0;
        Collections.sort(hand);
        verifyHighCard(hand);
        String verifiedHand = "";

        if(verifyRoyalFlush(hand) == true) {

            verifiedHand = "Royal Flush (^_^)";
            handPayout = 250;
        }
        else if(verifyStraightFlush(hand) == true) {

            verifiedHand = "Straight Flush (^_^)";
            handPayout = 50;
        }
        else if(verifyFourOfAKind(hand) == true) {

            verifiedHand = "Four of a Kind (^_^)";
            handPayout = 25;
        }
        else if(verifyFullHouse(hand) == true) {

            verifiedHand = "Full House (^_^)";
            handPayout = 6;
        }
        else if(verifyFlush(hand) == true) {

            verifiedHand = "Flush (^_^)";
            handPayout = 5;
        }
        else if(verifyStraight(hand) == true) {

            verifiedHand = "Straight (^_^)";
            handPayout = 4;
        }
        else if(verifyThreeOfAKind(hand) == true) {

            verifiedHand = "Three of a Kind (^_^)";
            handPayout = 3;
        }
        else if(verifyTwoPair(hand) == true) {

            verifiedHand = "Pair of Twos (^_^)";
            handPayout = 2;
        }
        else if(verifyPair(hand) == true) {

            verifiedHand = "Pair (^_^)";
            handPayout = 1;
        }
        else {

            verifiedHand = verifyHighCard(hand) + " is your High Card";
            handPayout = 0;
        }

        System.out.println("You have a " + verifiedHand + ". Payout Amount: " + handPayout);
        return handPayout;
    }

    private boolean verifyPair(ArrayList<Card> hand) {

        boolean pair = false;

        // different ways of putting the pair in order of cards
        if((hand.get(0).getRank() == hand.get(1).getRank()) // xx abc
                || (hand.get(1).getRank() == hand.get(2).getRank()) // ab xx c
                || (hand.get(2).getRank() == hand.get(3).getRank()) // a xx bc
                || (hand.get(3).getRank() == hand.get(4).getRank())) { // abc xx

            pair = true;
        }

        return pair;
    }

    private boolean verifyTwoPair(ArrayList<Card> hand) {
        
        boolean twoPair = false;

        if(((hand.get(1).getRank() == hand.get(2).getRank()) // extra card, xx yy
                && (hand.get(3).getRank() == hand.get(4).getRank()))
                || ((hand.get(0).getRank() == hand.get(1).getRank()) // xx, extra card, yy
                && (hand.get(3).getRank() == hand.get(4).getRank()))
                || ((hand.get(0).getRank() == hand.get(1).getRank()) // xx, yy, extra card
                && (hand.get(2).getRank() == hand.get(3).getRank()))) {
            
            twoPair = true;
        }

        return twoPair;
    }

    private boolean verifyThreeOfAKind(ArrayList<Card> hand) {

        boolean threeOfAKind = false;

        if(((hand.get(0).getRank() == hand.get(1).getRank()) // three of a kind, x, y
                && (hand.get(1).getRank() == hand.get(2).getRank()))
                || ((hand.get(1).getRank() == hand.get(2).getRank()) // x, three of a kind, y
                && (hand.get(2).getRank() == hand.get(3).getRank())) 
                || ((hand.get(2).getRank() == hand.get(3).getRank()) // x, y, three of a kind
                && (hand.get(3).getRank() == hand.get(4).getRank()))) {
            
            threeOfAKind = true;
        }

        return threeOfAKind;
    }

    private boolean verifyStraight(ArrayList<Card> hand) {

        boolean straight = false;

        if((hand.get(0).getRank() == (hand.get(1).getRank() - 1)) // straight w/o ace
                && (hand.get(1).getRank() == (hand.get(2).getRank() - 1))
                && (hand.get(2).getRank() == (hand.get(3).getRank() - 1))
                && (hand.get(3).getRank() == (hand.get(4).getRank() - 1))) {

            straight = true;
        }

        if((hand.get(1).getRank() == (hand.get(2).getRank() - 1)) // straight with ace
                && (hand.get(2).getRank() == (hand.get(3).getRank() - 1))
                && (hand.get(3).getRank() == (hand.get(4).getRank() - 1))
                && (hand.get(4).getRank() == (hand.get(0).getRank() - 1))) {
            straight = true;
        }

        return straight;
    }

    private boolean verifyFlush(ArrayList<Card> hand) {

        boolean flush = false;

        if((hand.get(0).getSuit() == hand.get(1).getSuit()) // same suits
                && (hand.get(1).getSuit() == hand.get(2).getSuit())
                && (hand.get(2).getSuit() == hand.get(3).getSuit())
                && (hand.get(3).getSuit() == hand.get(4).getSuit())) {

            flush = true;
        }

        return flush;
    }

    private boolean verifyFullHouse(ArrayList<Card> hand) {

        boolean fullHouse = false;

        if(((hand.get(0).getRank() == hand.get(1).getRank()) // three of a kind and a pair
                && (hand.get(1).getRank() == hand.get(2).getRank())
                && (hand.get(3).getRank() == hand.get(4).getRank()))
                
                || ((hand.get(0).getRank() == hand.get(1).getRank()) // pair and three of a king
                && (hand.get(2).getRank() == hand.get(3).getRank())
                && (hand.get(3).getRank() == hand.get(4).getRank()))) {
            
            fullHouse = true;
        }
        
        return fullHouse;
    }

    private boolean verifyFourOfAKind(ArrayList<Card> hand) {
        
        boolean fourOfAKind = false;
        
        if(((hand.get(0).getRank() == hand.get(1).getRank()) // four of the same number and another card
                && (hand.get(1).getRank() == hand.get(2).getRank())
                && (hand.get(2).getRank() == hand.get(3).getRank()))
                
                || ((hand.get(1).getRank() == hand.get(2).getRank()) // another card and four of the same number
                && (hand.get(2).getRank() == hand.get(3).getRank())
                && (hand.get(3).getRank() == hand.get(4).getRank()))) {
           
            fourOfAKind = true;
        }

        return fourOfAKind;
    }

    private boolean verifyStraightFlush(ArrayList<Card> hand) {

        boolean straightFlush = false;

        if ((hand.get(0).getSuit() == hand.get(1).getSuit()) // same suit
                && (hand.get(1).getSuit() == hand.get(2).getSuit()) 
                && (hand.get(2).getSuit() == hand.get(3).getSuit())
                && (hand.get(3).getSuit() == hand.get(4).getSuit())
                
                && (hand.get(0).getRank() == (hand.get(1).getRank() - 1)) // consecutive numbers
                && (hand.get(1).getRank() == (hand.get(2).getRank() - 1))
                && (hand.get(2).getRank() == (hand.get(3).getRank() - 1))
                && (hand.get(3).getRank() == (hand.get(4).getRank() - 1))) { 
            
            straightFlush = true;
        }
        
        return straightFlush;
    }

    private boolean verifyRoyalFlush(ArrayList<Card> hand) {

        boolean royalFlush = false;

        if((hand.get(0).getSuit() == hand.get(1).getSuit()) // same suit
                && (hand.get(1).getSuit() == hand.get(2).getSuit())
                && (hand.get(2).getSuit() == hand.get(3).getSuit())
                && (hand.get(3).getSuit() == hand.get(4).getSuit())
                
                && (hand.get(0).getRank() == 0) // 10 through ace
                && (hand.get(1).getRank() == 9)
                && (hand.get(2).getRank() == 10)
                && (hand.get(3).getRank() == 11)
                && (hand.get(4).getRank() == 12)) {

            royalFlush = true;
        }

        return royalFlush;
    }

    private String verifyHighCard(ArrayList<Card> hand) {

        int highCardValue = 0;
        int highCardSuit = 0;
        int highestPOS = 0;

        String highCardValueString;
        String highCardSuitString;
        String highCard;

        for(int i = 0; i < 5; i++) {

            if(hand.get(i).getRank() > highCardValue) {

                highestPOS = i;
                highCardValue = hand.get(i).getRank();
                highCardSuit = hand.get(i).getSuit();
            }
        }

        if(highCardValue == 0) {

            highCardValueString = "Ace";
        }
        else if(highCardValue == 1) {

            highCardValueString = "2";
        }
        else if(highCardValue == 2) {

            highCardValueString = "3";
        }
        else if(highCardValue == 3) {

            highCardValueString = "4";
        }
        else if(highCardValue == 4) {

            highCardValueString = "5";
        }
        else if(highCardValue == 5) {

            highCardValueString = "6";
        }
        else if(highCardValue == 6) {

            highCardValueString = "7";
        }
        else if(highCardValue == 7) {

            highCardValueString = "8";
        }
        else if(highCardValue == 8) {

            highCardValueString = "9";
        }
        else if(highCardValue == 9) {

            highCardValueString = "10";
        }
        else if(highCardValue == 10) {

            highCardValueString = "Jack";
        }
        else if(highCardValue == 11) {

            highCardValueString = "Queen";
        }
        else {

            highCardValueString = "King";
        }

        if(highCardSuit == 0) {

            highCardSuitString = "Clubs";
        }
        else if(highCardSuit == 1) {

            highCardSuitString = "Diamonds";
        }
        else if(highCardSuit == 2) {

            highCardSuitString = "Hearts";
        }
        else {

            highCardSuitString = "Spades";
        }

        highCard = highCardValueString + " of " + highCardSuitString;
        return highCard;
    }

    private void redraw() {

        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < 5; i++) {

            int cardNumber = i + 1;

            System.out.println("Do you want to replace the Card in: POSITION_" + cardNumber + "? " + "(YES-1 NO-2)");

            String redrawInput = scanner.next();
            int redrawResponse = Integer.parseInt(redrawInput);

            if(redrawResponse == 1) {

                p.redraw(i, cards.deal());
            }
        }
    }
}
