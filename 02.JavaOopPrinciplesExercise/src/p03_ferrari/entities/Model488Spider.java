package p03_ferrari.entities;

public class Model488Spider extends Ferrari {
    public Model488Spider(String driverName) {
        super(driverName);
    }

    @Override
    public String useBrakes() {
        return "Brakes!";
    }

    @Override
    public String drive() {
        return "Zadu6avam sA!";
    }

    @Override
    protected String getModel() {
        return "488-Spider";
    }
}
