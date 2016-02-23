import java.util.ArrayList;

import org.chocosolver.solver.*;
import org.chocosolver.solver.constraints.ICF;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.constraints.LCF;
import org.chocosolver.solver.constraints.LogicalConstraintFactory;
import org.chocosolver.solver.constraints.nary.circuit.CircuitConf;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.trace.Chatterbox;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VF;

public class RArAllocInit {

	public static void main (String[] args) {
		
		//CReate a solver object that will solve the problem
		Solver solver = new Solver();
		
		//REad the problem in from file in standard format
		RArReaderMPCX reader = new RArReaderMPCX("RArInput2.txt");		
		int numberOfRAr = reader.getNumberOfRArs();   //the number of welders in the problem
		String[] RArType =  reader.getTypeOfRArs();      //the number of jobs in the problem	  
		int[] RArSize = reader.getSizeOfRArs(); //for each welder, what jobs are they required for
	    int[] RArCap = reader.getCapOfRArs(); //minimum number of welders needed for each job
	   
	    int[] DFE = {100,100,100,100,100,100,100,100};
	    int[] Index = {1,2,4,8,16,32,64,128};
	  
	
	    int numberOfDFE = DFE.length;
	    

		//VAriable
		IntVar[] LW = VF.enumeratedArray("temp",numberOfRAr, 0,255,solver);
	    //the total cost
		
	    //IntVar tc = VF.enumerated("TotalCost", 0, costBound, solver);
		//the current-load of each DFE
		IntVar[] DFELoad = VF.enumeratedArray("DFELoad", numberOfDFE, 0, 100, solver);
		
		//the number of DFE worked on each RAr
		//IntVar[] RArAlloc = VF.enumeratedArray("RArAllocN",numberOfRAr,0,numberOfDFE,solver);
		
	    //a 2d array for the DFE allocated to each RAr
		IntVar[][] RAr_DFE = VF.enumeratedMatrix("RArDFE", numberOfRAr, numberOfDFE, 0 , 1, solver); 
				
		//the transpose of the above matrix
		IntVar[][] DFE_RAr = new IntVar[numberOfDFE][numberOfRAr];	
		for(int w=0; w<numberOfDFE; w++){
			for(int j=0; j<numberOfRAr; j++){
				DFE_RAr[w][j] = RAr_DFE[j][w];
			}
		}
		
		
		
		
		//for each RAr, the index of DFE allocated on it
		IntVar[][] RDIndex = new IntVar[numberOfRAr][];  
		for(int w=0;w<numberOfRAr;w++)
		{
		RDIndex[w] = new IntVar[RArSize[w]];
		}
	
		
		
		//COnstraint
		//each RAr has been allocated on enough DFE
		for (int i=0;i<numberOfRAr;i++)
		{
			solver.post(IntConstraintFactory.sum(RAr_DFE[i],VF.fixed(RArSize[i],solver)));
		}
		
		//the load of each DFE never over its capacity
		 for (int j = 0; j<numberOfDFE; j++) {
	           solver.post(IntConstraintFactory.scalar(DFE_RAr[j], RArCap, DFELoad[j])); 
	           solver.post(IntConstraintFactory.arithm(DFELoad[j], "<=", DFE[j]));
	        }
      	
		 
		 
	    //constraints for Adjcent type
		 for(int i=0;i<numberOfRAr;i++)
		 {
			 if(RArType[i].equals("A"))
			 {   
				 solver.post(IntConstraintFactory.scalar(RAr_DFE[i],Index, LW[i])); 
				 int size = RArSize[i];
				 int base = (int) (Math.pow(2,size)-1);
				 int max = (int) Math.pow(2, 8)-1;
				 int nbase = (int) Math.pow(2, 8-size) -1;
				 solver.post(LCF.or(IntConstraintFactory.arithm(LW[i], "=", base*(int) Math.pow(2,0)),
						 			IntConstraintFactory.arithm(LW[i], "=", base*(int) Math.pow(2,1)),
						 			IntConstraintFactory.arithm(LW[i], "=", base*(int) Math.pow(2,2)),
						 			IntConstraintFactory.arithm(LW[i], "=", base*(int) Math.pow(2,3)),
						 			IntConstraintFactory.arithm(LW[i], "=", base*(int) Math.pow(2,4)),
						 			IntConstraintFactory.arithm(LW[i], "=", base*(int) Math.pow(2,5)),
						 			IntConstraintFactory.arithm(LW[i], "=", base*(int) Math.pow(2,6)),
						 			IntConstraintFactory.arithm(LW[i], "=", base*(int) Math.pow(2,7)),
						 			IntConstraintFactory.arithm(LW[i], "=", max-nbase*(int) Math.pow(2,0)),
						 			IntConstraintFactory.arithm(LW[i], "=", max-nbase*(int) Math.pow(2,1)),
						 			IntConstraintFactory.arithm(LW[i], "=", max-nbase*(int) Math.pow(2,2)),
						 			IntConstraintFactory.arithm(LW[i], "=", max-nbase*(int) Math.pow(2,3)),
						 			IntConstraintFactory.arithm(LW[i], "=", max-nbase*(int) Math.pow(2,4)),
						 			IntConstraintFactory.arithm(LW[i], "=", max-nbase*(int) Math.pow(2,5)),
						 			IntConstraintFactory.arithm(LW[i], "=", max-nbase*(int) Math.pow(2,6)),
						 			IntConstraintFactory.arithm(LW[i], "=", max-nbase*(int) Math.pow(2,7))
						 			));
				 //int sum = LW.getValue();
				 //System.out.println(Integer.toBinaryString(sum));
				 
				 //int div = (int) (sum / (Math.pow(2,size)-1));
				 //int nsum = (int) (Math.pow(2, 8)-1-sum);
				 //int ndiv = (int) (nsum/(Math.pow(2,8-size)-1));
				 //if((sum % (Math.pow(2,size)-1)) == 0 && ((div & (-div)) == div)){
				//	 solver.post(IntConstraintFactory.TRUE(solver));
				 //}else if ((nsum % (Math.pow(2,8-size)-1)) == 0 && ((ndiv & (-ndiv)) == ndiv)){
				//	 solver.post(IntConstraintFactory.TRUE(solver));
				 //}else{
				//	 solver.post(IntConstraintFactory.FALSE(solver));
				 //}
				 
			}
		 }
		 
		
		 
		 //SEarch
		 
		//Specify a search strategy (or take the default)
	//	solver.set(IntStrategyFactory.minDom_LB(jc));		
	//    solver.set(IntStrategyFactory.domOverWDeg(jc, 1));
		//solver.set(IntStrategyFactory.lexico_LB(jc));
		//solver.set(IntStrategyFactory.lastConflict(solver));
		
		
		//use a pretty print object to display the results
		Chatterbox.showSolutions(solver);
		
		//find a solution
		solver.findSolution();
		//find the optimal solution to minimize the total cost
		//solver.findOptimalSolution(ResolutionPolicy.MINIMIZE, tc);
	//	System.out.println(tc);
		
				
		//print out the search		
        Chatterbox.printStatistics(solver);		
        for(int i=0;i<3;i++)
        {
        	System.out.println(RAr_DFE[2][i]);
        }
	}
}