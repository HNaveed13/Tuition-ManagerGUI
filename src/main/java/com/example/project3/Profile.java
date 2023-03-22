package com.example.project3;
/**
 * Creates Profile objects utilizing user input.
 * If input is valid, Profile information from TuitionManager is utilized
 * to create a Profile object.
 * @author Sharukh Khan, Hamad Naveed
 */

public class Profile implements Comparable<Profile> {
    private String lname;
    private String fname;
    private Date dob; //use the Date class described in (f)

    /**
     * Creates profile object with last name, first name, and date of birthdate elements.
     *
     * @param lname given by the user
     * @param fname given by the user
     * @param dob   given by the user
     */
    public Profile(String lname, String fname, Date dob) {
        this.lname = lname;
        this.fname = fname;
        this.dob = dob;
    }
    /**
     * Returns profile object's last name.
     * @return profile last name string.
     */
    public String getLname() {
        return lname;
    }

    /**
     * Returns profile object's first name.
     * @return profile first name string.
     */
    public String getFname() {
        return fname;
    }

    /**
     * Returns profile object's DOB.
     * @return profile DOB.
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Converts profile information into a single string printout.
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob;
    }

    /**
     * Compares profile objects.
     * Returns true if profile objects are equal, returns false if they are different.
     */
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (!(obj instanceof Profile))
            return false;
        Profile profile = (Profile) obj;
        return lname.equals(profile.getLname()) && fname.equals(profile.getFname())
                && dob.equals(profile.getDob());
    }

    /**
     * Compares profile objects.
     * Returns true if profile objects are equal, returns false if they are different.
     */
    @Override
    public int compareTo(Profile o) {
        int lastNameComp = this.lname.toLowerCase().compareTo(o.lname.toLowerCase());
        if (lastNameComp != 0) {
            return lastNameComp;
        }
        int firstNameComp = this.fname.toLowerCase().compareTo(o.fname.toLowerCase());
        if (firstNameComp != 0) {
            return firstNameComp;
        }
        return this.dob.compareTo(o.dob);
    }
}
