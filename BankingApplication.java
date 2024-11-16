import java.util.ArrayList;
import java.util.Scanner;

class InsufficientFundsException extends Exception {
    InsufficientFundsException(String message) {
        super(message);
    }
}

class AccountNotFoundException extends Exception {
    AccountNotFoundException(String message) {
        super(message);
    }
}

class DuplicateAccountException extends Exception {
    DuplicateAccountException(String message) {
        super(message);
    }
}

class InvalidAmountException extends Exception {
    InvalidAmountException(String message) {
        super(message);
    }
}

abstract class Account {
    String accountNumber;
    String accountHolder;
    double balance;

    Account(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    String getAccountNumber() {
        return accountNumber;
    }

    double getBalance() {
        return balance;
    }

    void deposit(double amount) throws InvalidAmountException {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited " + amount + ". New balance: " + balance);
        } else {
            throw new InvalidAmountException("Invalid deposit amount. It must be greater than zero.");
        }
    }

    void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Invalid withdrawal amount. It must be greater than zero.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal.");
        }
        balance -= amount;
        System.out.println("Withdrew " + amount + ". New balance: " + balance);
    }
}

class SavingsAccount extends Account {
    static final double INTEREST_RATE = 6.0;

    SavingsAccount(String accountNumber, String accountHolder, double balance) {
        super(accountNumber, accountHolder, balance);
    }

    void addInterest() {
        balance += balance * INTEREST_RATE / 100;
        System.out.println("Interest added. New balance: " + balance);
    }
}

class CheckingAccount extends Account {
    double overdraftLimit;

    CheckingAccount(String accountNumber, String accountHolder, double balance, double overdraftLimit) {
        super(accountNumber, accountHolder, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Invalid withdrawal amount. It must be greater than zero.");
        }
        if (amount > balance + overdraftLimit) {
            throw new InsufficientFundsException("Withdrawal exceeds overdraft limit.");
        }
        balance -= amount;
        System.out.println("Withdrew " + amount + ". New balance: " + balance);
    }
}

class Bank {
    ArrayList<Account> accounts = new ArrayList<>();

    void createAccount(Account account) throws DuplicateAccountException {
        if (accountExists(account.getAccountNumber())) {
            throw new DuplicateAccountException("Account with number " + account.getAccountNumber() + " already exists.");
        }
        accounts.add(account);
        System.out.println("Account created: " + account.accountNumber);
    }

    void deleteAccount(String accountNumber) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        accounts.remove(account);
        System.out.println("Account " + accountNumber + " has been deleted.");
    }

    Account getAccount(String accountNumber) throws AccountNotFoundException {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        throw new AccountNotFoundException("Account with number " + accountNumber + " not found.");
    }

    boolean accountExists(String accountNumber) {
        return accounts.stream().anyMatch(account -> account.getAccountNumber().equals(accountNumber));
    }

    void transfer(String fromAccount, String toAccount, double amount) throws Exception {
        Account from = getAccount(fromAccount);
        Account to = getAccount(toAccount);

        from.withdraw(amount);
        to.deposit(amount);
        System.out.println("Transferred " + amount + " from account " + fromAccount + " to account " + toAccount);
    }
}

class BankingApplication {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Create Account\n2. Delete Account\n3. Deposit\n4. Withdraw\n5. Balance Inquiry\n6. Transfer\n7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        //int type = getAccountType(scanner);
                        String accNumber = getAccountNumber(scanner);

                        // Check if account number already exists before asking for further inputs
                        if (bank.accountExists(accNumber)) {
                            throw new DuplicateAccountException("Account with number " + accNumber + " already exists.");
                        }

                        String holderName = getAccountHolderName(scanner);
                        double initialBalance = getInitialBalance(scanner);
/* 
                        if (type == 1) {
                            bank.createAccount(new SavingsAccount(accNumber, holderName, initialBalance));
                        } else if (type == 2) {
                            double overdraftLimit = getOverdraftLimit(scanner);
                            bank.createAccount(new CheckingAccount(accNumber, holderName, initialBalance, overdraftLimit));
                        }
                        break;
*/
                    case 2:
                        String delAccount = getAccountNumber(scanner);
                        bank.deleteAccount(delAccount);
                        break;

                    case 3:
                        String depositAccount = getAccountNumber(scanner);
                        Account depositAcc = bank.getAccount(depositAccount);  // Verify account before asking for amount
                        double depositAmount = getAmount(scanner, "Enter deposit amount: ");
                        depositAcc.deposit(depositAmount);
                        break;

                    case 4:
                        String withdrawAccount = getAccountNumber(scanner);
                        Account withdrawAcc = bank.getAccount(withdrawAccount);  // Verify account before asking for amount
                        double withdrawalAmount = getAmount(scanner, "Enter withdrawal amount: ");
                        withdrawAcc.withdraw(withdrawalAmount);
                        break;

                    case 5:
                        String inquiryAccount = getAccountNumber(scanner);
                        Account account = bank.getAccount(inquiryAccount);
                        System.out.println("Current balance: " + account.getBalance());
                        break;

                    case 6:
                        System.out.print("Enter sender's account number: ");
                        String fromAccount = scanner.next();

                        // Check if sender's account exists before asking for receiver's account
                        try {
                            Account senderAccount = bank.getAccount(fromAccount);

                            System.out.print("Enter receiver's account number: ");
                            String toAccount = scanner.next();
                            // Check if receiver's account exists
                            Account receiverAccount = bank.getAccount(toAccount);

                            double transferAmount = getAmount(scanner, "Enter transfer amount: ");
                            bank.transfer(fromAccount, toAccount, transferAmount);
                        } catch (AccountNotFoundException e) {
                            System.out.println(e.getMessage());  // If sender's account is not found, do not ask for receiver's account
                        }
                        break;

                    case 7:
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
/*/
    private static int getAccountType(Scanner scanner) throws Exception {
        System.out.print("Enter account type (1 for Savings, 2 for Checking): ");
        int type = scanner.nextInt();
        if (type != 1 && type != 2) {
            throw new Exception("Invalid account type.");
        }
        return type;
    }
*/
    private static String getAccountNumber(Scanner scanner) {
        System.out.print("Enter account number: ");
        return scanner.next();
    }

    private static String getAccountHolderName(Scanner scanner) {
        System.out.print("Enter account holder name: ");
        return scanner.next();
    }

    private static double getInitialBalance(Scanner scanner) throws Exception {
        System.out.print("Enter initial balance (minimum 10000): ");
        double balance = scanner.nextDouble();
        if (balance < 10000) {
            throw new Exception("Initial balance must be at least 10000.");
        }
        return balance;
    }
/*
    private static double getOverdraftLimit(Scanner scanner) {
        System.out.print("Enter overdraft limit: ");
        return scanner.nextDouble();
    }
*/

    private static double getAmount(Scanner scanner, String prompt) throws InvalidAmountException {
        System.out.print(prompt);
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero.");
        }
        return amount;
    }
}