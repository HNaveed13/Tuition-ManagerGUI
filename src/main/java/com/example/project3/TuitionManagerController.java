package com.example.project3;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;

/**
 * A class that handles input and output for the entire Tuition Manager GUI.
 * Takes in input through TextFields and Button groups to display the appropriate data on the output panel.
 * @author Sharukh Khan, Hamad Naveed
 *
 */
public class TuitionManagerController {
    private Roster roster = new Roster();
    private Enrollment enrollment = new Enrollment(4);
    private String majorModifyRoster = "";

    @FXML
    private TextField studentNameTextFieldFirstName;

    @FXML
    private TextField studentNameTextFieldLastName;

    @FXML
    private TextField creditsCompletedTextField;

    @FXML
    private DatePicker date;

    @FXML
    private RadioButton baitMajorModifyRoster;

    @FXML
    private RadioButton csMajorModifyRoster;

    @FXML
    private RadioButton eeMajorModifyRoster;

    @FXML
    private RadioButton itMajorModifyRoster;

    @FXML
    private RadioButton mathMajorModifyRoster;

    @FXML
    private ToggleGroup residency;

    @FXML
    private RadioButton residentResidency;

    @FXML
    private RadioButton nonresidentResidency;

    @FXML
    private RadioButton tristateResidency;

    @FXML
    private RadioButton nyResidency;

    @FXML
    private RadioButton ctResidency;

    @FXML
    private RadioButton internationalResidency;

    @FXML
    private CheckBox studyAbroad;

    @FXML
    private TextField enrollFirstName;

    @FXML
    private TextField enrollLastName;

    @FXML
    private DatePicker enrollDate;

    @FXML
    private TextField enrollCredits;

    @FXML
    private TextField scholarshipFirstName;

    @FXML
    private TextField scholarshipLastName;

    @FXML
    private DatePicker scholarshipDate;

    @FXML
    private TextField scholarshipAmount;

    @FXML
    private TextField schoolNameText;

    @FXML
    private TextArea outputTextArea;

    /**
     * Disables non-resident residency options (e.g. study abroad, tristate, etc)
     */
    @FXML
    private void residentSelected() {
        tristateResidency.setDisable(true);
        tristateResidency.setSelected(false);
        nyResidency.setDisable(true);
        nyResidency.setSelected(false);
        ctResidency.setDisable(true);
        ctResidency.setSelected(false);
        internationalResidency.setDisable(true);
        internationalResidency.setSelected(false);
        studyAbroad.setDisable(true);
        studyAbroad.setSelected(false);
    }

    /**
     * Disables resident residency options. Enables nonResident residency options
     */
    @FXML
    private void nonResidentSelected() {
        tristateResidency.setDisable(false);
        internationalResidency.setDisable(false);
        nyResidency.setDisable(false);
        ctResidency.setDisable(false);
        studyAbroad.setDisable(false);
    }

    /**
     * Enables states to be selected for tristate residents, and disables non-tristate residency options (e.g. study abroad).
     */
    @FXML
    private void tristateSelected() {
        internationalResidency.setSelected(false);
        studyAbroad.setDisable(true);
        studyAbroad.setSelected(false);
        nyResidency.setDisable(false);
        ctResidency.setDisable(false);
    }

    /**
     * Disables international residency options (e.g. study abroad)
     */
    @FXML
    private void nyTristateSelected() {
        internationalResidency.setSelected(false);
        studyAbroad.setDisable(true);
        studyAbroad.setSelected(false);
        ctResidency.setSelected(false);
    }

    /**
     * Disables international residency options (e.g. study abroad)
     */
    @FXML
    private void ctTristateSelected() {
        internationalResidency.setSelected(false);
        studyAbroad.setDisable(true);
        studyAbroad.setSelected(false);
        nyResidency.setSelected(false);
    }

    /**
     * Disables non-international residency options (e.g. tristate)
     */
    @FXML
    private void internationalSelected() {
        tristateResidency.setSelected(false);
        ctResidency.setDisable(true);
        ctResidency.setSelected(false);
        nyResidency.setDisable(true);
        nyResidency.setSelected(false);
        studyAbroad.setDisable(false);
    }

