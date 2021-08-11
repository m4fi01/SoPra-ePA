package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * @author Thomas Alexander Hövelmann
 * Test class for treatment log entries
 */
public class TreatmentLogEntryTest {

    /**
     * Creating object to test methods of class TreatmentLogEntry
     */
    private TreatmentLogEntry treatmentLogEntry;  // is used to test method getEditor()

    /**
     * Initializing data for testing of method getEditor()
     */
    @Before
    public void setUp() throws Exception {
        Doctor doctor = new Doctor(111);
        LocalDateTime dateAndTime = LocalDateTime.of(2011, 02, 03, 9, 33);
        TreatmentEntry oldTreatmentEntry = new TreatmentEntry(doctor, dateAndTime);
        TreatmentEntry expectedOldTreatmentEntry = new TreatmentEntry(doctor, dateAndTime);
        LocalDateTime changeDateAndTime = LocalDateTime.of(2021, 03, 03, 13, 33);
        treatmentLogEntry = new TreatmentLogEntry(changeDateAndTime, oldTreatmentEntry, doctor);
    }

    /**
     * Testing method getEditor()
     */
    @Test
    public void getEditor() {
        // creating an object Doctor to compare with the output of method getEditor()
        Doctor expectedEditor = new Doctor(111);
        // testing
        assertEquals(expectedEditor, treatmentLogEntry.getEditor());
    }

    /**
     * Tests the constructor with parameter null for all parameters
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructWithNull() {
        TreatmentLogEntry tLogEntry = new TreatmentLogEntry(null,null,null);
    }

    /**
     * Tests the constructor with parameter null for changeDateAndTime
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructWithNullChangeDateAndTime() {
        Doctor doctor = new Doctor(1);
        LocalDateTime changeDateAndTime = LocalDateTime.of(2021, 03, 03, 13, 33);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, changeDateAndTime);
        TreatmentLogEntry tLogEntry = new TreatmentLogEntry(null,tEntry,doctor);
    }

    /**
     * Tests the constructor with parameter null for oldTreatmentEntry
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructWithNullOldTreatmentEntry() {
        Doctor doctor = new Doctor(1);
        LocalDateTime changeDateAndTime = LocalDateTime.of(2021, 03, 03, 13, 33);
        TreatmentLogEntry tLogEntry = new TreatmentLogEntry(changeDateAndTime,null,doctor);
    }

    /**
     * Tests the constructor with parameter null for oldTreatmentEntry
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructWithNullEditor() {
        Doctor doctor = new Doctor(1);
        LocalDateTime changeDateAndTime = LocalDateTime.of(2021, 03, 03, 13, 33);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, changeDateAndTime);
        TreatmentLogEntry tLogEntry = new TreatmentLogEntry(changeDateAndTime,tEntry,null);
    }

    /**
     * Tests the constructor with a doctor who has not created the original treatmentEntry
     */
    @Test(expected = IllegalStateException.class)
    public void constructWithWrongEditor() {
        Doctor doctor = new Doctor(1);
        Doctor doctor2 = new Doctor(2);
        LocalDateTime changeDateAndTime = LocalDateTime.of(2021, 03, 03, 13, 33);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, changeDateAndTime);
        TreatmentLogEntry tLogEntry = new TreatmentLogEntry(changeDateAndTime,tEntry,doctor2);
    }
}