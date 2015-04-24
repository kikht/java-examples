package ru.nsu.java.serialization.sax;

import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import ru.nsu.java.serialization.api.*;

public class RFCContentHandler extends DefaultHandler {
    private final StringBuilder sb = new StringBuilder();
    private List<Document> data;
    private Document curDocument;
    private RFCDocument curRFC;
    private List<DocId> curIds;
    private Author curAuthor;
    private GregorianCalendar curDate;
    private Format curFormat;


    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        sb.setLength(0);
        switch (qName) {
            case "rfc-index":
                data = new ArrayList<>();
                break;
            case "bcp-entry":
            case "std-entry":
            case "fyi-entry":
            case "rfc-not-issued-entry":
                curDocument = new Document();
                break;
            case "rfc-entry":
                curRFC = new RFCDocument();
                curDocument = curRFC;
                break;
            case "obsoletes":
            case "obsoleted-by":
            case "updates":
            case "updated-by":
            case "is-also":
            case "see-also":
                if (curDocument != null) {
                    RefType type = RefType.fromValue(qName);
                    curIds = curDocument.getReferences(type);
                } else {
                    System.out.println("warn reference start");
                }
                break;
            case "author":
                curAuthor = new Author();
                break;
            case "date":
                curDate = new GregorianCalendar();
                break;
            case "format":
                curFormat = new Format();
                break;
            case "doc-id":
            case "title":
            case "name":
            case "month":
            case "year":
            case "day":
            case "file-format":
            case "char-count":
            case "page-count":
            case "current-status":
            case "publication-status":
            case "stream":
            case "errata-url":
            case "keywords":
            case "kw":
            case "abstract":
            case "p":
            case "wg_acronym":
            case "area":
            case "draft":
                //nop
                break;
            default:
                System.out.println("start uri: " + uri + " local: " + localName + " q: " + qName);
        }
    }

    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "bcp-entry":
            case "std-entry":
            case "fyi-entry":
            case "rfc-not-issued-entry":
                data.add(curDocument);
                curDocument = null;
                break;
            case "rfc-entry":
                data.add(curRFC);
                curRFC = null;
                curDocument = null;
                break;
            case "obsoletes":
            case "obsoleted-by":
            case "updates":
            case "updated-by":
            case "is-also":
            case "see-also":
                curIds = null;
                break;
            case "doc-id":
                DocId id = new DocId(sb.toString());
                if (curIds != null) {
                    curIds.add(id);
                } else if (curDocument != null) {
                    curDocument.setId(id);
                } else {
                    System.out.println("warn doc-id end");
                }
                break;
            case "title":
                if (curAuthor != null) {
                    curAuthor.setTitle(sb.toString());
                } else if (curDocument != null) {
                    curDocument.setTitle(sb.toString());
                } else {
                    System.out.println("warn title end");
                }
                break;
            case "author":
                curRFC.getAuthor().add(curAuthor);
                curAuthor = null;
                break;
            case "name":
                curAuthor.setName(sb.toString());
                break;
            case "date":
                curRFC.setDate(curDate.getTime());
                curDate = null;
                break;
            case "month":
                int month=0;
                switch (sb.toString()) {
                    case "January"  : month=Calendar.JANUARY  ; break;
	                case "February" : month=Calendar.FEBRUARY ; break;
	                case "March"    : month=Calendar.MARCH    ; break;
	                case "April"    : month=Calendar.APRIL    ; break;
	                case "May"      : month=Calendar.MAY      ; break;
	                case "June"     : month=Calendar.JUNE     ; break;
	                case "July"     : month=Calendar.JULY     ; break;
	                case "August"   : month=Calendar.AUGUST   ; break;
	                case "September": month=Calendar.SEPTEMBER; break;
	                case "October"  : month=Calendar.OCTOBER  ; break;
	                case "November" : month=Calendar.NOVEMBER ; break;
	                case "December" : month=Calendar.DECEMBER ; break;
                }
                curDate.set(Calendar.MONTH, month);
                break;
            case "year":
                curDate.set(Calendar.YEAR, Integer.valueOf(sb.toString()));
                break;
            case "day":
                curDate.set(Calendar.DAY_OF_MONTH, 
                        Integer.valueOf(sb.toString()));
                break;
            case "format":
                curRFC.getFormat().add(curFormat);
                curFormat = null;
                break;
            case "file-format":
                curFormat.setFileFormat(FileFormat.valueOf(sb.toString()));
                break;
            case "char-count":
                curFormat.setCharCount(Long.valueOf(sb.toString()));
                break;
            case "page-count":
                curFormat.setPageCount(Long.valueOf(sb.toString()));
                break;
            case "current-status":
                curRFC.setCurrentStatus(Status.fromValue(sb.toString()));
                break;
            case "publication-status":
                curRFC.setPublicationStatus(Status.fromValue(sb.toString()));
                break;
            case "stream":
                curRFC.setStream(Stream.fromValue(sb.toString()));
                break;
            case "errata-url":
                curRFC.setErrataUrl(sb.toString());
                break;
            case "kw":
                curRFC.getKeywords().add(sb.toString());
                break;
            case "p":
                curRFC.getAbstract().add(sb.toString());
                break;
            case "wg_acronym":
                curRFC.setWgAcronym(sb.toString());
                break;
            case "area":
                curRFC.setArea(sb.toString());
                break;
            case "draft":
                curRFC.setDraft(sb.toString());
                break;
            case "rfc-index":
            case "keywords":
            case "abstract":
                //nop
                break;
            default:
                System.out.println("end uri: " + uri + " local: " + localName + " q: " + qName);
        }
    }
    
    public void characters(char[] ch, int start, int length) {
        sb.append(ch, start, length);
    }
    
    public List<Document> getData() { return data; }
}
