package com.example.project3;

/**
 * Collects and manages Student objects in roster array.
 * Perform functions called by TuitionManagerView.fxml file.
 * Functions include adding and removing Student objects from the array,
 * and printing the array roster in no specified order, by student major, and or by student standing
 * @author Sharukh Khan, Hamad Naveed
 */

public class Roster {
    private Student[] roster;
    private int size;
    private static final int NOT_FOUND = -1;

    /**
     * Roster object array created.
     */
    public Roster() {
        this.roster = new Student[4];
        this.size = 0;
    }

    /**
     * Returns number of size in roster array.
     * @return number of size.
     */
    public int getSize() {return size;}

    /**
     * Returns roster array.
     * @return roster.
     */
    public Student[] getRoster() { return roster; }

    /**
     * Returns roster array.
     * @param profile object
     * @return roster.
     */
    public Student get(Profile profile) {
        for (int i = 0; i < size; i++) {
            if (roster[i].getProfile().equals(profile)) {
                return roster[i];
            }
        }
        return null;
    }

    /**
     * Finds if a student already exists in array roster.
     * @param student object being searched for.
     * @return index of where the student object is in the array.
     */
    private int find(Student student) {
        for (int i = 0; i < size; i++) {
            if (roster[i].equals(student))
                return i;
        }
        return NOT_FOUND;
    }

    /**
     * Grows array length by 4 if array is full.
     */
    private void grow() {
        Student[] tmp = new Student[size + 4];
        for (int i = 0; i < size; i++) {
            tmp[i] = roster[i];
        }
        roster = tmp;
    }

    /**
     * Adds student object to roster array.
     * @param student object to be added.
     * @return false if student is not added, true if it is added.
     */
    public boolean add(Student student) {
        if (contains(student)) {
            return false;
        }
        if (size == roster.length)
            grow();
        roster[size++] = student;
        return true;
    }

    /**
     * Removes student from array if it exists in the array.
     * @param student Student object to be deleted
     * @return false if student is not in array, true if it exists and is removed.
     */
    public boolean remove(Student student) {
        int index = find(student);
        if (index == NOT_FOUND) {
            return false;
        }
        for (int i = index; i < size - 1; i++) {
            roster[i] = roster[i + 1];
        }
        size--;
        return true;
    }

    /**
     * Checks to see if there is any
     * @param student object to be deleted.
     * @return false if student is not in array, true if it exists and is removed.
     */
    public boolean contains(Student student) {
        return find(student) != NOT_FOUND;
    }

    /**
     * Prints all student in array, if array is not empty, sorted by last name, first name, DOB.
     */
    public String print() {
        StringBuilder print = new StringBuilder();
        if (size != 0) {
            print.append("*** Student roster sorted by last name, first name, DOB *** \n");
            for (Student student : roster)
                if (student != null)
                    print.append(student).append("\n");
            print.append("** end of roster **");
        } else {
            print.append("**** Student roster is empty! ****");
        }
        return print.toString();
    }

    /**
     * Prints all student in array, if array is not empty, in order of major.
     */
    public String printBySchoolMajor() {
        StringBuilder print = new StringBuilder();
        if (size == 0) {
            print.append("The roster is empty!\n");
        }
        Student[] sort = new Student[roster.length];
        int numStudents = 0;
        for (Major major : Major.values()) {
            for (Student student : roster) {
                if (student != null && student.getMajor() == major) {
                    sort[numStudents++] = student;
                }
            }
        }
        print.append("* Student roster sorted by school, major **\n");
        for (int i = 0; i < numStudents; i++) {
            print.append(sort[i]).append("\n");
        }
        print.append("* end of roster **");
        return print.toString() + "\n";
    }

    /**
     * Prints all student in array, if array is not empty, in order of standing.
     */
    public String printByStanding() {
        StringBuilder print = new StringBuilder();
        // First, sort the non-Sophomore students
        for (int i = 0; i < size - 1; i++) {
            int swapIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (roster[j] != null && roster[i] != null) {
                    if (!roster[j].getStanding().equals("Sophomore") && !roster[i].getStanding().equals("Sophomore") &&
                            compareStanding(roster[j].getStanding(), roster[swapIndex].getStanding()) < 0) {
                        swapIndex = j;
                    }
                }
            }
            Student tmpStudent = roster[swapIndex];
            roster[swapIndex] = roster[i];
            roster[i] = tmpStudent;
        }

        // Second, sort the Sophomore students and insert them into the sorted array
        int firstSophomoreIndex = -1;
        for (int i = 0; i < size; i++) {
            if (roster[i] != null && roster[i].getStanding().equals("Sophomore")) {
                if (firstSophomoreIndex == -1) {
                    firstSophomoreIndex = i;
                } else {
                    int insertIndex = firstSophomoreIndex;
                    while (insertIndex < i && compareStanding(roster[i].getStanding(), roster[insertIndex].getStanding()) > 0) {
                        insertIndex++;
                    }
                    Student tmpStudent = roster[i];
                    for (int j = i; j > insertIndex; j--) {
                        roster[j] = roster[j - 1];
                    }
                    roster[insertIndex] = tmpStudent;
                    firstSophomoreIndex++;
                }
            }
        }
        // Print the sorted roster
        if (size != 0) {
            print.append("* Student roster sorted by standing *\n");
            for (int i = 0; i < size; i++) {
                if (roster[i] != null) {
                    print.append(roster[i]).append("\n");
                }
            }
            print.append("* end of roster **");
        } else {
            print.append("The roster is empty!");
        }
        return print.toString() + "\n";
    }

    /**
     * compare Standing
     */
    private int compareStanding(String s1, String s2) {
        if (s1.equals("Senior")) {
            if (s2.equals("Senior")) {
                return 0;
            } else {
                return 1;
            }
        } else if (s1.equals("Junior")) {
            if (s2.equals("Senior")) {
                return -1;
            } else if (s2.equals("Junior")) {
                return 0;
            } else {
                return 1;
            }
        } else if (s1.equals("Sophomore")) {
            if (s2.equals("Freshman")) {
                return -1;
            } else if (s2.equals("Sophomore")) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (s2.equals("Freshman")) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
