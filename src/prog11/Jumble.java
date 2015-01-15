package prog11;

import java.io.File;
import java.util.Scanner;

import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.List;

import prog08.Tree;
import prog10.BTree;

public class Jumble {
  /**
   * Sort the letters in a word.
   * @param word Input word to be sorted, like "computer".
   * @return Sorted version of word, like "cemoptru".
   */
  public static String sort (String word) {
    char[] sorted = new char[word.length()];

    for (int i =  0; i < word.length(); i++)
      sorted[i] = word.charAt(i);

    Arrays.sort(sorted);

    return new String(sorted, 0, word.length());
  }

  public static void main (String[] args) {
    UserInterface ui = new GUI();
 //   Map<String,String> map = new TreeMap<String,String>();
 //   Map<String,String> map = new DummyList<String,String>();
 //   Map<String,String> map = new SkipList<String,String>();
 //   Map<String,String> map = new Tree<String,String>();
 //   Map<String,String> map = new BTree<String,String>();
    Map<String, List<String>> map = new ChainedHashTable<String, List<String>>();
    Scanner in = null;
    do {
      try {
        in = new Scanner(new File(ui.getInfo("Enter word file.")));
      } catch (Exception e) {
        System.out.println(e);
        System.out.println("Try again.");
      }
    } while (in == null);
	    
    int n = 0;
    while (in.hasNextLine()) {
      String word = in.nextLine();
      if (n++ % 1000 == 0)
	      System.out.println(word + " sorted is " + sort(word));
      
      // EXERCISE: Insert an entry for word into map.
      // What is the key?  What is the value?
      
      List<String> list = map.get(sort(word));
      if(list == null)
    	  list = new ArrayList<String>();
      list.add(word);
      map.put(sort(word), list);
    }
    
    String jumble = ui.getInfo("Enter jumble.");
    
    while (jumble != null) {
      // EXERCISE:  Look up the jumble in the map.
      // What key do you use?
      List<String> words = map.get(sort(jumble));

      if (words == null)
        ui.sendMessage("No match for " + jumble);
      else {
    	String str;
        str = jumble + " unjumbled is " + words.get(0);
      	for(int i = 1; i < words.size(); i++)
      		str += ", " + words.get(i);
      	ui.sendMessage(str);
      }
        jumble = ui.getInfo("Enter jumble.");
    }
    
    int letters;
    String sorted;
    String sorted2;
    	try {
    		sorted = sort(ui.getInfo("Enter letters from clues."));
    		letters = Integer.parseInt(ui.getInfo("How many letters in the first word?"));
    	} catch (Exception e){
    		System.out.println(e);
    		System.out.println("Try again.");
    		return;
    	}
    
    for(String sorted1:map.keySet()) {
    	if(sorted1.length() == letters){
    		StringBuilder sb = new StringBuilder(sorted.length());
    		int index = 0;
    		for(int i = 0; i < sorted.length(); i++) {
    			if(index < letters && sorted.charAt(i) == sorted1.charAt(index))
    				index++;
    			else
    				sb.append(sorted.charAt(i));
    		}
    		if(index == sorted1.length()){
    			sorted2 = sb.toString();
    			if(map.containsKey(sorted2)) {
    				System.out.println(map.get(sorted1) + " " + map.get(sorted2));
    			}
    				
    		}
    		
    	}
    }
  }
}

        
    

