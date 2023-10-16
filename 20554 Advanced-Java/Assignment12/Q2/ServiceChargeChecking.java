/**
 * Representing a bank account with a monthly service fee
 * 
 * @author Itay Schechner
 */
public class ServiceChargeChecking extends CheckingAccount {

    // the default monthly service fee
    public static final double DEFAULT_SERVICE_FEE = 20.0;

    // the service fee for the account
    private double serviceFee;

    /**
     * Constructor for the service charge checking with the default service fee
     */
    public ServiceChargeChecking(String accountNumber, String holderName, String holderId, double balance) {
        this(accountNumber, holderName, holderId, balance, DEFAULT_SERVICE_FEE);
    }

    /**
     * Constructor for the service charge checking with a specified service fee
     */
    public ServiceChargeChecking(String accountNumber, String holderName, String holderId, double balance,
            double serviceFee) {
        super(accountNumber, holderName, holderId, balance);
        this.serviceFee = serviceFee;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    @Override
    public void monthlyManagement() {
        balance -= serviceFee;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "\tService Fee: $" + serviceFee;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ServiceChargeChecking))
            return false;
        ServiceChargeChecking account = (ServiceChargeChecking) obj;
        return super.equals(account) && this.serviceFee == account.serviceFee;
    }

}
