package com.example.project3;

/**
 * @author Sharukh Khan, Hamad Naveed
 */

public class Enrollment {
    private EnrollStudent[] enrollStudents;
    private int size;

    /**
     * Enrollment object array created.
     */
    public Enrollment(int capacity) {
        enrollStudents = new EnrollStudent[capacity];
        size = 0;
    }

    /**
     * Adds enrollStudent object to roster array.
     * @param enrollStudent object to be added.
     */
    public void add(EnrollStudent enrollStudent) {
        if (size == enrollStudents.length) {
            grow();
        }
        enrollStudents[size] = enrollStudent;
        size++;
    }

    /**
     * Returns EnrollStudent array.
     * @param student object
     * @return null.
     */
    public EnrollStudent get(EnrollStudent student) {
        for (int i = 0; i < size; i++) {
            if (enrollStudents[i].getProfile().equals(student.getProfile())) {
                return enrollStudents[i];
            }
        }
        return null;
    }

    /**
     * Grows array length
     */
    private void grow() {
        int newCapacity = enrollStudents.length * 2;
        EnrollStudent[] newEnrollStudents = new EnrollStudent[newCapacity];
        for (int i = 0; i < size; i++) {
            newEnrollStudents[i] = enrollStudents[i];
        }
        enrollStudents = newEnrollStudents;
    }

    /**
     * Remove enrollStudent from the list
     * @param  enrollStudent
     */
    public void remove(EnrollStudent enrollStudent) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (enrollStudents[i].equals(enrollStudent)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return;
        }
        enrollStudents[index] = enrollStudents[size - 1];
        enrollStudents[size - 1] = null;
        size--;
    }

    /**
     * Check to see is enrollStudent is in the list
     * @param  enrollStudent
     */
    public boolean contains(EnrollStudent enrollStudent) {
        for (int i = 0; i < size; i++) {
            if (enrollStudents[i].equals(enrollStudent)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Print out enrollment list
     */
    public String print() {
        StringBuilder print = new StringBuilder();
        if (size != 0) {
            print.append("** Enrollment **\n");
            for (EnrollStudent student : enrollStudents)
                if (student != null)
                    print.append(student).append("\n");
            print.append("* end of enrollment *");
        } else {
            print.append("Enrollment is empty!");
        }
        return print.toString();
    }

    /**
     * return the enrollemnt list
     */
    public EnrollStudent[] getEnrollment(){ return enrollStudents; }

    public EnrollStudent get(Profile profile) {
        for (EnrollStudent enrollment : enrollStudents) {
            if (enrollment.getProfile().equals(profile)) {
                return enrollment;
            }
        }
        return null;
    }

    /**
     * return the size of enrollment list
     */
    public int getSize(){
        return size;
    }
}