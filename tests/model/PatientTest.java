package model;

import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Leon Krick
 * Test class for patients
 */
public class PatientTest {
    Patient patient1;
    Patient patient2;
    Patient patient3;
    Patient patient4;
    Doctor doctor;

    /**
     * initializes Patients to use for testing
     */
    @Before
    public void setUp() throws Exception {
        patient1 = new Patient(234);
        patient2 = new Patient(-20);
        patient3 = new Patient(0);
        doctor = new Doctor(33);

        // creating and initializing data for object patient4
        patient4 = new Patient(235);
        patient4.setBirthday(LocalDate.now());
        patient4.setSex("X");
        patient4.setAddress("Marktstr", "pink", "333", "city");
        patient4.setInsuranceName("BK");
        patient4.setInsuranceNumber("123");
        patient4.setPersonalDoctor(new Doctor(33));
        patient4.setName("Selena", "Gomez");

    }

    /**
     * Tests setInsuranceName
     */
    @Test()
    public void setInsuranceName_insuranceNameNull() {
        patient1.setInsuranceName(null);
        patient2.setInsuranceName("");
        patient3.setInsuranceName("Test");
        assertEquals("", patient1.getInsuranceName());
        assertEquals("", patient2.getInsuranceName());
        assertEquals("Test", patient3.getInsuranceName());
    }

    /**
     * Tests setInsuranceName when the new insurance name is not null
     */
    @Test
    public void setInsuranceName_insuranceNameNotNull(){
        patient1.setInsuranceName("TK");
        patient2.setInsuranceName("AOK");
        patient3.setInsuranceName("BK");
        assertEquals("TK", patient1.getInsuranceName());
        assertEquals("AOK", patient2.getInsuranceName());
        assertEquals("BK", patient3.getInsuranceName());
    }

    /**
     * Tests setInsuranceNumber when the new insurance number is null
     */
    @Test
    public void setInsuranceNumber_insuranceNumberNull() {
        patient1.setInsuranceNumber(null);
        patient2.setInsuranceNumber("");
        assertEquals("", patient1.getInsuranceNumber());
        assertEquals("", patient2.getInsuranceNumber());
    }

    /**
     * Tests setInsuranceNumber when the new insurance number is not null
     */
    @Test
    public void setInsuranceNumber_insuranceNumbelNotNull(){
        patient1.setInsuranceNumber("BK111");
        patient2.setInsuranceNumber("BK112");
        patient3.setInsuranceNumber("BK113");
        assertEquals("BK111", patient1.getInsuranceNumber());
        assertEquals("BK112", patient2.getInsuranceNumber());
        assertEquals("BK113", patient3.getInsuranceNumber());
    }

    /**
     * Tests setName
     */
    @Test
    public void setName_NameNull() {
        patient1.setName(null, null);
        patient2.setName(";", "null");
        patient3.setName("Test;1", "Test2");
        assertEquals(";", patient1.getName());
        assertEquals("null;", patient2.getName());
        assertEquals("Test2;Test1", patient3.getName());
    }

    /**
     * Tests setName when the new name is not null
     */
    @Test
    public void setName_NameNotNull(){
        patient1.setName("firstName", "lastName");
        patient2.setName("Selena", "Gomez");
        patient3.setName("Black", "Pink");
        assertEquals("lastName;firstName", patient1.getName());
        assertEquals("Gomez;Selena", patient2.getName());
        assertEquals("Pink;Black", patient3.getName());

    }

    /**
     * Tests setAddress when the new streetName is null
     */
    @Test
    public void setAddress_streetNameNull() {
        patient1.setAddress(null, "", ";", "\\");
        assertEquals(";;;\\", patient1.getAddress());
    }

    /**
     * Tests setAddress when the new houseNumber is null
     */
    @Test
    public void setAddress_houseNumberNull(){
        patient2.setAddress("^@", null, "dsf;", "Te;s;t");
        assertEquals("^@;;dsf;Test", patient2.getAddress());
    }
    /**
     * Tests setAddress when the new postalCode is null
     */
    @Test
    public void setAddress_postalCodeNull(){
        patient3.setAddress("Marktstr", "1", null, "Test");
        assertEquals("Marktstr;1;;Test", patient3.getAddress());
    }
    /**
     * Tests setAddress when the new cityName is null
     */
    @Test
    public void setAddress_cityNameNull(){
        patient3.setAddress("Marktstr", "1", "123", null);
        assertEquals("Marktstr;1;123;", patient3.getAddress());
    }
    /**
     * Tests setAddress
     */
    @Test
    public void setAddress_AddressNotNull(){
        patient3.setAddress("\"", ";\"", "" + '"', "Test");
        assertEquals("\";\";\";Test", patient3.getAddress());
    }


