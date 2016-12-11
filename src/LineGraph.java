
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class LineGraph implements ActionListener{
	JFrame simpleframe;
	
	LineGraph(int num, double radius, int type, boolean graph) {
        JPanel gui = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.NONE;
        gui.add(new Label("radius"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gui.add(new TextField(Double.toString(radius)),c);
        
        
        c.gridwidth = GridBagConstraints.RELATIVE;
        gui.add(new Label("number of sensors"),c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gui.add(new TextField(Integer.toString(num)),c);
        
        gui.add(new Label(""),c);
        
        Choice selector = new Choice();
		selector.add("Green");
        selector.add("Red");
        selector.add("Blue");
        selector.add("DickButt");
        
        switch (type){
        	case 0:
                selector.select(0);
                break;
        	case 1:
                selector.select(1);
                break;
        	case 2:
                selector.select(2);
                break;
        	case 3:
            	selector.select(3);
        		break;
        }
        
        gui.add(selector, c);
        
        gui.add(new Label(""),c);
        
        CheckboxGroup graphs = new CheckboxGroup();
        
        Checkbox g1 = new Checkbox("ASS");
        Checkbox g2 = new Checkbox("CLOWNS");
        
        g1.setCheckboxGroup(graphs);
        g2.setCheckboxGroup(graphs);
        
        if(graph)
        	g2.setState(true);
        else
        	g1.setState(true);
        
        c.gridwidth = GridBagConstraints.RELATIVE;
        gui.add(g1,c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gui.add(g2,c);
        
        gui.add(new Label(""),c);
        
        Button confirm = new Button("OK");
        confirm.addActionListener(this);
        
        gui.add(confirm,c);
        
        
        
       JFrame simpleframe = new JFrame("Line Graph");
       simpleframe.setSize(250, 300);
       simpleframe.add(gui);
       simpleframe.setVisible(true);
       
    }
	
	public void actionPerformed(ActionEvent e){
	}
}