package ru.nsu.java.serialization.java;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import ru.nsu.java.serialization.api.*;
import ru.nsu.java.serialization.sax.*;

public class TestJava {

    @Before 
    public void setUp() throws Exception {
        SAXSerialization sax = new SAXSerialization();
        data = sax.deserialize(new FileInputStream("../rfc-index.xml"));

    }

    @Test
    public void test() throws Exception {
        JavaSerialization ser = new JavaSerialization();

        FileOutputStream out = new FileOutputStream("java.out");
        ser.serialize(out, data);
        out.close();

        FileInputStream in = new FileInputStream("java.out");
        List<Document> res = ser.deserialize(in);

        assertEquals(res.size(), data.size());
    }

    private List<Document> data;
}
