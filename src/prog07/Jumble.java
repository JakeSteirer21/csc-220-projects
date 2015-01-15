package prog07;

import java.io.File;
import java.util.Scanner;

import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
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
    Map<String,String> map = new BTree<String,String>();
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
      map.put(sort(word), word);
    }

    while (true) {
      String jumble = ui.getInfo("Enter jumble.");
      if (jumble == null)
        return;

      // EXERCISE:  Look up the jumble in the map.
      // What key do you use?
      String word = map.get(sort(jumble));

      if (word == null)
        ui.sendMessage("No match for " + jumble);
      else
        ui.sendMessage(jumble + " unjumbled is " + word);
    }
  }
}

        
    

