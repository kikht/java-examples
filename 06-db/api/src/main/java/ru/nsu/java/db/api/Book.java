package ru.nsu.java.db.api;

public interface Book {
    public int getId();
    public String getTitle();
    public Integer getYear();
    public Author getAuthor();
    public String getLanguage();
}
