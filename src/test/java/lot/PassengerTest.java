package lot;

import lot.lotapp.Passenger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PassengerTest {
    private static Passenger passenger;
    private static int id = 1;
    private static String firstName = "Patryk";
    private static String lastName = "Sałyga";
    private static String mobileNumber = "123456789";

    @BeforeAll
    public static void setUpClass() {
        passenger = new Passenger(id,firstName,lastName,mobileNumber);
    }
    @Test
    public void ObjectExistTest(){
        assertNotNull(passenger, "Object passanger does not exist");
    }
    @Test
    public void GettersTest(){
        assertEquals(passenger.getId(),id);
        assertEquals(passenger.getFirstName(),firstName);
        assertEquals(passenger.getLastName(),lastName);
        assertEquals(passenger.getMobileNumber(),mobileNumber);
    }
}
