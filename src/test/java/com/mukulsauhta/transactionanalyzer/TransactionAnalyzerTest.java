package com.mukulsauhta.transactionanalyzer;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionAnalyzerTest {

    @Test
    void returnsEmptyResultWhenInputIsEmpty() {
        TransactionAnalyzer analyzer = new TransactionAnalyzer();

        AnalysisResult result = analyzer.analyze(List.of());

        assertTrue(result.getSummaries().isEmpty());
        assertNull(result.getTopSpender());
    }

    @Test
    void summarizesMultipleAccountsWithUnsortedTransactions() {
        TransactionAnalyzer analyzer = new TransactionAnalyzer();

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

        AnalysisResult result = analyzer.analyze(transactions);

        assertEquals(2, result.getSummaries().size());
        assertEquals("A100", result.getTopSpender());

        AccountSummary a100 = result.getSummaries().get(0);
        AccountSummary b200 = result.getSummaries().get(1);

        assertEquals("A100", a100.getAccountId());
        assertEquals("B200", b200.getAccountId());
        assertEquals(0, new BigDecimal("500.10").compareTo(a100.getTotalCredits()));
        assertEquals(0, new BigDecimal("200.05").compareTo(a100.getTotalDebits()));
        assertEquals(0, new BigDecimal("300.05").compareTo(a100.getEndingBalance()));
        assertEquals(0, new BigDecimal("500.10").compareTo(a100.getLargestTransaction()));
        assertEquals(2, a100.getTransactionCount());
        assertFalse(a100.isOverdrawn());
        assertEquals(0, new BigDecimal("20.10").compareTo(b200.getTotalCredits()));
        assertEquals(0, new BigDecimal("50.25").compareTo(b200.getTotalDebits()));
        assertEquals(0, new BigDecimal("-30.15").compareTo(b200.getEndingBalance()));
        assertEquals(0, new BigDecimal("50.25").compareTo(b200.getLargestTransaction()));
        assertEquals(2, b200.getTransactionCount());
        assertTrue(b200.isOverdrawn());
    }

    @Test
    void ignoresInvalidTransactions() {
        TransactionAnalyzer analyzer = new TransactionAnalyzer();
        List<Transaction> transactions = new ArrayList<>();
        String timestamp = "2026-05-10T10:00:00Z";

        transactions.add(new Transaction("A100", "credit", new BigDecimal("100"), timestamp));
        transactions.add(new Transaction(null, "credit", new BigDecimal("999"), timestamp));
        transactions.add(new Transaction("", "credit", new BigDecimal("999"), timestamp));
        transactions.add(new Transaction("   ", "debit", new BigDecimal("999"), timestamp));
        transactions.add(new Transaction("Z900", "refund", new BigDecimal("999"), timestamp));
        transactions.add(new Transaction("A100", null, new BigDecimal("999"), timestamp));
        transactions.add(new Transaction("A100", "credit", null, timestamp));
        transactions.add(new Transaction("A100", "credit", BigDecimal.ZERO, timestamp));
        transactions.add(new Transaction("A100", "debit", new BigDecimal("-10"), timestamp));
        transactions.add(null);

        AnalysisResult result = analyzer.analyze(transactions);

        assertEquals(1, result.getSummaries().size());
        assertEquals("A100", result.getTopSpender());

        AccountSummary a100 = result.getSummaries().get(0);
        assertEquals("A100", a100.getAccountId());
        assertEquals(0, new BigDecimal("100").compareTo(a100.getTotalCredits()));
        assertEquals(0, BigDecimal.ZERO.compareTo(a100.getTotalDebits()));
        assertEquals(0, new BigDecimal("100").compareTo(a100.getEndingBalance()));
        assertEquals(0, new BigDecimal("100").compareTo(a100.getLargestTransaction()));
        assertEquals(1, a100.getTransactionCount());
        assertFalse(a100.isOverdrawn());
    }

    @Test
    void choosesLexicographicallySmallerAccountWhenDebitsTie() {
        TransactionAnalyzer analyzer = new TransactionAnalyzer();
        List<Transaction> transactions = List.of(
                new Transaction("B200", "debit", new BigDecimal("50.00"),
                        "2026-05-10T10:00:00Z"),
                new Transaction("A100", "debit", new BigDecimal("50.0"),
                        "2026-05-10T10:00:00Z")
        );

        AnalysisResult result = analyzer.analyze(transactions);

        assertEquals("A100", result.getTopSpender());
    }

    @Test
    void returnsEmptyResultWhenAllTransactionsAreInvalid() {
        TransactionAnalyzer analyzer = new TransactionAnalyzer();
        List<Transaction> transactions = List.of(
                new Transaction("A100", "credit", BigDecimal.ZERO,
                        "2026-05-10T10:00:00Z"),
                new Transaction("B200", "refund", new BigDecimal("999"),
                        "2026-05-10T11:00:00Z")
        );

        AnalysisResult result = analyzer.analyze(transactions);

        assertTrue(result.getSummaries().isEmpty());
        assertNull(result.getTopSpender());
    }

    @Test
    void zeroEndingBalanceIsNotOverdrawn() {
        TransactionAnalyzer analyzer = new TransactionAnalyzer();
        List<Transaction> transactions = List.of(
                new Transaction("A100", "debit", new BigDecimal("25.50"),
                        "2026-05-10T10:00:00Z"),
                new Transaction("A100", "credit", new BigDecimal("25.50"),
                        "2026-05-10T11:00:00Z")
        );

        AnalysisResult result = analyzer.analyze(transactions);
        AccountSummary a100 = result.getSummaries().get(0);

        assertEquals(0, BigDecimal.ZERO.compareTo(a100.getEndingBalance()));
        assertFalse(a100.isOverdrawn());
    }

    @Test
    void choosesSmallestAccountIdWhenAllAccountsHaveOnlyCredits() {
        TransactionAnalyzer analyzer = new TransactionAnalyzer();
        List<Transaction> transactions = List.of(
                new Transaction("B200", "credit", new BigDecimal("500"),
                        "2026-05-10T10:00:00Z"),
                new Transaction("A100", "credit", new BigDecimal("10"),
                        "2026-05-10T11:00:00Z")
        );

        AnalysisResult result = analyzer.analyze(transactions);

        assertEquals("A100", result.getTopSpender());
        assertEquals(0, BigDecimal.ZERO.compareTo(result.getSummaries().get(0).getTotalDebits()));
        assertEquals(0, BigDecimal.ZERO.compareTo(result.getSummaries().get(1).getTotalDebits()));
    }
}