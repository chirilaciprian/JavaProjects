package org.example;

import java.util.ArrayList;
import java.util.*;

public class SharedMemory {
    public List<Token> getTokens() {
        return tokens;
    }

    private final List<Token> tokens = new ArrayList<>();
    public SharedMemory(int n) {
        for(int i=0;i<n*n*n;i++)
                tokens.add(new Token(i+1));

        Collections.shuffle(tokens);
    }
    public synchronized List<Token> extractTokens(int howMany) {
        List<Token> extracted = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            if (tokens.isEmpty()) {
                break;
            }
            extracted.add(tokens.get(i));
            tokens.remove(i);
        }
        return extracted;
    }
}
