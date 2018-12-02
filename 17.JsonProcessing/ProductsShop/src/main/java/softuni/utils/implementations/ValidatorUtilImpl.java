package softuni.utils.implementations;

import softuni.utils.interfaces.ValidatorUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtilImpl<E> implements ValidatorUtil<E> {
    private Validator validator;

    public ValidatorUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    //If set of errors is empty, then the element is valid
    @Override
    public boolean isValid(E element) {
        return this.validator.validate(element).size() == 0;

    }

    //Returns set of errors (errors are generated from the validation annotations)
    @Override
    public Set<ConstraintViolation<E>> violations(E element) {
        return this.validator.validate(element);
    }
}
