package prog12;

import java.util.*;
import java.net.*;
import java.io.*;

public class URLReader {
    public static void main(String[] args) {
	try {
	    URL url;
	    BufferedReader in;

	    url = new URL(args[0]);
	    in = new BufferedReader(new InputStreamReader(url.openStream()));

	    String line;
	    
	    List<String> words = new ArrayList<String>();
	    List<String> urls = new ArrayList<String>();
	    
	    while ((line = in.readLine()) != null) {
		int n = line.length();
		System.out.println(line);
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
		    URL url2 = new URL(url, s);
		    urls.add(url2.toString());
		}
	    }
	    
	    in.close();
	    
	    System.out.println(words);
	    System.out.println(urls);
	    
	    System.out.println(url.toString());
	    
	    URL url2 = new URL(url, "mailto:search@cs.miami.edu");
	    
	    System.out.println(url2.toString());
	} catch (Exception e) {
	    System.out.println(e);
	}
	
    }
}