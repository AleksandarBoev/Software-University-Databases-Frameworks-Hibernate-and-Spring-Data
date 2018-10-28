package p05_border_control.entities.classes;

import p05_border_control.contracts.Robot;

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
