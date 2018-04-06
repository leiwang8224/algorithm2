package Math;

public class IntToRomanNumeral {
    public static void main(String args[]) {

        printRoman(3549);
    }

    private static void printRoman(int number) {
        char[] c = new char[10001];
        int i = 0;

        // if number entered is not valid
        if (number <= 0){
            System.out.println("Invalid number");
            return;
        }

        while (number != 0) {
            // if base value of number is greater than 1000
            if (number >= 1000) {
                // Add 'M' number/1000 times after index i
                i = digit('M', number/1000, i, c);
                number = number%1000;
            }

            // if base value of number is greater than
            // or equal to 500
            else if (number >= 500) {
                // to add base symbol to the char array
                if (number < 900) {
                    // add 'D' number/1000 times after index i
                    i = digit('D', number / 500, i, c);
                    number = number % 500;
                }

                // to handle subtractive notation in case of number
                // having digit as 9 and adding corresponding base symbol
                else {
                    // Add C and M after index i
                    i = subDigit('C', 'M', i, c);
                    number = number % 100;
                }
            } // if base value of number is greater than or equal to 100
            else if ( number >= 100) {
                // to add base symbol to the char array
                if (number < 400) {
                    i = digit('C', number / 100, i, c);
                    number = number % 100;
                }
                // to handle subtractive notation in case of number
                // having digit as 4 and adding corresponding base
                // symbol
                else {
                    i = subDigit('C', 'D', i, c);
                    number = number % 100;
                }
            }

            else if (number >= 50) {
                if (number < 90) {
                    i = digit('L', number/50, i, c);
                    number = number%50;
                }
                // to handle subtractive notation in case of number
                // having digit as 9 and adding corresponding base
                // symbol
                else {
                    i = subDigit('X', 'C', i, c);
                    number = number % 10;
                }
            }

            else if (number >= 10) {
                if (number < 40) {
                    i = digit('X', number / 10, i, c);
                    number = number % 10;
                }

                else {
                    i = subDigit('X', 'L', i, c);
                    number = number % 10;
                }
            }

            else if (number >= 5) {
                if (number < 9) {
                    i = digit('V', number / 5, i, c);
                    number = number % 5;
                }
                else {
                    i = subDigit('I','X',i,c);
                    number = 0;
                }
            }

            else if (number >= 1) {
                if (number < 4) {
                    i = digit('I',number,i,c);
                    number = 0;
                } else {
                    i = subDigit('I','V',i,c);
                    number = 0;
                }
            }
        }


        System.out.print("Roman numeral for " + number + " is ");
        for (int j = 0; j < i; j++) {
            System.out.print(c[j]);
        }


    }

    // to add symbol 'ch' n times after index i in c[]
    private static int digit(char ch, int n, int i, char[] c) {
        for (int j = 0; j < n; j++)
            c[i++] = ch;
        return i;
    }

    // add corresponding base symbols in the array
    // to handle cases which follow subtractive notation
    // base symbols are added index 'i'
    private static int subDigit(char num1, char num2, int i, char[] c) {
        c[i++] = num1;
        c[i++] = num2;
        return i;
    }
}
