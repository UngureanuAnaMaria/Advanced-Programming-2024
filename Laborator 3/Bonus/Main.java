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
        churchTimetable.put(LocalDate.of(2024, 3, 11), new TimeInterval(LocalTime.of(8, 30), LocalTime.of(12, 0)));
        church.setTimetable(churchTimetable);

        Statue statue = new Statue();
        Map<LocalDate,TimeInterval> statueTimetable = new HashMap<>();
        statueTimetable.put(LocalDate.of(2024, 3, 13), new TimeInterval(LocalTime.of(8, 0), LocalTime.of(20, 0)));
        statueTimetable.put(LocalDate.of(2024, 3, 11), new TimeInterval(LocalTime.of(8, 0), LocalTime.of(20, 0)));
        statue.setTimetable(statueTimetable);

        Concert concert = new Concert();
        Map<LocalDate,TimeInterval> concertTimetable = new HashMap<>();
        concertTimetable.put(LocalDate.of(2024, 3, 12), new TimeInterval(LocalTime.of(21, 0), LocalTime.of(0, 0)));
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
        attractionList.add(church);
        attractionList.add(statue);

        System.out.println("Attractions:");
        Trip trip = new Trip("NY", LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 13));
        trip.setAttractions(attractionList);

        System.out.println(trip);

        trip.displayVisitableLocationsSortedByOpeningHour();

        System.out.println("BONUS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        Map<Attraction, LocalDate> visit = trip.visitDifferentTypesOfAttractionEveryDay();

        for(Map.Entry<Attraction, LocalDate> entry : visit.entrySet())
            System.out.println("Attraction : " + entry.getKey() + ", date : " + entry.getValue());

        TravelPlan travelPlan = new TravelPlan();
        travelPlan.addVisit(museum, LocalDate.of(2024, 3, 10));
        travelPlan.addVisit(church, LocalDate.of(2024, 3, 11));
        travelPlan.addVisit(concert, LocalDate.of(2024, 3, 12));
        travelPlan.addVisit(statue, LocalDate.of(2024, 3, 13));

        travelPlan.printPlan();
    }
}
