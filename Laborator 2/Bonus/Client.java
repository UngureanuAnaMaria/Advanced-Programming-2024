enum ClientType {
    REGULAR, PREMIUM;
}
public class Client {
    private String name;
    private int startTime;
    private int endTime;
    private ClientType type;

    public Client(String name, int startTime, int endTime, ClientType type) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public ClientType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Client : " + "name = " + getName() + ", type = " + getType() + ", visitStartTime = " + getStartTime() + ", visitEndTime = " + getEndTime();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Client)) return false;
        Client comp = (Client) obj;
        return (comp.name == name && comp.startTime == startTime && comp.endTime == endTime && comp.type == type);

    }
}

