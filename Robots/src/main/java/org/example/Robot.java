package org.example;

import java.util.*;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Robot implements Runnable {

    private String name;
    private int TokensNumber;
    private boolean pause = false;

    public String getName() {
        return name;
    }

    private boolean running = true;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setExplore(Exploration explore) {
        this.explore = explore;
    }

    Exploration explore;

    public Robot(String name) {
        this.name = name;
    }

    public void run() {
        Random rand = new Random();
        int row = rand.nextInt(explore.getMap().getN());
        int col = rand.nextInt(explore.getMap().getN());
        while (running == true) {
            if (pause == true) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            pause = false;
            if (explore.getMap().visit(row - 1, col, this)) {
                System.out.println("[" + name + "]" + " Succes . linia:" + row + " coloana:" + col);
                row--;
            } else if (explore.getMap().visit(row, col + 1, this)) {
                System.out.println("[" + name + "]" + " Succes . linia:" + row + " coloana:" + col);
                col++;
            } else if (explore.getMap().visit(row + 1, col, this)) {
                System.out.println("[" + name + "]" + " Succes . linia:" + row + " coloana:" + col);
                row++;
            } else if (explore.getMap().visit(row, col - 1, this)) {
                System.out.println("[" + name + "]" + " Succes . linia:" + row + " coloana:" + col);
                col--;
            } else {
                System.out.println("[" + name + "]" + " a terminat de vizitat");
                break;
            }


            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void PauseRobot() {
        pause = true;
    }
}
