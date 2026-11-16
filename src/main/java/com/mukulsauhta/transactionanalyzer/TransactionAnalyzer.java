package com.mukulsauhta.transactionanalyzer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *  The TransactionAnalyzer does the bulk of the work in this project; it will be the one to analyze the transactions
 *   and apply them to accounts which will enable the analysis result to be the object we return.
 * TransactionAnalyzer
 */
public class TransactionAnalyzer {
    /**
     *  Returns true when a transaction passes validation, false otherwise. Private because it's used internally
     * @param transaction should not be null, checking this transaction to see if it is valid
     * @return true or false based on details outlined above
     */
    private boolean isValid(Transaction transaction){
        if (transaction == null) {
            return false;
        }

        String accountId = transaction.getAccountId();
        if (accountId == null || accountId.isBlank()) {
            return false;
        }

        String type = transaction.getType();
        if (!"credit".equals(type) && !"debit".equals(type)) {
            return false;
        }

        BigDecimal amount = transaction.getAmount();
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * The helper that applies one valid transaction to an account summary.
     * @param summary the summary that we'll be applying the transaction to (will have its corresponding values updated
     * such as amount, highestDebt, etc.)
     * @param transaction the transaction that will determine how the sumamry is affected (amount, type of transaction, etc.)
     */
    private void applyToSummary(AccountSummary summary, Transaction transaction){
        BigDecimal amount = transaction.getAmount();

        if ("credit".equals(transaction.getType())) {
            BigDecimal updatedCredits = summary.getTotalCredits().add(amount);
            summary.setTotalCredits(updatedCredits);
        } else if ("debit".equals(transaction.getType())) {
            BigDecimal updatedDebits = summary.getTotalDebits().add(amount);
            summary.setTotalDebits(updatedDebits);
        }

        if (amount.compareTo(summary.getLargestTransaction()) > 0) {
            summary.setLargestTransaction(amount);
        }

        summary.setTransactionCount(summary.getTransactionCount() + 1);
    }

    /**
     * Analyzes transactions and returns summaries ordered by account ID.
     * Invalid transactions are ignored.
     * @param transactions the transactions to analyze
     * @return the account summaries and top spender
     */
    public AnalysisResult analyze(List<Transaction> transactions) {
        Map<String, AccountSummary> summariesByAccountId = new TreeMap<>();

        for (Transaction transaction : transactions) {
            if (!isValid(transaction)) {
                continue;
            }

            AccountSummary summary = summariesByAccountId.computeIfAbsent(
                    transaction.getAccountId(), AccountSummary::new);
            applyToSummary(summary, transaction);
        }

        String topSpender = null;
        BigDecimal highestDebits = null;
        for (AccountSummary summary : summariesByAccountId.values()) {
            BigDecimal balance = summary.getTotalCredits()
                    .subtract(summary.getTotalDebits());
            summary.setEndingBalance(balance);
            summary.setOverdrawn(balance.compareTo(BigDecimal.ZERO) < 0);

            BigDecimal totalDebits = summary.getTotalDebits();
            if (highestDebits == null
                    || totalDebits.compareTo(highestDebits) > 0
                    || (totalDebits.compareTo(highestDebits) == 0
                    && summary.getAccountId().compareTo(topSpender) < 0)) {
                highestDebits = totalDebits;
                topSpender = summary.getAccountId();
            }
        }

        return new AnalysisResult(new ArrayList<>(summariesByAccountId.values()), topSpender);
    }
}
