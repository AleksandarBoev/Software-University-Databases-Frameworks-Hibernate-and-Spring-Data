package p07_mankind.entities.abstract_classes;

import p07_mankind.contracts.Human;
import p07_mankind.entities.exceptions.InvalidNameLengthException;
import p07_mankind.entities.exceptions.InvalidUpperCaseException;

public abstract class BaseHuman implements Human {
    private String firstName;
    private String lastName;

    protected BaseHuman(String firstName, String lastName) throws InvalidUpperCaseException, InvalidNameLengthException {
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    @Override
    public String toString() {
        return String.format("First Name: %s%nLast Name: %s", this.firstName, this.lastName);
    }

    private void setFirstName(String firstName) throws InvalidUpperCaseException, InvalidNameLengthException {
        if (!Character.isUpperCase(firstName.charAt(0))) {
            throw new InvalidUpperCaseException("firstName");
        } else if (firstName.length() < 4) {
            throw new InvalidNameLengthException(4, "firstName");
        } else {
            this.firstName = firstName;
        }
    }

    private void setLastName(String lastName) throws InvalidNameLengthException, InvalidUpperCaseException {
        if (!Character.isUpperCase(lastName.charAt(0))) {
            throw new InvalidUpperCaseException("lastName");
        } else if (lastName.length() < 3) {
            throw new InvalidNameLengthException(3, "lastName");
        } else {
            this.lastName = lastName;
        }
    }
}