    /**
     * Tests setBirthday
     */
    @Test
    public void setBirthday() {
        patient1.setBirthday(LocalDate.of(2020, 3, 26));
        assertEquals(LocalDate.of(2020, 3, 26), patient1.getBirthday());
    }

    /**
     * Tests setBirthday
     */
    @Test(expected = Exception.class)
    public void setBirthdayNull() throws Exception {
        patient2.setBirthday(null);
    }

    /**
     * Tests setBirthday
     */
    @Test(expected = Exception.class)
    public void setBirthdayIllegal() throws Exception {
        patient3.setBirthday(LocalDate.of(2025, 7, 30));
    }

    /**
     * Tests setSex
     */
    @Test
    public void setSex() {
        patient1.setSex("");
        patient3.setSex("x");
        assertEquals("", patient1.getSex());
        assertEquals("X", patient3.getSex());
    }

    /**
     * Tests setSex
     */
    @Test(expected = Exception.class)
    public void setSexIllegal() throws Exception {
        patient3.setSex("xTesty");
    }

    /**
     * Tests setSex
     */
    @Test(expected = Exception.class)
    public void setSexIllegalNull() throws Exception {
        patient3.setSex(null);
    }

    /**
     * Tests personalDataToString
     */
    @Test
    public void personalDataToString() {
        assertEquals("    VORNAME: \n    NACHNAME: \n    GEBURTSDATUM:\n    GESCHLECHT: \n    STRAßENNAME: \n    HAUSNUMMER: \n    POSTLEITZAHL (PLZ): \n    STADTNAME: \n    NAME DER VERSICHERUNG: \n    VERSICHERTENNUMMER: \n    HAUSARZT:", patient1.personalDataToString());
        patient1.setName("Lazarus", null);
        patient1.setBirthday(LocalDate.of(2020, 3, 25));
        patient1.setSex("XY");
        patient1.setAddress("street", "house", "postalCode", "city");
        patient1.setInsuranceName("no insurance");
        patient1.setInsuranceNumber("no insurance");
        patient1.setPersonalDoctor(new Doctor(24));
        assertEquals("    VORNAME: Lazarus\n    NACHNAME: \n    GEBURTSDATUM: 25.03.2020\n    GESCHLECHT: XY\n    STRAßENNAME: street\n    HAUSNUMMER: house\n    POSTLEITZAHL (PLZ): postalCode\n    STADTNAME: city\n    NAME DER VERSICHERUNG: no insurance\n    VERSICHERTENNUMMER: no insurance\n    HAUSARZT: (ARZT-ID) 24", patient1.personalDataToString());
        patient1.setName("Lazarus", "null");
        assertEquals("    VORNAME: Lazarus\n    NACHNAME: null\n    GEBURTSDATUM: 25.03.2020\n    GESCHLECHT: XY\n    STRAßENNAME: street\n    HAUSNUMMER: house\n    POSTLEITZAHL (PLZ): postalCode\n    STADTNAME: city\n    NAME DER VERSICHERUNG: no insurance\n    VERSICHERTENNUMMER: no insurance\n    HAUSARZT: (ARZT-ID) 24", patient1.personalDataToString());
        patient1.setAddress("street", "null", null, "keine");
        assertEquals("    VORNAME: Lazarus\n    NACHNAME: null\n    GEBURTSDATUM: 25.03.2020\n    GESCHLECHT: XY\n    STRAßENNAME: street\n    HAUSNUMMER: null\n    POSTLEITZAHL (PLZ): \n    STADTNAME: keine\n    NAME DER VERSICHERUNG: no insurance\n    VERSICHERTENNUMMER: no insurance\n    HAUSARZT: (ARZT-ID) 24", patient1.personalDataToString());
    }

