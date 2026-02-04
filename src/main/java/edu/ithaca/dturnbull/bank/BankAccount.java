package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (!isEmailValid(email)){
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");            
        } 
        else if (!isAmountValid(startingBalance)){
            throw new IllegalArgumentException("Starting Balance must be valid.");
        }
        else {
            this.email = email;
            this.balance = startingBalance;
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Takes in an amount, determines whether the amount is a valid money amount
     * @param amount the amount to check
     * @return true if valid (non-negative, max 2 decimal places), false if otherwise
     */
    public static boolean isAmountValid(double amount){
        int amountInt = (int) (amount * 100);
        if ((double) amountInt != (amount * 100)|| amount <= 0){
            return false;
        }
        return true;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller
     *       than balance
     * @throws IllegalArgumentException   if amount is negative or 0, or has >2 decimal places
     * @throws InsufficientFundsException if amount is larger than balance
     */
    public void withdraw(double amount) throws InsufficientFundsException, IllegalArgumentException {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Withdrawal amount must be valid");
        }
        if (amount <= balance) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * Adds amount to balance
     * @param amount amount to deposit
     * @throws IllegalArgumentException if amount is non-positive, or has >2 decimal places
     */
    public void deposit(double amount) throws IllegalArgumentException {
        
    }




    public static boolean isEmailValid(String email) {
        if (email.indexOf('@') == -1) {
            return false;
        }
        if (email.isEmpty()) {
            return false;
        }
        String[] p_and_d = email.split("@");
        if (p_and_d.length != 2) {
            return false;
        }

        String prefix = p_and_d[0];
        String domain = p_and_d[1];
        String[] domain_parts = domain.split("\\.");
        String end_domain = domain_parts[domain_parts.length - 1];
        if (prefix.isEmpty() || domain.isEmpty()) {
            return false;
        }
        if (String.valueOf(prefix.charAt(0)).matches("[^a-zA-Z0-9]")) {
            return false;
        }
        if (String.valueOf(prefix.charAt(prefix.length() - 1)).matches("[^a-zA-Z0-9]")) {
            return false;
        }
        if (!prefix.matches("[A-Za-z0-9._-]+") || !domain.matches("[A-Za-z0-9.-]+")) {
            return false;
        }
        if (prefix.matches(".*[._-]{2,}.*") || domain.matches(".*[._-]{2,}.*")) {
            return false;
        }
        if (!domain.contains(".")) {
            return false;
        }
        if (end_domain.length() < 2) {
            return false;
        } else {
            return true;
        }
    }
}