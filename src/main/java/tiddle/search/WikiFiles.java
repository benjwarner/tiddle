package tiddle.search;

import org.apache.lucene.document.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WikiFiles {
    private List<WikiFile> wikiFiles;

    public WikiFiles(List<WikiFile> wikiFiles) {
        this.wikiFiles = wikiFiles;
    }

    public WikiFiles() {
        wikiFiles = new ArrayList<>();
    }

    public List<Document> getDocumentsForAllWikiFiles() {
        final List<Document> documentsForAllFiles = new ArrayList<>();
        for (final WikiFile wikiFile : wikiFiles) {
            final List<Document> documentsForFile = wikiFile.getDocuments();
            documentsForAllFiles.addAll(documentsForFile);
        }
        return documentsForAllFiles;
    }

    public static WikiFiles forSingleWikiFile(WikiFile wikiFile) {
        final List<WikiFile> wikiFiles = new ArrayList<>();
        wikiFiles.add(wikiFile);
        return new WikiFiles(wikiFiles);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((wikiFiles == null) ? 0 : wikiFiles.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WikiFiles other = (WikiFiles) obj;
        if (wikiFiles == null) {
            if (other.wikiFiles != null)
                return false;
            else return true;
        } else if (wikiFiles.size() != other.wikiFiles.size()) {
            return false;
        } else {
            Iterator<WikiFile> otherIterator = other.wikiFiles.iterator();
            for (WikiFile wikiFile : wikiFiles) {
                WikiFile otherWikiFile = otherIterator.next();
                if (!wikiFile.equals(otherWikiFile)) {
                    return false;
                }
            }
            return true;
        }
    }

    public int size() {
        return wikiFiles.size();
    }

    public void add(WikiFiles other) {
        this.wikiFiles.addAll(other.wikiFiles);
    }
}
