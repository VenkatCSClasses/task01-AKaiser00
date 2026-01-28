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
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        // Starting Tests
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string


        // Testing Prefix
        assertTrue(BankAccount.isEmailValid("abc-d@mail.com")); // valid, testing special char
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com")); // valid, testing period before @
        assertTrue(BankAccount.isEmailValid("abc@mail.com")); // valid, normal email
        assertTrue(BankAccount.isEmailValid("abc_def@mail.com")); // valid, testing special char

        assertFalse(BankAccount.isEmailValid("abc-@mail.com")); // special char at end of prefix
        assertFalse(BankAccount.isEmailValid("abc..def@mail.com")); // special chars in a row
        assertFalse(BankAccount.isEmailValid(".abc@mail.com")); // special char at beginning of prefix
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com")); // not permitted special char
        assertFalse(BankAccount.isEmailValid("@mail.com")); // empty prefix
        assertFalse(BankAccount.isEmailValid("abc@@mail.com")); // multiple @ symbols, in a row
        assertFalse(BankAccount.isEmailValid("abc@mail@archive.com")); // multiple @ symbols


        // Testing Domain
        assertTrue(BankAccount.isEmailValid("abc.def@mail.cc")); // two character end domain
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com")); // permitted special chars
        assertTrue(BankAccount.isEmailValid("abc.def@mail.org")); // .org domain
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com")); // .com domain

        assertFalse(BankAccount.isEmailValid("abc.def@mail.c")); // one character end domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com")); // not permitted special char
        assertFalse(BankAccount.isEmailValid("abc.def@mail")); // non-existent domain
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com")); // multiple special chars in a row
        assertFalse(BankAccount.isEmailValid("abc@")); // empty domain
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}