package org.example;

public class Driver extends Person {
    private String destination;

    public Driver(String name, int age, String destination) {
        super(name, age);
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", destination='" + destination + '\'' +
                '}';
    }

    public int compareTo(Driver p) {
        return Integer.compare(this.getAge(), p.getAge());
    }
}


