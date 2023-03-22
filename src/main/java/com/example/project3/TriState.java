package com.example.project3;

/**
 *  A class that stores information about a student from the tri-state area.
 *  This information includes a name, major, credits, and state.
 *  @author Sharukh Khan, Hamad Naveed
 */

public class TriState extends NonResident {
    private String state;

    public static final int NY_TRISTATE_DISCOUNT = 4000;
    public static final int CT_TRISTATE_DISCOUNT = 5000;

    /**
     * Creates TriState student object type
     * @param profile The student's last name, first name, and DOB
     * @param major The student's major
     * @param creditCompleted given by the user
     * @param state The tri-state location the student is from
     */
    public TriState(Profile profile, Major major, int creditCompleted, String state) {
        super(profile, major, creditCompleted);
        this.state = state;
    }

    /**
     * Calculates the tuition due for a tri-state student.
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
            if (state.equals("NY")) {
                tuition -= NY_TRISTATE_DISCOUNT;
            } else if (state.equals("CT")) {
                tuition -= CT_TRISTATE_DISCOUNT;
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

    public String toString() {
        return super.toString() + "(tri-state: " + state + ")";
    }

}
