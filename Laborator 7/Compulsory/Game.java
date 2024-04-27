package org.example;
import java.util.*;

public class Game {
    public int n;
    public Bag bag;
    private final List<Player> players = new ArrayList<>();

    public Game(int n) {
        this.n = n;
        bag = new Bag(n);
    }
    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }
    public void play() {
        for (Player player : players) {
            Thread thread = new Thread(player);
            thread.start();
            //new Thread(player).start();
        }
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
