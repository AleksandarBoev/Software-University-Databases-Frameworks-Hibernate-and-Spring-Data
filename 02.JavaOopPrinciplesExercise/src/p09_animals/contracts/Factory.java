package p09_animals.contracts;

public interface Factory<T> {
    T create(String[] arguments);
}
