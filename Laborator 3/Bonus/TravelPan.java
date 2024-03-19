import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TravelPlan {
    private Map<Attraction, LocalDate> plan;

    public TravelPlan() {
        this.plan = new HashMap<>();
    }

    public void addVisit(Attraction attraction, LocalDate date) {
        plan.put(attraction, date);
    }

    public void printPlan() {
        for (Map.Entry<Attraction, LocalDate> entry : plan.entrySet()) {
            System.out.println("Visit " + entry.getKey().getName() + " on " + entry.getValue());
        }
    }
}
