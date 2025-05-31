package com.prog.tierpark;

import com.prog.tierpark.model.Worker;

public class Session {

    private static Worker loggedInWorker;

    public static void setLoggedInWorker(Worker worker) {
        loggedInWorker = worker;
    }

    public static Worker getLoggedInWorker() {
        return loggedInWorker;
    }

    public static void clear() {
        loggedInWorker = null;
    }
}
