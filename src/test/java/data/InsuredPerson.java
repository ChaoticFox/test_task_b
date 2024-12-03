package data;

public class InsuredPerson {
    private final boolean isResident;
    private String country;
    private String name;
    private String surname;
    private String identityCode;
    private int birthDay;
    private String birthMonth;
    private int birthYear;

    public InsuredPerson(boolean isResident) {
        this.isResident = isResident;
    }

    public InsuredPerson(boolean isResident,
                         String country,
                         String name,
                         String surname,
                         String identityCode,
                         int birthDay,
                         String birthMonth,
                         int birthYear) {
        this.isResident = isResident;
        this.country = country;
        this.name = name;
        this.surname = surname;
        this.identityCode = identityCode;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
    }

    public Boolean getIsResident() {
        return this.isResident;
    }
}
