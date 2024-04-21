package lot.lotapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Node;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddFlightController implements Initializable {

    @FXML
    private TextField numberFld;
    @FXML
    private TextField originFld;
    @FXML
    private TextField destinationFld;
    @FXML
    private DatePicker dateFld;
    @FXML
    private TextField timeFld;
    @FXML
    private TextField seatsFld;
    @FXML
    private Button sendBtn;

    FlightService flightService = null;
    Connection connection = null;
    Flight flight = null;


    @Override
    public void initialize(URL url, ResourceBundle rb){
    }

    @FXML
    private void send(){
        String number = numberFld.getText();
        String origin = originFld.getText();
        String destination = destinationFld.getText();
        LocalDate date = dateFld.getValue();
        LocalTime time = LocalTime.parse(timeFld.getText(), DateTimeFormatter.ofPattern("H:mm"));
        int seats = Integer.parseInt(seatsFld.getText());

        if(!number.matches("^[A-Z][A-Z0-9] \\d+$") || !origin.matches("^[A-Z][a-z]+(\\s[A-Z][a-z]+)*$")
                || !destination.matches("^[A-Z][a-z]+(\\s[A-Z][a-z]+)*$")) {
            clear();
        }else {

            if (flight == null) {
                try {
                    flightService.create(number, origin, destination, date, time, seats);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                flightService.update(flight, number, origin, destination, date, time, seats);
            }
            ((Stage) sendBtn.getScene().getWindow()).close();
        }
    }
    @FXML
    private void clear(){
        numberFld.clear();
        originFld.clear();
        destinationFld.clear();
        dateFld.setValue(null);
        timeFld.clear();
        seatsFld.clear();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        flightService = new FlightService(connection);
    }

    public void setTextField(Flight flight) {
        this.flight = flight;
        numberFld.setText(flight.getNumber());
        originFld.setText(flight.getOrigin());
        destinationFld.setText(flight.getDestination());
        dateFld.setValue(flight.getDate());
        timeFld.setText(String.valueOf(flight.getTime()));
        seatsFld.setText(String.valueOf(flight.getAvailableSeats()));
    }
}
