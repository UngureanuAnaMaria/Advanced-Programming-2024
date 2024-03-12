import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Problem {
    private List<Client> clients;
    private List<Vehicle> vehicles;
    private List<Depot> depots;
    private Map<Client, Vehicle> allocation;

    public Problem(List<Client> clients, List<Vehicle> vehicles, List<Depot> depots, Map<Client, Vehicle> allocation) {
         this.depots = depots;
         this.vehicles = vehicles;
         this.clients = clients;
         this.allocation = allocation;
    }

    public Problem() {
        this.depots = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.allocation = new HashMap<>();
    }

    public void addDepot(Depot depot) {
        if(!depots.equals(depot))
            depots.add(depot);
    }

    public void addVehicle(Vehicle vehicle) {
        if(!vehicles.equals(vehicle))
            vehicles.add(vehicle);
    }

    public void addClient(Client client) {
        if(!clients.equals(client))
            clients.add(client);
    }

    public Vehicle[] getVehicles(){
        return vehicles.toArray(new Vehicle[0]);
    }

    public void allocateClientsToVehicles() {
    //sort StartTime
        Vehicle nearestVehicle;
        for(Client client : clients) {
            nearestVehicle = findNearestVehicle(client);

            int ok = 1;
            for(Vehicle vehicle : vehicles) {
                List<Client> clientList = vehicle.getClients();
                for(Client clientFromList : clientList)
                    if (client.equals(clientFromList))
                        ok = 0;
            }

            if (nearestVehicle != null && ok == 1) {
                allocation.put(client, nearestVehicle);
                nearestVehicle.addClient(client);
            }
        }

    }

    private Vehicle findNearestVehicle(Client client) {
        double minDistance = Integer.MAX_VALUE;//max distance
        Vehicle nearestVehicle = null;
        for(Vehicle vehicle : vehicles) {
            int distance = (int)Math.floor(Math.random()*(Integer.MAX_VALUE - Integer.MIN_VALUE + 1) + Integer.MIN_VALUE);//Math.floor(Math.random() *(max - min + 1) + min)
            if(distance < minDistance) {
                minDistance = distance;
                nearestVehicle = vehicle;
            }
        }
        return nearestVehicle;
    }

    public Map<Client, Vehicle> getAllocationProblem() {
        return allocation;
    }
}
