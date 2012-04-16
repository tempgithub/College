// @author Matt Jadud 
// @version 20110215
public class PerformanceTesting
{
    public PerformanceTesting() {
    }

    /* How long does it take to push 'num' elements? */
    public long testStackInsertion(long num) {
        long start, stop;

        // Create a fresh Stack.
        // Your stack will use generics, so this will
        // probably look like Stack<SomethingHere>();
        Stack s = new Stack();

        // I don't care what I'm pushing onto the Stack,
        // so long as I don't create it as part of the loop.
        Object o = new Object();

        // Start timing.
        start = System.nanoTime();
        // Run the loop.
        for (int i = 0; i < num; i++) {
            s.push(o);
        }
        // Stop timing.
        stop = System.nanoTime();

        return stop - start;

    }

    /* How long does it take to push 'num' elements? */
    public long testQueueInsertion(long num) {
        long start, stop;

        // Create a fresh Stack.
        // Your stack will use generics, so this will
        // probably look like Stack<SomethingHere>();
        Queue q = new Queue();

        // I don't care what I'm pushing onto the Stack,
        // so long as I don't create it as part of the loop.
        Object o = new Object();

        // Start timing.
        start = System.nanoTime();
        // Run the loop.
        for (int i = 0; i < num; i++) {
            q.push(o);
        }
        // Stop timing.
        stop = System.nanoTime();

        return stop - start;

    }

    /* How long does it take to push 'num' elements? */
    public long testStackRemoval(long num) {
        long start, stop;

        // Create a fresh Stack.
        // Your stack will use generics, so this will
        // probably look like Stack<SomethingHere>();
        Stack s = new Stack();

        // I don't care what I'm pushing onto the Stack,
        // so long as I don't create it as part of the loop.
        Object o = new Object();

        // Run the loop.
        for (int i = 0; i < num; i++) {
            s.push(o);
        }

        // Start timing.
        start = System.nanoTime();
        // Run the loop.
        for (int i = 0; i < num; i++) {
            s.pop();
        }
        // Stop timing.
        stop = System.nanoTime();

        return stop - start;

    }

    /* How long does it take to push 'num' elements? */
    public long testQueueRemoval(long num) {
        long start, stop;

        // Create a fresh Stack.
        // Your stack will use generics, so this will
        // probably look like Stack<SomethingHere>();
        Queue q = new Queue();

        // I don't care what I'm pushing onto the Stack,
        // so long as I don't create it as part of the loop.
        Object o = new Object();

        // Run the loop.
        for (int i = 0; i < num; i++) {
            q.push(o);
        }

        // Start timing.
        start = System.nanoTime();
        // Run the loop.
        for (int i = 0; i < num; i++) {
            q.pop();
        }
        // Stop timing.
        stop = System.nanoTime();

        return stop - start;

    }

    /* How long does it take to push 'num' elements? */
    public long testStackPeek(long num) {
        long start, stop;

        // Create a fresh Stack.
        // Your stack will use generics, so this will
        // probably look like Stack<SomethingHere>();
        Stack s = new Stack();

        // I don't care what I'm pushing onto the Stack,
        // so long as I don't create it as part of the loop.
        Object o = new Object();

        // Run the loop.
        for (int i = 0; i < num; i++) {
            s.push(o);
        }

        // Start timing.
        start = System.nanoTime();
        // Run the loop.
        s.peek();
        // Stop timing.
        stop = System.nanoTime();

        return stop - start;

    }

    /* How long does it take to push 'num' elements? */
    public long testQueuePeek(long num) {
        long start, stop;

        // Create a fresh Stack.
        // Your stack will use generics, so this will
        // probably look like Stack<SomethingHere>();
        Queue q = new Queue();

        // I don't care what I'm pushing onto the Stack,
        // so long as I don't create it as part of the loop.
        Object o = new Object();

        // Run the loop.
        for (int i = 0; i < num; i++) {
            q.push(o);
        }

        // Start timing.
        start = System.nanoTime();
        // Run the loop.
        q.peek();
        // Stop timing.
        stop = System.nanoTime();

        return stop - start;

    }

    /* How long does it take to push 'num' elements? */
    public long testStackIsEmpty(long num) {
        long start, stop;

        // Create a fresh Stack.
        // Your stack will use generics, so this will
        // probably look like Stack<SomethingHere>();
        Stack s = new Stack();

        // I don't care what I'm pushing onto the Stack,
        // so long as I don't create it as part of the loop.
        Object o = new Object();

        // Run the loop.
        for (int i = 0; i < num; i++) {
            s.push(o);
        }

        // Start timing.
        start = System.nanoTime();
        // Run the loop.
        s.isEmpty();
        // Stop timing.
        stop = System.nanoTime();

        return stop - start;

    }

    /* How long does it take to push 'num' elements? */
    public long testQueueIsEmpty(long num) {
        long start, stop;

        // Create a fresh Stack.
        // Your stack will use generics, so this will
        // probably look like Stack<SomethingHere>();
        Queue q = new Queue();

        // I don't care what I'm pushing onto the Stack,
        // so long as I don't create it as part of the loop.
        Object o = new Object();

        // Run the loop.
        for (int i = 0; i < num; i++) {
            q.push(o);
        }

        // Start timing.
        start = System.nanoTime();
        // Run the loop.
        q.isEmpty();
        // Stop timing.
        stop = System.nanoTime();

        return stop - start;

    }

