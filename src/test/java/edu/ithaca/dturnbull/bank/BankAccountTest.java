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
        BankAccount bankAccount3 = new BankAccount("a@b.com", 0.01);
        assertEquals(0.01, bankAccount3.getBalance(), 0.001); // Boundary: Almost Zero
    }

    @Test
    void isAmountValidTest() {

        // Valid Amount
        assertTrue(BankAccount.isAmountValid(200)); // Non-Boundary: Valid Case
        assertTrue(BankAccount.isAmountValid(200.11)); // Boundary: Most Decimal Points Allowed
        assertTrue(BankAccount.isAmountValid(0.01)); // Boundary: Closest To Non-Positive

        // Negative Amount
        assertFalse(BankAccount.isAmountValid(-200.1)); // Non-Boundary: Negative Case
        assertFalse(BankAccount.isAmountValid(0)); // Boundary: Closest to Non-Negative

        // >2 Decimal Places
        assertFalse(BankAccount.isAmountValid(200.333)); // Boundary: 3 Decimal Places
        assertFalse(BankAccount.isAmountValid(200.33333)); // Non-Boundary: >3 Decimal Places
    }

    @Test
    void withdrawTest() throws InsufficientFundsException, IllegalArgumentException {

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
        assertThrows(IllegalArgumentException.class, () -> bankAccount5.withdraw(0)); // Boundary: 0
        assertThrows(IllegalArgumentException.class, () -> bankAccount5.withdraw(-150)); // Non-Boundary: Negative Withdrawal

        // Excessive Decimal Points Equiv Class
        BankAccount bankAccount6 = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> bankAccount6.withdraw(100.001)); // Boundary: 3 Decimal Places
        assertThrows(IllegalArgumentException.class, () -> bankAccount6.withdraw(100.0001)); // Non-Boundary: >3 Decimal Places
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
    void depositTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        // Valid Deposit Equiv Class
        bankAccount.deposit(100);
        assertEquals(300, bankAccount.getBalance(), 0.001); // Non-Boundary: Normal Deposit
        
        bankAccount.deposit(0.01);
        assertEquals(300.01, bankAccount.getBalance(), 0.001); // Boundary: Smallest Deposit

        // Negative Amount Equiv Class
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0)); // Boundary: Zero
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-0.01)); // Boundary: Negative
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-100)); // Non-Boundary: Negative

        // Excessive Decimal Points Equiv Class
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(100.001)); // Boundary: 3 Decimal Places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(100.0001)); // Non-Boundary: >3 Decimal Places
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        // check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100));

        BankAccount bankAccount3 = new BankAccount("a@b.com", 0.01);
        assertEquals(0.01, bankAccount3.getBalance(), 0.001); // Boundary: Almost Zero

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -0.01)); // Boundary: Barely Negative

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -200)); // Non-Boundary: Normal Negative

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", 200.001)); // Boundary: 3 Decimal Places

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", 200.0001)); // Non-Boundary: >3 Decimal Places

        
    }

    @Test
    void transferTest() throws InsufficientFundsException, IllegalArgumentException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        BankAccount bankAccount2 = new BankAccount("c@d.com", 200);

        // Valid Transfer Equiv Class
        bankAccount.transfer(100, bankAccount2);
        assertEquals(100, bankAccount.getBalance(), 0.001); // Sender Balance Decreases
        assertEquals(300, bankAccount2.getBalance(), 0.001); // Receiver Updated

        bankAccount.transfer(100, bankAccount2); // Boundary: Transfer All
        assertEquals(0, bankAccount.getBalance(), 0.001); // Sender Empty
        assertEquals(400, bankAccount2.getBalance(), 0.001); // Receiver Updated

        // Insufficient Funds Equiv Class
        assertThrows(InsufficientFundsException.class, () -> bankAccount.transfer(1, bankAccount2)); // Boundary: Overdraft by 1 (empty)
        
        BankAccount bankAccount3 = new BankAccount("e@f.com", 200);
        assertThrows(InsufficientFundsException.class, () -> bankAccount3.transfer(300, bankAccount2)); // Non-Boundary: Overdraft

        // Invalid Amount Equiv Class
        assertThrows(IllegalArgumentException.class, () -> bankAccount3.transfer(-100, bankAccount2)); // Non-Boundary: Negative
        assertThrows(IllegalArgumentException.class, () -> bankAccount3.transfer(0, bankAccount2)); // Boundary: Zero
        assertThrows(IllegalArgumentException.class, () -> bankAccount3.transfer(100.001, bankAccount2)); // Boundary: 3 Decimal Places

        // Invalid Receiver Equiv Class
        assertThrows(IllegalArgumentException.class, () -> bankAccount3.transfer(100, null)); // Boundary: Null Receiver
    }

}