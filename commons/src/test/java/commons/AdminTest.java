package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
    Admin admin;
    @BeforeEach
    void setUp() {
        admin = new Admin("James", "password");
    }

    @Test
    void getName() {
        assertEquals("James", admin.getName());
    }

    @Test
    void setName() {
        admin.setName("Jake");
        assertEquals("Jake", admin.getName());
    }

    @Test
    void getPassword() {
        assertEquals("password", admin.getPassword());
    }

    @Test
    void setPassword() {
        admin.setPassword("1234");
        assertEquals("1234", admin.getPassword());
    }

    @Test
    void testEquals() {
        Admin admin1 = admin;
        assertEquals(admin, admin1);

        Admin admin2 = new Admin("James", "password");
        assertEquals(admin, admin2);

        admin2.setPassword("1234");
        assertNotEquals(admin, admin2);

        admin2.setPassword("password");
        admin2.setName("Jake");
        assertNotEquals(admin, admin2);

    }

    @Test
    void testHashCode() {
        Admin admin1 = new Admin("James", "password");
        assertEquals(admin.hashCode(), admin1.hashCode());
    }
}