package tiddle.search;

import org.apache.log4j.Logger;
import tiddle.util.XmlUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class WikiFileLoader {
    private final static Logger LOG = Logger.getLogger(WikiFileLoader.class);
    private final static List<WikiFileFactory> registeredFileFactories;

    static {
        registeredFileFactories = new ArrayList<>();
        registeredFileFactories.add(new WikiClassicFileFactory());
        registeredFileFactories.add(new WikiModernFileFactory());
    }


    public static WikiFiles getWikiFiles(String[] wikiFilePaths) {
        final WikiFiles wikiFiles = new WikiFiles();
        for (final String wikiFilePath : wikiFilePaths) {
            wikiFiles.add(getWikiFiles(wikiFilePath));
        }
        return wikiFiles;
    }

    public static WikiFiles getWikiFiles(final String wikiFilePath) {
        File file = null;
        List<WikiFile> wikiFiles = new ArrayList<>();

        //Have a look as URL
        try {
            file = new File(new URI(wikiFilePath));
            if (!file.exists()) {
                file = null;
            }
        } catch (Throwable e) {
            final String message = "Failed reading wiki file/dir " + wikiFilePath + " as URL, trying as plain old file";
            LOG.error(message);
        }

        //Have a look as a plain old file
        if (file == null) {
            try {
                file = new File(wikiFilePath);
                if (!file.exists()) {
                    file = null;
                }
            } catch (Throwable e) {
                LOG.error("Failed reading wiki file/dir " + wikiFilePath + " as File, going to try on classpath.");
            }
        }

        //If file is not null then add
        if (file != null) {
            wikiFiles.addAll(getWikiFilesForFileOrDirectory(file));

            //Otherwise last ditch effort, look on classpath
        } else {
            try {
                final String wikiFileContent = XmlUtils.getClasspathFileAsString(wikiFilePath);
                wikiFiles.add(new WikiClassicFile(wikiFilePath, wikiFileContent));
            } catch (Throwable e) {
                final String message = "Failed reading wiki file from classpath.  Giving up on this one.";
                LOG.error(message);
                throw new RuntimeException("Failed reading wiki file at path:" + wikiFilePath + " as a URL, file, or on the classpath.  Current working directory is:" + System.getProperty("user.dir"));
            }
        }

        return new WikiFiles(wikiFiles);
    }


    private static List<WikiFile> getWikiFilesInDirectory(final File directory) {
        List<WikiFile> wikiFiles = new ArrayList<>();
        for (final File file : directory.listFiles()) {
            wikiFiles.addAll(getWikiFilesForFileOrDirectory(file));
        }
        return wikiFiles;
    }


    private static List<WikiFile> getWikiFilesForFileOrDirectory(final File file) {
        List<WikiFile> wikiFiles = new ArrayList<>();
        if (file.isDirectory()) {
            wikiFiles.addAll(getWikiFilesInDirectory(file));
        } else {
            String fileContent;
            try {
                fileContent = XmlUtils.getFileAsString(file);

                if (isSubversionBaseFile(file)) {
                    LOG.debug("Ignoring file at " + file.getAbsolutePath() + " Tiddle thinks that this" +
                            " file is a valid TiddlyWiki files, however it has been " +
                            "detected that this is a subversion system file.");
                } else {
                    final WikiFile wikiFile = getWikiFile(file.getAbsolutePath(), fileContent);
                    if (wikiFile == null) {
                        LOG.debug("Ignoring file at " + file.getAbsolutePath() + " Tiddle doesn't think that this file is a TiddlyWiki file.");
                    } else {
                        wikiFiles.add(new WikiClassicFile(file.getAbsolutePath(), fileContent));
                    }
                }
            } catch (IOException e) {
                LOG.warn("Could not read contents of wikiFile at:" + file.getAbsolutePath());
                //Lets be forgiving and keep pushing through the list of files.
            }
        }
        return wikiFiles;
    }

    private static WikiFile getWikiFile(final String path, final String content) {
        for(final WikiFileFactory wikiFileFactory: registeredFileFactories){
            if(wikiFileFactory.isValidWikiFile(content)){
                return wikiFileFactory.createWikiFile(path, content);
            }
        }
        return null;
    }

    private static boolean isSubversionBaseFile(File file) {
        return file.getAbsolutePath().endsWith(".svn-base");
    }
}
