package com.jmlearning.simplegames.simplepoker;

import java.util.ArrayList;

public class Player {

    private ArrayList<Card> hand;
    private double chips;
    private double bet;

    public Player() {

        hand = new ArrayList <>();
        chips = 5.0;
        bet = 0.0;
    }

    public void addCard(Card c) {

        hand.add(c);
    }

    public void removeCard(Card c) {

        hand.remove(c);
    }

    public void newHand() {

        hand = new ArrayList <>();
    }

    public void bets(double amount) {

        bet = amount;
    }

    public void winnings(double odds) {

        chips = chips + odds * bet;
    }

    public double getChips() {

        return chips;
    }

    public void redraw(int position, Card c) {

        hand.set(position, c);
    }

    public ArrayList<Card> getHand() {

        return hand;
    }
}
