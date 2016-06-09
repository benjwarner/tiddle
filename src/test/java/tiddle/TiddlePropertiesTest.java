package tiddle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;


public class TiddlePropertiesTest {
	@Test
	public void testLoadProperties(){
		String[] wikiFilePaths = TiddleProperties.getInstance("tiddle-test").getWikiFilePaths();
		assertNotNull(wikiFilePaths);
		assertEquals(new String[]{"/wikis/tiddle-help_20091015.html","/wikis/tiddly-wiki_20091015.html"}, wikiFilePaths);
	}
}
