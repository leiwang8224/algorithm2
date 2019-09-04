package CTCI.OOP;

import java.util.ArrayList;

/**
 * Design the data structures for a generic deck of cards. Explain how you
 * would subclass the data structures to implement blackjack.
 */
public class DeckOfCards {
    public static void main(String[] args) {
        int numHands = 5;
        BlackJackGameAutomator automator = new BlackJackGameAutomator(numHands);
        automator.initializeDeck();

        boolean success = automator.dealInitial();

        if (!success) {
            System.out.println("Error, out of bounds");
        } else {
            System.out.println("Initial");
            automator.printHandsAndScore();
            ArrayList<Integer> blackjacks = automator.getBlackJacks();
            if (blackjacks.size() > 0) {
                System.out.print("Blackjack at ");
                for (int index : blackjacks) {
                    System.out.print(index + ", ");
                }
                System.out.println();
            } else {
                success = automator.playAllHands();
                if (!success) {
                    System.out.println("Error. Out of cards.");
                } else {
                    System.out.println("\n Completed Game");
                    automator.printHandsAndScore();
                    ArrayList<Integer> winners = automator.getWinners();
                    if (winners.size() > 0) {
                        System.out.print("Winners: ");
                        for (int index : winners) {
                            System.out.print(index + ", ");
                        }
                        System.out.println();
                    } else {
                        System.out.println("Draw");
                    }
                }
            }
        }

    }

    /**
     * Each card has a suit
     */
    enum Suit {
        Club (0), Diamond(1), Heart(2), Spade(3);
        private int value;
        private Suit (int v) {
            value = v;
        }

        int getValue() {
            return value;
        }

        static Suit getSuiteFromValue(int value){
            switch(value) {
                case 1: return Diamond;
                case 2: return Heart;
                case 3: return Spade;
                case 0: return Club;
                default: return null;
            }
        }
    }

    /**
     * A deck has a certain number of cards
     */
    abstract static class Card {
        private boolean available = true;

        /**
         * number or face that's on the card - a number 2 through 10, or 11
         * for Jac, 12 for Queen, 13 for King, or 1 for Ace
         */
        protected int faceValue;
        Suit suit;

        Card(int card, Suit suit) {
            faceValue = card;
            this.suit = suit;
        }

        abstract int value();
        Suit getSuit() {
            return suit;
        }

        // checks if the card is availabel
        boolean isAvailable() {
            return available;
        }

        void markUnavailable() {
            available = false;
        }

        void markAvailable() {
            available = true;
        }

        void print() {
            String[] faceValues = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
            System.out.print(faceValues[faceValue - 1]);
            switch(suit) {
                case Club:
                    System.out.print("c");
                    break;
                case Heart:
                    System.out.print("h");
                    break;
                case Diamond:
                    System.out.print("d");
                    break;
                case Spade:
                    System.out.print("s");
                    break;
            }
            System.out.print(" ");
        }
    }

    /**
     * Cards forms a deck
     * - deck could be different kinds hence the use of generics
     * @param <T>
     */
    static class Deck < T extends Card> {
        private ArrayList<T> cards; // all cards, dealt or not
        private int dealtIndex = 0; // marks first undealt card

        void setDeckOfCards(ArrayList<T> deckOfCards) {
            this.cards = deckOfCards;
        }

        void shuffle() {
            for (int index = 0; index < cards.size(); index++) {
                int randomIndex = Math.abs((int)(Math.random() * cards.size() - index - 1));
                System.out.println("random index = " + randomIndex);
                T card1 = cards.get(index);
                T card2 = cards.get(randomIndex);
                cards.set(index, card2);
                cards.set(randomIndex, card1);
            }
        }

        int remainingCards() {
            return cards.size() - dealtIndex;
        }

        T[] dealHand(int number) {
            if (remainingCards() < number) {
                return null;
            }

            T[] hand = (T[]) new Card[number];
            int count = 0;
            while (count < number) {
                T card = dealCard();
                if (card != null) {
                    hand[count] = card;
                    count++;
                }
            }
            return hand;
        }

        T dealCard() {
            if (remainingCards() == 0) return null;

            T card = cards.get(dealtIndex);
            card.markUnavailable();
            dealtIndex++;
            return card;
        }

        void print() {
            for (Card card : cards) {
//                card.print()
            }
        }
    }

    /**
     * A hand in a deck of cards
     * @param <T>
     */
    static class Hand <T extends Card> {
        ArrayList<T> cards = new ArrayList<>();

        int score() {
            int score = 0;
            for (T card : cards) {
                score = score + card.value();
            }
            return score;
        }

        void addCard(T card) {
            cards.add(card);
        }

        void print() {
            for (Card card : cards)
                card.print();
        }
    }

