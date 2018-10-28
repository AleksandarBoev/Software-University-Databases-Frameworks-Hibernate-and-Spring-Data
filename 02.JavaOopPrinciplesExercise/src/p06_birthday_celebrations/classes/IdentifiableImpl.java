package p06_birthday_celebrations.classes;

import p06_birthday_celebrations.contracts.Identifiable;

public class IdentifiableImpl implements Identifiable {
    private String id;

    public IdentifiableImpl(String id) {
        this.id = id;
    }


    @Override
    public String getId() {
        return this.id;
    }
}
