package com.example.demo.swift;

public class SwiftBulkOperation {

    private String containerName;
    private String fileName;

    public SwiftBulkOperation(String containerName, String fileName) {
        this.containerName = containerName;
        this.fileName = fileName;
    }

    public String getContainerName() {
        return containerName;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * toString Don't modify this
     * @return
     */
    @Override
    public String toString() {
        return containerName + "/" + fileName;
    }
}
