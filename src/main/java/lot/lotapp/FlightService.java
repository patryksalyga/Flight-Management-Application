package lot.lotapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FlightService {

    Connection connection;

    public FlightService(Connection connection) {
        this.connection = connection;
    }

    //C - create
    public void create(String number,
                       String origin,
                       String destination,
                       LocalDate date,
                       LocalTime time,
                       int seats) throws SQLException{

            PreparedStatement statement = connection.prepareStatement("INSERT INTO Flights (number,origin,destination,date,time,seats) VALUES (?,?,?,?,?,?)");
            statement.setString(1, number);
            statement.setString(2, origin);
            statement.setString(3, destination);
            statement.setDate(4, Date.valueOf(date));
            statement.setTime(5, Time.valueOf(time));
            statement.setInt(6, seats);

            statement.executeUpdate();
    }
    //R - read returns a list of flight objects
    public ObservableList<Flight> read(int id,
                                       String number,
                                       String origin,
                                       String destination,
                                       LocalDate date,
                                       LocalTime time,
                                       int seats) throws SQLException {
        String sql = "SELECT * FROM Flights WHERE 1=1";
        //list to count and store changes
        List<String> parametry = new ArrayList<>();

        //Look for filters and adds it to query
        if (id != 0) {
            sql += " AND id = ?";
            parametry.add(String.valueOf(id));
        }

        if (number != null) {
            sql += " AND number = ?";
            parametry.add(number);
        }

        if (origin != null) {
            sql += " AND origin = ?";
            parametry.add(origin);
        }

        if (destination != null) {
            sql += " AND destination = ?";
            parametry.add(destination);
        }

        if (date != null) {
            sql += " AND date = ?";
            parametry.add(String.valueOf(date));
        }

        if (time != null) {
            sql += " AND time = ?";
            parametry.add(String.valueOf(time));
        }

        if (seats != -1) {
            sql += " AND seats = ?";
            parametry.add(String.valueOf(seats));
        }

        PreparedStatement statement = connection.prepareStatement(sql);

        for (int i = 0; i < parametry.size(); i++) {
            statement.setString(i + 1, parametry.get(i));
        }

        ResultSet resultSet = statement.executeQuery();
        ObservableList<Flight> results = FXCollections.observableArrayList();
        //Passengers List assigned to the selected flight
        ObservableList<Passenger> passengers = new PassengerService(connection).read(0,null,null,null,id,true);
        //Makes new flight objects and adds to the list
        while (resultSet.next()) {
            results.add(new Flight(Integer.parseInt(resultSet.getString("id")),resultSet.getString("number"), resultSet.getString("origin"),
                    resultSet.getString("destination"), LocalDate.parse(resultSet.getString("date")), LocalTime.parse(resultSet.getString("time")),
                    Integer.parseInt(resultSet.getString("seats")),passengers));
        }

        return results;
    }
    //U - updates flight in database
    public void update(Flight flight,
                         String number,
                         String origin,
                         String destination,
                         LocalDate date,
                         LocalTime time,
                         int seats){

        String sql = "UPDATE flights SET ";
        List<String> parametry = new ArrayList<>();

        //search for differences between previous version and new version
        if(!flight.getNumber().equals(number)){
            sql += "number = ?,";
            parametry.add(number);
        }
        if(!flight.getOrigin().equals(origin)){
            sql += " origin = ?,";
            parametry.add(origin);
        }
        if(!flight.getDestination().equals(destination)){
            sql += " destination = ?,";
            parametry.add(destination);
        }
        if(!flight.getDate().equals(date)){
            sql += " date = ?,";
            parametry.add(String.valueOf(date));
        }
        if(!flight.getTime().equals(time)){
            sql += " time = ?,";
            parametry.add(String.valueOf(time));
        }
        if(seats != flight.getAvailableSeats()){
            sql += " seats = ?,";
            parametry.add(String.valueOf(seats));
        }
        //Delete last comma
        sql = sql.substring(0, sql.length() - 1);

        //Add id of previous object
        sql += " WHERE id = " + flight.getId();

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
    //D - deletes selected flight from database
    public void delete(String id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `flights` WHERE id  ="+id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
