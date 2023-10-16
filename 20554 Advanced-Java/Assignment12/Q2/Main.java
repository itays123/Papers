import java.util.Random;

public class Main {

    public static final int NUM_OF_ACCOUNTS = 5;
    public static final int MAX_INITIAL_BALANCE = 10000;
    public static final int MAX_ACTION_AMOUNT = 6000;
    public static final int NUM_OF_ACTIONS = 20;

    public static Random rand = new Random();

    public static void main(String[] args) {
        BankAccount[] accounts = generateAccounts();

        printAccounts(accounts);
        randomWithdraw(accounts);

        System.out.println("\n\n\nPerforming monthly management: ");
        for (BankAccount account : accounts) {
            System.out.println("\n\n\nBefore monthly management: " + account);
            account.monthlyManagement();
            System.out.println("After monthly management: " + account);
        }
    }

    public static BankAccount[] generateAccounts() {
        BankAccount[] accounts = new BankAccount[NUM_OF_ACCOUNTS];
        System.out.println("\n\n\nCreating accounts: ");
        accounts[0] = new ServiceChargeChecking("000000", "Mr. Java", "20554",
                rand.nextInt(MAX_INITIAL_BALANCE));
        accounts[1] = new NoServiceChargeChecking("111111", "Mr. Java", "20554",
                rand.nextInt(MAX_INITIAL_BALANCE));
        accounts[2] = new InterestChecking("222222", "Mr. Java", "20554",
                rand.nextInt(MAX_INITIAL_BALANCE));
        accounts[3] = new SavingsAccount("333333", "Mr. Java", "20554",
                rand.nextInt(MAX_INITIAL_BALANCE));
        accounts[4] = new HighInterestSavings("444444", "Mr. Java", "20554",
                rand.nextInt(MAX_INITIAL_BALANCE));
        return accounts;
    }

    public static void printAccounts(BankAccount[] accounts) {
        System.out.println("\n\n\nAccount states: ");
        for (BankAccount account : accounts) {
            System.out.println(account);
        }
    }

    public static void randomWithdraw(BankAccount[] accounts) {
        int idx, amount;
        // try to make NUM_OF_ACTIONS withdraws.
        System.out.println("\n\n\nWithdrawing money...");
        for (int i = 0; i < NUM_OF_ACTIONS; i++) {
            idx = rand.nextInt(NUM_OF_ACCOUNTS);
            amount = rand.nextInt(MAX_ACTION_AMOUNT);
            try {
                accounts[idx].withdraw(amount);
                System.out.println("Withdrawn $" + amount + " from account " + accounts[idx].getAccountNumber()
                        + "; new balance is $" + accounts[idx].getBalance());
            } catch (IllegalBalanceException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
