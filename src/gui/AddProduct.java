package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import departments.Department;
import events.Event;
import store.Store;

public class AddProduct extends JFrame{
	
	Window parrent;
	public AddProduct(String[] department_names, Component previous) {
		parrent = (Window) previous;
		setTitle("Add product");
		setSize(new Dimension(200,350));
		setVisible(true);
		setResizable(false);
		SpringLayout lyt = new SpringLayout();
			
		JLabel add_label = new JLabel("<html><center><p style=\"font-size:12px\">Add item<br>specifications</p></center></html>");
		JTextField txt_field = new JTextField("Item name");
		txt_field.setPreferredSize(new Dimension(125,30));
		txt_field.setFont(new Font(txt_field.getFont().getName(),Font.PLAIN,20));
		
		JTextField txt2_field = new JTextField("Item Id");
		txt2_field.setPreferredSize(new Dimension(125,30));
		txt2_field.setFont(new Font(txt2_field.getFont().getName(),Font.PLAIN,20));
	
		JTextField txt3_field = new JTextField("Item Price");
		txt3_field.setPreferredSize(new Dimension(125,30));
		txt3_field.setFont(new Font(txt3_field.getFont().getName(),Font.PLAIN,20));

		Vector<String> depts = new Vector();
		for(int i=0;i<4;i++)
			if(department_names[i] != null)
				depts.add(department_names[i]);
		JComboBox dept_select = new JComboBox(depts);
        dept_select.setPreferredSize(new Dimension(130,30));
        
        JButton load = new JButton("<html><p style=\"font-size:12px\">Add Product</p></html>");
        load.setPreferredSize(new Dimension(140,50));
        load.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		if(!isDouble(txt3_field.getText())) {
        			add_label.setText("<html><center><p style=\"font-size:12px;color:red\">    Price not <br>    a number</p></center></html>");
        			return;
        		}
        		if(!isInteger(txt2_field.getText())) {
        			add_label.setText("<html><center><p style=\"font-size:12px;color:red\">ID not a number</p></center></html>");
        			return;
        		}
        		if(Double.parseDouble(txt3_field.getText()) < 0) {
        			add_label.setText("<html><center><p style=\"font-size:12px;color:red\">Try a value <br> bigger than zero</p></center></html>");
        			return;
        		}
        		Department select = null;
				ListIterator dept_it = Store.getInstance().getDepartments().listIterator();
				while(dept_it.hasNext()) 
				{
					Department current = (Department) dept_it.next();
					if(current.getName().equals((String)dept_select.getSelectedItem())) {
						select = current;
					}
					if(current.getItem( Integer.parseInt(txt2_field.getText())) != null) {
						add_label.setText("<html><center><p style=\"font-size:12px;color:red\">Item already <br>exists</p></center></html>");
						return;
					}
					
				}
				Event.addProduct(select.getId(),Integer.parseInt(txt2_field.getText()) ,Double.parseDouble(txt3_field.getText()), txt_field.getText());
				parrent.setVisible(true);
				parrent.init_table();
				dispose();
			}
        	
        });
		lyt.putConstraint(SpringLayout.NORTH, add_label, 20, SpringLayout.NORTH, getContentPane());
		lyt.putConstraint(SpringLayout.WEST, add_label, 40, SpringLayout.WEST, getContentPane());
			
		lyt.putConstraint(SpringLayout.NORTH, txt_field, 10, SpringLayout.SOUTH, add_label);
		lyt.putConstraint(SpringLayout.WEST, txt_field, 30, SpringLayout.WEST, getContentPane());
		
		lyt.putConstraint(SpringLayout.NORTH, txt2_field, 10, SpringLayout.SOUTH, txt_field);
		lyt.putConstraint(SpringLayout.WEST, txt2_field, 30, SpringLayout.WEST, getContentPane());
		
		lyt.putConstraint(SpringLayout.NORTH, txt3_field, 10, SpringLayout.SOUTH, txt2_field);
		lyt.putConstraint(SpringLayout.WEST, txt3_field, 30, SpringLayout.WEST, getContentPane());
		
		lyt.putConstraint(SpringLayout.NORTH, dept_select, 10, SpringLayout.SOUTH, txt3_field);
		lyt.putConstraint(SpringLayout.WEST, dept_select, 30, SpringLayout.WEST, getContentPane());
		
		lyt.putConstraint(SpringLayout.NORTH, load, 10, SpringLayout.SOUTH, dept_select);
		lyt.putConstraint(SpringLayout.WEST, load, 23, SpringLayout.WEST, getContentPane());
		
		setLayout(lyt);
		add(add_label);
		add(txt_field);
		add(txt2_field);
		add(txt3_field);
		add(dept_select);
		add(load);
	}
	private boolean isDouble(String in) {
		try {
			Double.parseDouble(in);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	private boolean isInteger(String in) {
		try {
			Integer.parseInt(in);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
}
