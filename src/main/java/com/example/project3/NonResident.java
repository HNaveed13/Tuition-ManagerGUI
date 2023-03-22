package com.example.project3;

/**
 *  A class that stores information about a non-resident student.
 *  This information includes a name, major, and credits.
 *  @author Sharukh Khan, Hamad Naveed
 */
public class NonResident extends Student {
    public static final int PARTTIME_CREDIT_HOUR_COST = 966;
    public static final int FULLTIME_TUITION_COST = 29737;

    /**
     * Creates non student object type
     * @param profile The student's last name, first name, and DOB
     * @param major The student's major
     * @param creditCompleted given by the user
     */
    public NonResident(Profile profile, Major major, int creditCompleted) {
        super(profile, major, creditCompleted);
    }

    /**
     * Calculates the tuition due for a non-resident student.
     */
    @Override
    public double tuitionDue(int creditsEnrolled) {
        double tuition;
        if (creditsEnrolled < 12) {
            tuition = (PARTTIME_CREDIT_HOUR_COST * creditsEnrolled) + (UNIVERSITY_FEE * PARTTIME_FEE_RATE);
        } else {
            tuition = FULLTIME_TUITION_COST + UNIVERSITY_FEE;
            if (creditsEnrolled > CREDIT_EXCEED_LIMIT) {
                tuition += PARTTIME_CREDIT_HOUR_COST * (creditsEnrolled - CREDIT_EXCEED_LIMIT);
            }
        }
        return tuition;
    }

    /**
     * Check if student is a resident
     * @return boolean false
     */
    @Override
    public boolean isResident() {
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + " (non-resident)";
    }

}