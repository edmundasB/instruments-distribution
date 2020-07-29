package com.nasdaq.homework.tree;

import com.nasdaq.homework.exceptions.TreeNotFoundException;
import com.nasdaq.homework.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class Tree {
    private static Node<Transaction> root;

    public void load(List<Transaction> transactions) {
        root = makeTree(transactions);
    }

    private Node<Transaction> makeTree(List<Transaction> transactions) {
        Node<Transaction> response = new Node<>("CSD");

        for(Transaction child: transactions){
            Optional<Node<Transaction>> parent = response.search(child.getAccountFrom());

            if(parent.isPresent()){
                 parent.get().addChild(parent.get(), child.getAccountTo(), child);
            } else {
                Node<Transaction> newNode = response.addChild(response, child.getAccountFrom(), child);
                newNode.addChild(newNode, child.getAccountTo(), child);
            }
        }
        return response;
    }

    public Node<Transaction> fetchTree(String accountName) throws TreeNotFoundException {
        if(accountName != null && !accountName.isEmpty()){
            return filteredTree(accountName);
        } else{
            return fetchTree();
        }
    }

    private Node<Transaction> fetchTree() throws TreeNotFoundException {
        if(root == null){
            throw new TreeNotFoundException("Tree not loaded.");
        }
        return root;
    }

    private Node<Transaction> filteredTree(String name) {
        List<Node<Transaction>> nodeList = root.searchMultiple(name);
        List<Transaction> foundPathTransactions = new ArrayList<>();

        for(Node<Transaction> node: nodeList) {
                foundPathTransactions.add(node.getData());

                Node<Transaction> pathNode = node;
                while (pathNode.getParent().getData() != null && pathNode.getParent().getParent().getData() != null) {
                    foundPathTransactions.add(pathNode.getParent().getData());
                    pathNode = pathNode.getParent();
            }
        }

        Collections.reverse(foundPathTransactions);

        return makeTree(foundPathTransactions);
    }

    public void clean() {
        root = null;
    }
}
