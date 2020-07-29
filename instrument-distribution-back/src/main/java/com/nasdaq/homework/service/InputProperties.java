package com.nasdaq.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:input.properties")
@Component
public class InputProperties {
    @Value("${input.csv.column.id}")
    private String transactionId;
    @Value("${input.csv.column.source}")
    private String sourceAccount;
    @Value("${input.csv.column.target}")
    private String targetAccount;
    @Value("${input.csv.column.instrument}")
    private String instrument;
    @Value("${input.csv.column.amount}")
    private String amount;
    @Value("${input.csv.delimiter}")
    private Character delimiter;

    public InputProperties() {
    }

    public InputProperties(String transactionId, String sourceAccount, String targetAccount, String instrument, String amount, Character delimiter) {
        this.transactionId = transactionId;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.instrument = instrument;
        this.amount = amount;
        this.delimiter = delimiter;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public String getInstrument() {
        return instrument;
    }

    public String getAmount() {
        return amount;
    }

    public Character getDelimiter() {
        return delimiter;
    }
}
