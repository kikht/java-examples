package ru.nsu.java.db.jpa.entities;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="BOOK_STORE")
public class BookStoreEnt {

    @Id 
    @Column(name="NAME", unique=true, nullable=false, length=400)
    private String name;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="bookStore")
    private List<BookToBookStoreEnt> books = new ArrayList<>();

    public BookStoreEnt() {}

    public BookStoreEnt(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public List<BookToBookStoreEnt> getBooks() {
        return this.books;
    }
    
    public void setBooks(List<BookToBookStoreEnt> books) {
        this.books = books;
    }
}

