package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author  Thomas Alexander Hövelmann
 *
 * @see User
 */
public class Patient extends User implements Serializable {
	/**
	 * TODO: JavaDoc
	 */
	private boolean firstLogin = true;

    /**
 	 * Name of the patient's insurance company.
 	 */
    private String insuranceName;

    /**
 	 * Patient's insurance number; used to identify patients outside the context of user management.
 	 */
    private String insuranceNumber;

    /**
 	 * Patient's name. Format: "&lt;last name&gt;;&gt;first name&gt;"; {@link #setName(String, String)}
 	 */
    private String name;

	/**
	 * Patient's address. Format: "&lt;street name&gt;;&gt;house number&gt;;&gt;postal code&gt;;&gt;city name&gt;";
	 * {@link #setAddress(String, String, String, String)}
	 */
    private String address;

    /**
 	 * Patient's birthday.
 	 */
    private LocalDate birthday;

    /**
 	 * Patient's sex. Format: only contains 'x', 'y', 'X' or 'Y'; {@link #setSex}
 	 */
    private String sex;

    /**
 	 * Collection of all treatment entries for this patient {@link TreatmentEntry}.
 	 */
    private ArrayList<TreatmentEntry> treatmentEntries;

    /**
 	 * Collection of all log entries for this patient {@link PatientLogEntry}.
 	 */
    private ArrayList<PatientLogEntry> logEntries;

    /**
 	 * Reference to the personal doctor specified by this patient {@link Doctor}.
 	 */
    private Doctor personalDoctor;

	/**
	 * Constructs a new and empty {@code EPA}-object with the specified identification number {@link User}.
	 *
	 * @param patientID Identification number of this patient {@link User}.
	 */
	public Patient(int patientID) {
		super(patientID);
		this.treatmentEntries = new ArrayList<>();
		this.logEntries = new ArrayList<>();
		setAddress(null,null,null,null);
		setName(null,null);
		this.sex = "";
		this.insuranceName = "";
		this.insuranceNumber = "";
	}

	/**
	 * Constructor for shallow copies. Used in {@link #getCopyForLogging()}.
	 *
	 * @param patientID Identification number of the template {@link User}.
	 * @param insuranceName {@link #insuranceName} of the template
	 * @param insuranceNumber {@link #insuranceNumber} of the template
	 * @param name {@link #name} of the template
	 * @param address {@link #address} of the template
	 * @param sex {@link #sex} of the template
	 * @param birthday {@link #birthday} of the template
	 * @param personalDoctor {@link #personalDoctor} of the template
	 */
	private Patient(int patientID, String insuranceName, String insuranceNumber, String name, String address, String sex, LocalDate birthday, Doctor personalDoctor) {
		super(patientID);
		this.treatmentEntries = new ArrayList<>();
		this.logEntries = new ArrayList<>();
		this.insuranceName = insuranceName;
		this.insuranceNumber = insuranceNumber;
		this.name = name;
		this.address = address;
		this.sex = sex;
		this.birthday = birthday;
		this.personalDoctor = personalDoctor;
	}

	/**
	 * Sets {@link #insuranceName} as specified. Note: {@code null} is interpreted like "".
	 *
	 * @param insuranceName {@link #insuranceName}
	 */
	public void setInsuranceName(String insuranceName) {
		if(insuranceName == null) {
			insuranceName = "";
		}
		this.insuranceName = insuranceName;
	}

	/**
	 * Sets {@link #insuranceNumber} as specified. Note: {@code null} is interpreted like "".
	 *
	 * @param insuranceNumber {@link #insuranceNumber}
	 */
	public void setInsuranceNumber(String insuranceNumber) {
		if(insuranceNumber == null) {
			insuranceNumber = "";
		}
		this.insuranceNumber = insuranceNumber;
	}

