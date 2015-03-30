package ru.nsu.java.db.jpa;

import java.util.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import ru.nsu.java.db.api.*;
import ru.nsu.java.db.jpa.entities.*;

public class JPA_DAO implements BooksDAO {
    
    private EntityManager currentEntityManager;

    public void createAuthor(String firstName, String lastName, Date birth)
            throws DAOException {
        EntityManager entityManager = getEntityManager();
        try {
            AuthorEnt author = new AuthorEnt(firstName, lastName, birth);
            entityManager.persist(author);
            entityManager.flush();
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }
    public void createBook(String title, int year, int authorId, 
            String langCode) throws DAOException {
        EntityManager entityManager = getEntityManager();
        try {
            AuthorEnt author = entityManager.find(
                    AuthorEnt.class, authorId);
            LanguageEnt lang = entityManager.find(
                    LanguageEnt.class, langCode);
            BookEnt book = new BookEnt(title, author, year, lang);
            entityManager.persist(book);
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    public List<Book> searchBooks(String searchString) throws DAOException {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery(
                "FROM BookEnt WHERE title LIKE :query OR " +
                "author.firstName LIKE :query OR author.lastName LIKE :query ")
                .setParameter("query", "%"+searchString+"%")
                .getResultList();
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    public List<String> listBookstores() throws DAOException {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery<String> criteria = entityManager
                .getCriteriaBuilder()
                .createQuery(String.class);
            Root<BookStoreEnt> root = criteria.from(BookStoreEnt.class);
            return entityManager
                .createQuery(criteria.select(root.get("name")))
                .getResultList();
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    public int getStockQuantity(String bookStore, int bookId) 
            throws DAOException {
        EntityManager entityManager = getEntityManager();
        try {
            BookToBookStoreEnt.Id id = 
                new BookToBookStoreEnt.Id(bookStore, bookId);
            BookToBookStoreEnt entity = (BookToBookStoreEnt) 
                entityManager.find(BookToBookStoreEnt.class, id);
            return entity.getStock();
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    public boolean buyBook(String bookStore, int bookId) throws DAOException {
        EntityManager entityManager = getEntityManager();
        try {
            BookToBookStoreEnt.Id id = 
                new BookToBookStoreEnt.Id(bookStore, bookId);
            BookToBookStoreEnt entity = (BookToBookStoreEnt) 
                entityManager.find(BookToBookStoreEnt.class, id);
            if (entity == null)
                return false;

            int stock = entity.getStock();
            if (stock > 0) {
                entity.setStock(stock - 1);
                entityManager.flush();
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
        if (currentEntityManager != null)
            throw new DAOException("Already in transaction");

        currentEntityManager = EntityManagerFactorySingleton
            .getEntityManagerFactory().createEntityManager();
        currentEntityManager.getTransaction().begin();
    }

    public void commit() throws DAOException {
        if (currentEntityManager == null) 
            throw new DAOException("Not in transaction");

        currentEntityManager.getTransaction().commit();
        currentEntityManager.close();
        currentEntityManager = null;
    }

    public void rollback() throws DAOException {
        if (currentEntityManager == null) 
            throw new DAOException("Not in transaction");

        currentEntityManager.getTransaction().rollback();
        currentEntityManager.close();
        currentEntityManager = null;
    }

    private EntityManager getEntityManager() throws DAOException {
        if (currentEntityManager != null) {
            return currentEntityManager;
        } else {
            throw new DAOException("Must be in transaction");
        }
    }
}
