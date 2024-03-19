import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public interface Visitable {
    Map<LocalDate, TimeInterval> getTimetable();
    default LocalTime getOpeningHour(LocalDate date) {
        Map<LocalDate, TimeInterval> timetable = getTimetable();
        if (timetable != null) {
            TimeInterval timeInterval = timetable.get(date);
            if (timeInterval != null) {
                return timeInterval.getStart();
            }
        }
        return null;
    }

}
