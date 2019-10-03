package CTCI.RecursionAndDP;

import java.util.Stack;

/**
 * In the classic problem of the Towers of Hanoi, you have 3 towers and N disks
 * of different sizes which can slide onto any tower. The puzzle starts with disks
 * sorted in ascending order of size from top to bottom (i.e., each disk sits on top
 * of an even larger one). You have the following constraints:
 * 1. Only one disk can be moved at a time
 * 2. A disk is slid off the top of one tower onto another tower
 * 3. A disk cannot be placed on top of a smaller disk.
 * Write a program to move the disks from the first tower to the last using stacks.
 */
public class TowerOfHanoi {
    public static void main(String[] args) {
        Tower source = new Tower();
        Tower dest = new Tower();
        Tower buffer = new Tower();

        source.name = "s";
        dest.name = "d";
        buffer.name = "b";

        // load up tower
        int numberOfDisks = 5;
        for (int disk = numberOfDisks - 1; disk >= 0; disk--)
            source.add(disk);

        source.print();
        source.moveDisks(numberOfDisks, dest, buffer);
        dest.print();


    }

    /**
     * Case n = 1: move disk 1 from tower 1 to tower 3
     * 1. We simply move disk 1 from tower 1 to tower 3
     * Case n = 2: move disk 1 and disk 2 from tower 1 (src) to tower 3 (dest)
     * 1. Move disk 1 from tower 1 (src) to tower 2 (buffer)
     * 2. Move disk 2 from tower 1 (src) to tower 3 (dest)
     * 3. Move disk 1 from tower 2 (buffer) to tower 3 (dest)
     * tower 2 acts as a buffer
     * Case n = 3: move disk 1, 2 and 3 from tower 1 to tower 3
     * 1. We know we can move the top 2 so let's assume we have already done that,
     * but instead move them to tower 2 instead of tower 3
     * 2. Move disk 3 (src) to tower 3 (dest)
     * 3. Move disk 1 and disk 2 (buffer) to tower 3 (dest). Just call Case n = 2.
     * Case n = 4: move disk 1, 2, 3 and 4 from tower 1 to tower 3
     * 1. Move disks 1, 2, and 3 (src) to tower 2 (buffer)
     * 2. Move disk 4 (src) to tower 3 (dest)
     * 3. Move disks 1, 2 and 3 back to tower 3. Just call case n = 3
     * Remember that the labels of tower 2 and tower 3 are not important.
     * They are equivalent towers. So moving disks to tower 3 with tower 2 serving
     * as a buffer is equivalent to moving disks to tower 2 with tower 3 as buffer.
     */
    static class Tower{
        private Stack<Integer> disks = new Stack<>();
        String name;

        void add(int d) {
            if (!disks.isEmpty() && disks.peek() <= d) {
                System.out.println("Error placing disk " + d);
            } else {
                disks.push(d);
            }
        }

        void moveTopTo(Tower t) {
            int top = disks.pop();
            t.add(top);
        }

        void print() {
            System.out.println("Contents of Tower " + name + ": " + disks.toString());
        }

        void moveDisks(int quantity, Tower dest, Tower buffer) {
            if (quantity <= 0) return;

            // recurse on quantity less 1 and exchange buffer with dest (move to buffer using dest as buffer)
            // ex: move disk 1 and 2 from src to buffer using dest as buffer
            this.moveDisks(quantity - 1, buffer, dest);
            System.out.println("Move " + disks.peek() + " from "+ this.name + " to " + dest.name);
            // move top disk to dest
            // ex: move disk 3 to dest
            moveTopTo(dest);
            // move disks from buffer to dest, using this as buffer
            // ex: move disk 1 and 2 from buffer to dest using src as buffer
            buffer.moveDisks(quantity - 1, dest, this);
        }
    }

    
}
