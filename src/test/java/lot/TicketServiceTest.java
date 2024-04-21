package lot;

import lot.lotapp.DatabaseConnection;
import lot.lotapp.TicketService;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketServiceTest {
        @Test
        public void Test() {
            String url = null;
            String name = null;
            String passwd = null;
            Connection connection = DatabaseConnection.getConnect(url, name, passwd);
            if (connection != null) {
                TicketService ticketService = new TicketService(connection);
                ticketService.create(0, 0);
                ticketService.delete(0, 0);
                assertTrue(true);
            }
        }
}
