package com.nasdaq.homework.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private final T data;
    private final String name;
    private Node<T> parent;
    private  Node<T> leftChild;
    private  Node<T> rightSibling;
    private List<Node<T>> children = new ArrayList<>();

    Node(NodeBuilder<T> builder) {
        this.data = builder.getData();
        this.name = builder.getKey();
        this.parent = builder.getParent();
    }

    @JsonIgnore
    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    @JsonIgnore
    public Node<T> getRightSibling() {
        return rightSibling;
    }

    public void setRightSibling(Node<T> rightSibling) {
        this.rightSibling = rightSibling;
    }

    public String getName() {
        return name;
    }
    @JsonIgnore
    public T getData() {
        return data;
    }

    @JsonIgnore
    public Node<T> getParent() {
        return parent;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    boolean hasChildren() {
        return this.children.isEmpty();
    }

    boolean isNotLeaf(){
        return !hasChildren();
    }

    public T getAttributes() {
        return data;
    }

    public void setParent(Node<T> copied) {
        this.parent = copied;
    }

    public void addChildToList(Node<T> child) {
        this.children.add(child);
    }
}
