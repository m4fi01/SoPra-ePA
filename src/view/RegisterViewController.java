package view; /**
 * Sample Skeleton for 'Register.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import controller.EPAController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * @author Thomas Alexander Hövelmann
 */
public class RegisterViewController extends ViewController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="doctorRadioButton"
    private Label registerIDLabel; // Value injected by FXMLLoader

    @FXML // fx:id="doctorRadioButton"
    private RadioButton doctorRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="patientRadioButton"
    private RadioButton patientRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="registerPasswordField"
    private PasswordField registerPasswordField; // Value injected by FXMLLoader

    @FXML // fx:id="registerPasswordRepeatField"
    private PasswordField registerPasswordRepeatField; // Value injected by FXMLLoader

    @FXML // fx:id="registerCancelButton"
    private Button registerCancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="registerRegisterButton"
    private Button registerRegisterButton; // Value injected by FXMLLoader

    @FXML
    void action_cancel(ActionEvent event) {
        stage.close();
    }

    @FXML
    void action_doctorSelected(ActionEvent event) {
    }

    @FXML
    void action_patientSelected(ActionEvent event) {
    }

    @FXML
    void action_register(ActionEvent event) {
        EPAController ePAController = gUIController.getEPAController();
        if(doctorRadioButton.isSelected()) {
            ePAController.registerUser('D');
            stage.close();
        } else if(patientRadioButton.isSelected()) {
            ePAController.registerUser('P');
            stage.close();
        } else {
            ePAController.prompt("FEHLER!\nEs muss ein Nutzertyp ausgewählt werden.");
        }
    }

    @Override
    public void update() {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        ToggleGroup radioButtons = new ToggleGroup();
        doctorRadioButton.setToggleGroup(radioButtons);
        patientRadioButton.setToggleGroup(radioButtons);
    }
}
