package org.example.Models;

public class BankAccount extends AccountEntry implements Encryptable{
    private int BankNumber;
    private String pin;

    public String getPin() {
        return pin;
    }


    public void setPin(String pin) {
        this.pin = pin;
    }


    public BankAccount(int BankNumber, String pin) {
        this.BankNumber = BankNumber;
        this.pin = pin;
    }


    @Override
    public String getKey() {
        return pin;
    }

    @Override
    public String toString() {
        return String.format("%s : %s", BankNumber, pin);
    }

    public int getBankNumber() {
        return BankNumber;
    }

    public void setBankNumber(int bankNumber) {
        BankNumber = bankNumber;
    }
}
