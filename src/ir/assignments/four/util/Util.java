package ir.assignments.four.util;

import ir.assignments.two.helper.TestHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Util {
	
	private static final String STOPWORDSPATH = "Stopwords.txt";
	private static HashSet<String> stopwords = new HashSet<String>();
	
	/** get list of all files in path **/
	public static List<File> getFilesInPath(String path) {
		List<File> files = new ArrayList<File>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; ++i) {
			if (listOfFiles[i].isFile()) {
				files.add(listOfFiles[i]);
			} else if (listOfFiles[i].isDirectory()) {
				files.addAll(getFilesInPath(listOfFiles[i].getAbsolutePath()));
			}
		}
		
		return files;
	}

	/** tokenizes HTML file, omitting all stopwords **/
	public static ArrayList<String> tokenizeHTMLFile(File input) {
		initStopWords(STOPWORDSPATH);
		
		ArrayList<String> result = new ArrayList<String>();
		StringBuilder token = new StringBuilder();
		String tokenString;
		int size = 0;
		Document doc = null;

		try {
			doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			if (doc == null) {
				return result;
			}
			for (char c : doc.select("body").text().replaceAll("<[^>]*>|&[^>]*;", "").toCharArray()) {
				if (c == -1) {
					if (token.length() != 0) {
						tokenString = token.toString().toLowerCase();
						if (!stopwords.contains(tokenString)) {
							result.add(tokenString);
						}
					}
					break; // EOF
				}
				if (isValidChar(c)) {
					// append c to end of accumulated token
					token.append(c);
				} else {
					if (token.length() != 0) {
						// add accumulated token to list
						tokenString = token.toString().toLowerCase();
						if (!stopwords.contains(tokenString) && tokenString.length() > 2
								&& tokenString.length() < 20) {
							result.add(tokenString);
						}
						// clear token object
						token.delete(0, token.length());
						++size;
						if (size % 100000 == 0) {
							TestHelper.debugMessage("Size of Tokens List: " + size);
						}
					}
				}
			}
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
		return result;
	}

	/** init stop words **/
	public static void initStopWords(String path) {
		if (!stopwords.isEmpty()) {
			return;
		}
		BufferedReader br = null;

		try {
			String currentLine;
			br = new BufferedReader(new FileReader(path));
			while ((currentLine = br.readLine()) != null) {
				stopwords.add(currentLine.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Determines if char c is valid char in token.
	 * 
	 * @param c Char to examine.
	 * @return Validity of c.
	 */
	private static boolean isValidChar(int c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
	}

}