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
        /*
        k=0;
        for(Depot depot : depots)
           System.out.println(depotsLocation[k++]);
        k=0;
        for(Vehicle vehicle : vehicles)
             System.out.println(vehiclesLocation[k++]);
        */

        int[] clientsLocation = new int[clients.size()];
        k = 0;
        for(Client client : clients) {
            clientsLocation[k] = (int) Math.floor(Math.random() * (matrixLength + 1));
            k++;
        }

        //System.out.println(clientsLocation);

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
        /*
        k=0;
        for(Vehicle vehicle : vehicles)
            System.out.println(vehiclesLocation[k++]);
        */

        printMap(clientsLocation, vehiclesLocation, depotsLocation, m, n);

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
        /*
        k=0;
        for(Vehicle vehicle : vehicles)
            System.out.println(vehiclesLocation[k++]);
        */
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
                    //shortestPath[i][j] = (costMatrix[i][j] == 0) ? Integer.MAX_VALUE : costMatrix[i][j];

                    //!!!!!!!!!!!!!!!!!!!!!!!!!
                    //Improvement
                    shortestPath[i][j] = (costMatrix[i][j] == 0) ? 0 : costMatrix[i][j];
                }
    }

    public void calculateAllShortestPaths(int m, int n) {
        int matrixLength = costMatrix.length;

        initializeShortestPath();
        /*
        //Floyd-Warshall algorithm implementation(O(matrixLength^3))
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
        */

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //Improvement(O(matrixLength^(3/2)))
        //grid graph with all weight 1 on edges
        int index;
        for (int i = 0; i <= matrixLength/2 + 1; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if(j > m && shortestPath[i][j - 1] == m) index = -1;
                else if(j % m == 0 && j != 0 && i == j - 1) index = m;
                else index = 1;
                if(shortestPath[i][j] == 0 && i != j && j != 0) {
                    shortestPath[i][j] = shortestPath[i][j - 1] + index;
                    shortestPath[j][i] = shortestPath[i][j];
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

    //Textual representation of the map using Unicode symbols
    private void printMap(int[] clientsLocation, int[] vehiclesLocation, int [] depotsLocation, int m, int n) {
        // Top left corner
        System.out.print("\u250c ");

        int dd = 0, vv = 0, cc = 0;
        // Print top row
        for (int i = 0; i < m; i++) {
            int d = 0, v = 0, c = 0;
            int okDepot = 0;
            for(Depot depot : depots) {
                if (depotsLocation[d] == i) {
                    System.out.print("D" + dd + "  ");
                    okDepot = 1;
                    dd++;
                    depotsLocation[d] = -1;
                }
                d++;
            }

            int okVehicle = 0;
            if(okDepot == 0) {
                for (Vehicle vehicle : vehicles) {
                    if (vehiclesLocation[v] == i) {
                        System.out.print("V" + vv + "  ");
                        okVehicle = 1;
                        vv++;
                        vehiclesLocation[v] = -1;
                    }
                    v++;
                }
            }

            int okClient = 0;
            if(okDepot == 0 && okVehicle == 0) {
                for(Client client : clients) {
                    if (clientsLocation[c] == i) {
                        System.out.print("C" + cc + "  ");
                        okClient = 1;
                        cc++;
                        clientsLocation[c] = -1;
                    }
                    c++;
                }
            }

            if(okClient == 0 && okVehicle == 0 && okDepot == 0) {
                System.out.print("    ");
            }
        }
        //Top right corner
        System.out.print("\u2510");

        System.out.println();
        // Print middle rows
        for (int i = 1; i < n - 1; i++) {

            System.out.println("\u2523 ");

            for (int j = 0; j < m; j++) {
                int d = 0, v = 0, c = 0;
                int okDepot = 0;
                for(Depot depot : depots) {
                    if (depotsLocation[d] == j) {
                        System.out.print("D" + dd + "  ");
                        okDepot = 1;
                        dd++;
                        depotsLocation[d] = -1;
                    }
                    d++;
                }

                int okVehicle = 0;
                if(okDepot == 0) {
                    for (Vehicle vehicle : vehicles) {
                        if (vehiclesLocation[v] == j) {
                            System.out.print("V" + vv + "  ");
                            okVehicle = 1;
                            vv++;
                            vehiclesLocation[v] = -1;
                        }
                        v++;
                    }
                }

                int okClient = 0;
                if(okDepot == 0 && okVehicle == 0) {
                    for(Client client : clients) {
                        if (clientsLocation[c] == j) {
                            System.out.print("C" + cc + "  ");
                            okClient = 1;
                            cc++;
                            clientsLocation[c] = -1;
                        }
                        c++;
                    }
                }

                if(okClient == 0 && okVehicle == 0 && okDepot == 0) {
                    System.out.print("    ");
                }
            }

            System.out.print("\u252b");
            System.out.println();
        }

        // Print bottom row
        System.out.print("\u2517 ");

        for (int i = 0; i < m; i++) {
            int d = 0, v = 0, c = 0;
            int okDepot = 0;
            for(Depot depot : depots) {
                if (depotsLocation[d] == i) {
                    System.out.print("D" + dd + "  ");
                    okDepot = 1;
                    dd++;
                    depotsLocation[d] = -1;
                }
                d++;
            }

            int okVehicle = 0;
            if(okDepot == 0) {
                for (Vehicle vehicle : vehicles) {
                    if (vehiclesLocation[v] == i) {
                        System.out.print("V" + vv + "  ");
                        okVehicle = 1;
                        vv++;
                        vehiclesLocation[v] = -1;
                    }
                    v++;
                }
            }

            int okClient = 0;
            if(okDepot == 0 && okVehicle == 0) {
                for(Client client : clients) {
                    if (clientsLocation[c] == i) {
                        System.out.print("C" + cc + "  ");
                        okClient = 1;
                        cc++;
                        clientsLocation[c] = -1;
                    }
                    c++;
                }
            }

            if(okClient == 0 && okVehicle == 0 && okDepot == 0) {
                System.out.print("    ");
            }
        }

        System.out.println("\u251b");
    }
}

