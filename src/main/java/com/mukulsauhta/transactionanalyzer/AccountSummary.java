package com.mukulsauhta.transactionanalyzer;

import java.math.BigDecimal;

/**
 * This class represents the active summary for a user's account, including their information such as total credits, account id, total
 *  debits, ending balance, largest transaction, valid transaction counts, being overdrawn, etc.
 * AccountSummary
 */
public class AccountSummary {

    private final String accountId;
    private BigDecimal totalCredits;
    private BigDecimal totalDebits;
    private BigDecimal endingBalance;
    private BigDecimal largestTransaction;
    private int transactionCount;
    private boolean overdrawn;

    /**
     * The constructor for account summary which creates the summary for an ID and initializes the transactional values to 0 so they can be updated based
     *  on future transactions
     * @param accountId
     */
    public AccountSummary(String accountId) {
        this.accountId = accountId;
        this.totalCredits = BigDecimal.ZERO;
        this.totalDebits = BigDecimal.ZERO;
        this.endingBalance = BigDecimal.ZERO;
        this.largestTransaction = BigDecimal.ZERO;
        this.transactionCount = 0;
        this.overdrawn = false;
    }

    /**
     * 
     * @return the total credits for this account id
     */
    public BigDecimal getTotalCredits() {
        return totalCredits;
    }

    /**
     *  Set the total credits to a value based on transactions
     * @param totalCredits
     */
    public void setTotalCredits(BigDecimal totalCredits) {
        this.totalCredits = totalCredits;
    }

    /**
     * Gets the account ID for this summary.
     * @return the account ID
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Gets the total debits for this account.
     * @return the total debits
     */
    public BigDecimal getTotalDebits() {
        return totalDebits;
    }

    /**
     * Sets the total debits for this account.
     * @param totalDebits the total debits
     */
    public void setTotalDebits(BigDecimal totalDebits) {
        this.totalDebits = totalDebits;
    }

    /**
     * Gets the ending balance for this account.
     * @return the ending balance
     */
    public BigDecimal getEndingBalance() {
        return endingBalance;
    }

    /**
     * Sets the ending balance for this account.
     * @param endingBalance the ending balance
     */
    public void setEndingBalance(BigDecimal endingBalance) {
        this.endingBalance = endingBalance;
    }

    /**
     * Gets the largest transaction for this account.
     * @return the largest transaction
     */
    public BigDecimal getLargestTransaction() {
        return largestTransaction;
    }

    /**
     * Sets the largest transaction for this account.
     * @param largestTransaction the largest transaction
     */
    public void setLargestTransaction(BigDecimal largestTransaction) {
        this.largestTransaction = largestTransaction;
    }

    /**
     * Gets the transaction count for this account.
     * @return the transaction count
     */
    public int getTransactionCount() {
        return transactionCount;
    }

    /**
     * Sets the transaction count for this account.
     * @param transactionCount the transaction count
     */
    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    /**
     * Checks whether this account is overdrawn.
     * @return true if the account is overdrawn; false otherwise
     */
    public boolean isOverdrawn() {
        return overdrawn;
    }

    /**
     * Sets whether this account is overdrawn.
     * @param overdrawn true if the account is overdrawn; false otherwise
     */
    public void setOverdrawn(boolean overdrawn) {
        this.overdrawn = overdrawn;
    }

}