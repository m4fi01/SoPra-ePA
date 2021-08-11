package view;


import java.net.URL;
import java.util.ResourceBundle;

import controller.EPAController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.EPA;
import model.Patient;

/**
 * @author Thomas Alexander Hövelmann
 */
public class PatientenDatenController extends ViewController {
        @FXML // ResourceBundle that was given to the FXMLLoader
        private ResourceBundle resources;

        @FXML // URL location of the FXML file that was given to the FXMLLoader
        private URL location;

        @FXML
        TextField tfFirstName;

        @FXML
        TextField tfLastName;

        @FXML
        TextField tfStreet;

        @FXML
        TextField tfHouseNumber;

        @FXML
        TextField tfPostalCode;

        @FXML
        TextField tfCityName;

        @FXML
        TextField tfInsuranceName;

        @FXML
        TextField tfInsuranceNumber;

        @FXML
        DatePicker dpBirthday;

        @FXML
        TextField tfSex;

        @FXML
        TextField tfPersonalDoctor;

        @FXML
        Button bttConfirm;

        @FXML
        Button bttCancel;

        @FXML
        void action_confirm(ActionEvent event){
                EPAController ePAController = gUIController.getEPAController();
                // This view can only be accessed by patients
                Patient patient = (Patient)ePAController.getEPA().getActiveUser();
                try {
                        checkInput();
                        if (patient.isFirstLogin()) {
                                ePAController.getPatientController().createData(
                                        tfInsuranceName.getText(),
                                        tfInsuranceNumber.getText(),
                                        tfFirstName.getText(),
                                        tfLastName.getText(),
                                        tfStreet.getText(),
                                        tfHouseNumber.getText(),
                                        tfPostalCode.getText(),
                                        tfCityName.getText(),
                                        dpBirthday.getValue(),
                                        tfSex.getText(),
                                        ePAController.getDoctorByID(tfPersonalDoctor.getText())
                                );
                        } else {
                                if(checkForChanges()) {
                                        ePAController.getPatientController().updateData(
                                                tfInsuranceName.getText(),
                                                tfInsuranceNumber.getText(),
                                                tfFirstName.getText(),
                                                tfLastName.getText(),
                                                tfStreet.getText(),
                                                tfHouseNumber.getText(),
                                                tfPostalCode.getText(),
                                                tfCityName.getText(),
                                                dpBirthday.getValue(),
                                                tfSex.getText(),
                                                ePAController.getDoctorByID(tfPersonalDoctor.getText())
                                        );
                                }
                        }
                }
                catch (IllegalArgumentException iAEx) {
                        if(iAEx.getMessage() != null) {
                                if (iAEx.getMessage().equals("Input")) {
                                        return;
                                } else if (iAEx.getMessage().equals("Birthday")) {
                                        ePAController.prompt("FEHLER!\nDer angegebene Geburtstag ist ungültig.");
                                        return;
                                } else if (iAEx.getMessage().equals("Sex")) {
                                        ePAController.prompt(
                                                "FEHLER!\nDas angegebene Geschlecht ist ungültig.\n" +
                                                        "Es werden nur Folgen aus 'X' und 'Y' akzeptiert."
                                        );
                                        return;
                                }
                        }
                        throw iAEx;
                }
                ePAController.getIOController().save();
                HomepageController homepageController = gUIController.getHomepageController();
                homepageController.update();
                stage.close();
                homepageController.show();
        }

