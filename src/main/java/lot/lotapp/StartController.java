package lot.lotapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;

public class StartController {
    @FXML
    private TextField urlFld;
    @FXML
    private TextField userFld;
    @FXML
    private PasswordField passwdFld;
    @FXML
    private Button sendBtn;

    @FXML
    private void clear() {
        urlFld.clear();
        userFld.clear();
        passwdFld.clear();
    }

    @FXML
    private void send() {
        Connection connection = DatabaseConnection.getConnect(urlFld.getText(),userFld.getText(),passwdFld.getText());

        if(connection != null){
            try {
                //Load searchPassenger.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                Parent parent = loader.load();

                MenuController menuController = loader.getController();
                //Transfer instance of this object to new controller to get access to this object methods
                menuController.setConnection(connection);

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();

                ((Stage) sendBtn.getScene().getWindow()).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
