package ru.nsu.java.db.jdbc;

import java.util.*;
import java.util.Date;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import ru.nsu.java.db.api.*;

public class JDBC_DAO implements BooksDAO {

    private final DataSource dataSource;
    private Connection currentConnection;

    public JDBC_DAO() throws NamingException, DAOException {
        Context ctx = new InitialContext();
        dataSource = (DataSource) ctx.lookup("jdbc/booksDB");
        if (dataSource == null) {
            throw new DAOException("Can't get DataSource");
        }
    }

    private Connection getConnection() throws DAOException {
        if (currentConnection == null)
            throw new DAOException("Must be in transaction");

        return currentConnection;
    }

    public List<String> listBookstores() throws DAOException {
        Connection conn = getConnection();
        try (
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM book_store");
        ) {
            List<String> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    public void createAuthor(String firstName, String lastName, Date birth) 
            throws DAOException {
        Connection conn = getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO author VALUES (next value for s_author_id, ?, ?, ?)")) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setDate(3, new java.sql.Date(birth.getTime()));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }
    
    public void createBook(String title, int year, int authorId, 
            String langCode) throws DAOException {
        Connection conn = getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO book VALUES (next value for s_book_id, ?, ?, ?, ?);")) {
       
            stmt.setInt(1, authorId);
            stmt.setString(2, title);
            stmt.setInt(3, year);
            stmt.setString(4, langCode);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    private static class AuthorBean implements Author {
        public AuthorBean(String firstName, String lastName, Date birth) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.birth = birth;
        }

        private final String firstName, lastName;
        private final Date birth;

        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public Date getBirthday() { return birth; }
    }

    private static class BookBean implements Book {
        public BookBean(int id, String title, Integer year, String firstName, 
                String lastName, Date birth, String language) {
            this.id = id;
            this.title = title;
            this.year = year;
            this.author = new AuthorBean(firstName, lastName, birth);
            this.language = language;
        }

        private final int id;
        private final String title;
        private final Integer year;
        private final Author author;
        private final String language;
    
        public int getId() { return id; }
        public String getTitle() { return title; }
        public Integer getYear() { return year; }
        public Author getAuthor() { return author; }
        public String getLanguage() { return language; }
    }

    public List<Book> searchBooks(String searchString) throws DAOException {
        Connection conn = getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
"SELECT b.id, b.title, b.published_in, " +
    "a.first_name, a.last_name, a.date_of_birth, l.description " +
    "FROM book b " +
    "JOIN author a ON b.author_id = a.id " +
    "JOIN language l ON b.language_id = l.id " +
    "WHERE b.title LIKE ? OR a.first_name LIKE ? OR a.last_name LIKE ?")) {

            String searchQual = "%" + searchString + "%";
            stmt.setString(1, searchQual);
            stmt.setString(2, searchQual);
            stmt.setString(3, searchQual);
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Book> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new BookBean(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getString(7)));
                }
                return result;
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    public int getStockQuantity(String bookStore, int bookId) 
            throws DAOException {
        Connection conn = getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT stock FROM book_to_book_store " +
                "WHERE book_store_name = ? AND book_id = ?")) {
            stmt.setString(1, bookStore);
            stmt.setInt(2, bookId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int result = rs.getInt(1);
                    if (rs.next()) 
                        throw new DAOException(
                            "Problem with primary key in book_to_book_store");
                    return result;
                } else {
                    return 0;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    public boolean buyBook(String bookStore, int bookId) throws DAOException {
        Connection conn = getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE book_to_book_store SET stock = stock - 1 " +
                "WHERE book_store_name = ? AND book_id = ? AND stock > 0")) {
            stmt.setString(1, bookStore);
            stmt.setInt(2, bookId);
            int res = stmt.executeUpdate();
            if (res > 1)
                throw new DAOException(
                        "Problem with primary key in book_to_book_store");

            return res > 0;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }
    
    public void begin() throws DAOException {
        try {
            currentConnection = dataSource.getConnection();
            currentConnection.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    public void commit() throws DAOException {
        try {
            if (currentConnection == null) {
                throw new DAOException("Transaction is not started");
            }
            currentConnection.commit();
            currentConnection.close();
            currentConnection = null;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    public void rollback() throws DAOException {
        try {
            if (currentConnection == null) {
                throw new DAOException("Transaction is not started");
            }
            currentConnection.rollback();
            currentConnection.close();
            currentConnection = null;
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

}