        private void checkInput() {
                EPAController ePAController = gUIController.getEPAController();
                StringBuilder errMsg = new StringBuilder();
                errMsg.append("FEHLER!");
                if(tfFirstName.getText().equals("")) {
                        errMsg.append("\nEs wurde kein Vorname angegeben.");
                }
                if(tfLastName.getText().equals("")) {
                        errMsg.append("\nEs wurde kein Nachname angegeben.");
                }
                if(tfStreet.getText().equals("")) {
                        errMsg.append("\nEs wurde kein Straßenname angegeben.");
                }
                if(tfHouseNumber.getText().equals("")) {
                        errMsg.append("\nEs wurde keine Hausnummer angegeben.");
                }
                if(tfPostalCode.getText().equals("")) {
                        errMsg.append("\nEs wurde keine Postleitzahl angegeben.");
                }
                if(tfCityName.getText().equals("")) {
                        errMsg.append("\nEs wurde kein Stadtname angegeben.");
                }
                if(tfInsuranceName.getText().equals("")) {
                        errMsg.append("\nEs wurde kein Versicherungsname angegeben.");
                }
                if(tfInsuranceNumber.getText().equals("")) {
                        errMsg.append("\nEs wurde keine Versicherungsnummer angegeben.");
                }
                if(dpBirthday.getValue() == null) {
                        errMsg.append("\nEs wurde kein Geburtsdatum ausgewählt.");
                }
                if(tfSex.getText().equals("")) {
                        errMsg.append("\nEs wurde kein Geschlecht angegeben.");
                }
                if(ePAController.getDoctorByID(tfPersonalDoctor.getText()) == null) {
                        errMsg.append("\nEs wurde kein gültiger Hausarzt angegeben.");
                }
                if(!errMsg.toString().equals("FEHLER!")) {
                        gUIController.getEPAController().prompt(errMsg.toString());
                        throw new IllegalArgumentException("Input");
                }
        }

        private boolean checkForChanges() {
                EPAController ePAController = gUIController.getEPAController();
                Patient patient = ePAController.getEPA().getLoadedPatient();
                char semicolon = ';';
                // Name:
                StringBuilder sBLastName = new StringBuilder();
                StringBuilder sBFirstName = new StringBuilder();
                boolean isLastName = true;
                boolean isFirstName = false;
                for(char c : patient.getName().toCharArray()) {
                        if(c == semicolon) {
                                isLastName = false;
                                isFirstName = true;
                        } else {
                                if(isLastName) {
                                        sBLastName.append(c);
                                } else if(isFirstName) {
                                        sBFirstName.append(c);
                                }
                        }
                }
                String lastName = sBLastName.toString();
                String firstName = sBFirstName.toString();
                // Address:
                StringBuilder sBStreetName = new StringBuilder();
                StringBuilder sBHouseNumber = new StringBuilder();
                StringBuilder sBPostalCode = new StringBuilder();
                StringBuilder sBCityName = new StringBuilder();
                boolean isStreetName = true;
                boolean isHouseNumber = false;
                boolean isPostalCode = false;
                boolean isCityName = false;
                for(char c : patient.getAddress().toCharArray()) {
                        if(c == semicolon) {
                                if(isStreetName) {
                                        isStreetName = false;
                                        isHouseNumber = true;
                                } else if(isHouseNumber) {
                                        isHouseNumber = false;
                                        isPostalCode = true;
                                } else if(isPostalCode) {
                                        isPostalCode = false;
                                        isCityName = true;
                                }
                        } else {
                                if(isStreetName) {
                                        sBStreetName.append(c);
                                } else if(isHouseNumber) {
                                        sBHouseNumber.append(c);
                                } else if(isPostalCode) {
                                        sBPostalCode.append(c);
                                } else if(isCityName) {
                                        sBCityName.append(c);
                                }
                        }
                }
                String streetName = sBStreetName.toString();
                String houseNumber = sBHouseNumber.toString();
                String postalCode = sBPostalCode.toString();
                String cityName = sBCityName.toString();
                if(!tfFirstName.getText().equals(firstName)) {
                        return true;
                }
                if(!tfLastName.getText().equals(lastName)) {
                        return true;
                }
                if(!tfStreet.getText().equals(streetName)) {
                        return true;
                }
                if(!tfHouseNumber.getText().equals(houseNumber)) {
                        return true;
                }
                if(!tfPostalCode.getText().equals(postalCode)) {
                        return true;
                }
                if(!tfCityName.getText().equals(cityName)) {
                        return true;
                }
                if(!tfInsuranceName.getText().equals(patient.getInsuranceName())) {
                        return true;
                }
                if(!tfInsuranceNumber.getText().equals(patient.getInsuranceNumber())) {
                        return true;
                }
                if(!dpBirthday.getValue().equals(patient.getBirthday())) {
                        return true;
                }
                if(!tfSex.getText().equals(patient.getSex())) {
                        return true;
                }
                if(patient.getPersonalDoctor() != null) {
                        return !tfPersonalDoctor.getText().equals(String.valueOf(patient.getPersonalDoctor().getUserID()));
                } else {
                        return !tfPersonalDoctor.getText().equals("");
                }
        }

