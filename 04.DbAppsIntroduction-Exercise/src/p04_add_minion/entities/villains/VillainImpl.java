package p04_add_minion.entities.villains;

import p04_add_minion.contracts.Villain;

public class VillainImpl implements Villain {
    private Integer id;
    private String name;

    public VillainImpl(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
