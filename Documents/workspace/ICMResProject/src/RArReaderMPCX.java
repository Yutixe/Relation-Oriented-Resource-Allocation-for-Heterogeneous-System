import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class RArReaderMPCX {

	private int numberOfRArs;   //the number of RArs in the problem
//	private String[] RArs; //a set of resource allocation requirements
	private String[] typeOfRArs; //a set of resource allocation requirements
	private int[] sizeOfRArs;
	private int[] capOfRArs;
//	private int[][] RArRelations; //The relations between different RArs


	public RArReaderMPCX(String filename) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename));
			numberOfRArs = scanner.nextInt();
            typeOfRArs = new String[numberOfRArs];
            for (int w=0;w<numberOfRArs;w++){
            	typeOfRArs[w] = scanner.next();
            }
            sizeOfRArs = new int[numberOfRArs];
            for (int w=0;w<numberOfRArs;w++){
            	sizeOfRArs[w] = scanner.nextInt();
            }
            capOfRArs = new int[numberOfRArs];
            for (int w=0;w<numberOfRArs;w++){
            	capOfRArs[w] = scanner.nextInt();
            }
		//	RArRelations = new int[numberOfRArs][numberOfRArs];
		//	for (int w=0;w<numberOfRArs;w++){
		//		for (int j=0;j<numberOfRArs;j++) {
		//			RArRelations[w][j] = scanner.nextInt();
		//		}
		//	}	
			
		}
		catch (IOException e) {
			System.out.println("File error:" + e);
		}
	}
			    
	public int getNumberOfRArs() {
		return numberOfRArs;
	}

	public String[] getTypeOfRArs() {
		return typeOfRArs;
	}
	
	public int[] getSizeOfRArs() {
		return sizeOfRArs;
	}
	public int[] getCapOfRArs() {
		return capOfRArs;
	}

//	public int[][] getRArRelations() {
	//	return RArRelations;
//	}


	public static void main(String[] args) {
		RArReaderMPCX reader = new RArReaderMPCX("RArInput2.txt");
		int limit = reader.getNumberOfRArs();
//		int[] minWelderArray = reader.getMinWelders();
//		int[] a = reader.getWelderRate();
		String[] a = reader.getTypeOfRArs();
		for (int i = 0; i < limit; i++) {
			
		   System.out.print(a[i]+" ");
		}
		System.out.println(limit);
	}
}