    /**
     * Tests getCopyForLogging
     */
    @Test
    public void getCopyForLogging() {
        patient1.setBirthday(LocalDate.now());
        patient1.setSex("XY");
        patient1.setAddress("street", "house", "postalCode", "city");
        patient1.setInsuranceName("no insurance");
        patient1.setInsuranceNumber("no insurance");
        patient1.setPersonalDoctor(new Doctor(24));
        patient1.setName("Lazarus", "null");
        patient1.getTreatmentEntries().add(new TreatmentEntry(new Doctor(2), LocalDateTime.now()));
        Patient copy = patient1.getCopyForLogging();
        assertTrue((patient1.personalDataToString().equals(copy.personalDataToString())) && (patient1.getTreatmentEntries().size() == 1) && (copy.getTreatmentEntries().size() == 0));
    }
    /**
     * Tests setFirstLogin()
     */
    @Test
    public void setFirstLogin(){
        patient1.setFirstLogin(true);
        assertTrue(patient1.isFirstLogin());

    }
    /**
     * Test isFirstLogin()
     */
    @Test
    public void isFirstLogin(){
        patient1.setFirstLogin(true);
        assertTrue(patient1.isFirstLogin());
    }

    /**
     * Test getLogEntries()
     */
    @Test
    public void getLogEntries(){
        // adding data to arrayList logEntries
        LocalDateTime changeDateAndTime1 = LocalDateTime.of(2021, 03, 03, 11, 33);
        LocalDateTime changeDateAndTime2 = LocalDateTime.of(2021, 03, 05, 11, 33);
        patient4.getLogEntries().add(new PatientLogEntry(changeDateAndTime1, patient4));
        patient4.getLogEntries().add(new PatientLogEntry(changeDateAndTime2, patient4));

        assertTrue("logEntries muss aus 2 Elements bestehen", 2==patient4.getLogEntries().size());
        assertEquals("Gomez;Selena", patient4.getLogEntries().get(0).getOldPatient().getName());
    }

    /**
     * Test getPersonalDoctor()
     */
    @Test
    public void getPersonalDoctor(){
        assertEquals(doctor, patient4.getPersonalDoctor());
    }

    /**
     * Test getTreatmentEntries()
     */
    @Test
    public void getTreatmentEntries(){
        LocalDateTime dateAndTime1 = LocalDateTime.of(2021, 03, 03, 11, 33);
        LocalDateTime dateAndTime2 = LocalDateTime.of(2021, 03, 05, 11, 33);
        // creating object of TreatmentEntry
        TreatmentEntry treatmentEntry1 = new TreatmentEntry(doctor, dateAndTime1);
        treatmentEntry1.setTreatment("Treatment");
        treatmentEntry1.setNotes("abc");
        treatmentEntry1.setDiagnosis("xyz");
        treatmentEntry1.setReason("acd");
        TreatmentEntry treatmentEntry2 = new TreatmentEntry(doctor, dateAndTime2);
        treatmentEntry2.setTreatment("Test");
        treatmentEntry2.setNotes("eeee");
        treatmentEntry2.setDiagnosis("xyz");
        treatmentEntry2.setReason("keine Ahnung");
        // adding created objects to treatmentEntries of patient4
        patient4.getTreatmentEntries().add(treatmentEntry1);
        patient4.getTreatmentEntries().add(treatmentEntry2);

        assertTrue("treatmentEntries besteht aus 2 Elements", 2==patient4.getTreatmentEntries().size());
        assertEquals("keine Ahnung",patient4.getTreatmentEntries().get(1).getReason());
    }
    /**
     * Test setPersonalDoctor()
     */
    @Test
    public void setPersonalDoctor(){
        Doctor doctor2 = new Doctor(335);
        patient4.setPersonalDoctor(doctor2);
        assertEquals(doctor2, patient4.getPersonalDoctor());
    }
    /**
     * Test getInsuranceName()
     */
    @Test
    public void getInsuranceName(){
        assertEquals("BK", patient4.getInsuranceName());
    }
    /**
     * Test getInsuranceNumber()
     */
    @Test
    public void getInsuranceNumber(){
        assertEquals("123", patient4.getInsuranceNumber());
    }
    /**
     * Test getName()
     */
    @Test
    public void getName(){
        assertEquals("Gomez;Selena", patient4.getName());
    }
    /**
     * Test getAddress()
     */
    @Test
    public void getAddress(){
        assertEquals("Marktstr;pink;333;city", patient4.getAddress());
    }
    /**
     * Test getBirthday()
     */
    @Test
    public void getBirthday(){
        assertEquals(LocalDate.now(), patient4.getBirthday());
    }
    /**
     * Test getSex()
     */
    @Test
    public void getSex(){
        assertEquals("X", patient4.getSex());
    }

}