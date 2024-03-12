import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Problem {
    private List<Client> clients;
    private List<Vehicle> vehicles;
    private List<Depot> depots;
    private Map<Client, Vehicle> allocation;
    private int[][] costMatrix;
    private int m;
    private int n;
    private int[][] shortestPath;

    public Problem(List<Client> clients, List<Vehicle> vehicles, List<Depot> depots, Map<Client, Vehicle> allocation, int[][] costMatrix, int m, int n) {
        this.depots = depots;
        this.vehicles = vehicles;
        this.clients = clients;
        this.allocation = allocation;
        this.costMatrix = costMatrix;
        this.shortestPath = new int[m*n][m*n];
        this.m = m;
        this.n = n;
    }

    public Problem(int m, int n, int[][] costMatrix) {
        this.depots = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.allocation = new HashMap<>();
        this.costMatrix = costMatrix;
        this.shortestPath = new int[m*n][m*n];
        this.m = m;
        this.n = n;
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

    public Depot[] getDepots() {
        return depots.toArray(new Depot[0]);
    }

    public Client[] getClients() {
        return clients.toArray(new Client[0]);
    }

    public Vehicle[] getVehicles(){
        return vehicles.toArray(new Vehicle[0]);
    }

    public void allocateClientsToVehicles() {
        //sort StartTime
        int matrixLength = costMatrix.length - 1;

        Vehicle nearestVehicle;

        int[] depotsLocation = new int[depots.size()];
        int[] vehiclesLocation = new int[vehicles.size()];
        int k = 0;
        int w = 0;
        for(Depot depot : depots) {
            depotsLocation[k] = (int) Math.floor(Math.random() * (matrixLength + 1));
            Vehicle[] vehicleForEveryDepot = depot.getVehicles();
            for(Vehicle vehicle : vehicleForEveryDepot) {
                vehiclesLocation[w] = depotsLocation[k];
                w++;
            }
            k++;
        }

        k=0;
        for(Depot depot : depots)
           System.out.println(depotsLocation[k++]);
        k=0;
        for(Vehicle vehicle : vehicles)
             System.out.println(vehiclesLocation[k++]);

        int[] clientsLocation = new int[clients.size()];
        k = 0;
        for(Client client : clients) {
            clientsLocation[k] = (int) Math.floor(Math.random() * (matrixLength + 1));
            k++;
        }

        System.out.println(clientsLocation);

        k = 0;
        for(Client client : clients) {
            nearestVehicle = findNearestVehicle(client, clientsLocation, k, vehiclesLocation);

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
            k++;
        }

        k=0;
        for(Vehicle vehicle : vehicles)
            System.out.println(vehiclesLocation[k++]);

        k = 0;
        w = 0;
        for(Depot depot : depots) {
            Vehicle[] vehicleForEveryDepot = depot.getVehicles();
            for(Vehicle vehicle : vehicleForEveryDepot) {
                vehiclesLocation[w] = depotsLocation[k];
                w++;
            }
            k++;
        }

        k=0;
        for(Vehicle vehicle : vehicles)
            System.out.println(vehiclesLocation[k++]);

    }

    private Vehicle findNearestVehicle(Client client, int[] clientsLocation, int k, int[] vehiclesLocation) {
        int matrixLength = costMatrix.length - 1;
        int minDistance = Integer.MAX_VALUE;
        Vehicle nearestVehicle = null;
        int w = 0;
        for(Vehicle vehicle : vehicles) {
            if(shortestPath[vehiclesLocation[w]][clientsLocation[k]] < minDistance) {
                minDistance = shortestPath[vehiclesLocation[w]][clientsLocation[k]];
                nearestVehicle = vehicle;
                vehiclesLocation[w] = clientsLocation[k];
            }
            w++;
        }
        return nearestVehicle;
    }

    public Map<Client, Vehicle> getAllocationProblem() {
        return allocation;
    }

    public void initializeShortestPath() {
        int matrixLength = costMatrix.length;
        for(int i = 0; i < matrixLength; i++)
            for(int j = 0; j < matrixLength; j++)
                if(i==j) {
                    shortestPath[i][j] = 0;
                }
                else {
                    shortestPath[i][j] = (costMatrix[i][j] == 0) ? Integer.MAX_VALUE : costMatrix[i][j];
                }
    }

    public void calculateAllShortestPaths(int m, int n) {
        int matrixLength = costMatrix.length;

        initializeShortestPath();

        //Floyd-Warshall algorithm implementation
        for (int k = 0; k < matrixLength; k++) {
            for (int i = 0; i < matrixLength; i++) {
                for (int j = 0; j < matrixLength; j++) {
                    if(shortestPath[i][k]!= Integer.MAX_VALUE && shortestPath[k][j] != Integer.MAX_VALUE &&
                            (shortestPath[i][k] + shortestPath[k][j]) < shortestPath[i][j]) {
                        shortestPath[i][j] = shortestPath[i][k] + shortestPath[k][j];
                    }
                }
            }
        }
    }

    public void printShortestPathMatrix() {
        System.out.println("Shortest Paths Matrix:");
        int matrixLength = costMatrix.length;
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if (shortestPath[i][j] == Integer.MAX_VALUE) {
                    System.out.print("INF  ");
                } else {
                    System.out.print(shortestPath[i][j]+ "  ");
                }
            }
            System.out.println();
        }
    }
}

