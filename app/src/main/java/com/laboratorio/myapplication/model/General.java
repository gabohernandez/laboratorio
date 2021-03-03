package com.laboratorio.myapplication.model;

import java.util.List;

public class General {

    private Long id;
    private List<GeneralNode> activeNodes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<GeneralNode> getActiveNodes() {
        return activeNodes;
    }

    public void setActiveNodes(List<GeneralNode> activeNodes) {
        this.activeNodes = activeNodes;
    }
}
