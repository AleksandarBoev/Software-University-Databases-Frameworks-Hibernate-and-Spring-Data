package app.enums;

public enum EditionType {
    NORMAL, PROMO, GOLD;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
