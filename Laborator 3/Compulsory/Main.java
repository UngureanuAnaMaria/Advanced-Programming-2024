import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Museum museum = new Museum();
        Map<LocalDate,TimeInterval> museumTimetable = new HashMap<>();
        museumTimetable.put(LocalDate.of(2024, 3, 10), new TimeInterval(LocalTime.of(9, 0), LocalTime.of(17, 0)));
        museum.setTimetable(museumTimetable);
        museum.setTicketPrice(20);

        Church church = new Church();
        Map<LocalDate,TimeInterval> churchTimetable = new HashMap<>();
        churchTimetable.put(LocalDate.of(2024, 2, 10), new TimeInterval(LocalTime.of(8, 30), LocalTime.of(12, 0)));
        church.setTimetable(churchTimetable);

        Statue statue = new Statue();
        Map<LocalDate,TimeInterval> statueTimetable = new HashMap<>();
        statueTimetable.put(LocalDate.of(2024, 1, 10), new TimeInterval(LocalTime.of(8, 0), LocalTime.of(20, 0)));
        statue.setTimetable(statueTimetable);

        Concert concert = new Concert();
        Map<LocalDate,TimeInterval> concertTimetable = new HashMap<>();
        concertTimetable.put(LocalDate.of(2024, 3, 10), new TimeInterval(LocalTime.of(21, 0), LocalTime.of(0, 0)));
        concert.setTimetable(concertTimetable);
        concert.setTicketPrice(200);

        Visitable[] arr = {museum, church, statue, concert};

        for(Visitable v : arr) {
            System.out.println(v.getTimetable());
            System.out.println(v.getOpeningHour(LocalDate.of(2024, 3, 10)));
        }

        System.out.println(museum);
        System.out.println(statue);
        System.out.println(concert);
        System.out.println(church);

        List<Attraction> attractionList = new ArrayList<>();
        attractionList.add(concert);
        attractionList.add(museum);

        List<Location> locationList = new ArrayList<>();
        locationList.add(church);

        System.out.println("Attractions:");
        Trip trip = new Trip("NY", LocalDate.of(2024, 1, 10), LocalDate.of(2024, 3, 10));
        trip.setAttractions(attractionList);
        trip.setLocations(locationList);

        System.out.println(trip);
    }
}
