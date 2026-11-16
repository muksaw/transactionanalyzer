package com.mukulsauhta.transactionanalyzer;

import java.math.BigDecimal;

/**
 *  This class represents a transaction that a user makes regarding their account balance, either depositing or withdrawing money
 * Transaction
 */
public class Transaction {

    private final String accountId;
    private final String type;
    private final BigDecimal amount;
    private final String timestamp;

    /**
     *  A transaction is represented by its fields, where it won't be  edited once created. This is why we don't have setters
     * @param accountId represents the accountID the transaction is being applied to
     * @param type is the type of transaction, either debit or credit
     * @param amount is the amount of money that the account is performing the transaction with
     * @param timestamp is the time at which the transaction occurred.
     */
    public Transaction(String accountId, String type,
                       BigDecimal amount, String timestamp) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    /**
     * @return returns the account id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 
     * @return returns the type of transaction we're working with
     */
    public String getType(){
        return type;
    }

    /**
     * 
     * @return returns the amount that the user was either putting in or taking out
     */
    public BigDecimal getAmount(){
        return amount;
    }

    /**
     * 
     * @return the timestamp representing the time of the transaction
     */
    public String getTimestamp(){
        return timestamp;
    }
}