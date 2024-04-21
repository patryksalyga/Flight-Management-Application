package lot.lotapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class SearchFlightController implements Initializable {
    @FXML
    private TextField idFld;
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
    private MenuController menuController = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //method sets filters for searching and invokes a refresh of the previous table and pops out
    @FXML
    private void send(){
        int id;
        String number;
        String origin;
        String destination;
        LocalDate date = dateFld.getValue();
        LocalTime time;
        int seats;

        //setting filters
        //if text filed is empty set null
        if(idFld.getText().trim().isEmpty()){
            id=0;
        }else{
            id = Integer.parseInt(idFld.getText());
        }

        if(numberFld.getText().trim().isEmpty()){
            number=null;
        }else{
            number = numberFld.getText();
        }

        if(originFld.getText().trim().isEmpty()){
            origin=null;
        }else{
            origin = originFld.getText();
        }

        if(destinationFld.getText().trim().isEmpty()){
            destination=null;
        }else{
            destination = destinationFld.getText();
        }

        if(timeFld.getText().trim().isEmpty()){
            time=null;
        }else{
            time = LocalTime.parse(timeFld.getText());
        }

        if(seatsFld.getText().trim().isEmpty()){
            seats=-1;
        }else{
            seats = Integer.parseInt(seatsFld.getText());
        }


        menuController.refreshFlightsTable(id,number,origin,destination,date,time,seats);
        //close this window
        ((Stage) sendBtn.getScene().getWindow()).close();

    }

    //clears all text fields
    @FXML
    private void clear(){
        idFld.clear();
        numberFld.clear();
        originFld.clear();
        destinationFld.clear();
        dateFld.setValue(null);
        timeFld.clear();
        seatsFld.clear();
    }

    //sets connection with previous controller
    public void setConnection(MenuController controller) {
        this.menuController = controller;
    }
}
