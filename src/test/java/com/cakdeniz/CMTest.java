package com.cakdeniz;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

public class CMTest {

    ContactManager cm;
    @BeforeAll
    public static void setupAll(){
        System.out.println("Should print before all tests");
    }

    @BeforeEach
    public void setup(){
        cm = new ContactManager();
    }
    @Test
    public void shouldCreateContact() {
        cm.addContact("Cenk", "Akdeniz", "05394838103");
        Assertions.assertFalse(cm.getAllContacts().isEmpty());
        Assertions.assertEquals(cm.getAllContacts().size(), 1);
        Assertions.assertTrue(cm.getAllContacts().stream().filter(contact ->
                        contact.getFirstName().equals("Cenk") &&
                        contact.getLastName().equals("Akdeniz") &&
                        contact.getPhoneNumber().equals("05394838103"))
                .findAny()
                .isPresent()
        );
    }

    @Test
    @DisplayName("Should not create contact when first name is null")
    public void shouldThrowRuntimeExceptionWhenFirstNameNull(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            cm.addContact(null, "Akdeniz", "05394838103");
        });
    }
    @Test
    @DisplayName("Should not create contact when last name is null")
    public void shouldThrowRuntimeExceptionWhenLastNameNull(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            cm.addContact("Cenk", null, "05394838103");
        });
    }

    @Test
    @DisplayName("Should not create contact when phone number is null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberNull(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            cm.addContact("Cenk", "Akdeniz", null);
        });
    }

    @DisplayName("Test different values")
    @ParameterizedTest
    @ValueSource(strings = {"01234567891", "02020202021","09876543211"})
    public void shouldTestContactCreationUsingValueSource(String phoneNumber){
        cm.addContact("Cenk","Akdeniz", phoneNumber);
        Assertions.assertFalse(cm.getAllContacts().isEmpty());
        Assertions.assertEquals(cm.getAllContacts().size(), 1);
    }


    @DisplayName("Test different values")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestContactCreationUsingMethodSource(String phoneNumber){
        cm.addContact("Cenk","Akdeniz", phoneNumber);
        Assertions.assertFalse(cm.getAllContacts().isEmpty());
        Assertions.assertEquals(cm.getAllContacts().size(), 1);
    }
    private static List<String> phoneNumberList(){
        return Arrays.asList("01234567891", "02020202021","09876543211");
    }

    @DisplayName("Test different values")
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void shouldTestContactCreationUsingCsvSource(String phoneNumber){
        cm.addContact("Cenk","Akdeniz", phoneNumber);
        Assertions.assertFalse(cm.getAllContacts().isEmpty());
        Assertions.assertEquals(cm.getAllContacts().size(), 1);
    }
}
