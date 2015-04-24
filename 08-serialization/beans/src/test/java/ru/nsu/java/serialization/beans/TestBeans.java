package ru.nsu.java.serialization.beans;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import ru.nsu.java.serialization.api.*;
import ru.nsu.java.serialization.sax.*;

public class TestBeans {

    @Before 
    public void setUp() throws Exception {
        SAXSerialization sax = new SAXSerialization();
        data = sax.deserialize(new FileInputStream("../rfc-index.xml"));

    }

    @Test
    public void test() throws Exception {
        BeansSerialization ser = new BeansSerialization();

        FileOutputStream out = new FileOutputStream("beans.xml");
        ser.serialize(out, data);
        out.close();

        FileInputStream in = new FileInputStream("beans.xml");
        List<Document> res = ser.deserialize(in);

        assertEquals(res.size(), data.size());
    }

    private List<Document> data;
}
