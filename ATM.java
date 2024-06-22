/**
 * 
 */

/**
 * @author HP
 *
 */
import java.util.HashMap;
import java.util.Map;

public class ATM {
    private Map<String, BankAccount> accounts = new HashMap<>();

    public void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public boolean verifyAccount(String accountNumber, String pin) {
        BankAccount account = accounts.get(accountNumber);
        return account != null && account.getPin().equals(pin);
    }
}
