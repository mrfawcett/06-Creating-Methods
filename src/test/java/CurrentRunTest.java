import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CurrentRunTest {
    @DisplayName("currentRun: 0 before any spin (new GameSpinner(4))")
    @Test
    void currentRun_Test01() {
        GameSpinner g = new GameSpinner(4);
        assertEquals(0, g.currentRun(), "no spins have happened yet, so the run length is 0");
    }

    @DisplayName("currentRun: 1 after the first spin (1 sector)")
    @Test
    void currentRun_Test02() {
        GameSpinner g = new GameSpinner(1);
        g.spin();
        assertEquals(1, g.currentRun(), "after exactly one spin the run length is 1");
    }

    @DisplayName("currentRun: 2 after two identical spins (1 sector: both spins are 1)")
    @Test
    void currentRun_Test03() {
        GameSpinner g = new GameSpinner(1);
        g.spin();
        g.spin();
        assertEquals(2, g.currentRun(),
            "two spins of 1 in a row make a run of 2; did you update previousSpin after each spin?");
    }

    @DisplayName("currentRun: 7 after seven spins of a 1-sector spinner")
    @Test
    void currentRun_Test04() {
        GameSpinner g = new GameSpinner(1);
        for (int i = 1; i <= 7; i++) g.spin();
        assertEquals(7, g.currentRun(), "seven identical spins in a row make a run of 7");
    }

    @DisplayName("currentRun: grows by exactly 1 on every spin of a 1-sector spinner (25 spins)")
    @Test
    void currentRun_Test05() {
        GameSpinner g = new GameSpinner(1);
        for (int i = 1; i <= 25; i++) {
            g.spin();
            assertEquals(i, g.currentRun(), "after " + i + " spins of 1 the run length should be " + i);
        }
    }

    @DisplayName("currentRun: calling it does not spin or change anything (3 spins, then 3 reads all give 3)")
    @Test
    void currentRun_Test06() {
        GameSpinner g = new GameSpinner(1);
        g.spin();
        g.spin();
        g.spin();
        assertEquals(3, g.currentRun(), "first read after three spins");
        assertEquals(3, g.currentRun(), "second read must be the same; currentRun() must not spin");
        assertEquals(3, g.currentRun(), "third read must be the same; currentRun() must not spin");
    }

    @DisplayName("currentRun: after two spins of a 6-sector spinner it is 2 if they matched, otherwise 1")
    @Test
    void currentRun_Test07() {
        GameSpinner g = new GameSpinner(6);
        int first = g.spin();
        int second = g.spin();
        if (first == second) {
            assertEquals(2, g.currentRun(), "spins were " + first + ", " + second + " (same) so the run is 2");
        } else {
            assertEquals(1, g.currentRun(), "spins were " + first + ", " + second + " (different) so the run is 1");
        }
    }

    @DisplayName("currentRun: a different value restarts the run at 1, not 0 (2 sectors)")
    @Test
    void currentRun_Test08() {
        GameSpinner g = new GameSpinner(2);
        int prev = g.spin();
        for (int i = 1; i <= 2000; i++) {
            int v = g.spin();
            if (v != prev) {
                assertEquals(1, g.currentRun(),
                    "spun " + prev + " then " + v + "; a new value starts a fresh run of length 1");
                return;
            }
            prev = v;
        }
        fail("a 2-sector spinner returned the same value 2001 times in a row; spin() is not random");
    }

    @DisplayName("currentRun: matches the run length computed from spin()'s own return values (6 sectors, 500 spins)")
    @Test
    void currentRun_Test09() {
        GameSpinner g = new GameSpinner(6);
        int prev = 0;
        int expectedRun = 0;
        for (int i = 1; i <= 500; i++) {
            int v = g.spin();
            if (v == prev) {
                expectedRun++;
            } else {
                expectedRun = 1;
            }
            prev = v;
            assertEquals(expectedRun, g.currentRun(),
                "after spin " + i + " (value " + v + ") the run length should be " + expectedRun);
        }
    }

    @DisplayName("currentRun: after a run is broken it can grow again (2 sectors)")
    @Test
    void currentRun_Test10() {
        GameSpinner g = new GameSpinner(2);
        int prev = g.spin();
        int run = 1;
        boolean sawBreak = false;
        boolean sawGrowthAfterBreak = false;
        for (int i = 1; i <= 2000 && !sawGrowthAfterBreak; i++) {
            int v = g.spin();
            if (v == prev) {
                run++;
                if (sawBreak) sawGrowthAfterBreak = true;
            } else {
                run = 1;
                sawBreak = true;
            }
            prev = v;
            assertEquals(run, g.currentRun(), "after spin value " + v + " the run should be " + run);
        }
        assertTrue(sawGrowthAfterBreak,
            "in 2000 spins of a 2-sector spinner a run never restarted and then grew; spin() is not random");
    }

    @DisplayName("currentRun: each spinner tracks its own run (fields must not be static)")
    @Test
    void currentRun_Test11() {
        GameSpinner a = new GameSpinner(1);
        GameSpinner b = new GameSpinner(1);
        a.spin();
        a.spin();
        a.spin();
        b.spin();
        assertEquals(3, a.currentRun(), "spinner a was spun three times");
        assertEquals(1, b.currentRun(), "spinner b was spun once; it must not share state with a");
        assertEquals(0, new GameSpinner(1).currentRun(), "a brand-new spinner has a run of 0");
    }
}
