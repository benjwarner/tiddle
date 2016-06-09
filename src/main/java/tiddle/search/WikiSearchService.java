package tiddle.search;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.util.List;

public class WikiSearchService {
    private final static Logger LOG = Logger.getLogger(WikiSearchService.class);
    private final Analyzer analyzer;
    private final Directory index;
    private String[] wikiFilePaths;

    public WikiSearchService(Analyzer analyzer, Directory index) {
        super();
        this.analyzer = analyzer;
        this.index = index;
    }

    public static WikiSearchService build(final String wikiFilePaths[]) {
        final WikiFiles wikiFiles = WikiFileLoader.getWikiFiles(wikiFilePaths);
        final WikiSearchService wikiSearchService = WikiSearchService.forDocuments(wikiFiles.getDocumentsForAllWikiFiles());
        wikiSearchService.wikiFilePaths = wikiFilePaths;
        return wikiSearchService;
    }

    public WikiSearchService rebuild() {
        return build(this.wikiFilePaths);
    }

    public static WikiSearchService forDocuments(final List<Document> documents) {
        try {
            // 0. Specify the analyzer for tokenizing text.
            // The same analyzer should be used for indexing and searching
            final StandardAnalyzer analyzer = new StandardAnalyzer();

            // 1. create the index
            final Directory index = new RAMDirectory();

            // the boolean arg in the IndexWriter ctor means to
            // create a new index, overwriting any existing index
            final IndexWriter w = new IndexWriter(index, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
            for (Document doc : documents) {
                w.addDocument(doc);
            }
            w.close();
            return new WikiSearchService(analyzer, index);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Search search(final String query) {
        try {
            // the "title" arg specifies the default field to use
            // when no field is explicitly specified in the query.
            QueryParser qp = new QueryParser("all", analyzer);
            final Query q = qp.parse(query);

            // 3. search
            int hitsPerPage = 10;
            final IndexSearcher searcher = new IndexSearcher(index);
            final TopDocCollector collector = new TopDocCollector(hitsPerPage);
            searcher.search(q, collector);
            final ScoreDoc[] hits = collector.topDocs().scoreDocs;

            //Return the search object
            return new Search(searcher, hits);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
