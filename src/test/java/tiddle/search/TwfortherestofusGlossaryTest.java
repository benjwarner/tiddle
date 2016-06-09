package tiddle.search;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TwfortherestofusGlossaryTest {
    @Test
    public void testGlossary() {
        final String input =
                "|bgcolor(#dddddd):''Case-sensitive search''|A function of ~TiddlyWiki whereby the user can search for a word or phrase with precise upper and lowercase specifications.|\n" +
                        "|bgcolor(#dddddd):''Client-side''|A ~TiddlyWiki file that is edited offline by the user or client, hence \"On the client's side.\" These cannot be edited through the Internet when the file is located on a web server.|\n" +
                        "|bgcolor(#dddddd):''CSS''|CSS is the computer code used to create custom style sheets. Style sheets are pages that control the look of html webpages. The style sheets for a ~TiddlyWiki file are internal. They are the three default style sheets [[StyleSheetColors]], [[StyleSheetLayout]], [[StyleSheetPrint]] and the one you use to override the other three, named simply [[StyleSheet]].<br><br>To learn more about CSS, see [[here|http://www.htmldog.com/reference/cssproperties/]]. |\n" +
                        "|bgcolor(#dddddd):''Default Tiddlers''|This is a tiddler that contains the tiddlers that are viewed when your file opens up. You can customize this tiddler or empty it out if you wish.|\n" +
                        "|bgcolor(#dddddd):''HTML''|HTML is the computer language used to create webpages. A ~TiddlyWiki file is a modified HTML file.|\n" +
                        "|bgcolor(#dddddd):''~JavaScript''|~JavaScript is a computer language used for special functions. It is made of up scripts or instructions that tell a webpage what to do under certain conditions. Java scripts are used to create and run many ~TiddlyWiki plugins.|\n" +
                        "|bgcolor(#dddddd):''Macro''|See [[this tiddler|Macros]]|\n" +
                        "|bgcolor(#dddddd):''~MainMenu''|The menu normally found on the left hand side of the screen. In the present file it is found in the header. It acts as a table of contents.|\n" +
                        "|bgcolor(#dddddd):''Options panel''|The menu in the middle of the right hand menu. Sometimes it is hidden, and you can open it by clicking \"options >>\".|\n" +
                        "|bgcolor(#dddddd):''Permalink''|A ~TiddlyWiki button on the tiddlers that places the exact url of the tiddler in your browser search window. Helpful for placing links to a specific tiddler in e-mails and webpages. |\n" +
                        "|bgcolor(#dddddd):''Permaview''|A ~TiddlyWiki option on the right hand menu that places the exact url of the present file when all the currently open tiddlers are open. Helpful for placing links to a specific combination of tiddlers in e-mails and webpages.|\n" +
                        "|bgcolor(#dddddd):''Plugin''|A tiddler with a special code that adds extra functionality to a ~TiddlyWiki file. For more information, see [[this tiddler|How to install a plugin]]|\n" +
                        "|bgcolor(#dddddd):''Server-side''|A ~TiddlyWiki file that is able to be edited while the file is located on a web server. This gives users the ability to edit it from any online computer. |\n" +
                        "|bgcolor(#dddddd):''Shadow tiddlers''|Tiddlers that work in the background and affect the functioning of ~TiddlyWiki. See also [[Special note on modifying shadow tiddlers]]|\n" +
                        "|bgcolor(#dddddd):''~SideBar''|The options at the top of the right hand menu.|\n" +
                        "|bgcolor(#dddddd):''Slider''|An expandable-and-collapsible menu that you can place in any tiddler. See [[here (external link)]] for a helpful showcase of four sliders and what to use them for.|\n" +
                        "|bgcolor(#dddddd):''~StyleSheet''|A tiddler with the code that controls the appearance, layout and printing properties of a ~TiddlyWiki file. For more information, see [[this tiddler|Messing with StyleSheets]]|\n" +
                        "|bgcolor(#dddddd):''systemConfig tag''| A tag that tells ~TiddlyWiki that the tiddler contains Javascript that should be run when the file opens. Usually appended to plugins.|\n" +
                        "|bgcolor(#dddddd):''Tabs''|The menu at the bottom of the right hand menu. They are called tabs because they have what looks like the tabs on file folders protruding from the top of them. |\n" +
                        "|bgcolor(#dddddd):''Tag''|A label that a user adds to a tiddler to index it according to topic. In other words, the tag is the topic or category by which you want to index and search for your tiddler. This provides an extra way to find tiddlers, because the user can go to the Tags tab in the right hand tabbed menu and browse his or her personal list of tags. See [[this tidder|through tags]]|\n" +
                        "|bgcolor(#dddddd):''Tiddler''|A 'window' containing text or code in ~TiddlyWiki. Tiddlers are like 3x5 note cards to which the user adds content and links with other tiddlers using tags and hyperlinks. For more on tiddlers, see [[this tiddler|Anatomy of a Tiddler]]. |\n" +
                        "|bgcolor(#dddddd):''~WikiWord''|See [[this tiddler|WikiWord]]|\n";

        final String expectedOutput =
                "<table class=\"twtable\"><tbody><tr class=\"oddRow\"><td style=\"background-color: #dddddd;\"><strong>Case-sensitive search</strong></td><td>A function of TiddlyWiki whereby the user can search for a word or phrase with precise upper and lowercase specifications.</td></tr>\n" +
                        "<tr class=\"evenRow\"><td style=\"background-color: #dddddd;\"><strong>Client-side</strong></td><td>A TiddlyWiki file that is edited offline by the user or client, hence \"On the client's side.\" These cannot be edited through the Internet when the file is located on a web server.</td></tr>\n" +
                        "<tr class=\"oddRow\"><td style=\"background-color: #dddddd;\"><strong>CSS</strong></td><td align='left'>CSS is the computer code used to create custom style sheets. Style sheets are pages that control the look of html webpages. The style sheets for a TiddlyWiki file are internal. They are the three default style sheets <a href=\"#StyleSheetColors\">StyleSheetColors</a>, <a href=\"#StyleSheetLayout\">StyleSheetLayout</a>, <a href=\"#StyleSheetPrint\">StyleSheetPrint</a> and the one you use to override the other three, named simply <a href=\"#StyleSheet\">StyleSheet</a>.<br><br>To learn more about CSS, see <a href=\"http://www.htmldog.com/reference/cssproperties/\">here</a>.</td></tr>\n" +
                        "<tr class=\"evenRow\"><td style=\"background-color: #dddddd;\"><strong>Default Tiddlers</strong></td><td>This is a tiddler that contains the tiddlers that are viewed when your file opens up. You can customize this tiddler or empty it out if you wish.</td></tr>\n" +
                        "<tr class=\"oddRow\"><td style=\"background-color: #dddddd;\"><strong>HTML</strong></td><td>HTML is the computer language used to create webpages. A TiddlyWiki file is a modified HTML file.</td></tr>\n" +
                        "<tr class=\"evenRow\"><td style=\"background-color: #dddddd;\"><strong>JavaScript</strong></td><td>JavaScript is a computer language used for special functions. It is made of up scripts or instructions that tell a webpage what to do under certain conditions. Java scripts are used to create and run many TiddlyWiki plugins.</td></tr>\n" +
                        "<tr class=\"oddRow\"><td style=\"background-color: #dddddd;\"><strong>Macro</strong></td><td>See <a href=\"#Macros\">this tiddler</a></td></tr>\n" +
                        "<tr class=\"evenRow\"><td style=\"background-color: #dddddd;\"><strong>MainMenu</strong></td><td>The menu normally found on the left hand side of the screen. In the present file it is found in the header. It acts as a table of contents.</td></tr>\n" +
                        "<tr class=\"oddRow\"><td style=\"background-color: #dddddd;\"><strong>Options panel</strong></td><td>The menu in the middle of the right hand menu. Sometimes it is hidden, and you can open it by clicking \"options >>\".</td></tr>\n" +
                        "<tr class=\"evenRow\"><td style=\"background-color: #dddddd;\"><strong>Permalink</strong></td><td align='left'>A TiddlyWiki button on the tiddlers that places the exact url of the tiddler in your browser search window. Helpful for placing links to a specific tiddler in e-mails and webpages.</td></tr>\n" +
                        "<tr class=\"oddRow\"><td style=\"background-color: #dddddd;\"><strong>Permaview</strong></td><td>A TiddlyWiki option on the right hand menu that places the exact url of the present file when all the currently open tiddlers are open. Helpful for placing links to a specific combination of tiddlers in e-mails and webpages.</td></tr>\n" +
                        "<tr class=\"evenRow\"><td style=\"background-color: #dddddd;\"><strong>Plugin</strong></td><td>A tiddler with a special code that adds extra functionality to a TiddlyWiki file. For more information, see <a href=\"#How to install a plugin\">this tiddler</a></td></tr>\n" +
                        "<tr class=\"oddRow\"><td style=\"background-color: #dddddd;\"><strong>Server-side</strong></td><td align='left'>A TiddlyWiki file that is able to be edited while the file is located on a web server. This gives users the ability to edit it from any online computer.</td></tr>\n" +
                        "<tr class=\"evenRow\"><td style=\"background-color: #dddddd;\"><strong>Shadow tiddlers</strong></td><td>Tiddlers that work in the background and affect the functioning of TiddlyWiki. See also <a href=\"#Special note on modifying shadow tiddlers\">Special note on modifying shadow tiddlers</a></td></tr>\n" +
                        "<tr class=\"oddRow\"><td style=\"background-color: #dddddd;\"><strong>SideBar</strong></td><td>The options at the top of the right hand menu.</td></tr>\n" +
                        "<tr class=\"evenRow\"><td style=\"background-color: #dddddd;\"><strong>Slider</strong></td><td>An expandable-and-collapsible menu that you can place in any tiddler. See <a href=\"#here (external link)\">here (external link)</a> for a helpful showcase of four sliders and what to use them for.</td></tr>\n" +
                        "<tr class=\"oddRow\"><td style=\"background-color: #dddddd;\"><strong>StyleSheet</strong></td><td>A tiddler with the code that controls the appearance, layout and printing properties of a TiddlyWiki file. For more information, see <a href=\"#Messing with StyleSheets\">this tiddler</a></td></tr>\n" +
                        "<tr class=\"evenRow\"><td style=\"background-color: #dddddd;\"><strong>systemConfig tag</strong></td><td align='right'>A tag that tells TiddlyWiki that the tiddler contains Javascript that should be run when the file opens. Usually appended to plugins.</td></tr>\n" +
                        "<tr class=\"oddRow\"><td style=\"background-color: #dddddd;\"><strong>Tabs</strong></td><td align='left'>The menu at the bottom of the right hand menu. They are called tabs because they have what looks like the tabs on file folders protruding from the top of them.</td></tr>\n" +
                        "<tr class=\"evenRow\"><td style=\"background-color: #dddddd;\"><strong>Tag</strong></td><td>A label that a user adds to a tiddler to index it according to topic. In other words, the tag is the topic or category by which you want to index and search for your tiddler. This provides an extra way to find tiddlers, because the user can go to the Tags tab in the right hand tabbed menu and browse his or her personal list of tags. See <a href=\"#through tags\">this tidder</a></td></tr>\n" +
                        "<tr class=\"oddRow\"><td style=\"background-color: #dddddd;\"><strong>Tiddler</strong></td><td align='left'>A 'window' containing text or code in TiddlyWiki. Tiddlers are like 3x5 note cards to which the user adds content and links with other tiddlers using tags and hyperlinks. For more on tiddlers, see <a href=\"#Anatomy of a Tiddler\">this tiddler</a>.</td></tr>\n" +
                        "<tr class=\"evenRow\"><td style=\"background-color: #dddddd;\"><strong>WikiWord</strong></td><td>See <a href=\"#WikiWord\">this tiddler</a></td></tr>\n\n</tbody></table>";

        assertEquals(html(expectedOutput), WikiToHtml.convertToHTML(input));
    }

    private static String html(String string) {
        return "<html><body>\n" + string + "\n</body></html>";
    }
}

