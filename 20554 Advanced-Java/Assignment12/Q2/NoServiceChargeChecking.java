/**
 * Representing a bank account with no monthly service fee
 * 
 * @author Itay Schechner
 */
public class NoServiceChargeChecking extends CheckingAccount {

    // the default monthly service fee
    public static final double DEFAULT_BALANCE_FLOOR = 50.0;

    // the service fee for the account
    private double balanceFloor;

    /**
     * Constructor for the service charge checking with the default balance floor
     */
    public NoServiceChargeChecking(String accountNumber, String holderName, String holderId, double balance) {
        this(accountNumber, holderName, holderId, balance, DEFAULT_BALANCE_FLOOR);
    }

    /**
     * Constructor for the service charge checking with a specified balance floor
     */
    public NoServiceChargeChecking(String accountNumber, String holderName, String holderId, double balance,
            double balanceFloor) {
        super(accountNumber, holderName, holderId, balance);
        this.balanceFloor = balanceFloor;
    }

    public double getBalanceFloor() {
        return balanceFloor;
    }

    public void setBalanceFloor(double balanceFloor) {
        this.balanceFloor = balanceFloor;
    }

    @Override
    public void monthlyManagement() {
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
        if (obj == null || !(obj instanceof NoServiceChargeChecking))
            return false;
        NoServiceChargeChecking account = (NoServiceChargeChecking) obj;
        return super.equals(account) && this.balanceFloor == account.balanceFloor;
    }

}
