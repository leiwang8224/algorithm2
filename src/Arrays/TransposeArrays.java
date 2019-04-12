package Arrays;

public class TransposeArrays {
    public static void main(String[] args) {

    }

    private void transposeArray(int[][] array) {
        int n = array.length -1;
        int temp = 0;
        for (int row = 0; row <= n; row++) {
            for (int col = row+1; col <= n; col++) {
                temp= array[row][col];
                array[row][col] = array[col][row];
                array[col][row] = temp;
            }
        }
    }
}
