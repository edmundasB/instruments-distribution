package com.nasdaq.homework.service;

import com.nasdaq.homework.model.ExtractorType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DataExtractorFactoryTest {
    @InjectMocks
    private DataExtractorFactory factory;
    @Mock
    private CSVDataExtractor csvDataExtractor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void makeCSVDataExtractor(){
        DataExtractor extractor = factory.getDataExtractor(ExtractorType.CSV);
        Assertions.assertTrue(extractor instanceof CSVDataExtractor);
    }

}
