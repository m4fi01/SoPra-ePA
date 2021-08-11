package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import view.GUIController;
import view.ReportViewController;

import javax.print.Doc;
import java.io.FileNotFoundException;
import java.lang.UnsupportedOperationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author  Thomas Alexander Hövelmann
 *
 * @see EPA
 * @see PermissionController
 * @see TreatmentEntryController
 * @see LogEntryController
 * @see IOController
 * @see PatientController
 */
public class EPAController {
	/**
	 * Base value for the generation of new user identification numbers
	 */
	private static final int NEW_USER_ID_OFFSET = 1024;

    /**
 	 * Reference to the application's {@link EPA}-object.
 	 */
    private final EPA ePA;

    /**
 	 * Reference to the application's {@link PermissionController}-object.
 	 */
    private PermissionController permissionController;

    /**
 	 * Reference to the application's {@link TreatmentEntryController}-object.
 	 */
    private TreatmentEntryController treatmentEntryController;

    /**
 	 * Reference to the application's {@link LogEntryController}-object.
 	 */
    private LogEntryController logEntryController;

    /**
 	 * Reference to the application's {@link IOController}-object.
 	 */
    private IOController iOController;

    /**
 	 * Reference to the application's {@link PatientController}-object.
 	 */
    private PatientController patientController;

	/**
	 * TODO: JavaDoc
	 * @return
	 */
    private final GUIController gUIController;

	/**
	 * Constructs a new {@code EPAController}-object witch initiates the creation of all associated controllers. Loads
	 * the serialised {@code EPA}-object from a previous session, if available, or otherwise creates a new
	 * {@code EPA}-object and shows a corresponding warning message.
	 */
	public EPAController(Stage primaryStage) {
		this.iOController = new IOController(this);
		gUIController = new GUIController(this, primaryStage);
		EPA model;
		try {
			model = iOController.load();
		}
		catch (FileNotFoundException fNFEx) {
			prompt("WARNUNG!\nEs konnte keine gespeicherte EPA gefunden werden. Daher wird eine neue, leere EPA angelegt.");
			model = new EPA();
		}
		this.ePA = model;
		this.patientController = new PatientController(this);
		this.logEntryController = new LogEntryController(this);
		this.permissionController = new PermissionController(this);
		this.treatmentEntryController = new TreatmentEntryController(this);
    }

	/**
	 * Returns {@link #ePA}.
	 *
	 * @return {@link #ePA}
	 */
	public EPA getEPA() {
		return ePA;
	}

	/**
	 * Returns {@link #iOController}. Note: Never {@code null}.
	 *
	 * @return {@link #iOController}
	 */
    public IOController getIOController() {
    	return iOController;
    }

	/**
	 * Returns {@link #patientController}. Note: Never {@code null}.
	 *
	 * @return {@link #patientController}
	 */
    public PatientController getPatientController() {
        return patientController;
    }

	/**
	 * Returns {@link #permissionController}. Note: Never {@code null}.
	 *
	 * @return {@link #permissionController}
	 */
    public PermissionController getPermissionController() {
    	return permissionController;
    }

	/**
	 * Returns {@link #treatmentEntryController}. Note: Never {@code null}.
	 *
	 * @return {@link #treatmentEntryController}
	 */
    public TreatmentEntryController getTreatmentEntryController() {
        return treatmentEntryController;
    }

	/**
	 * Returns {@link #logEntryController}. Note: Never {@code null}.
	 *
	 * @return {@link #logEntryController}
	 */
    public LogEntryController getLogEntryController() {
        return logEntryController;
    }

    /**
 	 * Registers a new user with the specified {@code userType} and displays the assigned {@code userID}
	 * {@link model.User} afterwards {@link #prompt(String)}.
	 *
 	 * @param userType Specified user type 'd','D' {@link model.Doctor}; 'p','P' {@link model.Patient}
	 * @throws IllegalArgumentException if the specified user type is not supported.
 	 */
    public void registerUser(char userType) {
		int newUserID = EPAController.NEW_USER_ID_OFFSET;
		for(Patient patient : ePA.getPatients()) {
			if(patient.getUserID() >= newUserID) {
				newUserID = patient.getUserID() + 1;
			}
		}
		for(Doctor doctor : ePA.getDoctors()) {
			if(doctor.getUserID() >= newUserID) {
				newUserID = doctor.getUserID() + 1;
			}
		}
    	if(userType == 'd' || userType == 'D') {
    		Doctor newDoctor = new Doctor(newUserID);
			ePA.getDoctors().add(newDoctor);
		} else if (userType == 'p' || userType == 'P') {
			Patient newPatient = new Patient(newUserID);
			ePA.getPatients().add(newPatient);
		} else {
    		throw new IllegalArgumentException();
		}
    	iOController.save();
		prompt("Nutzerregistrierung erfolgreich!\nIhre zugewiesene ID lautet: " + newUserID);
    }

