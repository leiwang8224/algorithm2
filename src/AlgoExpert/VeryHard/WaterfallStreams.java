package AlgoExpert.VeryHard;

public class WaterfallStreams {
    // O(w^2 * h) time | O(w) space - where w and h are the width and height of input array
    public double[] waterFallStreams(double[][] array, int source) {
        double[] rowAbove = array[0];
        // we will use -1 to represent water, since 1 is used for block.
        rowAbove[source] = -1;

        for (int row = 1; row < array.length; row++) {
            double[] curRow = array[row];  // make a copy of the row to process(do not change original array)
            for (int col = 0; col < rowAbove.length; col++) {
                // note valueAbove is converted to double (could be decimal)
                double valueAbove = rowAbove[col];

                boolean hasWaterAbove = valueAbove < 0;
                boolean hasBlock = curRow[col] == 1.0;

                // if no water from above cell, skip to next iteration
                if (!hasWaterAbove) continue;

                if (!hasBlock) {
                    // if there is no block in the current col, move the water down
                    curRow[col] += valueAbove;
                    continue;
                }

                // at this point we know there is water from above and needs to go down
                double splitWater = valueAbove / 2;

                //move water right
                int colToTheRight = col;
                while (colToTheRight + 1 < rowAbove.length) {
                    colToTheRight ++;
                    if (rowAbove[colToTheRight] == 1.0) // if there is a block in the way, water do not come down
                        break;
                    if (curRow[colToTheRight] != 1) { // if there is no block below us, water goes down
                        curRow[colToTheRight] += splitWater;
                        break;
                    }
                }

                // move water left
                int colToTheLeft = col;
                while (colToTheLeft -1 >= 0) {
                    colToTheLeft --;
                    if (rowAbove[colToTheLeft] == 1.0) { // if there is a block
                        break;
                    }

                    if (curRow[colToTheLeft] != 1.0) { // if there is no block below
                        curRow[colToTheLeft] += splitWater;
                        break;
                    }
                }
            }
            rowAbove = curRow;  // update rowAbove by copying the curRow
        }
        double[] finalPercentage = new double[rowAbove.length];
        for (int col = 0; col < rowAbove.length; col++) {
            double num = rowAbove[col];
            if (num == 0) finalPercentage[col] = num;
            else finalPercentage[col] = (num * -100); // *-100 to get positive values
        }
        return finalPercentage;
    }
}
