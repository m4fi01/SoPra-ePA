package model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

/**
 * @author Leon Krick
 * Test class for doctors
 */
public class DoctorTest {

    private Doctor doctor1;
    private Doctor doctor2;

    /**
     * initializes Doctors to use for testing
     */
    @Before
    public void setUp() throws Exception {
        doctor1 = new Doctor(234);
        doctor2 = new Doctor(-20);
    }

    /**
     * Tests toString
     */
    @Test
    public void testToString() {
        String toString1 = doctor1.toString();
        String toString2 = doctor2.toString();
        assertEquals("Printed doctor1's ID wrongly", "(ARZT-ID) 234", toString1);
        assertEquals("Printed doctor2's ID wrongly", "(ARZT-ID) -20", toString2);
    }
}