package org.example;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientThread extends Thread {
    private Game game = null;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket = null;
    private Player player;

    public ClientThread(Socket s) {
        this.socket = s;
    }

    public void sendMessage(String s) {
        out.println(s);
    }

    public void run() {
        try {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            // Get the request from the input stream: client → server
            while (true) {

                String request = in.readLine();
                // Send the response to the output stream: server → client

                String response = "Server received the request: " + request + "!";
                if (request.equals("stop") == true) {
                    GameServer.Stop();
                    out.println("Server stopped!");
                    out.flush();
                    System.exit(0);
                    break;
                }
                if (request.indexOf("create game") != -1) {
                    String gameName = request.substring("create game ".length());
                    game = new Game(gameName, new Board());
                    GameServer.addGame(game);
                    System.out.println(game.getName());
                    player.setTurn(1);
                    game.addPlayer(this.player);
                }
                if (request.indexOf("join game") != -1) {
                    String gameName = request.substring("join game ".length());
                    List<Game> tmplist = GameServer.getGames();
                    for (int i = 0; i < tmplist.size(); i++) {
                        if (tmplist.get(i).getName().equals(gameName)) {
                            tmplist.get(i).addPlayer(this.player);
                            this.game = tmplist.get(i);
                        }
                    }

                }
                if (request.indexOf("submit move") != -1 && player.getTurn() != 0) {
                    String tmp = request.substring("submit move ".length());
                    String positions[] = tmp.split(" ");
                    int i = Integer.parseInt(positions[0]);
                    int j = Integer.parseInt(positions[1]);
                    int val = game.getPlayers().indexOf(player) + 1;
                    this.game.getBoard().setCell(i, j, val);
//                    game.getBoard().displayMatrix();

                    System.out.println(game.getBoard().StringMatrix());
                    game.getPlayers().forEach(p -> {
                        p.updateTurn();
                    });

                    if (game.getBoard().checkVictory() == true) {
                        System.out.println(player.getName() + " a castigat!");
                        out.println(player.getName() + " a castigat!");
                        out.flush();
                        game.reset();
                        game.getPlayers().forEach(p->{p.startTimer();});
                    }
                    if (game.getBoard().checkVictory() != true) {
                        game.getPlayers().forEach(p -> {
                            try {
                                PrintWriter writer = new PrintWriter(p.getPlayerSocket().getOutputStream());
                                writer.println(game.getBoard().StringMatrix());
                                writer.flush();
                                p.startTimer();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                }
                if (request.indexOf("name") != -1 && player == null) {
                    String playerName = request.substring("name ".length());
                    player = new Player(playerName, socket, this);
                    System.out.println(player.getName());
                }
                if (request.equals("show")) {
                    System.out.println(player == null);
                    System.out.println(player.getName());
                }

                if (request.equals("exit") == true) {
                    break;
                }
                out.println(response);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
