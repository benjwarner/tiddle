package tiddle.search;

import java.util.ArrayList;
import java.util.List;

public class WikiToPlainText {
	private static List<Replacement> replacements;
	
	static{
		replacements = new ArrayList<Replacement>();
		
		//Get rid of blank lines at start of tiddler
		replacements.add(new Replacement("(?<![^.])(^[ \t]*$\r?\n)+(?=[^\\s])", ""));
		
		//Headings
		replacements.add(new Replacement("(?<!=)==([^=]+?)==(?!=)", "$1"));
		replacements.add(new Replacement("(?<!=)===([^=]+?)===(?!=)", "$1"));
		replacements.add(new Replacement("(?<!=)====([^=]+?)====(?!=)", "$1"));
		replacements.add(new Replacement("(?<!=)=====([^=]+?)=====(?!=)", "$1"));
		replacements.add(new Replacement("(?<!=)======([^=]+?)======(?!=)", "$1"));
		
		//Lists
		replacements.add(new Replacement("(?<!\\})\\{\\{\\{(.*?)\\}\\}\\}(?!\\})", "$1"));
	}
	
	
	public static String convertToPlainText(final String wikiText){
		String returnText = wikiText;
		for(Replacement r: replacements){
			returnText = r.replace(returnText);
		}
		return returnText;
	}
}

