package p07_mankind.entities.exceptions;

import p07_mankind.constants.ExceptionConstants;

public class ValueMismatchException extends HumanException {
    private String argumentName;

    public ValueMismatchException(String argumentName) {
        this.argumentName = argumentName;
    }

    @Override
    public String getMessage() {
        return String.format(ExceptionConstants.EXPECTED_VALUE_MISMATCH_EXCEPTION_MESSAGE, this.argumentName);
    }
}
