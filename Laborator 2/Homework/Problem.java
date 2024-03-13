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

    /**
     * This method generate the allocation of clients to vehicles
     * Firstly, for every client I find the nearest vehicle throught the findNearestVehicle method
     * Then I verify if the client is already allocated in that or another car
     * If the client isn't allocated and the vehicle isn't null then I alocate the client to nearestVehicle and I add that client to the list of client for that vehicle
     */
    
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

    /**
     * Greedy algoritm
     * For that specific client I find the nearest vehicle
     * Firstly, I initiate MinDistance with Integer.MAX_VALUE and nearestVehicle with null
     * I look throught the list of vehicles and I generate a random distance between [Integer.MIN_VALUE, Integer.MAX_VALUE]
     * If that distance is less than minDistance, then I modify minDistance and nearestVehicle
     * @param client
     * @return nearest vehicle
     */
    
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
