package model;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author  Thomas Alexander Hövelmann
 *
 * @see TreatmentEntry
 * @see LogEntry
 */
public class TreatmentLogEntry extends LogEntry implements Serializable {
    /**
 	 * The {@link TreatmentEntry} before it was modified.
 	 */
    private TreatmentEntry oldTreatmentEntry;

    /**
     * Constructs a new {@code TreatmentLogEntry}-object with the specified date and time of the change made
     * {@link LogEntry}, the treatment entry to be logged {@link #oldTreatmentEntry} and the doctor who made the change
     * {@link LogEntry}.
     *
     * @param changeDateAndTime Date and time of the change made {@link LogEntry}
     * @param oldTreatmentEntry {@link #oldTreatmentEntry}
     * @param editor The {@link Doctor} who made the change. {@link LogEntry}
     * @throws IllegalArgumentException if any of the parameters is {@code null}
     * @throws IllegalStateException if the {@code editor} is not equal to the {@code creator} of the old treatment
     * entry
     */
    public TreatmentLogEntry(LocalDateTime changeDateAndTime, TreatmentEntry oldTreatmentEntry, Doctor editor) {
        super(changeDateAndTime, editor);
        if(oldTreatmentEntry == null) {
            throw new IllegalArgumentException();
        }
        if(!oldTreatmentEntry.getCreator().equals(editor)) {
            throw new IllegalStateException();
        }
        this.oldTreatmentEntry = oldTreatmentEntry.getCopyForLogging();
    }

    /**
     * Returns {@link #oldTreatmentEntry}. Note: Never {@code null}.
     *
     * @return {@link #oldTreatmentEntry}
     */
    public TreatmentEntry getOldTreatmentEntry() {
        return oldTreatmentEntry;
    }

    /**
     * Method that overrides method in superclass to maintain type safety.
     * Returns the {@link Doctor} who made the change.{@link LogEntry}. Note: Never {@code null}.
     *
     * @return The {@link Doctor} who made the change.{@link LogEntry}
     */
    @Override
    public Doctor getEditor() {
        return (Doctor)super.getEditor();
    }
}
