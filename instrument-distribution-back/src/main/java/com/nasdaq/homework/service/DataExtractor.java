package com.nasdaq.homework.service;

import com.nasdaq.homework.model.Transaction;

import java.util.List;

public interface DataExtractor {
    List<Transaction> load(Object source) throws Exception;
}
