/**
 * The main, most general, bank account class.
 * 
 * @author Itay Schechner
 */
public abstract class BankAccount {

    // account number
    private String accountNumber;

    // account holder name
    private String holderName;

    // account holder Id
    private String holderId;

    // account balance
    protected double balance;

    /**
     * A constructor for the bank account object
     * 
     * @param accountNumber the account number
     * @param holderName    the holder's name
     * @param holderId      the holder's id
     * @param balance       the initial balance
     */
    public BankAccount(String accountNumber, String holderName, String holderId, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.holderId = holderId;
        this.balance = balance;
    }

    /* Getters and setters */

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getHolderId() {
        return holderId;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public void setHolderId(String holderId) {
        this.holderId = holderId;
    }

    // did not provide a setBalance method, since there are methods to set it

    /* Class methods, provided by the instructions */

    /**
     * Deposit money in the account
     * 
     * @param amount the amount to deposit
     */
    public void deposit(double amount) {
        this.balance += amount;
    }

    /**
     * Withdraw money from the account
     * 
     * @param amount the amount to withdraw
     * @throws IllegalBalanceException if amount is greater than the current balance
     */
    public void withdraw(double amount) throws IllegalBalanceException {
        if (amount > balance)
            throw new IllegalBalanceException("Failed to withdraw $" + amount + " from account " + accountNumber
                    + ": Balance $" + balance + " is insufficient");
        this.balance -= amount;
    }

    /**
     * Monthly management method - this is where all monthly actions should be done
     */
    public abstract void monthlyManagement();

    /**
     * Creates a string representation of the bank account
     */
    @Override
    public String toString() {
        return "Bank account: \n" +
                "\tType: " + getClass().getName() + "\n" + // will return the account type
                "\tAccount number: " + accountNumber + "\n" +
                "\tHolder Name: " + holderName + "\n" +
                "\tHolder Id: " + holderId + "\n" +
                "\tCurrent Balance: $" + balance;
    }

    /**
     * Compares between two bank account objects
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BankAccount))
            return false;
        BankAccount bankAccount = (BankAccount) obj;
        return this.accountNumber == bankAccount.accountNumber &&
                this.holderName == bankAccount.holderName &&
                this.holderId == bankAccount.holderId &&
                this.balance == bankAccount.balance;
    }

}
