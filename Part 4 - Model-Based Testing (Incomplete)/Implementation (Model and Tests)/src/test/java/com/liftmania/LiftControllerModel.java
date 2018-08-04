package com.liftmania;

import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Assert;
import org.junit.Test;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.liftmania.LiftControllerStates.*;

public class LiftControllerModel implements FsmModel {

    private static final int
            NO_OF_FLOORS = 3,
            NO_OF_LIFTS = 1,
            TOP_FLOOR = NO_OF_FLOORS - 1,
            BOTTOM_FLOOR = 0;
    private static final int LIFT0 = 0;

    private LiftController sut;
    private LiftControllerStates state = CLOSED_IDLE;

    private long approxTimeOfOpen = -1;
    private final ArrayList<Integer> summons = new ArrayList<>();
    private final ArrayList<Integer> requests = new ArrayList<>();
    private final ArrayList<Boolean> shouldIgnore = new ArrayList<>();
    private final ArrayList<Boolean> taskIsSummon = new ArrayList<>();

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {

        // Close window and create new LiftController
        if (sut != null) sut.visualiser.dispatchEvent(new WindowEvent(sut.visualiser, WindowEvent.WINDOW_CLOSING));
        sut = new LiftController(NO_OF_FLOORS, NO_OF_LIFTS, false);

        // Reset state and variables
        state = CLOSED_IDLE;
        approxTimeOfOpen = -1;
        summons.clear();
        requests.clear();
        shouldIgnore.clear();
        taskIsSummon.clear();
    }

    public boolean requestGuard() {
        return requests.size() < (2 * NO_OF_FLOORS); // to avoid too much requests
    }

    @Action
    public void request() {

        if (haveNoTasks()) {
            Assert.assertFalse(sut.lifts[LIFT0].moving); // Invariant 3
        }

        // Select random floor, collect information, and perform summon in SUT
        final int randFloor = ThreadLocalRandom.current().nextInt(0, NO_OF_FLOORS);
        requests.add(randFloor);
        setIgnoreValue(true, randFloor);
        taskIsSummon.add(false);
        sut.moveLift(LIFT0, randFloor);

        state = (state == CLOSED_IDLE || state == CLOSED_wREQ) ? CLOSED_wREQ : OPEN_wREQ;
    }

    public boolean summonGuard() {
        return summons.size() < NO_OF_LIFTS; // since any more crashes the elevator system
    }

    @Action
    public void summon() throws InterruptedException {

        if (haveNoTasks()) {
            Assert.assertFalse(sut.lifts[LIFT0].moving); // Invariant 3
        }

        // Select random floor, collect information, and perform summon in SUT
        final int randFloor = ThreadLocalRandom.current().nextInt(0, NO_OF_FLOORS);
        summons.add(randFloor);
        setIgnoreValue(false, randFloor);
        taskIsSummon.add(true);
        sut.callLiftToFloor(randFloor);

        if (randFloor == sut.lifts[LIFT0].floor && sut.lifts[LIFT0].moving) {
            Thread.sleep(500);
            Assert.assertTrue(sut.lifts[LIFT0].doorsOpen); // Temporal 3 (expected to fail)
            Assert.fail("Temporal property 3 did not fail."); // Expected to fail and didn't
        }

        if (randFloor == sut.lifts[LIFT0].floor && sut.lifts[LIFT0].doorsClosing) {
            Thread.sleep(500);
            Assert.assertTrue(sut.lifts[LIFT0].doorsOpen); // Temporal 5 (expected to fail)
            Assert.fail("Temporal property 5 did not fail."); // Expected to fail and didn't
        }

        state = (state == CLOSED_IDLE || state == CLOSED_wREQ) ? CLOSED_wREQ : OPEN_wREQ;
    }

    public boolean serviceGuard() {
        return state == CLOSED_wREQ;
    }

