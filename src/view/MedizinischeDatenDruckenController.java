package view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import controller.EPAController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * @author Thomas Alexander Hövelmann
 */
public class MedizinischeDatenDruckenController extends ViewController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private DatePicker dpStartTime;

    @FXML
    private DatePicker dpEndTime;

    @FXML
    private Button bttPrint;

    @FXML
    private Button bttCancel;

    @FXML
    void action_print(ActionEvent event){
        LocalDate startDate = dpStartTime.getValue();
        LocalDate endDate = dpEndTime.getValue();
        if((!(startDate == null) && !(endDate == null)) && startDate.isAfter(endDate)) {
            gUIController.getEPAController().prompt("FEHLER!\nDas Startdatum muss vor oder auf dem Enddatum liegen.");
            return;
        }
        if (startDate == null && endDate == null){//no time interval defined
            gUIController.getEPAController().printMedicalReport(null, null);
        }
        else if (startDate == null){//case only endDate defined
            gUIController.getEPAController().printMedicalReport(null, endDate.atTime(23,59,59));
        }
        else if (endDate == null){//case only startDate defined
            gUIController.getEPAController().printMedicalReport(startDate.atStartOfDay(), null);
        }
        else{//case startDate and endDate defined
            gUIController.getEPAController().printMedicalReport(startDate.atStartOfDay(), endDate.atTime(23,59,59));
        }
        action_cancel(event);
    }

    @FXML
    void action_cancel(ActionEvent actionEvent) {
        HomepageController homepageController = gUIController.getHomepageController();
        homepageController.update();
        stage.close();
        homepageController.show();
    }

    @Override
    public void update() {
        dpStartTime.setValue(null);
        dpEndTime.setValue(null);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    }
}

