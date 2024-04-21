package lot.lotapp;

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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PassengersListController implements Initializable {
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

    TicketService ticketService ;
    private Connection connection;
    private PassengerService passengerService;
    private ObservableList<Passenger> PassengersList = FXCollections.observableArrayList();
    private Passenger passenger;
    private Flight flight;
    private int newPassengers = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadDate();
    }


    //opens a window for adding passengers
    @FXML
    private void getAddPassengerView() {
        //if no available seats don't open new window
        if(flight.getAvailableSeats() - newPassengers > 0 || newPassengers<0) {
            //Load select Passenger.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("selectPassenger.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            SelectPassengerController selectPassengerController = loader.getController();
            //transfer connection flight and ticket service objects
            selectPassengerController.setConnection(connection, flight, ticketService);
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            //when window closes increment passengers count variable and refresh passengers
            stage.setOnHidden(e -> {
                newPassengers++;
                refreshPassengersTable(0, null, null, null, flight.getId());
            });
        }
    }

    //opens a window for filtering results
    @FXML
    private void getSearchPassengerView() {
        try {
            //load searchPassenger.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("searchPassenger.fxml"));
            Parent parent = loader.load();

            SearchPassengerController searchPassengerController = loader.getController();
            //transfer this controller to searchPassengerController
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

    //deletes selectes passenger from this flight and decrements passengers count variable
    @FXML
    private void deletePassengerRecord() {
        ticketService.delete(passengersTable.getSelectionModel().getSelectedItem().getId(), flight.getId());
        refreshPassengersTable(0, null, null, null, flight.getId());
        newPassengers--;
    }
    //refresh table with passengers assigned to this flight with results matching filters
    protected void refreshPassengersTable(int passengerId, String name, String surname, String number, int flightId){
        PassengersList.clear();
        try {
            PassengersList.addAll(passengerService.read(passengerId,name, surname, number, flightId, true));
            passengersTable.setItems(PassengersList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //method called when creating a controller
    public void setConnection(Connection connection, Flight flight) {
        this.connection = connection;
        this.flight = flight;
        passengerService = new PassengerService(connection);
        ticketService = new TicketService(connection);
        refreshPassengersTable(0,null,null,null, flight.getId());
    }

    private void LoadDate(){
        //connect cells with passengers variables
        idPassengerCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namePassengerCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnamePassengerCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        numberPassengerCol.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
    }
    //getters
    public Flight getFlight() {
        return flight;
    }

    public int getNewPassengers() {
        return newPassengers;
    }
}
