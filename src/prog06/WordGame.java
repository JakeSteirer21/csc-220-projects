package prog06;

import prog02.GUI;
import prog02.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordGame {
	public static UserInterface ui = new GUI();
	List<Node> node = new ArrayList<Node>();
	
	public static void main (String[] args) {
		WordGame game = new WordGame();
		game.loadDictionary(ui.getInfo("Enter dictionary file name"));
		
		if(game.node.size() == 0){
			return;
		}
		String startingWord = ui.getInfo("Enter starting word");
		
		if(game.find(startingWord) == null){
			ui.sendMessage("Not a word");
			return;
		}
		
		
		String endingWord = ui.getInfo("Enter ending word");
		
		if(game.find(endingWord) == null){
			ui.sendMessage("Not a word");
			return;
		}
		if( startingWord.length() != endingWord.length() || startingWord.equals(endingWord))
			ui.sendMessage("Invalid entry");	
		else{
			String[] commands = {"Computer solves", "Human Solves"};
			int n = ui.getCommand(commands);
			switch(n){
				case 0:
					game.solve(new Node(startingWord), new Node(endingWord));
					break;
				case 1:
					game.play(startingWord, endingWord);
					break;
				default:
				break;
			}
		}
	}
	
	private void play(String startingWord, String endingWord) {
		String start = startingWord;
		String target = endingWord;
		
		while(true){
			ui.sendMessage("Current word is " + start + ".\n Target word is " + target + ".");
			String input = ui.getInfo("Enter next word.");
			if(find(input) == null)
				ui.sendMessage("Not a word");
			else if(oneDegree(start, input))
				start = input;
			else 
				ui.sendMessage("Invalid entry");
			if( start.equals(target) ) {
				ui.sendMessage("You win");
				return;
			}
		}
	}
	
	private boolean oneDegree(String one, String two) {
		if (one.length() != two.length()) {
			return false;
		}
		int diff = 0;
		for(int i = 0; i < one.length(); i++){
			if(one.charAt(i) != two.charAt(i))
				diff++;
		}
		if(diff == 1)
			return true;
		return false;
	}
	private static class Node {
		String word;
		Node previous;
		public Node(String word) {this.word = word;}
	}
	private void loadDictionary(String dictionary){
		try {
			// Create a BufferedReader for the file.
			Scanner in = new Scanner(new File(dictionary));
			
			// Read each name and number and add the entry to the array.
			while (in.hasNextLine()) {
				node.add(new Node(in.nextLine()));
			}

			// Close the file.
			in.close();
		} catch (FileNotFoundException ex) {
			// Do nothing: no data to load.
			ui.sendMessage(dictionary + ": file not found. \n" + ex);
			return;
		}
	}
	private Node find(String word) {
		for(Node elem:node)
			if(word.equals(elem.word))
				return elem;
		return null;
	}
	private void solve(Node start, Node end) {
		ArrayQueue<Node> wordList = new ArrayQueue<Node>();
		wordList.offer(start);
		while(wordList.size() != 0) {
			Node currentNode = wordList.remove();
			for(Node elem:node) {
				if(oneDegree(elem.word, currentNode.word) && elem.previous == null) {
					Node  nextNode = elem;
					nextNode.previous = currentNode;
					wordList.offer(nextNode);
					if(nextNode.word.equals(end.word)){
						String s = "";
						while(nextNode != null) {
							s = nextNode.word + "\n" + s;
							nextNode = nextNode.previous;
						}
						ui.sendMessage(s);
						return;
					}
				}
			}
			
		}
		ui.sendMessage("No solution found");
	}
	
	
}
