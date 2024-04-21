package lot.lotapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddPassengerController implements Initializable {
    @FXML
    private TextField nameFld;
    @FXML
    private TextField surnameFld;
    @FXML
    private TextField numberFld;
    @FXML
    private Button sendBtn;


    PassengerService passengerService = null;
    Connection connection = null;
    Passenger passenger = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void send(){
        String name = nameFld.getText();
        String surname = surnameFld.getText();
        String number = numberFld.getText();

        if(!number.matches("^\\d{9}$") || !name.matches("^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*$")
                || !surname.matches("^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*(-[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*)?$")) {
            clear();
        }else {
            if (passenger == null) {
                passengerService.create(name, surname, number);
            } else {
                passengerService.update(passenger, name, surname, number);
            }
            ((Stage) sendBtn.getScene().getWindow()).close();
        }
    }
    @FXML
    private void clear(){
        nameFld.clear();
        surnameFld.clear();
        numberFld.clear();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        passengerService = new PassengerService(connection);
    }

    public void setTextField(Passenger passenger) {
        this.passenger = passenger;
        nameFld.setText(passenger.getFirstName());
        surnameFld.setText(passenger.getLastName());
        numberFld.setText(passenger.getMobileNumber());
    }
}
