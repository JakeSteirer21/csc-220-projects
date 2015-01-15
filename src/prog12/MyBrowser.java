package prog12;

import java.util.*;
import java.net.*;
import java.io.*;

public class MyBrowser implements Browser {
    private List<String> words;
    private List<String> urls;

    public List<String> getWords () { return words; }
    public List<String> getURLs () { return urls; }

    public boolean loadPage (String urlString) {
      if (urlString.equals("http://www.cs.miami.edu/~vjm"))
        System.out.println("this is it");

	words = new ArrayList<String>();
	urls = new ArrayList<String>();
	    
	try {
	    URL url;
	    BufferedReader in;

	    url = new URL(urlString);
	    in = new BufferedReader(new InputStreamReader(url.openStream()));

	    String line;
	    
	    while ((line = in.readLine()) != null) {
		int n = line.length();
		// System.out.println(line);
		for (int i = 0; i < n; i++) {
		    if (!Character.isLetter(line.charAt(i)))
			continue;
		    for (int j = i+1; j <= n; j++)
			if (j == n || !Character.isLetter(line.charAt(j))) {
			    words.add(line.substring(i, j));
			    i = j;
			    break;
			}
		}

		for (int i = 0; i < n - 5; i++) {
		    if (!line.substring(i, i + 5).toLowerCase().equals("href="))
			continue;
		    String s = line.substring(i + 6);
		    int ind = s.indexOf('\"');
		    if (ind != -1)
			s = s.substring(0, s.indexOf('\"'));
		    if (s.length() > 3 &&
			s.substring(s.length() - 3).equals("pdf"))
			continue;
		    if (s.length() > 3 &&
			s.substring(s.length() - 3).equals("mp4"))
			continue;
		    if (s.length() > 3 &&
			s.substring(s.length() - 3).equals("mov"))
			continue;
		    if (s.length() > 3 &&
			s.substring(s.length() - 3).equals("tgz"))
			continue;
		    URL url2 = new URL(url, s);
		    urls.add(url2.toString());
		}
	    }
	    
	    in.close();
	    
	    // System.out.println(words);
	    // System.out.println(urls);
	    // System.out.println(url.toString());
	} catch (Exception e) {
	    System.out.println(e);
	    return false;
	}
	return true;
    }
}

