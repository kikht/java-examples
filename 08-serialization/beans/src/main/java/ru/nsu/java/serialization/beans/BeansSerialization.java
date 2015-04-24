package ru.nsu.java.serialization.beans;

import java.io.*;
import java.beans.*;
import java.util.*;
import ru.nsu.java.serialization.api.*;

public class BeansSerialization implements SerializationAPI {
    public void serialize(OutputStream stream, List<Document> data) {
        XMLEncoder out = new XMLEncoder(stream);
        out.writeObject(data);
        out.close();
    }

    public List<Document> deserialize(InputStream stream) {
        XMLDecoder in = new XMLDecoder(stream);
        List<Document> data = (List<Document>) in.readObject();
        in.close();
        return data;
    }
}
