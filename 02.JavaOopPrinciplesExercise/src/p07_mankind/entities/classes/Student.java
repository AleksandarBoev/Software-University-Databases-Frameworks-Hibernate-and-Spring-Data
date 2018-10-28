package p07_mankind.entities.classes;

import p07_mankind.entities.abstract_classes.BaseHuman;
import p07_mankind.entities.exceptions.InvalidFacultyNumberException;
import p07_mankind.entities.exceptions.InvalidNameLengthException;
import p07_mankind.entities.exceptions.InvalidUpperCaseException;

public class Student extends BaseHuman {
    private String facultyNumber;

    public Student(String firstName, String lastName, String facultyNumber)
            throws InvalidUpperCaseException, InvalidNameLengthException, InvalidFacultyNumberException {
        super(firstName, lastName);
        this.setFacultyNumber(facultyNumber);
    }

    private void setFacultyNumber(String facultyNumber) throws InvalidFacultyNumberException {
        if (facultyNumber.length() < 5 || facultyNumber.length() > 10) {
            throw new InvalidFacultyNumberException();
        } else {
            this.facultyNumber = facultyNumber;
        }
    }

    @Override
    public String toString() {
        return String.format(super.toString() + "%nFaculty number: %s", this.facultyNumber);
    }
}
