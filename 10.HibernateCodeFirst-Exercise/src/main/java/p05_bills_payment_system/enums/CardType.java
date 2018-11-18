package p05_bills_payment_system.enums;

public enum CardType {
    BRONZE, SILVER, GOLD;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
