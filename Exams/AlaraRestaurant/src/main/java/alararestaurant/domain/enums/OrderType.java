package alararestaurant.domain.enums;

public enum OrderType {
    //•	type – OrderType enumeration with possible values: “ForHere, ToGo (default: ForHere)” (required)
    FORHERE, TOGO;

    @Override //stupid way of doing it
    public String toString() {
        if (this.name().length() == 7)
            return "ForHere";

        return "ToGo";
    }
}
