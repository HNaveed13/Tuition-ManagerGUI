package com.example.project3;

/**
 * @author Sharukh Khan, Hamad Naveed
 */

public class EnrollStudent {
    private Profile profile;
    private int creditsEnrolled;

    /**
     * EnrollStudent object array created.
     */
    public EnrollStudent(Profile profile){
        this.profile = profile;
    }

    public EnrollStudent(Profile profile, int creditsEnrolled){
        this.profile = profile;
        this.creditsEnrolled = creditsEnrolled;

    }

    /**
     * return profile of EnrollStudent
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * set credits for enrolled student
     * @param newCreditsEnrolled
     */
    public void setCreditsEnrolled(int newCreditsEnrolled) {
        this.creditsEnrolled = newCreditsEnrolled;
    }

    /**
     * return credits for enrolled student
     */
    public int getCreditsEnrolled() {
        return this.creditsEnrolled;
    }

    /**
     * Compares EnrollStudent objects.
     * Returns true if EnrollStudent objects are equal, returns false if they are different.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof EnrollStudent)) {
            return false;
        }
        EnrollStudent other = (EnrollStudent) obj;
        return this.profile.equals(other.profile);
    }

    /**
     * Converts student information into a single string printout.
     */
    @Override
    public String toString() {
        return profile + ": credits enrolled: " + creditsEnrolled;
    }


}