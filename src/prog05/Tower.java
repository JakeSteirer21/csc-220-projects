package prog05;

import java.util.Stack;

import prog02.UserInterface;
import prog02.GUI;

public class Tower {
  static UserInterface ui = new GUI();

  static public void main (String[] args) {
    int n = getInt("How many disks?");
    if (n <= 0)
      return;
    Tower tower = new Tower(n);

    String[] commands = { "Human plays.", "Computer plays." };
    int c = ui.getCommand(commands);
    if (c == 0)
      tower.play();
    else
      tower.solve();
  }

  /** Get an integer from the user using prompt as the request.
   *  Return 0 if user cancels.  */
  static int getInt (String prompt) {
    while (true) {
      String number = ui.getInfo(prompt);
      if (number == null)
        return 0;
      try {
        return Integer.parseInt(number);
      } catch (Exception e) {
        ui.sendMessage(number + " is not a number.  Try again.");
      }
    }
  }

  int nDisks;
  StackInt<Integer>[] pegs = (StackInt<Integer>[]) new ArrayStack[3];

  Tower (int nDisks) {
    this.nDisks = nDisks;
    for (int i = 0; i < pegs.length; i++)
      pegs[i] = new ArrayStack<Integer>();

    // EXERCISE: Initialize game with pile of nDisks disks on peg 'a'
    // (pegs[0]).
    for(Integer i = nDisks; i > 0; i--)
    	pegs[0].push(i);
  }

  void play () {
    while (! (pegs[0].empty() && pegs[1].empty()) /* EXERCISE:  player has not won yet. */) {
      displayPegs();
      String move = getMove();
      int from = move.charAt(0) - 'a';
      int to = move.charAt(1) - 'a';
      move(from, to);
    }

    ui.sendMessage("You win!");
  }

  String stackToString (StackInt<Integer> peg) {
    StackInt<Integer> helper = new ArrayStack<Integer>();

    // String to append items to.
    String s = "";

    // EXERCISE:  append the items in peg to s from bottom to top.
    
    while(! peg.empty())
    	helper.push(peg.pop());
    while(! helper.empty()){
    	s += helper.peek();
    	peg.push(helper.pop());
    }
    return s;
  }

  void displayPegs () {
    String s = "";
    for (int i = 0; i < pegs.length; i++) {
      char abc = (char) ('a' + i);
      s = s + abc + stackToString(pegs[i]);
      if (i < pegs.length-1)
        s = s + "\n";
    }
    ui.sendMessage(s);
  }

  String getMove () {
    String[] moves = { "ab", "ac", "ba", "bc", "ca", "cb" };
    return moves[ui.getCommand(moves)];
  }

  void move (int from, int to) {
    // EXERCISE:  move one disk form pegs[from] to pegs[to].
    // Don't do illegal moves.  Send a warning message instead, like:
    // Cannot place 2 on top of 1.
	  if(pegs[from].empty())
		  ui.sendMessage("Empty");
	  else if(! pegs[to].empty() && pegs[from].peek() > pegs[to].peek())
		  ui.sendMessage("Invalid move");
	  else
		  pegs[to].push(pegs[from].pop());
  }
  StackInt<Goal> goals = new ListStack<Goal>();	 
  // EXERCISE:  create Goal class.
  class Goal {
	  int howMany;
	  int fromPeg;
	  int toPeg;
 
	  Goal(int howMany, int fromPeg, int toPeg) {
		  this.howMany = howMany;
		  this.fromPeg = fromPeg;
		  this.toPeg = toPeg;
	  }
	  }
  // EXERCISE:  display contents of a stack of goals
void displayGoals() {
	 StackInt<Goal> helper = new ListStack<Goal>();
	 String s = "";
	 while (!goals.empty()) {
		 s += "Move " + goals.peek().howMany + " disk(s) from " +  (char) ('a' + goals.peek().fromPeg) + " to " + (char) ('a' + goals.peek().toPeg) + ".\n";
		 helper.push(goals.pop());
	 }
		 ui.sendMessage(s);
	while(!helper.empty()) 
		goals.push(helper.pop());
	 
 }
 void solve () {
    // EXERCISE
	 goals.push(new Goal(nDisks, 0, 2));
	 while(!goals.empty()) {
		 int howMany = goals.peek().howMany;
		 int source = goals.peek().fromPeg;
		 int dest = goals.peek().toPeg;
		 int transfer;
		 if((source+dest) == 1)
			 transfer = 2;
		 else if((source + dest) == 3)
			 transfer = 0;
		 else 
			 transfer = 1;
		 displayGoals();
		 goals.pop();
		 if(howMany == 1) {
			 move(source, dest);
			 displayPegs();
		 } else {
			 goals.push(new Goal(howMany - 1, transfer, dest));
			 goals.push(new Goal(1, source, dest ));
			 goals.push(new Goal(howMany - 1, source, transfer));
		 }
	 }	  
  }        
}
