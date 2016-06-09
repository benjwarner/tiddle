package tiddle.search;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;

public class Search {
    private final IndexSearcher searcher;
    private final ScoreDoc[] hits;

    public Search(IndexSearcher searcher, ScoreDoc[] hits) {
        super();
        this.searcher = searcher;
        this.hits = hits;
    }

    public IndexSearcher getSearcher() {
        return searcher;
    }

    public ScoreDoc[] getHits() {
        return hits;
    }
}
