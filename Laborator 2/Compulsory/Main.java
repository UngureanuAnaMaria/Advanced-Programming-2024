import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Depot depot = new Depot("Depot A");

        Vehicle vehicle1 = new Vehicle("BMW", depot);

        Client client1 = new Client("Popescu Ion", 10, 11, ClientType.REGULAR);
        Client client2 = new Client("Ionescu George", 14, 15, ClientType.PREMIUM);

        vehicle1.addClient(client1);

        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);

        Vehicle vehicle2 = new Vehicle("Audi", depot, clients);

        System.out.println(depot.toString());
        System.out.println(vehicle1.toString());
        System.out.println(vehicle2.toString());
        System.out.println(client1.toString());
        System.out.println(client2.toString());
    }
}
