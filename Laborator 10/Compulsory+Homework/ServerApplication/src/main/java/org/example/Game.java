package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static int idCounter = 1;
    private int id;
    private List<Player> players;
    private boolean isStarted;
    public int currentPlayerIndex;

    public Game() {
        this.id = idCounter++;
        this.players = new ArrayList<>();
        this.isStarted = false;
        this.currentPlayerIndex = 0;
    }

    public int getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean addPlayer(Player player) {

        if (players.size() < 2) {
            players.add(player);
            if (players.size() == 2) {
                isStarted = true;
            }
            return true;
        }
        return false;
    }


    public boolean isStarted() {
        return isStarted;
    }

    public synchronized String submitMove(Player player, int x, int y) {
        if(!this.isStarted) {
            return "Game not started";
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        if (players.get(currentPlayerIndex) != player) {
            return "Not your turn";
        }

        player.updateRemainingTime();
        if (player.isOutOfTime()) {
            return "You have run out of time! You lose!";
        }

        boolean result = player.getBoard().submitMove(x, y);

        return result ? "Hit" : "Miss";
    }

    public synchronized String placeShip(Player player, int x, int y, int length, boolean horizontal) {
        if(!this.isStarted) {
            return "Game not started";
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        if (players.get(currentPlayerIndex) != player) {
            return "Not your turn";
        }

        player.updateRemainingTime();
        if (player.isOutOfTime()) {
            return "You have run out of time! You lose!";
        }

        boolean result = player.getBoard().placeShip(x, y, length, horizontal);

        return result ? "Ship placed successfully" : "Invalid ship placement";
    }

}
