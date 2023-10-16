/**
 * Represents a savings account with higher interest rate and a balance floor
 */
public class HighInterestSavings extends SavingsAccount {

    // constants for the default interest rates and balance floor
    public static final double DEFAULT_INTEREST_RATE = 0.1;
    public static final double DEFAULT_BALANCE_FLOOR = 1000;

    private double balanceFloor;

    /**
     * Creates a high interest savings account with a specified balance floor and
     * the default interest rate
     */
    public HighInterestSavings(String accountNumber, String holderName, String holderId, double balance,
            double balanceFloor) {
        super(accountNumber, holderName, holderId, balance, DEFAULT_INTEREST_RATE);
        this.balanceFloor = balanceFloor;
    }

    /**
     * Creates a high interest savings account with the default balance floor and
     * interest rate
     */
    public HighInterestSavings(String accountNumber, String holderName, String holderId, double balance) {
        this(accountNumber, holderName, holderId, balance, DEFAULT_BALANCE_FLOOR);
    }

    public double getBalanceFloor() {
        return balanceFloor;
    }

    public void setBalanceFloor(double balanceFloor) {
        this.balanceFloor = balanceFloor;
    }

    @Override
    public void withdraw(double amount) throws IllegalBalanceException {
        if (balance - amount < balanceFloor)
            throw new IllegalBalanceException("Failed to withdraw $" + amount + " from account " + getAccountNumber()
                    + ": Balance $" + getBalance() + " insufficient to stay above floor $" + balanceFloor);
        super.withdraw(amount);
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "\tBalance Floor: $" + balanceFloor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof HighInterestSavings))
            return false;
        HighInterestSavings account = (HighInterestSavings) obj;
        return super.equals(account) && this.balanceFloor == account.balanceFloor;
    }

}
