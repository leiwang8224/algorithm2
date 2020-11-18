package Queue;

import java.util.*;

//LC-950
//In a deck of cards, every card has a unique integer.  You can order the deck in any order you want.
//
//        Initially, all the cards start face down (unrevealed) in one deck.
//
//        Now, you do the following steps repeatedly, until all cards are revealed:
//
//        Take the top card of the deck, reveal it, and take it out of the deck.
//        If there are still cards in the deck, put the next top card of the deck at the bottom of the deck.
//        If there are still unrevealed cards, go back to step 1.  Otherwise, stop.
//
//        Return an ordering of the deck that would reveal the cards in increasing order.
//
//        The first entry in the answer is considered to be the top of the deck.
//
//
//
//        Example 1:
//
//        Input: [17,13,11,2,3,5,7]
//        Output: [2,13,3,11,5,17,7]
//        Explanation:
//        We get the deck in the order [17,13,11,2,3,5,7] (this order doesn't matter), and reorder it.
//        After reordering, the deck starts as [2,13,3,11,5,17,7], where 2 is the top of the deck.
//        We reveal 2, and move 13 to the bottom.  The deck is now [3,11,5,17,7,13].
//        We reveal 3, and move 11 to the bottom.  The deck is now [5,17,7,13,11].
//        We reveal 5, and move 17 to the bottom.  The deck is now [7,13,11,17].
//        We reveal 7, and move 13 to the bottom.  The deck is now [11,17,13].
//        We reveal 11, and move 17 to the bottom.  The deck is now [13,17].
//        We reveal 13, and move 17 to the bottom.  The deck is now [17].
//        We reveal 17.
//        Since all the cards revealed are in increasing order, the answer is correct.

public class RevealCardsIncOrder {
    public static void main(String[] args) {
        int[] deck = new int[]{2,43,2,4,123,4,3,2,4};
        int[] soln = new int[deck.length];
        soln = deckRevealedInc(deck);
        for (int sol : soln) {
            System.out.println(sol);
        }

    }

    // create linkedlist of index from 0 to size-1
    // sort the deck from smallest to largest
    // iterate through the deck and put 0, 2, 4 ... cards in the result deck, rotate the index (indices linked list)
    // in beginning of the queue to the end of the queue
    public static int[] deckRevealedInc(int[] deck) {
        int N = deck.length;
        Queue<Integer> index = new LinkedList<>();

        // create an array of indices starting from 0 to deck.length-1
        for (int i = 0; i < N; ++i) {
            index.add(i);
        }

        int[] ans = new int[N];
        // sort the deck from smallest to largest
        Arrays.sort(deck);
        System.out.println("starting deck " + Arrays.toString(deck));
        for (int card:deck) {
            // move the smallest to the front
            System.out.println("assigning ans index " + index.peek() + " with " + card);
            ans[index.poll()] = card;
            System.out.println("deck looks like " + Arrays.toString(ans) + " index array = " + Arrays.toString(index.toArray()));
            // place next smallest in alternative indices (0, 2, 4, 6, 8 etc.)
            if (!index.isEmpty()) {
                // move the first in queue to last in queue
                System.out.println("index adding " + index.peek() + " to the end of list");
                index.add(index.poll());
            }
        }
        return ans;
    }

}