        @FXML
        public void action_cancel(ActionEvent actionEvent) {
                EPAController ePAController = gUIController.getEPAController();
                // This view can only be accessed by patients
                Patient patient = (Patient)ePAController.getEPA().getActiveUser();
                if (patient.isFirstLogin()) {
                        stage.close();
                        gUIController.getLoginViewController().show();
                } else {
                        stage.close();
                        gUIController.getHomepageController().show();
                }
        }

        @Override
        public void update() {
                EPAController ePAController = gUIController.getEPAController();
                Patient patient = ePAController.getEPA().getLoadedPatient();
                char semicolon = ';';
                // Name:
                StringBuilder sBLastName = new StringBuilder();
                StringBuilder sBFirstName = new StringBuilder();
                boolean isLastName = true;
                boolean isFirstName = false;
                for(char c : patient.getName().toCharArray()) {
                        if(c == semicolon) {
                                isLastName = false;
                                isFirstName = true;
                        } else {
                                if(isLastName) {
                                        sBLastName.append(c);
                                } else if(isFirstName) {
                                        sBFirstName.append(c);
                                }
                        }
                }
                String lastName = sBLastName.toString();
                String firstName = sBFirstName.toString();
                tfFirstName.setText(firstName);
                tfLastName.setText(lastName);
                // Address:
                StringBuilder sBStreetName = new StringBuilder();
                StringBuilder sBHouseNumber = new StringBuilder();
                StringBuilder sBPostalCode = new StringBuilder();
                StringBuilder sBCityName = new StringBuilder();
                boolean isStreetName = true;
                boolean isHouseNumber = false;
                boolean isPostalCode = false;
                boolean isCityName = false;
                for(char c : patient.getAddress().toCharArray()) {
                        if(c == semicolon) {
                                if(isStreetName) {
                                        isStreetName = false;
                                        isHouseNumber = true;
                                } else if(isHouseNumber) {
                                        isHouseNumber = false;
                                        isPostalCode = true;
                                } else if(isPostalCode) {
                                        isPostalCode = false;
                                        isCityName = true;
                                }
                        } else {
                                if(isStreetName) {
                                        sBStreetName.append(c);
                                } else if(isHouseNumber) {
                                        sBHouseNumber.append(c);
                                } else if(isPostalCode) {
                                        sBPostalCode.append(c);
                                } else if(isCityName) {
                                        sBCityName.append(c);
                                }
                        }
                }
                String streetName = sBStreetName.toString();
                String houseNumber = sBHouseNumber.toString();
                String postalCode = sBPostalCode.toString();
                String cityName = sBCityName.toString();
                tfStreet.setText(streetName);
                tfHouseNumber.setText(houseNumber);
                tfPostalCode.setText(postalCode);
                tfCityName.setText(cityName);
                tfInsuranceName.setText(patient.getInsuranceName());
                tfInsuranceNumber.setText(patient.getInsuranceNumber());
                dpBirthday.setValue(patient.getBirthday());
                tfSex.setText(patient.getSex());
                if(patient.getPersonalDoctor() != null) {
                        tfPersonalDoctor.setText(String.valueOf(patient.getPersonalDoctor().getUserID()));
                } else {
                        tfPersonalDoctor.setText(null);
                }
        }

        @FXML
        void initialize() {
        }
}

