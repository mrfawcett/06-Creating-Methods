import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpinTest {
    @DisplayName("spin: a spinner with 1 sector always returns 1 (new GameSpinner(1))")
    @Test
    void spin_Test01() {
        GameSpinner g = new GameSpinner(1);
        assertEquals(1, g.spin(), "with one sector the only possible result is 1");
    }

    @DisplayName("spin: 1 sector, 50 spins in a row, every one returns 1")
    @Test
    void spin_Test02() {
        GameSpinner g = new GameSpinner(1);
        for (int i = 1; i <= 50; i++) {
            assertEquals(1, g.spin(), "spin number " + i + " of a 1-sector spinner should return 1");
        }
    }

    @DisplayName("spin: 6 sectors, 1000 spins, every result is between 1 and 6 inclusive")
    @Test
    void spin_Test03() {
        GameSpinner g = new GameSpinner(6);
        for (int i = 1; i <= 1000; i++) {
            int v = g.spin();
            assertTrue(v >= 1 && v <= 6, "spin() returned " + v + " but must be in 1..6");
        }
    }

    @DisplayName("spin: never returns 0 (did you add 1 after the cast?)")
    @Test
    void spin_Test04() {
        GameSpinner g = new GameSpinner(4);
        for (int i = 1; i <= 1000; i++) {
            int v = g.spin();
            assertTrue(v != 0, "spin() returned 0; (int)(Math.random() * sectors) is 0..sectors-1, so add 1");
        }
    }

    @DisplayName("spin: never returns more than sectors (4 sectors, 1000 spins, max is 4)")
    @Test
    void spin_Test05() {
        GameSpinner g = new GameSpinner(4);
        for (int i = 1; i <= 1000; i++) {
            int v = g.spin();
            assertTrue(v <= 4, "spin() returned " + v + " on a 4-sector spinner");
        }
    }

    @DisplayName("spin: 6 sectors, every value 1..6 shows up at least once in 1000 spins")
    @Test
    void spin_Test06() {
        GameSpinner g = new GameSpinner(6);
        boolean[] seen = new boolean[7];
        for (int i = 1; i <= 1000; i++) {
            int v = g.spin();
            if (v >= 1 && v <= 6) seen[v] = true;
        }
        for (int v = 1; v <= 6; v++) {
            assertTrue(seen[v], "the value " + v + " never came up in 1000 spins of a 6-sector spinner");
        }
    }

    @DisplayName("spin: 2 sectors, 1000 spins, roughly half are 1 (accepts 25%-75%)")
    @Test
    void spin_Test07() {
        GameSpinner g = new GameSpinner(2);
        int ones = 0;
        for (int i = 1; i <= 1000; i++) {
            int v = g.spin();
            assertTrue(v == 1 || v == 2, "spin() returned " + v + " on a 2-sector spinner");
            if (v == 1) ones++;
        }
        assertTrue(ones >= 250 && ones <= 750,
            "expected between 250 and 750 ones out of 1000 spins, got " + ones);
    }

    @DisplayName("spin: 6 sectors, no value is spun more than half the time in 1200 spins")
    @Test
    void spin_Test08() {
        GameSpinner g = new GameSpinner(6);
        int[] counts = new int[7];
        for (int i = 1; i <= 1200; i++) {
            int v = g.spin();
            if (v >= 1 && v <= 6) counts[v]++;
        }
        for (int v = 1; v <= 6; v++) {
            assertTrue(counts[v] <= 600,
                "the value " + v + " came up " + counts[v] + " times in 1200 spins; the sectors should be equally likely");
        }
    }

    @DisplayName("spin: 10 sectors, 1000 spins, every result is in 1..10 (sectors is not hard-coded)")
    @Test
    void spin_Test09() {
        GameSpinner g = new GameSpinner(10);
        boolean sawAbove6 = false;
        for (int i = 1; i <= 1000; i++) {
            int v = g.spin();
            assertTrue(v >= 1 && v <= 10, "spin() returned " + v + " on a 10-sector spinner");
            if (v > 6) sawAbove6 = true;
        }
        assertTrue(sawAbove6, "a 10-sector spinner never returned 7, 8, 9 or 10 in 1000 spins; use the sectors field");
    }

    @DisplayName("spin: two spinners with different sector counts each stay inside their own range")
    @Test
    void spin_Test10() {
        GameSpinner small = new GameSpinner(2);
        GameSpinner big = new GameSpinner(8);
        for (int i = 1; i <= 500; i++) {
            int s = small.spin();
            int b = big.spin();
            assertTrue(s >= 1 && s <= 2, "2-sector spinner returned " + s);
            assertTrue(b >= 1 && b <= 8, "8-sector spinner returned " + b);
        }
    }
}
