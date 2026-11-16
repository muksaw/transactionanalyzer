package com.mukulsauhta.transactionanalyzer;

import java.math.BigDecimal;

public class AccountSummary {

    private final String accountId;
    private BigDecimal totalCredits;
    private BigDecimal totalDebits;
    private BigDecimal endingBalance;
    private BigDecimal largestTransaction;
    private int transactionCount;
    private boolean overdrawn;

    public AccountSummary(String accountId) {
        this.accountId = accountId;
        this.totalCredits = BigDecimal.ZERO;
        this.totalDebits = BigDecimal.ZERO;
        this.endingBalance = BigDecimal.ZERO;
        this.largestTransaction = BigDecimal.ZERO;
        this.transactionCount = 0;
        this.overdrawn = false;
    }

    public BigDecimal getTotalCredits() {
    return totalCredits;
}

    public void setTotalCredits(BigDecimal totalCredits) {
        this.totalCredits = totalCredits;
    }

    
}