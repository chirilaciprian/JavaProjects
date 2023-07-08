package org.example;

public class TimeKeeper extends Thread {
    private final int timeLimit;  // in seconds
    private boolean timeExpired = false;

    public TimeKeeper(int timeLimit) {
        this.timeLimit = timeLimit;
        setDaemon(true);
    }

    public boolean isTimeExpired() {
        return timeExpired;
    }

    @Override
    public void run() {
        int secondsElapsed = 0;
        while (secondsElapsed < timeLimit) {
            try {
                sleep(5000);  // wait 1 second
                secondsElapsed += 5;
                System.out.println("Exploration time: " + secondsElapsed + " seconds");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Time limit of " + timeLimit + " seconds has been reached. Stopping exploration.");
        timeExpired = true;
    }
}