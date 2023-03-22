package com.example.project3;
/**
 * Creates a collection of constant major data types.
 * @author Sharukh Khan, Hamad Naveed
 */
public enum Major {
    CS("01:198", "SAS"),
    MATH("01:640", "SAS"),
    ITI("04:547", "SC&I"),
    BAIT("33:136", "RBS"),
    EE("14:332", "SOE"),
    Unknown(null, null);

    /**
     * Creates enum major object with school code and name elements
     * @param code given by the user major they choose
     * @param schoolName given by the user major they choose
     */
    private final String code;
    private final String schoolName;

    Major(String code, String schoolName) {
        this.code = code;
        this.schoolName = schoolName;
    }

    /**
     * Returns major object's school name
     * @return code type String
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * Converts major information into a single string printout.
     */
    @Override
    public String toString() {
        return "(" + code + " " +  this.name() + " " + schoolName + ")";
    }
}
