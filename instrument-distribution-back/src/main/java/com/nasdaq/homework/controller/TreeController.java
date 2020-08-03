package com.nasdaq.homework.controller;

import com.nasdaq.homework.tree.Tree;
import com.nasdaq.homework.tree.Node;
import com.nasdaq.homework.exceptions.TreeNotFoundException;
import com.nasdaq.homework.model.Transaction;
import com.nasdaq.homework.tree.TreeInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/ledger")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TreeController {
    private final TreeInterface service;
    public TreeController(Tree service) {
        this.service = service;
    }

    @GetMapping("/tree")
    public ResponseEntity<Node<Transaction>> fetchTree(@RequestParam(required = false) String account) {
        try {
            return ResponseEntity.ok(service.fetchTree(account));
        } catch (TreeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
