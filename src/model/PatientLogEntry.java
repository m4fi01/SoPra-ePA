package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author  Thomas Alexander Hövelmann
 *
 * @see LogEntry
 * @see Patient
 */
public class PatientLogEntry extends LogEntry implements Serializable {
    /**
 	 * The {@link Patient} before it was modified.
 	 */
    private Patient oldPatient;

    /**
     * Constructs a new {@code PatientLogEntry}-object with the specified date and time of the change made
     * {@link LogEntry}, the patient to be logged {@link #oldPatient} and the same patient as the one who
     * made the change {@link LogEntry}.
     *
     * @param changeDateAndTime Date and time of the change made {@link LogEntry}
     * @param oldPatient {@link #oldPatient}
     * @throws IllegalArgumentException if any of the parameters is {@code null}
     */
    public PatientLogEntry(LocalDateTime changeDateAndTime, Patient oldPatient) {
        // Ensures that neither of the parameters is {@code null}
        super(changeDateAndTime, oldPatient);
        this.oldPatient = oldPatient.getCopyForLogging();
    }

    /**
     * Returns {@link #oldPatient}. Note: Never {@code null}.
     *
     * @return {@link #oldPatient}
     */
    public Patient getOldPatient() {
        return oldPatient;
    }

    /**
     * Method that overrides method in superclass to maintain type safety.
     * Returns the {@link Patient} who made the change.{@link LogEntry}. Note: Never {@code null}.
     *
     * @return The {@link Patient} who made the change.{@link LogEntry}
     */
    @Override
    public Patient getEditor() {
        return (Patient)super.getEditor();
    }
}
