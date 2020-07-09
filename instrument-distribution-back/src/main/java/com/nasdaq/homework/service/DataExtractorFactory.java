package com.nasdaq.homework.service;

import com.nasdaq.homework.model.ExtractorType;
import org.springframework.stereotype.Service;

@Service
public class DataExtractorFactory {
    private final CSVDataExtractor csvDataExtractor;

    public DataExtractorFactory(CSVDataExtractor csvDataExtractor) {
        this.csvDataExtractor = csvDataExtractor;
    }

    public DataExtractor getDataExtractor(ExtractorType type) {
        DataExtractor extractor = null;
        if (type == ExtractorType.CSV) {
            extractor =  csvDataExtractor;
        }
        return extractor;
    }
}
