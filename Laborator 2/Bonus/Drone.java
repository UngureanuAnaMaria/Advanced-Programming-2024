import java.util.List;
public class Drone extends Vehicle {
    private int flightDuration;

    public Drone(String name, Depot depot, int flightDuration) {
        super(name, depot);
        this.flightDuration = flightDuration;
    }

    public Drone(String name, Depot depot, List<Client> clients, int flightDuration) {
        super(name, depot, clients);
        this.flightDuration = flightDuration;
    }

    public void setFlightDuration(int flightDuration) {
        this.flightDuration = flightDuration;
    }

    public int getFlightDuration(){
        return this.flightDuration;
    }

    @Override
    public String toString() {
        return "Vehicle(Drone) : " + "name = " + getName() + ", depot = " + getDepot() + ", clients = " + getClients() + ", flight duration = " + flightDuration;
    }

    public int getCapacityOrFlightDuration() {
        return this.flightDuration;
    }

    public void setCapacityOrFlightDuration() {
        this.flightDuration = flightDuration;
    }
}

