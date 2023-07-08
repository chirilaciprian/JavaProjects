package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String winner;
    private String name;
    private Board board;
    List<Player> players = new ArrayList<Player>();

    public void addPlayer(Player p) {
        players.add(p);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Game(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void reset() {
        board.initMatrix();
    }
}
