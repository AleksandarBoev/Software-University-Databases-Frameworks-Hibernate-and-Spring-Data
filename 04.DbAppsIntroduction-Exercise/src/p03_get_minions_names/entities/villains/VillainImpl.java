package p03_get_minions_names.entities.villains;

import p03_get_minions_names.contracts.Minion;
import p03_get_minions_names.contracts.Villain;

import java.util.ArrayList;
import java.util.List;

public class VillainImpl implements Villain {
    private String name;
    private List<Minion> minions;

    public VillainImpl(String name) {
        this.name = name;
        this.minions = new ArrayList<>();
    }

    @Override
    public void addMinion(Minion minion) {
        this.minions.add(minion);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Villain: ").append(this.name).append(System.lineSeparator());

        if (this.minions.isEmpty()) {
            sb.append("<no minions>");
            return sb.toString();
        }

        int counter = 0;
        for (Minion minion : this.minions) {
            sb.append(++counter)
                    .append(". ")
                    .append(minion.toString())
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
