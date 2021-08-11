package controller;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Class for controlling {@code TreatmentEntry} objects. It is used to create and edit treatment entries,
 * search for treatment entries with a search term, search for treatment entries in a specific time and
 * search for all treatment entries the logged in doctor has permissions for.
 */
public class TreatmentEntryController {

    /**
 	 *  EPA Controller
 	 */
    private EPAController ePAController;

    /**
 	 *  TreatmentEntryAUI
 	 */
    //private TreatmentEntryAUI treatmentEntryAUI;

	/**
	 * Basic constructor specifying the controlling {@code EPAController}
	 * @param ePAController the {@code EPAController} object having access to the created instance
	 */
    public TreatmentEntryController(EPAController ePAController) {
		this.ePAController = ePAController;
    }

    /**
 	 *
	 * This method creates a new treatment entry for the currently loaded patient
	 * and adds it to the arraylist of treatment entries for the loaded patient.
	 * The new treatment entry includes the creator which is the currently active user (doctor),
	 * date and time, reason, diagnosis, treatment and notes. The log entries for a new treatment entry
	 * are empty
	 *
 	 * @param patient currently loaded patient for whom a new treatment entry should be created
 	 * @param dateAndTime date and time of the treatment entry
 	 * @param reason reason of the treatment entry
 	 * @param diagnosis diagnosis of the treatment entry
 	 * @param treatment treatment of the treatment entry
 	 * @param notes notes of the treatment entry
 	 *
 	 */
    public void createTreatmentEntry(Patient patient, LocalDateTime dateAndTime, String reason, String diagnosis, String treatment, String notes) {
        EPA ePA = ePAController.getEPA();
        User activeUser = ePA.getActiveUser();
		Doctor activeDoctor = (Doctor) activeUser;
    	TreatmentEntry tEntry = new TreatmentEntry(activeDoctor, dateAndTime);
    	tEntry.setReason(reason);
    	tEntry.setDiagnosis(diagnosis);
    	tEntry.setTreatment(treatment);
    	tEntry.setNotes(notes);
    	ArrayList<TreatmentEntry> tEntries = patient.getTreatmentEntries();
    	tEntries.add(tEntry);
		Collections.sort(tEntries);
		ePAController.getIOController().save();
    }

    /**
 	 *
	 * This method edits an old treatment entry. this only works if the currently active user is also
	 * the creator of the old treatment entry. If this is not the case a report window with the error message
	 * will be displayed. If the active user is the creator a new log entry with the old treatment entry will be created.
	 * Then the new entries will be saved into the old treatment entry. If one of the new entries is empty the old entry will stay.
	 * If everything is completed a report window will be displayed.
	 *
 	 * @param treatmentEntry old treatment entry that should be edited
 	 * @param nDateAndTime new date and time for the old treatment entry
 	 * @param nReason new reason for the old treatment entry
 	 * @param nDiagnosis new diagnosis for the old treatment entry
 	 * @param nTreatment new treatment for the old treatment entry
 	 * @param nNotes new notes for the old treatment entry
 	 *
 	 */
    public void editTreatmentEntry(TreatmentEntry treatmentEntry, LocalDateTime nDateAndTime, String nReason, String nDiagnosis, String nTreatment, String nNotes) {
        EPA ePA = ePAController.getEPA();
		User activeUser = ePA.getActiveUser();
		Doctor activeDoctor = (Doctor) activeUser;
		Doctor creator = treatmentEntry.getCreator();
		if(activeDoctor.equals(creator)) {
			LogEntryController lECtrl = ePAController.getLogEntryController();
			lECtrl.createLogEntry(treatmentEntry);
			treatmentEntry.setDateAndTime(nDateAndTime);
			treatmentEntry.setReason(nReason);
			treatmentEntry.setDiagnosis(nDiagnosis);
			treatmentEntry.setTreatment(nTreatment);
			treatmentEntry.setNotes(nNotes);
			Collections.sort(ePA.getLoadedPatient().getTreatmentEntries());
			ePAController.getIOController().save();
			ePAController.prompt("Änderungen erfolgreich");
		}
		else {
			ePAController.prompt("Fehlende Berechtigung");
		}
    }

