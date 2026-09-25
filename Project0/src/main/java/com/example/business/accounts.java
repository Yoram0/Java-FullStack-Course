package com.example.business;
import com.example.api.*;
import com.example.repo.Accounthandler;
import com.example.repo.Transaction;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class accounts {
    private Accounthandler accountHandler;
    private static Logger logger = LoggerFactory.getLogger(accounts.class);
    //this method will only dealing with vaildating the data from the repo layer to ensure everything is in check
    public accounts() {
        this.accountHandler = new Accounthandler();
    }

    public accounts(Accounthandler accountHandler) {
        this.accountHandler = accountHandler;
    }

    public int createAccount(String pin)
    {
        if(pin == null)
        {
            logger.warn("Pin cannot be null");
            return -1;
        }
        int accountPin = accountHandler.createAccountpin(pin);
        logger.info("Account was created returned pin is: " + accountPin);
        return accountPin;

    }
    // this method conneccts with another method in the repo layer
    // first thing this method does is check if amount and if pin is valid
    // if both of those are true/valid then depoist will call another method to then depoist a certain amount based on the account id
    public boolean depoist(String pin, BigDecimal amount, int accountId)
    {
        if(amount == null)
        {
            System.out.println("amount cannot not be null");
            return false;
        }
        else if(amount.compareTo(BigDecimal.ZERO) < 0)
        {
            System.out.println("Amount cannot be negative");
            return false;
        }
        if(accountHandler.validatePin(accountId,pin))
        {
            accountHandler.depoist(accountId, amount);
            logger.info("Account {} authenticated successfully", accountId);
            logger.info("Depoist successful for account {}. Amount {}", accountId, amount);
            return true;
        }
        else
        {
            System.out.println("Pin is invalid");
            return false;
        }
    }
    public BigDecimal getBalance(int accountID, String pin)
    {
        if(accountHandler.validatePin(accountID, pin))
        {
            return accountHandler.getBalance(accountID);
        }
        else {
            return null;
        }
    }
    public boolean withdraw(int accountID, String pin, BigDecimal amount)
    {
        boolean validPin = accountHandler.validatePin(accountID, pin);

        if(amount == null)
        {
            System.out.println("Amount cannot be null");
            return false;
        }
        if(amount.signum() == -1) //signum returns -1 if the value is negative
        {
            System.out.println("Amount cannot be num");
            return false;
        }
        if(!validPin)
        {
            System.out.println("Not a valid pin or accountID");
            return false;
        }
        BigDecimal balanceAmount = accountHandler.getBalance(accountID);
        if(balanceAmount.compareTo(amount) < 0)
        {
            System.out.println("Insuffcient funds");
            return false;
        }
        return accountHandler.withdraw(accountID, pin, amount);
    }
    public boolean transfer(int senderID, String pin, int receiverID, BigDecimal amount)
    {
        if(senderID == receiverID)
        {
            System.out.println("Cannot transfer money to your own acount");
            logger.warn("Sender ID and receiver ID cannot be the same sender: " + senderID + " Recevier: " + receiverID);
            return false;
        }
        if(!accountHandler.validatePin(senderID, pin))
        {
            System.out.println("Pin is not valid");
            return false;
        }
        if(amount == null)
        {
                System.out.println("Amount can not be null");
                return false;
        }
        if(amount.signum() <= 0)
        {
            System.out.println("Amount can not be a negative number");
            return false;
        }
        BigDecimal senderBalance = accountHandler.getBalance(senderID);

        if (senderBalance.compareTo(amount) < 0) {
        System.out.println("Insufficient funds");
        return false;
}
        if(!accountHandler.validAccountID(receiverID))
        {
            System.out.println("Not a valid account to transfer to");
            return false;
        }
        return accountHandler.transfer(senderID, pin, receiverID, amount);
    }

    public boolean login(int accountID, String pin)
    {
        boolean ifLogin = accountHandler.validatePin(accountID, pin);

        if(ifLogin)
            return true;
        else 
            System.out.println("Invalid pin or ID");
            return false;
    }
    public List<Transaction> getHistory(int accountId, String pin) 
    {
        if (!accountHandler.validatePin(accountId, pin)) {
            System.out.println("Invalid credentials.");
            return Collections.emptyList();
        }
        return accountHandler.getTransactionHistory(accountId);
    }
}
