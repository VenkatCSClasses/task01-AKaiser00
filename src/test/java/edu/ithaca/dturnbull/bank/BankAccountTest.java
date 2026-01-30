package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest() {
        // Starting Tests
        assertTrue(BankAccount.isEmailValid("a@b.com")); // boundary case: valid, minimal email address
        assertFalse(BankAccount.isEmailValid("")); // boundary case: invalid, empty string

        // Testing Prefix
        assertTrue(BankAccount.isEmailValid("abc-d@mail.com")); // not boundary: valid, testing special char
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com")); // not boundary: valid, testing period before @
        assertTrue(BankAccount.isEmailValid("abc@mail.com")); // not boundary: valid, normal email
        assertTrue(BankAccount.isEmailValid("abc_def@mail.com")); // not boundary: valid, testing special char

        assertFalse(BankAccount.isEmailValid("abc-@mail.com")); // boundary: special char at end of prefix
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com")); // not boundary: invalid, special chars in a row
        assertFalse(BankAccount.isEmailValid(".abc@mail.com")); // boundary: invalid, special char at beginning of
                                                                // prefix
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com")); // not boundary: invalid, not permitted special char
        assertFalse(BankAccount.isEmailValid("@mail.com")); // boundary: invalid, empty prefix
        assertFalse(BankAccount.isEmailValid("abc@@mail.com")); // boundary: multiple @ symbols, in a row
        assertFalse(BankAccount.isEmailValid("abc@mail@archive.com")); // not boundary: multiple @ symbols

        // Testing Domain
        assertTrue(BankAccount.isEmailValid("abc.def@mail.cc")); // boundary: valid, two character end domain
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com")); // not boundary: valid, permitted special
                                                                          // chars
        assertTrue(BankAccount.isEmailValid("abc.def@mail.org")); // not boundary: valid, .org domain
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com")); // not boundary: valid, .com domain

        assertFalse(BankAccount.isEmailValid("abc.def@mail.c")); // boundary: invalid, one character end domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com")); // not boundary: invalid, not permitted
                                                                           // special char
        assertFalse(BankAccount.isEmailValid("abc.def@mail")); // boundary: invalid, non-existent domain tag
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com")); // not boundary: invalid, multiple special chars in
                                                                    // a row
        assertFalse(BankAccount.isEmailValid("abc@")); // boundary: invalid, empty domain
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        // check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100));
    }

}