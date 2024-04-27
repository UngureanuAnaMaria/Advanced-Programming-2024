package org.example;
import java.util.*;

public class Player implements Runnable {
    private String name;
    private Game game;
    private boolean running = true;
    private List<Token> tokens = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void run() {
        while (running) {
            List<Token> extractedTokens = game.bag.extractTokens(1);
            if (!extractedTokens.isEmpty()) {
                System.out.println(name + " picked token(s): " + extractedTokens);
                tokens.addAll(extractedTokens);
            } else if (simulation(tokens) == game.n) {
                System.out.println("STOP, player have score n: " + name + " score: " + simulation(tokens));
                //stopGame homework
                running = false;
            } else if (extractedTokens.isEmpty()) {
                System.out.println(name + " score: " + simulation(tokens));
                running = false;
            }
        }

    }

    private int simulation(List<Token> sequence) {
        if (sequence.size() < 2) {
            return 0;
        }

        int score = 0;

        for(Token token : sequence) {
            List<Token> closedSequence = new ArrayList<>();
            int first = token.getFirst();
            int second = token.getSecond();
            closedSequence.add(token);
            for(Token token1 : sequence) {
                int first1 = token1.getFirst();
                int second1 = token1.getSecond();
                if(second1 == first) {
                    closedSequence.add(token1);
                    if(closedSequence.size() > score) {
                        score = closedSequence.size();
                    }
                }
                if(first1 == second) {
                    second = second1;
                    closedSequence.add(token1);
                }
            }
        }
        return score;
    }
}
