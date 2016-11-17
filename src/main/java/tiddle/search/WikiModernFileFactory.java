package tiddle.search;

/**
 * User: ben
 * Date: 16/11/2016
 * Time: 6:49 AM
 */
public class WikiModernFileFactory implements WikiFileFactory {
    @Override
    public boolean isValidWikiFile(final String fileContent) {
        return (WikiModernFile.EXTRACT_STORE_AREA_REGEX.matcher(fileContent).find());
    }

    @Override
    public WikiFile createWikiFile(final String path, final String content) {
        return new WikiModernFile(path, content);
    }
}
