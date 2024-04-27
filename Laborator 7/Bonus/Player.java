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
    private boolean isSmartPlayer;
    public volatile boolean running = true;

    //using synchronizedList to make the list thread-safe!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private List<Token> tokens = Collections.synchronizedList(new ArrayList<>());

    public Player(String name) {
        this.name = name;
        isSmartPlayer = false;
    }

    public Player(String name, boolean isSmartPlayer) {
        this.name = name;
        this.isSmartPlayer = isSmartPlayer;
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

                    if (isSmartPlayer) {
                        optimizeSequence(tokens);
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
        
        if (isSmartPlayer) {
            Graph graph = GraphBuilder.empty().buildGraph();
            Set<Integer> vertices = new HashSet<>(); //SET ensure uniqueness of vertices!!!!!!!!!!!!!!!!
            for (Token token : tokens) {
                int firstValue = token.getFirst();
                int secondValue = token.getSecond();

                vertices.add(firstValue);
                vertices.add(secondValue);
            }

            for (int vertex : vertices) {
                graph.addVertex(vertex);
            }

            for (Token token : tokens) {
                int firstValue = token.getFirst();
                int secondValue = token.getSecond();
                graph.addEdge(firstValue, secondValue);
            }
            System.out.println(graph);

            if (satisfiesOresCondition(graph, vertices)) {
                System.out.println("The graph satisfies Ore's Condition.");
                List<Token> hamiltonianCycle = findHamiltonianCycle(vertices);
                if (hamiltonianCycle != null) {
                    System.out.println("Hamiltonian Cycle found: " + hamiltonianCycle);
                } else {
                    System.out.println("No Hamiltonian Cycle found.");
                }
            } else {
                System.out.println("The graph does not satisfy Ore's Condition.");
            }
        }
    }

    private List<Token> findHamiltonianCycle(Set<Integer> vertices) {
        if (tokens.size() < 2) {
            return null;
        }

        List<List<Token>> cycles = new ArrayList<>();

        for(Token token : tokens) {
            List<Token> cycle = new ArrayList<>();
            int first = token.getFirst();
            int second = token.getSecond();
            cycle.add(token);
            for(Token tokenn : tokens) {
                if(tokenn != token) {
                    int first1 = tokenn.getFirst();
                    int second1 = tokenn.getSecond();
                    if (second1 == first) {
                        cycle.add(tokenn);
                        cycles.add(cycle);
                    }
                    if (first1 == second) {
                        second = second1;
                        cycle.add(tokenn);
                    }
                }
            }
        }

        for(List<Token> cycle : cycles) {
            boolean isHamiltonianCycle = true;
            for(int vertex : vertices) {
                if(!cycle.contains(vertex)) {
                    isHamiltonianCycle = false;
                }
            }

            if(isHamiltonianCycle) {
                return cycle;
            }
        }
        return null;
    }

    private boolean satisfiesOresCondition(Graph graph, Set<Integer> vertices) {
        int numVertices = vertices.size();
        for (Integer vertex1 : graph.vertices()) {
            for (Integer vertex2 : graph.vertices()) {
                if (!vertex1.equals(vertex2) && !graph.containsEdge(vertex1, vertex2)) {
                    int degree1 = graph.degree(vertex1);
                    int degree2 = graph.degree(vertex2);
                    if (degree1 + degree2 < numVertices) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private synchronized void optimizeSequence(List<Token> sequence) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Token token : sequence) {
            int firstValue = token.getFirst();
            frequencyMap.put(firstValue, frequencyMap.getOrDefault(firstValue, 0) + 1);
        }

        Collections.sort(sequence, (token1, token2) -> {
            int freq1 = frequencyMap.getOrDefault(token1.getFirst(), 0);
            int freq2 = frequencyMap.getOrDefault(token2.getFirst(), 0);
            return Integer.compare(freq2, freq1);
        });
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
