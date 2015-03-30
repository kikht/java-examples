package ru.nsu.java.db.jpa.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="BOOK_TO_BOOK_STORE")
public class BookToBookStoreEnt {
    @Embeddable
    public static class Id implements Serializable {
        
        @Column(name="BOOK_STORE_NAME", nullable=false, length=400)
        private String bookStore;

        @Column(name="BOOK_ID", nullable=false)
        private int bookId;

        public Id() { }

        public Id(String bookStore, int bookId) {
            this.bookStore = bookStore;
            this.bookId = bookId;
        }
   

        public String getBookStore() { return this.bookStore; }
        public void setBookStore(String bookStore) {
            this.bookStore = bookStore;
        }

        public int getBookId() { return this.bookId; }
        public void setBookId(int bookId) { this.bookId = bookId; }

        public boolean equals(Object obj) {
            if ( (this == obj ) ) return true;
		    if ( (obj == null ) ) return false;
		    if ( !(obj instanceof BookToBookStoreEnt.Id) ) return false;
		    BookToBookStoreEnt.Id other = ( BookToBookStoreEnt.Id ) obj; 
         
		    return ( this.getBookId() == other.getBookId() ) &&
                ( this.getBookStore() == null 
                    ? other.getBookStore() == null
                    : this.getBookStore().equals(other.getBookStore()) );
        }

        public int hashCode() {
            int result = getBookId();
            result = 31 * result + 
                (getBookStore() == null ? 0 : getBookStore().hashCode());
            return result;
        }   
    }

    @EmbeddedId
    private Id id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name="BOOK_ID", nullable=false)
    private BookEnt book;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("bookStore")
    @JoinColumn(name="BOOK_STORE_NAME", nullable=false)
    private BookStoreEnt bookStore;
   
    @Column(name="STOCK")
    private Integer stock;

    public BookToBookStoreEnt() {}
	
    public BookToBookStoreEnt(BookEnt book, BookStoreEnt bookStore, 
            Integer stock) {
        this.id = new Id(bookStore.getName(), book.getId());
        this.book = book;
        this.bookStore = bookStore;
        this.stock = stock;
    }
   
    public Id getId() {
        return this.id;
    }
    
    public void setId(Id id) {
        this.id = id;
    }

    public BookEnt getBook() {
        return this.book;
    }
    
    public void setBook(BookEnt book) {
        this.book = book;
    }

    public BookStoreEnt getBookStore() {
        return this.bookStore;
    }
    
    public void setBookStore(BookStoreEnt bookStore) {
        this.bookStore = bookStore;
    }
    
    public Integer getStock() {
        return this.stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
}

