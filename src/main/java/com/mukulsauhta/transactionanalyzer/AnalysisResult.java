package com.mukulsauhta.transactionanalyzer;

import java.util.List;

/**
 * Represents the result of analyzing transactions, including account summaries
 * listed lexicographically by account ID and the top spender.
 */
public class AnalysisResult {
    private final List<AccountSummary> summaries;
    private final String topSpender;

    /**
     * Creates an analysis result from the account summaries and top spender.
     * @param summaries account summaries listed lexicographically by account ID
     * @param topSpender the top spender
     */
    public AnalysisResult(List<AccountSummary> summaries, String topSpender) {
        this.summaries = List.copyOf(summaries);
        this.topSpender = topSpender;
    }

    /**
     * Gets the account summaries, listed lexicographically by account ID.
     * @return the account summaries
     */
    public List<AccountSummary> getSummaries() {
        return summaries;
    }

    /**
     * Gets the top spender.
     * @return the top spender with the highest total debits
     */
    public String getTopSpender() {
        return topSpender;
    }
}
