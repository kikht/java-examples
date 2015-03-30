package ru.nsu.java.db.jpa.entities;

import java.util.*;
import javax.persistence.*;
import ru.nsu.java.db.api.*;

@Entity
@Table(name="BOOK")
public class BookEnt implements Book {

    @Id 
    @Column(name="ID", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_book")
    @SequenceGenerator(name = "s_book", sequenceName = "S_BOOK_ID")
    private int id;
    
    @Column(name="TITLE", nullable=false, length=400)
    private String title;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AUTHOR_ID", nullable=false)
    private AuthorEnt author;
    
    @Column(name="PUBLISHED_IN")
    private Integer year;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LANGUAGE_ID", nullable=false)
    private LanguageEnt langEntity;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="book")
    private List<BookToBookStoreEnt> bookStores = new ArrayList<>();

    public BookEnt() {}
	
    public BookEnt(String title, AuthorEnt author, Integer year, 
            LanguageEnt language) {
        this.title = title;
        this.author = author;
        this.langEntity = language;
        this.year = year;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public LanguageEnt getLangEntity() {
        return this.langEntity;
    }
    
    public void setLangEntity(LanguageEnt language) {
        this.langEntity = language;
    }
    
    public AuthorEnt getAuthor() {
        return this.author;
    }
    
    public void setAuthor(AuthorEnt author) {
        this.author = author;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Integer getYear() {
        return this.year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }

    public List<BookToBookStoreEnt> getBookStores() {
        return this.bookStores;
    }
    
    public void setBookStores(List<BookToBookStoreEnt> bookStores) {
        this.bookStores = bookStores;
    }

    public String getLanguage() {
        return getLangEntity().getDescription();
    }
}

