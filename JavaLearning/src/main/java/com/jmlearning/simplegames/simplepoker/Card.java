package com.jmlearning.simplegames.simplepoker;

public class Card implements Comparable<Card> {

    private int suit;
    private int cardValue;

    public Card(int s, int c) {

        suit = s;
        cardValue = c;
    }

    @Override
    public int compareTo(Card c) {

        int answer = 0;

        if(this.cardValue < c.cardValue) {

            answer = -1;
        }
        else if(this.cardValue > c.cardValue) {

            answer = 1;
        }
        else {

            if(this.suit < c.suit) {

                answer = -1;
            }
            else if(this.suit > c.suit) {

                answer = 1;
            }
        }
        return answer;
    }

    public String toString() {

        String cardSuit;
        String cardValue;

        if(this.suit == 0) {

            cardSuit = "Clubs";
        }
        else if(this.suit == 1) {

            cardSuit = "Diamonds";
        }
        else if(this.suit == 2) {

            cardSuit = "Hearts";
        }
        else {

            cardSuit = "Spades";
        }

        if(this.cardValue == 0) {

            cardValue = "Ace";
        }
        else if(this.cardValue == 1) {

            cardValue = "2";
        }
        else if(this.cardValue == 2) {

            cardValue = "3";
        }
        else if(this.cardValue == 3) {

            cardValue = "4";
        }
        else if(this.cardValue == 4) {

            cardValue = "5";
        }
        else if(this.cardValue == 5) {

            cardValue = "6";
        }
        else if(this.cardValue == 6) {

            cardValue = "7";
        }
        else if(this.cardValue == 7) {

            cardValue = "8";
        }
        else if(this.cardValue == 8) {

            cardValue = "9";
        }
        else if(this.cardValue == 9) {

            cardValue = "10";
        }
        else if(this.cardValue == 10) {

            cardValue = "Jack";
        }
        else if(this.cardValue == 11) {

            cardValue = "Queen";
        }
        else {

            cardValue = "King";
        }

        String description;
        description = cardValue + " of " + cardSuit;

        return description;
    }

    public int getSuit() {

        return this.suit;
    }

    public int getRank() {

        return this.cardValue;
    }
}
