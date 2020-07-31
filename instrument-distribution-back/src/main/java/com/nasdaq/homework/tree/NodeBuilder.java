package com.nasdaq.homework.tree;

public class NodeBuilder<T> {
    private T data;
    private final String key;
    private Node<T> parent;

    public NodeBuilder(String key) {
        this.key = key;
    }

    public NodeBuilder<T> data(T data) {
        this.data = data;
        return this;
    }

    public NodeBuilder<T> parent(Node<T> parentNode) {
        this.parent = parentNode;
        return this;
    }

    public T getData() {
        return data;
    }

    public String getKey() {
        return key;
    }

    public Node<T> getParent() {
        return parent;
    }

    public Node<T> build() {
        return new Node<>(this);
    }
}
