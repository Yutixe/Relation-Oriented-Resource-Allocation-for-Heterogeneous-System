import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.set.SetConstraintsFactory;
import org.chocosolver.solver.trace.Chatterbox;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.solver.variables.VariableFactory;

/* Novel 1-dimensional Bin Packing solver, based on small input size; 
 * Model by Set variables through order theory, using 1D arrays to record bin's capacity;
 * Using lattice associated to each bin to represent allocation and using the domain of each bin to represent balls;
 * Trivial-greedy searching method without any heuristics.
 * 
 * Author: Teng Yu  30/11/2015
 */

public class BinPacking2 {

	public static void main(String[] args) {

	    //the array of bins in which the value of each dimension is the capacity of the corresponding bin.
	    int[] binSizes = {40,30};
		//the number of bins
		int nBins = binSizes.length;
		//using the top domain of bins to represent balls;
		//the value of each dimension is the ball size;
		int[] b_top = new int []{10,20,30};
		//using the empty bottom domain to represent bins can be empty;
		int[] b_bottom = new int []{};		

		//create the Java-Choco solver
		Solver solver = new Solver();
		
		//create the Variables
		//each bin is represented by a set variable, using b3 to represent the union;
		SetVar b1,b2,b3;
		b1 = VariableFactory.set("bin1",b_top,b_bottom,solver);
		b2 = VariableFactory.set("bin2",b_top,b_bottom,solver);
		b3 = VariableFactory.set("binCapacity",b_top,b_top,solver);
		SetVar[] b = {b1,b2};
		//an array of intVars, for the total size added to each bin
        IntVar[] binLoad = new IntVar[nBins];
        for (int bin = 0; bin < nBins; bin++) {
        binLoad[bin] = VariableFactory.enumerated("binLoad", 0, binSizes[bin], solver);
        } 
		
			            
        //CONSTRAINTS        
        //make sure each ball is allocated in exactly one bin;
        solver.post(SetConstraintsFactory.all_different(b)); 
        //make sure all balls are allocated;
        solver.post(SetConstraintsFactory.union(b,b3)); 
        //make sure each bin load are less than or equal to its capacity;
        for (int bin = 0; bin < nBins; bin++) {
        	solver.post(SetConstraintsFactory.sum(b[bin], binLoad[bin],true));
        }
        
        //SEARCH
        //trivial search method by findSolution;
        Chatterbox.showSolutions(solver);
        solver.findSolution();
        Chatterbox.printStatistics(solver);
     }        
	
}

