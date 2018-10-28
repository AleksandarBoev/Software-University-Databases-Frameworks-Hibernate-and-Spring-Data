package p05_border_control.entities.classes;

import p05_border_control.contracts.Citizen;

public class CitizenImpl extends IdentifiableImpl implements Citizen {
    private String name;
    private Integer age;

    public CitizenImpl(String name, Integer age, String id) {
        super(id);
        this.name = name;
        this.age = age;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getAge() {
        return this.age;
    }
}
