package org.example;

public class Token {
    private final int number;

    public Token(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String toString() {
        return String.valueOf(number);
    }
}