package org.example;
import java.util.*;
import com.github.javafaker.Faker;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();

        List<Driver> drivers = IntStream.rangeClosed(0, 10)
                .mapToObj(i -> new Driver(faker.name().fullName(), faker.number().numberBetween(20, 70), faker.address().cityName()))
                .toList();

        System.out.println(drivers);

        List<Passenger> passengers = IntStream.rangeClosed(0, 10)
                .mapToObj(i -> new Passenger(faker.name().fullName(), faker.number().numberBetween(20, 70), faker.address().cityName()))
                .toList();

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

        Map<Driver, Passenger> matches = problem.matchDriversAndPassengers(sortedDrivers, sortedPassengers);

        System.out.println("Matches:");
        for (Map.Entry<Driver, Passenger> entry : matches.entrySet()) {
            System.out.println(entry.getKey().getName() + " -> " + entry.getValue().getName());
        }
    }
}
