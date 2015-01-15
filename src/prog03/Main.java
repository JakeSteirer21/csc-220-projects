package prog03;

import prog02.GUI;
import prog02.UserInterface;

public class Main {
	/** Use this variable to store the result of each call to fib. */
	public static double fibn;
    
	/** Determine the time in milliseconds it takes to calculate the
        n'th Fibonacci number averaged over ncalls calls.
	@param fib an object that implements the Fib interface
	@param n the index of the Fibonacci number to calculate
	@param ncalls the number of calls to average over
	@return the average time per call
	 */
	static double time (Fib fib, int n, long ncalls) {
		// Get the current time in milliseconds.  This is a static
		// method in the System class.  Actually, it is the time in
		// milliseconds since midnight, January 1, 1970.  What type
		// should you use to store the current time?  Why?
        long start = System.currentTimeMillis();
		// Calculate the n'th Fibonacci number ncalls times.  Each
		// time store the result in fibn.
        for(int i = 0; i < ncalls; i++) {
        	fibn = fib.fib(n);
        }
		// Get the current time in milliseconds.
        long end = System.currentTimeMillis();
		// Using ncalls and the starting and ending times, calculate
		// the average time per call and return it.  Make sure to use
		// double precision arithmetic for the calculation by casting
		// it to double.
        double time = (double)(end - start)/ncalls;
		return time;
	}

	/** Determine the time in milliseconds it takes to to calculate
	the n'th Fibonacci number ACCURATE TO THREE SIGNIFICANT FIGURES.
	@param fib an object that implements the Fib interface
	@param n the index of the Fibonacci number to calculate
	@return the time it it takes to compute the n'th Fibonacci number
	 */
	public static double time (Fib fib, int n) {
		double totalTime = 0;
		double time;
		long ncalls = 1;
		// Since the clock is only accurate to the millisecond, we
		// need to use a value of ncalls such that the total time is a
		// second.  First we need to figure that value of ncalls.
    	totalTime = ncalls * time(fib, n, ncalls);
		while(totalTime < 1000) {
        	ncalls = ncalls * 2;
        	totalTime = ncalls * time(fib, n, ncalls);
        }
		time = time(fib, n, ncalls);
		// Starting with ncalls=1, calculate the total time, which is
		// ncalls times the average time.  Use the method
		// time(fib,n,ncalls) method to get the average time.  Keep
		// multiplying ncalls times 2 until the total time is more
		// than a second.

		// Return the average time for that value of ncalls.  As a
		// test, print out ncalls times this average time to make sure
		// it is more than a second.
		return time;
	}
	
	public static void doExperiments(Fib fib) {
		double c = 0;
		UserInterface ui = new GUI();
		String[] commands = {"yes", "no"};
		

		while (true) {
			String input = ui.getInfo("Enter n");
			if (input == null || input.equals(""))
				return;
	
				try{
					int n = Integer.parseInt(input);
				if(c != 0) {
					ui.sendMessage("Estimated time for input " + n + " is " + fib.o(n)*c);
					if((c * fib.o(n)) > 3600000) {
						ui.sendMessage("Estimated time is more than 1 hr\nI will ask you if you really want to run it");
                        if(ui.getCommand(commands) == 1)
                        	return;
					}
				}
				c = time(fib, n);
				ui.sendMessage("fib(" + n +") = " + fib.fib(n) + " in " + c + " miliseconds.");
				} catch (NumberFormatException e) {
					ui.sendMessage("Not an integer");
				}
			
		}
	}
	public static void main (String[] args) {		
		
		GUI ui = new GUI();
		String[] commands = {
				"ExponentialFib",
				"LinearFib",
				"LogFib",
				"ConstantFib"
				};
		int c = ui.getCommand(commands);
		switch (c) {
		case 0:
			doExperiments(new ExponentialFib());				
		break;
		case 1:
			doExperiments(new LinearFib());
		break;
		case 2:
			doExperiments(new LogFib());
		break;	
		case 3:
			doExperiments(new ConstantFib());
		break;
		}
	}
		
		
		
		
		
		
/*		System.out.println(efib);
		
		// Create (Exponential time) Fib object and test it.
		// Fib efib = new ExponentialFib();
		Fib efib = new ConstantFib();
		for (int i = 0; i < 11; i++)
			System.out.println(i + " " + efib.fib(i));

		// Determine running time for n1 = 20 and print it out.
		int n1 = 20;
		double time1 = time(efib, n1);
		System.out.println("n1 " + n1 + " time1 " + time1);

		// Calculate constant:  time = constant times O(n).
		double c = time1 / efib.o(n1);
		System.out.println("c " + c);

		// Estimate running time for n2=30.
		int n2 = 30;
		double time2est = c * efib.o(n2);
		System.out.println("n2 " + n2 + " estimated time " + time2est);

		// Calculate actual running time for n2=30.
		double time2 = time(efib, n2);
		System.out.println("n2 " + n2 + " actual time " + time2);

		// Estimate running time for n3=100.
		int n3 = 100;
		double time2est1 = c * efib.o(n3);
		System.out.println("n3 " + n3 + " estimated time " + time2est1);
		*/
}