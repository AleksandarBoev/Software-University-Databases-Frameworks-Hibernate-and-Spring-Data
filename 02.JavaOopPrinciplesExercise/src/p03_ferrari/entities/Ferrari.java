package p03_ferrari.entities;

import p03_ferrari.contracts.Car;

public abstract class Ferrari implements Car {
    private String driverName;

    public Ferrari(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public String toString() {
        return String.format("%s/%s/%s/%s",
                this.getModel(), this.useBrakes(), this.drive(), this.getDriverName());
    }

    protected String getModel() {
        return this.getClass().getSimpleName().replace("Model", "");
    }

    private String getDriverName() {
        return this.driverName;
    }
}
