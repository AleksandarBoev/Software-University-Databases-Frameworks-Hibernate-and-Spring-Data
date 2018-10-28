package p04_telephony.entities.exceptions;

public class InvalidUrlException extends Exception {
    private static final String ERROR_MESSAGE = "Invalid URL!";

    @Override
    public String getMessage(){
        return ERROR_MESSAGE;
    }
}
