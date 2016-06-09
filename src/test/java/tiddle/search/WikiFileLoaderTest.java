package tiddle.search;

import org.junit.Test;
import tiddle.util.XmlUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class WikiFileLoaderTest {
    @Test
    public void testLoadSingleFileFromClasspath() throws IOException {
        final String filePath = "/wikis/tiddle-help_20091015.html";
        WikiFiles actualWikiFiles = WikiFileLoader.getWikiFiles(filePath);
        assertTrue(actualWikiFiles.size() == 1);

        final String expectedFileContent = XmlUtils.getClasspathFileAsString(filePath);
        WikiFile expectedWikiFile = new WikiFile(filePath, expectedFileContent);
        WikiFiles expectedWikiFiles = WikiFiles.forSingleWikiFile(expectedWikiFile);
        assertEquals(expectedWikiFiles, actualWikiFiles);
    }

    @Test
    public void testLoadSingleFileFromStraightFile() throws IOException {
        final String filePath = "./src/test/resources/wikis/tiddle-help_20091015.html";
        WikiFiles actualWikiFiles = WikiFileLoader.getWikiFiles(filePath);
        assertTrue(actualWikiFiles.size() == 1);

        final String expectedFileContent = XmlUtils.getFileAsString(new File(filePath));
        WikiFile expectedWikiFile = new WikiFile(new File(filePath).getAbsolutePath(), expectedFileContent);
        WikiFiles expectedWikiFiles = WikiFiles.forSingleWikiFile(expectedWikiFile);
        assertEquals(expectedWikiFiles, actualWikiFiles);
    }

    @Test
    public void testLoadMultipleFilesFromRelativeDirectory() throws IOException {
        final String filePath = "./src/test/resources/wikis/";
        WikiFiles actualWikiFiles = WikiFileLoader.getWikiFiles(filePath);
        assertTrue(actualWikiFiles.size() == 2);

        final String filePath1 = "./src/test/resources/wikis/tiddle-help_20091015.html";
        final String filePath2 = "./src/test/resources/wikis/tiddly-wiki_20091015.html";

        final String expectedFileContent1 = XmlUtils.getFileAsString(new File(filePath1));
        final String expectedFileContent2 = XmlUtils.getFileAsString(new File(filePath2));

        final WikiFile expectedWikiFile1 = new WikiFile(new File(filePath1).getAbsolutePath(), expectedFileContent1);
        final WikiFile expectedWikiFile2 = new WikiFile(new File(filePath2).getAbsolutePath(), expectedFileContent2);
        final List<WikiFile> expectedWikiFilesList = new ArrayList<>();
        expectedWikiFilesList.add(expectedWikiFile1);
        expectedWikiFilesList.add(expectedWikiFile2);
        final WikiFiles expectedWikiFiles = new WikiFiles(expectedWikiFilesList);

        assertEquals(expectedWikiFiles, actualWikiFiles);
    }

    @Test
    public void testLoadMultipleFilesFromRelativeNestedDirectory() throws IOException {
        final String dirPath = "./src/test/resources/nested_wiki_directory";
        WikiFiles actualWikiFiles = WikiFileLoader.getWikiFiles(dirPath);
        assertTrue(actualWikiFiles.size() == 3);

        final String filePath1 = "./src/test/resources/nested_wiki_directory/tiddle-help_20091015.html";
        final String filePath2 = "./src/test/resources/nested_wiki_directory/tiddly-wiki_20091015.html";
        final String filePath3 = "./src/test/resources/nested_wiki_directory/another_directory/twfortherestofus_20091016.html";

        final String expectedFileContent1 = XmlUtils.getFileAsString(new File(filePath1));
        final String expectedFileContent2 = XmlUtils.getFileAsString(new File(filePath2));
        final String expectedFileContent3 = XmlUtils.getFileAsString(new File(filePath3));

        final WikiFile expectedWikiFile1 = new WikiFile(new File(filePath1).getAbsolutePath(), expectedFileContent1);
        final WikiFile expectedWikiFile2 = new WikiFile(new File(filePath2).getAbsolutePath(), expectedFileContent2);
        final WikiFile expectedWikiFile3 = new WikiFile(new File(filePath3).getAbsolutePath(), expectedFileContent3);
        final List<WikiFile> expectedWikiFilesList = new ArrayList<>();
        expectedWikiFilesList.add(expectedWikiFile3);
        expectedWikiFilesList.add(expectedWikiFile1);
        expectedWikiFilesList.add(expectedWikiFile2);

        final WikiFiles expectedWikiFiles = new WikiFiles(expectedWikiFilesList);
        assertEquals(expectedWikiFiles, actualWikiFiles);
    }
}
