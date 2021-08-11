package view;

import controller.EPAController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;


import javafx.event.ActionEvent;
import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class HomepageController extends ViewController {
    @FXML
    private TextArea patientInfoText;
    @FXML
    private TextArea searchbarText;
    @FXML
    private Pane mainPane;
    @FXML
    private Button editPersonalDataButton;
    @FXML
    private Button printButton;
    @FXML
    private Button transferButton;
    @FXML
    private Button switchEPAButton;
    @FXML
    private Button addButton;
    @FXML
    private Button searchButton;
    @FXML
    private ListView treatmentsList;
    @FXML
    private Button editEntryButton;

    private HashMap<String,TreatmentEntry> treatmentEntryHashMap;


    @FXML
    public void transferClicked(ActionEvent event) {
        ReferralViewController referralViewController = gUIController.getReferralViewController();
        referralViewController.update();
        stage.close();
        referralViewController.show();
    }

    @FXML
    public void logoutClicked(ActionEvent actionEvent) {
        stage.close();
        gUIController.getLoginViewController().show();
    }

    @FXML
    public void printClicked(ActionEvent actionEvent) {
        stage.close();
        gUIController.getMedizinischeDatenDruckenController().show();
        gUIController.getMedizinischeDatenDruckenController().update();
    }

    @FXML
    public void epaChangeClicked(ActionEvent actionEvent) {
        stage.close();
        gUIController.getSwitchEPAController().show();
    }

    @FXML
    public void logClicked(ActionEvent actionEvent) {
        stage.close();
        gUIController.getLogInspectorController().update();
        gUIController.getLogInspectorController().show();
    }

    @FXML
    public void searchClicked(ActionEvent actionEvent) {
        String toFind = searchbarText.getText();
        ArrayList<TreatmentEntry> treatmentEntries = new ArrayList<>();
        treatmentEntries = gUIController.getEPAController().getTreatmentEntryController().
                    searchForTreatmentEntry(gUIController.getEPAController().getEPA().getLoadedPatient(), toFind);
        this.refreshList(treatmentEntries);
    }

    @FXML
    public void editClicked(ActionEvent actionEvent) {
        PatientenDatenController patientenDatenController = gUIController.getPatientenDatenController();
        patientenDatenController.update();
        stage.close();
        patientenDatenController.show();
    }

    @FXML
    public void addClicked(ActionEvent actionEvent) {
        stage.close();
        gUIController.getTreatmentEntryViewController().update();
        gUIController.getTreatmentEntryViewController().show();
    }


    @FXML
    public void editEntryClicked(ActionEvent actionEvent) {
        try {
            if(treatmentsList.getSelectionModel().isEmpty()) {
                gUIController.getEPAController().prompt("bitte Behandlungseintrag auswählen");
            }
            else {
                String key = treatmentsList.getSelectionModel().getSelectedItem().toString();
                TreatmentEntry treatmentEntry = treatmentEntryHashMap.get(key);
                stage.close();
                gUIController.getTreatmentEntryViewController().update();
                gUIController.getTreatmentEntryViewController().show();
                gUIController.getTreatmentEntryViewController().setTreatmentEntry(treatmentEntry);
                gUIController.getTreatmentEntryViewController().setEditTEntry(true);
            }
        }
        catch (NullPointerException npe){

        }
    }

    @Override
    public void update() {
        EPAController ePAController = gUIController.getEPAController();
        User activeUser = ePAController.getEPA().getActiveUser();
        ArrayList<TreatmentEntry> treatmentEntries;
        treatmentEntryHashMap = new HashMap<>();
        searchbarText.setText(null);
        if(activeUser instanceof Doctor) {
            treatmentEntries = ePAController.getTreatmentEntryController().searchForTreatmentEntryWithPermission();
            editPersonalDataButton.setVisible(false);
            printButton.setVisible(false);
            searchButton.setVisible(true);
            searchbarText.setVisible(true);
            transferButton.setVisible(true);
            switchEPAButton.setVisible(true);
            addButton.setVisible(true);
            editEntryButton.setVisible(true);
        } else if(activeUser instanceof Patient) {
            treatmentEntries = ((Patient)activeUser).getTreatmentEntries();
            transferButton.setVisible(false);
            switchEPAButton.setVisible(false);
            addButton.setVisible(false);
            searchbarText.setVisible(false);
            searchButton.setVisible(false);
            editPersonalDataButton.setVisible(true);
            printButton.setVisible(true);
            editEntryButton.setVisible(false);
        } else {
            return;
        }
        this.refreshList(treatmentEntries);
        this.updatePatientInfo();
    }

    //methode to show allowed treatmentEntries to the logged in User
    private void refreshList(ArrayList<TreatmentEntry> treatmentEntries){
        treatmentsList.getItems().clear();
        treatmentEntryHashMap.clear();
        if(!treatmentEntries.isEmpty())
          for(TreatmentEntry t: treatmentEntries) {
              String toShow = t.getDateAndTime().toString();
              toShow= Integer.toString(treatmentEntries.indexOf(t)+1) + "- Behandlungseintrag vom " + toShow.replace("T"," um ") + " Uhr bei " + t.getCreator().toString();
              treatmentsList.getItems().add(toShow);
              treatmentEntryHashMap.put(toShow,t); //Hashmapping the shown strings with the related treatmentEntries, will be used for selection from list
          }
    }

    @FXML
    private void updatePatientInfo(){
        patientInfoText.setText(gUIController.getEPAController().getEPA().getLoadedPatient().personalDataToString());
    }

    @FXML
    void initialize() {
        treatmentsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

}
