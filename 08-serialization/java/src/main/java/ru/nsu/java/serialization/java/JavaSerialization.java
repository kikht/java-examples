package ru.nsu.java.serialization.java;

import java.io.*;
import java.util.*;
import ru.nsu.java.serialization.api.*;

public class JavaSerialization implements SerializationAPI {
    public void serialize(OutputStream stream, List<Document> data) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(stream);
            out.writeObject(data);
            out.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Document> deserialize(InputStream stream) {
        try {
            ObjectInputStream in = new ObjectInputStream(stream);
            List<Document> data = (List<Document>) in.readObject();
            in.close();
            return data;
        } catch (IOException|ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
