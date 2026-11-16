package com.mukulsauhta.transactionanalyzer;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Transaction> transactions = List.of(
                new Transaction("B200", "debit", new BigDecimal("50.25"),
                        "2026-05-10T11:00:00Z"),
                new Transaction("A100", "debit", new BigDecimal("200.05"),
                        "2026-05-10T12:00:00Z"),
                new Transaction("B200", "credit", new BigDecimal("20.10"),
                        "2026-05-10T13:00:00Z"),
                new Transaction("A100", "credit", new BigDecimal("500.10"),
                        "2026-05-10T10:00:00Z")
        );

        TransactionAnalyzer analyzer = new TransactionAnalyzer();
        AnalysisResult result = analyzer.analyze(transactions);

        for (AccountSummary summary : result.getSummaries()) {
            System.out.printf(
                    "%s | credits=%s | debits=%s | balance=%s | "
                            + "largest=%s | count=%d | overdrawn=%b%n",
                    summary.getAccountId(),
                    summary.getTotalCredits(),
                    summary.getTotalDebits(),
                    summary.getEndingBalance(),
                    summary.getLargestTransaction(),
                    summary.getTransactionCount(),
                    summary.isOverdrawn()
            );
        }

        System.out.println("Top spender: " + result.getTopSpender());
    }
}
