import java.util.Objects;

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
        return "Depot : " + "name = " + getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Depot depot = (Depot) o;
        return Objects.equals(name, depot.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
