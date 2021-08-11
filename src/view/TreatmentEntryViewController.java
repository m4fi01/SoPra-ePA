package view;

import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import controller.EPAController;
import controller.TreatmentEntryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Patient;
import model.TreatmentEntry;

/**
 * @author Thomas Alexander Hövelmann
 */
public class TreatmentEntryViewController extends ViewController {
    private boolean editTEntry;  //needs to be set to true if a treatment entry should be edited
    private TreatmentEntry tEntry; // needs to be the treatment entry that should be edited

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label labelDate;

    @FXML
    private Label labelTime;

    @FXML
    private Label labelReason;

    @FXML
    private Label labelDiagnosis;

    @FXML
    private Label labelTreatment;

    @FXML
    private Label labelNotes;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TextField textFieldTime;

    @FXML
    private TextField textFieldReason;

    @FXML
    private TextField textFieldDiagnosis;

    @FXML
    private TextField textFieldTreatment;

    @FXML
    private TextArea textareaNotes;

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
        EPAController ePAController = gUIController.getEPAController();
        TreatmentEntryController TEntryCtrl = ePAController.getTreatmentEntryController();
        LocalDate lDate = datepicker.getValue();
        String sTime = textFieldTime.getText();
        try {
            if(!(lDate == null || sTime == null)) {
                LocalTime lTime = LocalTime.parse(sTime);
                LocalDateTime date = LocalDateTime.of(lDate, lTime);
                Patient patient = ePAController.getEPA().getLoadedPatient();

                if (editTEntry) {
                    if(checkForChanges()) {
                        //edit given TreatmentEntry
                        TEntryCtrl.editTreatmentEntry(tEntry, date, textFieldReason.getText(), textFieldDiagnosis.getText(), textFieldTreatment.getText(), textareaNotes.getText());
                    }
                    HomepageController homepageController = gUIController.getHomepageController();
                    homepageController.update();
                    stage.close();
                    homepageController.show();
                } else {
                    //create new TreatmentEntry
                    TEntryCtrl.createTreatmentEntry(patient, date, textFieldReason.getText(), textFieldDiagnosis.getText(), textFieldTreatment.getText(), textareaNotes.getText());
                    HomepageController homepageController = gUIController.getHomepageController();
                    homepageController.update();
                    stage.close();
                    homepageController.show();
                }
            }
            else {
                ePAController.prompt("Es muss ein Datum und eine Uhrzeit eingegeben werden");
            }
        }
        catch(DateTimeException dTException) {
            ePAController.prompt("Die Uhrzeit ist ungültig");
        }

    }

    private boolean checkForChanges() {
        if(tEntry != null) {
            if(!datepicker.getValue().equals(tEntry.getDateAndTime().toLocalDate())) {
                return true;
            }
            if(!textFieldTime.getText().equals(tEntry.getDateAndTime().toLocalTime().toString())) {
                return true;
            }
            if(!textFieldReason.getText().equals(tEntry.getReason())) {
                return true;
            }
            if(!textFieldDiagnosis.getText().equals(tEntry.getDiagnosis())) {
                return true;
            }
            if(!textFieldTreatment.getText().equals(tEntry.getTreatment())) {
                return true;
            }
            if(!textareaNotes.getText().equals(tEntry.getNotes())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        editTEntry = false;
        tEntry = null;
        datepicker.setValue(null);
        textFieldTime.setText(null);
        textFieldReason.setText(null);
        textFieldDiagnosis.setText(null);
        textFieldTreatment.setText(null);
        textareaNotes.setText(null);
    }

    @FXML
    void initialize() {
        assert labelDate != null : "fx:id=\"labelDate\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert labelTime != null : "fx:id=\"labelTime\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert labelReason != null : "fx:id=\"labelReason\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert labelDiagnosis != null : "fx:id=\"labelDiagnosis\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert labelTreatment != null : "fx:id=\"labelTreatment\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert labelNotes != null : "fx:id=\"labelNotes\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert datepicker != null : "fx:id=\"datepicker\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert textFieldTime != null : "fx:id=\"textFieldTime\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert textFieldReason != null : "fx:id=\"textFieldReason\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert textFieldDiagnosis != null : "fx:id=\"textFieldDiagnosis\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert textFieldTreatment != null : "fx:id=\"textFieldTreatment\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert textareaNotes != null : "fx:id=\"textareaNotes\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert buttonCancel != null : "fx:id=\"buttonCancel\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";
        assert buttonConfirm != null : "fx:id=\"buttonConfirm\" was not injected: check your FXML file 'TreatmentEntryView.fxml'.";

    }

    public void setEditTEntry(boolean editTEntry) {
        this.editTEntry = editTEntry;
    }

    public void setTreatmentEntry(TreatmentEntry tEntry) {
        this.tEntry = tEntry;

        datepicker.setValue(tEntry.getDateAndTime().toLocalDate());
        textFieldTime.setText(tEntry.getDateAndTime().toLocalTime().toString());
        textFieldReason.setText(tEntry.getReason());
        textFieldDiagnosis.setText(tEntry.getDiagnosis());
        textFieldTreatment.setText(tEntry.getTreatment());
        textareaNotes.setText(tEntry.getNotes());
    }
}

