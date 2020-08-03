package com.nasdaq.homework.tree;

import com.nasdaq.homework.exceptions.TreeNotFoundException;
import com.nasdaq.homework.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Tree implements TreeInterface {
    private Node<Transaction> root;
    private List<Node<Transaction>> filteredPaths = new ArrayList<>();

    @Override
    public void load(List<Transaction> transactions) {
        root = makeTreeFromTransactions(transactions);
    }

    @Override
    public Node<Transaction> fetchTree(String accountName) throws TreeNotFoundException {
        if(accountName != null && !accountName.isEmpty()) {
            filteredPaths = new ArrayList<>();
            depthFirstPathSearch(getRoot(), accountName);
            return concatFiltered();
        } else{
            return fetchTree();
        }
    }

    @Override
    public void addBranch(Node<Transaction> parent, Node<Transaction> leftChild) {
        addChild(parent, leftChild);
    }

    private void addChild(Node<Transaction> parent, Node<Transaction> childNode) {
        if(parent.hasChildren()){
            parent.setLeftChild(childNode);
        } else {
            addRightSibling(parent.getLeftChild(), childNode);
        }
        parent.getChildren().add(childNode);
    }

    private Node<Transaction> makeTreeFromTransactions(List<Transaction> transactions) {
        setRoot(new NodeBuilder<Transaction>("CSD").build());

        for(Transaction child: transactions) {
         Optional<Node<Transaction>> parent = searchFirstNodeByName(getRoot(), child.getAccountFrom());

            if(parent.isPresent()){
                  addChild(parent.get(),
                          new NodeBuilder<Transaction>(child.getAccountTo())
                          .parent(parent.get())
                          .data(child).build());
            } else {

                Node<Transaction> parentNode = new NodeBuilder<Transaction>(child.getAccountFrom())
                        .parent(getRoot())
                        .data(child).build();

                addChild(getRoot(), parentNode);

                addChild(parentNode, new NodeBuilder<Transaction>(child.getAccountTo())
                        .parent(parentNode)
                        .data(child).build());
            }
        }
        return getRoot();
    }

    private Node<Transaction> concatFiltered() {
        Node<Transaction> mergedResult = new NodeBuilder<Transaction>("CSD").build();

        for(Node<Transaction> filteredTree: filteredPaths){
            addBranch(mergedResult, filteredTree.getLeftChild());
        }
        return mergedResult;
    }

    private static void addRightSibling(Node<Transaction> leftChild, Node<Transaction> childNode) {
        Node<Transaction> nextRightSibling = leftChild.getRightSibling();
        if (notHaveRightSibling(nextRightSibling)) {
            leftChild.setRightSibling(childNode);
        } else {
            boolean found = true;
            while(found){
                if(notHaveRightSibling(nextRightSibling.getRightSibling())) {
                    nextRightSibling.setRightSibling(childNode);
                    found = false;
                } else {
                    nextRightSibling = nextRightSibling.getRightSibling();
                }
            }
        }
    }

    private static boolean notHaveRightSibling(Node<Transaction> nextRightSibling) {
        return nextRightSibling == null;
    }

    private void depthFirstPathSearch(Node<Transaction> rt, String key){
        if(rt.getName().equals(key)){
            Node<Transaction> path = extractPathToNode(rt);
            filteredPaths.add(path);
        }

        if (rt.isNotLeaf()) {
            Node<Transaction> temp = rt.getLeftChild();
            while (temp != null) {
                depthFirstPathSearch(temp, key);
                temp = temp.getRightSibling();
            }
        }
    }

    protected Optional<Node<Transaction>> searchFirstNodeByName(String nodeKey) {
        return searchFirstNodeByName(getRoot(), nodeKey);
    }

    protected Optional<Node<Transaction>> searchFirstNodeByName(Node<Transaction> rt, String nodeKey) {
        Optional<Node<Transaction>> result = Optional.empty();
        if(rt.getName().equals(nodeKey)){
            result =  Optional.of(rt);
        }

        for (Node<Transaction> child: rt.getChildren() ){
            Optional<Node<Transaction>> foundInChild = searchFirstNodeByName(child, nodeKey);
            if(foundInChild.isPresent()){
                return foundInChild;
            }
        }

        return result;
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

    private Node<Transaction> fetchTree() throws TreeNotFoundException {
        if(getRoot() == null){
            throw new TreeNotFoundException("Tree not loaded.");
        }
        return getRoot();
    }

    private Node<Transaction> getRoot() {
        return root;
    }

    private void setRoot(Node<Transaction> root) {
        this.root = root;
    }

    protected void clean() {
        root = null;
    }
}
