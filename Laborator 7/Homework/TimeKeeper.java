package org.example;

public class TimeKeeper implements Runnable {
    private final Game game;
    private final long timeLimitMillis;

    public TimeKeeper(Game game, long timeLimitMillis) {
        this.game = game;
        this.timeLimitMillis = timeLimitMillis;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(timeLimitMillis);
        } catch (InterruptedException e) {
            return;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Game time limit exceeded: " + (elapsedTime / 10) + " milliseconds");
        if(elapsedTime / 10 != 0) {
            game.endGame();
        }
    }
}

