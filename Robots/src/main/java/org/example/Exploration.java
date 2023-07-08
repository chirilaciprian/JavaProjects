package org.example;

import java.util.*;

public class Exploration {
    private final SharedMemory mem = new SharedMemory(10);
    private final ExplorationMap map = new ExplorationMap(10, mem);
    private final List<Robot> robots = new ArrayList<>();

    public SharedMemory getMem() {
        return mem;
    }

    public ExplorationMap getMap() {
        return map;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public void addRobot(Robot r) {
        robots.add(r);
    }

    public void start() {
        for (Robot robot : robots) {
            robot.setExplore(this);
            new Thread(robot).start();
        }
    }

    public static void main(String args[]) {
        var explore = new Exploration();
        explore.addRobot(new Robot("Wall-E"));
        explore.addRobot(new Robot("R2D2"));
        explore.addRobot(new Robot("Optimus Prime"));
        TimeKeeper timekeeper = new TimeKeeper(100);
        timekeeper.start();
        explore.start();
        explore.robots.forEach(r -> r.PauseRobot());
        Scanner in = new Scanner(System.in);
        while (timekeeper.isTimeExpired() == false) {
            String CommandName = in.nextLine();
            System.out.println("Comanda: " + CommandName);
            if (CommandName.equals("start")) {
                explore.robots.forEach(r -> {
                    synchronized (r) {
                        r.notifyAll();
                    }
                });
            }
            if (CommandName.equals("stop")) {
                explore.robots.forEach(r -> r.PauseRobot());
            }
            explore.robots.forEach(r -> {
                if (CommandName.equals("start " + r.getName())) {
                    synchronized (r) {
                        r.notifyAll();
                    }
                }
            });
            explore.robots.forEach(r -> {
                if (CommandName.equals("stop " + r.getName())) {
                    r.PauseRobot();
                }
            });

        }
    }
}
