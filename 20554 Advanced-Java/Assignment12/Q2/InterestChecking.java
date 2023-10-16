/**
 * Represents a bank account with a monthly interest rate
 */
public class InterestChecking extends NoServiceChargeChecking {

    // the relatively higher, default balance floor for interest checking accounts
    public static final double DEFAULT_BALANCE_FLOOR = 200.0;

    // the default interest rate
    public static final double DEFAULT_INTEREST_RATE = 0.025;

    // the interest rate for this account
    private double interestRate;

    /**
     * Constructor for the interest checking account with the default balance floor
     * and interest rate
     */
    public InterestChecking(String accountNumber, String holderName, String holderId, double balance) {
        this(accountNumber, holderName, holderId, balance, DEFAULT_INTEREST_RATE);
    }

    /**
     * Constructor for the interest checking account with the default balance floor
     * and a specified interest rate
     */
    public InterestChecking(String accountNumber, String holderName, String holderId, double balance,
            double interestRate) {
        super(accountNumber, holderName, holderId, balance, DEFAULT_BALANCE_FLOOR);
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
        if (obj == null || !(obj instanceof InterestChecking))
            return false;
        InterestChecking account = (InterestChecking) obj;
        return super.equals(account) && this.interestRate == account.interestRate;
    }
}
