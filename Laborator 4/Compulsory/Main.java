package org.example;
import com.yourcompany.model.Person;
import java.util.*;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Alice", 25, "Destination A"));
        persons.add(new Person("Bob", 30, "Destination B"));
        persons.add(new Person("Charlie", 40, "Destination C"));
        persons.add(new Person("David", 35, "Destination D"));
        persons.add(new Person("Frank", 22, "Destination A"));
        persons.add(new Person("Emily", 23, "Destination B"));

        List<Person> drivers = persons.stream()
                .filter(person -> person.getAge() >= 25)
                .toList();

        List<Person> passengers = persons.stream()
                .filter(person -> person.getAge() < 25)
                .toList();

        List<Person> sortedDrivers = new LinkedList<>(drivers);
        Collections.sort(sortedDrivers, Comparator.comparing(Person::getAge));
        //Collections.sort(sortedDrivers, ((person1, person2) -> person1.compareTo(person2)));
        sortedDrivers.forEach(System.out::println);

        Set<Person> sortedPassengers = new TreeSet<>(Comparator.comparing(Person::getName));
        sortedPassengers.addAll(passengers);
        sortedPassengers.forEach(System.out::println);

    }
}
