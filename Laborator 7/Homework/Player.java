//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//We use the notify() method for waking up threads that are waiting for access to this object’s monitor.
//The wait() method causes the current thread to wait indefinitely until another thread either invokes notify() for this object or notifyAll().
//The volatile keyword in Java is used to indicate that a variable's value may be modified by different threads.
//It ensures that any thread that reads the variable sees the most recent write by any other thread.
package org.example;
import java.util.*;
import org.graph4j.*;
import org.graph4j.GraphBuilder;

public class Player implements Runnable {
    public String name;
    private Game game;
    public volatile boolean running = true;

    //using synchronizedList to make the list thread-safe!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private List<Token> tokens = Collections.synchronizedList(new ArrayList<>());

    public Player(String name) {
        this.name = name;
        isSmartPlayer = false;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void run() {
        while (running && !game.isGameFinished()) {
            Player currentPlayer;
            //The synchronized block ensures that only one thread at a time can execute the code!!!!!!!!!!!!!!!!!!!!!!
            synchronized (game) {
                currentPlayer = game.getCurrentPlayer();
            }

            if (currentPlayer == this) {
                List<Token> extractedTokens = game.bag.extractTokens(1);
                if (!extractedTokens.isEmpty()) {
                    System.out.println(name + " picked token(s): " + extractedTokens);
                    synchronized (tokens) {
                        tokens.addAll(extractedTokens);
                    }
                } else {
                    running = false;
                }
                synchronized (game) {
                    game.nextPlayer();
                }
            }
        }
        game.getWinner();
        System.out.println(name + " score: " + getScore());
    }

    private int simulation(List<Token> sequence) {
        if (sequence.size() < 2) {
            return 0;
        }

        int score = 0;

        List<List<Token>> sequences = new ArrayList<>();

        for(Token token : sequence) {
            List<Token> closedSequence = new ArrayList<>();
            int first = token.getFirst();
            int second = token.getSecond();
            closedSequence.add(token);
            for(Token tokenn : sequence) {
                if(tokenn != token) {
                    int first1 = tokenn.getFirst();
                    int second1 = tokenn.getSecond();
                    if (second1 == first) {
                        closedSequence.add(tokenn);
                        sequences.add(closedSequence);
                        if (closedSequence.size() > score) {
                            score = closedSequence.size();
                        }
                    }
                    if (first1 == second) {
                        second = second1;
                        closedSequence.add(tokenn);
                    }
                }
            }
        }

        for(List<Token> validSequence : sequences) {
            if(validSequence.size() > score) {
                score = validSequence.size();
            }
        }
        return score;
    }

    public int getScore() {
        synchronized (tokens) {
            return simulation(tokens);
        }
    }
}
