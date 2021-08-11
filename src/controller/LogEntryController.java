package controller;

import model.*;
import java.lang.UnsupportedOperationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;


/**
 * Class for controlling {@code TreatmentLogEntry} and {@code PatientLogEntry} objects in a generalized manner over
 * their abstract class {@code LogEntry}. Objects from this class can load permitted LogEntries, search for specific
 * {@code TreatmentLogEntry} or {@code PatientLogEntry} objects via {@code String} search parameter as well as create
 * such entries
 * @author  Leon Krick
 */
//@SuppressWarnings("all")
public class LogEntryController {

    private EPAController ePAController;

	/**
	 * Basic constructor for the LogEntryController class specifying the controlling {@code EPAController}
	 * @param ePAController the {@code EPAController} object having access to the created instance
	 */
    public LogEntryController(EPAController ePAController) {
    	this.ePAController = ePAController;
    }

    /**
 	 * Creates an {@code ArrayList<LogEntry>} of log entries from the given patient,
	 * that the current logged in user has permission to view. These {@code LogEntry} objects are copies of the original
	 * log entries where all attributes that weren't granted permission for for the logged in user, are set to null.
 	 *
 	 * @param patient the {@code Patient} object whose log entries to check for permission
 	 * @return ArrayList of permitted log entries from the given patient, viewable by the logged in user
 	 */
    public ArrayList<LogEntry> loadPermittedLogEntries(Patient patient) {
        //TODO test this code
        ArrayList<LogEntry> ret = new ArrayList<>();
        EPA epa = ePAController.getEPA();
        PermissionController permissionController = ePAController.getPermissionController();
        User activeUser = epa.getActiveUser();
        Doctor personalDoctor = patient.getPersonalDoctor();
        ArrayList<TreatmentEntry> treatmentEntries = patient.getTreatmentEntries();
        for(TreatmentEntry treatmentEntry : treatmentEntries){//handle treatmentEntries
        	if(activeUser.equals(personalDoctor) || activeUser.equals(patient)){//user is the patient or the personal doctor
        	ArrayList<TreatmentLogEntry> treatmentLogEntries = treatmentEntry.getLogEntries();
				//add all treatmentLogEntries to ret
				ret.addAll(treatmentLogEntries);
        	}
        	else if(permissionController.hasPermission(treatmentEntry)){//user is not the patient or personal doctor but has permission anyway
        		ArrayList<Permission> permissions = treatmentEntry.getPermissions();
        		Permission permissionForCurrentUser = permissions.stream().filter(perm -> activeUser.equals(perm.getDoctor())).findAny().orElse(null);//get information about what user is permitted to see
         		ArrayList<TreatmentLogEntry> treatmentLogEntries = treatmentEntry.getLogEntries();
        		for(TreatmentLogEntry treatmentLogEntry : treatmentLogEntries){//add permitted treatmentLogEntries to ret
       				ret.add(permissionForCurrentUser.getPermittedTreatmentLogEntry(treatmentLogEntry));
        		}
        	}
        }
        ArrayList<PatientLogEntry> patientLogEntries = patient.getLogEntries();
		//add all patientLogEntries to ret
		ret.addAll(patientLogEntries);
		ret.sort(Comparator.comparing(LogEntry::getChangeDateAndTime));//sort descending
		Collections.reverse(ret);
		return ret;
    }

