package org.example;

import java.net.*;
import java.io.*;

public class ClientThread extends Thread {
    private Socket socket;
    private GameServer server;
    private Player player;
    private Game game;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String request;
            while ((request = in.readLine()) != null) {
                if (request.equals("stop")) {
                    server.stop();
                    out.println("Server stopped");
                    break;
                } else if (request.equals("create game")) {
                    game = server.createGame();
                    player = new Player("Player1", 300000);
                    game.addPlayer(player);
                    out.println("Game created with ID: " + game.getId());
                } else if (request.equals("join game")) {
                    game = server.joinGame(new Player("Player2", 300000));
                    if (game != null) {
                        out.println("Joined game with ID: " + game.getId());
                    } else {
                        out.println("No available games to join");
                    }
                } else if (request.startsWith("place ship")) {
                    //place ship x y l h
                    String[] parts = request.split(" ");
                    int x = Integer.parseInt(parts[2]);
                    int y = Integer.parseInt(parts[3]);
                    int length = Integer.parseInt(parts[4]);
                    boolean horizontal = Boolean.parseBoolean(parts[5]);
                    String result = game.placeShip(player, x, y, length, horizontal);
                    out.println("Place result: " + result);
                } else if (request.startsWith("submit move")) {
                    //submit move x y
                    String[] parts = request.split(" ");
                    int x = Integer.parseInt(parts[2]);
                    int y = Integer.parseInt(parts[3]);
                    //boolean hit = player.getBoard().submitMove(x, y);
                    String result = game.submitMove(player, x, y);
                    out.println("Move result: " + result);
                } else if (request.startsWith("show board")) {
                    char[][] grid = player.getBoard().getGrid();
                    for(int i = 0; i < 10; i++) {
                        for(int j = 0; j < 10; j++) {
                            out.println(grid[i][j] + " ");
                        }
                        out.println("/n");
                    }
                } else {
                    out.println("Server received the request: " + request);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
