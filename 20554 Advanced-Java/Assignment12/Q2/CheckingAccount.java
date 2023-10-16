/**
 * The abstract checking account class, representing a bank account that allows
 * writing checks
 */
public abstract class CheckingAccount extends BankAccount {

    /**
     * Constructor for the checking account object
     */
    public CheckingAccount(String accountNumber, String holderName, String holderId, double balance) {
        super(accountNumber, holderName, holderId, balance);
    }

    /**
     * Method for writing checks.
     * 
     * @param amount the amount to write
     * @throws IllegalBalanceException if balance is less than the amount specified
     */
    public void writeCheck(double amount) throws IllegalBalanceException {
        if (amount > balance)
            throw new IllegalBalanceException("Balance insufficient to write check with specified amount");
    }

}
