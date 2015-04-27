package ru.nsu.java.serialization.xstream;

import java.io.*;
import java.util.*;
import com.thoughtworks.xstream.*;
import ru.nsu.java.serialization.api.*;

public class XStreamSerialization implements SerializationAPI {
    private final XStream xstream = new XStream();

    public XStreamSerialization() {
        //xstream.alias("ref-entry", Document.class);
        xstream.alias("rfc-entry", RFCDocument.class);
        xstream.alias("ref-type", RefType.class);
        xstream.alias("doc-id", DocId.class);
        xstream.alias("author", Author.class);
        xstream.alias("format", Format.class);

        xstream.addImplicitCollection(RFCDocument.class, "author");
        xstream.addImplicitCollection(RFCDocument.class, "format");
    }

    public void serialize(OutputStream stream, List<Document> data) {
        xstream.toXML(data, stream);
    }

    public List<Document> deserialize(InputStream stream) {
        return (List<Document>) xstream.fromXML(stream);
    }
}
