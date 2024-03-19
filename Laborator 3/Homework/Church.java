import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
public class Church extends Attraction implements Visitable {
    private Map<LocalDate, TimeInterval> timetable;

    public Church(String name, Map<LocalDate, TimeInterval> timetable) {
        super(name);
        this.timetable = timetable;
    }

    public Church() {
        super("church");
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
        return "Church{" +
                "timetable=" + timetable +
                '}';
    }
}

