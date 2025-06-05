package com.prog.tierpark;

import com.prog.tierpark.model.Worker;

/**
 * Utility class for managing the currently logged-in worker session.
 * Provides static methods to set, get, and clear the active session.
 */
public class Session {

    /** The worker currently logged into the system. */
    private static Worker loggedInWorker;

    /**
     * Sets the currently logged-in worker.
     *
     * @param worker the worker who has logged in
     */
    public static void setLoggedInWorker(Worker worker) {
        loggedInWorker = worker;
    }

    /**
     * Returns the currently logged-in worker.
     *
     * @return the active Worker instance, or null if not logged in
     */
    public static Worker getLoggedInWorker() {
        return loggedInWorker;
    }

    /**
     * Clears the current worker session (logs out).
     */
    public static void clear() {
        loggedInWorker = null;
    }
}
