package softuni.utils.interfaces;

import java.util.List;

public interface ValidatorUtil<E> {
    boolean isValid(E element);

    List<String> getErrorMessages(E element);
}
