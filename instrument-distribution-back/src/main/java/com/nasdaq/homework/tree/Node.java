package com.nasdaq.homework.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Node<T> {
    private static AtomicInteger depth;
    private T data;
    private static List multipleNodesSearchResult = new ArrayList<>();
    private final String name;
    private Node<T> parent;
    private final List<Node<T>> children = new ArrayList<>();

    public Node(String nodeName) {
        this.name = nodeName;
        depth = new AtomicInteger(0);
    }

    public Node(Node<T> parent, String name, T data) {
        this.parent = parent;
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public T getData() {
        return data;
    }

    @JsonIgnore
    public long getDepth() {
        return depth.get();
    }

    @JsonIgnore
    public Node<T> getParent() {
        return parent;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public Node<T> addChild(Node<T> parent, String childName, T childData) {
        Node<T> childNode = new Node<T>(parent, childName, childData);
        if(this.children.isEmpty()){
            depth.incrementAndGet();
        }
        children.add(childNode);

        return childNode;
    }

    public Optional<Node<T>> search(String searchName) {
        Optional<Node<T>> result = Optional.empty();
        if(this.name.equals(searchName)){
            result =  Optional.of(this);
        }

        for (Node<T> child: children){
            Optional<Node<T>> foundInChild = child.search(searchName);
            if(foundInChild.isPresent()){
                return foundInChild;
            }
        }

        return result;
    }

    public List<Node<T>> searchMultiple(String searchName) {
        multipleNodesSearchResult = new ArrayList<>();

        if(this.name.equals(searchName)){
            multipleNodesSearchResult.add(this);
        }

        for (Node<T> child: children){
            Optional<Node<T>> foundInChild = child.search(searchName);
            foundInChild.ifPresent(multipleNodesSearchResult::add);
        }

        return multipleNodesSearchResult;
    }

    public T getAttributes() {
        return data;
    }
}
