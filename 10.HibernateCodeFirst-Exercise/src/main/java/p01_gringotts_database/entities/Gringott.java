package p01_gringotts_database.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

//@Entity
//@Table(name = "gringotts")
public class Gringott {
    private long id; //primitive data type, because the database is generating it and it is quicker
    private String firstName;
    private String lastName;
    private String notes;
    private Integer age; //wrapper data type for easier debugging
    private String magicWandCreator;
    private Integer magicWandSize;
    private String depositGroup;
    private Timestamp depositStartDate;
    private BigDecimal depositAmount;
    private BigDecimal depositInterest;
    private BigDecimal depositCharge;
    private Timestamp depositExpirationDate;
    private Boolean isDepositExpired;

    public Gringott() {
    }

    public Gringott(String lastName, Integer age) { //with these two, because they should NOT be null
        this.setLastName(lastName);
        this.setAge(age);
    }

    public Gringott(String firstName, String lastName, String notes, int age, String magicWandCreator, int magicWandSize,
                    String depositGroup, Timestamp depositStartDate, BigDecimal depositAmount, BigDecimal depositInterest,
                    BigDecimal depositCharge, Timestamp depositExpirationDate, Boolean isDepositExpired) {
        this(lastName, age);
        this.setFirstName(lastName);
        this.setNotes(notes);
        this.setMagicWandCreator(magicWandCreator);
        this.setMagicWandSize(magicWandSize);
        this.setDepositGroup(depositGroup);
        this.setDepositStartDate(depositStartDate);
        this.setDepositAmount(depositAmount);
        this.setDepositInterest(depositInterest);
        this.setDepositCharge(depositCharge);
        this.setDepositExpirationDate(depositExpirationDate);
        this.setDepositExpired(isDepositExpired);
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "first_name", length = 50)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    @Column(name = "last_name", length = 60, nullable = false)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    @Column(name = "notes", columnDefinition = "TEXT", length = 1000) //by default Strings are made into VARCHAR, which max length 255
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "age", nullable = false)
    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        if (age < 0)
            throw new IllegalArgumentException("Age should be a non-negative number!");

        this.age = age;
    }

    @Column(name = "magic_wand_creator", length = 100)
    public String getMagicWandCreator() {
        return this.magicWandCreator;
    }

    public void setMagicWandCreator(String magicWandCreator) {
        this.magicWandCreator = magicWandCreator;
    }

    @Column(name = "magic_wand_size")
    public Integer getMagicWandSize() {
        return this.magicWandSize;
    }

    public void setMagicWandSize(Integer magicWandSize) {
        if (magicWandSize < 1)
            throw new IllegalArgumentException("Wand size should be bigger than 1!");

        this.magicWandSize = magicWandSize;
    }

    @Column(name = "deposit_group", length = 20)
    public String getDepositGroup() {
        return this.depositGroup;
    }

    public void setDepositGroup(String depositGroup) {
        this.depositGroup = depositGroup;
    }

    @Column(name = "deposit_start_date")
    public Timestamp getDepositStartDate() {
        return this.depositStartDate;
    }

    public void setDepositStartDate(Timestamp depositStartDate) {
        this.depositStartDate = depositStartDate;
    }

    @Column(name = "deposit_amount", columnDefinition = "DECIMAL(19, 2)")
    public BigDecimal getDepositAmount() {
        return this.depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    @Column(name = "deposit_interest", columnDefinition = "DECIMAL(19, 2)")
    public BigDecimal getDepositInterest() {
        return this.depositInterest;
    }

    public void setDepositInterest(BigDecimal depositInterest) {
        this.depositInterest = depositInterest;
    }

    @Column(name = "deposit_charge", columnDefinition = "DECIMAL(19, 2)")
    public BigDecimal getDepositCharge() {
        return this.depositCharge;
    }

    public void setDepositCharge(BigDecimal depositCharge) {
        this.depositCharge = depositCharge;
    }

    @Column(name = "deposit_expiration_date")
    public Timestamp getDepositExpirationDate() {
        return this.depositExpirationDate;
    }

    public void setDepositExpirationDate(Timestamp depositExpirationDate) {
        this.depositExpirationDate = depositExpirationDate;
    }

    @Column(name = "is_deposit_expired")
    public Boolean isDepositExpired() {
        return this.isDepositExpired;
    }

    public void setDepositExpired(Boolean depositExpired) {
        isDepositExpired = depositExpired;
    }
}
