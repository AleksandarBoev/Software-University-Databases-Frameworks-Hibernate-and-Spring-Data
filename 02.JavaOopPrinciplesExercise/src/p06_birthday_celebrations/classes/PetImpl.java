package p06_birthday_celebrations.classes;

import p06_birthday_celebrations.contracts.Pet;

import java.time.LocalDate;

public class PetImpl implements Pet {
    private String name;
//    private LocalDate birthDate;
    private String birthDateString;

    public PetImpl(String name, LocalDate birthDate) {
        this.name = name;
//        this.birthDate = birthDate;
    }

    public PetImpl(String name, String birthDateString) {
        this.name = name;
        this.birthDateString = birthDateString;
    }

//    @Override
//    public LocalDate getBirthDate() {
//        return this.birthDate;
//    }



    @Override
    public String getName() {
        return this.name;
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
}
