package ac2_project.example.ac2_ca.entity.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ac2_project.example.ac2_ca.entity.User_Email;

class UserEmailTest {

    @Test
    void testValidEmail() {
        User_Email email = new User_Email("test@example.com");
        assertEquals("test@example.com", email.getEmailAddress());
    }

    @Test
    void testNullEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User_Email(null);
        });
    }

    @Test
    void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User_Email("invalid-email");
        });
    }

    @Test
    void testEqualsWithSameEmail() {
        User_Email email1 = new User_Email("test@example.com");
        User_Email email2 = new User_Email("test@example.com");
        assertEquals(email1, email2);
    }

    @Test
    void testEqualsWithDifferentEmail() {
        User_Email email1 = new User_Email("test1@example.com");
        User_Email email2 = new User_Email("test2@example.com");
        assertNotEquals(email1, email2);
    }

    @Test
    void testHashCodeConsistency() {
        User_Email email1 = new User_Email("test@example.com");
        User_Email email2 = new User_Email("test@example.com");
        assertEquals(email1.hashCode(), email2.hashCode());
    }
} 