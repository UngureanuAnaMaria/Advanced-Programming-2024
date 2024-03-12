import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle {
    private String name;
    private Depot depot;
    private List<Client> clients;

    public Vehicle(String name, Depot depot) {
        this.name = name;
        this.depot = depot;
        this.clients = new ArrayList<>();
    }

    public Vehicle(String name, Depot depot, List<Client> clients) {
        this.name = name;
        this.depot = depot;
        this.clients = clients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public Depot getDepot() {
        return this.depot;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Client> getClients() {
        return this.clients;
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }

    @Override
    public String toString() {
        return "Vehicle : " + "name = " + getName() + ", depot = " + getDepot() + ", clients = " + getClients();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Vehicle)) return false;
        Vehicle comp = (Vehicle) obj;
        return (comp.name == name && comp.depot == depot && comp.clients == clients);
    }

    public abstract int getCapacityOrFlightDuration();

    public abstract void setCapacityOrFlightDuration();
}
