package p04_telephony.entities.classes;

import p04_telephony.contracts.Browseable;
import p04_telephony.contracts.Callable;
import p04_telephony.contracts.Smartphone;
import p04_telephony.entities.exceptions.InvalidNumberException;
import p04_telephony.entities.exceptions.InvalidUrlException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmartphoneImpl implements Smartphone {
    private static final String VALID_NUMBER_REGEX = "^[0-9]+$";
    private static final String VALID_URL_REGEX = "^[^0-9]+$";

    @Override
    public String call(String... numbers) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numbers.length; i++) {
            try{
                this.validateNumber(numbers[i]);
                sb.append("Calling... ").append(numbers[i]).append(System.lineSeparator());
            } catch (InvalidNumberException ine){
                sb.append(ine.getMessage()).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String browse(String... urls) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < urls.length; i++) {
            try{
                this.validateUrl(urls[i]);
                sb.append("Browsing: ").append(urls[i]).append("!").append(System.lineSeparator());
            } catch (InvalidUrlException iue){
                sb.append(iue.getMessage()).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    private void validateNumber(String number) throws InvalidNumberException {
        Pattern pattern = Pattern.compile(VALID_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(number);

        if (!matcher.matches()) {
            throw new InvalidNumberException();
        }
    }

    private void validateUrl(String url) throws InvalidUrlException {
        Pattern pattern = Pattern.compile(VALID_URL_REGEX);
        Matcher matcher = pattern.matcher(url);

        if (!matcher.matches()) {
            throw new InvalidUrlException();
        }
    }
}
