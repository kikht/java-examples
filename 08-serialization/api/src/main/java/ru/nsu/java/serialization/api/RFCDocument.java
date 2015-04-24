package ru.nsu.java.serialization.api;

import java.util.*;
import java.io.Serializable;

public class RFCDocument extends Document implements Serializable {
    protected List<Author> author;
    protected Date date;
    protected List<Format> format;
    protected List<String> keywords;
    protected List<String> abstrct;
    protected String draft;
    protected String notes;
    protected Status currentStatus;
    protected Status publicationStatus;
    protected Stream stream;
    protected String area;
    protected String wgAcronym;
    protected String errataUrl;

    public List<Author> getAuthor() {
        if (author == null) {
            author = new ArrayList<Author>();
        }
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date value) {
        this.date = value;
    }

    public List<Format> getFormat() {
        if (format == null) {
            format = new ArrayList<Format>();
        }
        return format;
    }

    public void setFormat(List<Format> format) {
        this.format = format;
    }

    public List<String> getKeywords() {
        if (keywords == null) {
            keywords = new ArrayList<String>();
        }
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getAbstract() {
        if (abstrct == null) {
            abstrct = new ArrayList<String>();
        }
        return abstrct;
    }

    public void setAbstract(List<String> abstrct) {
        this.abstrct = abstrct;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String value) {
        this.draft = value;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String value) {
        this.notes = value;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Status value) {
        this.currentStatus = value;
    }

    public Status getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(Status value) {
        this.publicationStatus = value;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream value) {
        this.stream = value;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String value) {
        this.area = value;
    }

    public String getWgAcronym() {
        return wgAcronym;
    }

    public void setWgAcronym(String value) {
        this.wgAcronym = value;
    }

    public String getErrataUrl() {
        return errataUrl;
    }

    public void setErrataUrl(String value) {
        this.errataUrl = value;
    }
}
