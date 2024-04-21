package lot.lotapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PassengerService {
    Connection connection;

    public PassengerService(Connection connection) {
        this.connection = connection;
    }

    public void create(String name, String surname, String number){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Passengers (name,surname,number) VALUES (?,?,?)");
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, number);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //method for searching passengers with filters returns a list of flight objects
    public ObservableList<Passenger> read(int id,String name, String surname, String number) throws SQLException {
        String sql = "SELECT * FROM Passengers WHERE 1=1";
        //list to count and store changes
        List<String> parametry = new ArrayList<>();
        //Look for filters and adds it to query and list
        if (id != 0) {
            sql += " AND id = ?";
            parametry.add(String.valueOf(id));
        }

        if (name != null) {
            sql += " AND name = ?";
            parametry.add(name);
        }

        if (surname != null) {
            sql += " AND surname = ?";
            parametry.add(surname);
        }

        if (number != null) {
            sql += " AND number = ?";
            parametry.add(number);
        }

        PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < parametry.size(); i++) {
                statement.setString(i + 1, parametry.get(i));
            }
        ResultSet resultSet = statement.executeQuery();

            //List for founded passengers
        ObservableList<Passenger> results = FXCollections.observableArrayList();

        while (resultSet.next()) {
            results.add(new Passenger(Integer.parseInt(resultSet.getString("id")),resultSet.getString("name"), resultSet.getString("surname"),
                    resultSet.getString("number")));
        }

        return results;
    }

    //method for searching passengers with filters assigned to the flight
    public ObservableList<Passenger> read(int passengerId,String name, String surname, String number, int flightId, boolean bool) throws SQLException {
        String sql;
        //if true search for passengers assigned to the flight if false search for all passengers who are not assigned to this flight
        if (bool) {
            sql = "SELECT DISTINCT * FROM Passengers JOIN Tickets ON Tickets.passengerid = Passengers.id WHERE 1=1 AND flightid = " + flightId;
        } else {
            sql = "SELECT DISTINCT * FROM Passengers LEFT JOIN Tickets ON Tickets.passengerid = Passengers.id WHERE Tickets.flightid != " + flightId + " OR Tickets.flightid IS NULL";
        }
        //List of filters
        List<String> parametry = new ArrayList<>();
        //If filter exists add to query
        if (passengerId != 0) {
            sql += " AND Passengers.id = ?";
            parametry.add(String.valueOf(passengerId));
        }

        if (name != null) {
            sql += " AND Passengers.name = ?";
            parametry.add(name);
        }

        if (surname != null) {
            sql += " AND Passengers.surname = ?";
            parametry.add(surname);
        }

        if (number != null) {
            sql += " AND Passengers.number = ?";
            parametry.add(number);
        }

        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < parametry.size(); i++) {
            statement.setString(i + 1, parametry.get(i));
        }
        ResultSet resultSet = statement.executeQuery();
        ObservableList<Passenger> results = FXCollections.observableArrayList();

        while (resultSet.next()) {
            results.add(new Passenger(Integer.parseInt(resultSet.getString("id")),resultSet.getString("name"), resultSet.getString("surname"),
                    resultSet.getString("number")));
        }

        return results;
    }
    //updates passenger in database
    public void update(Passenger passenger,String name, String surname, String number){
        String sql = "UPDATE Passengers SET ";
        List<String> parametry = new ArrayList<>();
        //search for differences between previous version and new version
        if(!passenger.getFirstName().equals(name)){
            sql += "name = ?,";
            parametry.add(name);
        }
        if(!passenger.getLastName().equals(surname)){
            sql += " surname = ?,";
            parametry.add(surname);
        }
        if(!passenger.getMobileNumber().equals(number)){
            sql += " number = ?,";
            parametry.add(number);
        }
        //Delete last comma
        sql = sql.substring(0, sql.length() - 1);
        //Add id of previous object
        sql += " WHERE id = " + passenger.getId();

        //if no changes skip
        if(parametry.size()>0) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                for (int i = 0; i < parametry.size(); i++) {
                    statement.setString(i + 1, parametry.get(i));
                }

                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //deletes selected flight from database
    public void delete(String id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `Passengers` WHERE id  ="+id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