    /**
 	 * Login of the user with the specified {@code userID} {@link model.User} and loads the patient with the specified
	 * {@code insuranceNumber} if the {@link model.User} is a {@link Doctor} or the patient with the specified
	 * {@code userID} if the {@link model.User} is a {@link Patient}. If the specified {@code userID} is not
	 * registered or the specified {@code insuranceNumber} does not exist and {@link model.User} is a {@link Doctor}
	 * this method returns {@code false}. For all cases in which the method returns {@code false}, the user is shown
	 * corresponding error messages.
	 *
 	 * @param userID Specified user to be logged in
	 * @param insuranceNumber Insurance number of the patient whose to be loaded
	 * @return {@code true} if the associated user is now logged in and a patient is loaded, {@code false} otherwise
 	 */
    public boolean loginUser(int userID, String insuranceNumber) {
    	Patient patientToBeLoaded = null;
        for(Patient patient : ePA.getPatients()) {
        	if(patient.getUserID() == userID) {
        		ePA.setActiveUser(patient);
        		ePA.setLoadedPatient(patient);
        		return true;
			}
        	if(patient.getInsuranceNumber() != null && patient.getInsuranceNumber().equals(insuranceNumber)) {
        		patientToBeLoaded = patient;
			}
		}
		if(patientToBeLoaded == null) {
			prompt("FEHLER!\nEs konnte kein Nutzer mit dieser Versicherungsnummer gefunden werden.");
			return false;
		}
		ePA.setLoadedPatient(patientToBeLoaded);
        for(Doctor doctor : ePA.getDoctors()) {
			if (doctor.getUserID() == userID) {
				ePA.setActiveUser(doctor);
				return true;
			}
		}
		prompt("FEHLER!\nEs konnte kein Nutzer mit dieser Nutzer-ID gefunden werden.");
        return false;
    }

    /**
 	 * Controls the creation and saving of a pdf-file containing all personal and medical information of the loaded
	 * {@link model.Patient}-object in the specified time period.
	 *
 	 * @param from Start date of the requested time period
 	 * @param to End date of the requested time period
	 * @throws IllegalArgumentException if the specified start date is later than the specified end date
	 * @throws IllegalStateException if the calling user is not a patient
 	 */
    public void printMedicalReport(LocalDateTime from, LocalDateTime to) throws UnsupportedOperationException {
		if(!(ePA.getActiveUser() instanceof Patient)) {
			throw new IllegalStateException();
		}
		Patient loadedPatient = ePA.getLoadedPatient();
		StringBuilder pdfData = new StringBuilder();
		pdfData.append(String.format("\n+%s+\n","-".repeat(127)));
		pdfData.append(String.format("    MEDIZINISCHER BERICHT\n"));
		if (from == null && to == null){//case time range undefined
			pdfData.append("    VON: Beginn der Aufzeichnungen\n    BIS: Jetzt");
		}
		else if (from == null){//case only to defined
			pdfData.append(String.format("    VON: Beginn der Aufzeichnungen\n    BIS: %s Uhr",to.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))));
		}
		else if (to == null){//case only from defined
			pdfData.append(String.format("    VON: %s Uhr\n    BIS: Jetzt",from.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))));
		}
		else{//case from and to defined
			pdfData.append(String.format("    VON: %s Uhr\n    BIS: %s Uhr",from.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),to.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))));
		}
		pdfData.append(String.format("\n+%s+\n","-".repeat(127)));
		pdfData.append(loadedPatient.personalDataToString());
		ArrayList<TreatmentEntry> tEntries = treatmentEntryController.searchForTreatmentEntryTime(loadedPatient, from, to);
		if(tEntries.isEmpty()) {
			pdfData.append(String.format("\n+%s+\n","-".repeat(127)));
			pdfData.append(String.format("    Keine medizinischen Daten für den spezifizierten Zeitraum vorhanden."));
		} else {
			int entryNumber = 1;
			for(TreatmentEntry tEntry : tEntries) {
				pdfData.append(String.format("\n+%s+\n","-".repeat(127)));
				pdfData.append(String.format("    BEHANDLUNGSEINTRAG %d:\n\n",entryNumber++));
				// Creator:
				pdfData.append(String.format("    ARZT: %-80s\n",tEntry.getCreator().toString()));
				// Date and time:
				pdfData.append(String.format("    TERMIN: %s Uhr\n",tEntry.getDateAndTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
				// Reason:
				pdfData.append(String.format("    GRUND DES BESUCHS: %s\n",tEntry.getReason()));
				// Diagnosis:
				pdfData.append(String.format("    DIAGNOSE (ICD-Codes): %s\n",tEntry.getDiagnosis()));
				// Treatment:
				pdfData.append(String.format("    BEHANDLUNG: %s\n",tEntry.getTreatment()));
				// Notes:
				pdfData.append(String.format("    NOIZEN: %s\n",tEntry.getNotes()));
			}
		}
		pdfData.append(String.format("\n+%s+","-".repeat(127)));
		iOController.savePDF(pdfData.toString());
    }

    /**
 	 * Shows a message-dialog with the specified {@link String}-object as the message. The message-dialog has to be
	 * closed in order to continue. Note: {@code null} is interpreted like "".
	 *
 	 * @param msg The message to be displayed
 	 */
    public void prompt(String msg) throws UnsupportedOperationException {
    	gUIController.getReportViewController().showMessage(msg);
    }

	/**
	 * Returns the corresponding {@link Doctor}-object to the specified {@code doctorID} or {@code null} if no
	 * {@link Doctor} with this {@code doctorID} exist.
	 *
	 * @param doctorID The identification number of the requested {@link Doctor}
	 * @return The {@link Doctor}-object with the specified {@code doctorID} or {@code null} if no
	 * {@link Doctor} with this {@code doctorID} exist
	 */
	public Doctor getDoctorByID(String doctorID) {
    	for(Doctor doctor : ePA.getDoctors()) {
    		if(String.valueOf(doctor.getUserID()).equals(doctorID)) {
				return doctor;
			}
		}
    	return null;
	}
}
