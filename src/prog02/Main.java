package prog02;

/**
 *
 * @author vjm
 */
public class Main {

	/** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
             to talk to the user.
      @param pd The PhoneDirectory object to use
             to process the phone directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = {
				"Add/Change Entry",
				"Look Up Entry",
				"Remove Entry",
				"Save Directory",
		"Exit"};

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case 0:
				// implement
                          name = ui.getInfo("Enter the name");
                          // Check for null or ""
                          if (name == null || name.equals("")){
                        	  ui.sendMessage("Invalid entry.");
                              break;
                          }                       
                          number = ui.getInfo("Enter the number");
                          if(number == null || number.equals("")){
                        	  ui.sendMessage("Invalid entry.");
                              break;
                          }
                          // Check for a good number.
                          oldNumber = pd.addOrChangeEntry(name, number);
                          if (oldNumber == null)
                            ui.sendMessage("Added new entry with name " + name + " and number " + number);
                          else
                            ui.sendMessage("Changed entry with name " + name +
                                           "\nOld number: " + oldNumber +
                                           "\nNew number: " + number);
				break;
			case 1:
				// implement
				         name = ui.getInfo("Enter the name");
				         if (name == null || name.equals("")){
                       	     ui.sendMessage("Invalid entry.");
                             break;
				         }
				         number = pd.lookupEntry(name);
				         if(number == null)
				        	 ui.sendMessage("Not found");
				         else
				        	 ui.sendMessage("Found entry with name "+ name + 
				        			 "\nNumber: " + number);
				break;
			case 2:
				// implement
				         name = ui.getInfo("Enter the name");
		                 if (name == null || name.equals("")){
               	              ui.sendMessage("Invalid entry.");
                         break;
		                 }
		                 if(pd.lookupEntry(name) == null){
				        	 ui.sendMessage("Not found");
		                     break;
		                 }
		                 pd.removeEntry(name);
		                 ui.sendMessage("Removed " + name);
				break;
			case 3:
				// implement
				        pd.save();
				        ui.sendMessage("Saved");
				break;
			case 4:
				// implement
				        pd.save();
		                ui.sendMessage("Goodbye");
				return;
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI();
		processCommands(fn, ui, pd);
	}
}
