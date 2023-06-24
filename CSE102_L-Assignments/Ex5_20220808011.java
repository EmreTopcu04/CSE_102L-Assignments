import java.util.ArrayList;

public class Ex5_20220808011 {
    public static void main(String[] args) throws InvalidTransactionException {

    }
}

class Account {
    private String accountNumber;
    private double balance;

    Account(String accountNumber, double balance) throws InsufficentFundException {
        this.accountNumber = accountNumber;
        if (balance < 0) {
            throw new InsufficentFundException(balance);
        } else {
            this.balance = balance;
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount) throws InvalidTransactionException {
        if (amount < 0) {
            throw new InvalidTransactionException(amount);
        } else {
            balance += amount;
        }
    }

    public void withdraw(double amount) throws InsufficentFundException, InvalidTransactionException {
        if (amount < 0) {
            throw new InvalidTransactionException(amount);
        } else if (balance < amount) {
            throw new InsufficentFundException(balance, amount);
        } else {
            balance -= amount;
        }
    }

    @Override
    public String toString() {
        return "Account: " + accountNumber + ", Balance: " + balance;
    }
}

class Customer {
    private String name;
    private ArrayList<Account> accounts = new ArrayList<>();

    Customer(String name) {
        this.name = name;
    }

    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(accountNumber)) {
                return accounts.get(i);
            }
        }
        throw new AccountNotFoundException(accountNumber);
    }

    public void addAccount(Account account) throws AccountAlreadyExistException{
        String accountNumber = account.getAccountNumber();
        try {
            getAccount(account.getAccountNumber());
            throw new AccountAlreadyExistException(accountNumber);
        } catch (AccountNotFoundException e) {
            accounts.add(account);
        } finally {
            System.out.println(this);
        }
        System.out.println("Added account: " + accountNumber + " with " + account.getBalance());
    }

    public void removeAccount(String accountNumber) {
        int index = 0;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equalsIgnoreCase(accountNumber)) {
                index = i;
            }
        }
        accounts.remove(index);
    }

    public void transfer(Account fromAccount, Account toAccount, double amount) throws InvalidTransactionException {
        try {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);

        } catch (InvalidTransactionException e) {
            throw new InvalidTransactionException(e, fromAccount + " to account " + toAccount);
        }
    }

    @Override
    public String toString() {
        String customerNumber = "";
        for (int i = 0; i < accounts.size(); i++) {
            customerNumber += "\t" + accounts.get(i) + "\n";
        }
        return "Customer " + name + ";\n" + customerNumber;
    }
}

class InsufficentFundException extends RuntimeException {
    InsufficentFundException(double balance) {
        super("Wrong balance; " + balance);

    }

    InsufficentFundException(double balance, double amount) {
        super("Required amount is " + amount + " but only " + balance + " remaining");
    }
}

class AccountAlreadyExistException extends RuntimeException {
    AccountAlreadyExistException(String accountNumber) {
        super("Account number " + accountNumber + " already exists");
    }
}

class AccountNotFoundException extends RuntimeException {
    AccountNotFoundException(String accountNumber) {
        super("Account number " + accountNumber + " already exists");

    }
}

class InvalidTransactionException extends Exception {
    InvalidTransactionException(double amount) {
        super("Invalid amount: " + amount);
    }

    InvalidTransactionException(Exception e, String message) {
        super(message + ":\n\t" + e.getMessage());
    }
}