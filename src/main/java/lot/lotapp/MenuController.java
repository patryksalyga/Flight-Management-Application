package lot.lotapp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import java.sql.SQLException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MenuController implements Initializable {

    Connection connection = null;
    FlightService flightService = null;
    PassengerService passengerService = null;
    Flight flight = null;
    Passenger passenger = null;

    ObservableList<Flight> FlightsList = FXCollections.observableArrayList();
    ObservableList<Passenger> PassengersList = FXCollections.observableArrayList();
    @FXML
    private TableView<Flight> flightsTable;

    @FXML
    private TableColumn<Flight, String> idFlightCol;
    @FXML
    private TableColumn<Flight, String> numberFlightCol;
    @FXML
    private TableColumn<Flight, String> originFlightCol;
    @FXML
    private TableColumn<Flight, String> destinationFlightCol;
    @FXML
    private TableColumn<Flight, Date> dateFlightCol;
    @FXML
    private TableColumn<Flight, LocalTime> hourFlightCol;
    @FXML
    private TableColumn<Flight, Integer> seatsFlightCol;
    @FXML
    private TableView<Passenger> passengersTable;
    @FXML
    private TableColumn<Passenger, String> idPassengerCol;
    @FXML
    private TableColumn<Passenger, String> namePassengerCol;
    @FXML
    private TableColumn<Passenger, String> surnamePassengerCol;
    @FXML
    private TableColumn<Passenger, String> numberPassengerCol;




    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadDate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //The method opens a new window. A new window allows you to provide new flight information and the controller sends variables to the flight service to add that flight to the database
    @FXML
    private void getAddFlightView() {
        try {
            //Load addFlight.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addFlight.fxml"));
            Parent parent = loader.load();

            AddFlightController addFlightController = loader.getController();
            //Transfer connection object to controller
            addFlightController.setConnection(connection);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            //When new window close
            stage.setOnHidden(e -> {
                //Refresh Flights Table to see all of them
                refreshFlightsTable(0, null, null, null, null, null, -1);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //The method opens a new window. A new window allows you to provide flight information and the controller sends variables to the flight service to search that flight in the database.
    @FXML
    private void getSearchFlightView(){
        try {
            //Load searchFlight.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("searchFlight.fxml"));
            Parent parent = loader.load();

            SearchFlightController searchFlightController = loader.getController();
            //Transfer instance of this object to new controller to get access to this object methods
            searchFlightController.setConnection(this);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //The method opens a new window. A new window allows you to change flight information and the controller sends variables to the flight service to update that flight in the database.
    @FXML
    private void editFlightRecord() {
        //Get flight for selected record
        flight = flightsTable.getSelectionModel().getSelectedItem();
        //Load addFlight.fxml
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addFlight.fxml"));
        try{
            loader.load();
        }catch (IOException ex){
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE,null,ex);
        }
        AddFlightController addFlightController = loader.getController();
        //Transfer connection object to controller
        addFlightController.setConnection(connection);
        //Transfer selected flight to controller
        addFlightController.setTextField(flight);
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        //When new window close
        stage.setOnHidden(e -> {
            //Refresh Flights Table to see all of them
            refreshFlightsTable(0, null, null, null, null, null, -1);
        });
    }
    //Deletes selected flight from database and refreshes table.
    @FXML
    private void deleteFlightRecord() {
        //Get the selected flight and use it as an argument
        flightService.delete(String.valueOf(flightsTable.getSelectionModel().getSelectedItem().getId()));
        refreshFlightsTable(0, null, null, null, null, null, -1);
    }
    //The method opens a new window. A new window allows you to provide new passenger information and the controller sends variables to the passenger service to add that passenger to the database
    @FXML
    private void getAddPassengerView(){
        try {
            //Load addPassenger.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addPassenger.fxml"));
            Parent parent = loader.load();

            AddPassengerController addPassengerController = loader.getController();
            //Transfer connection object to controller
            addPassengerController.setConnection(connection);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            //When new window close
            stage.setOnHidden(e -> {
                //Refresh Passengers Table to see all of them
                refreshPassengersTable(0, null, null, null);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //The method opens a new window. A new window allows you to provide flight information and the controller sends variables to the flight service to search that flight in the database.
    @FXML
    private void getSearchPassengerView(){
        try {
            //Load searchPassenger.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("searchPassenger.fxml"));
            Parent parent = loader.load();

            SearchPassengerController searchPassengerController = loader.getController();
            //Transfer instance of this object to new controller to get access to this object methods
            searchPassengerController.setConnection(this);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //The method opens a new window. A new window allows you to change flight information and the controller sends variables to the flight service to update that flight in the database.
    @FXML
    private void editPassengerRecord() {
        //Get passenger from selected record
        passenger = passengersTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        //Load addPassenger.fxml
        loader.setLocation(getClass().getResource("addPassenger.fxml"));
        try{
            loader.load();
        }catch (IOException ex){
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE,null,ex);
        }
        AddPassengerController addPassengerController = loader.getController();
        //Transfer connection
        addPassengerController.setConnection(connection);
        //Set text fields with selected passenger variables
        addPassengerController.setTextField(passenger);
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        //When new window close
        stage.setOnHidden(e -> {
            //Refresh Passengers Table to see all of them
            refreshPassengersTable(0, null, null, null);
        });

    }

    //Deletes selected passenger
    @FXML
    private void deletePassengerRecord() {
        passengerService.delete(String.valueOf(passengersTable.getSelectionModel().getSelectedItem().getId()));
        refreshPassengersTable(0, null, null, null);
    }

    //It's refreshing flight table. Uses arguments for filtering.
    protected void refreshFlightsTable(int id, String number, String origin, String destination, LocalDate date, LocalTime time, int seats) {
        FlightsList.clear();

        try {
            FlightsList.addAll(flightService.read(id,number,origin,destination,date,time,seats));
            flightsTable.setItems(FlightsList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    //It's refreshing passengers table. Uses arguments for filtering.
    protected void refreshPassengersTable(int id, String name, String surname, String number){
        PassengersList.clear();

        try {
            PassengersList.addAll(passengerService.read(id,name, surname, number));
            passengersTable.setItems(PassengersList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDate() throws ClassNotFoundException {
        //Connect cells with flights and passengers variables
        idFlightCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        numberFlightCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        originFlightCol.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationFlightCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        dateFlightCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        hourFlightCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        seatsFlightCol.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        idPassengerCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namePassengerCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnamePassengerCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        numberPassengerCol.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
    }

    public void setConnection(Connection connection){
        this.connection = connection;

        //Create flight service
        flightService = new FlightService(connection);
        //Create passenger service
        passengerService = new PassengerService(connection);
        //Refresh both tables to see all records
        refreshFlightsTable(0, null, null, null, null, null, -1);
        refreshPassengersTable(0, null, null, null);

    }

    //Opens a window with a list of passengers assigned to the selected flight
    @FXML
    public void getPassengersListView() {
        //Get selected flight
        flight = flightsTable.getSelectionModel().getSelectedItem();
        //Load window
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("passengersList.fxml"));
        try{
            loader.load();
        }catch (IOException ex){
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE,null,ex);
        }
        PassengersListController passengersListController = loader.getController();
        //Transfer connection and flight object
        passengersListController.setConnection(connection, flight);
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();

        stage.setOnHidden(e -> {
            //When window close update number of seats for that flight
            flightService.update(flight, flight.getNumber(), flight.getOrigin(), flight.getDestination(), flight.getDate(), flight.getTime(), flight.getAvailableSeats()-passengersListController.getNewPassengers());
            refreshFlightsTable(0, null, null, null, null, null, -1);
        });
    }
}
