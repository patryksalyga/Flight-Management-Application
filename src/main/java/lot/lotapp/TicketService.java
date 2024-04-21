package lot.lotapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TicketService {
    Connection connection;

    public TicketService(Connection connection) {
        this.connection = connection;
    }


    //add passenger to flight
    public void create(int flightid, int passengerid){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Tickets (flightid,passengerid) VALUES (?,?)");
            statement.setString(1, String.valueOf(flightid));
            statement.setString(2, String.valueOf(passengerid));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //delete passenger from flight
    public void delete(int passengerid, int flightid){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `Tickets` WHERE passengerid  ="+passengerid +" AND flightid ="+flightid);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
