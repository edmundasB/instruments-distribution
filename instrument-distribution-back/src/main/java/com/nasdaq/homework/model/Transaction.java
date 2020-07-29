package com.nasdaq.homework.model;

import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;

public class Transaction {
    private final String id;
    private final String accountFrom;
    private final String accountTo;
    private final String instrument;
    private final BigDecimal amount;

    public Transaction(CSVRecord record) {
        this.id = record.get(0);
        this.accountFrom = record.get(1);
        this.accountTo = record.get(2);
        this.instrument = record.get(3);
        this.amount = new BigDecimal(record.get(4));
    }

    public String getInstrument() {
        return instrument;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }
}
