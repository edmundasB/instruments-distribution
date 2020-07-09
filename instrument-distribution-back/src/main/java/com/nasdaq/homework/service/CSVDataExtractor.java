package com.nasdaq.homework.service;

import com.nasdaq.homework.model.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVDataExtractor implements DataExtractor {
    @Override
    public List<Transaction> load(Object csvSource) throws Exception {

        CSVParser parser = new CSVParser(new FileReader( (File) csvSource),
                CSVFormat.DEFAULT.withHeader("Transakcijos ID",
                        "Šaltinio sąskaita",
                        "Gavėjo sąskaita",
                        "Instrumentas",
                        "Pervedimo suma")
                        .withIgnoreHeaderCase()
                        .withTrim()
                        .withDelimiter(';')
                        .withFirstRecordAsHeader());

        List<Transaction> result = new ArrayList<>();
        parser.getRecords().forEach(r -> result.add(new Transaction(r)));

        return result;
    }
}
