package ru.nsu.java.db.hibernate.entities;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="BOOK_STORE")
public class BookStoreEntity {

    @Id 
    @Column(name="NAME", unique=true, nullable=false, length=400)
    private String name;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="bookStore")
    private List<BookToBookStoreEntity> books = new ArrayList<>();

    public BookStoreEntity() {}

    public BookStoreEntity(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public List<BookToBookStoreEntity> getBooks() {
        return this.books;
    }
    
    public void setBooks(List<BookToBookStoreEntity> books) {
        this.books = books;
    }
}

