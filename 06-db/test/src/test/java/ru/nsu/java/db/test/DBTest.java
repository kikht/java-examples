import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.*;
import java.text.*;
import javax.naming.*;
import org.apache.commons.dbcp2.*;
import net.ttddyy.dsproxy.support.ProxyDataSource;
import net.ttddyy.dsproxy.listener.SLF4JQueryLoggingListener;
import org.apache.log4j.Logger;
import ru.nsu.java.db.api.*;

@RunWith(Parameterized.class)
public class DBTest {
    @Parameters
    public static Collection implementations() {
        Object[][] data = new Object[][] {
            { ru.nsu.java.db.jdbc.JDBC_DAO.class },
            { ru.nsu.java.db.hibernate.HibernateDAO.class }
        };
        return Arrays.asList(data);
    }

    @BeforeClass
    public static void onlyOnce() throws Exception {
        //JNDI init
        InitialContext ic = new InitialContext();

        // Construct BasicDataSource
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("org.h2.Driver");
        bds.setUrl("jdbc:h2:/home/kikht/Dropbox/work/for_lectures/java/code/06-db/booksDB");
        bds.setUsername("sa");

        // Construct ProxyDataSource
        ProxyDataSource proxyDS = new ProxyDataSource();
        proxyDS.setDataSource(bds);
        proxyDS.setListener(new SLF4JQueryLoggingListener());
      
        ic.bind("jdbc/booksDB", proxyDS);
    }


    private BooksDAO dao;
    private static final Logger log = Logger.getLogger(DBTest.class);

    public DBTest(Class impl) throws Exception {
        dao = (BooksDAO) impl.newInstance();
    }

    @Test
    public void testBookstore() throws Exception {
        log.info("Running testBookstore for " + dao.getClass());
        dao.begin();
        List<String> result = dao.listBookstores();
        dao.commit();
        assertTrue(result.contains("Amazon"));
    }

    @Test
    public void testCreateAuthor() throws Exception {
        log.info("Running testCreateAuthor for " + dao.getClass());
        dao.begin();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse("1832-01-27");
        dao.createAuthor("Lewis", "Carroll", date);
        dao.rollback();
    }
    
    @Test
    public void testCreateBook() throws Exception {
        log.info("Running testCreateBook for " + dao.getClass());
        dao.begin();
        dao.createBook("Coming Up for Air", 1939, 1, "EN");
        dao.rollback();
    }

    @Test
    public void testSearchBooks() throws Exception {
        log.info("Running testSearchBooks for " + dao.getClass());
        dao.begin();
        List<? extends Book> res = dao.searchBooks("Orwell");
        log.info("Before access to entity");
        assertEquals(2, res.size());
        assertEquals("Orwell", res.get(0).getAuthor().getLastName());
        dao.rollback();
    }

    @Test
    public void testBuyBook() throws Exception {
        log.info("Running testBuyBook for " + dao.getClass());
        dao.begin();
        int stock = dao.getStockQuantity("Amazon", 1);
        assertEquals(10, stock);
        log.info("Before buy");
        boolean result = dao.buyBook("Amazon", 1);
        assertTrue(result);
        assertEquals(stock - 1, dao.getStockQuantity("Amazon", 1));
        dao.rollback();
    }
}
