package tiddle.search;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class WikiToHtml {
    private final static Logger LOG = Logger.getLogger(WikiToHtml.class);
    private static List<Replacement> replacements;

    static {
        replacements = new ArrayList<>();

        //Get rid of blank lines at start of tiddler
        replacements.add(new Replacement("(?<![^.])(^[ \t]*$\r?\n)+(?=[^\\s])", ""));

        //Put in html tags
        replacements.add(new Replacement("(.+)", "<html><body>\n$1\n</body></html>"));

        //Code
        replacements.add(new Replacement("(?<!\\{)\\{\\{\\{(.*?)\\}\\}\\}(?!\\})", "<pre>$1</pre>"));

        /**
         * For all expressions after this line, the order should not be mandatory. I.e. each
         * reg-ex should not effect the others.
         */

        //Headings
        replacements.add(new Replacement("^!!!!!!(.*?)$", "<h6>$1</h6>"));
        replacements.add(new Replacement("^!!!!!(.*?)$", "<h5>$1</h5>"));
        replacements.add(new Replacement("^!!!!(.*?)$", "<h4>$1</h4>"));
        replacements.add(new Replacement("^!!!(.*?)$", "<h3>$1</h3>"));
        replacements.add(new Replacement("^!!(.*?)$", "<h2>$1</h2>"));
        replacements.add(new Replacement("^!(.*?)$", "<h1>$1</h1>"));

        //Bold
        replacements.add(new Replacement("''(.*?)''", "<strong>$1</strong>"));

        //Italic
        replacements.add(new Replacement("//(.*?)//", "<em>$1</em>"));

        //Underline
        replacements.add(new Replacement("__(.*?)__", "<u>$1</u>"));

        //Strikethrough
        replacements.add(new Replacement("--(.*?)--", "<strike>$1</strike>"));

        //Superscript
        replacements.add(new Replacement("\\^\\^(.*?)\\^\\^", "<sup>$1</sup>"));

        //Subscript
        replacements.add(new Replacement("~~(.*?)~~", "<sub>$1</sub>"));

        //Highlighted
        replacements.add(new Replacement("@@(.*?)@@", "<span class=\"marked\">$1</span>"));

        //Lists
        //replacements.add(new Replacement("\\*(.*?$(\n\\s*[^*\n].*?$)*)", "<li>$1</li>"));
        replacements.add(new Replacement("\\*(.*?$)", "<li>$1</li>"));
        replacements.add(new Replacement("((<li>[^<]*</li>\\s*\\n)+)", "<ul>\n$1</ul>\n"));

        //Links
        replacements.add(new Replacement("(?-s)\\[\\[([^|\\]]*)\\|(http(?:s)?:[^\\]\n]*)\\]\\]", "<a href=\"$2\">$1</a>"));
        replacements.add(new Replacement("(?-s)\\[\\[(http(?:s)?:[^\\]\n]*)\\]\\]", "<a href=\"$1\">$1</a>"));
        replacements.add(new Replacement("(?-s)\\[\\[([^\\|\\]\n]*)\\|([^\\]\n]*)\\]\\]", "<a href=\"#$2\">$1</a>"));
        replacements.add(new Replacement("(?-s)\\[\\[([^\\]\n]*)\\]\\]", "<a href=\"#$1\">$1</a>"));

        //Tables
        replacements.add(new Replacement("\\|!([^|]*)(?=\\|)", "<th>$1</th>"));
        replacements.add(new Replacement("(<th>.*?</th>\\s*)\\|[hf]", "<tr>$1</tr>"));
        /*
         * This one is kind of weird.  Find: pipe, followed by any character except pipe or
		 * carriage return, with a negative lookahead of ]] at each character.  This is so
		 * that links do not get turned into table cells.  It is still not perfect, as this does
		 * not allow links inside of table cells.  But will do until I can think
		 * of something better.
		 */
        replacements.add(new Replacement("\\|(((?!\\]\\])[^|\\n])*)(?=\\|)", "<td>$1</td>"));
        replacements.add(new Replacement("(<td[^>]*>.*?)\\|", "<tr>$1</tr>"));
        replacements.add(new Replacement("((?:<tr>.*?</tr>\\s*\\n)+)", "<table class=\"twtable\"><tbody>$1</tbody></table>\n"));
        replacements.add(new Replacement("<(t[dh][^>]*)>bgcolor\\(([^)]*)\\):", "<$1 style=\"background-color: $2;\">"));
        replacements.add(new Replacement("(<t[dh][^>]*)>\\s((?:(?<!</t[dh]>).)*?)\\s(</t[dh]>)", "$1 align='center'>$2$3"));
        replacements.add(new Replacement("(<t[dh][^>]*)>\\s((?:(?<!</t[dh]>).)*?)(</t[dh]>)", "$1 align='right'>$2$3"));
        replacements.add(new Replacement("(<t[dh][^>]*)>((?:(?<!</t[dh]>).)*?)\\s(</t[dh]>)", "$1 align='left'>$2$3"));
        replacements.add(new Replacement("(<tr>.*?</tr>\\s*?<tr)(>.*?</tr>)", "$1 class=\"evenRow\"$2"));
        replacements.add(new Replacement("<tr(>.*?</tr>)", "<tr class=\"oddRow\"$1"));

        //Tildas to Negate Links
        replacements.add(new Replacement("(?-i)~([A-Z]\\w*?[A-Z]\\w*)", "$1"));

        //Carriage returns to HTML breaks
        final String blockElements = "</?(?:div|table|tr|td|h\\d|body|pre|th|ul|li|tbody)>";
        replacements.add(new Replacement("(?<!" + blockElements + ")\n(?!" + blockElements + ")", "<br/>\n"));
    }


    public static String convertToHTML(final String wikiText) {
        String returnText = wikiText;
        for (Replacement r : replacements) {
            returnText = r.replace(returnText);
            //LOG.debug("Executing: " + r.toString());
            //LOG.debug(returnText);
        }
        return returnText;
    }
}