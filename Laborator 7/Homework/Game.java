package org.example;
import java.util.*;

public class Game {
    public int n;
    public Bag bag;
    private final List<Player> players = new ArrayList<>();
    private  List<Thread> threads = new ArrayList<>();
    private Player winner = null;
    private volatile int currentPlayerIndex = 0;

    public Game(int n) {
        this.n = n;
        bag = new Bag(n);
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }

    public synchronized Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public synchronized void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void play() {
        long timeLimitMillis = 30;//30 mili sec, also 18 works
        Thread timeKeeperThread = new Thread(new TimeKeeper(this, timeLimitMillis));
        timeKeeperThread.setDaemon(true);//set thread as daemon
        timeKeeperThread.start();

        for (Player player : players) {
            Thread thread = new Thread(player);
            threads.add(thread);
            thread.start();
        }
    }

    public synchronized void endGame() {
        for (Player player : players) {
            player.running = false;
        }

        for (Thread thread : threads) {
            thread.interrupt();
        }

        getWinner();
    }


    public synchronized void getWinner() {
        int score = 0;
        if(winner == null) {
            for (Player player : players) {
                winner = player;
                score = player.getScore();
                break;
            }

            for (Player player : players) {
                if (player.getScore() > score) {
                    winner = player;
                    score = player.getScore();
                }
            }
        }
        System.out.println("Winner is: " + winner.name + " score: " + winner.getScore());
    }

    public synchronized boolean isGameFinished() {
        for(Player player : players) {
            if(player.getScore() == n) {
                winner = player;
                return true;
            }
        }
        return false;
    }

    public static void main(String args[]) {
        Game game = new Game(5);
        game.addPlayer(new Player("Player 1"));
        game.addPlayer(new Player("Player 2"));
        game.addPlayer(new Player("Player 3"));
        game.addPlayer(new Player("Player 4"));
        game.addPlayer(new Player("Player 5"));
        game.play();
    }
}
