package ru.nsu.java.db.jpa.entities;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="LANGUAGE")
public class LanguageEnt {

    @Id 
    @Column(name="ID", unique=true, nullable=false, length=2)
    private String id;

    @Column(name="DESCRIPTION", length=50)
    private String description;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="langEntity")
    private List<BookEnt> books = new ArrayList<>();

    public LanguageEnt() {}
	
    public LanguageEnt(String id, String description) {
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
   
    public List<BookEnt> getBooks() {
        return this.books;
    }
    
    public void setBooks(List<BookEnt> books) {
        this.books = books;
    }

}

