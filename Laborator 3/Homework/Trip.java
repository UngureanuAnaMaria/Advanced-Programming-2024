import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Trip {
    private String city;
    private LocalDate start, end;
    private List<Attraction> attractions = new ArrayList<>();


    public Trip() {
        this.city = "";
        this.start = LocalDate.now();
        this.end = LocalDate.now();
    }
    public Trip(String city, LocalDate start, LocalDate end) {
        this.city = city;
        this.start = start;
        this.end = end;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }

    public void displayVisitableLocationsSortedByOpeningHour() {
        List<Visitable> visitableLocations = new ArrayList<>();

        for (Attraction attraction : attractions) {
            if (attraction instanceof Visitable && !(attraction instanceof Payable)) {
                visitableLocations.add((Visitable) attraction);
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        visitableLocations.sort(Comparator.comparing(v -> v.getOpeningHour(LocalDate.of(2024, 3, 11))));

        for (Visitable location : visitableLocations) {
            Attraction location1 = (Attraction) location;
            System.out.println(location1.getName() + " - Opening Hour: " + location.getOpeningHour(LocalDate.of(2024, 3, 11)));
        }

    }

    @Override
    public String toString() {
        return "Trip{" +
                "city='" + city + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", attractions=" + attractions +
                '}';
    }
}