    /**
     * Converts the face cards to the equivalent in black jack terms
     * jack = 10
     * queen = 10
     * king = 10
     */
    static class BlackJackCard extends Card {
        BlackJackCard(int c, Suit suit) {
            super (c, suit);
        }

        int value() {
            if (isAce()) {
                return 1;
            } else if (isFaceCard()) {
                return 10;
            } else {
                return faceValue;
            }
        }

        int minValue() {
            if (isAce()) {
                return 11;
            } else {
                return value();
            }
        }

        int maxValue() {
            if (isAce()) return 11;
            else return value();
        }

        boolean isAce() {
            return faceValue == 1;
        }

        boolean isFaceCard() {
            return faceValue >= 11 && faceValue <= 13;
        }
    }

    static class BlackJackHand extends Hand<BlackJackCard> {
        int score() {
            ArrayList<Integer> scores = possibleScores();
            int maxUnder = Integer.MIN_VALUE;
            int minOver = Integer.MAX_VALUE;
            for (int score : scores) {
                if (score > 21 && score < minOver) minOver = score;
                else if (score <= 21 && score > maxUnder) maxUnder = score;
            }
            return maxUnder == Integer.MIN_VALUE ? minOver : maxUnder;
        }

        private ArrayList<Integer> possibleScores() {
                ArrayList<Integer> scores = new ArrayList<>();

                if (cards.size() == 0) return scores;
                for (BlackJackCard card : cards) addCardToScoreList(card, scores);
                return scores;
        }

        private void addCardToScoreList(BlackJackCard card, ArrayList<Integer> scores) {
            if (scores.size() == 0) scores.add(0);
            int length = scores.size();

            for (int index = 0; index < length; index++) {
                int score = scores.get(index);
                scores.set(index, score + card.minValue());
                if (card.minValue() != card.maxValue()) scores.add(score + card.maxValue());
            }


        }

        boolean busted() {
            return score() > 21;
        }

        boolean is21() {
            return score() == 21;
        }

        boolean isBlackJack() {
            if (cards.size() != 2) return false;
            BlackJackCard first = cards.get(0);
            BlackJackCard second = cards.get(1);
            return (first.isAce() && second.isFaceCard()) || (second.isAce() && first.isFaceCard());
        }
    }

    static class BlackJackGameAutomator {
        private Deck<BlackJackCard> deck;
        private BlackJackHand[] hands;
        private static final int HIT_UNTIL = 16;

        BlackJackGameAutomator(int numPlayers) {
            hands = new BlackJackHand[numPlayers];
            for (int index = 0; index < numPlayers; index++)
                hands[index] = new BlackJackHand();
        }

        boolean dealInitial() {
            for (BlackJackHand hand : hands) {
                BlackJackCard card1 = deck.dealCard();
                BlackJackCard card2 = deck.dealCard();

                if (card1 == null || card2 == null) return false;

                hand.addCard(card1);
                hand.addCard(card2);
            }
            return true;
        }

        ArrayList<Integer> getBlackJacks() {
            ArrayList<Integer> winners = new ArrayList<>();
            for (int index = 0; index < hands.length; index++) {
                if (hands[index].isBlackJack()) winners.add(index);
            }

            return winners;
        }

        boolean playHand(int index) {
            BlackJackHand hand = hands[index];
            return playHand(hand);
        }

        boolean playHand(BlackJackHand hand) {
            while (hand.score() < HIT_UNTIL) {
                BlackJackCard card = deck.dealCard();
                if (card == null) return false;
                hand.addCard(card);
            }
            return true;
        }

        boolean playAllHands() {
            for (BlackJackHand hand : hands) {
                if (!playHand(hand)) return false;
            }
            return true;
        }

        ArrayList<Integer> getWinners() {
            ArrayList<Integer> winners = new ArrayList<>();
            int winningScore = 0;
            for (int index = 0; index < hands.length; index++) {
                BlackJackHand hand = hands[index];
                if (!hand.busted()) {
                    if (hand.score() > winningScore) {
                        winningScore = hand.score();
                        winners.clear();
                        winners.add(index);
                    } else if (hand.score() == winningScore)
                        winners.add(index);
                }
            }
            return winners;
        }

        void initializeDeck() {
            ArrayList<BlackJackCard> cards = new ArrayList<>();
            for (int index = 1; index <= 13; index++) {
                for (int suitInNumber = 0; suitInNumber <= 3; suitInNumber++) {
                    Suit suit = Suit.getSuiteFromValue(suitInNumber);
                    BlackJackCard card = new BlackJackCard(index, suit);
                    cards.add(card);
                }
            }

            deck = new Deck<BlackJackCard>();
            deck.setDeckOfCards(cards);
            deck.shuffle();
        }

        void printHandsAndScore() {
            for (int index = 0; index < hands.length; index++) {
                System.out.print("Hand " + index + "(" + hands[index].score() + "): ");
                hands[index].print();
                System.out.println();
            }

        }

        
    }


}
