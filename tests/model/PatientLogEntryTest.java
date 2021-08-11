package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Test class for patient log entry
 */
public class PatientLogEntryTest {
    /**
     * Creating objects to test methods of class PatientLogEntry
     */
    Patient patient;
    PatientLogEntry patientLogEntry;
    Patient oldPatient;

    /**
     * Initializing data for tests
     */
    @Before
    public void setUp() throws Exception {
        oldPatient = new Patient(112);
        LocalDateTime changeDateAndTime = LocalDateTime.of(2021, 03, 03, 13, 33);
        patientLogEntry = new PatientLogEntry(changeDateAndTime, oldPatient);
    }

    /**
     * Testing method getEditor()
     */
    @Test
    public void getEditor() {
        assertEquals(oldPatient, patientLogEntry.getEditor());
    }

    /**
     * Testing method getOOldPatient()
     */
    @Test
    public void getOldPatient() {
        assertEquals(oldPatient,patientLogEntry.getOldPatient());
    }
}