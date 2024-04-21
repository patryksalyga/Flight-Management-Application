package lot;

import lot.lotapp.DatabaseConnection;
import lot.lotapp.PassengerService;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassengerServiceTest {
    @Test
    public void Test() {
        String url = null;
        String name = null;
        String passwd = null;
        Connection connection = DatabaseConnection.getConnect(url,name,passwd);
        if(connection!=null) {
            PassengerService passengerService = new PassengerService(connection);
            try {
                int passengersCount = passengerService.read(0, null, null, null).size();
                passengerService.create("Jan", "Kowalski", "000000000");
                assertEquals(passengersCount + 1, passengerService.read(0, null, null, null).size());
                assertEquals(1, passengerService.read(0, "Jan", "Kowalski", "000000000").size());
                passengerService.update(passengerService.read(0, "Jan", "Kowalski", "000000000").get(0), "Janusz", "Kowalski", "000000000");
                assertEquals(0, passengerService.read(0, "Jan", "Kowalski", "000000000").size());
                assertEquals(1, passengerService.read(0, "Janusz", "Kowalski", "000000000").size());
                passengerService.delete(String.valueOf(passengerService.read(0, "Janusz", "Kowalski", "000000000").get(0).getId()));
                assertEquals(0, passengerService.read(0, "Janusz", "Kowalski", "000000000").size());
                assertEquals(passengersCount, passengerService.read(0, null, null, null).size());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
