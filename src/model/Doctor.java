package model;

import java.io.Serializable;

/**
 * @author  Thomas Alexander Hövelmann
 *
 * @see User
 */
public class Doctor extends User implements Serializable {
    /**
     * Constructs a new {@code Doctor}-object with the specified identification number {@link User}.
     *
     * @param doctorID Identification number of this doctor {@link User}
     */
    public Doctor(int doctorID) {
        super(doctorID);
    }

    /**
     * Returns a {@link String}-representation of this doctor.
     *
     * @return {@link String}-representation of this doctor.
     */
    @Override
    public String toString() {
        return "(ARZT-ID) " + getUserID();
    }
}
