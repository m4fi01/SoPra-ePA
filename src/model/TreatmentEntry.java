package model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author  Thomas Alexander Hövelmann
 */
public class TreatmentEntry implements Serializable,Comparable<TreatmentEntry> {

    /**
 	 * Date and time of the appointment.
 	 */
    private LocalDateTime dateAndTime;

    /**
 	 * The reason for the appointment, symptoms if applicable.
 	 */
    private String reason;

    /**
 	 * The diagnosis as the result of the appointment (ICD codes).
 	 */
    private String diagnosis;

    /**
 	 * The prescribed treatment according to the diagnosis.
 	 */
    private String treatment;

    /**
 	 * Other notes associated with the appointment.
 	 */
    private String notes;

    /**
 	 * Collection of all permissions for doctors to interact with a specific treatment entry {@link Permission},
     * {@link TreatmentEntry}.
 	 */
    private ArrayList<Permission> permissions;

    /**
     * Collection of all log entries for this treatment entry {@link TreatmentLogEntry}.
 	 */
    private ArrayList<TreatmentLogEntry> logEntries;

    /**
 	 * The creator of this treatment entry; has full permission for this entry, the only user who can edit this
     * treatment entry {@link Doctor}.
 	 */
    private final Doctor creator;

    /**
     * Constructs a new {@code TreatmentEntry}-object with the specified creator {@link #creator} and date and time of
     * the appointment {@link #dateAndTime}.
     *
     * @param creator {@link #creator}
     * @param dateAndTime {@link #dateAndTime}
     * @throws IllegalArgumentException if any of the parameters is {@code null}
     */
    public TreatmentEntry(Doctor creator, LocalDateTime dateAndTime) {
        if(creator == null || dateAndTime == null) {
            throw new IllegalArgumentException();
        }
        this.creator = creator;
        this.dateAndTime = dateAndTime;
        this.permissions = new ArrayList<>();
        this.logEntries = new ArrayList<>();
        this.reason = "";
        this.diagnosis = "";
        this.treatment = "";
        this.notes = "";
    }

    /**
     * Returns {@link #dateAndTime}. Note: Never {@code null}.
     *
     * @return {@link #dateAndTime}
     */
    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    /**
     * Returns {@link #reason}. Note: Never {@code null}.
     *
     * @return {@link #reason}
     */
    public String getReason() {
        return reason;
    }

    /**
     * Returns {@link #diagnosis}. Note: Never {@code null}.
     *
     * @return {@link #diagnosis}
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Returns {@link #treatment}. Note: Never {@code null}.
     *
     * @return {@link #treatment}
     */
    public String getTreatment() {
        return treatment;
    }

    /**
     * Returns {@link #notes}. Note: Never {@code null}.
     *
     * @return {@link #notes}
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Returns {@link #permissions}. Note: Never {@code null}.
     *
     * @return {@link #permissions}
     */
    public ArrayList<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Returns {@link #logEntries}. Note: Never {@code null}.
     *
     * @return {@link #logEntries}
     */
    public ArrayList<TreatmentLogEntry> getLogEntries() {
        return logEntries;
    }

    /**
     * Returns {@link #creator}. Note: Never {@code null}.
     *
     * @return {@link #creator}
     */
    public Doctor getCreator() {
        return creator;
    }

    /**
     * Sets {@link #dateAndTime} as specified.
     *
     * @param dateAndTime {@link #dateAndTime}
     * @throws IllegalArgumentException if the specified {@link LocalDateTime}-object is {@code null}
     */
    public void setDateAndTime(LocalDateTime dateAndTime) {
        if(dateAndTime == null) {
            throw new IllegalArgumentException();
        }
        this.dateAndTime = dateAndTime;
    }

    /**
     * Sets {@link #reason} as specified. Note: {@code null} is interpreted like "".
     *
     * @param reason {@link #reason}
     */
    public void setReason(String reason) {
        if(reason == null) {
            reason = "";
        }
        this.reason = reason;
    }

    /**
     * Sets {@link #diagnosis} as specified. Note: {@code null} is interpreted like "".
     *
     * @param diagnosis {@link #diagnosis}
     */
    public void setDiagnosis(String diagnosis) {
        if(diagnosis == null) {
            diagnosis = "";
        }
        this.diagnosis = diagnosis;
    }

    /**
     * Sets {@link #treatment} as specified. Note: {@code null} is interpreted like "".
     *
     * @param treatment {@link #treatment}
     */
    public void setTreatment(String treatment) {
        if(treatment == null) {
            treatment = "";
        }
        this.treatment = treatment;
    }

    /**
     * Sets {@link #notes} as specified. Note: {@code null} is interpreted like "".
     *
     * @param notes {@link #notes}
     */
    public void setNotes(String notes) {
        if(notes == null) {
            notes = "";
        }
        this.notes = notes;
    }

    /**
     * Returns a shallow copy of this object for logging purposes.
     *
     * @return A shallow copy of this object.
     */
    public TreatmentEntry getCopyForLogging() {
        TreatmentEntry logCopy = new TreatmentEntry(creator, dateAndTime);
        logCopy.diagnosis = diagnosis;
        logCopy.notes = notes;
        logCopy.reason = reason;
        logCopy.treatment = treatment;
        return logCopy;
    }

    /**
     * Compares two treatment entries and returns -1,0 or 1 depending on their chronology
     *
     * @param tEntry Treatment entry to compare to
     * @return -1,0 or 1 if the dateAndTime of the calling treatment entry is chronological before, at or after the
     * specified treatment entry
     */
    @Override
    public int compareTo(TreatmentEntry tEntry) {
        if(this.dateAndTime.isBefore(tEntry.dateAndTime)) {
            return -1;
        } else if(this.dateAndTime.isAfter(tEntry.dateAndTime)) {
            return 1;
        } else {
            return 0;
        }
    }
}
