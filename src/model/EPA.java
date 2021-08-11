package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author  Thomas Alexander Hövelmann
 */
public class EPA implements Serializable {
    /**
 	 * Collection of all users registered as patients {@link Patient}.
 	 */
    private ArrayList<Patient> patients;

    /**
 	 * Collection of all users registered as doctors {@link Doctor}.
 	 */
    private ArrayList<Doctor> doctors;

    /**
 	 * Reference to the currently logged in user.
 	 */
    private User activeUser;

    /**
 	 * Reference to the patient whose record is currently loaded.
 	 */
    private Patient loadedPatient;

    /**
     * Constructs a new and empty {@code EPA}-object.
     */
    public EPA() {
        this.patients = new ArrayList<>();
        this.doctors = new ArrayList<>();
    }

    /**
     * Returns {@link #patients}.
     *
     * @return {@link #patients}
     */
    public ArrayList<Patient> getPatients() {
        return patients;
    }

    /**
     * Returns {@link #doctors}.
     *
     * @return {@link #doctors}
     */
    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    /**
     * Returns {@link #activeUser}.
     *
     * @return {@link #activeUser}
     */
    public User getActiveUser() {
        return activeUser;
    }

    /**
     * Returns {@link #loadedPatient}.
     *
     * @return {@link #loadedPatient}
     */
    public Patient getLoadedPatient() {
        return loadedPatient;
    }

    /**
     * Sets {@link #activeUser} as specified.
     *
     * @param activeUser {@link #activeUser}
     */
    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * Sets {@link #loadedPatient} as specified.
     *
     * @param loadedPatient {@link #loadedPatient}
     */
    public void setLoadedPatient(Patient loadedPatient) {
        this.loadedPatient = loadedPatient;
    }
}