    /**
 	 *
	 * This method is used to search for a term in the treatment entries.
	 * It will create an arraylist of all the treatment entries that fit the search term
	 * and will return this list.
	 *
 	 * @param patient currently loaded patient
 	 * @param searchTerm term for the search
 	 * @return ArrayList of {@code TreatmentEntry} objects returns an arraylist of all the treatment entries that fit the search term
 	 *
 	 */
    public ArrayList<TreatmentEntry> searchForTreatmentEntry(Patient patient, String searchTerm) {
        ArrayList<TreatmentEntry> tEntriesSearch = new ArrayList<>();
		ArrayList<TreatmentEntry> tEntriesPermission = searchForTreatmentEntryWithPermission();
		for(TreatmentEntry tEntry : tEntriesPermission) {
				// Building String for Date and Time
			StringBuilder dateAndTime = new StringBuilder();
			dateAndTime.append(tEntry.getDateAndTime().getDayOfMonth());
			dateAndTime.append(".");
			dateAndTime.append(tEntry.getDateAndTime().getMonthValue());
			dateAndTime.append(".");
			dateAndTime.append(tEntry.getDateAndTime().getYear());
			dateAndTime.append(" ");
			dateAndTime.append(tEntry.getDateAndTime().getHour());
			dateAndTime.append(":");
			dateAndTime.append(tEntry.getDateAndTime().getMinute());

			if(dateAndTime.toString().contains(searchTerm)){
				tEntriesSearch.add(tEntry);
			}
			else if(tEntry.getDiagnosis().contains(searchTerm)) {
				tEntriesSearch.add(tEntry);
			}
			else if(tEntry.getReason().contains(searchTerm)) {
				tEntriesSearch.add(tEntry);
			}
			else if(tEntry.getTreatment().contains(searchTerm)) {
				tEntriesSearch.add(tEntry);
			}
			else if(tEntry.getNotes().contains(searchTerm)) {
				tEntriesSearch.add(tEntry);
			}
		}
		return tEntriesSearch;
    }

    /**
 	 *
	 * This method is used to search for treatment entries that lay between from and to.
	 * It returns an arraylist of all the treatment entries that lay between the time from and to
	 *
 	 * @param patient currently loaded patient and active user
 	 * @param from start time for the search
 	 * @param to end time for the search
 	 * @return ArrayList of {@code TreatmentEntry} objects returns an arraylist of all the treatment entries that lay between the time from and to
 	 *
 	 */
    public ArrayList<TreatmentEntry> searchForTreatmentEntryTime(Patient patient, LocalDateTime from, LocalDateTime to) {
        ArrayList<TreatmentEntry> tEntries = patient.getTreatmentEntries();
        ArrayList<TreatmentEntry> tEntriesTime = new ArrayList<>();
        if(to == null && from == null){//case from and to are null
        	tEntriesTime.addAll(tEntries);
		}
        else if (to == null){//case only to is null
			for(TreatmentEntry tEntry : tEntries) {
				if(tEntry.getDateAndTime().isAfter(from)) {
					tEntriesTime.add(tEntry);
				}
			}
		}
        else if(from == null){//case only from is null
			for(TreatmentEntry tEntry : tEntries) {
				if(tEntry.getDateAndTime().isBefore(to)) {
					tEntriesTime.add(tEntry);
				}
			}
		}
        else{//case from and to are defined
			for(TreatmentEntry tEntry : tEntries) {
				if(tEntry.getDateAndTime().isAfter(from) && tEntry.getDateAndTime().isBefore(to)) {
					tEntriesTime.add(tEntry);
				}
			}
		}
        return tEntriesTime;
    }

    /**
 	 *
	 * This method is used to search for all the treatment entries of the loaded patient that the active user has permissions for.
	 * If the active user is the personal doctor of the loaded patient all treatment entries will be in the returned arraylist.
	 * If the active user is not the personal doctor all treatment entries will be searched for the permissions.
	 * If there is a permission for the active user (doctor) the treatment entry will be added to the arraylist.
	 *
 	 * @return ArrayList of {@code TreatmentEntry} objects returns an arraylist of all the treatment entries that the active user (doctor) has permissions for
 	 *
 	 */
    public ArrayList<TreatmentEntry> searchForTreatmentEntryWithPermission() {
		EPA ePA = ePAController.getEPA();
		Patient loadedPatient = ePA.getLoadedPatient();
		ArrayList<TreatmentEntry> patientTEntries = loadedPatient.getTreatmentEntries();
		User activeUser = ePA.getActiveUser();
		Doctor activeDoctor = (Doctor) activeUser;
		Doctor personalDoctor = loadedPatient.getPersonalDoctor();
		ArrayList<TreatmentEntry> tEntriesToDisplay;
		if(activeDoctor.equals(personalDoctor)) {
			tEntriesToDisplay = patientTEntries;
		}
		else {
			tEntriesToDisplay = new ArrayList<>();
			for(TreatmentEntry tEntry : patientTEntries) {
				if(activeDoctor.equals(tEntry.getCreator())) {
					tEntriesToDisplay.add(tEntry);
				}
				else {
					ArrayList<Permission> permissions = tEntry.getPermissions();
					for(Permission permission : permissions) {
						if(activeDoctor.equals(permission.getDoctor())) {
							TreatmentEntry permittedTreatmentEntry = permission.getPermittedTreatmentEntry(tEntry);
							tEntriesToDisplay.add(permittedTreatmentEntry);
						}
					}
				}
			}
		}

		return tEntriesToDisplay;
    }
}
