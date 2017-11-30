/*
This is a simple class used to show how to use Jess inside Java.
Make sure to include jess.jar in the build path.
*/
import jess.*;


public class JessTest {
	
	public JessTest() {
		
	}
	
	public void run() {
		try {
			Rete r = new Rete();
			r.eval("(deffunction square (?n) (return (* ?n ?n)))");
			Value v = r.eval("(square 3)");
			
			System.out.println(v.intValue(r.getGlobalContext()));
		} catch (JessException ex) {
			System.err.println(ex);
		}
		
	}
}
