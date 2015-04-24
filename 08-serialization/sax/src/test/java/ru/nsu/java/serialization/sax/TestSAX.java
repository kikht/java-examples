package ru.nsu.java.serialization.sax;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import ru.nsu.java.serialization.api.*;

public class TestSAX {
    @Test
    public void test() throws Exception {
        SAXSerialization sax = new SAXSerialization();
        List<Document> res = sax.deserialize(new FileInputStream("/home/kikht/Dropbox/work/for_lectures/java/code/08-serialization/rfc-index.xml"));
        assertNotNull(res);
        sax.serialize(new FileOutputStream("test.xml"), res);
    }
}
