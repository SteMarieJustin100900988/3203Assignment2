
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class GUI implements ActionListener{

	JPanel gui= new JPanel(new GridBagLayout());
	//declare fields to pass their values to the next screen
	TextField radius = new TextField();
	TextField num = new TextField();
    Choice selector = new Choice();
    Checkbox g1 = new Checkbox("Animated");
    Checkbox g2 = new Checkbox("Graph");
	
	GUI() {
       //neatly arrange main menu
        GridBagConstraints c = new GridBagConstraints();
        
        //radius input 
        c.fill = GridBagConstraints.NONE;
        gui.add(new Label("radius"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gui.add(radius,c);
        
        //sensor amount input
        c.gridwidth = GridBagConstraints.RELATIVE;
        gui.add(new Label("number of sensors"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gui.add(num,c);
        
        gui.add(new Label(""),c);//used as a spacer
        
        //algorithms types
        selector.add("Rigid");
        selector.add("Simple");
        selector.add("Simple Double Pass");
        selector.add("Hybrid");
        
        gui.add(selector, c);
        
        gui.add(new Label(""),c);//another spacer
        
        //check boxes to determine whether to use a animated line graph or averaged scatter plot
        CheckboxGroup graphs = new CheckboxGroup();
        
        g1.setCheckboxGroup(graphs);
        g2.setCheckboxGroup(graphs);
        
        c.gridwidth = GridBagConstraints.RELATIVE;
        gui.add(g1,c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gui.add(g2,c);
        
        gui.add(new Label(""),c);//final spacer
        
        //confirmation button
        Button confirm = new Button("OK");
        confirm.addActionListener(this);
        
        gui.add(confirm,c);
        
        
       //construct and display main menu.
       JFrame simpleframe = new JFrame("Sensor Controller");
       simpleframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       simpleframe.setSize(250, 300);
       simpleframe.add(gui);
       simpleframe.setVisible(true);
       
    }
	
	public void actionPerformed(ActionEvent e){
		 EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	            	//pass information through to be used to construct graphs but catch any invalid input
	            	try{
		            	if(!g1.getState())
		            		new Display(new AlgorithmSetup(Integer.parseInt(num.getText()),Double.parseDouble(radius.getText()), selector.getSelectedIndex(), false));
		            	else
		            		new Display(new AlgorithmSetup(Integer.parseInt(num.getText()),Double.parseDouble(radius.getText()), selector.getSelectedIndex(), true));
	            	}
	            	catch(Exception e)
	            	{JOptionPane.showMessageDialog(null, "ERROR PLEASE CORRECT INPUT");}
	            }
	        });
	}

    public static void main(String[] args) {
    	//create main menu on start up
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}