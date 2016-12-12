import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AnimationPanel extends JPanel implements ActionListener {
	
	Display parent;
	Timer timer=new Timer(250, this);

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.drawLine(10, parent.window.getHeight()/2, parent.window.getWidth()-25, parent.window.getHeight()/2);
		for (int i=0; i<parent.data.nodes.size(); i++){
		//draw circle(s) at location
			int diameter = (int) ((parent.window.getWidth()-25-10)*(2 * parent.data.r));
			int xPos = (int) (10+(parent.window.getWidth()-25-10)*parent.data.nodes.get(i)-(diameter/2));
			
			g.drawOval(xPos, parent.window.getHeight()/2-(diameter/2), diameter, diameter);

			g.drawLine(xPos+(diameter/2), parent.window.getHeight()/2-(diameter/2), xPos+(diameter/2), parent.window.getHeight()/2+(diameter/2));
		}
	}					
	
	public AnimationPanel(Display par) {
		parent = par;
		timer.start();// Start the timer here.
	}
	
	public void actionPerformed(ActionEvent ev){
		if(ev.getSource()==timer){
	      parent.updateCurrentState();
	    }
	 }
}
