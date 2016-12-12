import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Display {
	
	AlgorithmSetup data;
	JFrame window;
	JPanel panel;

	Display (AlgorithmSetup algoData){
		data = algoData;

		window = new JFrame();
		window.setSize(720, 600);
		
		
		if(data.barGraph){
			//Draw the circles on the line
			panel = new JPanel(new BorderLayout()) {
				public void paintComponent(Graphics g){
					g.drawLine(10, window.getHeight()/2, window.getWidth()-25, window.getHeight()/2);
					for (int i=0; i<data.nodes.size(); i++){
					//draw circle(s) at location
						int diameter = (int) ((window.getWidth()-25-10)*(2 * data.r));
						int xPos = (int) (10+(window.getWidth()-25-10)*data.nodes.get(i)-(diameter/2));
						
						g.drawOval(xPos, window.getHeight()/2-(diameter/2), diameter, diameter);

						g.drawLine(xPos+(diameter/2), window.getHeight()/2-(diameter/2), xPos+(diameter/2), window.getHeight()/2+(diameter/2));
					}
				}
			};
			
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
				for(int i = 0; i < data.nodes.size();i++)
				{
					if((2*(i+1)-1)*data.r < 1)
					{
						data.sum += Math.abs(data.nodes.get(i)-((2*(i+1)-1)*data.r));
						data.nodes.set(i, ((2*(i+1)-1)*data.r));
					}
				}
				break;
		}
		
		panel.repaint();
	}
	
	
	
	
	
}
