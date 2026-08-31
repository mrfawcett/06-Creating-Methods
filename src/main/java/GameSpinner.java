/** READ FIRST
 *
 * This problem involves the creation and use of a spinner to generate
 * random numbers in a game. A GameSpinner object represents a spinner
 * with a given number of sectors, all equal in size. The GameSpinner
 * class supports the following behaviors.
 *
 *   * Creating a new spinner with a specified number of sectors
 *   * Spinning the spinner and reporting the result
 *   * Reporting the length of the current run: the number of consecutive
 *     spins (including the most recent one) that all landed on the same
 *     value as the most recent spin
 *
 * The constructor is written for you. You complete spin() and currentRun().
 * The three private fields are the only state you need:
 *
 *   sectors        how many equal sectors the spinner has (spin returns 1..sectors)
 *   previousSpin   the value of the most recent spin (0 before any spin)
 *   currentLength  the length of the current run (0 before any spin)
 *
 * Example (four sectors; the random results shown are one possible outcome):
 *
 *   Statement                        Returns   Comment
 *   GameSpinner g = new GameSpinner(4);        four sectors
 *   g.currentRun();                  0         no spins yet
 *   g.spin();                        3         random 1..4; this time 3
 *   g.currentRun();                  1         one spin of 3 so far
 *   g.spin();                        3         3 again
 *   g.currentRun();                  2         two 3s in a row
 *   g.spin();                        4         different value
 *   g.currentRun();                  1         run restarts at 1, not 0
 *   g.spin();                        3
 *   g.currentRun();                  1         3 differs from the previous 4
 *   g.spin();                        1
 *   g.spin();                        1
 *   g.spin();                        1
 *   g.currentRun();                  3         three consecutive 1s
 *
 * This assignment is written in the style of an AP Computer Science A
 * free-response question.
 */
public class GameSpinner {
    private int sectors;
    private int previousSpin = 0;
    private int currentLength = 0;

    /** PROVIDED - do not change
     * Creates a spinner with s equal sectors. Precondition: s >= 1
     */
    public GameSpinner(int s) {
        sectors = s;
    }

    /** COMPLETE THIS METHOD
     * Precondition: sectors >= 1
     * Spins the spinner: picks a random integer from 1 to sectors, inclusive.
     * If the value is the same as previousSpin, the run gets one longer
     * (currentLength increases by 1); otherwise a new run starts
     * (currentLength becomes 1). Records the value as previousSpin and
     * returns it.
     * Example: new GameSpinner(1).spin() always returns 1, and after three
     * spins currentRun() is 3.
     * Hint: (int) (Math.random() * sectors) + 1 gives a random int in 1..sectors.
     */
    public int spin() {
        // Insert your code below

        return 0;
    }

    /** COMPLETE THIS METHOD
     * Returns the length of the current run: how many spins in a row
     * (counting the most recent) have landed on the most recent value.
     * Returns 0 if the spinner has never been spun. Does not spin.
     * Example: after spins of 3, 3, 4 the current run is 1; after 3, 3 it is 2.
     */
    public int currentRun() {
        // Insert your code below

        return 0;
    }
}
