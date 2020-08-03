package com.nasdaq.homework.tree;

import com.nasdaq.homework.exceptions.TreeNotFoundException;
import com.nasdaq.homework.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Tree extends AbstractTree implements TreeInterface {

    @Override
    public void load(List<Transaction> transactions) {
        root = makeTreeFromTransactions(transactions);
    }

    @Override
    public Node<Transaction> fetchTree(String accountName) throws TreeNotFoundException {
        if(accountName != null && !accountName.isEmpty()) {
            filteredPaths = new ArrayList<>();
            searchPath(getRoot(), accountName);
            return concatFiltered();
        } else{
            return fetchTree();
        }
    }

    @Override
    protected void searchPath(Node<Transaction> rt, String key) {
        if(rt.getName().equals(key)){
            Node<Transaction> path = extractPathToNode(rt);
            filteredPaths.add(path);
        }

        if (rt.isNotLeaf()) {
            Node<Transaction> temp = rt.getLeftChild();
            while (temp != null) {
                searchPath(temp, key);
                temp = temp.getRightSibling();
            }
        }
    }

    private static Node<Transaction> extractPathToNode(Node<Transaction> rt) {
        Node<Transaction> active = new NodeBuilder<Transaction>(rt.getName()).data(rt.getData()).build();
        Node<Transaction> current = rt;

        while(current.getParent() != null) {
            current = current.getParent();
            Node<Transaction> copied = new NodeBuilder<Transaction>(current.getName()).data(current.getData()).build();
            copied.setLeftChild(active);
            copied.addChildToList(active);
            active.setParent(copied);
            active = copied;
        }
        return active;
    }

}
