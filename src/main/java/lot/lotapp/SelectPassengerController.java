package lot.lotapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SelectPassengerController implements Initializable {
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
    @FXML
    private Button sendBtn;

    private Connection connection;
    private PassengerService passengerService;
    private ObservableList<Passenger> PassengersList = FXCollections.observableArrayList();
    private Flight flight;
    private Passenger passenger;
    private TicketService ticketService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadDate();
    }

    //adds selected passenger to the flight
    @FXML
    private void selectPassenger() {
        passenger = passengersTable.getSelectionModel().getSelectedItem();
        ticketService.create(flight.getId(), passenger.getId());
        ((Stage) sendBtn.getScene().getWindow()).close();
    }
    //It's refreshing passengers table. Uses arguments for filtering.
    protected void refreshPassengersTable(int passengerId, String name, String surname, String number, int flightId){
        PassengersList.clear();
        try {
            PassengersList.addAll(passengerService.read(passengerId,name, surname, number, flightId, false));
            passengersTable.setItems(PassengersList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //method called when creating a controller
    public void setConnection(Connection connection, Flight flight, TicketService service) {
        this.connection = connection;
        this.flight = flight;
        this.ticketService = service;
        passengerService = new PassengerService(connection);
        refreshPassengersTable(0,null,null,null, flight.getId());
    }

    private void LoadDate(){
        idPassengerCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namePassengerCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnamePassengerCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        numberPassengerCol.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
    }
    //opens a window for filtering results
    @FXML
    private void getSearchPassengerView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("searchPassenger.fxml"));
            Parent parent = loader.load();

            SearchPassengerController searchPassengerController = loader.getController();
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
//getter
    public Flight getFlight() {
        return flight;
    }
}
