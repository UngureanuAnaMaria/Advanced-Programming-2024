import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
public class Statue extends Location implements Visitable {
    private Map<LocalDate, TimeInterval> timetable;

    public Statue(String name, Map<LocalDate, TimeInterval> timetable) {
        super(name);
        this.timetable = timetable;
    }

    public Statue() {
        super("LA");
        this.timetable = new HashMap<>();
    }

    @Override
    public Map<LocalDate,TimeInterval> getTimetable() {
        return timetable;
    }
    public void setTimetable(Map<LocalDate, TimeInterval> timetable) {
        this.timetable = timetable;
    }

    @Override
    public String toString() {
        return "Statue{" +
                "timetable=" + timetable +
                '}';
    }
}
