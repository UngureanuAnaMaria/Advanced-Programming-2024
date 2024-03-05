import java.util.List;
import java.util.ArrayList;
public class Vehicle {
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
        return "Vehicle : " + "name = " + getName() +", depot = " + getDepot() + ", clients = " + getClients();
    }
}
