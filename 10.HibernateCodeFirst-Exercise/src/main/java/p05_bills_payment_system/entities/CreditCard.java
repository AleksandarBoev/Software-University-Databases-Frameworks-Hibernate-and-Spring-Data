package p05_bills_payment_system.entities;

import p05_bills_payment_system.enums.CardType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@DiscriminatorValue(value = "cc")
public class CreditCard extends BillingDetail{
    private CardType cardType;
    private int expirationMonth;
    private int expirationYear;

    public CreditCard() {

    }

    public CreditCard(String number, User owner, CardType cardType, int expirationMonth, int expirationYear) {
        super(number, owner);
        this.setCardType(cardType);
        this.setExpirationMonth(expirationMonth);
        this.setExpirationYear(expirationYear);
    }

    @Column(name = "card_type")
    public CardType getCardType() {
        return this.cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Column(name = "expitation_month")
    public int getExpirationMonth() {
        return this.expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        if (expirationMonth < 1 || expirationMonth > 12)
            throw new IllegalArgumentException("What kind of month is that?");

        this.expirationMonth = expirationMonth;
    }

    @Column(name = "expiration_year")
    public int getExpirationYear() {
        return this.expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        if (expirationYear < 1960 || expirationYear > this.maxAllowedYear())
            throw new IllegalArgumentException("Invalid year");

        this.expirationYear = expirationYear;
    }

    private int maxAllowedYear() {
        return LocalDate.now().getYear() + 20;
    }

    @Override
    public String toString() {
        return super.toString() + " " +
        "credit card { " +
                "cardType = " + this.cardType.toString() +
                ", expirationMonth = " + this.expirationMonth +
                ", expirationYear = " + this.expirationYear +
                '}';
    }
}