    @Action
    public void service() throws InterruptedException {

        final boolean isSummon = taskIsSummon.remove(0);
        final int toFloor = isSummon ? summons.remove(0) : requests.remove(0);

        // Discard shouldIgnore for current task (since we only need the next shouldIgnore)
        shouldIgnore.remove(0);

        if (sut.lifts[LIFT0].floor != toFloor) {

            // Wait for lift to arrive at its destination
            while (sut.lifts[LIFT0].floor != toFloor) {
                Assert.assertFalse(sut.lifts[LIFT0].betweenFloors && !sut.lifts[LIFT0].moving); // Invariant 4
                Assert.assertFalse(sut.lifts[LIFT0].betweenFloors && sut.lifts[LIFT0].doorsOpen); // Invariant 5
                Thread.sleep(200);
            }

            // Calculate approximate time that doors opened
            approxTimeOfOpen = System.currentTimeMillis();
        } else {
            // Lift might have already opened and closed its door so we should skip the above checks
            approxTimeOfOpen = -1; // Dummy approximation
        }

        // Lift has arrived at its destination
        Assert.assertFalse(sut.lifts[LIFT0].floor == TOP_FLOOR && sut.lifts[LIFT0].moving); // Invariant 2 (top floor)
        Assert.assertFalse(sut.lifts[LIFT0].floor == BOTTOM_FLOOR && sut.lifts[LIFT0].moving); // Invariant 2 (bottom floor)

        // Set state
        state = haveNoTasks() ? OPEN_IDLE : OPEN_wREQ;
    }

    public boolean closeDoorGuard() {
        return state == OPEN_IDLE || state == OPEN_wREQ;
    }

    @Action
    public void closeDoor() throws InterruptedException {

        if (sut.lifts[LIFT0].doorsOpen) {

            // Wait for lift to close its doors
            while (sut.lifts[LIFT0].doorsOpen) {
                Assert.assertFalse(sut.lifts[LIFT0].doorsOpen && sut.lifts[LIFT0].moving); // Invariant 1
                Thread.sleep(200);
            }

            // Get time that doors closed
            final long approxTimeOfClose = System.currentTimeMillis();

            if (approxTimeOfOpen != -1) {
                Assert.assertTrue(approxTimeOfClose - approxTimeOfOpen > 3000); // Real-time property 2 (expected to fail)
                Assert.fail("Real-time property 2 did not fail.");
            }

            // Check that lift does not immediately re-open its doors if the next task should be ignored
            if (!shouldIgnore.isEmpty() && shouldIgnore.get(0)) {
                Thread.sleep(500);
                Assert.assertFalse(sut.lifts[LIFT0].doorsOpen); // Temporal 2 (expected to fail)
                Assert.fail("Temporal property 2 did not fail."); // Expected to fail and didn't
            }

            // If next floor is not the current floor, check for real-time property 1
            final int nextFloor = nextFloor();
            if (nextFloor != -1 && sut.lifts[LIFT0].floor != nextFloor) {
                while (!sut.lifts[LIFT0].moving) {
                    Thread.sleep(200);
                }
                final long approxTimeOfMove = System.currentTimeMillis();
                Assert.assertTrue(approxTimeOfMove - approxTimeOfClose < 3000); // Real-time property 1
            }
        } else {
            // Lift might have already closed its door so we should skip the above checks
        }

        // Set state
        state = haveNoTasks() ? CLOSED_IDLE : CLOSED_wREQ;
    }

    public boolean stationaryCheckGuard() {
        return haveNoTasks() || sut.lifts[LIFT0].doorsOpen;
    }

    @Action
    public void stationaryCheck() {
        Assert.assertFalse(sut.lifts[LIFT0].moving); // Invariants 1 and 3
    }

    @Test
    public void runner() {
        final Model model = new Model(new LiftControllerModel());

        final GreedyTester tester = new GreedyTester(model);
        tester.setRandom(new Random());
        tester.setResetProbability(0.01);
        tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new TransitionCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(100);
        tester.printCoverage();
    }

    /**
     * Returns true if there are no outstanding summons or requests.
     */
    private boolean haveNoTasks() {
        return summons.isEmpty() && requests.isEmpty();
    }

    /**
     * Returns the next floor that will be visited, if any (-1).
     */
    private int nextFloor() {
        return haveNoTasks() ? -1 :
                taskIsSummon.get(0) ? summons.get(0) :
                        requests.get(0);
    }

    /**
     * Adds a true or false value to the shouldIgnore ArrayList.
     * If the new task is exactly the same as the previous one,
     * then it should be ignored (Temporal Property 2).
     */
    private void setIgnoreValue(final boolean isSummon, final int floor) {

        // No previous summons or requests
        if (taskIsSummon.isEmpty()) {
            shouldIgnore.add(false);
            return;
        }

        final boolean lastWasSummon = taskIsSummon.get(taskIsSummon.size() - 1);
        if (isSummon && lastWasSummon) { // current and previous both summons
            shouldIgnore.add(summons.get(summons.size() - 1).equals(floor)); // true if summons have same floor
        } else if (!isSummon && !lastWasSummon) { // current and previous both requests
            shouldIgnore.add(requests.get(requests.size() - 1).equals(floor)); // true if requests have same floor
        } else {
            shouldIgnore.add(false); // New task is not exactly the same as the previous one
        }
    }
}
