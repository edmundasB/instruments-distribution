package com.nasdaq.homework.service;

import com.nasdaq.homework.model.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:input.properties")
public class CSVDataExtractor implements DataExtractor {
    private final InputProperties properties;

    public CSVDataExtractor(InputProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<Transaction> load(Object csvSource) throws Exception {
        List<Transaction> result;
        try (CSVParser parser = new CSVParser(new FileReader((File) csvSource),
                CSVFormat.DEFAULT.withHeader(properties.getTransactionId(),
                        properties.getSourceAccount(),
                        properties.getTargetAccount(),
                        properties.getInstrument(),
                        properties.getAmount())
                        .withIgnoreHeaderCase()
                        .withTrim()
                        .withDelimiter(properties.getDelimiter())
                        .withFirstRecordAsHeader())) {

            result = new ArrayList<>();
            parser.getRecords().forEach(r -> result.add(new Transaction(r)));
        }
        return result;
    }
}
