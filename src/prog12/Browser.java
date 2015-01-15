package prog12;

import java.util.List;

public interface Browser {
    /**
     * Load all words and outside references from a web
     * page into lists
     * @param url the URL to load
     * @return true if successful; false if not
     */
	boolean loadPage (String url);
	/**
	 * list of words from web pages 
	 * @return list of words
	 */
    List<String> getWords ();
    /**
     * list of url links on web pages
     * @return list of urls
     */
    List<String> getURLs ();
}

