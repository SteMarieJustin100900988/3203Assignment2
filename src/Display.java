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
	boolean secondpass = false;
	double buffer = 0.000000001;//acceptable overlap buffer to account for calculation error due to rounding

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
			while(numIterATM < 60){
				updateCurrentState();
			}
			sumAtTime.set(varyingDataLocation, sumAtTime.get(varyingDataLocation)/60.0); //sets average for current data
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
			//for all algorithms we are working with nodes is sorted order left -> right
			case 0: //Rigid Algorithm
				//line sensors up end to end left to right
				if(currentIterationLocation < data.nodes.size()){
					if((2*(currentIterationLocation+1)-1)*data.r < 1)
					{
						data.sum += Math.abs(data.nodes.get(currentIterationLocation)-((2*(currentIterationLocation+1)-1)*data.r));//add movement to get total
						data.nodes.set(currentIterationLocation, ((2*(currentIterationLocation+1)-1)*data.r));
					}
					currentIterationLocation++;
				} else if (!data.barGraph){ //if we need multiple loops
					updateIteration();
				}
				break;
				
				
			case 1: //Simple Algorithm
				//fill gaps to the left of each node 
				if (currentIterationLocation < data.nodes.size())
				{
					if(currentIterationLocation == 0)//first node
					{
						if(data.nodes.get(currentIterationLocation) > data.r)
						{
							data.sum += Math.abs(data.nodes.get(currentIterationLocation)-data.r);
							data.nodes.set(currentIterationLocation, data.r);
						}
					}
					else//every node after
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
				
				
				
			case 2: //Simple Double Pass
				//fill gaps to the left of each node then pass backwards to fill gaps to the right to achieve maximum coverage
				if (!secondpass)
				{
					//first pass same as simple coverage
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
							if(data.nodes.get(currentIterationLocation) > data.nodes.get(currentIterationLocation-1)+(2.0*data.r))
							{
								data.sum += Math.abs(data.nodes.get(currentIterationLocation)-(data.nodes.get(currentIterationLocation-1)+(2.0*data.r)));
								data.nodes.set(currentIterationLocation, (data.nodes.get(currentIterationLocation-1)+(2.0*data.r)));
							}
						}
						currentIterationLocation++;
					}
					else
					{
						secondpass = true;
						currentIterationLocation--;
					}
				}
				else
				{
					//check for overlap
					boolean overlap = false;
					for(int i = 0; i < data.nodes.size(); i++)
					{
						if(data.nodes.get(i) < data.r-buffer)
							overlap = true;
						else if(data.nodes.get(i) > 1-data.r+buffer)
							overlap = true;
						
						if(i>0)
							if(Math.abs(data.nodes.get(i)-data.nodes.get(i-1)) < 2.0*data.r-buffer)
							{
								overlap = true;
							}
						
								
						if(i < data.nodes.size()-1)
							if(Math.abs(data.nodes.get(i)-data.nodes.get(i+1)) < 2.0*data.r-buffer)
							{
								overlap = true;
							}
					}
					if(!overlap){//if we have achieved maximum coverage stop moving nodes
						if (!data.barGraph){ //if we need multiple loops
							updateIteration();
						}
						break;
					}
					if (currentIterationLocation >= 0)
					{
						//simple algorithm right to left
						if(currentIterationLocation == data.nodes.size()-1)
						{
							if(data.nodes.get(currentIterationLocation) < 1.0-data.r)
							{
								data.sum += Math.abs(data.nodes.get(currentIterationLocation)-data.r);
								data.nodes.set(currentIterationLocation, 1.0-data.r);
							}
						}
						else
						{
							if(data.nodes.get(currentIterationLocation) < data.nodes.get(currentIterationLocation+1)-(2.0*data.r))
							{
								data.sum += Math.abs(data.nodes.get(currentIterationLocation)-(data.nodes.get(currentIterationLocation+1)-(2.0*data.r)));
								data.nodes.set(currentIterationLocation, (data.nodes.get(currentIterationLocation+1)-(2.0*data.r)));
							}
						}
						currentIterationLocation--;
					} else if (!data.barGraph){ //if we need multiple loops
						updateIteration();
					}
				}
				break;
				
				
				
				
				
			case 3: //Hybrid Algorithm
				//move pieces only if we need to to achieve full coverage
				if(currentIterationLocation < data.nodes.size()){
					if(data.bufferSize <= 0 || data.bufferSize%(2*data.r) <= 0)//if we have <= enough sensors then perform standard rigid algorithm
					{
						if((2*(currentIterationLocation+1)-1)*data.r < 1)
						{
							data.sum += Math.abs(data.nodes.get(currentIterationLocation)-((2*(currentIterationLocation+1)-1)*data.r));
							data.nodes.set(currentIterationLocation, ((2*(currentIterationLocation+1)-1)*data.r));
						}
						currentIterationLocation++;
					}
					else
					{//move pieces based on how much space we have in the buffer
						if(currentIterationLocation == 0)
						{
							if(Math.abs(data.nodes.get(currentIterationLocation)-data.r) < data.bufferSize%(2*data.r))
							{//if we can still achieve full coverage without moving this node then reduce the buffer to account for it and move on
								data.bufferSize -= (2*data.r)-Math.abs(data.nodes.get(currentIterationLocation)-data.r);

								currentIterationLocation++;
							}
							else
							{//rigid movement if we can't spare space
								data.sum += Math.abs(data.nodes.get(currentIterationLocation)-data.r);
								data.nodes.set(currentIterationLocation, data.r);
								currentIterationLocation++;
							}
						}
						else
						{
							if(Math.abs(data.nodes.get(currentIterationLocation)-(data.nodes.get(currentIterationLocation-1))) < data.bufferSize%(data.nodes.size()*2*data.r) && Math.abs(data.nodes.get(currentIterationLocation)-(data.nodes.get(currentIterationLocation-1))) < (2*data.r))
							{//if we have the buffer space and there is no gap to the left of the node reduce the buffer
								for(int i = 0;i < currentIterationLocation;i++)//reduce buffer based on every node this one overlaps to it's left
								{
									if(Math.abs(data.nodes.get(currentIterationLocation)-(data.nodes.get(i))) < (2*data.r))
										data.bufferSize -= (2*data.r)-Math.abs(data.nodes.get(currentIterationLocation)-(data.nodes.get(i)));
								}

								currentIterationLocation++;
							}
							else
							{//move nodes we need to move buy putting them next to their left neighbor
								if(data.nodes.get(currentIterationLocation-1)+(2*data.r) > 1-data.r)
								{
									data.sum += Math.abs(data.nodes.get(currentIterationLocation)-(1-data.r));
									data.nodes.set(currentIterationLocation, 1-data.r);
								}
								else
								{
									data.sum += Math.abs(data.nodes.get(currentIterationLocation)-(data.nodes.get(currentIterationLocation-1)+(2*data.r)));
									data.nodes.set(currentIterationLocation, (data.nodes.get(currentIterationLocation-1)+(2*data.r)));
								}
								currentIterationLocation++;
							}
						}
					}
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
