package ru.nsu.java.serialization.api;

import java.io.*;
import java.util.*;

public interface SerializationAPI {
    public void serialize(OutputStream stream, List<Document> data);
    public List<Document> deserialize(InputStream stream);
}
