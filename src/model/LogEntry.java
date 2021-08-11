package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Abstract superclass that ensures type safety.
 *
 * @author  Thomas Alexander Hövelmann
 *
 * @see TreatmentLogEntry
 * @see PatientLogEntry
 */
public abstract class LogEntry implements Serializable {
    /**
     * Date and time of the change made.
     */
    private LocalDateTime changeDateAndTime;

    /**
     * The {@link User} who made the change.
     */
    private User editor;

    /**
     * Used to construct objects in subclasses.
     *
     * @param changeDateAndTime {@link #changeDateAndTime}
     * @param editor {@link #editor}
     * @throws IllegalArgumentException if any of the parameters is {@code null}
     */
    public LogEntry(LocalDateTime changeDateAndTime, User editor) {
        if(changeDateAndTime == null || editor == null) {
            throw new IllegalArgumentException();
        }
        this.changeDateAndTime = changeDateAndTime;
        this.editor = editor;
    }

    /**
     * Returns {@link #changeDateAndTime}. Note: Never {@code null}.
     *
     * @return {@link #changeDateAndTime}
     */
    public LocalDateTime getChangeDateAndTime() {
        return changeDateAndTime;
    }

    /**
     * Method that should be overridden in subclasses to maintain type safety.
     * Returns {@link #editor}. Note: Never {@code null}.
     *
     * @return {@link #editor}
     */
    protected User getEditor() {
        return editor;
    }
}
