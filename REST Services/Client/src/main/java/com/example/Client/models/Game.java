package com.example.Client.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Game {
    @Id
    private Long id;
    private String name;
    private String player1Name;
    private String player2Name;

    public Game() {
    }

    public Game(String name, String player1Name, String player2Name) {
        this.name = name;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }
}
