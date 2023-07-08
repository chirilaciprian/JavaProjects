package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    static ServerSocket ss = null;
    static List<Game> games = new ArrayList<Game>();

    static void Stop() {
        try {
            ss.close();
            System.out.println("Server stopped");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {

        try {
            ss = new ServerSocket(34567);
            while (true) {
                Socket s = ss.accept();
                new ClientThread(s).start();
                System.out.println("Connection Working");
            }
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        } finally {
            ss.close();
        }
    }

    public static void addGame(Game g) {
        games.add(g);
    }

    public static List<Game> getGames() {
        return games;
    }
}
