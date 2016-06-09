package tiddle.search;
import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.junit.Test;

import tiddle.search.WikiFile;


public class WikiFileTest {
	final static String wikiStoreAreaStartTags = "<div id=\"storeArea\">\n";
	final static String wikiStoreAreaContent = "<div title=\"SiteTitle\" modifier=\"Ben\" created=\"200908260028\" changecount=\"1\">\n<pre>Ben's Wiki</pre>\n</div>\n<div title=\"MainMenu\" modifier=\"Ben\" created=\"200908250409\" modified=\"200908262058\" tags=\"Unix Less\" changecount=\"2\"><pre>[[GettingStarted]]\n[[Unix Commands]]\n[[Java]]</pre>\n</div>";
	final static String wikiStoreAreaEndTags = "\n</div>\n<!--POST-STOREAREA-->";
	final static String wikiStoreArea = wikiStoreAreaStartTags + wikiStoreAreaContent + wikiStoreAreaEndTags;
	final static String wikiComplete = "asldkjfalkdsjf" + wikiStoreArea + "asdfasdfasdf";

	@Test
	public void testExtractStoreArea(){
		WikiFile wikiFile = new WikiFile("c:\\fake\\wiki\\path\\mywiki.html", wikiComplete);
		assertEquals(wikiStoreAreaContent.trim(), wikiFile.extractStoreArea());
	}
	
	@Test
	public void testGetDocuments(){
		WikiFile wikiFile = new WikiFile("c:\\fake\\wiki\\path\\mywiki.html", wikiComplete);
		List<Document> docs = wikiFile.getDocuments();
		
		//<div title=\"SiteTitle\" modifier=\"Ben\" created=\"200908260028\" changecount=\"1\">\n<pre>Ben's Wiki</pre>\n</div>
		Document doc1 = docs.get(0);
		assertEquals("SiteTitle", doc1.get("title"));
		assertEquals("Ben", doc1.get("modifier"));
		assertEquals("200908260028", doc1.get("created"));
		assertEquals("1", doc1.get("changecount"));
		assertEquals(null, doc1.get("tags"));
		assertEquals("Ben's Wiki", doc1.get("content"));
		
		//<div title=\"MainMenu\" modifier=\"Ben\" created=\"200908250409\" modified=\"200908262058\" changecount=\"2\"><pre>[[GettingStarted]]\n[[Unix Commands]]\n[[Java]]</pre>\n</div>
		Document doc2 = docs.get(1);
		assertEquals("MainMenu", doc2.get("title"));
		assertEquals("Ben", doc2.get("modifier"));
		assertEquals("200908250409", doc2.get("created"));
		assertEquals("200908262058", doc2.get("modified"));
		assertEquals("2", doc2.get("changecount"));
		assertEquals("Unix Less", doc2.get("tags"));
		assertEquals("[[GettingStarted]]\n[[Unix Commands]]\n[[Java]]", doc2.get("content"));
	}
}
