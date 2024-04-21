package lot;

import lot.lotapp.DatabaseConnection;
import lot.lotapp.FlightService;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightServiceTest {
    @Test
    public void Test(){
        String url = null;
        String name = null;
        String passwd = null;
        Connection connection = DatabaseConnection.getConnect(url,name,passwd);
        if(connection!= null) {
            FlightService flightService = new FlightService(connection);
            try {
                int flightsnumber = flightService.read(0, null, null, null, null, null, 0).size();
                flightService.create("LO000", "Test1", "Test2", LocalDate.of(1990, 1, 1), LocalTime.of(0, 0), 1);
                assertEquals(flightsnumber + 1, flightService.read(0, null, null, null, null, null, 0).size());
                assertEquals(1, flightService.read(0, "LO000", "Test1", "Test2", LocalDate.of(1990, 1, 1), LocalTime.of(0, 0), 1).size());
                flightService.update(flightService.read(0, "LO000", "Test1", "Test2", LocalDate.of(1990, 1, 1), LocalTime.of(0, 0), 1).get(0), "LO000", "Test3", "Test4", LocalDate.of(1990, 1, 1), LocalTime.of(0, 0), 1);
                assertEquals(0, flightService.read(0, "LO000", "Test1", "Test2", LocalDate.of(1990, 1, 1), LocalTime.of(0, 0), 1).size());
                assertEquals(1, flightService.read(0, "LO000", "Test3", "Test4", LocalDate.of(1990, 1, 1), LocalTime.of(0, 0), 1).size());
                flightService.delete(String.valueOf(flightService.read(0, "LO000", "Test3", "Test4", LocalDate.of(1990, 1, 1), LocalTime.of(0, 0), 1).get(0).getId()));
                assertEquals(flightsnumber, flightService.read(0, null, null, null, null, null, 0).size());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
