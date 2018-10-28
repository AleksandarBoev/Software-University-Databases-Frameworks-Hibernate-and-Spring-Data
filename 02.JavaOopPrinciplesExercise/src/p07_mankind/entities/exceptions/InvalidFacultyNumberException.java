package p07_mankind.entities.exceptions;

import p07_mankind.constants.ExceptionConstants;

public class InvalidFacultyNumberException extends HumanException {
    @Override
    public String getMessage() {
        return ExceptionConstants.INVALID_FACULTY_NUMBER_EXCEPTION_MESSAGE;
    }
}
