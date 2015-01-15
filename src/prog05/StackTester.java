package prog05;
import prog02.GUI;

public class StackTester {
  public static void main (String[] args) {
    GUI ui = new GUI();
    StackInt<String> stack = new LinkedStack<String>();
    
    String[] commands = { "quit", "empty", "peek", "pop", "push" };
    String item;

    while (true) {
      int c = ui.getCommand(commands);
      switch (c) {
      case 0:
        return;
      case 1:
        ui.sendMessage("empty() returns " + stack.empty());
        break;
      case 2:
        ui.sendMessage("peek() returns " + stack.peek());
        break;
      case 3:
        ui.sendMessage("pop() returns " + stack.pop());
        break;
      case 4:
        item = ui.getInfo("What do you want to push?");
        if (item == null)
          break;
        ui.sendMessage("push(item) returns " + stack.push(item));
        break;
      };
    }
  }
}

        
        