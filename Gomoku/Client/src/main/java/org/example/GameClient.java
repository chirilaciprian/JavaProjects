package org.example;

import java.io.IOException;
import java.io.*;
import java.net.Socket;

public class GameClient {
    private Socket serverSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean running;

    public GameClient(String serverAddress, int serverPort) {
        try {
            serverSocket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server: " + serverAddress + ":" + serverPort);
            reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            writer = new PrintWriter(serverSocket.getOutputStream());
            running = true;
            startCommandListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startCommandListener() {
        Thread commandListenerThread = new Thread(() -> {
            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while (running) {
                    String command = keyboardReader.readLine();
                    if (command.equals("exit")) {
                        running = false;
                    } else {
                        writer.println(command);
                        writer.flush();
                    }
                }
                keyboardReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        commandListenerThread.start();
    }

    private void startResponseListener() {
        Thread responseListenerThread = new Thread(() -> {
            try {
                String serverMessage;
                while (running && (serverMessage = reader.readLine()) != null) {
                    System.out.println(serverMessage);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        responseListenerThread.start();
    }

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Specify the server address
        int serverPort = 34567; // Specify the server port
        GameClient gameClient = new GameClient(serverAddress, serverPort);
        gameClient.startResponseListener();
    }
}
