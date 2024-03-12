import java.util.Map;
import java.util.HashMap;

public class Solution {
    //imbunatatiri:afis clienti, depots, vehicles
    private Map<Client, Vehicle> allocation;

    public Solution(){
        this.allocation = new HashMap<>();
    }

    public Solution(Map<Client, Vehicle> allocation) {
        this.allocation = allocation;
    }
    public Map<Client, Vehicle> getAllocationSolution() {
        return allocation;
    }

    public void setAllocation(Map<Client, Vehicle> allocation) {
        this.allocation = allocation;
    }

    public void printAllocation() {
        //!!!!!!!!!!!!!!!!!!!!!!!
        for(Map.Entry<Client, Vehicle> entry : allocation.entrySet())
            System.out.println("Client : " + entry.getKey() + ", vehicle : " + entry.getValue());
    }
}

