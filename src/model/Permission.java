package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author  Thomas Alexander Hövelmann
 *
 * @see TreatmentEntry
 * @see TreatmentLogEntry
 */
public class Permission implements Serializable {
    /**
 	 * Permission to interact with the date and time as part of a treatment entry {@link TreatmentEntry}.
 	 */
    private boolean accessToDateAndTime;

	/**
	 * Permission to interact with the reason as part of a treatment entry {@link TreatmentEntry}.
	 */
    private boolean accessToReason;

	/**
	 * Permission to interact with the diagnosis as part of a treatment entry {@link TreatmentEntry}.
	 */
    private boolean accessToDiagnosis;

	/**
	 * Permission to interact with the treatment as part of a treatment entry {@link TreatmentEntry}.
	 */
    private boolean accessToTreatment;

	/**
	 * Permission to interact with the notes as part of a treatment entry {@link TreatmentEntry}.
	 */
    private boolean accessToNotes;

    /**
 	 * The {@link Doctor} who has the specified permissions.
 	 */
    private Doctor doctor;

	/**
	 * Constructs a new {@code Permission}-object with the specified permissions for the specified {@link Doctor}.
	 *
	 * @param doctor {@link #doctor}
	 * @param accessToDateAndTime {@link #accessToDateAndTime}
	 * @param accessToReason {@link #accessToReason}
	 * @param accessToDiagnosis {@link #accessToDiagnosis}
	 * @param accessToTreatment {@link #accessToTreatment}
	 * @param accessToNotes {@link #accessToNotes}
	 * @throws IllegalArgumentException if the specified {@link Doctor}-object is {@code null}
	 */
	public Permission(Doctor doctor, boolean accessToDateAndTime, boolean accessToReason, boolean accessToDiagnosis, boolean accessToTreatment, boolean accessToNotes) {
		if(doctor == null) {
			throw new IllegalArgumentException();
		}
		this.doctor = doctor;
		this.accessToDateAndTime = accessToDateAndTime;
		this.accessToReason = accessToReason;
		this.accessToDiagnosis = accessToDiagnosis;
		this.accessToTreatment = accessToTreatment;
		this.accessToNotes = accessToNotes;
	}

	/**
	 * Returns {@link #accessToDateAndTime}.
	 *
	 * @return {@link #accessToDateAndTime}
	 */
	public boolean hasAccessToDateAndTime() {
		return accessToDateAndTime;
	}

	/**
	 * Returns {@link #accessToReason}.
	 *
	 * @return {@link #accessToReason}
	 */
	public boolean hasAccessToReason() {
		return accessToReason;
	}

	/**
	 * Returns {@link #accessToDiagnosis}.
	 *
	 * @return {@link #accessToDiagnosis}
	 */
	public boolean hasAccessToDiagnosis() {
		return accessToDiagnosis;
	}

	/**
	 * Returns {@link #accessToTreatment}.
	 *
	 * @return {@link #accessToTreatment}
	 */
	public boolean hasAccessToTreatment() {
		return accessToTreatment;
	}

	/**
	 * Returns {@link #accessToNotes}.
	 *
	 * @return {@link #accessToNotes}
	 */
	public boolean hasAccessToNotes() {
		return accessToNotes;
	}

	/**
	 * Returns {@link #doctor}. Note: Never {@code null}.
	 *
	 * @return {@link #doctor}
	 */
	public Doctor getDoctor() {
		return doctor;
	}

	/**
 	 * Returns a copy of the specified {@link TreatmentEntry}-object according to this permission. Note: If the access
	 * to date and time of the specified {@link TreatmentEntry}-object is forbidden the copy is dated to the 01.Jan.404
	 * 04:00:04, because {@code dateAndTime} must not be {@code null}.
 	 *
 	 * @param tEntry {@link TreatmentEntry} which is to be copied according to this permission
 	 * @return A copy of the specified {@link TreatmentEntry}-object according to this permission.
 	 */
    public TreatmentEntry getPermittedTreatmentEntry(TreatmentEntry tEntry) {
        TreatmentEntry permittedTEntry;
        if(accessToDateAndTime) {
        	permittedTEntry = new TreatmentEntry(tEntry.getCreator(), tEntry.getDateAndTime());
		} else {
        	permittedTEntry = new TreatmentEntry(tEntry.getCreator(),
					                             LocalDateTime.of(404,1,1,
														     4,0,4));
		}
        if(accessToReason) {
        	permittedTEntry.setReason(tEntry.getReason());
		}
        if(accessToTreatment) {
        	permittedTEntry.setTreatment(tEntry.getTreatment());
		}
        if(accessToDiagnosis) {
        	permittedTEntry.setDiagnosis(tEntry.getDiagnosis());
		}
        if(accessToNotes) {
        	permittedTEntry.setNotes(tEntry.getNotes());
		}
        return permittedTEntry;
    }

	/**
	 * Returns a copy of the specified {@link TreatmentLogEntry}-object according to this permission. Uses the method
	 * {@link #getPermittedTreatmentEntry(TreatmentEntry)} to copy the {@link TreatmentEntry}-object contained by the
	 * specified {@link TreatmentLogEntry}-object according to this permission.
	 *
	 * @param tLogEntry {@link TreatmentLogEntry} which is to be copied according to this permission
	 * @return A copy of the specified {@link TreatmentLogEntry}-object according to this permission.
	 */
	public TreatmentLogEntry getPermittedTreatmentLogEntry(TreatmentLogEntry tLogEntry) {
		TreatmentEntry permittedTEntry = getPermittedTreatmentEntry(tLogEntry.getOldTreatmentEntry());
		return new TreatmentLogEntry(tLogEntry.getChangeDateAndTime(),
									 permittedTEntry,
								     tLogEntry.getEditor());
	}
}
