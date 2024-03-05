public class Depot {
    private String name;

    public Depot(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Depot : " + "name = " + getName();
    }
}
