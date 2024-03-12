import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

public class Concert extends Attraction implements Visitable, Payable{
    private Map<LocalDate, TimeInterval> timetable;
    private double ticketPrice;

    public Concert(String name, Map<LocalDate, TimeInterval> timetable, double ticketPrice) {
        super(name);
        this.timetable = timetable;
        this.ticketPrice = ticketPrice;
    }

    public Concert() {
        super("concert");
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
        return "Concert{" +
                "timetable=" + timetable +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
