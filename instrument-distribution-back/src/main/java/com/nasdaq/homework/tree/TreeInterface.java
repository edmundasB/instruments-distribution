package com.nasdaq.homework.tree;

import com.nasdaq.homework.exceptions.TreeNotFoundException;
import com.nasdaq.homework.model.Transaction;

import java.util.List;

public interface TreeInterface {
    void load(List<Transaction> transactions);

    Node<Transaction> fetchTree(String accountName) throws TreeNotFoundException;
}
