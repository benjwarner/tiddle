package tiddle.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tiddle.search.WikiToHtml;


public class WikiToHtmlTest {
	@Test
	public void testBold(){
		assertEquals(html("This should be <strong>bold</strong> oh yeah"), WikiToHtml.convertToHTML("This should be ''bold'' oh yeah"));
		assertEquals(html("This should be <strong>bo'ld</strong> oh yeah"), WikiToHtml.convertToHTML("This should be ''bo'ld'' oh yeah"));
		assertEquals(html("This should <pre>be ''not bold'' oh</pre> yeah"), WikiToHtml.convertToHTML("This should {{{be ''not bold'' oh}}} yeah"));
		assertEquals(html("This should be <strong>bold<br/>\nwith a line break</strong> oh yeah"), WikiToHtml.convertToHTML("This should be ''bold\nwith a line break'' oh yeah"));
	}

	@Test
	public void testItalic(){
		assertEquals(html("This should be <em>italic</em> oh yeah"), WikiToHtml.convertToHTML("This should be //italic// oh yeah"));
		assertEquals(html("This should be <em>italic<br/>\nwith a line break</em> oh yeah"), WikiToHtml.convertToHTML("This should be //italic\nwith a line break// oh yeah"));
		assertEquals(html("This should be <pre>not //italic\nwith a line break//</pre> oh yeah"), WikiToHtml.convertToHTML("This should be {{{not //italic\nwith a line break//}}} oh yeah"));
	}

	@Test
	public void testBoldItalic(){
		assertEquals(html("This should be <strong><em>bold italic</em></strong> oh yeah"), WikiToHtml.convertToHTML("This should be ''//bold italic//'' oh yeah"));
		assertEquals(html("This should be <strong><em>bold italic<br/>\nwith a line break</em></strong> oh yeah"), WikiToHtml.convertToHTML("This should be ''//bold italic\nwith a line break//'' oh yeah"));
		assertEquals(html("This should not be <pre>''//bold italic\nwith a line break//''</pre> oh yeah"), WikiToHtml.convertToHTML("This should not be {{{''//bold italic\nwith a line break//''}}} oh yeah"));
	}

	@Test
	public void testUnderline(){
		assertEquals(html("This should be <u>underline</u> oh yeah"), WikiToHtml.convertToHTML("This should be __underline__ oh yeah"));
		assertEquals(html("<pre>This should not be __underline__ oh yeah</pre>"), WikiToHtml.convertToHTML("{{{This should not be __underline__ oh yeah}}}"));
		assertEquals(html("This should be <u>underline<br/>\nwith a line break</u> oh yeah"), WikiToHtml.convertToHTML("This should be __underline\nwith a line break__ oh yeah"));
	}

	@Test
	public void testStrikethrough(){
		assertEquals(html("This should be <strike>strikethrough</strike> oh yeah"), WikiToHtml.convertToHTML("This should be --strikethrough-- oh yeah"));
		assertEquals(html("This should be <strike>strikethrough<br/>\nwith a line break</strike> oh yeah"), WikiToHtml.convertToHTML("This should be --strikethrough\nwith a line break-- oh yeah"));
		assertEquals(html("This should not be <pre>--strikethrough\nwith a line break-- oh yeah</pre>"), WikiToHtml.convertToHTML("This should not be {{{--strikethrough\nwith a line break-- oh yeah}}}"));
	}

	@Test
	public void testSuperscript(){
		assertEquals(html("This should be <sup>superscript</sup> oh yeah"), WikiToHtml.convertToHTML("This should be ^^superscript^^ oh yeah"));
		assertEquals(html("This should be <sup>superscript<br/>\nwith a line break</sup> oh yeah"), WikiToHtml.convertToHTML("This should be ^^superscript\nwith a line break^^ oh yeah"));
		assertEquals(html("<pre>This should not be ^^superscript\nwith a line break^^ oh yeah</pre>"), WikiToHtml.convertToHTML("{{{This should not be ^^superscript\nwith a line break^^ oh yeah}}}"));
	}

	@Test
	public void testSubscript(){
		assertEquals(html("This should be <sub>subscript</sub> oh yeah"), WikiToHtml.convertToHTML("This should be ~~subscript~~ oh yeah"));
		assertEquals(html("This should be <sub>subscript<br/>\nwith a line break</sub> oh yeah"), WikiToHtml.convertToHTML("This should be ~~subscript\nwith a line break~~ oh yeah"));
		assertEquals(html("This should not be <pre>~~subscript</pre>\nwith a line break~~ oh yeah"), WikiToHtml.convertToHTML("This should not be {{{~~subscript}}}\nwith a line break~~ oh yeah"));
	}

