package p07_mankind.entities.exceptions;

import p07_mankind.constants.ExceptionConstants;

public class InvalidNameLengthException extends HumanException {
    private int expectedLength;
    private String nameType;

    public InvalidNameLengthException(int expectedLength, String nameType) {
        this.expectedLength = expectedLength;
        this.nameType = nameType;
    }

    @Override
    public String getMessage() {
        return String.format(ExceptionConstants.NAME_LENGTH_EXCEPTION_MESSAGE, this.expectedLength, this.nameType);
    }
}
