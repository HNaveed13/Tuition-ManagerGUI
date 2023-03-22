package com.example.project3;
/**
 * Creates Student objects utilizing user input.
 * Student class is abstract
 * If input is valid, student information from TuitionManagerView.fxml is utilized
 * to create a Student object.
 * @author Sharukh Khan, Hamad Naveed
 */
public abstract class Student implements Comparable<Student> {
    private Profile profile;
    private Major major; //Major is an enum type
    private int creditCompleted;

    public static final int MIN_CREDITS = 3;
    public static final int MIN_FULLTIME_CREDITS = 12;
    public static final int MAX_CREDITS = 24;
    public static final int CREDIT_EXCEED_LIMIT = 16;
    public static final int UNIVERSITY_FEE = 3268;
    public static final int DEFAULT_COST = 0;
    public static final double PARTTIME_FEE_RATE = 0.8;

    /**
     *Check to see if credit enrolled is valid or not
     * @param creditEnrolled given by user
     * @return boolean, true is valid. Or it will be false
     */
    public boolean isValid(int creditEnrolled){
        if (creditEnrolled < MIN_CREDITS || creditEnrolled > MAX_CREDITS) {
            return false;
        }
        if (creditEnrolled > CREDIT_EXCEED_LIMIT && !isResident()) {
            return false;
        }
        return true;
    } //polymorphism

    /**
     * abstract method that is used in Resident, NonResident, International, TriState.
     * @param creditsEnrolled
     * @return boolean
     */
    public abstract double tuitionDue(int creditsEnrolled); //polymorphism
    public abstract boolean isResident(); //polymorphism

    /**
     * Creates student object with profile, major, and credit complete elements
     * @param profile given by the user
     * @param major given by the user
     * @param creditCompleted given by the user
     */
    public Student(Profile profile, Major major, int creditCompleted){
        this.profile = profile;
        this.major = major;
        this.creditCompleted = creditCompleted;
    }

    /**
     * Creates student object with profile only
     * @param profile given by the user
     */
    public Student(Profile profile) {
        this.profile = profile;
    }

    /**
     * Creates student object with profile and major only
     * @param profile given by the user
     * @param major given by the user
     */
    public Student(Profile profile, Major major) {
        this.profile = profile;
        this.major = major;
    }

    /**
     * Returns student object's profile.
     * @return profile type string
     */
    public Profile getProfile(){
        return profile;
    }

    /**
     * Returns student object's major.
     * @return major type string
     */
    public Major getMajor(){return major;}

    /**
     * Sets student object's major.
     * @param major the new major object
     */
    public void setMajor(Major major) {
        this.major = major;
    }


    /**
     * Returns student object's credit completed.
     * @return creditCompleted type int
     */
    public int getCreditCompleted(){return creditCompleted;}

    /**
     * Sets student object's credit completed.
     * @return creditCompleted type int
     */
    public void setCreditCompleted(int creditCompleted){ this.creditCompleted = creditCompleted; }

    /**
     * Returns student object's standing.
     * @return credits standing type string
     */
    public String getStanding() {
        if (this.creditCompleted < 30) {
            return "Freshman";
        } else if (creditCompleted >= 30 && this.creditCompleted < 60) {
            return "Sophomore";
        } else if (creditCompleted >= 60 && this.creditCompleted < 90) {
            return "Junior";
        } else {
            return "Senior";
        }
    }

    /**
     * Converts student information into a single string printout.
     */
    @Override
    public String toString() { return profile.toString() + " " + major.toString() + " credits completed: " +
            creditCompleted + " (" + getStanding() + ")"; }

    /**
     * Compares student objects.
     * Returns true if student objects are equal, returns false if they are different.
     */
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (!(obj instanceof Student))
            return false;
        Student tmp = (Student) obj;
        return getProfile().equals(tmp.getProfile());
    }

    /**
     * Compares student objects.
     * Returns true if student objects are equal, returns false if they are different.
     */
    @Override
    public int compareTo(Student o) {
        int compare = this.getProfile().getLname().compareTo(o.getProfile().getLname());
        if (compare == 0) {
            compare = this.getProfile().getFname().compareTo(o.getProfile().getFname());
            if (compare == 0) {
                compare = this.getProfile().getDob().compareTo(o.getProfile().getDob());
            }
        }
        return compare;
    }

}

