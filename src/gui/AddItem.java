package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import departments.Department;
import events.Event;
import lists.Item;
import lists.ItemList;
import lists.ShoppingCart;
import store.Store;

public class AddItem extends JFrame{

	Window parrent_component;
	JTable table;
	DefaultTableModel modelt;
	AddItem(ItemList selected_list, Component comp, String customer_name) {
		SpringLayout lyt = new SpringLayout();
		this.parrent_component = (Window) comp;
		setTitle("Add item to list");
		setSize(new Dimension(500,500));
		setResizable(false);
		setVisible(true);
		table = new JTable();
        init_table();
        JScrollPane lstscroll = new JScrollPane(table);
        //table.setPreferredSize(new Dimension(430,300));
        JButton back = new JButton("<html><center><p style = \"font-size:12px\">Add item<p></center></html>");
        back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow() >= 0) {
					if(selected_list instanceof ShoppingCart)
						Event.addItem(Integer.parseInt((String)	table.getValueAt(table.getSelectedRow(), 1)), "ShoppingCart", customer_name);
					else 
						Event.addItem(Integer.parseInt((String)	table.getValueAt(table.getSelectedRow(), 1)), "WishList", customer_name);
				}
				parrent_component.setVisible(true);
				parrent_component.init_item_list();
				parrent_component.reinit_labels();
				dispose();
			}
        	
        });
        back.setPreferredSize(new Dimension(160,90));
        
        lyt.putConstraint(SpringLayout.NORTH, lstscroll, 30, SpringLayout.NORTH, getContentPane());
        lyt.putConstraint(SpringLayout.EAST,lstscroll, -26, SpringLayout.EAST, getContentPane());
        lyt.putConstraint(SpringLayout.WEST, lstscroll, 20, SpringLayout.WEST, getContentPane());
        lyt.putConstraint(SpringLayout.SOUTH, lstscroll, -120, SpringLayout.SOUTH, getContentPane());
        
        lyt.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, getContentPane());
        lyt.putConstraint(SpringLayout.WEST, back, 170, SpringLayout.WEST, getContentPane());
        
		setLayout(lyt);
		add(back);
        add(lstscroll);
	}
	private void init_table_model() {
		modelt = new DefaultTableModel();
		modelt.addColumn("Name");
		modelt.addColumn("Item_Id");
		modelt.addColumn("Department_Id");
		modelt.addColumn("Price");
	}
	private void init_table() {
		init_table_model();
		table.setModel(modelt);
		table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		
		ArrayList<Department> dept_list = Store.getInstance().getDepartments();
		ArrayList<Item> show_list = new ArrayList();
		ListIterator dept_it = dept_list.listIterator();
		while(dept_it.hasNext()) 
			show_list.addAll(((Department)dept_it.next()).getItems());
		show_list.sort(new Comparator() {

			@Override
			public int compare(Object arg0, Object arg1) {
				Item I1 = (Item) arg0;
				Item I2 = (Item) arg1;
				return I1.getName().compareTo(I2.getName());
			}
			
			
		});
		for(int i = 0; i < show_list.size(); i++) {
			String[] new_row = {show_list.get(i).getName(), ((Integer)show_list.get(i).getId()).toString()
					, ((Integer)show_list.get(i).getDepartment().getId()).toString()
					,((Double)show_list.get(i).getPrice()).toString()};
			modelt.addRow(new_row);
		}
		table.setModel(modelt);
	}
}
