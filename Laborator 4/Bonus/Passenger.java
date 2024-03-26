package org.example;

public class Passenger extends Person implements Node {
    private String destination;

    public Passenger(String name, int age, String destination) {
        super(name, age);
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", destination='" + destination + '\'' +
                '}';
    }
}
