package lot.lotapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class SearchPassengerController implements Initializable {
    @FXML
    private TextField idFld;
    @FXML
    private TextField nameFld;
    @FXML
    private TextField surnameFld;
    @FXML
    private TextField numberFld;
    @FXML
    private Button sendBtn;
    private MenuController menuController = null;
    private PassengersListController passengersListController = null;
    private SelectPassengerController selectPassengerController = null;
    boolean isMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //method sets filters for searching and invokes a refresh of the previous table and pops out
    @FXML
    private void send(){
        int id;
        String name;
        String surname;
        String number;

        //setting filters
        //if text filed is empty set null
        if(idFld.getText().trim().isEmpty()){
            id=0;
        }else{
            id = Integer.parseInt(idFld.getText());
        }

        if(nameFld.getText().trim().isEmpty()){
            name=null;
        }else{
            name = nameFld.getText();
        }

        if(surnameFld.getText().trim().isEmpty()){
            surname=null;
        }else{
            surname = surnameFld.getText();
        }

        if(numberFld.getText().trim().isEmpty()){
            number=null;
        }else{
            number = numberFld.getText();
        }

        //if triggered by main menu refresh menu table
        if(menuController != null) {
            menuController.refreshPassengersTable(id, name, surname, number);
        }
        //if triggered by passenger controller refresh table with passengers assigned to this flight
        if(passengersListController != null){
            passengersListController.refreshPassengersTable(id,name,surname,number,passengersListController.getFlight().getId());
        }
        //if triggered by select passenger to flight window refresh table with passengers not assigned to this flight
        if(selectPassengerController != null){
            selectPassengerController.refreshPassengersTable(id,name,surname,number,selectPassengerController.getFlight().getId());
        }
        ((Stage) sendBtn.getScene().getWindow()).close();

    }
    //clears all text fields
    @FXML
    private void clear(){
        idFld.clear();
        nameFld.clear();
        surnameFld.clear();
        numberFld.clear();
    }

    //sets connection with previous controller
    public void setConnection(MenuController controller) {
        this.menuController = controller;
        isMenu = true;
    }

    public void setConnection(PassengersListController controller){
        this.passengersListController = controller;
        isMenu = false;
    }

    public void setConnection(SelectPassengerController controller){
        this.selectPassengerController = controller;
        isMenu = false;
    }
}
