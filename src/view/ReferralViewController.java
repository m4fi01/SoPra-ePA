package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.EPAController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Doctor;

/**
 * @author Thomas Alexander Hövelmann
 */
public class ReferralViewController extends ViewController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label labelDoctorId;

    @FXML
    private TextField textFieldInput;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonConfirm;

    @FXML
    void buttonCancelPressed(ActionEvent event) {
        HomepageController homepageController = gUIController.getHomepageController();
        homepageController.update();
        stage.close();
        homepageController.show();
    }

    @FXML
    void buttonConfirmPressed(ActionEvent event) {
        try {
            int doctorID = Integer.parseInt(textFieldInput.getText());
            EPAController ePAController = gUIController.getEPAController();
            Doctor activeUser = (Doctor)ePAController.getEPA().getActiveUser();
            Doctor targetDoctor = ePAController.getDoctorByID(String.valueOf(doctorID));
            if(targetDoctor == null) {
                ePAController.prompt("FEHLER!\nDie eingegebene Facharzt-ID ist ungültig.");
                return;
            }
            if(activeUser.equals(targetDoctor)) {
                ePAController.prompt("FEHLER!\nÜberweisungen an Sie selbst sind nicht vorgesehen.");
                return;
            }
            for(Doctor doctor : ePAController.getEPA().getDoctors()) {
                if(doctor.equals(targetDoctor)) {
                    PermissionsInterfaceController permissionsInterfaceController = gUIController.getPermissionsInterfaceController();
                    permissionsInterfaceController.update();
                    permissionsInterfaceController.setDoctor(targetDoctor);
                    stage.close();
                    permissionsInterfaceController.show();
                    return;
                }
            }
        }
        catch (NumberFormatException nFEx) {
            gUIController.getEPAController().prompt("FEHLER!\nDie eingegebene Facharzt-ID ist ungültig.");
        }
    }

    @Override
    public void show() {
        update();
        super.show();
    }

    @Override
    public void showAndWait() {
        update();
        super.showAndWait();
    }

    @Override
    public void update() {
        textFieldInput.setText(null);
    }

    @FXML
    void initialize() {
        assert labelDoctorId != null : "fx:id=\"labelDoctorId\" was not injected: check your FXML file 'ReferralView.fxml'.";
        assert textFieldInput != null : "fx:id=\"textFieldInput\" was not injected: check your FXML file 'ReferralView.fxml'.";
        assert buttonCancel != null : "fx:id=\"buttonCancel\" was not injected: check your FXML file 'ReferralView.fxml'.";
        assert buttonConfirm != null : "fx:id=\"buttonConfirm\" was not injected: check your FXML file 'ReferralView.fxml'.";

    }
}
