package tiddle.javafx;

/**
 * User: ben
 * Date: 28/04/2016
 * Time: 5:38 PM
 */
public class ResultsListItem {
    final String title;
    final String tags;
    final String htmlContent;
    final String textContent;

    public ResultsListItem(final String title, final String tags, final String htmlContent, final String textContent) {
        this.title = title;
        this.tags = tags;
        this.htmlContent = htmlContent;
        this.textContent = textContent;
    }
}
