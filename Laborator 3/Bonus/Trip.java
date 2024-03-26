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

    public Map<Attraction, LocalDate> visitDifferentTypesOfAttractionEveryDayLDF() {
        Map<Attraction, Integer> coloring = coloringNodesGraph();//nodes color
        Map<Attraction, LocalDate> visit = new HashMap<>();
        long daysBetween = ChronoUnit.DAYS.between(start, end) + 1;
        int[] colors = new int[(int) daysBetween];//keep the colors used for attractions
        int index = 0;
        for(LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            List<Attraction> sortedAttractions = new ArrayList<>(attractions);
            sortedAttractions.sort(Comparator.comparingInt(a -> getDegree(a, coloring)));

            for (Attraction attraction : sortedAttractions) {
                Map<LocalDate, TimeInterval> timetable = new HashMap<>();
                if(attraction instanceof Church || attraction instanceof Concert || attraction instanceof Museum || attraction instanceof Statue) {
                    timetable = ((Visitable) attraction).getTimetable();
                }

                if(timetable.containsKey(date) && index != 0 && coloring.get(attraction) != colors[index - 1]) {
                    //coloring.get(attraction) != colors[index - 1] actual and previous attraction has different types
                    visit.put(attraction, date);
                    colors[index] = coloring.get(attraction);
                    index++;
                } else if(index == 0 && timetable.containsKey(date)) {
                    visit.put(attraction, date);
                    colors[index] = coloring.get(attraction);
                    index++;
                }
            }

        }
        return visit;
    }

    private int getDegree(Attraction attraction, Map<Attraction, Integer> coloring) {
        int degree = 0;
        int color = coloring.get(attraction);

        for (Attraction adjacentAttraction : attractions) {
            if (!adjacentAttraction.equals(attraction) && coloring.get(adjacentAttraction) == color) {
                //attraction has the same color
                degree++;
            }
        }

        return degree;
    }


    public Map<Attraction, LocalDate> visitDifferentTypesOfAttractionEveryDay() {
        Map<Attraction, Integer> coloring = coloringNodesGraph();//nodes color
        Map<Attraction, LocalDate> visit = new HashMap<>();
        long daysBetween = ChronoUnit.DAYS.between(start, end) + 1;
        int[] colors = new int[(int) daysBetween];//keep the colors used for attractions
        int index = 0;
        for(LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            for (Attraction attraction : attractions) {
                Map<LocalDate, TimeInterval> timetable = new HashMap<>();
                if(attraction instanceof Church || attraction instanceof Concert || attraction instanceof Museum || attraction instanceof Statue) {
                    timetable = ((Visitable) attraction).getTimetable();
                }

                if(timetable.containsKey(date) && index != 0 && coloring.get(attraction) != colors[index - 1]) {
                    //coloring.get(attraction) != colors[index - 1] actual and previous attraction has different types
                    visit.put(attraction, date);
                    colors[index] = coloring.get(attraction);
                    index++;
                } else if(index == 0 && timetable.containsKey(date)) {
                    visit.put(attraction, date);
                    colors[index] = coloring.get(attraction);
                    index++;
                }
            }


        }
        return visit;

    }

    //coloring the nodes of graph with a new color for every type
    private Map<Attraction, Integer> coloringNodesGraph() {
        Map<Attraction, Integer> coloring = new HashMap<>();

        for(Attraction attraction : attractions) {
            if(attraction instanceof Visitable && attraction instanceof Payable) {
                coloring.put(attraction, 1);
            } else if(attraction instanceof Visitable) {
                coloring.put(attraction, 2);
            } else if(attraction instanceof Payable) {
                coloring.put(attraction, 3);
            }
        }

        return coloring;
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

