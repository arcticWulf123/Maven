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

    public void searchAccount() {
        for (AccountEntry e : accountList) {
            //TODO
        }
    }

}