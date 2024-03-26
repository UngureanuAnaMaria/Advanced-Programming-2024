package org.example;
import java.util.*;

public class Problem {
    private List<Driver>  sortedDrivers;
    private Set<Passenger> sortedPassengers;
    private Map<String, List<Person>> destinationMap;


    public Problem(List<Driver> sortedDrivers, Set<Passenger> sortedPassengers) {
        this.sortedDrivers = sortedDrivers;
        this.sortedPassengers = sortedPassengers;
        this.destinationMap = new HashMap<>();
    }

    public List<String> getDestinationsPassedByDrivers() {
        return sortedDrivers.stream()
                .map(Driver::getDestination)
                .distinct()
                .toList();
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void computeDestinationMap() {
        destinationMap.clear(); //clear the map to avoid duplicates

        for (Passenger passenger : sortedPassengers) {
            destinationMap.computeIfAbsent(passenger.getDestination(), key -> new ArrayList<>()).add(passenger);
        }

        for (Driver driver : sortedDrivers) {
            destinationMap.computeIfAbsent(driver.getDestination(), key -> new ArrayList<>()).add(driver);
        }

        for (List<Person> people : destinationMap.values()) {
            people.sort(Comparator.comparing(Person::getName));
        }
    }

    public Map<String, List<Person>> getDestinationMap() {
        return destinationMap;
    }

    public  Map<Driver, Passenger> matchDriversAndPassengers(List<Driver> drivers, Set<Passenger> passengers) {
        Collections.sort(drivers, Comparator.comparing(Driver::getDestination));

        Map<Driver, Passenger> matches = new HashMap<>();
        for (Driver driver : drivers) {
            String driverDestination = driver.getDestination();
            Passenger matchedPassenger = null;

            for (Passenger passenger : passengers) {
                if (passenger.getDestination().equals(driverDestination)) {
                    matchedPassenger = passenger;
                    break;
                }
            }

            if (matchedPassenger != null) {
                matches.put(driver, matchedPassenger);
                passengers.remove(matchedPassenger); // Remove the passenger from the list of unmatched passengers
            }
        }

        return matches;
    }
}
