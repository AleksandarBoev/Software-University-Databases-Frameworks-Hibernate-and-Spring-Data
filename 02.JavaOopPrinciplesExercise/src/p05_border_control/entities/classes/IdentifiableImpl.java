package p05_border_control.entities.classes;

import p05_border_control.contracts.Identifiable;

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
