package view;

import java.util.ArrayList;
import java.util.HashMap;

import controller.EPAController;
import controller.PermissionController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import model.Doctor;
import model.Permission;
import model.TreatmentEntry;


public class PermissionsInterfaceController extends ViewController {
    @FXML
    private Pane mainPane;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveButton;

    @FXML
    private TextArea searchbarText;

    @FXML
    private CheckBox dateAndTimeCheckbox;

    @FXML
    private CheckBox timeCheckbox;

    @FXML
    private CheckBox visitReasonCheckbox;

    @FXML
    private CheckBox icdCodeCheckbox;

    @FXML
    private CheckBox treatmentCheckbox;

    @FXML
    private CheckBox notesCheckbox;

    @FXML
    private TextArea doctorIDText;

    @FXML
    private ListView treatmentsList;

    private ArrayList<TreatmentEntry> treatmentEntries;
    private TreatmentEntry treatmentEntry;
    private Doctor doctor;
    private HashMap<String,TreatmentEntry> treatmentEntryHashMap;

    @FXML
    void action_cancel(ActionEvent event) {
        stage.close();
        gUIController.getHomepageController().show();
    }

    @FXML
    void saveClicked(ActionEvent event){
        EPAController ePAController = gUIController.getEPAController();
        HomepageController homepageController = gUIController.getHomepageController();
        homepageController.update();
        stage.close();
        homepageController.show();
    }

    @FXML
    void selectedTreatmentEntry() {
        String selectedEntry = (String)treatmentsList.getSelectionModel().getSelectedItem();
        if(selectedEntry != null) {
            dateAndTimeCheckbox.setVisible(true);
            visitReasonCheckbox.setVisible(true);
            icdCodeCheckbox.setVisible(true);
            treatmentCheckbox.setVisible(true);
            notesCheckbox.setVisible(true);
            treatmentEntry = treatmentEntryHashMap.get(selectedEntry);
            Permission currentPermission = null;
            for (Permission permission : treatmentEntry.getPermissions()) {
                if (permission.getDoctor().equals(doctor)) {
                    currentPermission = permission;
                    break;
                }
            }
            if (treatmentEntry.getCreator().equals(doctor)) {
                dateAndTimeCheckbox.setSelected(true);
                visitReasonCheckbox.setSelected(true);
                icdCodeCheckbox.setSelected(true);
                treatmentCheckbox.setSelected(true);
                notesCheckbox.setSelected(true);
            } else if (currentPermission == null) {
                dateAndTimeCheckbox.setSelected(false);
                visitReasonCheckbox.setSelected(false);
                icdCodeCheckbox.setSelected(false);
                treatmentCheckbox.setSelected(false);
                notesCheckbox.setSelected(false);
            } else {
                dateAndTimeCheckbox.setSelected(currentPermission.hasAccessToDateAndTime());
                visitReasonCheckbox.setSelected(currentPermission.hasAccessToReason());
                icdCodeCheckbox.setSelected(currentPermission.hasAccessToDiagnosis());
                treatmentCheckbox.setSelected(currentPermission.hasAccessToTreatment());
                notesCheckbox.setSelected(currentPermission.hasAccessToNotes());
            }
        }
    }

    @FXML
    public void searchClicked(ActionEvent actionEvent) {
        EPAController ePAController = gUIController.getEPAController();
        String toFind = searchbarText.getText();
        treatmentEntries = ePAController.getTreatmentEntryController().searchForTreatmentEntry(
                ePAController.getEPA().getLoadedPatient(),
                toFind
        );
        this.refreshList(treatmentEntries);
    }

    private void savePermission(CheckBox checkBox) {
        if(treatmentEntry.getCreator().equals(doctor)) {
            checkBox.setSelected(true);
            gUIController.getEPAController().prompt("FEHLER:\nDie Rechte des Arztes, der den Behandlungseintrag erstellt hat,\nkönnen nicht eingeschränkt werden.");
            return;
        }
        PermissionController permissionController = gUIController.getEPAController().getPermissionController();
        permissionController.newPermission(
                treatmentEntry,
                doctor,
                dateAndTimeCheckbox.isSelected(),
                visitReasonCheckbox.isSelected(),
                icdCodeCheckbox.isSelected(),
                treatmentCheckbox.isSelected(),
                notesCheckbox.isSelected()
        );
    }

    @FXML
    void dateAndTimeClicked(ActionEvent event) {
        this.savePermission(dateAndTimeCheckbox);
    }

    @FXML
    void reasonClicked(ActionEvent event) {
        this.savePermission(visitReasonCheckbox);
    }

    @FXML
    void diagnosisClicked(ActionEvent event) {
        this.savePermission(icdCodeCheckbox);
    }

    @FXML
    void treatmentClicked(ActionEvent event) {
        this.savePermission(treatmentCheckbox);
    }

    @FXML
    void notesClicked(ActionEvent event) {
        this.savePermission(notesCheckbox);
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        doctorIDText.setText(String.valueOf(doctor.getUserID()));
    }

    //methode to show allowed treatmentEntries to the logged in User
    private void refreshList(ArrayList<TreatmentEntry> treatmentEntries){
        treatmentsList.getItems().clear();
        treatmentEntryHashMap.clear();
        if(!treatmentEntries.isEmpty())
            for(TreatmentEntry t: treatmentEntries) {
                String toShow = t.getDateAndTime().toString();
                toShow= Integer.toString(treatmentEntries.indexOf(t)+1) + "- Behandlungseintrag vom " +toShow.replace("T"," um " );
                treatmentsList.getItems().add(toShow);
                treatmentEntryHashMap.put(toShow,t); //Hashmapping the shown strings with the related treatmentEntries, will be used for selection from list
            }
    }

    @Override
    public void update() {
        treatmentEntries = gUIController.getEPAController().getTreatmentEntryController().searchForTreatmentEntryWithPermission();
        this.treatmentEntry = null;
        this.doctor = null;
        searchbarText.setText(null);
        dateAndTimeCheckbox.setVisible(false);
        visitReasonCheckbox.setVisible(false);
        icdCodeCheckbox.setVisible(false);
        treatmentCheckbox.setVisible(false);
        notesCheckbox.setVisible(false);
        this.refreshList(treatmentEntries);
    }

    @FXML
    void initialize() {
        treatmentsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        treatmentEntryHashMap = new HashMap<>();
        doctorIDText.setEditable(false);
    }
}

