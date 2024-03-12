import java.util.Arrays;

public class Depot {
    private String name;
    private Vehicle[] vehicles;
    public Depot(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setVehicles(Vehicle ... vehicles) {
        this.vehicles = vehicles;
        for (Vehicle v : vehicles) {
            v.setDepot(this);
        }
    }

    public Vehicle[] getVehicles() {
        return this.vehicles;
    }

    @Override
    public String toString() {
        return "Depot{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Depot)) return false;
        Depot comp = (Depot) obj;
        return ( comp.name == name);
    }
}
