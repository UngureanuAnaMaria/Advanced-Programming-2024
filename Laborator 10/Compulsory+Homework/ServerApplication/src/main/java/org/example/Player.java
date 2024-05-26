package org.example;

public class Player {
    private String name;
    private Board board;
    private boolean isReady;
    private long remainingTime;
    private long lastMoveTime;

    public Player(String name, long initialTime) {
        this.name = name;
        this.board = new Board();
        this.isReady = false;
        this.remainingTime = initialTime;
        this.lastMoveTime = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isReady() {
        return isReady;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void updateRemainingTime() {
        long currentTime = System.currentTimeMillis();
        remainingTime -= (currentTime - lastMoveTime);
        lastMoveTime = currentTime;
    }

    public boolean isOutOfTime() {
        return remainingTime <= 0;
    }
}
