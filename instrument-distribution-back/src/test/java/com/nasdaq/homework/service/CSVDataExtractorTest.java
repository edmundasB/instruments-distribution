package com.nasdaq.homework.service;

import com.nasdaq.homework.model.ExtractorType;
import com.nasdaq.homework.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@SpringBootTest
class CSVDataExtractorTest {
    @Autowired
    public DataExtractorFactory factory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadTransactionsFromCSV() throws Exception {
        DataExtractor csvDataExtractor = factory.getDataExtractor(ExtractorType.CSV);
        File inputFile =  new File(getClass().getResource("../test_data.csv").toURI());
        List<Transaction> transactions = csvDataExtractor.load(inputFile);

        Assertions.assertNotNull(transactions, "Failed to load transactions from CSV.");
        Assertions.assertEquals(9, transactions.size(), "Not all transactions were loaded.");
    }

    @Test
    public void loadTransactionFromCSVThenDataNotCorrect() throws Exception {
        DataExtractor csvDataExtractor = factory.getDataExtractor(ExtractorType.CSV);
        File inputFile =  new File(getClass().getResource("../test_data_incorrect.csv").toURI());

        Assertions.assertThrows( IllegalArgumentException.class, () -> { csvDataExtractor.load(inputFile); } );
    }

}
