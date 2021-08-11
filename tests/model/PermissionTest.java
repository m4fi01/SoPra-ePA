package model;

import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

/**
 * Test class for permissions
 */
public class PermissionTest {

    /**
     * Tests hasAccessToDateAndTime
     */
    @Test
    public void hasAccessToDateAndTime() {
        Doctor doctor = new Doctor(1);
        Permission permission = new Permission(doctor, true, true, true, true, true);
        assertTrue(permission.hasAccessToDateAndTime());
    }

    /**
     * Tests hasAccessToReason
     */
    @Test
    public void hasAccessToReason() {
        Doctor doctor = new Doctor(1);
        Permission permission = new Permission(doctor, true, true, true, true, true);
        assertTrue(permission.hasAccessToReason());
    }

    /**
     * Tests hasAccessToDiagnosis
     */
    @Test
    public void hasAccessToDiagnosis() {
        Doctor doctor = new Doctor(1);
        Permission permission = new Permission(doctor, true, true, true, true, true);
        assertTrue(permission.hasAccessToDiagnosis());
    }

    /**
     * Tests hasAccessToTreatment
     */
    @Test
    public void hasAccessToTreatment() {
        Doctor doctor = new Doctor(1);
        Permission permission = new Permission(doctor, true, true, true, true, true);
        assertTrue(permission.hasAccessToTreatment());
    }

    /**
     * Tests hasAccessToNotes
     */
    @Test
    public void hasAccessToNotes() {
        Doctor doctor = new Doctor(1);
        Permission permission = new Permission(doctor, true, true, true, true, true);
        assertTrue(permission.hasAccessToNotes());
    }

    /**
     * Tests getDoctor
     */
    @Test
    public void getDoctor() {
        Doctor doctor = new Doctor(2);
        Permission permission = new Permission(doctor, true, true, true, true, true);
        assertEquals(permission.getDoctor(), doctor);
    }

    /**
     * Tests getPermittedTreatmentEntry
     */
    @Test
    public void getPermittedTreatmentEntry() {
        Doctor doctor = new Doctor(1);
        Doctor doctor2 = new Doctor(2);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        Permission permission = new Permission(doctor, false, true, true, true, false);
        TreatmentEntry tEntry = new TreatmentEntry(doctor2, lDateTime);
        tEntry.setReason("Reason");
        tEntry.setDiagnosis("Diagnosis");
        tEntry.setTreatment("Treatment");
        tEntry.setNotes("Notes");
        assertTrue(permission.getPermittedTreatmentEntry(tEntry).getDateAndTime().isEqual(LocalDateTime.of(404,1,1,
                4,0,4)));
        assertEquals("Reason", permission.getPermittedTreatmentEntry(tEntry).getReason());
        assertEquals("Diagnosis", permission.getPermittedTreatmentEntry(tEntry).getDiagnosis());
        assertEquals("Treatment", permission.getPermittedTreatmentEntry(tEntry).getTreatment());
        assertEquals("", permission.getPermittedTreatmentEntry(tEntry).getNotes());

        Permission permission2 = new Permission(doctor,false,false,false,false,false);
        TreatmentEntry pTEntry = permission2.getPermittedTreatmentEntry(tEntry);
        assertEquals(pTEntry.getDateAndTime(),LocalDateTime.of(404,1,1, 4,0,4));
        assertEquals(pTEntry.getReason(),"");
        assertEquals(pTEntry.getDiagnosis(),"");
        assertEquals(pTEntry.getTreatment(),"");
        assertEquals(pTEntry.getNotes(),"");
    }

    /**
     * Tests getPermittedTreatmentLogEntry
     */
    @Test
    public void getPermittedTreatmentLogEntry() {
        Doctor doctor = new Doctor(1);
        Doctor doctor2 = new Doctor(2);
        LocalDateTime lDateTime = LocalDateTime.of(2021, 3, 3, 12, 15);
        Permission permission = new Permission(doctor, true, true, true, false, true);
        TreatmentEntry tEntry = new TreatmentEntry(doctor2, lDateTime);
        tEntry.setReason("Reason");
        tEntry.setDiagnosis("Diagnosis");
        tEntry.setTreatment("Treatment");
        tEntry.setNotes("Notes");

        LocalDateTime changeDateTime = LocalDateTime.of(1900, 3, 3, 12, 15);
        TreatmentLogEntry tLogEntry = new TreatmentLogEntry(changeDateTime, tEntry, doctor2);
        TreatmentLogEntry permittedLogEntry = permission.getPermittedTreatmentLogEntry(tLogEntry);


        assertTrue(permittedLogEntry.getOldTreatmentEntry().getDateAndTime().isEqual(LocalDateTime.of(2021, 3, 3, 12, 15)));
        assertEquals("Reason", permittedLogEntry.getOldTreatmentEntry().getReason());
        assertEquals("Diagnosis", permittedLogEntry.getOldTreatmentEntry().getDiagnosis());
        assertEquals("", permittedLogEntry.getOldTreatmentEntry().getTreatment());
        assertEquals("Notes", permittedLogEntry.getOldTreatmentEntry().getNotes());
    }

    /**
     * Tests the constructor with null
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructWithNull() {
        Permission permission = new Permission(null, true, true, true, false, true);
    }
}