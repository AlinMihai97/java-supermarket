package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import events.Event;

public class ModifyProduct extends JFrame{

	Window parrent;
	public ModifyProduct(Component comp, int row, JTable table) {
		parrent = (Window) comp;
		setTitle("Modify product");
		setSize(new Dimension(200,200));
		setResizable(false);
		SpringLayout lyt = new SpringLayout();
		setVisible(true);
		JLabel modify_label = new JLabel("<html><center><p style=\"font-size:12px\">Modify this field<br> to modify the price</p></center></html>");
		JTextField txt_field = new JTextField((String) table.getValueAt(row, 3));
		txt_field.setPreferredSize(new Dimension(125,30));
		txt_field.setFont(new Font(txt_field.getFont().getName(),Font.PLAIN,20));
		txt_field.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(isDouble(txt_field.getText())) {
					if(Double.parseDouble(txt_field.getText()) >= 0) {
						Event.modifyProduct(Integer.parseInt((String) table.getValueAt(row, 2)),Integer.parseInt((String) table.getValueAt(row, 1)),Double.parseDouble(txt_field.getText()));
						parrent.init_table();
						parrent.setVisible(true);
						dispose();
					}
					else {
						modify_label.setText("<html><center><p style=\"font-size:12px;color:red\">Try a value <br> bigger than zero</p></center></html>");
					}
				}
				else {
					modify_label.setText("<html><center><p style=\"font-size:16px;color:red\">Value not <br>a number</p></center></html>");
				}
			}
		});
		lyt.putConstraint(SpringLayout.NORTH, modify_label, 20, SpringLayout.NORTH, getContentPane());
		lyt.putConstraint(SpringLayout.WEST, modify_label, 23, SpringLayout.WEST, getContentPane());	
		lyt.putConstraint(SpringLayout.NORTH, txt_field, 20, SpringLayout.SOUTH, modify_label);
		lyt.putConstraint(SpringLayout.WEST, txt_field, 30, SpringLayout.WEST, getContentPane());
		setLayout(lyt);
		add(modify_label);
		add(txt_field);
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
}
