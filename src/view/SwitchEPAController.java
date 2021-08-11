package view;

import java.net.URL;
import java.util.ResourceBundle;

import controller.EPAController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.EPA;
import model.Patient;

/**
 * @author Thomas Alexander Hövelmann
 */
public class SwitchEPAController extends ViewController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField insuranceNumberTextField;

    @FXML
    void pressedCancel(ActionEvent event) {
        HomepageController homepageController = gUIController.getHomepageController();
        homepageController.update();
        stage.close();
        homepageController.show();
    }

    @FXML
    void pressedContinue(ActionEvent event) {
        EPAController ePAController = gUIController.getEPAController();
        EPA ePA = ePAController.getEPA();
        Patient patientToBeLoaded = null;
        for(Patient patient : ePA.getPatients()) {
            if(patient.getInsuranceNumber() != null && patient.getInsuranceNumber().equals(insuranceNumberTextField.getText())) {
                patientToBeLoaded = patient;
            }
        }
        if(patientToBeLoaded != null) {
            ePAController.getEPA().setLoadedPatient(patientToBeLoaded);
            pressedCancel(event);
        } else {
            ePAController.prompt("FEHLER!\nEs konnte kein Nutzer mit dieser Versicherungsnummer gefunden werden.");
        }
    }

    @Override
    public void update() {
    }

    @FXML
    void initialize() {
    }
}

