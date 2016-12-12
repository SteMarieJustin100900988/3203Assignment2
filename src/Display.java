import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Display {
	
	AlgorithmSetup data;
	JFrame window;
	JPanel panel;
	int currentIterationLocation;
	
	//used for graph only
	ArrayList<Double> sumAtTime;
	ArrayList<Double> radiusAtTime;
	ArrayList<Integer> numberAtTime;
	int numIterATM; //number of iterations for the current data
	int varyingDataLocation; //how many times the data has been changed

	Display (AlgorithmSetup algoData){
		data = algoData;
		currentIterationLocation = 0;

		window = new JFrame();
		window.setSize(720, 600);
		

		if(data.barGraph){
			//Draw the circles on the line
			panel = new AnimationPanel(this);
			
		} else {
			//draw the line graph
			sumAtTime = new ArrayList<Double>();
			radiusAtTime = new ArrayList<Double>();
			numberAtTime = new ArrayList<Integer>();
			numIterATM = 0;
			varyingDataLocation = 0;
			
			panel = new GraphPanel(this);
		}

		window.add(panel);
		window.setVisible(true);
		
		if(data.barGraph){
			updateCurrentState();
		} else {
			updateGraphState();
		}
	}
	
	public void updateGraphState(){
		while(varyingDataLocation < 11){
			sumAtTime.add(0.0);
			while(numIterATM < 20){
				updateCurrentState();
			}
			sumAtTime.set(varyingDataLocation, sumAtTime.get(varyingDataLocation)/20.0); //sets average for current data
			radiusAtTime.add(data.r);
			numberAtTime.add(data.nodes.size());
			
			numIterATM = 0;
			varyingDataLocation++;
			data.reboot(data.r/2.0);
			panel.repaint();
		}
	}

	
	/*
	 * Updates the current state, then draws it as a line
	 */
	public void updateCurrentState(){
		//can be edited to change the algorithm, currently only does 1
		switch(data.algoNum){
			case 0: //Rigid Algorithm
				if(currentIterationLocation < data.nodes.size()){
					if((2*(currentIterationLocation+1)-1)*data.r < 1)
					{
						data.sum += Math.abs(data.nodes.get(currentIterationLocation)-((2*(currentIterationLocation+1)-1)*data.r));
						data.nodes.set(currentIterationLocation, ((2*(currentIterationLocation+1)-1)*data.r));
					}
					currentIterationLocation++;
				} else if (!data.barGraph){ //if we need multiple loops
					updateIteration();
				}
				break;
			case 1:
				if (currentIterationLocation < data.nodes.size())
				{
					if(currentIterationLocation == 0)
					{
						if(data.nodes.get(currentIterationLocation) > data.r)
						{
							data.sum += Math.abs(data.nodes.get(currentIterationLocation)-data.r);
							data.nodes.set(currentIterationLocation, data.r);
						}
					}
					else
					{
						if(data.nodes.get(currentIterationLocation) > data.nodes.get(currentIterationLocation-1)+(2*data.r))
						{
							data.sum += Math.abs(data.nodes.get(currentIterationLocation)-(data.nodes.get(currentIterationLocation-1)+(2*data.r)));
							data.nodes.set(currentIterationLocation, (data.nodes.get(currentIterationLocation-1)+(2*data.r)));
						}
					}
					currentIterationLocation++;
				} else if (!data.barGraph){ //if we need multiple loops
					updateIteration();
				}
				break;
			default:
				testAlgo();
				break;
		}
	}

	//for line graph only - resets and stores information
	private void updateIteration() {
		currentIterationLocation = 0;
		sumAtTime.set(varyingDataLocation, sumAtTime.get(varyingDataLocation)+data.sum);
		data.reboot();
		numIterATM++;
	}
	
	public void testAlgo(){
		for(int i=0; i<data.nodes.size();i++){
			data.nodes.set(i, (data.nodes.get(i)+0.01));
		}
	}
	
	
	
	
}
