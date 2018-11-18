package p05_bills_payment_system.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ba")
public class BankAccount extends BillingDetail {
    private String bankName;
    private String swiftCode;

    public BankAccount() {
    }

    public BankAccount(String number, User owner, String bankName, String swiftCode) {
        super(number, owner);
        this.setBankName(bankName);
        this.setSwiftCode(swiftCode);
    }

    @Column(name = "bank_name")
    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "SWIFT_code")
    public String getSwiftCode() {
        return this.swiftCode;
    }

    /*
    A SWIFT code may be of eight to eleven digits and has the following components:

    - The first four characters are used as the bank codes. Only letters can be used.
    - The next two characters, only letters, are to describe or give the country code.
    - The next two characters can be a mix of both numbers and letters. These are used for location-based codes.
    - The last three characters of the code can be digits and letters, and are optional.
    These last three characters in a SWIFT code are used to give details about the branch code.

    A good example of a SWIFT code would be the CommBank’s code- CTBAAU2S
 */
    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    @Override
    public String toString() {
        return super.toString() + " " + "bank account {" +
                "bankName='" + this.bankName + '\'' +
                ", swiftCode='" + this.swiftCode + '\'' +
                '}';
    }
}
