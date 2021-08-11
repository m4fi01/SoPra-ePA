package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author Thomas Alexander Hövelmann
 */
public class ReportViewController extends ViewController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label labelReport;

    @FXML
    private Button buttonOk;

    @FXML
    void buttonOkPressed(ActionEvent event) {
        stage.close();
    }

    @FXML
    void initialize() {
    }

    @Override
    public void update() {

    }

    public void showMessage(String msg) {
        labelReport.setText(msg);
        stage.showAndWait();
    }
}
