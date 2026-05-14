package org.example.Services;

import org.example.Models.*;

import java.util.*;

/*
   To-do
   1. Handler for getting account details
   2. Add account
   3. Delete account
   4. Edit account details
   5. View account details
   6. Search account
 */
public class AccountService {
    private List<AccountEntry> accountList = new ArrayList<>();

    public void addBankAccount(int accountNumber, String pin) {
        AccountEntry e = new BankAccount(accountNumber, pin);
        accountList.add(e);
    }

    public void addEmailAccount(String email, String password) {
        AccountEntry e = new EmailAccount(email, password);
        accountList.add(e);
    }

    public void addSocialAccount(String username, String platform, String password) {
        AccountEntry e = new SocialAccount(username, platform, password);
        accountList.add(e);
    }

    public void deleteAccount(AccountEntry e) {
        accountList.remove(e);
    }

    public void editEmailAccount(EmailAccount e, String email, String password) {
        e.setEmail(email);
        e.setPassword(password);
    }

    public void editBankAccount(BankAccount e, int bankNumber, String pin) {
        e.setBankNumber(bankNumber);
        e.setPin(pin);
    }

    public void editSocialAccount(SocialAccount e, String username, String platform, String password) {
        e.setPlatform(platform);
        e.setPassword(password);
        e.setUsername(username);
    }

    public void viewSocialAccount(SocialAccount e) {
        System.out.printf("""
                Account details:
                Username: %s
                Platform: %s
                Password: %s
                """, e.getUsername(), e.getPlatform(), e.getKey());
    }

    public void viewEmailAccount(EmailAccount e) {
        System.out.printf("""
                Account details:
                Email: %s
                Password: %s
                """, e.getEmail(), e.getPassword());
    }

    public void viewBankAccount(BankAccount e) {
        System.out.printf("""
                Account details:
                Bank Number: %s
                Pin: %s
                """, e.getBankNumber(), e.getPin());
    }

    public void searchAccount(String query) {
        String q = query.toLowerCase().trim();

        List<AccountEntry> results = accountList.stream()
                .filter(e -> {
                    if (e instanceof EmailAccount email) {
                        return email.getEmail().toLowerCase().contains(q);
                    } else if (e instanceof BankAccount bank) {
                        return String.valueOf(bank.getBankNumber()).contains(q);
                    } else if (e instanceof SocialAccount social) {
                        return social.getUsername().toLowerCase().contains(q) ||
                                social.getPlatform().toLowerCase().contains(q);
                    }
                    return false;
                })
                .toList();

        if (results.isEmpty()) {
            System.out.println("Sorry... we did not find anything related to that query.");
            return;
        }

        System.out.println("Search results for: \"" + query + "\"");
        System.out.println("=".repeat(40));

        for (AccountEntry e : results) {
            if (e instanceof EmailAccount email) {
                viewEmailAccount(email);
            } else if (e instanceof BankAccount bank) {
                viewBankAccount(bank);
            } else if (e instanceof SocialAccount social) {
                viewSocialAccount(social);
            }
            System.out.println("-".repeat(40));
        }

        System.out.println("Total: " + results.size() + " result(s) found.");
    }
}