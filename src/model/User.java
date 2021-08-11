package model;

import java.io.Serializable;

/**
 * Abstract superclass that ensures type safety.
 *
 * @author  Thomas Alexander Hövelmann
 *
 * @see Doctor
 * @see Patient
 */
public abstract class User implements Serializable {
    /**
     * Identification number of this user.
     */
    private int userID;

    /**
     * Used to construct objects in subclasses.
     *
     * @param userID {@link #userID}
     */
    public User(int userID) {
        this.userID = userID;
    }

    /**
     * Returns {@link #userID}.
     *
     * @return {@link #userID}
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Compares the specified {@link Object} with this {@link User} for equality.
     *
     * @param obj the {@link Object} to be compared for equality with this {@link User}.
     * @return {@code true} if the specified {@link Object} is equal to this {@link User}; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userID == user.userID;
    }

    /**
     * Returns {@link #userID} as a unique hash code.
     *
     * @return {@link #userID}
     */
    @Override
    public int hashCode() {
        return userID;
    }
}
