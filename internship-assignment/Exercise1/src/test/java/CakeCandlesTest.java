import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CakeCandlesTest {

    @Test
    public void birthCakeCandlesTest() {
        CakeCandles cakeCandles = new CakeCandles();
        int[] candles = {2, 5, 5, 5};
        assertEquals(3, cakeCandles.birthCakeCandles(candles));
    }
    @Test
    public void getTallestCandleTest() {
        CakeCandles cakeCandles = new CakeCandles();
        int[] candles = {4, 4, 5, 2};
        assertEquals(5, cakeCandles.getTallestCandle(candles));
    }


    @Test
    public void findOthersTallestCandlesTest() {
        CakeCandles cakeCandles = new CakeCandles();
        int[] candles = {4, 4, 5, 2};
        assertEquals(2, cakeCandles.findOthersTallestCandles(candles, 4));
    }
}
