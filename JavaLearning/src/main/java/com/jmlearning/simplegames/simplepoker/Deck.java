package com.jmlearning.simplegames.simplepoker;

import java.util.Arrays;
import java.util.Collections;

public class Deck {

    private Card[] cards;
    private int topCard;

    public Deck() {

        int deckPosition = 0;
        cards = new Card[52];

        // suits
        for(int i = 0; i < 4; i++) {

            // ace through king
            for(int j = 0; j < 13; j++) {

                cards[deckPosition] = new Card(i, j);
                deckPosition++;
            }
        }

        topCard = 0;
    }

    public void shuffle() {

        for(int i = 0; i < 52; i++) {

            Collections.shuffle(Arrays.asList(cards));
        }
    }

    public Card deal() {

        if(topCard == 51) {

            shuffle();
            topCard = 0;
        }

        return cards[topCard++];
    }
}
