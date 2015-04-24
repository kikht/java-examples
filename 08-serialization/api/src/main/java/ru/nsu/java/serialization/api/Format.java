package ru.nsu.java.serialization.api;

import java.io.Serializable;

public class Format implements Serializable {

    protected FileFormat fileFormat;
    protected long charCount;
    protected long pageCount;

    public FileFormat getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(FileFormat value) {
        this.fileFormat = value;
    }

    public long getCharCount() {
        return charCount;
    }

    public void setCharCount(long value) {
        this.charCount = value;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long value) {
        this.pageCount = value;
    }

}
