package prog08;

import prog02.GUI;

import java.util.Scanner;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WordGame {
  private static class Node {
    String word;
    Node previous;
    
    Node (String word) {
      this.word = word;
    }
  }
  
  static GUI ui = new GUI();
  
  public static void main (String[] args) {
    WordGame game = new WordGame();
    game.loadDictionary(ui.getInfo("Enter dictionary file:"));
    
    String start = ui.getInfo("Enter starting word:");
    String target = ui.getInfo("Enter target word:");
    
    String[] commands = { "Computer plays.", "Human plays." };
    int c = ui.getCommand(commands);
    
    if (c == 1)
      game.play(start, target);
    else
      game.solve(start, target);
  }
  
  void play (String start, String target) {
    while (true) {
      ui.sendMessage("Current word: " + start + "\n" +
                     "Target word: " + target);
      String word = ui.getInfo("What is your next word?");
      if (find(word) == null)
        ui.sendMessage(word + " is not in the dictionary.");
      else if (!oneDegree(start, word))
        ui.sendMessage("Sorry, but " + word +
                       " differs by more than one letter from " + start);
      else if (word.equals(target)) {
        ui.sendMessage("You win!");
        return;
      }
      else
        start = word;
    }
  }    
  
  static boolean oneDegree (String snow, String slow) {
    if (snow.length() != slow.length())
      return false;
    int count = 0;
    for (int i = 0; i < snow.length(); i++)
      if (snow.charAt(i) != slow.charAt(i))
        count++;
    return count == 1;
  }
  
  List<Node> nodes = new ArrayList<Node>();
  
  void loadDictionary (String file) {
    try {
      Scanner in = new Scanner(new File(file));
      while (in.hasNextLine()) {
        String word = in.nextLine();
        Node node = new Node(word);
        nodes.add(node);
      }
    } catch (Exception e) {
      ui.sendMessage("Uh oh: " + e);
    }
  }
  
  Node find (String word) {
    for (int i = 0; i < nodes.size(); i++)
      if (word.equals(nodes.get(i).word))
        return nodes.get(i);
    return null;
  }
  
  void clearAllPrevious () {
    for (int i = 0; i < nodes.size(); i++)
      nodes.get(i).previous = null;
  }
  
  public class NodeComparator implements Comparator<Node> {
		
		String targetWord;
		int links = 0;
		
		public NodeComparator (String targetWord) {
			this.targetWord = targetWord;
		}
		
		public int value(Node node) {
			while(node.previous != null) {
				node = node.previous;
				links++;
			}
			if(node.word.length() != targetWord.length()) {
				links += node.word.length() + targetWord.length();
			} 
			else {
				for(int i = 0; i <= node.word.length()-1; i++) {
					if(node.word.charAt(i) != targetWord.charAt(i)) {
						links++;
					}
				}
			}
			return links;
		}
		
		@Override
		public int compare(Node node1, Node node2) {
			// TODO Auto-generated method stub
			return value(node1) - value(node2);
		}
	}
		public int distanceToStart(Node node) {
			int links= 0;
			while(node.previous != null) {
				node = node.previous;
				links++;
			}
			return links;
		}
		
		void solve(String word, String targetWord) {
			Queue<Node> queue = new Heap<Node>(new NodeComparator(targetWord));
			Node startWord = find(word);
			Node endWord = find(targetWord);
			Node foundNode;
			String Word = "";
			int dequeueCount = 0;
			queue.offer(startWord);
			while(!queue.isEmpty()){
				Node currentNode = queue.poll();
				dequeueCount++;
				for(int i = 0; i < nodes.size(); i++) {
					foundNode = nodes.get(i);
					//System.out.println(foundNode.word);
					if(oneDegree(currentNode.word, foundNode.word) == true && !foundNode.word.equals(startWord.word) 
							&& foundNode.previous == null) {
						foundNode.previous = currentNode;
						queue.offer(foundNode);
						if(foundNode.word.equals(endWord.word)) {
							ui.sendMessage("you win!");
							ui.sendMessage(dequeueCount + "words");
							while(foundNode != null) {
								Word = foundNode.word + "\n" + Word;
								foundNode = foundNode.previous;
							}
							ui.sendMessage(Word);
							return;
						}	
					}
					if(foundNode.previous != null && distanceToStart(foundNode.previous)+1 < distanceToStart(foundNode)) {
						queue.remove(foundNode);
					}
				}
			}
  }
}
