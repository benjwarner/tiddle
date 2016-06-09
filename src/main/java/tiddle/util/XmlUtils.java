package tiddle.util;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XmlUtils {

    public static Document stringToDom(String xml) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        return builder.parse(bis);
    }

    /**
     * reads a file from the given fileReader into a string.
     */
    public static String getFileAsString(final Reader fileReader) throws IOException {
        BufferedReader br = new BufferedReader(fileReader);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public static Document getClasspathXmlFileAsDom(final String filePath) throws Exception {
        final String xmlFileContents = getClasspathFileAsString(filePath);
        return stringToDom(xmlFileContents);
    }


    public static String getFileAsString(InputStream is) throws IOException {
        StringBuilder contents = new StringBuilder();
        BufferedReader input = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = null;
        while ((line = input.readLine()) != null) {
            contents.append(line).append("\n");
        }
        input.close();
        return contents.toString();
    }

    public static String getClasspathFileAsString(final String filePath) throws IOException {
        final InputStream is = filePath.getClass().getResourceAsStream(filePath);
        return getFileAsString(is);
    }

    public static void saveFile(final String filename, final String fileContent) throws IOException {
        FileWriter fstream = new FileWriter(filename);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(fileContent);
        out.close();
    }


    public static String escapeCarriageReturns(String str) {
        Pattern regex = Pattern.compile("$.*?^", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.MULTILINE);
        Matcher matcher = regex.matcher(str);
        str = matcher.replaceAll("\\\\n");
        return str;
    }

    public static String trim(String str) {
        Pattern regex = Pattern.compile("^[\\s\\xA0]*", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.MULTILINE);
        Matcher matcher = regex.matcher(str);
        str = matcher.replaceAll("");

        regex = Pattern.compile("[\\s\\xA0]*$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.MULTILINE);
        matcher = regex.matcher(str);
        str = matcher.replaceAll("");

        return str;
    }

    public static String getFileAsString(final File file) throws IOException {
        Reader fileReader = new FileReader(file);
        return getFileAsString(fileReader);
    }

    public static Map<String, String> getFilesInDirectory(final String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            throw new IllegalArgumentException(directoryPath + " does not exist");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directoryPath + " is not a directory");
        }

        Map<String, String> fileMap = new HashMap<>();

        final String files[] = directory.list();
        for (final String fileName : files) {
            final File file = new File(directory.getPath() + "/" + fileName);
            if (file.isFile()) {
                final String fileContents = getFileAsString(file);
                fileMap.put(file.getName(), fileContents);
            }
        }
        return fileMap;
    }
}
