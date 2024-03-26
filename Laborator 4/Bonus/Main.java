package org.example;
import java.util.*;
import com.github.javafaker.Faker;
import java.util.stream.IntStream;
import org.graph4j.Graph;
//import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
//import org.jgrapht.alg.independentset.ChordalGraphIndependentSetFinder;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.matching.DenseEdmondsMaximumCardinalityMatching;
import org.graph4j.alg.matching.HopcroftKarpMaximumMatching;
import org.graph4j.GraphBuilder;

public class Main {
    public static void main(String[] args) {

        int n = 5000;
        double edgeProbability = 0.1;//prob having a edge between a driver and a passenger

        List<Driver> drivers = generateRandomDrivers(n);
        List<Passenger> passengers = generateRandomPassengers(n);

        System.out.println(drivers);
        System.out.println(passengers);

        List<Driver> sortedDrivers = new LinkedList<>(drivers);
        //Collections.sort(sortedDrivers, Comparator.comparing(Person::getAge));
        Collections.sort(sortedDrivers, ((person1, person2) -> person1.compareTo(person2)));
        sortedDrivers.forEach(System.out::println);

        Set<Passenger> sortedPassengers = new TreeSet<>(Comparator.comparing(Person::getName));
        sortedPassengers.addAll(passengers);
        sortedPassengers.forEach(System.out::println);

        Problem problem = new Problem(sortedDrivers, sortedPassengers);
        problem.computeDestinationMap();
        System.out.println("\nDestinations passed by drivers: " + problem.getDestinationsPassedByDrivers());
        System.out.println("\nDestination map: \n" );
        Map<String, List<Person>> destinationMap = problem.getDestinationMap();
        for (Map.Entry<String, List<Person>> entry : destinationMap.entrySet()) {
            String destination = entry.getKey();
            List<Person> people = entry.getValue();
            System.out.println("Destination: " + destination);
            for (Person person : people) {
                System.out.println(person);
            }
            System.out.println();
        }

        long startTimeGreedy = System.currentTimeMillis();
        Map<Driver, Passenger> matches = problem.matchDriversAndPassengers(sortedDrivers, sortedPassengers);
        long endTimeGreedy = System.currentTimeMillis();

        System.out.println("Matches:");
        for (Map.Entry<Driver, Passenger> entry : matches.entrySet()) {
            System.out.println(entry.getKey().getName() + " -> " + entry.getValue().getName());
        }

        org.jgrapht.graph.SimpleGraph<Node, DefaultEdge> jgraphtGraph = createBipartiteGraphJGraphT(sortedDrivers,passengers);
        Graph graph4jGraph = createBipartiteGraphGraph4J(sortedDrivers, passengers);


        //find maximum cardinality matching using JGraphT
        long startTimeJGraphT = System.currentTimeMillis();
        DenseEdmondsMaximumCardinalityMatching<Node, DefaultEdge> matchingJGraphT = new DenseEdmondsMaximumCardinalityMatching<>(jgraphtGraph);
        long endTimeJGraphT = System.currentTimeMillis();
        System.out.println(matchingJGraphT.getMatching().toString());

        //find maximum cardinality matching using Graph4J
        long startTimeGraph4J = System.currentTimeMillis();
        HopcroftKarpMaximumMatching matchingGraph4J = new HopcroftKarpMaximumMatching(graph4jGraph);
        long endTimeGraph4J = System.currentTimeMillis();
        System.out.println(matchingGraph4J.getMatching().toString());

        //compare execution times
        System.out.println("Execution time using JGraphT: " + (endTimeJGraphT - startTimeJGraphT) + " milliseconds");
        System.out.println("Execution time using Graph4J: " + (endTimeGraph4J - startTimeGraph4J) + " milliseconds");
        System.out.println("Execution time using Greedy: " + (endTimeGreedy - startTimeGreedy) + " milliseconds");
    }

    public static List<Driver> generateRandomDrivers(int count) {
        Faker faker = new Faker();
        List<Driver> drivers = IntStream.rangeClosed(0, count - 1)
                .mapToObj(i -> new Driver(faker.name().fullName(), faker.number().numberBetween(20, 70), faker.address().cityName()))
                .toList();
        return drivers;
    }

    public static List<Passenger> generateRandomPassengers(int count) {
        Faker faker = new Faker();
        List<Passenger> passengers = IntStream.rangeClosed(0, count - 1)
                .mapToObj(i -> new Passenger(faker.name().fullName(), faker.number().numberBetween(20, 70), faker.address().cityName()))
                .toList();
        return passengers;
    }

    public static org.jgrapht.graph.SimpleGraph<Node, DefaultEdge> createBipartiteGraphJGraphT(List<Driver> drivers, List<Passenger> passengers) {
        org.jgrapht.graph.SimpleGraph<Node, DefaultEdge> bipartiteGraph = new SimpleGraph<>(DefaultEdge.class);

        for (Driver driver : drivers) {
            bipartiteGraph.addVertex(driver);
        }

        for (Passenger passenger : passengers) {
            bipartiteGraph.addVertex(passenger);
        }

        for (Driver driver : drivers) {
            for (Passenger passenger : passengers) {
                if (Math.random() < 0.1) {
                    bipartiteGraph.addEdge(driver, passenger);
                }
            }
        }
        return bipartiteGraph;
    }

    public static org.graph4j.Graph createBipartiteGraphGraph4J(List<Driver> drivers, List<Passenger> passengers) {
        org.graph4j.Graph graph = GraphBuilder.empty().buildGraph();
        for (Driver driver : drivers) {
            graph.addVertex(driver);
        }
        for (Passenger passenger : passengers) {
            graph.addVertex(passenger);
        }
        for (Driver driver : drivers) {
            for (Passenger passenger : passengers) {
                if (Math.random() < 0.1) {
                    graph.addEdge(driver, passenger);
                }
            }
        }
        return graph;
    }
}
