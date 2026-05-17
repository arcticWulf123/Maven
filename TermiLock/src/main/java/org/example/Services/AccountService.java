package org.example.Services;

import org.example.Models.*;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class AccountService {
    private List<AccountEntry> accountList = new ArrayList<>();

    public synchronized void generatePassword(Socket socket, PrintWriter out, BufferedReader in) {
        PasswordGenerator passwordGenerator = new PasswordGenerator();

        while (true) {
            try {
                out.println("Enter password length: ");
                out.println("END");

                int length = Integer.parseInt(in.readLine());

                out.println("Generated password: " + passwordGenerator.generate(length));
                out.println("Generate another password? (y/n): ");
                out.println("END");

                String choice = in.readLine();

                if (choice.equalsIgnoreCase("n")) {
                    out.println("Returning to menu...");
                    break;
                }

            } catch (IOException e) {
                System.out.println("Connection lost... Cannot generate password");
                break;

            } catch (NumberFormatException e) {
                out.println("Invalid length. Please enter a number.");
                continue;
            }
        }
    }

    public synchronized void viewAccountEntries(Socket socket, PrintWriter out, BufferedReader in, User u) {
        while (true) {
            try {
                out.println("""
                        [1] Bank Accounts
                        [2] Email Accounts
                        [3] Social Accounts
                        [4] Back
                        Choice:
                        """);
                out.println("END");

                int choice = Integer.parseInt(in.readLine());

                List<AccountEntry> accounts = u.getAccounts();

                switch (choice) {
                    case 1:
                        accounts.stream()
                                .filter(e -> e instanceof BankAccount)
                                .forEach(e -> viewBankAccount(out, (BankAccount) e));

                        break;
                    case 2:
                        accounts.stream()
                                .filter(e -> e instanceof EmailAccount)
                                .forEach(e -> viewEmailAccount(out, (EmailAccount) e));

                        break;
                    case 3:
                        accounts.stream()
                                .filter(e -> e instanceof SocialAccount)
                                .forEach(e -> viewSocialAccount(out, (SocialAccount) e));

                        break;
                    case 4:
                        out.println("Returning to menu...");

                        return;
                    default:
                        out.println("Invalid choice.");

                        break;
                }

            } catch (IOException e) {
                System.out.println("Error! Could not load your account entries...");
                break;
            }
        }
    }

    /*
     * 1. Update working
     * 2. Generator working
     * 3. Add working
     * 4.
     */
    public synchronized void deleteAccountEntry(Socket socket, PrintWriter out, BufferedReader in, User u) {
        String yesOrNo = "";
        while (true) {
            try {
                out.println("""
                        [1] Bank Accounts
                        [2] Email Accounts
                        [3] Social Accounts
                        [4] Back
                        Choice:
                        """);
                out.println("END");
                int choice = Integer.parseInt(in.readLine());
                switch (choice) {
                    case 1:
                        deleteBankAccount(accountList, out, in, u);
                        out.println("Delete another? (y/n): ");
                        out.println("END");
                        yesOrNo = in.readLine();
                        if (yesOrNo.equalsIgnoreCase("y")) {
                            deleteBankAccount(accountList, out, in, u);
                        } else {
                            return;
                        }
                        continue;
                    case 2:
                        deleteEmailAccount(getEmailAccounts(u), out, in, u);
                        out.println("Delete another? (y/n): ");
                        out.println("END");
                        yesOrNo = in.readLine();
                        if (yesOrNo.equalsIgnoreCase("y")) {
                            deleteEmailAccount(accountList, out, in, u);
                        } else {
                            return;
                        }
                        continue;
                    case 3:
                        deleteSocialAccount(getSocialAccounts(u), out, in, u);
                        out.println("Delete another? (y/n): ");
                        out.println("END");
                        yesOrNo = in.readLine();
                        if (yesOrNo.equalsIgnoreCase("y")) {
                            deleteSocialAccount(accountList, out, in, u);
                        } else {
                            return;
                        }
                        continue;
                    case 4:
                        out.println("Returning to menu...");
                        out.println("END");
                        return;
                    default:
                        out.println("Invalid choice.");
                        out.println("END");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error! Could not delete account entry...");
                break;
            }
        }
    }

    public synchronized void deleteBankAccount(List<AccountEntry> accounts, PrintWriter out, BufferedReader in,
            User u) {
        try {
            while (true) {
                accounts = getBankAccounts(u); // ← refresh list each loop

                if (accounts.isEmpty()) {
                    out.println("No bank accounts found.");
                    return;
                }

                for (int i = 0; i < accounts.size(); i++) {
                    out.println("[" + i + "] " + accounts.get(i));
                }
                out.println("Enter index to delete: ");
                out.println("END");

                int index = Integer.parseInt(in.readLine());

                if (index >= 0 && index < accounts.size()) {
                    u.getAccounts().remove(accounts.get(index));
                    out.println("Account deleted.");
                    break;
                } else {
                    out.println("Invalid index.");
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not delete bank account...");
        }
    }

    public synchronized void deleteEmailAccount(List<AccountEntry> accounts, PrintWriter out, BufferedReader in,
            User u) {
        try {
            while (true) {
                accounts = getEmailAccounts(u);

                if (accounts.isEmpty()) {
                    out.println("No email accounts found.");
                    return;
                }

                for (int i = 0; i < accounts.size(); i++) {
                    out.println("[" + i + "] " + accounts.get(i));
                }
                out.println("Enter index to delete: ");
                out.println("END");

                int index = Integer.parseInt(in.readLine());

                if (index >= 0 && index < accounts.size()) {
                    u.getAccounts().remove(accounts.get(index));
                    out.println("Account deleted.");
                    break;
                } else {
                    out.println("Invalid index.");
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not delete email account...");
        }
    }

    public synchronized void deleteSocialAccount(List<AccountEntry> accounts, PrintWriter out, BufferedReader in,
            User u) {
        try {
            while (true) {
                accounts = getSocialAccounts(u);

                if (accounts.isEmpty()) {
                    out.println("No social accounts found.");
                    return;
                }

                for (int i = 0; i < accounts.size(); i++) {
                    out.println("[" + i + "] " + accounts.get(i));
                }
                out.println("Enter index to delete: ");
                out.println("END");

                int index = Integer.parseInt(in.readLine());

                if (index >= 0 && index < accounts.size()) {
                    u.getAccounts().remove(accounts.get(index));
                    out.println("Account deleted.");
                    break;
                } else {
                    out.println("Invalid index.");
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not delete social account...");
        }
    }

    public synchronized List<AccountEntry> getBankAccounts(User u) {
        return u.getAccounts().stream()
                .filter(e -> e instanceof BankAccount)
                .toList();
    }

    public synchronized List<AccountEntry> getEmailAccounts(User u) {
        return u.getAccounts().stream()
                .filter(e -> e instanceof EmailAccount)
                .toList();
    }

    public synchronized List<AccountEntry> getSocialAccounts(User u) {
        return u.getAccounts().stream()
                .filter(e -> e instanceof SocialAccount)
                .toList();
    }

    public synchronized void updateAccountEntry(Socket socket, PrintWriter out, BufferedReader in, User u) {
        while (true) {
            try {
                out.println("""
                        [1] Bank Accounts
                        [2] Email Accounts
                        [3] Social Accounts
                        [4] Back
                        Choice:
                        """);
                out.println("END");

                int choice = Integer.parseInt(in.readLine());

                switch (choice) {
                    case 1:
                        updateBankAccount(getBankAccounts(u), out, in, u);
                        break;
                    case 2:
                        updateEmailAccount(getEmailAccounts(u), out, in, u);
                        break;
                    case 3:
                        updateSocialAccount(getSocialAccounts(u), out, in, u);
                        break;
                    case 4:
                        out.println("Returning to menu...");
                        return;
                    default:
                        out.println("Invalid choice.");
                        continue;
                }

            } catch (IOException e) {
                System.out.println("Error! Could not update account entry...");
                break;
            }
        }
    }

    public synchronized void updateBankAccount(List<AccountEntry> accounts, PrintWriter out, BufferedReader in,
            User u) {
        try {
            if (accounts.isEmpty()) {
                out.println("No bank accounts found.");
                return;
            }

            for (int i = 0; i < accounts.size(); i++) {
                out.println("[" + (i) + "] " + accounts.get(i));
            }
            out.println("Enter index to edit: ");
            out.println("END");

            int index = Integer.parseInt(in.readLine());

            if (index >= 0 && index <= accounts.size()) {
                BankAccount target = (BankAccount) accounts.get(index);

                out.println("Enter new bank number: ");
                out.println("END");
                int newBankNumber = Integer.parseInt(in.readLine());

                out.println("Enter new pin: ");
                out.println("END");
                String newPin = in.readLine();

                editBankAccount(target, newBankNumber, newPin);

                out.println("Bank account updated.");
                return;
            } else {
                out.println("Invalid index.");
            }

        } catch (IOException e) {
            System.out.println("Could not update bank account...");
        }
    }

    public synchronized void updateEmailAccount(List<AccountEntry> accounts, PrintWriter out, BufferedReader in,
            User u) {
        try {
            if (accounts.isEmpty()) {
                out.println("No email accounts found.");
                return;
            }

            for (int i = 0; i < accounts.size(); i++) {
                out.println("[" + (i) + "] " + accounts.get(i));
            }
            out.println("Enter index to edit: ");
            out.println("END");

            int index = Integer.parseInt(in.readLine());

            if (index >= 0 && index <= accounts.size()) {
                EmailAccount target = (EmailAccount) accounts.get(index);

                out.println("Enter new email: ");
                out.println("END");
                String newEmail = in.readLine();

                out.println("Enter new password: ");
                out.println("END");
                String newPassword = in.readLine();

                editEmailAccount(target, newEmail, newPassword);

                out.println("Email account updated.");
            } else {
                out.println("Invalid index.");
            }

        } catch (IOException e) {
            System.out.println("Could not update email account...");
        }
    }

    public synchronized void updateSocialAccount(List<AccountEntry> accounts, PrintWriter out, BufferedReader in,
            User u) {
        try {
            if (accounts.isEmpty()) {
                out.println("No social accounts found.");
                return;
            }

            for (int i = 0; i < accounts.size(); i++) {
                out.println("[" + (i) + "] " + accounts.get(i));
            }
            out.println("Enter index to edit: ");
            out.println("END");

            int index = Integer.parseInt(in.readLine());

            if (index >= 0 && index <= accounts.size()) {
                SocialAccount target = (SocialAccount) accounts.get(index);

                out.println("Enter new username: ");
                out.println("END");
                String newUsername = in.readLine();

                out.println("Enter new platform: ");
                out.println("END");
                String newPlatform = in.readLine();

                out.println("Enter new password: ");
                out.println("END");
                String newPassword = in.readLine();

                editSocialAccount(target, newUsername, newPlatform, newPassword);

                out.println("Social account updated.");
            } else {
                out.println("Invalid index.");
            }

        } catch (IOException e) {
            System.out.println("Could not update social account...");
        }
    }

    public synchronized void addAccountEntry(Socket socket, PrintWriter out, BufferedReader in, User u) {
        while (true) {
            try {
                out.println("""
                        [1] Bank Account
                        [2] Email Account
                        [3] Social Account
                        [4] Back
                        Choice:
                        """);
                out.println("END");
                int choice = Integer.parseInt(in.readLine());
                switch (choice) {
                    case 1:
                        out.println("Enter bank number: ");
                        out.println("END");
                        int banknumber = Integer.parseInt(in.readLine());
                        out.println("Enter pin: ");
                        out.println("END");
                        String pin = in.readLine();
                        BankAccount a = new BankAccount(banknumber, pin);
                        u.addAccountEntry(a);
                        break;
                    case 2:
                        out.println("Enter email address: ");
                        out.println("END");
                        String email = in.readLine();
                        out.println("Enter password: ");
                        out.println("END");
                        String password = in.readLine();
                        EmailAccount e = new EmailAccount(email, password);
                        u.addAccountEntry(e);
                        break;
                    case 3:
                        out.println("Enter platform");
                        out.println("END");
                        String platform = in.readLine();
                        out.println("Enter username: ");
                        out.println("END");
                        String username = in.readLine();
                        out.println("Enter password: ");
                        out.println("END");
                        password = in.readLine();
                        SocialAccount s = new SocialAccount(username, platform, password);
                        u.addAccountEntry(s);
                        break;
                    case 4:
                        out.println("Returning to menu...");
                        return;
                    default:
                        break;
                }
                out.println("Add another account? (y/n): ");
                out.println("END");
                String response = in.readLine();
                if (response.equalsIgnoreCase("n")) {
                    out.println("Returning to menu...");
                    return;
                }
            } catch (IOException e) {
                System.out.println("Error! could not add account...");
            }
        }
    }

    public synchronized void searchAccount(PrintWriter out, BufferedReader in, User u) {
        while (true) {
            try {
                out.println("""
                        [1] Search
                        [2] Back
                        Choice:
                        """);
                out.println("END");

                int choice = Integer.parseInt(in.readLine());

                switch (choice) {
                    case 1:
                        out.println("Enter search query: ");
                        out.println("END");

                        String query = in.readLine();
                        String q = query.toLowerCase().trim();

                        List<AccountEntry> results = u.getAccounts().stream()
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
                            out.println("No results found for: \"" + query + "\"");
                            break;
                        }

                        out.println("Search results for: \"" + query + "\"");
                        out.println("=".repeat(40));

                        for (AccountEntry e : results) {
                            if (e instanceof EmailAccount email) {
                                viewEmailAccount(out, email);
                            } else if (e instanceof BankAccount bank) {
                                viewBankAccount(out, bank);
                            } else if (e instanceof SocialAccount social) {
                                viewSocialAccount(out, social);
                            }
                            out.println("-".repeat(40));
                        }

                        out.println("Total: " + results.size() + " result(s) found.");
                        break;

                    case 2:
                        out.println("Returning to menu...");
                        return;

                    default:
                        out.println("Invalid choice.");
                        continue;
                }

            } catch (IOException e) {
                System.out.println("Error! Could not perform search...");
                break;
            }
        }
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

    public void viewSocialAccount(PrintWriter out, SocialAccount e) {
        out.println(String.format("""
                Account details:
                Username: %s
                Platform: %s
                Password: %s
                """, e.getUsername(), e.getPlatform(), e.getKey()));
    }

    public void viewEmailAccount(PrintWriter out, EmailAccount e) {
        out.println(String.format("""
                Account details:
                Email: %s
                Password: %s
                """, e.getEmail(), e.getPassword()));
    }

    public void viewBankAccount(PrintWriter out, BankAccount e) {
        out.println(String.format("""
                Account details:
                Bank Number: %s
                Pin: %s
                """, e.getBankNumber(), e.getPin()));
    }
}