package ru.nsu.java.serialization.sax;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.stream.*;
import org.xml.sax.*;
import ru.nsu.java.serialization.api.*;

public class SAXSerialization implements SerializationAPI {
    private final GregorianCalendar calendar = new GregorianCalendar();

    public void serialize(OutputStream stream, List<Document> data) {
        try {
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter w = factory.createXMLStreamWriter(stream);

            w.writeStartDocument();
            w.writeStartElement("rfc-index");
            for (Document doc : data) {
                if (doc instanceof RFCDocument) {
                    RFCDocument rfc = (RFCDocument) doc;
                    w.writeStartElement("rfc-entry");
                    
                    element(w, "doc-id", doc.getId().toString());
                    element(w, "title", doc.getTitle());
                    
                    for (Author author : rfc.getAuthor()) {
                        w.writeStartElement("author");
                        element(w, "name", author.getName());
                        element(w, "title", author.getTitle());
                        w.writeEndElement();
                    }
                    
                    //TODO: other elements
                    w.writeEndElement();
                } else {
                    String tagName;
                    switch (doc.getId().getType()) {
                        case BCP: tagName = "bcp-entry"; break;
                        case FYI: tagName = "fyi-entry"; break;
                        case STD: tagName = "std-entry"; break;
                        case RFC: tagName = "rfc-not-issued-entry"; break;
                        default:
                            throw new RuntimeException("Unknown type");
                    }
                    w.writeStartElement(tagName);
                    element(w, "doc-id", doc.getId().toString());

                    if (doc.getTitle() != null) {
                        element(w, "title", doc.getTitle());
                    }

                    if (!doc.getReferences(RefType.IS_ALSO).isEmpty()) {
                        w.writeStartElement("is-also");
                        for (DocId id : doc.getReferences(RefType.IS_ALSO)) {
                            element(w, "doc-id", id.toString());
                        }
                        w.writeEndElement();
                    }

                    w.writeEndElement();
                }
            }

            w.writeEndElement();
            w.writeEndDocument();
            w.flush();
            w.close();
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Document> deserialize(InputStream stream) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XMLReader xmlReader = parser.getXMLReader();

            RFCContentHandler handler = new RFCContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(stream));

            return handler.getData();
        } catch (IOException|SAXException|ParserConfigurationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void element(XMLStreamWriter writer, String element, String data) 
            throws XMLStreamException {
        writer.writeStartElement(element);
        writer.writeCharacters(data);
        writer.writeEndElement();
    }
}
