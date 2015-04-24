package ru.nsu.java.serialization.api;

import java.util.*;
import java.io.Serializable;

public class Document implements Serializable {

    protected DocId id;
    protected String title;
    protected Map<RefType, List<DocId>> references;

    public DocId getId() { return id; }
    public void setId(DocId id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public Map<RefType, List<DocId>> getReferences() {
        if (references == null) {
            references = new EnumMap<RefType, List<DocId>>(RefType.class);
        }
        return references;
    }

    public List<DocId> getReferences(RefType type) {
        List<DocId> list = getReferences().get(type);
        if (list == null) {
            list = new ArrayList<>();
            getReferences().put(type, list);
        }
        return list;
    }

    public void setReferences(Map<RefType, List<DocId>> refs) {
        this.references = refs; 
    }
}