	@Test
	public void testHighlighted(){
		assertEquals(html("This should be <span class=\"marked\">highlighted</span> oh yeah"), WikiToHtml.convertToHTML("This should be @@highlighted@@ oh yeah"));
		assertEquals(html("This should not be <pre>@@highlighted@@</pre> oh yeah"), WikiToHtml.convertToHTML("This should not be {{{@@highlighted@@}}} oh yeah"));
		assertEquals(html("This should be <span class=\"marked\">highlighted<br/>\nwith a line break</span> oh yeah"), WikiToHtml.convertToHTML("This should be @@highlighted\nwith a line break@@ oh yeah"));
	}
	
	@Test
	public void testBullets(){
		assertEquals(html("<ul>\n<li>One</li>\n<li>Two</li>\n</ul>"), WikiToHtml.convertToHTML("*One\n*Two"));
	}
	
	@Test
	public void testCode(){
		assertEquals(html("<pre>function myCode(){}</pre>"), WikiToHtml.convertToHTML("{{{function myCode(){}}}}"));
		assertEquals(html("<pre>\nString[] myArray = { \"array\", \"of\", \"String\" };\n</pre>"), WikiToHtml.convertToHTML("{{{\nString[] myArray = { \"array\", \"of\", \"String\" };\n}}}"));
	}

	@Test
	public void testBlankLinesAtStartOfTiddler(){
		assertEquals(html("Hi <br/>\n<br/>\nthere!"), WikiToHtml.convertToHTML("\n\n\t   \nHi \n\nthere!"));
	}

	@Test
	public void testTildaRemoval(){
		assertEquals(html("~myTiddlyWiki MyTiddlyWiki ~my-tiddly-wiki MYTiddlyWiki M2Wiki<br/>\nMyTiddlyWiki MyT"), WikiToHtml.convertToHTML("~myTiddlyWiki ~MyTiddlyWiki ~my-tiddly-wiki ~MYTiddlyWiki ~M2Wiki\n~MyTiddlyWiki MyT"));
	}
	
	@Test
	public void testHeadings(){
		assertEquals(html("test\n<h1>Heading 1</h1>\ntext"), WikiToHtml.convertToHTML("test\n!Heading 1\ntext"));
		assertEquals(html("<h2>Heading 2</h2>\ntext"), WikiToHtml.convertToHTML("!!Heading 2\ntext"));
		assertEquals(html("<h3>Heading 3</h3>"), WikiToHtml.convertToHTML("!!!Heading 3"));
		assertEquals(html("<h4>Heading 4</h4>"), WikiToHtml.convertToHTML("!!!!Heading 4"));
		assertEquals(html("<h5>Heading 5</h5>"), WikiToHtml.convertToHTML("!!!!!Heading 5"));
		assertEquals(html("<h1>Heading 1</h1>\ntext\n<h5>Heading 5</h5>"), WikiToHtml.convertToHTML("!Heading 1\ntext\n!!!!!Heading 5"));
	}
	
	@Test
	public void testTable1(){
		final String table1AsWiki = ""
			+ "|!Header1 left aligned |! Header2 right aligned|!Header3 No alignment|! Header4 center aligned |h\n"
			+ "|bgcolor(#dddddd):Row1 Col1|Row1 Col2|Row1 Col3|Row1 Col4|\n"
			+ "|Left aligned | Right aligned|No alignment| center aligned |\n";
			
		final String table1AsHtml = ""
			+ html("<table class=\"twtable\"><tbody><tr class=\"oddRow\"><th align='left'>Header1 left aligned</th><th align='right'>Header2 right aligned</th><th>Header3 No alignment</th><th align='center'>Header4 center aligned</th></tr>\n"
			+ "<tr class=\"evenRow\"><td style=\"background-color: #dddddd;\">Row1 Col1</td><td>Row1 Col2</td><td>Row1 Col3</td><td>Row1 Col4</td></tr>\n"
			+ "<tr class=\"oddRow\"><td align='left'>Left aligned</td><td align='right'>Right aligned</td><td>No alignment</td><td align='center'>center aligned</td></tr>\n\n</tbody></table>");
		
		assertEquals(table1AsHtml, WikiToHtml.convertToHTML(table1AsWiki));
	}
	