    /**
 	 * Searches the given patients permitted log entries for occurrence of the given {@code String} parameter and returns a list of
	 * TreatmentLogEntry objects containing the parameter as a substring.
 	 * @param parameter {@code String} to be searched for
	 * @param patient {@code Patient} whose logs to be searched
 	 * @return ArrayList of {@code TreatmentLogEntry} objects containing the parameter as a substring
 	 */
    public ArrayList<TreatmentLogEntry> searchTreatment(String parameter, Patient patient) throws UnsupportedOperationException {
		//TODO test this code
		ArrayList<LogEntry> permittedLogEntries = loadPermittedLogEntries(patient);
		//filter for TreatmentLogEntries
		ArrayList<TreatmentLogEntry> permittedTreatmentLogEntries = (ArrayList<TreatmentLogEntry>)(ArrayList<?>)permittedLogEntries.stream().filter(logEntry -> (logEntry instanceof TreatmentLogEntry)).collect(Collectors.toList());//I hate type safety
		//filter for substring occurrence of parameter in oldTreatmentEntry's notes, treatment, diagnosis, reason, DateAndTime and getChangeDateAndTime().toString()
		ArrayList<TreatmentLogEntry> fittingResults = (ArrayList<TreatmentLogEntry>) permittedTreatmentLogEntries.stream().filter(permittedTreatmentLogEntry -> (permittedTreatmentLogEntry.getChangeDateAndTime().toString().contains(parameter) ||
				permittedTreatmentLogEntry.getOldTreatmentEntry().getTreatment().contains(parameter) ||
				permittedTreatmentLogEntry.getOldTreatmentEntry().getDiagnosis().contains(parameter) ||
				permittedTreatmentLogEntry.getOldTreatmentEntry().getNotes().contains(parameter) ||
				permittedTreatmentLogEntry.getOldTreatmentEntry().getReason().contains(parameter) ||
				permittedTreatmentLogEntry.getOldTreatmentEntry().getDateAndTime().toString().contains(parameter))).collect(Collectors.toList());
		return new ArrayList<>(fittingResults);
    }

    /**
	 * Searches the given patients permitted log entries for occurrence of the given {@code String} parameter and returns a list of
	 * PatientLogEntry objects containing the parameter as a substring.
 	 * @param parameter {@code String} to be searched for
	 * @param patient {@code Patient} whose logs to be searched
 	 * @return ArrayList of {@code PatientLogEntry} objects containing the parameter as a substring
 	 */
    public ArrayList<PatientLogEntry> searchPatient(String parameter, Patient patient) {
    	//TODO test this code
		ArrayList<LogEntry> permittedLogEntries = loadPermittedLogEntries(patient);
		//filter for PatientLogEntries
		ArrayList<PatientLogEntry> permittedPatientLogEntries = (ArrayList<PatientLogEntry>)(ArrayList<?>)permittedLogEntries.stream().filter(logEntry -> (logEntry instanceof PatientLogEntry)).collect(Collectors.toList());//I hate type safety
		//filter for substring occurrence of parameter in personalDataToString() and getChangeDateAndTime().toString()
		ArrayList<PatientLogEntry> fittingResults = (ArrayList<PatientLogEntry>) permittedPatientLogEntries.stream().filter(permittedPatientLogEntry -> (permittedPatientLogEntry.getOldPatient().personalDataToString().contains(parameter) ||
				permittedPatientLogEntry.getChangeDateAndTime().toString().contains(parameter))).collect(Collectors.toList());
		return new ArrayList<>(fittingResults);
    }

    /**
 	 * Creates a new TreatmentLogEntry with {@code changeDateAndTime} as current date and time and with the current user
	 * as the editor by adding a copy of the given {@code TreatmentEntry} without its {@code logEntries} property to its
	 * {@code logEntries} ArrayList
 	 * @param treatmentEntry the {@code TreatmentEntry} for which to create a new {@code LogEntry}
 	 */
    public void createLogEntry(TreatmentEntry treatmentEntry){
    	ArrayList<TreatmentLogEntry> logEntries = treatmentEntry.getLogEntries();
		LocalDateTime dateAndTime = LocalDateTime.now();
		TreatmentEntry copyWithoutLogs = treatmentEntry.getCopyForLogging();
		Doctor editor = (Doctor)ePAController.getEPA().getActiveUser();
    	TreatmentLogEntry treatmentLogEntry = new TreatmentLogEntry(dateAndTime, copyWithoutLogs, editor);
        logEntries.add(treatmentLogEntry);
    }

    /**
	 * Creates a new PatientLogEntry with {@code changeDateAndTime} as current date and time by adding a copy of
	 * the given {@code Patient} without its @code logEntries property to its {@code logEntries} ArrayList
 	 * @param patient the {@code Patient} for which to create a new {@code LogEntry}
 	 */
    public void createLogEntry(Patient patient){
        ArrayList<PatientLogEntry> logEntries = patient.getLogEntries();
        LocalDateTime dateTime = LocalDateTime.now();
        Patient copyWithoutLogs = patient.getCopyForLogging();
        PatientLogEntry patientLogEntry = new PatientLogEntry(dateTime, copyWithoutLogs);
        logEntries.add(patientLogEntry);
    }
}