    /**
     * This method loads student objects into the roster from a txt file
     * It will read the first token and determine which subclass to use for the constructor
     * for example if the first token is R then it will use the Resident method to create a resident object
     */
    @FXML
    private void loadRosterFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Roster File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    String type = tokens[0];
                    String lastName = tokens[1];
                    String firstName = tokens[2];
                    Date dob = new Date(tokens[3]);
                    Major major = Major.valueOf(tokens[4].toUpperCase());
                    int credits = Integer.parseInt(tokens[5]);
                    Student student = null;
                    switch (type) {
                        case "R":
                            student = new Resident(new Profile(firstName, lastName, dob), major, credits);
                            break;
                        case "I":
                            boolean studyAbroad = Boolean.parseBoolean(tokens[6]);
                            student = new International(new Profile(firstName, lastName, dob), major, credits, studyAbroad);
                            break;
                        case "T":
                            String state = tokens[6];
                            student = new TriState(new Profile(firstName, lastName, dob), major, credits, state);
                            break;
                        case "N":
                            student = new NonResident(new Profile(firstName, lastName, dob), major, credits);
                            break;
                    }
                    if (student != null) {
                        roster.add(student);
                    }
                }
                outputTextArea.appendText("Roster loaded from file " + file.getName() + "\n");
            } catch (FileNotFoundException e) {
                outputTextArea.appendText("File " + file.getName() + " not found.\n");
            } catch (Exception e) {
                outputTextArea.appendText("Error loading roster from file " + file.getName() + ": " + e.getMessage() + "\n");
            }
        }
    }

    /**
     * Validates if the current user input can successfully create a student.
     * @return true if the input is valid, false if not.
     */
    private boolean checkValidAddStudentInput() {
        if (studentNameTextFieldFirstName.getText().isEmpty()) {
            outputTextArea.appendText("Enter a first name for the Student.\n");
            return false;
        } else if (studentNameTextFieldLastName.getText().isEmpty()) {
            outputTextArea.appendText("Enter a last name for the Student.\n");
            return false;
        } else if (date.getValue() == null) {
            outputTextArea.appendText("Enter a date of birth.\n");
            return false;
        } else if (majorModifyRoster.equals("")) {
            outputTextArea.appendText("Please select a major.\n");
            return false;
        } else if (residency.getSelectedToggle() == null) {
            outputTextArea.appendText("Please select a form of residency.\n");
            return false;
        } else if (creditsCompletedTextField.getText().isEmpty()) {
            outputTextArea.appendText("Enter credits completed.\n");
            return false;
        } else if (!(creditsCompletedTextField.getText().matches("-?\\d+"))) {
            outputTextArea.appendText("Please use only digits for credit completed.\n");
            return false;
        } else if (Integer.parseInt(String.valueOf(creditsCompletedTextField.getText())) < 0) {
            outputTextArea.appendText("Please use positive digits for credit completed.\n");
            return false;
        } else if (tristateResidency.isSelected() && !nyResidency.isSelected() && !ctResidency.isSelected()) {
            outputTextArea.appendText("Please select a state.\n");
            return false;
        } else if(!tristateResidency.isSelected() && (ctResidency.isSelected() || nyResidency.isSelected())){
            outputTextArea.appendText("Please select tristate.\n");
            return false;
        }else if(!internationalResidency.isSelected() && studyAbroad.isSelected()){
            outputTextArea.appendText("Please select international.\n");
            return false;
        }
        return true;
    }

    /**
     * Adds a student to the roster based on user input specifications.
     * The method prints out the status of the actionwhether the student was added to the roster,
     * or if the student is already inside the roster.
     */
    @FXML
    private void addStudentToRoster() {
        Student student = null;
        if (checkValidAddStudentInput()) {
            String fname = studentNameTextFieldFirstName.getText().trim();
            String lname = studentNameTextFieldLastName.getText().trim();
            DatePicker dobPicker = date;

            int year = dobPicker.getValue().getYear();
            int month = dobPicker.getValue().getMonthValue();
            int day = dobPicker.getValue().getDayOfMonth();

            Date dob = new Date(month + "/" + day + "/" + year);
            if (!dob.isValid(outputTextArea)) {
                return;
            }
            Profile profile = new Profile(lname, fname, dob);
            Major major = getMajorModifyRoster();

            if (residentResidency.isSelected()) {
                student = new Resident(profile, major,
                        Integer.parseInt(creditsCompletedTextField.getText()));

            } else if (nonresidentResidency.isSelected()) {
                if (internationalResidency.isSelected()) {
                    if (studyAbroad.isSelected()) {
                        student = new International(profile, major,
                                Integer.parseInt(creditsCompletedTextField.getText()), true);
                    } else {
                        student = new International(profile, major,
                                Integer.parseInt(creditsCompletedTextField.getText()), false);
                    }

                } else if (tristateResidency.isSelected()) {
                    if (nyResidency.isSelected()) {
                        student = new TriState(profile, major,
                                Integer.parseInt(creditsCompletedTextField.getText()), "NY");
                    } else {
                        student = new TriState(profile, major,
                                Integer.parseInt(creditsCompletedTextField.getText()), "CT");
                    }
                } else {
                    student = new NonResident(profile, major,
                            Integer.parseInt(creditsCompletedTextField.getText()));
                }
            }
            if (roster.add(student)) {
                outputTextArea.appendText(studentNameTextFieldFirstName.getText() + " " +
                        studentNameTextFieldLastName.getText() + " " + dob + " was added to roster.\n");
            } else {
                outputTextArea.appendText(studentNameTextFieldFirstName.getText() + " " +
                        studentNameTextFieldLastName.getText() + " " + dob + " is already in roster.\n");
            }
        }
    }

    /**
     * Validates if the current user input can successfully create a student.
     * @return true if the input is valid, false if not.
     */
    private boolean checkValidEnrollStudentInput() {
        if (enrollFirstName.getText().isEmpty()) {
            outputTextArea.appendText("Enter a first name for the Student.\n");
            return false;
        } else if (enrollLastName.getText().isEmpty()) {
            outputTextArea.appendText("Enter a last name for the Student.\n");
            return false;
        } else if(enrollDate.getValue() == null) {
            outputTextArea.appendText("Enter a date of birth.\n");
            return false;
        }else if(enrollCredits.getText().isEmpty()){
            outputTextArea.appendText("Enter credits enrolled.\n");
            return false;
        } else if (!(enrollCredits.getText().matches("-?\\d+")) ) {
            outputTextArea.appendText("Please use only digits for credit enrolled.\n");
            return false;
        } else if(Integer.parseInt(String.valueOf(enrollCredits.getText())) < 0){
            outputTextArea.appendText("Please use positive digits for credit enrolled.\n");
            return false;
        }
        return true;
    }

    /**
     * Enroll a student to the roster based on user input specifications.
     * The method prints out the status of the actionwhether the student was enrolled,
     * or if the student is already enrolled.
     */
    @FXML
    private void enrollStudent() {
        if(checkValidEnrollStudentInput()) {
            try {
                String fname = enrollFirstName.getText().trim();
                String lname = enrollLastName.getText().trim();
                DatePicker dobPicker = enrollDate;
                int credits = Integer.parseInt(enrollCredits.getText());

                int year = dobPicker.getValue().getYear();
                int month = dobPicker.getValue().getMonthValue();
                int day = dobPicker.getValue().getDayOfMonth();

                Date dob = new Date(month + "/" + day + "/" + year);
                if(!dob.isValid(outputTextArea)){
                    return;
                }
                Profile profile = new Profile(lname, fname, dob);

                Student filler = new Resident(profile);
                if (!roster.contains(filler)) {
                    outputTextArea.appendText("Cannot enroll: " + profile + " is not in the roster.\n");
                    return;
                }
                for (Student student : roster.getRoster()) {
                    if (student.getProfile().equals(profile)) {
                        if (student instanceof Resident) {
                            EnrollStudent studentToBeEnrolled = new EnrollStudent(profile, credits);

                            if ((Integer.parseInt(enrollCredits.getText()) < Student.MIN_CREDITS)) {
                                outputTextArea.appendText("(Resident) " + credits + ": invalid credit hours.\n");
                                return;
                            }
                            if ((Integer.parseInt(enrollCredits.getText()) > Student.MAX_CREDITS)) {
                                outputTextArea.appendText("(Resident) " + credits + ": invalid credit hours.\n");
                                return;
                            }

                            if (enrollment.contains(studentToBeEnrolled)) {
                                EnrollStudent existingStudent = enrollment.get(studentToBeEnrolled);
                                existingStudent.setCreditsEnrolled(credits);
                            } else {
                                enrollment.add(studentToBeEnrolled);
                            }
                            outputTextArea.appendText(studentToBeEnrolled.getProfile() + " enrolled " + credits + " credits\n");
                            return;
                        } else if (student instanceof International) {
                            EnrollStudent studentToBeEnrolled = new EnrollStudent(profile, credits);

                            if ((Integer.parseInt(enrollCredits.getText()) < 12 || Integer.parseInt(enrollCredits.getText()) > 24) && !(((International) student).isStudyAbroad())) {
                                outputTextArea.appendText("(International student) " + credits + ": invalid credit hours.\n");
                                return;
                            } else if (Integer.parseInt(enrollCredits.getText()) < 3 || Integer.parseInt(enrollCredits.getText()) > 12 && (((International) student).isStudyAbroad())) {
                                outputTextArea.appendText("(International student: study abroad) " + credits + ": invalid credit hours.\n");
                                return;
                            }
                            if (enrollment.contains(studentToBeEnrolled)) {
                                EnrollStudent existingStudent = enrollment.get(studentToBeEnrolled);
                                existingStudent.setCreditsEnrolled(credits);
                            } else {
                                enrollment.add(studentToBeEnrolled);
                            }
                            outputTextArea.appendText(studentToBeEnrolled.getProfile() + " enrolled " + credits + " credits\n");
                            return;
                        } else if (student instanceof NonResident) {
                            EnrollStudent studentToBeEnrolled = new EnrollStudent(profile, credits);

                            if (Integer.parseInt(enrollCredits.getText()) < 3 || Integer.parseInt(enrollCredits.getText()) > 24) {
                                outputTextArea.appendText("(Non-Resident) " + credits + ": invalid credit hours.\n");
                                return;
                            }
                            if (enrollment.contains(studentToBeEnrolled)) {
                                EnrollStudent existingStudent = enrollment.get(studentToBeEnrolled);
                                existingStudent.setCreditsEnrolled(credits);
                            } else {
                                enrollment.add(studentToBeEnrolled);
                            }
                            outputTextArea.appendText(studentToBeEnrolled.getProfile() + " enrolled " + credits + " credits\n");
                            return;
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                outputTextArea.appendText("Credits enrolled is not an integer.\n");
            } catch (Exception e) {
                outputTextArea.appendText("Missing data in line command.\n");
            }
        }
    }

    /**
     * Drops student from enrollment list
     * checks if student is enrolled
     * prompts message if dropped
     */
    @FXML
    private void dropStudent(){
        String fname = enrollFirstName.getText().trim();
        String lname = enrollLastName.getText().trim();
        DatePicker dobPicker = enrollDate;

        int year = dobPicker.getValue().getYear();
        int month = dobPicker.getValue().getMonthValue();
        int day = dobPicker.getValue().getDayOfMonth();

        Date dob = new Date(month + "/" + day + "/" + year);
        Profile profile = new Profile(lname, fname, dob);
        EnrollStudent studentToBeDropped = new EnrollStudent(profile);

        if (!enrollment.contains(studentToBeDropped)){
            outputTextArea.appendText(studentToBeDropped.getProfile() + " is not enrolled\n");
        }
        else if(enrollment.contains(studentToBeDropped)){
            enrollment.remove(studentToBeDropped);
            outputTextArea.appendText(studentToBeDropped.getProfile() + " dropped.\n");
        }
    }

    /**
     * Deletes a student from the roster.
     * If the student exists in the roster, it is deleted and the appropraite statemenet
     * is printed. If the student is not in the array, a message outlining that the student
     * is not in the roster is printed.
     */
    @FXML
    private void deleteStudentFromRoster() {
        Student student = null;
        if (checkValidAddStudentInput()) {
            String fname = studentNameTextFieldFirstName.getText().trim();
            String lname = studentNameTextFieldLastName.getText().trim();
            DatePicker dobPicker = date;

            int year = dobPicker.getValue().getYear();
            int month = dobPicker.getValue().getMonthValue();
            int day = dobPicker.getValue().getDayOfMonth();

            Date dob = new Date(month + "/" + day + "/" + year);
            Profile profile = new Profile(lname, fname, dob);
            Major major = getMajorModifyRoster();

            if (residentResidency.isSelected()) {
                student = new Resident(profile, major,
                        Integer.parseInt(creditsCompletedTextField.getText()));

            } else if (nonresidentResidency.isSelected()) {
                if (internationalResidency.isSelected()) {
                    if (studyAbroad.isSelected()) {
                        student = new International(profile, major,
                                Integer.parseInt(creditsCompletedTextField.getText()), true);
                    } else {
                        student = new International(profile, major,
                                Integer.parseInt(creditsCompletedTextField.getText()), false);
                    }

                } else if (tristateResidency.isSelected()) {
                    if (nyResidency.isSelected()) {
                        student = new TriState(profile, major,
                                Integer.parseInt(creditsCompletedTextField.getText()), "NY");
                    } else {
                        student = new TriState(profile, major,
                                Integer.parseInt(creditsCompletedTextField.getText()), "CT");
                    }
                } else {
                    student = new NonResident(profile, major,
                            Integer.parseInt(creditsCompletedTextField.getText()));
                }
            }
            if (roster.remove(student)) {
                outputTextArea.appendText(studentNameTextFieldFirstName.getText() + " " +
                        studentNameTextFieldLastName.getText() + " " + dob + " removed from the roster.\n");
            } else {
                outputTextArea.appendText(studentNameTextFieldFirstName.getText() + " " +
                        studentNameTextFieldLastName.getText() + " " + dob + " is not in the roster.\n");
            }
        }
    }

    /**
     * Get the student's major in the roster page on selection of a radio button.
     */
    @FXML
    private Major getMajorModifyRoster() {
        if (csMajorModifyRoster.isSelected()) {
            return Major.CS;
        } else if (mathMajorModifyRoster.isSelected()) {
            return Major.MATH;
        } else if (itMajorModifyRoster.isSelected()) {
            return Major.ITI;
        } else if (baitMajorModifyRoster.isSelected()) {
            return Major.BAIT;
        } else if (eeMajorModifyRoster.isSelected()) {
            return Major.EE;
        } else {
            outputTextArea.appendText("Please select a major.\n");
            return null;
        }
    }

    /**
     * Sets the student's major in the roster page on selection of a radio button.
     */
    @FXML
    private void setMajorModifyRoster() {
        if (csMajorModifyRoster.isSelected()) {
            majorModifyRoster = "CS";
        } else if (mathMajorModifyRoster.isSelected()) {
            majorModifyRoster = "MATH";
        } else if (itMajorModifyRoster.isSelected()) {
            majorModifyRoster = "ITI";
        } else if (baitMajorModifyRoster.isSelected()) {
            majorModifyRoster = "BAIT";
        } else if (eeMajorModifyRoster.isSelected()) {
            majorModifyRoster = "EE";
        }
    }

    /**
     * Removes student from the Roster class array.
     * If student exist in the array it's major is changed
     * If student is not in the array the appropriate statement is printed.
     * If student is not in the array the appropriate statement is printed.
     */
    @FXML
    private void changeMajor() {
        try {
            if (checkValidAddStudentInput()) {
                String fname = studentNameTextFieldFirstName.getText().trim();
                String lname = studentNameTextFieldLastName.getText().trim();
                DatePicker dobPicker = date;

                int year, month, day;
                if (dobPicker != null && dobPicker.getValue() != null) {
                    year = dobPicker.getValue().getYear();
                    month = dobPicker.getValue().getMonthValue();
                    day = dobPicker.getValue().getDayOfMonth();
                } else {
                    outputTextArea.appendText("Please select a valid date of birth.\n");
                    return;
                }
                Date dob = new Date(month + "/" + day + "/" + year);
                Profile profile = new Profile(lname, fname, dob);
                Major major = getMajorModifyRoster();

                // Find the student in the roster list
                Student student = null;
                for (Student s : roster.getRoster()) {
                    if(s == null){
                        break;
                    }
                    if (s.getProfile().equals(profile)) {
                        student = s;
                        break;
                    }
                }

                if (student != null && roster.contains(student)) {
                    // Update the major of the student object
                    student.setMajor(major);
                    outputTextArea.appendText(studentNameTextFieldFirstName.getText() + " " +
                            studentNameTextFieldLastName.getText() + " " + dob + " major has been changed!.\n");
                } else {
                    outputTextArea.appendText(studentNameTextFieldFirstName.getText() + " " +
                            studentNameTextFieldLastName.getText() + " " + dob + " is not in the roster.\n");
                }
            }
        } catch (NullPointerException e) {
            outputTextArea.appendText("An error occurred while changing the major: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    /**
     * Validates if the current user input can successfully create a student.
     * @return true if the input is valid, false if not.
     */
    @FXML
    private boolean checkValidScholarStudentInput() {
        if (scholarshipFirstName.getText().isEmpty()) {
            outputTextArea.appendText("Enter a first name for the Student.\n");
            return false;
        } else if (scholarshipLastName.getText().isEmpty()) {
            outputTextArea.appendText("Enter a last name for the Student.\n");
            return false;
        } else if(scholarshipDate.getValue() == null) {
            outputTextArea.appendText("Enter a date of birth.\n");
            return false;
        } else if (scholarshipAmount.getText().isEmpty()) {
            outputTextArea.appendText("Please enter a scholarship amount.\n");
            return false;
        } else if(!(scholarshipAmount.getText().matches("-?\\d+"))){
            outputTextArea.appendText("Please enter digits for scholarship amount.\n");
            return false;
        }
        return true;
    }

    /**
     * This will print the enrolled student's tuition based on what student they are
     * checks if student is a resident and if amount of earning is valid
     * student form the roster and enrollment to compare
     */
    @FXML
    private void awardScholarship() {
        if(checkValidScholarStudentInput()) {
            try {
                // Parse the input tokens.
                String fname = scholarshipFirstName.getText().trim();
                String lname = scholarshipLastName.getText().trim();
                DatePicker dobPicker = scholarshipDate;

                int year = dobPicker.getValue().getYear();
                int month = dobPicker.getValue().getMonthValue();
                int day = dobPicker.getValue().getDayOfMonth();

                Date dob = new Date(month + "/" + day + "/" + year);
                int amount = Integer.parseInt(scholarshipAmount.getText());

                // Create a profile object from the parsed input.
                Profile profile = new Profile(lname, fname, dob);
                // Find the student in the roster.
                Student student = roster.get(profile);
                if (student == null) {
                    outputTextArea.appendText(profile + " is not in the roster.\n");
                    return;
                }
                // Check if the student is eligible for a scholarship.
                if (!(student instanceof Resident)) {
                    outputTextArea.appendText(student.getProfile() + " is not a resident student and is not eligible for a scholarship.\n");
                    return;
                }
                // Award the scholarship to the student.
                Resident resident = (Resident) student;
                EnrollStudent enrollStudent = enrollment.get(profile);
                if (amount <= 0) {
                    outputTextArea.appendText("Invalid scholarship amount: " + amount + "\n");
                    return;
                } else if (amount > Resident.MAX_FINANCIAL_AID_AMOUNT) {
                    outputTextArea.appendText("Invalid scholarship amount: " + amount + "\n");
                    return;
                }
                if (enrollStudent.getCreditsEnrolled() < 12) {
                    outputTextArea.appendText("Part-time students are not eligible for scholarship\n");
                    return;
                }

                resident.setScholarship(amount);

                double tuition = resident.tuitionDue(enrollStudent.getCreditsEnrolled());
                outputTextArea.appendText(resident.getProfile() + " :scholarship amount updated.\n");
            } catch (NoSuchElementException e) {
                outputTextArea.appendText("Missing data in line command.\n");
            } catch (IllegalArgumentException e) {
                outputTextArea.appendText("Invalid input: " + e.getMessage());
            } catch (Exception e) {
                outputTextArea.appendText("No enrollment found");
            }
        }
    }

    /**
     * This will print the enrolled student's tuition based on what student they are
     * checks if student is enrolled
     * this will compare the student from the roster to get their reference
     */
    @FXML
    public void printTuition() {
        double totalTuition = 0.0;
        DecimalFormat df = new DecimalFormat("#,##0.00");
        if(enrollment.getSize() != 0) {
            outputTextArea.appendText("** Tuition due ** \n");
            for (EnrollStudent student : enrollment.getEnrollment()) {
                for (Student student1 : roster.getRoster()) {
                    if (student1 != null && student != null && student.getProfile().equals(student1.getProfile())) {
                        double tuition = student1.tuitionDue(student.getCreditsEnrolled());
                        student1.tuitionDue(student.getCreditsEnrolled());
                        outputTextArea.appendText(student.getProfile() + " enrolled " + student.getCreditsEnrolled() + " credits: " + "tuition due: $" + df.format(tuition) + "\n");
                    }
                }
            }
            outputTextArea.appendText("* end of tuition due \n");
        }else{
            outputTextArea.appendText("** Tuition Empty ***\n");
        }
    }

    /**
     * This will just print the students whose credits are over 120
     * if they are then they will be printed
     */
    @FXML
    private void semesterEnd() {
        boolean check = false;
        outputTextArea.appendText("** list of students eligible for graduation **\n");
        for(EnrollStudent student : enrollment.getEnrollment()){
            for(Student student1 : roster.getRoster()){
                if(student != null && student1 != null &&
                        student.getProfile().equals(student1.getProfile()) &&
                        (student1.getCreditCompleted() + student.getCreditsEnrolled() > 119)){
                    check = true;
                    int credits = student1.getCreditCompleted() + student.getCreditsEnrolled();
                    outputTextArea.appendText(student1 + "- (Credits Completed after semester: "+ credits +")\n");
                    break;
                }
            }
        }
        if(!check){
            outputTextArea.appendText("LIST IS EMPTY!\n");
        }
        outputTextArea.appendText("** end of list **\n");
    }

    /**
     * Clears the text area in outputTextArea
     */
    public void clearTextArea() {
        outputTextArea.setText("");
    }

    /**
     * This class will list by school
     * If the input for school is invalid or not provided it will throw an error
     * will only print students from school provided
     */
    @FXML
    private void listBySchool() {
        if (schoolNameText.getText().isEmpty()) {
            outputTextArea.appendText("No school name provided.\n");
            return;
        }
        String schoolName = schoolNameText.getText().trim();
        schoolName = schoolName.toUpperCase();

        if(schoolName.equals("SAS") || schoolName.equals("SC&I")
                || schoolName.equals("RBS") || schoolName.equals("SOE")) {
            outputTextArea.appendText("* Students in " + schoolName + " *\n");
            for (Student student : roster.getRoster()) {
                if (student != null && student.getMajor().getSchoolName().equals(schoolName)) {
                    outputTextArea.appendText(String.valueOf(student) + "\n");
                }
            }
        } else {
            outputTextArea.appendText("School doesn't exist: " + schoolName + "\n");
        }
        outputTextArea.appendText("* end of list **\n");
    }

    /**
     * Prints out the entire roster.
     */
    @FXML
    private void printRoster() {
        outputTextArea.appendText(roster.print() + "\n");
    }

    /**
     * Prints out the entire enrollment.
     */
    @FXML
    private void printEnrollment() {
        outputTextArea.appendText(enrollment.print() + "\n");
    }

    /**
     * Prints out roster by standing.
     */
    @FXML
    private void printStanding() {
        outputTextArea.appendText(roster.printByStanding() + "\n");
    }

    /**
     * Prints out roster by major.
     */
    @FXML
    private void printMajor() {
        outputTextArea.appendText(roster.printBySchoolMajor() + "\n");
    }
}