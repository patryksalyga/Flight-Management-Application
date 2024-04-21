package lot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lot.lotapp.Flight;
import lot.lotapp.Passenger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {
    private static Flight flight;
    private static int id = 1;
    private static String flightNumber = "LO280";
    private static String origin = "Warsaw";
    private static String destination = "Madrid";
    private static LocalDate date = LocalDate.of(2024, 3, 18);
    private static LocalTime time = LocalTime.of(15,30);
    private static int seats = 180;

    @BeforeAll
    public static void setUpClass() {
        flight = new Flight(id, flightNumber, origin, destination, date, time, seats);
    }


    @Test
    public void ObjectExistTest(){
        assertNotNull(flight, "Object flight does not exist");
    }

    @Test
    public void GettersTest(){
        assertEquals(id,flight.getId());
        assertEquals(flightNumber,flight.getNumber());
        assertEquals(destination,flight.getDestination());
        assertEquals(date,flight.getDate());
        assertEquals(time,flight.getTime());
        assertEquals(seats,flight.getAvailableSeats());
    }
}
