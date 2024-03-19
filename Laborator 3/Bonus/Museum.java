import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
public class Museum extends Attraction implements Visitable, Payable {
    private Map<LocalDate, TimeInterval> timetable;
    private double ticketPrice;

    public Museum(String name, Map<LocalDate, TimeInterval> timetable, double ticketPrice) {
        super(name);
        this.timetable = timetable;
        this.ticketPrice = ticketPrice;
    }

    public Museum() {
        super("museum");
        this.timetable = new HashMap<>();
        this.ticketPrice = 0;
    }
    @Override
    public Map<LocalDate,TimeInterval> getTimetable() {
        return timetable;
    }
    public void setTimetable(Map<LocalDate, TimeInterval> timetable) {
        this.timetable = timetable;
    }

    @Override
    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Museum{" +
                "timetable=" + timetable +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
