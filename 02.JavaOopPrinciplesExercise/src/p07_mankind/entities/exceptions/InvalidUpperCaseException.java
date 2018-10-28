package p07_mankind.entities.exceptions;

import p07_mankind.constants.ExceptionConstants;

public class InvalidUpperCaseException extends HumanException {
    private String nameType;

    public InvalidUpperCaseException(String nameType) {
        this.nameType = nameType;
    }

    public String getMessage() {
        return String.format(ExceptionConstants.NAME_UPPER_CASE_LETTER_EXCEPTION_MESSAGE, this.nameType);
    }
}
