/**
 * Represents a savings bank account
 */
public class SavingsAccount extends BankAccount {

    // the default interest rate
    public static final double DEFAULT_INTEREST_RATE = 0.075;

    // the interest rate for this account
    private double interestRate;

    /**
     * Constructor for the savings account with the default interest rate
     */
    public SavingsAccount(String accountNumber, String holderName, String holderId, double balance) {
        this(accountNumber, holderName, holderId, balance, DEFAULT_INTEREST_RATE);
    }

    /**
     * Constructor for the savings account with a specified interest rate
     */
    public SavingsAccount(String accountNumber, String holderName, String holderId, double balance,
            double interestRate) {
        super(accountNumber, holderName, holderId, balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void monthlyManagement() {
        balance *= (1 + interestRate);
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "\tInterest Rate: " + interestRate * 100 + "%";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof SavingsAccount))
            return false;
        SavingsAccount account = (SavingsAccount) obj;
        return super.equals(account) && this.interestRate == account.interestRate;
    }
}