    public void runTests() {
        /* INSERTION TIMING
         * This times how long it takes to insert 1, 10, 100, etc. elements
         * onto your stack. How many can your stack handle? How long does it take?
         */
        String currentTest = "STACK INSERTION TESTS";

        System.out.println("Running " + currentTest);
        // How many runs should we average over?
        int numRuns = 20;
        long total = 0;   
        int[] iterations = {10, 100, 1000, 10000, 100000, 200000, 300000, 
                400000, 500000, 600000, 700000, 800000, 900000, 1000000};

        for (int iter = 0; iter < iterations.length ; iter++) {

            for (int avg = 0; avg < numRuns; avg++) {    
                // Do the test.
                long time = testStackInsertion(iterations[iter]);
                // Add the time it took to the total.
                total += time;
            }

            total = total / numRuns;
            System.out.println(iterations[iter] + "," + total);
        }

        System.out.println("Done with " + currentTest);

        /* INSERTION TIMING
         * This times how long it takes to insert 1, 10, 100, etc. elements
         * onto your stack. How many can your stack handle? How long does it take?
         */
        currentTest = "QUEUE INSERTION TESTS";

        System.out.println("\nRunning " + currentTest);
        // How many runs should we average over?
        numRuns = 20;
        total = 0;

        for (int iter = 0; iter < iterations.length ; iter++) {

            for (int avg = 0; avg < numRuns; avg++) {    
                // Do the test.
                long time = testQueueInsertion(iterations[iter]);
                // Add the time it took to the total.
                total += time;
            }

            total = total / numRuns;
            System.out.println(iterations[iter] + "," + total);
        }

        System.out.println("Done with " + currentTest);

        /* INSERTION TIMING
         * This times how long it takes to insert 1, 10, 100, etc. elements
         * onto your stack. How many can your stack handle? How long does it take?
         */
        currentTest = "STACK REMOVAL TESTS";

        System.out.println("\nRunning " + currentTest);
        // How many runs should we average over?
        numRuns = 20;
        total = 0;   

        for (int iter = 0; iter < iterations.length ; iter++) {

            for (int avg = 0; avg < numRuns; avg++) {    
                // Do the test.
                long time = testStackRemoval(iterations[iter]);
                // Add the time it took to the total.
                total += time;
            }

            total = total / numRuns;
            System.out.println(iterations[iter] + "," + total);
        }

        System.out.println("Done with " + currentTest);

        /* INSERTION TIMING
         * This times how long it takes to insert 1, 10, 100, etc. elements
         * onto your stack. How many can your stack handle? How long does it take?
         */
        currentTest = "QUEUE REMOVAL TESTS";

        System.out.println("\nRunning " + currentTest);
        // How many runs should we average over?
        numRuns = 20;
        total = 0;   

        for (int iter = 0; iter < 4 ; iter++) {

            for (int avg = 0; avg < numRuns; avg++) {    
                // Do the test.
                long time = testQueueRemoval(iterations[iter]);
                // Add the time it took to the total.
                total += time;
            }

            total = total / numRuns;
            System.out.println(iterations[iter] + "," + total);
        }

        System.out.println("Done with " + currentTest);

        /* INSERTION TIMING
         * This times how long it takes to insert 1, 10, 100, etc. elements
         * onto your stack. How many can your stack handle? How long does it take?
         */
        currentTest = "STACK PEEK TESTS";

        System.out.println("\nRunning " + currentTest);
        // How many runs should we average over?
        numRuns = 20;
        total = 0;   

        for (int iter = 0; iter < iterations.length ; iter++) {

            for (int avg = 0; avg < numRuns; avg++) {    
                // Do the test.
                long time = testStackPeek(iterations[iter]);
                // Add the time it took to the total.
                total += time;
            }

            total = total / numRuns;
            System.out.println(iterations[iter] + "," + total);
        }

        System.out.println("Done with " + currentTest);

        /* INSERTION TIMING
         * This times how long it takes to insert 1, 10, 100, etc. elements
         * onto your stack. How many can your stack handle? How long does it take?
         */
        currentTest = "QUEUE PEEK TESTS";

        System.out.println("\nRunning " + currentTest);
        // How many runs should we average over?
        numRuns = 20;
        total = 0;   

        for (int iter = 0; iter < iterations.length ; iter++) {

            for (int avg = 0; avg < numRuns; avg++) {    
                // Do the test.
                long time = testQueuePeek(iterations[iter]);
                // Add the time it took to the total.
                total += time;
            }

            total = total / numRuns;
            System.out.println(iterations[iter] + "," + total);
        }

        System.out.println("Done with " + currentTest);
        
                /* INSERTION TIMING
         * This times how long it takes to insert 1, 10, 100, etc. elements
         * onto your stack. How many can your stack handle? How long does it take?
         */
        currentTest = "STACK isEMPTY TESTS";

        System.out.println("\nRunning " + currentTest);
        // How many runs should we average over?
        numRuns = 20;
        total = 0;   

        for (int iter = 0; iter < iterations.length ; iter++) {

            for (int avg = 0; avg < numRuns; avg++) {    
                // Do the test.
                long time = testStackIsEmpty(iterations[iter]);
                // Add the time it took to the total.
                total += time;
            }

            total = total / numRuns;
            System.out.println(iterations[iter] + "," + total);
        }

        System.out.println("Done with " + currentTest);

        /* INSERTION TIMING
         * This times how long it takes to insert 1, 10, 100, etc. elements
         * onto your stack. How many can your stack handle? How long does it take?
         */
        currentTest = "QUEUE isEMPTY TESTS";

        System.out.println("\nRunning " + currentTest);
        // How many runs should we average over?
        numRuns = 20;
        total = 0;   

        for (int iter = 0; iter < iterations.length ; iter++) {

            for (int avg = 0; avg < numRuns; avg++) {    
                // Do the test.
                long time = testQueueIsEmpty(iterations[iter]);
                // Add the time it took to the total.
                total += time;
            }

            total = total / numRuns;
            System.out.println(iterations[iter] + "," + total);
        }

        System.out.println("Done with " + currentTest);
    }
}