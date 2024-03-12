import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Depot depot1 = new Depot("Depot A");
        Depot depot2 = new Depot("Depot B");

        Vehicle vehicle1 = new Truck("BMW", depot1, 4);
        depot1.setVehicles(vehicle1);
        //System.out.println(vehicle1.getCapacityOrFlightDuration());
        Client client1 = new Client("Popescu Ion", 10, 11, ClientType.REGULAR);
        Client client2 = new Client("Ionescu George", 14, 15, ClientType.PREMIUM);
        Client client3 = new Client("Vasilescu George", 9, 15, ClientType.PREMIUM);
        Client client4  = new Client("Vasiliu George", 9, 15, ClientType.PREMIUM);

        vehicle1.addClient(client1);

        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);

        Vehicle vehicle2 = new Drone("Audi", depot2, clients, 100);
        //System.out.println(vehicle2.getCapacityOrFlightDuration());
        depot2.setVehicles(vehicle2);
        Problem problem = new Problem();

        problem.addDepot(depot1);
        problem.addDepot(depot2);
        problem.addClient(client1);
        problem.addClient(client2);
        problem.addClient(client3);
        problem.addClient(client4);
        problem.addVehicle(vehicle1);
        problem.addVehicle(vehicle2);

        Vehicle[] vehicles = problem.getVehicles();

        for(Vehicle vehicle : vehicles)
            System.out.println(vehicle);

        problem.allocateClientsToVehicles();

        Solution solution = new Solution();

        Map<Client, Vehicle> allocation = problem.getAllocationProblem();
        solution.setAllocation(allocation);

        System.out.println();
        System.out.println("ALLOCATION : ");

        solution.printAllocation();
    }
}

