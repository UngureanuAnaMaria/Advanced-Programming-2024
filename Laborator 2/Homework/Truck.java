import java.util.List;
public class Truck extends Vehicle{
    private int capacity;

    public Truck(String name, Depot depot, int capacity) {
          super(name, depot);
          this.capacity = capacity;
    }

    public Truck(String name, Depot depot, List<Client> clients,  int capacity) {
        super(name, depot, clients);
        this.capacity = capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity(){
        return this.capacity;
    }

    @Override
    public String toString() {
        return "Vehicle(Truck) : " + "name = " + getName() + ", depot = " + getDepot() + ", clients = " + getClients() + ", capacity = " + capacity;
    }
    @Override
    public int getCapacityOrFlightDuration() {
        return this.capacity;
    }

    public void setCapacityOrFlightDuration() {
        this.capacity = capacity;
    }
}
