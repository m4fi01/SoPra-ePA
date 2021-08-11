package controller;

import model.*;

import java.lang.UnsupportedOperationException;
import java.util.ArrayList;

/**
 * @author Mohammad Almidani
 *
 * this controller has the functionality of checking the permission for a treatment
 * in case of showing the data or editing it.
 */

public class PermissionController {

    /**
 	 * Reference to the application's {@link EPAController}-object.
 	 */
    private EPAController ePAController;

   // private PermissionAUI permissionAUI;

	/**
	 * the Constructor of PermissionController with the main ePAController given in parameter
	 * @param ePAController the {@code EPAController} object having access to the created instance
	 */
    public PermissionController(EPAController ePAController) {
		this.ePAController = ePAController;
    }

    /**
 	 *
	 * This method creates new Permissions for a new written TreatmentEntry.
	 * The required Data for the new TreatmentEntry are given as Parameters and
	 * will be read from the GUI and send here through the #ePAController
	 *
 	 * @param tEntry the TreatmentEntry that will have new Permissions or its Permissions will be edited
 	 * @param doctor the Doctor, for whom the new Permissions are being edited
 	 * @param dateAndTime access to date and time for the Treatment
 	 * @param reason access to cause of the visit to the doctor
 	 * @param diagnoses access to diagnoses of the treatment
 	 * @param treatment access to the treatment taken for the patient
 	 * @param notes access to extra notes, which are written form the doctor who made the treatment
 	 */
    public void newPermission(TreatmentEntry tEntry, Doctor doctor, boolean dateAndTime, boolean reason, boolean diagnoses, boolean treatment, boolean notes) {
    	ArrayList<Permission> permissions = tEntry.getPermissions();
		for(Permission per : permissions) {
			if (per.getDoctor().equals(doctor)) {
				permissions.remove(per);
				break;
			}
		}
		// Adds a new permission if at least one piece of data is permitted
		if(dateAndTime | reason | diagnoses | treatment | notes) {
			Permission newPer = new Permission(doctor, dateAndTime, reason, diagnoses, treatment, notes);
			permissions.add(newPer);
		}
    }

    /**
 	 *
	 * This method will check the Permissions for a given treatmentEntry.
	 * It will check the activeUser from the ePAController and will provide the searched values
	 * according to his ID
	 *
 	 * @param tEntry the treatmentEntry, for which the permissions are being checked
 	 * @return boolean
 	 */
    public boolean hasPermission(TreatmentEntry tEntry) {
   		EPA ePA = ePAController.getEPA();
    	User activeUser = ePA.getActiveUser();
		Patient patient = ePA.getLoadedPatient();
		//case patient himself
		if(activeUser.equals(patient)) {
			return true;
		}
		//case personal doctor
		if(activeUser.equals(patient.getPersonalDoctor())) {
			return true;
		}
		//case specialist
		ArrayList<Permission> permissions = tEntry.getPermissions();
		for (Permission per : permissions) {
			if (activeUser.equals(per.getDoctor())) {
				return true;
			}
		}
		return false;
    }
}
