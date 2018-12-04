package softuni.utils.implementations;

import softuni.utils.interfaces.ValidatorUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidatorUtilImpl<E> implements ValidatorUtil<E> {
    private Validator validator;

    public ValidatorUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean isValid(E element) {
        return this.validator.validate(element).isEmpty();
    }

    @Override
    public List<String> getErrorMessages(E element) {
        Set<ConstraintViolation<E>> errorSet = this.validator.validate(element);

        return errorSet.stream().map(e -> e.getMessage()).collect(Collectors.toList());
    }
}
