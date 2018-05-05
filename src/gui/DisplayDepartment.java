package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import customer.Customer;
import departments.Department;

public class DisplayDepartment extends JFrame{
	Component lastWindow;
	
	
	DisplayDepartment(String selected_dept, Department current, Component component) {
		super();
		this.lastWindow = component;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(100,100));
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setResizable(false);
		setVisible(true);
		setTitle(selected_dept + " stats");
		setSize(new Dimension(400,525));
		SpringLayout lyt = new SpringLayout();
		JButton get_back = new JButton("<html><center><p style=\"font-size:12px\">Go Back</p></center></html>");
		get_back.setPreferredSize(new Dimension(150,40));
		get_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lastWindow.setVisible(true);
				dispose();
			}
		});
		DefaultTableModel observersm = new DefaultTableModel();
		DefaultTableModel customersm = new DefaultTableModel();
		observersm.addColumn("Department Observers");
		customersm.addColumn("Department Customers");
		ListIterator<Customer> c_it = current.getCustomers().listIterator();
		while(c_it.hasNext()) {
			String[] x = {c_it.next().getName()};
			customersm.addRow(x);
		}
		ListIterator<Customer> d_it = current.getObservers().listIterator();
		while(d_it.hasNext()) {
			String[] x = {d_it.next().getName()};
			observersm.addRow(x);
		}
		JTable t1 = new JTable(customersm);
		JTable t2 = new JTable(observersm);
		t1.setPreferredSize(new Dimension(152,240));
		t2.setPreferredSize(new Dimension(152,240));
		t1.getColumnModel().getColumn(0).setPreferredWidth(140);
		t2.getColumnModel().getColumn(0).setPreferredWidth(140);
	
		t1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		t2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JScrollPane s1 = new JScrollPane(t1);
		JScrollPane s2 = new JScrollPane(t2);
		//s1.setPreferredSize(new Dimension(160,240));
		//s2.setPreferredSize(new Dimension(160,240));
		
		JLabel most_wanted = new JLabel("<html><p style=\"font-size:12px\">Most wanted item: <p style=\"padding-left:35px;font-size:14px\">" + current.getMostWished() +"</p></p></html>");
		JLabel most_expensive_item = new JLabel("<html><p style=\"font-size:12px\">Most expensive item: <p style=\"padding-left:35px;font-size:14px\">" + current.getMostExpensive() +"</p></p></html>");
		lyt.putConstraint(SpringLayout.NORTH, s1, 20, SpringLayout.NORTH, getContentPane());
		lyt.putConstraint(SpringLayout.NORTH, s2, 20, SpringLayout.NORTH, getContentPane());
		lyt.putConstraint(SpringLayout.SOUTH, s1, -210, SpringLayout.SOUTH, getContentPane());
		lyt.putConstraint(SpringLayout.SOUTH, s2, -210, SpringLayout.SOUTH, getContentPane());
		
		lyt.putConstraint(SpringLayout.WEST, s1, 20, SpringLayout.WEST, getContentPane());
		lyt.putConstraint(SpringLayout.EAST, s1, -203, SpringLayout.EAST, getContentPane());
		lyt.putConstraint(SpringLayout.EAST, s2, -20, SpringLayout.EAST, getContentPane());
		lyt.putConstraint(SpringLayout.WEST, s2, 203, SpringLayout.WEST, getContentPane());
		
		lyt.putConstraint(SpringLayout.WEST, get_back, 125, SpringLayout.WEST, getContentPane());
		lyt.putConstraint(SpringLayout.SOUTH, get_back, -20, SpringLayout.SOUTH, getContentPane());
		
		lyt.putConstraint(SpringLayout.NORTH, most_wanted, 20, SpringLayout.SOUTH, s1);
		lyt.putConstraint(SpringLayout.NORTH, most_expensive_item, 20, SpringLayout.SOUTH, most_wanted);
		
		lyt.putConstraint(SpringLayout.WEST, most_wanted, 10, SpringLayout.WEST, getContentPane());
		lyt.putConstraint(SpringLayout.WEST, most_expensive_item, 10 , SpringLayout.WEST, getContentPane());
		
		add(s1);
		add(s2);
		add(most_wanted);
		add(most_expensive_item);
		add(get_back);
		
		
		setLayout(lyt);
	}
}
