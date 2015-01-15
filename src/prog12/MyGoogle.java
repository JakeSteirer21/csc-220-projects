package prog12;

import java.util.*;


public class MyGoogle implements Google {
	
	private List<String> allURLs; //list of all URLs on the web
	private Map<String, Integer> url2index; //list of URLs and the index of each one
	private List<Integer> refCounts; //the number of references to each page on the web
	
	private List<String> allWords; //master list of all words found on web pages
	private Map<String, Integer> word2index; //list of words with index of each one
	private List<List<Integer>> urlIndexLists; //the list of URLs that the word appears on
	
	private class PageComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer page1, Integer page2) {
			return refCounts.get(page1) - refCounts.get(page2);
		}
	}
	
	
	MyGoogle() {
		allURLs = new ArrayList<String>();
		url2index = new TreeMap<String, Integer>();
		refCounts = new ArrayList<Integer>();
		
		allWords = new ArrayList<String>();
		word2index = new HashMap<String, Integer>();
		urlIndexLists = new ArrayList<List<Integer>>();
	}
	
	public void indexURL(String url) {
		allURLs.add(url);
		url2index.put(url, allURLs.size()-1);
		refCounts.add(0);
	}
	
	public void indexWord(String word) {
		allWords.add(word);
		word2index.put(word, allWords.size()-1);
		urlIndexLists.add(new ArrayList<Integer>());
	}
	
	public void gather(Browser browser, List<String> startingURLs) {
		Queue<String> urlQueue = new ArrayDeque<String>();
		
		for(String s:startingURLs)
			if(url2index.get(s) == null) {
				indexURL(s);
				urlQueue.offer(s);
			}
		
		int count = 0;
		while (!urlQueue.isEmpty() && count++ < 100) {
			String url = urlQueue.poll();
			int index = url2index.get(url);
			Set<String> urlsSeen = new HashSet<String>();
			Set<String> wordsSeen = new HashSet<String>();
			System.out.println("dequeued " + url);
			
			if(!browser.loadPage(url))
				continue;

			for(int i = 0; i < browser.getURLs().size(); i++) {
				String s = browser.getURLs().get(i);
				if(url2index.get(s) == null) {
					indexURL(s);
					urlQueue.offer(s);
				}
				if(!urlsSeen.contains(s)) {
					int index1 = url2index.get(s);
					refCounts.set(index1,refCounts.get(index1) + 1);
					urlsSeen.add(s);
				}
			}
			for(int i = 0; i < browser.getWords().size(); i++) {
				String s = browser.getWords().get(i);
				if(word2index.get(s) == null)
					indexWord(s);
				if(!wordsSeen.contains(s))
					urlIndexLists.get(word2index.get(s)).add(index);
					wordsSeen.add(s);
			}
		}
		
		System.out.println("allURLS:");
		System.out.println(allURLs);
		System.out.println("url2index:");
		System.out.println(url2index);
		System.out.println("refCounts:");
		System.out.println(refCounts);
		System.out.println("allWords:");
		System.out.println(allWords);
		System.out.println("word2index:");
		System.out.println(word2index);
		System.out.println("urlIndexLists:");
		System.out.println(urlIndexLists);
	}
	
	public String[] search(List<String> keyWords, int numResults) {
		// Iterator into list of page ids for each key word.
	    Iterator<Integer>[] pageIndexIterators =
	      (Iterator<Integer>[]) new Iterator[keyWords.size()];
	    
	    // Current page index in each list, just ``behind'' the iterator.
	    int[] currentPageIndices = new int[keyWords.size()];
	    
	    // LEAST popular page is at top of heap so if heap has numResults
	    // elements and the next match is better than the least popular page
	    // in the queue, the least popular page can be thrown away.

	    PriorityQueue<Integer> bestPageIndices = new PriorityQueue<Integer>(numResults, new PageComparator());
		
	    for(int i = 0; i < keyWords.size(); i++) {
	    	if(!word2index.containsKey(keyWords.get(i)))
	    		return new String[0];
	    	int index = word2index.get(keyWords.get(i));
	    	pageIndexIterators[i] = urlIndexLists.get(index).iterator();    
	    }
	    while(updateSmallest(currentPageIndices, pageIndexIterators)) {
	    	if(allEqual(currentPageIndices)) {
	    		if(bestPageIndices.size() == numResults ) {
	    			if(refCounts.get(bestPageIndices.peek()) < refCounts.get(currentPageIndices[0])) {
	    				bestPageIndices.poll();
	    				bestPageIndices.offer(currentPageIndices[0]);
	    			}
	    		} else {
	    			bestPageIndices.offer(currentPageIndices[0]);
	    		}
	    	}
	    }
	    
	    String[] results = new String[bestPageIndices.size()];
	    for(int i = results.length - 1; i != -1; i--)
	    	results[i] = allURLs.get(bestPageIndices.poll());
	    
	    
		return results;
	}
	
	    
	    
	    /** Look through currentPageIndices for all the smallest elements.  For
	      each smallest element currentPageIndices[i], load the next element
	      from pageIndexIterators[i].  If pageIndexIterators[i] does not have a
	      next element, return false.  Otherwise, return true.
	      @param currentPageIndices array of current page indices
	      @param pageIndexIterators array of iterators with next page indices
	      @return true if all minimum page indices updates, false otherwise
	  */
	  private boolean updateSmallest
	    (int[] currentPageIndices, Iterator<Integer>[] pageIndexIterators) {
		  int smallest = currentPageIndices[0];
		  for(int i = 0; i < currentPageIndices.length; i++)
			  if(currentPageIndices[i] < smallest)
				  smallest = currentPageIndices[i];
		  for(int i = 0; i < currentPageIndices.length; i++) {
			  if(currentPageIndices[i] == smallest)
				  if(pageIndexIterators[i].hasNext()) {
					  currentPageIndices[i] = pageIndexIterators[i].next();
				  } else {
					  return false;
				  }
		  }
		  return true;
	  }


	  /** Check if all elements in an array are equal.
	      @param array an array of numbers
	      @return true if all are equal, false otherwise
	  */
	  private boolean allEqual (int[] array) {
		  int current = array[0];
		  for(int i = 0; i < array.length; i++)
			  if(current != array[i])
				  return false;
		  return true;
	  }
	    


}
