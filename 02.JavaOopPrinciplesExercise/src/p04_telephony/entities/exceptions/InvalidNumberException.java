package p04_telephony.entities.exceptions;

public class InvalidNumberException extends Exception {
    private static final String ERROR_MESSAGE = "Invalid number!";

    @Override
    public String getMessage() {
        return this.ERROR_MESSAGE;
    }
}
