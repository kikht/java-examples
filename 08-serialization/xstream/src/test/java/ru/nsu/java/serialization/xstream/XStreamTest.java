package ru.nsu.java.serialization.xstream;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import ru.nsu.java.serialization.api.*;
import ru.nsu.java.serialization.sax.*;

public class XStreamTest {

    @Before 
    public void setUp() throws Exception {
        SAXSerialization sax = new SAXSerialization();
        data = sax.deserialize(new FileInputStream("../rfc-index.xml"));

    }

    @Test
    public void test() throws Exception {
        XStreamSerialization ser = new XStreamSerialization();

        FileOutputStream out = new FileOutputStream("xstream.xml");
        ser.serialize(out, data);
        out.close();

        FileInputStream in = new FileInputStream("xstream.xml");
        List<Document> res = ser.deserialize(in);

        assertEquals(res.size(), data.size());
    }

    private List<Document> data;
}
