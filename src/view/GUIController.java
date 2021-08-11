package view;

import controller.EPAController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUIController {
    private final EPAController ePAController;
    private final Stage primaryStage;
    private HomepageController homepageController;
    private LogInspectorController logInspectorController;
    private LoginViewController loginViewController;
    private MedizinischeDatenDruckenController medizinischeDatenDruckenController;
    private PatientenDatenController patientenDatenController;
    private PermissionsInterfaceController permissionsInterfaceController;
    private ReferralViewController referralViewController;
    private RegisterViewController registerViewController;
    private ReportViewController reportViewController;
    private SwitchEPAController switchEPAController;
    private TreatmentEntryViewController treatmentEntryViewController;

    public GUIController(EPAController ePAController, Stage primaryStage) {
        this.ePAController = ePAController;
        this.primaryStage = primaryStage;
        init();
    }

    private void init() {
        // First view; uses the primary stage of this application
        loginViewController = (LoginViewController)initView(primaryStage,"/view/Login.fxml",null,"Login");

        // Universal report view; prohibits use of other windows of this application while shown
        reportViewController = (ReportViewController)initView("/view/ReportView.fxml",null,"Meldung");
        reportViewController.getStage().initModality(Modality.APPLICATION_MODAL);

        homepageController = (HomepageController)initView("/view/Homepage.fxml",null,"Ansicht: EPA");
        logInspectorController = (LogInspectorController)initView("/view/LogInspector.fxml",null,"Ansicht: Log");
        medizinischeDatenDruckenController = (MedizinischeDatenDruckenController)initView("/view/MedizinischeDatenDrucken.fxml",null,
                "Drucken");
        patientenDatenController = (PatientenDatenController)initView("/view/Patientendaten.fxml",null,
                "Bearbeitung: Persönliche Daten");
        permissionsInterfaceController = (PermissionsInterfaceController)initView("/view/PermissionsInterface.fxml",null,
                "Überweisung: Datenfreigabe");
        referralViewController = (ReferralViewController)initView("/view/ReferralView.fxml",null,"Überweisung: Zielarzt");
        registerViewController = (RegisterViewController)initView("/view/Register.fxml",null,"Registrierung");
        switchEPAController = (SwitchEPAController)initView("/view/SwitchEPA.fxml",null,"Ansicht: Andere EPA laden");
        treatmentEntryViewController = (TreatmentEntryViewController)initView("/view/TreatmentEntryView.fxml",null,
                "Bearbeitung: Behandlungseintrag erstellen");
    }

    private ViewController initView(String resourcePath, String cssPath, String title) {
        return initView(new Stage(),resourcePath,cssPath,title);
    }

    private ViewController initView(Stage stage, String resourcePath, String cssPath, String title) {
        ViewController viewController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resourcePath));
            Parent root = fxmlLoader.load();
            viewController = fxmlLoader.getController();
            viewController.setGUIController(this);
            viewController.setStage(stage);

            Scene scene = new Scene(root);
            if(cssPath != null) {
                scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            }
            stage.setTitle(title);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewController;
    }

    public EPAController getEPAController() {
        return ePAController;
    }

    public HomepageController getHomepageController() {
        return homepageController;
    }

    public LogInspectorController getLogInspectorController() {
        return logInspectorController;
    }

    public LoginViewController getLoginViewController() {
        return loginViewController;
    }

    public MedizinischeDatenDruckenController getMedizinischeDatenDruckenController() {
        return medizinischeDatenDruckenController;
    }

    public PatientenDatenController getPatientenDatenController() {
        return patientenDatenController;
    }

    public PermissionsInterfaceController getPermissionsInterfaceController() {
        return permissionsInterfaceController;
    }

    public ReferralViewController getReferralViewController() {
        return referralViewController;
    }

    public RegisterViewController getRegisterViewController() {
        return registerViewController;
    }

    public ReportViewController getReportViewController() {
        return reportViewController;
    }

    public SwitchEPAController getSwitchEPAController() {
        return switchEPAController;
    }

    public TreatmentEntryViewController getTreatmentEntryViewController() {
        return treatmentEntryViewController;
    }
}
