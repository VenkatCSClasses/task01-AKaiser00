package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.xml.stream.events.Characters;

class BankAccountTest {

    @Test
    void getBalanceTest() {

        // Positive Balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001); // Non-Boundary: Normal Positive

        BankAccount bankAccount2 = new BankAccount("a@b.com", 0.01);
        assertEquals(0.01, bankAccount2.getBalance(), 0.001); // Boundary: Smallest Positive

        // Zero Balance
        BankAccount bankAccount3 = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount3.getBalance(), 0.001); // Boundary: Zero

        // Negative Balance
        BankAccount bankAccount4 = new BankAccount("a@b.com", -0.01);
        assertEquals(-0.01, bankAccount4.getBalance(), 0.001); // Boundary: Largest Negative
    
        BankAccount bankAccount5 = new BankAccount("a@b.com", -200);
        assertEquals(-200, bankAccount5.getBalance(), 0.001); // Non-Boundary: Normal Negative
    
    }

    @Test
    void withdrawTest() throws InsufficientFundsException {

        // Valid Withdraw Equiv Class
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100); 
        assertEquals(100, bankAccount.getBalance(), 0.001); // Non-Boundary: Doesn't Fully Remove Balance

        BankAccount bankAccount2 = new BankAccount("a@b.com", 200);
        bankAccount2.withdraw(200); 
        assertEquals(0, bankAccount2.getBalance(), 0.001); // Boundary: Fully Removes Balance

        // Insufficient Funds Equiv Class
        BankAccount bankAccount3 = new BankAccount("a@b.com", 200);
        assertThrows(InsufficientFundsException.class, () -> bankAccount3.withdraw(200.01)); // Boundary: $0.01 Overdraft
        assertThrows(InsufficientFundsException.class, () -> bankAccount3.withdraw(300)); // Non-Boundary: Overdraft

        // Negative Withdrawal Equiv Class
        BankAccount bankAccount5 = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> bankAccount5.withdraw(0)); // Boundary: 0 Withdrawal
        assertThrows(IllegalArgumentException.class, () -> bankAccount5.withdraw(-150)); // Non-Boundary: Negative Withdrawal
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