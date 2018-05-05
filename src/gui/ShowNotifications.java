package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import customer.Customer;
import events.Event;
import notification.Notification;

public class ShowNotifications extends JFrame{

	Window parent;
	public ShowNotifications(String customer_name, Window window) {
		parent = window;
		SpringLayout lyt = new SpringLayout();
		setTitle("Notifications");
		setSize(new Dimension(400,500));
		setResizable(false);
		setVisible(true);
		Customer customer = Event.getCustomer(customer_name);
		ArrayList<Notification> list = customer.getNotification_list();
		
		DefaultTableModel tbmd = new DefaultTableModel();
		tbmd.addColumn("Type");
		tbmd.addColumn("Item ID");
		tbmd.addColumn("Dept ID");
		tbmd.addColumn("Date");
		for(Notification x: list) {
			String type = "";
			switch(x.getNotificationType()) {
			case ADD:
				type = "ADD";
				break;
			case MODIFY:
				type = "MODIFY";
				break;
			case REMOVE:
				type = "REMOVE";
				break;
			}
			String[] new_row = {type, ((Integer)x.getItem_id()).toString() , ((Integer)x.getDepartment_id()).toString(),x.getDate().toString()};
			tbmd.addRow(new_row);
		}
		JTable t1 = new JTable(tbmd);
		t1.getColumnModel().getColumn(0).setPreferredWidth(80);
		t1.getColumnModel().getColumn(1).setPreferredWidth(50);
		t1.getColumnModel().getColumn(2).setPreferredWidth(50);
		t1.getColumnModel().getColumn(3).setPreferredWidth(100);
		JButton ret = new JButton("<html><p style=\"font-size:12px\">Go Back</p></html>");
		ret.setPreferredSize(new Dimension(160,90));
		JScrollPane lstscroll = new JScrollPane(t1);
		
		lyt.putConstraint(SpringLayout.NORTH, lstscroll, 30, SpringLayout.NORTH, getContentPane());
        lyt.putConstraint(SpringLayout.EAST,lstscroll, -40, SpringLayout.EAST, getContentPane());
        lyt.putConstraint(SpringLayout.WEST, lstscroll, 35, SpringLayout.WEST, getContentPane());
        lyt.putConstraint(SpringLayout.SOUTH, lstscroll, -120, SpringLayout.SOUTH, getContentPane());
        
        lyt.putConstraint(SpringLayout.SOUTH, ret, -20, SpringLayout.SOUTH, getContentPane());
        lyt.putConstraint(SpringLayout.WEST, ret, 120 , SpringLayout.WEST, getContentPane());
        add(lstscroll);
        add(ret);
        setLayout(lyt);
        
        ret.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.setVisible(true);
				dispose();
			}
        });
	}
}