	/**
	 * Sets {@link #name} as "{@code lastName};{@code firstName}". Note: {@code null} is interpreted like "".
	 *
	 * @param firstName Patient's first name
	 * @param lastName Patient's last name
	 */
	public void setName(String firstName, String lastName) {
		if(firstName == null) {
			firstName = "";
		}
		if(lastName == null) {
			lastName = "";
		}
		firstName = firstName.replaceAll(";","");
		lastName = lastName.replaceAll(";","");
		this.name = lastName + ";" + firstName;
	}

	/**
	 * Sets {@link #address} as "{@code streetName};{@code houseNumber};{@code postalCode};{@code cityName}". Note:
	 * {@code null} is interpreted like "".
	 *
	 * @param streetName Street name of patient's current place of residence
	 * @param houseNumber House number of patient's current place of residence
	 * @param postalCode Postal code of patient's current place of residence
	 * @param cityName City name of patient's current place of residence
	 */
	public void setAddress(String streetName, String houseNumber, String postalCode, String cityName) {
		if(streetName == null) {
			streetName = "";
		}
		if(houseNumber == null) {
			houseNumber = "";
		}
		if(postalCode == null) {
			postalCode = "";
		}
		if(cityName == null) {
			cityName = "";
		}
		streetName = streetName.replaceAll(";","");
		houseNumber = houseNumber.replaceAll(";","");
		postalCode = postalCode.replaceAll(";","");
		cityName = cityName.replaceAll(";","");
		this.address = streetName + ";" + houseNumber + ";" + postalCode + ";" + cityName;
	}

	/**
	 * Sets {@link #birthday} as specified.
	 *
	 * @param birthday {@link #birthday}
	 * @throws IllegalArgumentException if the specified {@link LocalDate} is {@code null}
	 */
	public void setBirthday(LocalDate birthday) {
		if(birthday == null) {
			throw new IllegalArgumentException("Birthday");
		}
		else if(birthday.isAfter(LocalDate.now())){
			throw new IllegalArgumentException("Birthday");
		}
		this.birthday = birthday;
	}

	/**
	 * Sets {@link #sex} as specified.
	 *
	 * @param sex {@link #sex}
	 * @throws IllegalArgumentException if the specified {@link String} is {@code null} or contains characters other
	 * than 'x', 'y', 'X' or 'Y'
	 */
	public void setSex(String sex) {
		if(sex == null) {
			throw new IllegalArgumentException("Sex");
		}
		sex = sex.toUpperCase();
		for(char c : sex.toCharArray()) {
			if(c != 'X' && c != 'Y') {
				throw new IllegalArgumentException("Sex");
			}
		}
		this.sex = sex;
	}

	/**
	 * Sets {@link #personalDoctor} as specified.
	 *
	 * @param personalDoctor {@link #personalDoctor}
	 */
	public void setPersonalDoctor(Doctor personalDoctor) {
		this.personalDoctor = personalDoctor;
	}

	/**
	 * Sets {@link #firstLogin} as specified.
	 *
	 * @param firstLogin {@link #firstLogin}
	 */
	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	/**
	 * Returns {@link #insuranceName}. Note: Never {@code null}.
	 *
	 * @return {@link #insuranceName}
	 */
	public String getInsuranceName() {
		return insuranceName;
	}

	/**
	 * Returns {@link #insuranceNumber}. Note: Never {@code null}.
	 *
	 * @return {@link #insuranceNumber}
	 */
	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	/**
	 * Returns {@link #name}. Note: Never {@code null}.
	 *
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns {@link #address}. Note: Never {@code null}.
	 *
	 * @return {@link #address}
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Returns {@link #birthday}. Note: Maybe {@code null}.
	 *
	 * @return {@link #birthday}
	 */
	public LocalDate getBirthday() {
		return birthday;
	}

	/**
	 * Returns {@link #sex}. Note: Never {@code null}.
	 *
	 * @return {@link #sex}
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * Returns {@link #treatmentEntries}. Note: Never {@code null}.
	 *
	 * @return {@link #treatmentEntries}
	 */
	public ArrayList<TreatmentEntry> getTreatmentEntries() {
		return treatmentEntries;
	}

