import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Display {
	
	AlgorithmSetup data;
	JFrame window;

	Display (AlgorithmSetup algoData){
		data = algoData;

		window = new JFrame();
		window.add(new JPanel(new BorderLayout()));
		
		if(data.barGraph){
			//setup for displaying movement
		} else {
			//setup for displaying line graph
		}
		
		
	}

	
	/*
	 * Draws the current state as a line
	 */
	public void drawCurrentState(){
		//draw line
		for (int i=0; i<data.nodes.size(); i++){
			//draw circle(s) at location
		}
	}
	
	
	
	
	
}
