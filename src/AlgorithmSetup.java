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
	double sum = 0; //total amount of movement
	
	
	
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
	}
	
	
	//reboot functions, to allow for multiple iterations
	//no changes
	public void reboot(){
		reboot(nodes.size());
	}
	
	//change r
	public void reboot(double newR){
		r = newR;
		reboot(nodes.size());
	}
	
	//change r and n
	public void reboot(double newR, int newN){
		r = newR;
		reboot(newN);
	}
	
	//change n
	public void reboot(int newN){
		int n = newN;
		bufferSize = (n * 2.0 * r) - 1.0; 
		nodes.clear();
		
		//generate the nodes
		Random generator = new Random();
		double temp;
		for (int i=0; i<n; i++){ 
			temp = generator.nextDouble();
			nodes.add(temp);
		}
		
		//sorts the nodes
		for(int i=0; i<n-1; i++){
			for(int j=i+1; j<n; j++){
				if(nodes.get(i) > nodes.get(j)){
					temp = nodes.get(i);
					nodes.set(i, nodes.get(j));
					nodes.set(j, temp);
				}
			}
		}
		
		sum = 0;
	}
	//reboot function ends
	
}
