package ru.nsu.java.db.hibernate.entities;

import java.util.*;
import javax.persistence.*;
import ru.nsu.java.db.api.*;

@Entity
@Table(name="BOOK")
public class BookEntity implements Book {

    @Id 
    @Column(name="ID", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_book")
    @SequenceGenerator(name = "s_book", sequenceName = "S_BOOK_ID")
    private int id;
    
    @Column(name="TITLE", nullable=false, length=400)
    private String title;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="AUTHOR_ID", nullable=false)
    private AuthorEntity author;
    
    @Column(name="PUBLISHED_IN")
    private Integer year;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LANGUAGE_ID", nullable=false)
    private LanguageEntity langEntity;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="book")
    private List<BookToBookStoreEntity> bookStores = new ArrayList<>();

    public BookEntity() {}
	
    public BookEntity(String title, AuthorEntity author, Integer year, 
            LanguageEntity language) {
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

    public LanguageEntity getLangEntity() {
        return this.langEntity;
    }
    
    public void setLangEntity(LanguageEntity language) {
        this.langEntity = language;
    }
    
    public AuthorEntity getAuthor() {
        return this.author;
    }
    
    public void setAuthor(AuthorEntity author) {
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

    public List<BookToBookStoreEntity> getBookStores() {
        return this.bookStores;
    }
    
    public void setBookStores(List<BookToBookStoreEntity> bookStores) {
        this.bookStores = bookStores;
    }

    public String getLanguage() {
        return getLangEntity().getDescription();
    }
}

