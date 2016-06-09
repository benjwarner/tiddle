package tiddle;

import java.util.Properties;

public class TiddleProperties {
    private final String[] wikiFilePaths;
    private final String[] hotkeysShow;
    private static TiddleProperties instance;
    private final static String DEFAULT_PROPERTIES_FILENAME = "tiddle";

    private TiddleProperties(final String propertiesFilenameWithoutExtension) {
        Properties config = PropertyFileLoader.getProperties(propertiesFilenameWithoutExtension);
        if (config == null) {
            throw new RuntimeException("Cannot find application.properties. Ensure that this file is on the classpath.");
        } else if (config.get("wiki.url") == null) {
            throw new RuntimeException("Cannot find wiki.url setting in application.properties. Ensure that this setting is specified.");
        }
        final String wikiFilePropertyValue = (String) config.get("wiki.url");
        this.wikiFilePaths = wikiFilePropertyValue.split(";");

        final String hotkeysShowStr = (String) config.get("hotkey.combinations.show");
        this.hotkeysShow = hotkeysShowStr.split(";");
    }

    public static TiddleProperties getInstance() {
        if (instance == null) {
            instance = new TiddleProperties(DEFAULT_PROPERTIES_FILENAME);
        }
        return instance;
    }

    static TiddleProperties getInstance(final String propertiesFilenameWithoutExtension) {
        instance = new TiddleProperties(propertiesFilenameWithoutExtension);
        return instance;
    }

    public String[] getWikiFilePaths() {
        return wikiFilePaths;
    }

    public String[] getHotkeysShow() {
        return hotkeysShow;
    }
}
