package p03_get_minions_names.entities.minions;

import p03_get_minions_names.contracts.Minion;

public class MinionImpl implements Minion {
    private String name;
    private Integer age;

    public MinionImpl(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return this.name + " " + this.age;
    }
}
