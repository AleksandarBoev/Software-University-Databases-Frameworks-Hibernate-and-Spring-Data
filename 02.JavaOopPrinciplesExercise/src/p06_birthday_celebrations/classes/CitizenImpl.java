package p06_birthday_celebrations.classes;

import p06_birthday_celebrations.contracts.Citizen;

import java.time.LocalDate;

public class CitizenImpl extends IdentifiableImpl implements Citizen {
    private String name;
    private Integer age;
//    private LocalDate birthDate;
    private String birthDateString;

    public CitizenImpl(String name, Integer age, String id, LocalDate birthDate) {
        super(id);
        this.name = name;
        this.age = age;
//        this.birthDate = birthDate;
    }

    public CitizenImpl(String name, Integer age, String id, String birthDateString) {
        super(id);
        this.name = name;
        this.age = age;
        this.birthDateString = birthDateString;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getAge() {
        return this.age;
    }

    @Override
    public String getBirthDateString() {
        return this.birthDateString;
    }

    @Override
    public String extractYearOfBirthDateString() {
        String[] dateTokens = this.birthDateString.split("/");
        return dateTokens[2];
    }

//    @Override
//    public LocalDate getBirthDate() {
//        return this.birthDate;
//    }
}
