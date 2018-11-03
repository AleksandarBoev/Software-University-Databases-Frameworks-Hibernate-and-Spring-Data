package p04_add_minion.entities.minions;

import p04_add_minion.contracts.Minion;

public class MinionImpl implements Minion {
    private Integer id;
    private String name;
    private Integer age;
    private Integer townid;

    public MinionImpl(Integer id, String name, Integer age, Integer townid) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.townid = townid;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
