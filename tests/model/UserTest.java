package model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * @author Thomas Alexander Hövelmnn
 * Test class for user
 */
public class UserTest {
    /**
     * Tests the equals method
     */
    @Test
    public void equalsTest() {
        Doctor doctor1 = new Doctor(1);
        Doctor doctor2 = new Doctor(2);
        assertNotEquals(null,doctor1);
        assertNotEquals(new Object(),doctor1);
        assertNotEquals(doctor2,doctor1);
        assertEquals(doctor1,doctor1);
        assertNotEquals(doctor1,null);
        assertNotEquals(doctor1,new Object());
    }

    /**
     * Tests the hashCode method
     */
    @Test
    public void hashCodeTest() {
        Doctor doctor = new Doctor(12324);
        assertEquals(12324,doctor.hashCode());
    }
}
