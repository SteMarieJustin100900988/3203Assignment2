import java.util.ArrayList;
import java.util.Random;


/*
 * Sets up and holds the list of nodes, radius, buffer size, and performs algorithms.
 */
public class AlgorithmSetup {
	
	double r; //radius
	int algoNum; //which algorithm to execute
	boolean barGraph; //whether to use bar graph display (part 1) (true) or line graph (part 2)(false)
	double bufferSize; //size of buffer used in some algorithms
	
	ArrayList<Double> nodes;

	/*
	 * Sets up and holds the list of nodes, radius, buffer size, and performs algorithms.
	 */
	AlgorithmSetup(int numNodes, double radius, int whichAlgo, boolean drawAsBar){
		r = radius;
		algoNum = whichAlgo;
		barGraph = drawAsBar;
		
		bufferSize = (numNodes * 2.0 * r) - 1.0; 
		
		nodes = new ArrayList<Double>();
		
		//generate the nodes
		Random generator = new Random();
		double temp;
		for (int i=0; i<numNodes; i++){ 
			temp = generator.nextDouble();
			nodes.add(temp);
		}

		//*@TESTCODE*/printArrayList(nodes);
		
		//sorts the nodes
		for(int i=0; i<numNodes-1; i++){
			for(int j=i+1; j<numNodes; j++){
				if(nodes.get(i) > nodes.get(j)){
					temp = nodes.get(i);
					nodes.set(i, nodes.get(j));
					nodes.set(j, temp);
				}
			}
		}
		
		//*@TESTCODE*/printArrayList(nodes);
		
		//*@TESTCODE*/System.out.println("Buffer size: "+bufferSize);
	}
	/*
	private void printArrayList(ArrayList l){ //@TESTCODE
		System.out.print("ArrayList [");
		
		for (int i=0; i<l.size()-1; i++){
			System.out.print(l.get(i)+", ");
		}
		
		System.out.println(l.get(l.size()-1)+"].");
	}*/
	
}