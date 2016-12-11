
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class GUI implements ActionListener{

	JPanel gui= new JPanel(new GridBagLayout());
	TextField radius = new TextField();
	TextField num = new TextField();
    Choice selector = new Choice();
    Checkbox g1 = new Checkbox("ASS");
    Checkbox g2 = new Checkbox("CLOWNS");
	
	GUI() {
       
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.NONE;
        gui.add(new Label("radius"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gui.add(radius,c);
        
        
        c.gridwidth = GridBagConstraints.RELATIVE;
        gui.add(new Label("number of sensors"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gui.add(num,c);
        
        gui.add(new Label(""),c);
        
        selector.add("Green");
        selector.add("Red");
        selector.add("Blue");
        selector.add("DickButt");
        
        gui.add(selector, c);
        
        gui.add(new Label(""),c);
        
        CheckboxGroup graphs = new CheckboxGroup();
        
        
        g1.setCheckboxGroup(graphs);
        g2.setCheckboxGroup(graphs);
        
        c.gridwidth = GridBagConstraints.RELATIVE;
        gui.add(g1,c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gui.add(g2,c);
        
        gui.add(new Label(""),c);
        
        Button confirm = new Button("OK");
        confirm.addActionListener(this);
        
        gui.add(confirm,c);
        
        
        
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
	            	try{
	            	if(g1.getState())
	            		new LineGraph(Integer.parseInt(num.getText()),Double.parseDouble(radius.getText()), selector.getSelectedIndex(), false);
	            	else
	            		new LineGraph(Integer.parseInt(num.getText()),Double.parseDouble(radius.getText()), selector.getSelectedIndex(), true);
	            	}
	            	catch(Exception e)
	            	{JOptionPane.showMessageDialog(null, "ERROR PLEASE CORRECT INPUT");}
	            }
	        });
	}

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}