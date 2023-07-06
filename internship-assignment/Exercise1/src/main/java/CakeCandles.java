import java.util.Scanner;

public class CakeCandles {
    public void launchApp() {
        int numberOfCandles;

        do {
            System.out.print("Number of candles: ");
            numberOfCandles = new Scanner(System.in).nextInt();
        } while (numberOfCandles < 1 || numberOfCandles > Math.pow(10, 3));

        int[] candles = new int[numberOfCandles];

        System.out.print("Size of each candle: ");
        for (int i = 0; i < candles.length; i++) {
            boolean candleSizeIsValid = false;
            do {
                candles[i] = new Scanner(System.in).nextInt();
                if (candles[i] < 1 || candles[i] > Math.pow(10, 3)) {
                    System.out.println("\n" + candles[i] + " is not valid!");
                    System.out.print("Try again: ");
                } else {
                    candleSizeIsValid = true;
                }
            } while (!candleSizeIsValid);

        }

        int numberOfTallestCandles = birthCakeCandles(candles);
        System.out.println("Number of tallest candles is: " + numberOfTallestCandles);
    }

    public int birthCakeCandles(int candles[]) {
        return findOthersTallestCandles(candles, getTallestCandle(candles));
    }

    public int getTallestCandle(int candles[]) {
        int tallestCandle = 0;
        for (int candle : candles) {
            if (candle > tallestCandle) {
                tallestCandle = candle;
            }
        }
        return tallestCandle;
    }

    public int findOthersTallestCandles(int candles[], int tallestCandle) {
        int numberOfTallestCandles = 0;
        for (int candle : candles) {
            if (candle == tallestCandle) {
                numberOfTallestCandles++;
            }
        }
        return numberOfTallestCandles;
    }
}
