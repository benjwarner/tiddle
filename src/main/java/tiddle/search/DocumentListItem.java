package tiddle.search;

import org.apache.lucene.document.Document;

public class DocumentListItem {
    final Document document;

    public DocumentListItem(Document document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return document.get("title");
    }

    public Document getDocument() {
        return document;
    }
}
