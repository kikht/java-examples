package ru.nsu.java.db.hibernate.entities;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="LANGUAGE")
public class LanguageEntity {

    @Id 
    @Column(name="ID", unique=true, nullable=false, length=2)
    private String id;

    @Column(name="DESCRIPTION", length=50)
    private String description;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="langEntity")
    private List<BookEntity> books = new ArrayList<>();

    public LanguageEntity() {}
	
    public LanguageEntity(String id, String description) {
        this.id = id;
        this.description = description;
    }
   
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
   
    public List<BookEntity> getBooks() {
        return this.books;
    }
    
    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

}

