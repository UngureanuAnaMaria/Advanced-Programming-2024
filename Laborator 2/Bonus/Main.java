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

        /*
        int[][] costMatrix = {
                {0, 1, 0, 2, 0, 0},
                {1, 0, 4, 0, 5, 0},
                {0, 2, 0, 0, 0, 3},
                {1, 0, 0, 0, 6, 0},
                {0, 2, 0, 6, 0, 3},
                {0, 0, 1, 0, 2, 0}
        };
        */

        //for improvement
        int[][] costMatrix = {
                {0, 1, 0, 1, 0, 0},
                {1, 0, 1, 0, 1, 0},
                {0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1},
                {0, 0, 1, 0, 1, 0}
        };

        int m = 3, n = 2;

        Problem problem = new Problem(m, n, costMatrix);

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

        problem.calculateAllShortestPaths(m, n);
        problem.printShortestPathMatrix();

        problem.allocateClientsToVehicles();

        Solution solution = new Solution();

        Map<Client, Vehicle> allocation = problem.getAllocationProblem();
        solution.setAllocation(allocation);

        System.out.println();
        System.out.println("ALLOCATION : ");

        solution.printAllocation();



        int randomM = 0, randomN = 0;
        m = (int)Math.floor(Math.random()*(10 - 2 + 1) + 2);//m at least 2
        n = (int)Math.floor(Math.random()*(10 - 2 + 1) + 2);//n at least 2
        System.out.println();
        System.out.println(m);
        System.out.println();
        System.out.println(n);
        System.out.println();
        int[][] randomCostMatrix = new int[m * n][m * n];
        generateRandomCostMatrix(m, n, randomCostMatrix);

        Problem randomProblem = new Problem(m, n, randomCostMatrix);
        generateProblem(randomProblem);

        for (int i = 0; i < m * n; i++) {
            for (int j = 0; j < m * n; j++) {
                System.out.print(randomCostMatrix[i][j] + "  ");
            }
            System.out.println();
        }

        randomProblem.calculateAllShortestPaths(m, n);
        randomProblem.printShortestPathMatrix();

        //randomProblem.allocateClientsToVehicles();

        Solution solution1 = new Solution();

        Map<Client, Vehicle> allocation1 = randomProblem.getAllocationProblem();
        solution1.setAllocation(allocation1);

        System.out.println();
        System.out.println("ALLOCATION : ");

        solution1.printAllocation();

        Depot[] depots = randomProblem.getDepots();

        for(Depot depot : depots)
            System.out.println(depot);

        Vehicle[] randomVehicles = randomProblem.getVehicles();

        for(Vehicle vehicle : randomVehicles)
            System.out.println(vehicle);

        Client[] randomClients = randomProblem.getClients();

        for(Client client : randomClients)
            System.out.println(client);


    }

    public static void generateProblem(Problem problem) {

        //Integer.MAX_VALUE instead of 100 => ERROR
        int depotsRandomNumber = (int) Math.floor(Math.random() * (100 - 1 + 1) + 1); // at least 1 depot
        char indexChar = 'A';
        for (int i = 0; i < depotsRandomNumber; i++) {
            String indexStringValue = String.valueOf(indexChar);
            String name = "Depot " + indexStringValue;
            problem.addDepot(new Depot(name));
            indexChar++;
        }

        int vehiclesRandomNumber = (int) Math.floor(Math.random() * (100 - 1 + 1) + 1); // at least 1 vehicle
        int indexInt = 1;
        for (int i = 0; i < vehiclesRandomNumber; i++) {
            String indexStringValue = String.valueOf(indexInt);
            String name = "Vehicle " + indexStringValue;
            int truckOrDrone = (int) Math.floor(Math.random() * (1 - 0 + 1) + 0);//1 truck, 0 drone
            int depotNumber = (int) Math.floor(Math.random() * (depotsRandomNumber - 1 - 1 + 1) + 1);
            char indexCharVehicle = (char)('A' + depotNumber);
            String depotName = "Depot " + indexCharVehicle;
            Depot depot = new Depot(depotName);
            if(truckOrDrone == 1) {
                int capacity = (int) Math.floor(Math.random() * (1000 - 1 + 1) + 1);
                problem.addVehicle(new Truck(name, depot, capacity));
                depot.addVehicle(new Truck(name, depot, capacity));
            } else {
                int flightDuration = (int) Math.floor(Math.random() * (1000 - 1 + 1) + 1);
                problem.addVehicle(new Drone(name,  depot, flightDuration));
                depot.addVehicle(new Drone(name,  depot, flightDuration));
            }
            indexInt++;
        }

        int clientRandomNumber = (int) Math.floor(Math.random() * (100 - 1 + 1) + 1); // at least 1 client
        indexInt = 1;
        for(int i = 0; i < clientRandomNumber; i++) {
            String indexStringValue = String.valueOf(indexInt);
            String name = "Client " + indexStringValue;
            int start = (int) Math.floor(Math.random() * (23 - 0 + 1) + 0);
            int end = (int) Math.floor(Math.random() * (23 - 0 + 1) + 0);
            while(start > end) {
                end = (int) Math.floor(Math.random() * (23 - 0 + 1) + 0);
            }
            //for LocalTime
            /*
            int hourStart = (int) Math.floor(Math.random() * (23 - 0 + 1) + 0);
            int minutesStart = (int) Math.floor(Math.random() * (59 - 0 + 1) + 0);
            int secondsStart = (int) Math.floor(Math.random() * (59 - 0 + 1) + 0);
            LocalTime start = LocalTime.of(hourStart, minutesStart, secondsStart);
            int hourEnd = (int) Math.floor(Math.random() * (23 - 0 + 1) + 0);
            while(hourStart > hourEnd) {
                hourEnd = (int) Math.floor(Math.random() * (23 - 0 + 1) + 0);
            }
            int minutesEnd = (int) Math.floor(Math.random() * (59 - 0 + 1) + 0);
            if(hourStart == hourEnd) {
                while(minutesStart > minutesEnd)
                    minutesEnd = (int) Math.floor(Math.random() * (59 - 0 + 1) + 0);
            }
            int secondsEnd = (int) Math.floor(Math.random() * (59 - 0 + 1) + 0);
            if(minutesStart == minutesEnd) {
                while(secondsStart > secondsEnd)
                    secondsEnd = (int) Math.floor(Math.random() * (59 - 0 + 1) + 0);
            }
            LocalTime end = LocalTime.of(hourEnd, minutesEnd, secondsEnd);*/
            int regularOrPremium = (int) Math.floor(Math.random() * (1 - 0 + 1) + 0);//1 regular, 0 premium

            if(regularOrPremium == 1) {
                problem.addClient(new Client(name, start, end, ClientType.REGULAR));
            } else {
                problem.addClient(new Client(name, start, end, ClientType.PREMIUM));
            }
            indexInt++;
        }
    }

    public static void generateRandomCostMatrix(int m, int n, int[][] costMatrix) {
        int matrixLength = costMatrix.length;
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if (i == j) costMatrix[i][j] = 0;
                else if((i+1) % m == 0 && (j == i + m || j == i - 1 || j == i - m)) {
                    costMatrix[i][j] = (int) Math.floor(Math.random() * (9 - 1 + 1) + 1);
                } else if(i % m == 0 && (j == i + 1 || j == i + m || j == i - m)) {
                    costMatrix[i][j] = (int) Math.floor(Math.random() * (9 - 1 + 1) + 1);
                }
                else if((i % m != 0 && (i + 1) % m != 0) && (j == i + 1 || j == i + m || j == i - m || j == i - 1)) {
                    costMatrix[i][j] = (int) Math.floor(Math.random() * (9 - 1 + 1) + 1);
               } else {
                    costMatrix[i][j] = 0;
                }
            }
        }
    }

    public static void generateRandomCostMatrixAllWeightOne(int m, int n, int[][] costMatrix) {
        int matrixLength = costMatrix.length;
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if (i == j) costMatrix[i][j] = 0;
                else if((i+1) % m == 0 && (j == i + m || j == i - 1 || j == i - m)) {
                    costMatrix[i][j] = 1;
                } else if(i % m == 0 && (j == i + 1 || j == i + m || j == i - m)) {
                    costMatrix[i][j] = 1;
                }
                else if((i % m != 0 && (i + 1) % m != 0) && (j == i + 1 || j == i + m || j == i - m || j == i - 1)) {
                    costMatrix[i][j] = 1;
                } else {
                    costMatrix[i][j] = 0;
                }
            }
        }
    }
}

