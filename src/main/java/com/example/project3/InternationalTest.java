package com.example.project3;
import org.junit.Test;
import static org.junit.Assert.*;

public class InternationalTest {

    @Test
    public void testTuitionDue() {

        Date dob = new Date("01/01/2000");
        Profile p1 = new Profile("Doe", "John", dob);
        Major m1 = Major.CS;
        International i1 = new International(p1, m1, 12, false);
        assertEquals(35655.0, i1.tuitionDue(12), 0.001);

        Date dob1 = new Date("01/01/2000");
        Profile p2 = new Profile("Doe", "Jane", dob1);
        Major m2 = Major.EE;
        International i2 = new International(p2, m2, 16, true);
        assertEquals(5918.0, i2.tuitionDue(12), 0.001);

        Date dob2 = new Date("01/01/2000");
        Profile p3 = new Profile("Smith", "John", dob2);
        Major m3 = Major.BAIT;
        International i3 = new International(p3, m3, 10, false);
        assertEquals(35655.0, i3.tuitionDue(10), 0.001);

        Date dob3 = new Date("01/01/2000");
        Profile p4 = new Profile("Smith", "Jane", dob3);
        Major m4 = Major.BAIT;
        International i4 = new International(p4, m4, 18, false);
        assertEquals(37587.0, i4.tuitionDue(18), 0.001);

        Profile p5 = new Profile("Johnson", "John", dob);
        Major m5 = Major.CS;
        International i5 = new International(p5, m5, 24, true);
        assertEquals(5918.0, i5.tuitionDue(12), 0.001);

        Profile p6 = new Profile("Johnson", "Jane", dob);
        Major m6 = Major.BAIT;
        International i6 = new International(p6, m6, 3, false);
        assertEquals(35655.0, i6.tuitionDue(3), 0.001);

        Profile p7 = new Profile("Kim", "John", dob);
        Major m7 = Major.CS;
        International i7 = new International(p7, m7, 17, true);
        assertEquals(-1.0, i7.tuitionDue(17), 0.001);

        Profile p8 = new Profile("Kim", "Jane", dob);
        Major m8 = Major.BAIT;
        International i8 = new International(p8, m8, 8, false);
        assertEquals(35655.0, i8.tuitionDue(8), 0.001);
    }
}