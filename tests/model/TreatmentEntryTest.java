package model;

import org.junit.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * @author Thomas Alexander Hövelmann
 * Test class for treatment entries
 */
public class TreatmentEntryTest {

    /**
     * Test getDateAndTime
     */
    @Test
    public void getDateAndTime() {
        Doctor doctor = new Doctor(1);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, lDateTime);
        assertEquals(tEntry.getDateAndTime(), lDateTime);
    }

    /**
     * Tests getReason and setReason
     */
    @Test
    public void getReason() {
        Doctor doctor = new Doctor(1);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, lDateTime);
        String reason = "Test";
        tEntry.setReason(reason);
        assertEquals(tEntry.getReason(), reason);

        String reason2 = "";
        tEntry.setReason(reason2);
        assertEquals(tEntry.getReason(), reason2);

        tEntry.setReason(null);
        assertEquals("", tEntry.getReason());
    }

    /**
     * Tests getDiagnosis and setDiagnosis
     */
    @Test
    public void getDiagnosis() {
        Doctor doctor = new Doctor(1);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, lDateTime);
        String diagnosis = "Test";
        tEntry.setDiagnosis(diagnosis);
        assertEquals(tEntry.getDiagnosis(), diagnosis);

        String diagnosis2 = "";
        tEntry.setDiagnosis(diagnosis2);
        assertEquals(tEntry.getDiagnosis(), diagnosis2);

        tEntry.setDiagnosis(null);
        assertEquals("", tEntry.getDiagnosis());
    }

    /**
     * Tests getTreatment and setTreatment
     */
    @Test
    public void getTreatment() {
        Doctor doctor = new Doctor(1);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, lDateTime);
        String treatment = "Test";
        tEntry.setTreatment(treatment);
        assertEquals(tEntry.getTreatment(), treatment);

        String treatment2 = "";
        tEntry.setTreatment(treatment2);
        assertEquals(tEntry.getTreatment(), treatment2);

        tEntry.setTreatment(null);
        assertEquals("", tEntry.getTreatment());
    }

    /**
     * Tests getNotes and setNotes
     */
    @Test
    public void getNotes() {
        Doctor doctor = new Doctor(1);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, lDateTime);
        String notes = "Test";
        tEntry.setNotes(notes);
        assertEquals(tEntry.getNotes(), notes);

        String notes2 = "";
        tEntry.setNotes(notes2);
        assertEquals(tEntry.getNotes(), notes2);

        tEntry.setNotes(null);
        assertEquals("", tEntry.getNotes());
    }

    /**
     * Tests getPermission
     */
    @Test
    public void getPermissions() {
        Doctor doctor = new Doctor(1);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, lDateTime);
        assertTrue(tEntry.getPermissions().isEmpty());

        Doctor doctorPermission = new Doctor(2);
        Permission permission = new Permission(doctorPermission, true, true, true, true, true);
        ArrayList<Permission> permissionList = tEntry.getPermissions();
        permissionList.add(permission);
        assertTrue(tEntry.getPermissions().contains(permission));

        Permission permission2 = new Permission(doctorPermission, false, false, true, true, true);
        permissionList.add(permission2);
        assertTrue(tEntry.getPermissions().contains(permission));
        assertTrue(tEntry.getPermissions().contains(permission2));
    }

    /**
     * Tests getLogEntries
     */
    @Test
    public void getLogEntries() {
        Doctor doctor = new Doctor(1);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, lDateTime);
        assertTrue(tEntry.getLogEntries().isEmpty());

        LocalDateTime changeDateTime = LocalDateTime.of(1900, 3, 3, 12, 15);
        TreatmentLogEntry tLogEntry = new TreatmentLogEntry(changeDateTime, tEntry, doctor);
        tEntry.getLogEntries().add(tLogEntry);
        assertTrue(tEntry.getLogEntries().contains(tLogEntry));
    }

    /**
     * Tests getCreator
     */
    @Test
    public void getCreator() {
        Doctor doctor = new Doctor(150);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, lDateTime);
        assertEquals(tEntry.getCreator(), doctor);
    }

    /**
     * Tests getCopyForLogging
     */
    @Test
    public void getCopyForLogging() {
        Doctor doctor = new Doctor(1);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, lDateTime);
        tEntry.setReason("Reason");
        tEntry.setDiagnosis("Diagnosis");
        tEntry.setTreatment("Treatment");
        tEntry.setNotes("Notes");

        LocalDateTime changeDateTime = LocalDateTime.of(1900, 3, 3, 12, 15);
        TreatmentLogEntry tLogEntry = new TreatmentLogEntry(changeDateTime, tEntry, doctor);
        tEntry.getLogEntries().add(tLogEntry);

        Doctor doctorPermission = new Doctor(2);
        Permission permission = new Permission(doctorPermission, true, true, true, true, true);
        tEntry.getPermissions().add(permission);

        assertEquals("Reason", tEntry.getCopyForLogging().getReason());
        assertEquals("Diagnosis", tEntry.getCopyForLogging().getDiagnosis());
        assertEquals("Treatment", tEntry.getCopyForLogging().getTreatment());
        assertEquals("Notes", tEntry.getCopyForLogging().getNotes());
        assertTrue(tEntry.getCopyForLogging().getLogEntries().isEmpty());
        assertTrue(tEntry.getCopyForLogging().getPermissions().isEmpty());
    }

    /**
     * Tests setDateAndTime
     */
    @Test
    public void setDateAndTime() {
        Doctor doctor = new Doctor(1);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, lDateTime);

        LocalDateTime nDateTime = LocalDateTime.of(1900, 3, 3, 12, 15);
        tEntry.setDateAndTime(nDateTime);

        assertEquals(tEntry.getDateAndTime(), nDateTime);
    }

    /**
     * Tests setDateAndTime with parameter null
     */
    @Test(expected = IllegalArgumentException.class)
    public void setDateAndTimeToNull() {
        Doctor doctor = new Doctor(1);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, lDateTime);

        tEntry.setDateAndTime(null);
    }

    /**
     * Tests the constructor with parameter null for doctor
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructWithNullDoctor() {
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        TreatmentEntry tEntry = new TreatmentEntry(null, lDateTime);
    }

    /**
     * Tests the constructor with parameter null for dateAndTime
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructWithNullDateAndTime() {
        Doctor doctor = new Doctor(1);
        TreatmentEntry tEntry = new TreatmentEntry(doctor, null);
    }

    /**
     * Tests the constructor with parameter null for both parameters
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructWithNull() {
        TreatmentEntry tEntry = new TreatmentEntry(null, null);
    }

    /**
     * Tests if the comparison of two treatment entries works as intended
     */
    @Test
    public void compareToTest() {
        Doctor doctor = new Doctor(1);
        LocalDateTime lDateTime1 = LocalDateTime.of(2021, 3, 3, 12, 00);
        LocalDateTime lDateTime2 = LocalDateTime.of(2021, 3, 3, 13, 00);
        TreatmentEntry tEntry1 = new TreatmentEntry(doctor, lDateTime1);
        TreatmentEntry tEntry2 = new TreatmentEntry(doctor, lDateTime2);
        assertEquals(-1,tEntry1.compareTo(tEntry2));
        assertEquals(0,tEntry1.compareTo(tEntry1));
        assertEquals(1,tEntry2.compareTo(tEntry1));
    }
}