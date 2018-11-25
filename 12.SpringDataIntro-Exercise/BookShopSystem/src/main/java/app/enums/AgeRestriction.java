package app.enums;

public enum AgeRestriction {
    MINOR, TEEN, ADULT;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
