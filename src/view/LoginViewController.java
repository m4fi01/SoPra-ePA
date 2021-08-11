package view;

import java.net.URL;
import java.util.ResourceBundle;

import controller.EPAController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Doctor;
import model.Patient;
import model.User;

/**
 * @author Thomas Alexander Hövelmann
 */
public class LoginViewController extends ViewController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="IDFieldLogin"
    private TextField IDFieldLogin; // Value injected by FXMLLoader

    @FXML // fx:id="passwordFieldLogin"
    private PasswordField passwordFieldLogin; // Value injected by FXMLLoader

    @FXML // fx:id="insuranceNumberFieldLogin"
    private TextField insuranceNumberFieldLogin; // Value injected by FXMLLoader

    @FXML
    void action_login(ActionEvent event) {
        EPAController ePAController = gUIController.getEPAController();
        try {
            int userID = Integer.parseInt(IDFieldLogin.getText());
            if(ePAController.loginUser(userID, insuranceNumberFieldLogin.getText())) {
                User activeUser = ePAController.getEPA().getActiveUser();
                if(activeUser instanceof Patient && ((Patient)activeUser).isFirstLogin()) {
                    PatientenDatenController patientenDatenController = gUIController.getPatientenDatenController();
                    patientenDatenController.update();
                    stage.close();
                    patientenDatenController.show();
                } else {
                    HomepageController homepageController = gUIController.getHomepageController();
                    homepageController.update();
                    stage.close();
                    homepageController.show();
                }
            }
        } catch (NumberFormatException nFEx) {
            ePAController.prompt("FEHLER!\nDie eingegebene Nutzer-ID ist ungültig.");
        }
    }

    @FXML
    void action_register(ActionEvent event) {
        stage.close();
        gUIController.getRegisterViewController().showAndWait();
        stage.show();
    }

    @FXML
    void action_exit(ActionEvent event) {
        EPAController ePAController = gUIController.getEPAController();
        System.exit(0);
    }

    @Override
    public void update() {
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert IDFieldLogin != null : "fx:id=\"IDFieldLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordFieldLogin != null : "fx:id=\"passwordFieldLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert insuranceNumberFieldLogin != null : "fx:id=\"insuranceNumberFieldLogin\" was not injected: check your FXML file 'Login.fxml'.";
    }
}
