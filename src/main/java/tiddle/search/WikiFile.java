package tiddle.search;

import org.apache.lucene.document.Document;

import java.util.List;

/**
 * User: ben
 * Date: 15/11/2016
 * Time: 5:40 PM
 */
public interface WikiFile {
    List<Document> getDocuments();
}
