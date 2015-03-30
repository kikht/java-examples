package ru.nsu.java.db.hibernate;

import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import ru.nsu.java.db.api.*;
import ru.nsu.java.db.hibernate.entities.*;

public class HibernateDAO implements BooksDAO {
    
    private Session currentSession;

    public void createAuthor(String firstName, String lastName, Date birth)
            throws DAOException {
        Session session = getSession();
        try {
            AuthorEntity author = new AuthorEntity(firstName, lastName, birth);
            session.save(author);
            session.flush();
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }
    public void createBook(String title, int year, int authorId, 
            String langCode) throws DAOException {
        Session session = getSession();
        try {
            AuthorEntity author = (AuthorEntity) session.load(
                    AuthorEntity.class, authorId);
            LanguageEntity lang = (LanguageEntity) session.get(
                    LanguageEntity.class, langCode);
            BookEntity book = new BookEntity(title, author, year, lang);
            session.save(book);
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    public List<Book> searchBooks(String searchString) throws DAOException {
        Session session = getSession();
        try {
            return session.createQuery(
                "FROM BookEntity WHERE title LIKE :query OR " +
                "author.firstName LIKE :query OR author.lastName LIKE :query ")
                .setString("query", "%"+searchString+"%")
                .list();
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    public List<String> listBookstores() throws DAOException {
        Session session = getSession();
        try {
            return session.createCriteria(BookStoreEntity.class)
                .setProjection(Projections.property("name"))
                .list();
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    public int getStockQuantity(String bookStore, int bookId) 
            throws DAOException {
        Session session = getSession();
        try {
            BookToBookStoreEntity.Id id = 
                new BookToBookStoreEntity.Id(bookStore, bookId);
            BookToBookStoreEntity entity = (BookToBookStoreEntity) 
                session.get(BookToBookStoreEntity.class, id);
            return entity.getStock();
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    public boolean buyBook(String bookStore, int bookId) throws DAOException {
        Session session = getSession();
        try {
            BookToBookStoreEntity.Id id = 
                new BookToBookStoreEntity.Id(bookStore, bookId);
            BookToBookStoreEntity entity = (BookToBookStoreEntity) 
                session.get(BookToBookStoreEntity.class, id);
            if (entity == null)
                return false;

            int stock = entity.getStock();
            if (stock > 0) {
                entity.setStock(stock - 1);
                session.flush();
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    // Transaction control
    public void begin() throws DAOException {
        if (currentSession != null)
            throw new DAOException("Already in transaction");

        currentSession = 
            SessionFactorySingleton.getSessionFactory().openSession();
        currentSession.beginTransaction();
    }

    public void commit() throws DAOException {
        if (currentSession == null) 
            throw new DAOException("Not in transaction");

        currentSession.getTransaction().commit();
        currentSession.close();
        currentSession = null;
    }

    public void rollback() throws DAOException {
        if (currentSession == null) 
            throw new DAOException("Not in transaction");

        currentSession.getTransaction().rollback();
        currentSession.close();
        currentSession = null;
    }

    private Session getSession() throws DAOException {
        if (currentSession != null) {
            return currentSession;
        } else {
            throw new DAOException("Must be in transaction");
        }
    }
}