	/**
	 * Returns {@link #logEntries}. Note: Never {@code null}.
	 *
	 * @return {@link #logEntries}
	 */
	public ArrayList<PatientLogEntry> getLogEntries() {
		return logEntries;
	}

	/**
	 * Returns {@link #personalDoctor}. Note: Maybe {@code null}.
	 *
	 * @return {@link #personalDoctor}
	 */
	public Doctor getPersonalDoctor() {
		return personalDoctor;
	}

	/**
	 * Returns {@link #firstLogin}.
	 *
	 * @return {@link #firstLogin}
	 */
	public boolean isFirstLogin() {
		return firstLogin;
	}

	/**
 	 * Returns a {@link String}-representation of this patient's personal data.
 	 *
 	 * @return {@link String}-representation of this patient's personal data.
 	 */
    public String personalDataToString() {
    	char semicolon = ';';
        StringBuilder pData = new StringBuilder();
        // Name:
		StringBuilder sBLastName = new StringBuilder();
		StringBuilder sBFirstName = new StringBuilder();
		boolean isLastName = true;
		for(char c : name.toCharArray()) {
			if(c == semicolon) {
				isLastName = false;
			} else {
				if(isLastName) {
					sBLastName.append(c);
				} else {
					sBFirstName.append(c);
				}
			}
		}
		String lastName = sBLastName.toString();
		String firstName = sBFirstName.toString();
		pData.append(String.format("    VORNAME: %s\n", firstName));
		pData.append(String.format("    NACHNAME: %s\n", lastName));
        // Birthday:
		if(birthday != null) {
			pData.append(String.format("    GEBURTSDATUM: %s\n", birthday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
		} else {
			pData.append(String.format("    GEBURTSDATUM:\n"));
		}
        // Sex:
		pData.append(String.format("    GESCHLECHT: %s\n", sex));
		// Address data:
		StringBuilder sBStreetName = new StringBuilder();
		StringBuilder sBHouseNumber = new StringBuilder();
		StringBuilder sBPostalCode = new StringBuilder();
		StringBuilder sBCityName = new StringBuilder();
		boolean isStreetName = true;
		boolean isHouseNumber = false;
		boolean isPostalCode = false;
		for(char c : address.toCharArray()) {
			if(c == semicolon) {
				if(isStreetName) {
					isStreetName = false;
					isHouseNumber = true;
				} else if(isHouseNumber) {
					isHouseNumber = false;
					isPostalCode = true;
				} else {
					isPostalCode = false;
				}
			} else {
				if(isStreetName) {
					sBStreetName.append(c);
				} else if(isHouseNumber) {
					sBHouseNumber.append(c);
				} else if(isPostalCode) {
					sBPostalCode.append(c);
				} else {
					sBCityName.append(c);
				}
			}
		}
		String streetName = sBStreetName.toString();
		String houseNumber = sBHouseNumber.toString();
		String postalCode = sBPostalCode.toString();
		String cityName = sBCityName.toString();
		pData.append(String.format("    STRAßENNAME: %s\n",streetName));
		pData.append(String.format("    HAUSNUMMER: %s\n", houseNumber));
		pData.append(String.format("    POSTLEITZAHL (PLZ): %s\n", postalCode));
		pData.append(String.format("    STADTNAME: %s\n", cityName));
		// Insurance data:
		pData.append(String.format("    NAME DER VERSICHERUNG: %s\n", insuranceName));
		pData.append(String.format("    VERSICHERTENNUMMER: %s\n", insuranceNumber));
		// Personal doctor:
		if(personalDoctor != null) {
			pData.append(String.format("    HAUSARZT: %s", personalDoctor.toString()));
		} else {
			pData.append(String.format("    HAUSARZT:"));
		}
        return pData.toString();
    }

	/**
	 * Returns a shallow copy of this object for logging purposes.
	 *
	 * @return A shallow copy of this object.
	 */
	public Patient getCopyForLogging() {
    	Patient logCopy = new Patient(getUserID(),insuranceName,insuranceNumber,name,address,sex,birthday,personalDoctor);
    	return logCopy;
	}
}
