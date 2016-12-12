import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Display {
	
	AlgorithmSetup data;
	JFrame window;
	JPanel panel;
	int currentIterationLocation;

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
			panel = new JPanel(new BorderLayout()) {
				public void paintComponent(Graphics g){
					//do the thing
				}
			};
		}

		window.add(panel);
		window.setVisible(true);
		updateCurrentState();
	}

	
	/*
	 * Updates the current state, then draws it as a line
	 */
	public void updateCurrentState(){
		//can be edited to change the algorithm, currently only does 1
		switch(data.algoNum){
			case 0:
				if(currentIterationLocation < data.nodes.size()){
					if((2*(currentIterationLocation+1)-1)*data.r < 1)
					{
						data.sum += Math.abs(data.nodes.get(currentIterationLocation)-((2*(currentIterationLocation+1)-1)*data.r));
						data.nodes.set(currentIterationLocation, ((2*(currentIterationLocation+1)-1)*data.r));
					}
					currentIterationLocation++;
				}
				break;
			default:
				testAlgo();
				break;
		}
		
		panel.repaint();
	}
	
	public void testAlgo(){
		for(int i=0; i<data.nodes.size();i++){
			data.nodes.set(i, (data.nodes.get(i)+0.01));
		}
	}
	
	
	
}
