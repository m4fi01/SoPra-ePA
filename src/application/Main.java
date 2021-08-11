package application;

import controller.EPAController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.GUIController;

/**
 * @author Thomas Alexander Hövelmann
 */
public class Main extends Application {

    //hat Spaß gemacht :D
    /**
 	 *
 	 * TODO: create JavaDoc. 
 	 * @param primaryStage first application window, requirement for the use of FXML
 	 */
    @Override
    public void start(Stage primaryStage) {
        EPAController ePACtrl = new EPAController(primaryStage);
        primaryStage.show();
    }

    /**
 	 *
 	 * TODO: create JavaDoc. 
 	 * @param args Starting arguments for the main method
 	 */
    public static void main(String[] args) {
        launch(args);
    }
}
