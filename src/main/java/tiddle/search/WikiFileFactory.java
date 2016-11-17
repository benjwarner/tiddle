package tiddle.search;

/**
 * User: ben
 * Date: 16/11/2016
 * Time: 6:47 AM
 */
public interface WikiFileFactory {
    boolean isValidWikiFile(String fileContent);
    WikiFile createWikiFile(String path, String content);
}
