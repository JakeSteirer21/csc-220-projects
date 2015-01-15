package prog01;

public class Main {
  public static void main (String[] args) {
    System.out.println("Set a breakpoint here and then single-step.");
	// I practiced single stepping at home
    Computer c1 = new Computer("Dell", "Core 2", 2, 140, 2.40);
    Notebook n1 = new Notebook("Apple", "Core i7", 8, 500, 2.66, 15.0, 8.5);

    Computer c2 = n1; // O.K. because a Notebook is a Computer

    // The compiler doesn't ``know'' that c2 really contains a notebook.
    //Notebook n2 = c2; // compiler error

    // ``(Notebook) c2'' is a CAST, which is like a dynamic declaration.
    // We are telling the compiler that we are certain that c2
    // contains a Notebook at this point in the program.
    Notebook n3 = (Notebook) c2; // o.k., c2 references a Notebook

    // If we make a mistake, we won't find out until we run the program.
    //Notebook n4 = (Notebook) c1; // run time error

    System.out.println(c1 instanceof Notebook);
    System.out.println(c2 instanceof Notebook);
    System.out.println(n1 instanceof Computer);

    System.out.println(c1);
    System.out.println(n1);

    // Even though c2 is a Computer variable, this prints out the
    // information for Notebook.
    System.out.println(c2);

    System.out.println(n1.getWeight());

    // Even though n1 and c2 refer to the same thing, this is a
    // compiler error because the compiler doesn't ``know'' this.
    //System.out.println(c2.getWeight());

    // If we write a special computePower method for a Notebook, then
    // that gets called even if c2 is a Computer variable.
    System.out.println(c2.computePower());
  }
}
    
    