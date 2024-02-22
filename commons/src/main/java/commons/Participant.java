package commons;

import java.util.Objects;

public class Participant {
    private String name; /** name of participant **/
    private String email; /** email of participant **/
    private String IBAN; /**IBAN of participant **/
    private String BIC; /** BIC of participant **/

    public Participant(String name, String email, String IBAN, String BIC){
        this.name=name;
        this.email=email;
        this.IBAN=IBAN;
        this.BIC=BIC;
    }
    public String getName(String name){
        return name;
    }

    public String getEmail(String email){
        return email;
    }

    public String getIBAN(String IBAN){
        return IBAN;
    }

    public String getBIC(String BIC){
        return BIC;
    }

    public void setName(String newName){
        this.name=newName;
    }

    public void setEmail(String newEmail){
        this.email=newEmail;
    }

    public void setIBAN(String newIBAN){
        this.IBAN=newIBAN;
    }

    public void setBIC(String newBIC){
        this.BIC=newBIC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(IBAN, that.IBAN) && Objects.equals(BIC, that.BIC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, IBAN, BIC);
    }
}
