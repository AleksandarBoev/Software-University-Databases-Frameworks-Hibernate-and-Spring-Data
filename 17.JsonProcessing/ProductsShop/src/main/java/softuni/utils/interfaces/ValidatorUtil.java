package softuni.utils.interfaces;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidatorUtil<E> {
    boolean isValid(E element);

    public Set<ConstraintViolation<E>> violations(E element);
}
