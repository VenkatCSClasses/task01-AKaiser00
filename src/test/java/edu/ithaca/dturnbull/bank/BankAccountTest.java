package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.xml.stream.events.Characters;

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

        // Blank String Equiv Class   
        assertFalse(BankAccount.isEmailValid("")); // Boundary Case: Empty String
        assertTrue(BankAccount.isEmailValid("a@b.cc")); // Boundary Case: Smallest Valid Email
        assertTrue(BankAccount.isEmailValid("abc@def.com")); // Non-Boundary: Normal Email

        // Prefix Empty Equiv Class      
        assertFalse(BankAccount.isEmailValid("@mail.com")); // Boundary Case: Empty Prefix
        assertTrue(BankAccount.isEmailValid("a@def.com")); // Boundary Case: Smallest Valid Prefix
        assertTrue(BankAccount.isEmailValid("abc@def.com")); // Non-Boundary: Normal Prefix

        // Domain Empty Equiv Class   
        assertFalse(BankAccount.isEmailValid("abc@")); // Boundary Case: Missing Domain
        assertTrue(BankAccount.isEmailValid("abc@d.cc")); // Boundary Case: Smallest Valid Domain
        assertTrue(BankAccount.isEmailValid("abc@def.com")); // Non-Boundary: Normal Domain

        // Domain Tag Length Equiv Class   
        assertFalse(BankAccount.isEmailValid("abc@d.e")); // Boundary Case: Tag Length 1
        assertTrue(BankAccount.isEmailValid("abc@d.cc")); // Boundary Case: Tag Length 2
        assertTrue(BankAccount.isEmailValid("abc@d.com")); // Non-Boundary: Tag Length 3+

        // Invalid Special Characters Equiv Class   
        assertFalse(BankAccount.isEmailValid("abc:abc@def.com")); // Boundary Case: 1 Invalid Char
        assertTrue(BankAccount.isEmailValid("abc@def.com")); // Boundary Case: 0 Invalid Char

        // Special Characters Locations Equiv Class   
        assertFalse(BankAccount.isEmailValid("-abc@def.com")); // Boundary Case: Special @ Start of Prefix
        assertFalse(BankAccount.isEmailValid("abc-@def.com")); // Boundary Case: Special @ End of Prefix
        assertFalse(BankAccount.isEmailValid("abc--abc@def.com")); // Non-Boundary: Specials @ Middle of Prefix, consecutive
        assertTrue(BankAccount.isEmailValid("abc-abc@def.com")); // Non-Boundary: Special @ Middle of Prefix, non-consecutive         

        // # of @ Symbols Equiv Class   
        assertFalse(BankAccount.isEmailValid("abcdef.com")); // Boundary Case: 0 @ Symbols
        assertTrue(BankAccount.isEmailValid("abc@def.com")); // Boundary Case: 1 @ Symbol
        assertFalse(BankAccount.isEmailValid("abc@def@def.com")); // Boundary Case: 2 @ Symbols

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