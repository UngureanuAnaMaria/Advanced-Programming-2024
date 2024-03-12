public abstract class Location implements Comparable<Location>{
    private String name;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Location other) {
        if(!this.name.equals(null))
            return this.name.compareTo(other.name);
        return 0;
    }
}
