package view;

import javafx.stage.Stage;

public abstract class ViewController {
    protected GUIController gUIController;
    protected Stage stage;

    public void setGUIController(GUIController gUIController) {
        this.gUIController = gUIController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void show() {
        stage.show();
    }

    public void showAndWait() {
        stage.showAndWait();
    }

    abstract void update();
}
