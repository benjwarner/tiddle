package tiddle.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replacement {
    private static final String NON_FORMATTED_REGEX_STR = "(?:<pre>.*?</pre>)";
    private static final Pattern NON_FORMATTED_REGEX = Pattern.compile(NON_FORMATTED_REGEX_STR, Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.MULTILINE);
    private final String find;
    private final String replace;
    private final Pattern findRegex;

    public Replacement(String find, final String replace) {
        this.find = find;
        this.replace = replace;
        this.findRegex = Pattern.compile(NON_FORMATTED_REGEX + "|" + find, Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.MULTILINE);
    }

    public String replace(String str) {
        StringBuffer resultString = new StringBuffer();
        try {
            final Matcher matcher = findRegex.matcher(str);
            while (matcher.find()) {
                final String match = matcher.group();
                if (!NON_FORMATTED_REGEX.matcher(match).matches()) {
                    matcher.appendReplacement(resultString, replace);
                }
            }
            matcher.appendTail(resultString);
            return resultString.toString();

            //return str.replaceAll("(?sim)" + find, replace);
        } catch (Exception e) {
            throw new RuntimeException("Error in regex: " + find + ":" + replace, e);
        }
    }

    @Override
    public String toString() {
        return "Replacement [find=" + find + ", replace=" + replace + "]";
    }
}
