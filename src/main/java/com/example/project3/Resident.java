package com.example.project3;

/**
 *  A class that stores information about a resident student.
 *  This information includes a name, major, credits.
 *  @author Sharukh Khan, Hamad Naveed
 */
public class Resident extends Student {
    private int scholarship;

    public static final int PARTTIME_CREDIT_HOUR_COST = 404;
    public static final int FULLTIME_TUITION_COST = 12536;
    public static final int MAX_FINANCIAL_AID_AMOUNT = 10000;

    /**
     * Creates Resident student object type
     * @param profile The student's last name, first name, and DOB
     * @param major The student's major
     * @param creditCompleted The student's total credit
     */
    public Resident(Profile profile, Major major, int creditCompleted) {
        super(profile, major, creditCompleted);
    }

    /**
     * Creates Resident student object type
     * @param profile The student's last name, first name, and DOB
     */
    public Resident(Profile profile) {
        super(profile);
    }

    /**
     * Creates Resident student object type
     * @param profile The student's last name, first name, and DOB
     * @param major  The student's major
     */
    public Resident(Profile profile, Major major) {
        super(profile, major);
    }

    /**
     * Calculates the tuition due for a non-resident student.
     * @param creditsEnrolled
     * @return tuition
     */
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
        if (scholarship > MAX_FINANCIAL_AID_AMOUNT) {
            scholarship = MAX_FINANCIAL_AID_AMOUNT;
        }
        tuition -= scholarship;
        return tuition;
    }

    /**
     * Returns the scholarship amount for the student.
     * @return the scholarship amount
     */
    public int getScholarship() {
        return scholarship;
    }

    /**
     * Sets the scholarship amount for the student.
     * Scholarship amount is capped at $10,000.
     * @param scholarship the new scholarship amount
     */
    public void setScholarship(int scholarship) {
        if (scholarship > MAX_FINANCIAL_AID_AMOUNT) {
            scholarship = MAX_FINANCIAL_AID_AMOUNT;
        }
        this.scholarship = scholarship;
    }

    /**
     * Check if student is a resident
     * @return boolean true
     */
    @Override
    public boolean isResident() {
        return true;
    }

    /**
     * Returns true string statement from super class and this class
     */
    public String toString() {
        return super.toString() + " (resident)";
    }
}