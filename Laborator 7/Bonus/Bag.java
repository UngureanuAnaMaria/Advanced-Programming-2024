package org.example;
import java.util.*;

public class Bag {
    private List<Token> tokens;

    public Bag(int n) {
        tokens = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                if(i != j) {
                    tokens.add(new Token(i, j));
                }
            }
        }
    }
    public synchronized List<Token> extractTokens(int howMany) {
        List<Token> extracted = new ArrayList<>();
        int numberExtracted = 0;
        for (Token token : tokens) {
            if(numberExtracted == howMany) {
                break;
            }
            if (tokens.isEmpty()) {
                break;
            }
            extracted.add(token);
            numberExtracted++;
        }
        tokens.removeAll(extracted);
        return extracted;
    }
}
