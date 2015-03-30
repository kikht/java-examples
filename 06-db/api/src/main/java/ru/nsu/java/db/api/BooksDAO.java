package ru.nsu.java.db.api;

import java.util.List;
import java.util.Date;

public interface BooksDAO {
    public void createAuthor(String firstName, String lastName, Date birth)
        throws DAOException;
    public void createBook(String title, int year, int authorId, 
            String langCode) throws DAOException;

    public List<? extends Book> searchBooks(String searchString) 
        throws DAOException;
    public List<String> listBookstores() throws DAOException;
    public int getStockQuantity(String bookStore, int bookId) 
        throws DAOException;

    public boolean buyBook(String bookStore, int bookId) throws DAOException;

    // Transaction control
    public void begin() throws DAOException;
    public void commit() throws DAOException;
    public void rollback() throws DAOException;
}
