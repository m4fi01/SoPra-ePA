package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test class for epa
 */
public class EPATest {
    /**
     * Creating objects to test methods of class EPA
     */
    private EPA epa = new EPA();
    private Doctor expectedUser;            // is used to test methods getActiveUser() and seActiveUser()
    private Patient expectedLoadedPatient;  // is used to test methods getLoadedPatient() and setLoadedPatient()

    /**
     * Initializing data for testing
     */
    @Before
    public void setUp() throws Exception {
        // initializing data for testing of method getPatients()
        epa.getPatients().add(new Patient(333));
        epa.getPatients().add(new Patient(334));
        epa.getPatients().add(new Patient( 335));
        // initializing data for testing of method getDoctors()
        epa.getDoctors().add(new Doctor(111));
        epa.getDoctors().add(new Doctor(112));
        epa.getDoctors().add(new Doctor(113));
        // initializing data for testing of other methods
        expectedUser = new Doctor(111);
        expectedLoadedPatient = new Patient(333);
    }

    /**
     * Testing method getPatients()
     */
    @Test
    public void getPatients() {
        // creating 3 objects Patients to compare with the output of method getPatients()
        Patient expectedPatient1 = new Patient(333);
        Patient expectedPatient2 = new Patient(334);
        Patient expectedPatient3 = new Patient(335);
        // testing
        assertEquals(expectedPatient1, epa.getPatients().get(0));
        assertEquals(expectedPatient2, epa.getPatients().get(1));
        assertEquals(expectedPatient3, epa.getPatients().get(2));
    }

    /**
     * Testing method getDoctors()
     */
    @Test
    public void getDoctors() {
        // creating 3 objects Doctors to compare with the output of method getDoctors()
        Doctor expectedDoctor1 = new Doctor(111);
        Doctor expectedDoctor2 = new Doctor(112);
        Doctor expectedDoctor3 = new Doctor(113);
        // testing
        assertEquals(expectedDoctor1, epa.getDoctors().get(0));
        assertEquals(expectedDoctor2, epa.getDoctors().get(1));
        assertEquals(expectedDoctor3, epa.getDoctors().get(2));
    }

    /**
     * Testing method getActiveUser()
     */
    @Test
    public void getActiveUser() {
        epa.setActiveUser(new Doctor(111));
        assertEquals(expectedUser, epa.getActiveUser());

    }

    /**
     * Testing method getLoadedPatient()
     */
    @Test
    public void getLoadedPatient() {
        epa.setLoadedPatient(new Patient(333));
        assertEquals(expectedLoadedPatient, epa.getLoadedPatient());
    }

    /**
     * Testing method setActiveUSer()
     */
    @Test
    public void setActiveUser() {
        epa.setActiveUser(new Doctor(111));
        assertEquals(expectedUser, epa.getActiveUser());
    }

    /**
     * Testing method setLoadedPatient()
     */
    @Test
    public void setLoadedPatient() {
        epa.setLoadedPatient(new Patient(333));
        assertEquals(expectedLoadedPatient, epa.getLoadedPatient());
    }
}