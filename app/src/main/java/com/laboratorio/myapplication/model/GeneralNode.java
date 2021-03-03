package com.laboratorio.myapplication.model;

public class GeneralNode {
    private Long id;
    private Node node;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return node.getName();
    }
}
