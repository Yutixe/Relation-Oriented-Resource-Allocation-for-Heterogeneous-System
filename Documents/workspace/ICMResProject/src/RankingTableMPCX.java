import org.chocosolver.solver.*;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.trace.Chatterbox;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VF;

public class RankingTableMPCX {

	public static int rankTableMPCX(String rar)
	{
		int r = 0;
		
		switch(rar){
		case "s1":
		case "a1": r=1;
		     break;
		case "s2": r=2;
		     break;
		case "s3":
		case "a2": r=3;
		     break;
		case "s4":
		case "s3a2": r=4;
			break;
		case "s4a2":
		case "a3": r=5;
		    break;
		case "s5":
		case "s4a3": r=6;
		    break;
		case "s5a3":
		case "a4": r=7;
		    break;
		case "s6":
		case "s5a4": r=8;
		    break;
		case "s6a4":
		case "a5": r=9;
			break;
		case "s6a5": r=10;
		    break;
		case "a6": r=11;
		    break;
		case "a7":
		case "s7": r=12;
	        break;
		case "a8":
		case "s8": r=11;
	        break;		    			     
		}
		return r;
	}
			
public int[] getRankings(int N) {	
	RArReaderMPCX reader = new RArReaderMPCX("RArInput1.txt");
	
		String[] RArs = reader.getRArs(); 
		int[] ranking = new int[N];		
		for(int i=0;i<N;i++)
		{
			ranking[i] = rankTableMPCX(RArs[i]);
		}	
		return ranking;
	}

	public static void main (String[] args) {
		RArReaderMPCX reader = new RArReaderMPCX("RArInput0.txt");
		int N = reader.getNumberOfRArs();
		String[] RArs = reader.getRArs(); 
		int[] ranking = new int[N];		
		for(int i=0;i<N;i++)
		{
			ranking[i] = rankTableMPCX(RArs[i]);
			System.out.println(ranking[i]+" ");
		}	
		
}
}