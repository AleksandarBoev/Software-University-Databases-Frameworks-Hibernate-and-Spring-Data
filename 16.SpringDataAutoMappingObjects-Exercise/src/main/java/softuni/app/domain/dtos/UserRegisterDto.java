package softuni.app.domain.dtos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//used to register a user in the db
public class UserRegisterDto {
    private static final String INVALID_EMAIL_MESSAGE = "Invalid email, must contain @ sign and a period.";
    private static final String INVALID_PASSWORD_MESSAGE = "Password length must be at least 6 symbols and must contain at least 1 uppercase, 1 lowercase letter and 1 digit.";
    private static final String INVALID_CONFIRM_PASSWORD_MESSAGE = "Confirm password must match the provided password.";
    private static final String INVALID_FULL_NAME = "Full name must not be empty!";

    private static final String EMAIL_REGEX = "^[A-z0-9]+@[a-z]+\\.[a-z]+$"; //simplest kind of authentication for an email
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$"; //copy pasted from internet

    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserRegisterDto(String email, String password, String confirmPassword, String fullName) {
        this.setEmail(email);
        this.setPassword(password);
        this.setConfirmPassword(confirmPassword);
        this.setFullName(fullName);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        boolean emailIsCorrect = matcher.find();

        if (!emailIsCorrect)
            throw new IllegalArgumentException(INVALID_EMAIL_MESSAGE);

        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        boolean passwordIsCorrect = matcher.find();

        if (!passwordIsCorrect)
            throw new IllegalArgumentException(INVALID_PASSWORD_MESSAGE);

        this.password = password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        if (!confirmPassword.equals(this.password))
            throw new IllegalArgumentException(INVALID_CONFIRM_PASSWORD_MESSAGE);

        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        if (fullName.length() == 0)
            throw new IllegalArgumentException(INVALID_FULL_NAME);

        this.fullName = fullName;
    }
}
