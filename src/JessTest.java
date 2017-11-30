/*
This is a simple class used to show how to use Jess inside Java.
Make sure to include jess.jar in the build path.
*/
import jess.*;


public class JessTest {
	
	public JessTest(){
		
	}
	
	public void run(){
		try {
			Rete r = new Rete();
			r.batch("q1.clp");
			
			Context c = r.getGlobalContext();
			
			Fact status = new Fact("status", r);
			status.setSlotValue("__data", new Value("driving", RU.SYMBOL));
			r.assertFact(status);
			
			Fact light = new Fact("light", r);
			light.setSlotValue("__data", new Value("green", RU.SYMBOL));
			r.assertFact(light);
			
			Fact check = new Fact("check", r);
			check.setSlotValue("checked", new Value("no", RU.SYMBOL));
			r.assertFact(check);
			
			r.eval("(facts)");
			r.run();
			
			
			Value sigma = check.getSlotValue("checked");
			double number = sigma.floatValue(c);
			number++;
			System.out.println(number);
			
		} catch (JessException ex) {
			System.err.println(ex);
		}
		
	}
}
