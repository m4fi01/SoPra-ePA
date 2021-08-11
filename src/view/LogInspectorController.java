package view;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import controller.EPAController;
import controller.LogEntryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;

/**
 * @author Thomas Alexander Hövelmann, Leon Krick
 */
public class LogInspectorController extends ViewController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField changeDateTF;

    @FXML
    private TextField changeTimeTF;

    @FXML
    private TextField editorIDTF;

    @FXML
    private TextField firstNameTF;

    @FXML
    private TextField lastNameTF;

    @FXML
    private TextField streetTF;

    @FXML
    private TextField houseNumberTF;

    @FXML
    private TextField cityTF;

    @FXML
    private TextField postCodeTF;

    @FXML
    private TextField insuranceNumberTF;

    @FXML
    private TextField birthdayTF;

    @FXML
    private TextField sexTF;

    @FXML
    private TextField personalDoctorTF;

    @FXML
    private TextField dateTF;

    @FXML
    private TextField timeTF;

    @FXML
    private TextField symptomTF;

    @FXML
    private TextField iCDCodeTF;

    @FXML
    private TextField treatmentTF;

    @FXML
    private TextArea noteTF;

    @FXML
    private Label changeDateLb;

    @FXML
    private Label changeTimeLb;

    @FXML
    private Label editorIDLb;

    @FXML
    private Label firstNameLb;

    @FXML
    private Label lastNameLb;

    @FXML
    private Label streetLb;

    @FXML
    private Label houseNumberLb;

    @FXML
    private Label cityLb;

    @FXML
    private Label postCodeLb;

    @FXML
    private Label insuranceNumberLb;

    @FXML
    private Label birthdayLb;

    @FXML
    private Label sexLb;

    @FXML
    private Label personalDoctorLb;

    @FXML
    private Label dateLb;

    @FXML
    private Label timeLb;

    @FXML
    private Label symptomLb;

    @FXML
    private Label iCDCodeLb;

    @FXML
    private Label treatmentLb;

    @FXML
    private Label noteLb;

    @FXML
    private ListView logListView;

    HashMap<String, LogEntry> logEntryHashMap;

    @FXML
    void pressedCancel() {
        HomepageController homepageController = gUIController.getHomepageController();
        homepageController.update();
        stage.close();
        homepageController.show();
    }

    @FXML
    void listItemClicked(){//TODO fill with respective information
        Object selectedItem = logListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null){return;}
        String key = selectedItem.toString();
        LogEntry logEntry = logEntryHashMap.get(key);
        if(logEntry instanceof PatientLogEntry){
            updateInformation(false);
            PatientLogEntry patientLogEntry = (PatientLogEntry) logEntry;
            Patient oldPatient = patientLogEntry.getOldPatient();
            // Name:
            StringBuilder sBLastName = new StringBuilder();
            StringBuilder sBFirstName = new StringBuilder();
            String name = oldPatient.getName();
            char semicolon = ';';
            boolean isLastName = true;
            boolean isFirstName = false;
            for(char c : name.toCharArray()) {
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
            LocalDateTime localDateTime = patientLogEntry.getChangeDateAndTime();
            String address = oldPatient.getAddress();
            // Address data:
            StringBuilder sBStreetName = new StringBuilder();
            StringBuilder sBHouseNumber = new StringBuilder();
            StringBuilder sBPostalCode = new StringBuilder();
            StringBuilder sBCityName = new StringBuilder();
            boolean isStreetName = true;
            boolean isHouseNumber = false;
            boolean isPostalCode = false;
            boolean isCityName = false;
            for(char c : address.toCharArray()) {
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
            changeDateTF.setText(localDateTime.toLocalDate().toString());
            changeTimeTF.setText(localDateTime.toLocalTime().toString());
            editorIDTF.setText("" + patientLogEntry.getEditor().getUserID());
            firstNameTF.setText(firstName);
            lastNameTF.setText(lastName);
            streetTF.setText(streetName);
            houseNumberTF.setText(houseNumber);
            postCodeTF.setText(postalCode);
            cityTF.setText(cityName);
            insuranceNumberTF.setText(oldPatient.getInsuranceNumber());
            birthdayTF.setText(oldPatient.getBirthday().toString());
            sexTF.setText(oldPatient.getSex());
            personalDoctorTF.setText("" + oldPatient.getPersonalDoctor().getUserID());
        }
        else if(logEntry instanceof TreatmentLogEntry){
            updateInformation(true);
            TreatmentLogEntry treatmentLogEntry = (TreatmentLogEntry) logEntry;
            TreatmentEntry oldTreatmentEntry = treatmentLogEntry.getOldTreatmentEntry();
            LocalDateTime localDateTime = treatmentLogEntry.getChangeDateAndTime();
            changeDateTF.setText(localDateTime.toLocalDate().toString());
            changeTimeTF.setText(localDateTime.toLocalTime().toString());
            editorIDTF.setText("" + treatmentLogEntry.getEditor().getUserID());
            dateTF.setText(oldTreatmentEntry.getDateAndTime().toLocalDate().toString());
            timeTF.setText(oldTreatmentEntry.getDateAndTime().toLocalTime().toString());
            symptomTF.setText(oldTreatmentEntry.getReason());
            iCDCodeTF.setText(oldTreatmentEntry.getDiagnosis());
            treatmentTF.setText(oldTreatmentEntry.getTreatment());
            noteTF.setText(oldTreatmentEntry.getNotes());
        }
        changeDateLb.setVisible(true);
        changeDateTF.setVisible(true);
        changeTimeLb.setVisible(true);
        changeTimeTF.setVisible(true);
        editorIDLb.setVisible(true);
        editorIDTF.setVisible(true);
    }

    public void updateInformation(boolean isTreatmentLogEntry){
            firstNameLb.setVisible(!isTreatmentLogEntry);
            firstNameTF.setVisible(!isTreatmentLogEntry);
            lastNameLb.setVisible(!isTreatmentLogEntry);
            lastNameTF.setVisible(!isTreatmentLogEntry);
            streetLb.setVisible(!isTreatmentLogEntry);
            streetTF.setVisible(!isTreatmentLogEntry);
            houseNumberLb.setVisible(!isTreatmentLogEntry);
            houseNumberTF.setVisible(!isTreatmentLogEntry);
            cityLb.setVisible(!isTreatmentLogEntry);
            cityTF.setVisible(!isTreatmentLogEntry);
            postCodeLb.setVisible(!isTreatmentLogEntry);
            postCodeTF.setVisible(!isTreatmentLogEntry);
            insuranceNumberLb.setVisible(!isTreatmentLogEntry);
            insuranceNumberTF.setVisible(!isTreatmentLogEntry);
            birthdayLb.setVisible(!isTreatmentLogEntry);
            birthdayTF.setVisible(!isTreatmentLogEntry);
            sexLb.setVisible(!isTreatmentLogEntry);
            sexTF.setVisible(!isTreatmentLogEntry);
            personalDoctorLb.setVisible(!isTreatmentLogEntry);
            personalDoctorTF.setVisible(!isTreatmentLogEntry);

            dateLb.setVisible(isTreatmentLogEntry);
            dateTF.setVisible(isTreatmentLogEntry);
            timeLb.setVisible(isTreatmentLogEntry);
            timeTF.setVisible(isTreatmentLogEntry);
            symptomLb.setVisible(isTreatmentLogEntry);
            symptomTF.setVisible(isTreatmentLogEntry);
            iCDCodeLb.setVisible(isTreatmentLogEntry);
            iCDCodeTF.setVisible(isTreatmentLogEntry);
            treatmentLb.setVisible(isTreatmentLogEntry);
            treatmentTF.setVisible(isTreatmentLogEntry);
            noteLb.setVisible(isTreatmentLogEntry);
            noteTF.setVisible(isTreatmentLogEntry);
    }

    @Override
    public void update() {
        LogEntryController logEntryController = gUIController.getEPAController().getLogEntryController();
        ArrayList<LogEntry> logEntries = logEntryController.loadPermittedLogEntries(gUIController.getEPAController().getEPA().getLoadedPatient());
        logEntryHashMap = new HashMap<>();
        refreshList(logEntries);
        changeDateLb.setVisible(false);
        changeDateTF.setVisible(false);
        changeTimeLb.setVisible(false);
        changeTimeTF.setVisible(false);
        editorIDLb.setVisible(false);
        editorIDTF.setVisible(false);

        firstNameLb.setVisible(false);
        firstNameTF.setVisible(false);
        lastNameLb.setVisible(false);
        lastNameTF.setVisible(false);
        streetLb.setVisible(false);
        streetTF.setVisible(false);
        houseNumberLb.setVisible(false);
        houseNumberTF.setVisible(false);
        cityLb.setVisible(false);
        cityTF.setVisible(false);
        postCodeLb.setVisible(false);
        postCodeTF.setVisible(false);
        insuranceNumberLb.setVisible(false);
        insuranceNumberTF.setVisible(false);
        birthdayLb.setVisible(false);
        birthdayTF.setVisible(false);
        sexLb.setVisible(false);
        sexTF.setVisible(false);
        personalDoctorLb.setVisible(false);
        personalDoctorTF.setVisible(false);

        dateLb.setVisible(false);
        dateTF.setVisible(false);
        timeLb.setVisible(false);
        timeTF.setVisible(false);
        symptomLb.setVisible(false);
        symptomTF.setVisible(false);
        iCDCodeLb.setVisible(false);
        iCDCodeTF.setVisible(false);
        treatmentLb.setVisible(false);
        treatmentTF.setVisible(false);
        noteLb.setVisible(false);
        noteTF.setVisible(false);
    }

    //method to show given logEntries to the logged in User
    private void refreshList(ArrayList<LogEntry> logEntries){
        logListView.getItems().clear();
        logEntryHashMap.clear();
        logListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        if(!logEntries.isEmpty())
            for(LogEntry l: logEntries) {
                String toShow = "";
                if(l instanceof PatientLogEntry){
                    PatientLogEntry patientLogEntry = (PatientLogEntry) l;
                    toShow = Integer.toString(logEntries.indexOf(l) + 1) + "- Patientendatenänderung vom " + patientLogEntry.getChangeDateAndTime().toString().replace("T", " um ");
                }
                else if(l instanceof TreatmentLogEntry){
                    TreatmentLogEntry treatmentLogEntry = (TreatmentLogEntry) l;
                    toShow = Integer.toString(logEntries.indexOf(l) + 1) + "- Behandlungseintragsänderung vom " + treatmentLogEntry.getChangeDateAndTime().toString().replace("T", " um ");
                }
                logListView.getItems().add(toShow);
                logEntryHashMap.put(toShow,l); //Hashmapping the shown strings with the related logEntries, will be used for selection from list
            }
    }

    @FXML
    void initialize() {
        assert changeDateTF != null : "fx:id=\"changeDateTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert changeTimeTF != null : "fx:id=\"changeTimeTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert editorIDTF != null : "fx:id=\"editorIDTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert firstNameTF != null : "fx:id=\"firstNameTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert lastNameTF != null : "fx:id=\"lastNameTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert streetTF != null : "fx:id=\"streetTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert houseNumberTF != null : "fx:id=\"houseNumberTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert cityTF != null : "fx:id=\"cityTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert postCodeTF != null : "fx:id=\"postCodeTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert insuranceNumberTF != null : "fx:id=\"insuranceNumberTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert birthdayTF != null : "fx:id=\"birthdayTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert sexTF != null : "fx:id=\"sexTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert personalDoctorTF != null : "fx:id=\"personalDoctorTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert dateTF != null : "fx:id=\"dateTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert timeTF != null : "fx:id=\"timeTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert symptomTF != null : "fx:id=\"symptomTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert iCDCodeTF != null : "fx:id=\"iCDCodeTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert treatmentTF != null : "fx:id=\"treatmentTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
        assert noteTF != null : "fx:id=\"noteTF\" was not injected: check your FXML file 'LogInspector.fxml'.";
    }
}
