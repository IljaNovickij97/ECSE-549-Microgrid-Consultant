import microgrid.Individual;
import jess.*;

public class JessInterface {
	
	private Rete r = new Rete();
	public boolean finished_flag = false;
	
	public JessInterface(){
		
	}
	
	public double[] newSigmas(Individual initial, double[] input_sigmas){
		double[] sigma = new double[3];
		double[] requirements = initial.getRequirements();
		try {
			r.batch("engine.clp");
			r.reset();
			Context c = r.getGlobalContext();
			
		    //(LoadMatching (Generation 10)(Load 12)(NotMatched FALSE))
			Fact load_matching = new Fact("LoadMatching", r);
			load_matching.setSlotValue("Generation", new Value( (float) initial.getTotal_generated(), RU.FLOAT));
			load_matching.setSlotValue("Load", new Value( (float) initial.getLoad().getTotal_energy(), RU.FLOAT));
			load_matching.setSlotValue("NotMatched", new Value(false));
			r.assertFact(load_matching);
			
			Fact firing = new Fact("Firing", r);
			firing.setSlotValue("MoreRE", new Value(true));
			firing.setSlotValue("LessCO2", new Value(true));
			firing.setSlotValue("LessLCOE", new Value(true));
			firing.setSlotValue("DONE", new Value(false));
			r.assertFact(firing);
			
			Fact microgrid = new Fact("Microgrid", r);
			
			microgrid.setSlotValue("REI", new Value( (float) initial.getR_injection(), RU.FLOAT));
			microgrid.setSlotValue("CO2", new Value( (float) initial.getCO2(), RU.FLOAT));
			microgrid.setSlotValue("LCOE", new Value( (float) initial.getLCOE(), RU.FLOAT));
			r.assertFact(microgrid);
			
			
			Fact sigmas = new Fact("Sigmas", r);
			sigmas.setSlotValue("Sgen", new Value( (float) input_sigmas[0], RU.FLOAT));
			sigmas.setSlotValue("Ssolar", new Value( (float) input_sigmas[1], RU.FLOAT));
			sigmas.setSlotValue("Sess", new Value( (float) input_sigmas[2], RU.FLOAT));
			r.assertFact(sigmas);
			
			Fact reqs = new Fact("UserReq", r);
			reqs.setSlotValue("minRE", new Value( (float) requirements[0], RU.FLOAT));
			reqs.setSlotValue("maxCO2", new Value( (float) requirements[1], RU.FLOAT));
			reqs.setSlotValue("maxLCOE", new Value( (float) requirements[2], RU.FLOAT));
			r.assertFact(reqs);
			
			r.run();
			Value sigmaval = sigmas.getSlotValue("Sgen");
			sigma[0] = sigmaval.floatValue(c);
			sigmaval = sigmas.getSlotValue("Ssolar");
			sigma[1] = sigmaval.floatValue(c);
			sigmaval = sigmas.getSlotValue("Sess");
			sigma[2] = sigmaval.floatValue(c);
			
			for(int i =0; i<3;i++) {
				if(sigma[i] > 1) {
					sigma[i] = 1;
				}
				if(sigma[i] < -1) {
					sigma[i] = -1;
				}
				
			}
			
			Value done = firing.getSlotValue("DONE");
			if (done.symbolValue(c) == "TRUE") {
				finished_flag = true;
			}
			
			
			
		} catch (JessException ex) {
			System.err.println(ex);
		}
		return sigma;
	}
	
	public void restart() {
		finished_flag = false;
	}
}
