package controller;

import model.Doctor;
import model.EPA;
import model.Patient;
import model.TreatmentEntry;

import java.time.LocalDate;
import java.lang.UnsupportedOperationException;
import java.util.ArrayList;

/**
 * This controller is suited for operations regarding {@code Patient} objects and can create and update personal
 * data for/from patients as well as it has the functionality to let patients do a substring search to find specific
 * treatmentEntries
 */
public class PatientController {

    /**
 	 * Attribute of class PatientController
 	 */
    private EPAController ePAController;

	/**
	 * Constructor of class PatientController
	 * @param ePAController the {@code EPAController} object having access to the created instance
	 */
	public PatientController(EPAController ePAController) {
    	this.ePAController = ePAController;
    }

    /**
 	 *
	 * This method allows a new patient to create their personal information.
	 * Parameters of this method are taken from GUI "Patient data" where patient entered
	 * This method will call the method setData(...).
	 * A new object of patient will be created when this method is called
	 * @param insuranceName is Name of insurance company which patient uses
	 * @param insuranceNumber is insurance number of patient
	 * @param firstName is firstname of patient
	 * @param  lastName is family name of patient
	 * @param streetName is the name of street where patient lives
	 * @param houseNumber is the house number of patient
	 * @param  postalCode is the postal code of city where patient lives
	 * @param  cityName is the name of city
	 * @param birthday is birthday of patient
	 * @param sex is Gender of patient
	 * @param personalDoctor is the doctor who patient registered for medical examination
 	 */
    public void createData(String insuranceName, String insuranceNumber, String firstName, String lastName, String streetName, String houseNumber, String postalCode, String cityName, LocalDate birthday, String sex, Doctor personalDoctor) throws UnsupportedOperationException {
        EPA ePA = ePAController.getEPA();
        // This method can only be called by patients which means that the active user equals the loaded patient
        Patient patient = ePA.getLoadedPatient();
		patient.setFirstLogin(false);
		setData(patient, insuranceName, insuranceNumber, firstName, lastName, streetName, houseNumber, postalCode, cityName, birthday, sex, personalDoctor);
    }

    /**
 	 *
	 * The method updateData(...) is used to update the information of one patient in the list of patients.
	 * The patient has already created their own data and then they want to edit their information again.
	 * The method setData(...) is called for creating new data. The old data is changed by new data.
	 * Apart from patientID, all personal information of patient can be updated
	 * @param insuranceName is Name of insurance company which patient uses
	 * @param insuranceNumber is insurance number of patient
	 * @param firstName is firstname of patient
	 * @param  lastName is family name of patient
	 * @param streetName is the name of street where patient lives
	 * @param houseNumber is the house number of patient
	 * @param  postalCode is the postal code of city where patient lives
	 * @param  cityName is the name of city
	 * @param birthday is birthday of patient
	 * @param sex is Gender of patient
	 * @param personalDoctor is the doctor who patient registered for medical examination
 	 */
    public void updateData(String insuranceName, String insuranceNumber, String firstName, String lastName, String streetName, String houseNumber, String postalCode, String cityName, LocalDate birthday, String sex, Doctor personalDoctor) throws UnsupportedOperationException {
		EPA ePA = ePAController.getEPA();
		// This method can only be called by patients which means that the active user equals the loaded patient
		Patient patient = ePA.getLoadedPatient();
		LogEntryController lECtrl = ePAController.getLogEntryController();
		lECtrl.createLogEntry(patient);
		setData(patient, insuranceName, insuranceNumber, firstName, lastName, streetName, houseNumber, postalCode, cityName, birthday, sex, personalDoctor);
    }

    /**
 	 *
	 * This method allows patient to find their own treatment entries based on the parameter "searchTerm".
	 * All treatment entries which match with the parameter "searchTerm" will be saved into an ArrayList.
	 * This ArrayList of all treatment entries found out will be returned. If the ArrayList is empty, it returns null.
 	 * @param searchTerm : is any keyword which patient entered.
 	 * @return ArrayList of Treatment Entry saves the results of searching for "searchTerm"
 	 */
    public ArrayList<TreatmentEntry> seeTreatment(String searchTerm){
        EPA ePA = ePAController.getEPA();
		int currentUserID = ePA.getActiveUser().getUserID();    // get userID of person who is logging
		ArrayList<Patient> patients = ePA.getPatients();
		ArrayList<TreatmentEntry> treatmententries = null;
		ArrayList<TreatmentEntry> findTreamententries = null;
		for(Patient p : patients){
			if(p.getUserID()== currentUserID){     // find the patient who is logging
				treatmententries = p.getTreatmentEntries();   // treamentEntries includes all treatment entries of this patient
				for(TreatmentEntry t : treatmententries){
					// convert localDateTime to a String
					StringBuilder dateAndTime = new StringBuilder();
					dateAndTime.append(t.getDateAndTime().getDayOfMonth());
					dateAndTime.append(".");
					dateAndTime.append(t.getDateAndTime().getMonthValue());
					dateAndTime.append(".");
					dateAndTime.append(t.getDateAndTime().getYear());
					// check, if search term is found in any attribute of this TreatmentEntry t
					if(dateAndTime.toString().contains(searchTerm) || t.getReason().contains(searchTerm) || t.getDiagnosis().contains(searchTerm) ||
							t.getTreatment().contains(searchTerm) || t.getNotes().contains(searchTerm)){
						findTreamententries.add(t);     // add the treatmentEntry which contains search term into find treatmentEntries list
					}
				}
				break;
			}
		}
		return findTreamententries;
    }

    /**
 	 *
	 * This method allows user to enter their personal information.
	 * This method will be used by both methods createData(...) and updateData(...)
	 * @param patient is the current patient who wants to create or update their personal data
 	 * @param insuranceName is Name of insurance company which patient uses
 	 * @param insuranceNumber is insurance number of patient
 	 * @param firstName is firstname of patient
	 * @param  lastName is family name of patient
 	 * @param streetName is the name of street where patient lives
	 * @param houseNumber is the house number of patient
	 * @param  postalCode is the postal code of city where patient lives
	 * @param  cityName is the name of city
 	 * @param birthday is birthday of patient
 	 * @param sex is Gender of patient
	 * @param personalDoctor is the doctor who patient registered for medical examination
 	 */
    private void setData(Patient patient, String insuranceName, String insuranceNumber, String firstName, String lastName, String streetName, String houseNumber, String postalCode, String cityName, LocalDate birthday, String sex, Doctor personalDoctor) throws UnsupportedOperationException {
        patient.setInsuranceName(insuranceName);
        patient.setInsuranceNumber(insuranceNumber);
        patient.setName(firstName, lastName);
        patient.setAddress(streetName, houseNumber, postalCode, cityName);
        patient.setBirthday(birthday);
        patient.setSex(sex);
        patient.setPersonalDoctor(personalDoctor);
    }
}
