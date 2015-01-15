package prog03;

public class LinearFib implements Fib{
	public double fib (int n) {
		double a = 1;
		double b = 0;
		double c;
		
		if (n <= 1)
			return n;
		for(int i = 0; i < n-1; i++) {
			c = a + b;
			b = a;
			a = c;
		}
		return a;
	}
	
	public double o (int n) {
	 return n;	
	}
}
