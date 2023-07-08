package org.example;

import java.net.Socket;
import java.util.Timer;

public class Player {
    private String name;
    private Socket playerSocket;
    private ClientThread clientThread;
    private int turn;
    Timer timeToPlay;
    private Thread timerThread;

    public Player(String name, Socket playerSocket, ClientThread clientThread) {
        this.name = name;
        this.playerSocket = playerSocket;
        this.clientThread = clientThread;
        this.turn = 0;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void updateTurn() {
        turn = (turn + 1) % 2;
    }

    public int getTurn() {
        return turn;
    }

    public String getName() {
        return name;
    }

    public Socket getPlayerSocket() {
        return playerSocket;
    }

    public void startTimer() {
        timerThread = new Thread(() -> {
            try {
                Thread.sleep(6000 * 1000);
                clientThread.sendMessage("Time's up! You lose.");
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        });
        timerThread.start();
    }

    public void stopTimer() {
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.interrupt();
        }
    }
}
