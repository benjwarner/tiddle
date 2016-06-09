package tiddle.search;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import tiddle.Constants;

public class WikiFile {
    public static final String COULD_NOT_FIND_STORE_AREA_MESSAGE = 
    		"For Tiddle to understand a TiddlyWiki file, the file must contain a " +
    		"valid storeArea.  Store area must exist in a div with id='storeArea'.  " +
    		"It must end with a comment <!--POST-STOREAREA-->";
	private final static Logger LOG = Logger.getLogger(WikiFile.class);
    private final static Pattern EXTRACT_STORE_AREA_REGEX = Pattern.compile("<div\\sid=\"storeArea\">(.*?)</div>\\s*<!--POST-STOREAREA-->", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.MULTILINE);
	private final String path;
	private final String content;
	
	public WikiFile(String path, String content) {
		super();
		this.path = path;
		this.content = content;
	}
	
	public URI toURI(final String withBookmark) throws URISyntaxException{
		URI uri = (new File(path)).toURI();
		String uriStr;
		try {
			uriStr = uri.toString() + "#" + URLEncoder.encode(withBookmark, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		uri = new URI(uriStr);
		return uri;
	}
	
	public List<Document> getDocuments(){
		final String storeArea = extractStoreArea();
		return extractDocumentsFromStoreArea(storeArea);
	}	
	
	protected List<Document> extractDocumentsFromStoreArea(final String storeArea) {
		final Pattern extractTiddlerRegEx = Pattern.compile("<div([^>]*)>\\s*<pre>(.*?)</pre>\\s*</div>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.MULTILINE);
		final Pattern extractTiddlerAttributesRegEx = Pattern.compile("(\\w*?)\\s*=\\s*([\"'])(.*?)\\2");
		
		Matcher tiddlerMatcher = extractTiddlerRegEx.matcher(storeArea);
		final List<Document> elements = new ArrayList<Document>();
			
		while(tiddlerMatcher.find()){
	    	final MatchResult tiddlerMatchResult = tiddlerMatcher.toMatchResult();
	    	Document doc = new Document();
	    	final String wikiContent = tiddlerMatchResult.group(2);
	    	final StringBuffer allFields = new StringBuffer();
	    	
	    	addIfNotEmpty("content", wikiContent, doc, allFields);
	    	
	    	//Extract tiddler attributes from the tiddler div tag.
	    	final String tiddlerAttributes = tiddlerMatchResult.group(1);
	    	//LOG.debug("Parsing tiddler attributes: " + tiddlerAttributes);
	    	final Matcher tiddlerAttributeMatcher = extractTiddlerAttributesRegEx.matcher(tiddlerAttributes);
	    	
	    	//Get all the attributes from the tiddler div tag.  Whack them into the doc.
	    	while(tiddlerAttributeMatcher.find()){
	    		final MatchResult tiddlerAttributeMatchResult = tiddlerAttributeMatcher.toMatchResult();
	    		final String attributeName = tiddlerAttributeMatchResult.group(1);
	    		final String attributeValue = tiddlerAttributeMatchResult.group(3);
	    		addIfNotEmpty(attributeName, attributeValue, doc, allFields);
	    	}
	    	
	    	//Add the html and text content, this is so we can grab them out later to display them.
	    	if( wikiContent != null ){
	    		final String contentHtml = WikiToHtml.convertToHTML(wikiContent);
	    		doc.add(new Field("htmlContent", contentHtml, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    		
	    		final String contentPlainText = WikiToPlainText.convertToPlainText(wikiContent);
	    		doc.add(new Field("textContent", contentPlainText, Field.Store.YES, Field.Index.NOT_ANALYZED));
	    	}
	    	
	    	//Put all the appended fields into the doc.  This allows better searching.
	    	doc.add(new Field("all", allFields.toString(), Field.Store.YES, Field.Index.ANALYZED));

	    	//Work out paths and URLs
	    	doc.add(new Field(Constants.DOC_FIELD_WIKI_FILE_PATH, path, Field.Store.YES, Field.Index.ANALYZED));
			
			try {
				final String title = doc.get("title"); 
				final URI uri = toURI(title);
				final URL url = uri.toURL();
				doc.add(new Field(Constants.DOC_FIELD_WIKI_FILE_URL, url.toExternalForm(), Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field(Constants.DOC_FIELD_WIKI_FILE_URI, uri.toString(), Field.Store.YES, Field.Index.ANALYZED));
			} catch (URISyntaxException e) {
				LOG.warn("Cannot convert path: " + path + " to URI.");
			} catch (MalformedURLException e) {
				LOG.warn("Cannot convert path: " + path + " to URL.");
			}
	    	
	    	elements.add(doc);
		}		
		return elements;
	}

	protected String extractStoreArea() {
		final Matcher extractStoreAreaMatcher = EXTRACT_STORE_AREA_REGEX.matcher(content);
		if(!extractStoreAreaMatcher.find()){
			throw new RuntimeException(COULD_NOT_FIND_STORE_AREA_MESSAGE);
		}
    	final String storeArea = extractStoreAreaMatcher.group(1).trim();
		return storeArea;
	}

	private void addIfNotEmpty(String fieldName, String fieldValue, Document doc, StringBuffer allFields) {
		if(fieldValue != null && fieldValue.trim().length() > 0){
			//LOG.debug("Adding attribute: " + fieldName + ":" + fieldValue);
			doc.add(new Field(fieldName, fieldValue, Field.Store.YES, Field.Index.ANALYZED));
			allFields.append(" ").append(fieldValue);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		WikiFile other = (WikiFile) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	public String getPathAsString(){
		return path;
	}

	public static boolean isValidWikiFile(String fileContent) {
		return(EXTRACT_STORE_AREA_REGEX.matcher(fileContent).find());
	}
}
