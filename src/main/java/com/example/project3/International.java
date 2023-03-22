package com.example.project3;

/**
 *  A class that stores information about an international student.
 *  This information includes a name, major, credits, and study abroad status.
 *  @author Sharukh Khan, Hamad Naveed
 */
public class International extends NonResident {

    private boolean isStudyAbroad;
    public static final int HEALTH_INSURANCE = 2650;

    /**
     * Creates non student object type
     *
     * @param profile         The student's last name, first name, and DOB
     * @param major           The student's major
     * @param creditCompleted given by the user
     * @param isStudyAbroad true is studying abroad, otherwise false
     */
    public International(Profile profile, Major major, int creditCompleted, boolean isStudyAbroad) {
        super(profile, major, creditCompleted);
        this.isStudyAbroad = isStudyAbroad;
    }

    /**
     * Creates non student object type
     *
     * @param profile         The student's last name, first name, and DOB
     * @param major           The student's major
     * @param creditCompleted given by the user
     */
    public International(Profile profile, Major major, int creditCompleted) {
        super(profile, major, creditCompleted);
    }

    /**
     * Calculates the tuition due for an international student.
     */
    @Override
    public double tuitionDue(int creditsEnrolled) {
        double tuition;
        int maxCredits = isStudyAbroad ? 12 : 24;

        if (creditsEnrolled < 3 || creditsEnrolled > maxCredits) {
            return -1.0;
        }

        if (creditsEnrolled > 16) {
            tuition = FULLTIME_TUITION_COST + (PARTTIME_CREDIT_HOUR_COST * (creditsEnrolled - 16)) + UNIVERSITY_FEE + HEALTH_INSURANCE;
        } else {
            tuition = FULLTIME_TUITION_COST + UNIVERSITY_FEE + HEALTH_INSURANCE;
        }

        if (isStudyAbroad) {
            tuition = DEFAULT_COST;
            tuition = UNIVERSITY_FEE + HEALTH_INSURANCE;
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

    /**
     * Check if student is studying abroad
     * @return boolean
     */
    public boolean isStudyAbroad() {
        return isStudyAbroad;
    }

    /**
     * Returns true string statement from super class and this class
     */
    public String toString() {
        if (isStudyAbroad) {
            return super.toString() + " international: study abroad";
        } else {
            return super.toString() + " international";
        }
    }
}

