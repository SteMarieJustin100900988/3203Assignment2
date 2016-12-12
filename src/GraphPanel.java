import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphPanel extends JPanel implements ActionListener {
	Display parent;
	Timer timer = new Timer(500, this); //update every 1/2 second

	public void paintComponent(Graphics g){
		//clear drawing
		super.paintComponent(g);
		
		//draw the line grid
		int pwh = parent.window.getHeight(); //parent window height
		int pww = parent.window.getWidth(); //parent window width
		int leftL = 30; //left limit
		int rightL = pww-45; //right limit
		int topL = 30; //top limit
		int bottomL = pwh-75; //bottom limit
		
		for(int i=0; i< 11; i++){
			//horizontal lines
			int xh1 = leftL;
			int xh2 = rightL;
			int yh1 = (int)((pwh-topL-75)*(i/10.0))+topL;
			int yh2 = (int)((pwh-topL-75)*(i/10.0))+topL;
			
			g.drawLine(xh1,yh1,xh2,yh2);
			
			//vertical lines
			int xv1 = (int)((pww-leftL-45)*(i/10.0))+leftL;
			int xv2 = (int)((pww-leftL-45)*(i/10.0))+leftL;
			int yv1 = topL;
			int yv2 = bottomL;

			g.drawLine(xv1,yv1,xv2,yv2);
		}
		
		g.drawLine(leftL, (pwh-75)+1, rightL, (pwh-75)+1);
		g.drawLine(leftL-1, bottomL, leftL-1, topL);
		
		//to calculate the scale of n
		double highest = 0;
		double lowest = 0;
		for(int j=0; j<parent.sumAtTime.size(); j++){
			if(parent.sumAtTime.get(j)>highest){
				highest = parent.sumAtTime.get(j);
			}
			if(parent.sumAtTime.get(j)<lowest){
				lowest = parent.sumAtTime.get(j);
			}
		}
		double scale = (pwh-topL-75)/(highest - lowest);
		
		//drawing points
		for (int i=parent.sumAtTime.size()-1; i>=0; i--){
			int diameter = 8;
			int xPos = (int)((i*1.0)/((parent.sumAtTime.size()-1))*(pww-leftL-45))-(diameter/2)+leftL;
			
			int yPos = (int)(parent.sumAtTime.get(i)*scale) + topL -(diameter/2);
			
			g.drawOval(xPos, yPos, diameter, diameter);
		}
		
		//drawing text
        g.drawString(Integer.toString((int)(highest+1)), leftL-20, topL);
        g.drawString("0", leftL-15, bottomL-1);
       // g.drawString(Integer.toString((int)(highestRad)), rightL+15, bottomL+15);
		
        for(int i=0; i< 11; i++){			
			//vertical lines
			int xv2 = (int)((pww-leftL-45)*((10-i)/10.0))+10;
			int yv2 = bottomL + 16;
			double digit = parent.radiusAtTime.get(i);
			digit = (double)((int)(digit*10000.0))/10000.0;
			
			//g.drawString("i = "+Double.toString(digit),xv2,yv2);
			g.drawString("i = "+Integer.toString(i),xv2,yv2);
		}
		g.drawString("r = "+Double.toString((double)((int)(parent.radiusAtTime.get(0)*100))/100.0),
				(pww-leftL-45)+10, bottomL+32);
        
		g.drawString("r = 2^(1-i)n / 2n)", (pww-leftL-45)/2, bottomL+32);
		g.drawString("n = "+Integer.toString(parent.data.nodes.size()), (pww-leftL-45)/2, topL-15);
		
		//text 'movement'
		g.drawString("m", leftL-25, (pwh-topL-75)/2-15);
		g.drawString("o", leftL-25, (pwh-topL-75)/2);
		g.drawString("v", leftL-25, (pwh-topL-75)/2+15);
		g.drawString("e", leftL-25, (pwh-topL-75)/2+30);
		g.drawString("m", leftL-25, (pwh-topL-75)/2+45);
		g.drawString("e", leftL-25, (pwh-topL-75)/2+60);
		g.drawString("n", leftL-25, (pwh-topL-75)/2+75);
		g.drawString("t", leftL-25, (pwh-topL-75)/2+90);
		
		
		
	}					
	
	public GraphPanel(Display par) {
		parent = par;
		timer.start();// Start the timer here.
	}
	
	public void actionPerformed(ActionEvent ev){
		if(ev.getSource()==timer){
	      this.repaint();
	    }
	 }
}
