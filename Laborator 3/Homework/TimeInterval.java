import java.time.LocalTime;
public class TimeInterval extends Pair<LocalTime, LocalTime> {
    public TimeInterval(LocalTime start, LocalTime end) {
        super(start, end);
    }
}
