package lot.lotapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    private int id;
    private String number;
    private String origin;
    private String destination;
    private LocalDate date;
    private LocalTime time;
    private int availableSeats;

    private ObservableList<Passenger> passengers = FXCollections.observableArrayList();
    //Constructor for new flights
    public Flight(int id, String number, String origin, String destination, LocalDate date, LocalTime time, int availableSeats) {
        this.id = id;
        this.number = number;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.availableSeats = availableSeats;
    }
    //Constructor overload for flights with passengers list
    public Flight(int id, String number, String origin, String destination, LocalDate date, LocalTime time, int availableSeats, ObservableList<Passenger> passengers) {
        this.id = id;
        this.number = number;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.passengers.addAll(passengers);
        this.availableSeats = availableSeats;
    }
    //getters
    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

}
