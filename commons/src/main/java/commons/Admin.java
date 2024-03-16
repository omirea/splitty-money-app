package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    private String name;

    private String password;

    /**
     * No argument constructor for Admin to allow JPA to work
     */
    public Admin() {
    }

    /**
     * 2 parameter constructor for admin to initialize both the name and password
     * @param name is the name of the admin
     * @param password is the password of the admin
     */
    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * gets the admin name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * allows someone to set the name of the admin to a new name
     * @param name is the new name for the admin
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the admin password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * allows for setting the password of the admin to a new password
     * @param password is the new password for the admin
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Two Admins are considered equal if they have the same name and password.
     *
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(getName(), admin.getName()) &&
            Objects.equals(getPassword(), admin.getPassword());
    }

    /**
     * Returns a hash code value for the object.
     * uses the inbuilt Objects.hash() from the Java Library
     * @return the hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPassword());
    }
}
