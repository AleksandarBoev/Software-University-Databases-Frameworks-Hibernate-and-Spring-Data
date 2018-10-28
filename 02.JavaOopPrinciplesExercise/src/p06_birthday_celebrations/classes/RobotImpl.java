package p06_birthday_celebrations.classes;

import p06_birthday_celebrations.contracts.Robot;

public class RobotImpl extends IdentifiableImpl implements Robot {
    private String model;

    public RobotImpl(String model, String id) {
        super(id);
        this.model = model;
    }

    @Override
    public String getModel() {
        return this.model;
    }
}
