package ru.nsu.java.db.jpa.entities;

import java.util.*;
import javax.persistence.*;
import ru.nsu.java.db.api.*;

@Entity
@Table(name="AUTHOR")
public class AuthorEnt implements Author {

    @Id 
    @Column(name="ID", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_author")
    @SequenceGenerator(name = "s_author", sequenceName = "S_AUTHOR_ID")
    private int id;
    
    @Column(name="FIRST_NAME", length=50)
    private String firstName;
    
    @Column(name="LAST_NAME", nullable=false, length=50)
    private String lastName;
    
    @Temporal(TemporalType.DATE)
    @Column(name="DATE_OF_BIRTH", length=8)
    private Date birthday;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="author")
    private List<BookEnt> books = new ArrayList<>();

    public AuthorEnt() {}

    public AuthorEnt(String firstName, String lastName, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public List<BookEnt> getBooks() {
        return this.books;
    }
    
    public void setBooks(List<BookEnt> books) {
        this.books = books;
    }
}

