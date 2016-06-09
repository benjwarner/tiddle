package tiddle.search;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.junit.Test;

import tiddle.search.Search;
import tiddle.search.WikiFile;
import tiddle.search.WikiSearchService;
import tiddle.util.XmlUtils;


public class WikiSearchServiceTest {
    private final static Logger LOG = Logger.getLogger(WikiSearchServiceTest.class);

	@Test
	public void testSearch() throws Exception{
		final String filePath = "/wikis/tiddle-help_20091015.html";
		final String wikiFileContent = XmlUtils.getClasspathFileAsString(filePath);
		final WikiFile wikiFile = new WikiFile(filePath, wikiFileContent);
		
		WikiSearchService searchService = WikiSearchService.forDocuments(wikiFile.getDocuments());
		final Search results = searchService.search("Unix");
		LOG.debug(results);
		
	    LOG.debug("Found " + results.getHits().length + " hits.");
	    for(int i=0;i<results.getHits().length;++i) {
	      int docId = results.getHits()[i].doc;
	      Document d = results.getSearcher().doc(docId);
	      LOG.debug((i + 1) + ". " + d.get("title"));
	    }
	}
}