	private static String html(String string) {
		return "<html><body>\n" + string + "\n</body></html>";
	}

	@Test
	public void testLinksWithNoAlternateText(){
		final String internalLinkAsWiki = "asdf [[InternalPage]] fdas";
		final String internalLinkAsHtml = "asdf <a href=\"#InternalPage\">InternalPage</a> fdas";
		final String externalLinkAsWiki = "asdf [[http://www.google.com:8080/myfolder/mypage.jsp?p1=v1&p2=v2]] asdf";
		final String externalLinkAsHtml = "asdf <a href=\"http://www.google.com:8080/myfolder/mypage.jsp?p1=v1&p2=v2\">http://www.google.com:8080/myfolder/mypage.jsp?p1=v1&p2=v2</a> asdf";
		final String bothInternalAndExternalLinksAsWiki = internalLinkAsWiki + externalLinkAsWiki;
		final String bothInternalAndExternalLinksAsHtml = internalLinkAsHtml + externalLinkAsHtml;
		
		assertEquals(html(internalLinkAsHtml), WikiToHtml.convertToHTML(internalLinkAsWiki));
		assertEquals(html(externalLinkAsHtml), WikiToHtml.convertToHTML(externalLinkAsWiki));
		assertEquals(html(bothInternalAndExternalLinksAsHtml), WikiToHtml.convertToHTML(bothInternalAndExternalLinksAsWiki));
	}
	
	@Test
	public void testLinksWithAlternateText(){
		final String internalLinkAsWiki = "asdf [[Internal Page|InternalPage]] fdas";
		final String internalLinkAsHtml = "asdf <a href=\"#InternalPage\">Internal Page</a> fdas";
		final String externalLinkAsWiki = "asdf [[Google|http://www.google.com:8080/myfolder/mypage.jsp?p1=v1&p2=v2]] asdf";
		final String externalLinkAsHtml = "asdf <a href=\"http://www.google.com:8080/myfolder/mypage.jsp?p1=v1&p2=v2\">Google</a> asdf";
		final String bothInternalAndExternalLinksAsWiki = internalLinkAsWiki + externalLinkAsWiki;
		final String bothInternalAndExternalLinksAsHtml = internalLinkAsHtml + externalLinkAsHtml;
		
		assertEquals(html(internalLinkAsHtml), WikiToHtml.convertToHTML(internalLinkAsWiki));
		assertEquals(html(externalLinkAsHtml), WikiToHtml.convertToHTML(externalLinkAsWiki));
		assertEquals(html(bothInternalAndExternalLinksAsHtml), WikiToHtml.convertToHTML(bothInternalAndExternalLinksAsWiki));
	}
	
	@Test
	public void testMultipleLinksInTable(){
		final String linksAsWiki = "|bgcolor(#dddddd):<strong>CSS</strong>|CSS is the computer code used to create custom style sheets. Style sheets are pages that control the look of html webpages. The style sheets for a ~TiddlyWiki file are internal. They are the three default style sheets [[StyleSheetColors]], [[StyleSheetLayout]], [[StyleSheetPrint]] and the one you use to override the other three, named simply [[StyleSheet]].<br><br>To learn more about CSS, see [[here|http://www.htmldog.com/reference/cssproperties/]]. |";
		final String linksAsHtml = 
			"<table class=\"twtable\"><tbody><tr class=\"oddRow\"><td style=\"background-color: #dddddd;\"><strong>CSS</strong></td><td align='left'>CSS is the computer code used to create custom style sheets. Style sheets are pages that control the look of html webpages. The style sheets for a TiddlyWiki file are internal. They are the three default style sheets <a href=\"#StyleSheetColors\">StyleSheetColors</a>, <a href=\"#StyleSheetLayout\">StyleSheetLayout</a>, <a href=\"#StyleSheetPrint\">StyleSheetPrint</a> and the one you use to override the other three, named simply <a href=\"#StyleSheet\">StyleSheet</a>.<br><br>To learn more about CSS, see <a href=\"http://www.htmldog.com/reference/cssproperties/\">here</a>.</td></tr>\n" +
			"</tbody></table>";
		assertEquals(html(linksAsHtml), WikiToHtml.convertToHTML(linksAsWiki));
	}
}

