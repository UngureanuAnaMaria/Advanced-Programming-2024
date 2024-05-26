package org.example;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private ServerSocket serverSocket;
    private boolean running = true;
    private List<Game> games;

    public GameServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        games = new ArrayList<>();
    }


    public void start() {
        System.out.println("Game Server started...");

        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();//acc conn Y/N
                System.out.println("Client connected...");
                new ClientThread(clientSocket, this).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized Game createGame() {
        Game game = new Game();
        games.add(game);
        return game;
    }

    public synchronized Game joinGame(Player player) {
        for (Game game : games) {
            if (!game.isStarted() && game.addPlayer(player)) {
                return game;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            GameServer server = new GameServer(1234);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
