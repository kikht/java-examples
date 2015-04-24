package ru.nsu.java.serialization.api;

import java.io.Serializable;

public class DocId implements Serializable {
    protected DocType type;
    protected int id;

    public DocId() {}
    public DocId(String docId) {
        type = DocType.valueOf(docId.substring(0, 3));
        id = Integer.valueOf(docId.substring(3));
    }
    
    public DocType getType() { return type; }
    public void setType(DocType type) { this.type = type; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String toString() {
        return type.toString() + id;
    }
}
