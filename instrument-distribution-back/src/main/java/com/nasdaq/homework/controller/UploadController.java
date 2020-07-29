package com.nasdaq.homework.controller;

import com.nasdaq.homework.tree.Tree;
import com.nasdaq.homework.model.Transaction;
import com.nasdaq.homework.service.DataExtractorFactory;
import com.nasdaq.homework.model.ExtractorType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/v1/upload")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UploadController {
    private final Tree tree;
    private final DataExtractorFactory dataExtractorFactory;

    @PostMapping
    public ResponseEntity<String> loadLedger(@RequestParam MultipartFile ledgerCsv) throws Exception {
        File tempFile = File.createTempFile("ledger", ".csv");
        tempFile.deleteOnExit();
        ledgerCsv.transferTo(tempFile);

        List<Transaction> transactionList = dataExtractorFactory.getDataExtractor(ExtractorType.CSV).load(tempFile);
        tree.load(transactionList);

        return ResponseEntity.ok().build();
    }

    public UploadController(DataExtractorFactory dataExtractorFactory, Tree tree) {
        this.dataExtractorFactory = dataExtractorFactory;
        this.tree = tree;
    }
}
