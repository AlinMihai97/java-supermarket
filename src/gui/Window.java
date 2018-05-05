package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import customer.Customer;
import departments.BookDepartment;
import departments.Department;
import departments.MusicDepartment;
import departments.SoftwareDepartment;
import departments.VideoDepartment;
import events.Event;
import lists.Item;
import lists.ItemList;
import lists.ShoppingCart;
import store.Store;

public class Window extends JFrame{
	
	Comparator<Item> c1 = new Comparator<Item>() {
		@Override
		public int compare(Item arg0, Item arg1) {
			return arg0.getName().compareTo(arg1.getName());
		}
	};
	Comparator<Item> c2 = new Comparator<Item>() {

		@Override
		public int compare(Item arg0, Item arg1) {
			if(arg0.getPrice() != arg1.getPrice())
			{
				double ret_val = arg0.getPrice() - arg1.getPrice();
				if(ret_val < 0)
					return -1;
				else return 1;
			}
			else {
				return arg0.getName().compareTo(arg1.getName());
			}
		}
	
	};
	Comparator<Item> c3 = new Comparator<Item>() {
		@Override
		public int compare(Item arg0, Item arg1) {
			if(arg0.getPrice() != arg1.getPrice())
			{
				double ret_val = arg0.getPrice() - arg1.getPrice();
				if(ret_val < 0)
					return 1;
				else return -1;
			}
			else {
				return arg0.getName().compareTo(arg1.getName());
			}
		}
	};
	private static Window instance = null;
	Comparator<Item> selected_store_comparator = c1;
	SpringLayout layout;
	private File store_file = new File("store.txt");
	private File customers_file = new File("customers.txt");
	
	//HERE WE START DECLARING COMPONENTS FOR LISTENER CALL
	private JTable table;
	private DefaultTableModel modelt;
	private DefaultTableModel cust_model;
	private DefaultTableModel item_model;
	
	private JTextField store_field;
	private JTextField customers_field;
	private JLabel store_label;
	private JLabel customers_label;
	private JButton start_button;
	private JScrollPane lstscroll;
	private JButton add_product ;
    private JButton del_product;
    private JButton modify_product;
    
    private JButton bookDepartment;
    private JButton musicDepartment;
    private JButton videoDepartment;
    private JButton softwareDepartment;
    
    private ItemList selected_list=null;
    private Customer selected_customer=null;
    
    private JComboBox<String> list_select;
    private JTable t1;
    private JTable t2;
    private JLabel current_budget;
    private JLabel list_total;
    private JButton order_cart;
    
    private String[] department_names = new String[4];
 
	private Window(String window_type) {
		super(window_type);
		layout = new SpringLayout();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(100,100));
		getContentPane().setBackground(Color.LIGHT_GRAY);
	}
	public static Window getInstance() {
		if(instance == null) {
			instance = new Window("SuperMarket");
		}
		return instance;
	}
	public void LoadFiles() {
		getContentPane().removeAll();
		setSize(new Dimension(550,260));
		setResizable(false);
		
		start_button =  new JButton("<html><center><p style=\"font-size:16px\"> Enter <br> Supermarket </p></center></html>");
		start_button.setPreferredSize(new Dimension(200,100));
		start_button.setFocusPainted(false);
		JLabel info_label = new JLabel("<html><center><p style = \"font-size:12px\">Clicking on this button <br> loads the input files <br>and enters supermarket<p></center></html>");
		JLabel info_label2 = new JLabel("<html><center><p style = \"font-size:12px\">Clicking on this text fields <br> allows you to change <br> the input files<p></center></html>");
		
		store_field = new JTextField(store_file.getName());
		store_field.setPreferredSize(new Dimension(125,30));
		store_field.setFont(new Font(store_field.getFont().getName(),Font.PLAIN,20));
		store_label = new JLabel("<html><center>Store file: </center><html>");
		store_label.setPreferredSize(new Dimension(100,20));
		store_label.setFont(new Font(store_label.getFont().getName(),Font.PLAIN,20));
		
		customers_field = new JTextField(customers_file.getName());
		customers_field.setPreferredSize(new Dimension(125,30));
		customers_field.setFont(new Font(customers_field.getFont().getName(),Font.PLAIN,20));
		customers_label = new JLabel("<html><center>Customers file:</center><html>");
		customers_label.setPreferredSize(new Dimension(150,20));
		customers_label.setFont(new Font(customers_label.getFont().getName(),Font.PLAIN,20));
		
		layout.putConstraint(SpringLayout.WEST, start_button, 20, SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, start_button, 25, SpringLayout.NORTH, getContentPane());
		
		layout.putConstraint(SpringLayout.NORTH, info_label, 15, SpringLayout.SOUTH, start_button);
		layout.putConstraint(SpringLayout.WEST, info_label, 28, SpringLayout.WEST, getContentPane());
		
		layout.putConstraint(SpringLayout.EAST, store_field, -30, SpringLayout.EAST, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, store_field, 38, SpringLayout.NORTH, getContentPane());
		
		layout.putConstraint(SpringLayout.EAST, store_label, 10, SpringLayout.WEST, store_field);
		layout.putConstraint(SpringLayout.NORTH, store_label, 45, SpringLayout.NORTH, getContentPane());

		layout.putConstraint(SpringLayout.EAST, customers_field, -30, SpringLayout.EAST, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, customers_field, 22, SpringLayout.SOUTH, store_field);
		
		layout.putConstraint(SpringLayout.EAST, customers_label, 10, SpringLayout.WEST, customers_field);
		layout.putConstraint(SpringLayout.NORTH, customers_label, 30, SpringLayout.SOUTH, store_label);
		
		layout.putConstraint(SpringLayout.NORTH, info_label2, 25, SpringLayout.SOUTH, customers_label);
		layout.putConstraint(SpringLayout.EAST, info_label2, -45, SpringLayout.EAST, getContentPane());
		
		setLayout(layout);
		add(start_button);
		add(info_label);
		add(store_label);
		add(store_field);
		add(customers_label);
		add(customers_field);
		add(info_label2);
		setVisible(true);
		
		store_field.addMouseListener(new HomeScreenMouse());
		store_label.addMouseListener(new HomeScreenMouse());
		customers_field.addMouseListener(new HomeScreenMouse());
		customers_label.addMouseListener(new HomeScreenMouse());
		start_button.addMouseListener(new HomeScreenMouse());
	}
	public void SetSuperMarketEnviroment() {
		setTitle("SuperMarket");
		setSize(new Dimension(800,680));
		getContentPane().removeAll();
		setVisible(true);
		getContentPane().setLayout(new GridLayout());
		//Tabing the work area
        JTabbedPane tabs = new JTabbedPane();
        getContentPane().add(tabs);
        JPanel tab1 = new JPanel();
        JPanel tab2 = new JPanel();
        tabs.addTab("Tab1", tab1);
        tabs.addTab("Tab2", tab2);
        
        JLabel lab = new JLabel("<html><p style=\"font-size:12px\">Store Management</p></html>");
        lab.setPreferredSize(new Dimension(200, 30));
        tabs.setTabComponentAt(0, lab);  // tab index, jLabel
        
        JLabel lab2 = new JLabel("<html><p style=\"font-size:12px\">Client Management</p></html>");
        lab2.setPreferredSize(new Dimension(200, 30));
        tabs.setTabComponentAt(1, lab2);  // tab index, jLabel
        
        //creating the look of StoreManagement
        SpringLayout store_layout = new SpringLayout();
        
        JLayeredPane store_left_pane = new JLayeredPane();
        store_left_pane.setBorder(BorderFactory.createTitledBorder("<html><p style=\"font-size:12px\">Edit Items</p></html>"));
        store_left_pane.setPreferredSize(new Dimension(500,593));
        
        JLayeredPane store_right_pane = new JLayeredPane();
        store_right_pane.setBorder(BorderFactory.createTitledBorder("<html><p style=\"font-size:12px\">View Departments</p></html>"));
        store_right_pane.setPreferredSize(new Dimension(270,593));
        
        tab1.setLayout(new FlowLayout());
        tab1.add(store_left_pane);
        tab1.add(store_right_pane);
        
        JLabel sortya = new JLabel("<html><p style=\"font-size:10px\">Sorting order: </p></html>");
        String[] sort_types = {"A to Z", "Cheap to Expensive" , "Expensive to Cheap"};
        JComboBox<String> sort_select = new JComboBox<>(sort_types);
        sort_select.setPreferredSize(new Dimension(170,20));
        sort_select.addActionListener(new SortSelectOrder());
        
        table = new JTable();
        init_table();
        lstscroll = new JScrollPane(table);
        
		store_left_pane.add(sortya);
        store_left_pane.add(sort_select);
        store_left_pane.add(lstscroll);
        
        add_product = new JButton("<html><p style=\"font-size:12px\">Add Product</p></html>");
        del_product = new JButton("<html><p style=\"font-size:12px\">Del Product</p></html>");
        modify_product = new JButton("<html><p style=\"font-size:12px\">Modify Product</p></html>");
        add_product.setPreferredSize(new Dimension(140,80));
        del_product.setPreferredSize(new Dimension(140,80));
        modify_product.setPreferredSize(new Dimension(150,80));
        add_product.addActionListener(new ProductButtons());
        del_product.addActionListener(new ProductButtons());
        modify_product.addActionListener(new ProductButtons());
        
        store_layout.putConstraint(SpringLayout.WEST, lstscroll, 25, SpringLayout.WEST, store_left_pane);
        store_layout.putConstraint(SpringLayout.EAST, lstscroll, -17, SpringLayout.EAST, store_left_pane);
        store_layout.putConstraint(SpringLayout.NORTH, lstscroll, 40, SpringLayout.NORTH, store_left_pane);
        
        store_layout.putConstraint(SpringLayout.WEST, sortya,70, SpringLayout.WEST, store_left_pane);
        store_layout.putConstraint(SpringLayout.NORTH, sortya, 10, SpringLayout.NORTH, store_left_pane);
        
        store_layout.putConstraint(SpringLayout.WEST, sort_select, 15, SpringLayout.EAST, sortya);
        store_layout.putConstraint(SpringLayout.NORTH, sort_select, 10, SpringLayout.NORTH, store_left_pane);
        
        store_layout.putConstraint(SpringLayout.SOUTH, add_product, -10, SpringLayout.SOUTH, store_left_pane);
        store_layout.putConstraint(SpringLayout.WEST, add_product, 10, SpringLayout.WEST, store_left_pane);
        store_layout.putConstraint(SpringLayout.SOUTH, del_product, -10, SpringLayout.SOUTH, store_left_pane);
        store_layout.putConstraint(SpringLayout.WEST, del_product, 20, SpringLayout.EAST, add_product);
        store_layout.putConstraint(SpringLayout.SOUTH, modify_product, -10, SpringLayout.SOUTH, store_left_pane);
        store_layout.putConstraint(SpringLayout.WEST, modify_product, 20, SpringLayout.EAST, del_product);
        
        store_left_pane.add(add_product);
        store_left_pane.add(del_product);
        store_left_pane.add(modify_product);
        store_left_pane.setLayout(store_layout);
        //creating right pane look
        SpringLayout dept_select_layout = new SpringLayout();
        bookDepartment = new JButton("<html><p style=\"font-size:12px\">Book Department</p></html>");
        musicDepartment = new JButton("<html><p style=\"font-size:12px\">Music Department</p></html>");
        videoDepartment = new JButton("<html><p style=\"font-size:12px\">Video Department</p></html>");
        softwareDepartment = new JButton("<html><p style=\"font-size:12px\">Software Department</p></html>");
        bookDepartment.setPreferredSize(new Dimension(200,100));
        musicDepartment.setPreferredSize(new Dimension(200,100));
        videoDepartment.setPreferredSize(new Dimension(200,100));
        softwareDepartment.setPreferredSize(new Dimension(200,100));
        
        dept_select_layout.putConstraint(SpringLayout.EAST, bookDepartment, -27, SpringLayout.EAST, store_right_pane);
        dept_select_layout.putConstraint(SpringLayout.NORTH, bookDepartment, 27, SpringLayout.NORTH, store_right_pane);
        dept_select_layout.putConstraint(SpringLayout.EAST, musicDepartment, -27, SpringLayout.EAST, store_right_pane);
        dept_select_layout.putConstraint(SpringLayout.NORTH, musicDepartment, 34, SpringLayout.SOUTH, bookDepartment);
        dept_select_layout.putConstraint(SpringLayout.EAST, videoDepartment, -27, SpringLayout.EAST, store_right_pane);
        dept_select_layout.putConstraint(SpringLayout.NORTH, videoDepartment, 34, SpringLayout.SOUTH, musicDepartment);
        dept_select_layout.putConstraint(SpringLayout.EAST, softwareDepartment, -27, SpringLayout.EAST, store_right_pane);
        dept_select_layout.putConstraint(SpringLayout.NORTH, softwareDepartment, 34, SpringLayout.SOUTH, videoDepartment);
        
        if(department_names[0] == null) {
        	bookDepartment.setEnabled(false);
        }
        else {
        	bookDepartment.addActionListener(new Dept_buttons());
        }
        if(department_names[1] == null) {
        	musicDepartment.setEnabled(false);
        }
        else {
        	musicDepartment.addActionListener(new Dept_buttons());
        }
        if(department_names[2] == null) {
        	videoDepartment.setEnabled(false);
        }
        else {
        	videoDepartment.addActionListener(new Dept_buttons());
        }
        if(department_names[3] == null) {
        	softwareDepartment.setEnabled(false);
        }
        else {
        	softwareDepartment.addActionListener(new Dept_buttons());
        }
        store_right_pane.setLayout(dept_select_layout);
        store_right_pane.add(bookDepartment);
        store_right_pane.add(musicDepartment);
        store_right_pane.add(videoDepartment);
        store_right_pane.add(softwareDepartment);
        
        //creating the look of ClientManagement
        JLayeredPane client_left_pane = new JLayeredPane();
        client_left_pane.setBorder(BorderFactory.createTitledBorder("<html><p style=\"font-size:12px\">Customer View</p></html>"));
        client_left_pane.setPreferredSize(new Dimension(270,593));
        
        JLayeredPane client_right_pane = new JLayeredPane();
        client_right_pane.setBorder(BorderFactory.createTitledBorder("<html><p style=\"font-size:12px\">List Edit</p></html>"));
        client_right_pane.setPreferredSize(new Dimension(500,593));
        SpringLayout lyt_clients = new SpringLayout();
        JButton add_item = new JButton("<html><p style=\"font-size:12px\">Add Item</p></html>");
        JButton del_item = new JButton("<html><p style=\"font-size:12px\">Delete Item</p></html>");
        order_cart = new JButton("<html><p style=\"font-size:12px\">Place Order</p></html>");
        order_cart.setEnabled(true);
        JButton suggest_item = new JButton("<html><p style=\"font-size:12px\">Suggest Item</p></html>");
        JButton get_notifications = new JButton("<html><p style=\"font-size:12px\">Show Notifications</p></html>");
        add_item.setPreferredSize(new Dimension(160,90));
        del_item.setPreferredSize(new Dimension(160,90));
        order_cart.setPreferredSize(new Dimension(160,90));
        suggest_item.setPreferredSize(new Dimension(160,90));
        get_notifications.setPreferredSize(new Dimension(190,90));
        
        t2 = new JTable();
        cust_model = new DefaultTableModel();
        cust_model.addColumn("Customers");
        init_customer_list();
        
        init_item_list();
        
        String[] input = {"Shopping Cart", "Wish List"};
        list_select = new JComboBox<String>(input);
        list_select.setPreferredSize(new Dimension(150,25));
        list_select.setFont( new Font(list_select.getFont().getName(), Font.PLAIN, 14) );
        list_select.addActionListener(new ActionListener() {
			@Override
		public void actionPerformed(ActionEvent arg0) {
			if( t1.getSelectedRow() >= 0) {
				Customer customer = Event.getCustomer(( String) t1.getValueAt(t1.getSelectedRow(),0));
				if(list_select.getSelectedItem().equals("Shopping Cart")) {
					order_cart.setEnabled(true);
					selected_list = customer.getShoppingCart();
				}
				else {
					order_cart.setEnabled(false);
					selected_list = customer.getWishlist();
				}
			}
			init_item_list();
			reinit_labels();
		}
        	
        });
        t1 = new JTable(cust_model);
       
        
        JLabel slct = new JLabel("<html><center><p style=\"font-size:12px\">Select customer here "
        		+ "to view <br> his items in the right pane</p></center></html>");
        JLabel slct2 = new JLabel("<html><p style=\"font-size:12px\">Select List: </p></html>");
        t1.getColumnModel().getColumn(0).setPreferredWidth(100);
        t1.getTableHeader().setFont(new Font(t1.getFont().getFontName(), Font.BOLD, 17));
        t2.getColumnModel().getColumn(0).setPreferredWidth(135);
		t2.getColumnModel().getColumn(1).setPreferredWidth(80);
		t2.getColumnModel().getColumn(2).setPreferredWidth(80);
		t2.getColumnModel().getColumn(3).setPreferredWidth(95);
		t2.getTableHeader().setFont(new Font(t2.getFont().getFontName(), Font.BOLD, 17));
		
		t1.setPreferredSize(new Dimension(100,300));
		t2.setPreferredSize(new Dimension(460,280));
		t1.setFont(new Font(t1.getFont().getFontName(), Font.PLAIN, 16));
		t2.setFont(new Font(t2.getFont().getFontName(), Font.PLAIN, 16));
		t1.setRowHeight(20);
		JScrollPane jsp1 = new JScrollPane(t1);
		JScrollPane jsp2 = new JScrollPane(t2);
		//jsp1.setPreferredSize(t1.getPreferredSize());
		//jsp2.setPreferredSize(t2.getPreferredSize());
		
		current_budget = new JLabel("<html><p style=\"font-size:12px\">Available Budget: $$$</p></html>");
	    list_total = new JLabel("<html><p style=\"font-size:12px\">List Total: $$$</p></html>");
	    
	    JTextField current_budget_field = new JTextField();
	    JTextField list_total_field = new JTextField();
	    current_budget_field.setEditable(false);
	    list_total_field.setEditable(false);
	    current_budget_field.setPreferredSize(new Dimension(80,20));
	    list_total_field.setPreferredSize(new Dimension(80,20));
	    current_budget_field.setFont(new Font(current_budget_field.getFont().getFontName(), Font.PLAIN, 14));
	    list_total_field.setFont(new Font(list_total_field.getFont().getFontName(), Font.PLAIN, 14));
	    
		lyt_clients.putConstraint(SpringLayout.WEST, slct, 22, SpringLayout.WEST, client_left_pane);
		lyt_clients.putConstraint(SpringLayout.NORTH, slct, 10, SpringLayout.NORTH, client_left_pane);
		lyt_clients.putConstraint(SpringLayout.WEST, jsp1, 48, SpringLayout.WEST, client_left_pane);
		lyt_clients.putConstraint(SpringLayout.EAST, jsp1, -48, SpringLayout.EAST, client_left_pane);
		lyt_clients.putConstraint(SpringLayout.NORTH, jsp1, 15, SpringLayout.SOUTH, slct);
		lyt_clients.putConstraint(SpringLayout.SOUTH, jsp1, -200, SpringLayout.SOUTH, client_left_pane);
		lyt_clients.putConstraint(SpringLayout.WEST, current_budget, 15, SpringLayout.WEST, client_left_pane);
		lyt_clients.putConstraint(SpringLayout.NORTH, current_budget, 15, SpringLayout.SOUTH, jsp1);
		lyt_clients.putConstraint(SpringLayout.WEST, list_total, 15, SpringLayout.WEST, client_left_pane);
		lyt_clients.putConstraint(SpringLayout.NORTH, list_total, 8, SpringLayout.SOUTH, current_budget);
		lyt_clients.putConstraint(SpringLayout.WEST, get_notifications, 37, SpringLayout.WEST, client_left_pane);
		lyt_clients.putConstraint(SpringLayout.NORTH, get_notifications, 20, SpringLayout.SOUTH, list_total);
		
		lyt_clients.putConstraint(SpringLayout.WEST, slct2, 30, SpringLayout.WEST, client_right_pane);
		lyt_clients.putConstraint(SpringLayout.NORTH, slct2, 10, SpringLayout.NORTH, client_right_pane);
		lyt_clients.putConstraint(SpringLayout.WEST, list_select, 15, SpringLayout.EAST, slct2);
		lyt_clients.putConstraint(SpringLayout.NORTH, list_select, 8, SpringLayout.NORTH, client_right_pane);
		lyt_clients.putConstraint(SpringLayout.WEST, jsp2, 22, SpringLayout.WEST, client_right_pane );
		lyt_clients.putConstraint(SpringLayout.NORTH, jsp2, 15, SpringLayout.SOUTH, slct2);
		lyt_clients.putConstraint(SpringLayout.SOUTH, jsp2, -235, SpringLayout.SOUTH, client_right_pane);
		lyt_clients.putConstraint(SpringLayout.WEST, add_item, 40, SpringLayout.WEST, jsp2);
		lyt_clients.putConstraint(SpringLayout.NORTH, add_item, 20, SpringLayout.SOUTH, jsp2);
		lyt_clients.putConstraint(SpringLayout.EAST, del_item, -40, SpringLayout.EAST, jsp2);
		lyt_clients.putConstraint(SpringLayout.NORTH, del_item, 20, SpringLayout.SOUTH, jsp2);
		lyt_clients.putConstraint(SpringLayout.WEST, order_cart, 40, SpringLayout.WEST, jsp2);
		lyt_clients.putConstraint(SpringLayout.NORTH, order_cart, 20, SpringLayout.SOUTH, add_item);
		lyt_clients.putConstraint(SpringLayout.EAST, suggest_item, -40, SpringLayout.EAST, jsp2);
		lyt_clients.putConstraint(SpringLayout.NORTH, suggest_item, 20, SpringLayout.SOUTH, add_item);
		
		
		client_left_pane.add(slct);
		client_left_pane.add(jsp1);
		client_left_pane.add(current_budget);
		client_left_pane.add(list_total);
		client_left_pane.add(get_notifications);
		client_right_pane.add(slct2);
		client_right_pane.add(jsp2);
		client_right_pane.add(list_select);
		client_right_pane.add(add_item);
		client_right_pane.add(del_item);
		client_right_pane.add(order_cart);
		client_right_pane.add(suggest_item);
		
		client_left_pane.setLayout(lyt_clients);
		client_right_pane.setLayout(lyt_clients);
        tab2.add(client_left_pane);
        tab2.add(client_right_pane);
        
        //here we do action events
        t1.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				setModelT2((String)t1.getValueAt(((JTable)arg0.getSource()).getSelectedRow(),0));
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
        });
        add_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(t1.getSelectedRow() >= 0) {
					Window.this.setVisible(false);
					new AddItem(selected_list, Window.this,( String) t1.getValueAt(t1.getSelectedRow(),0));
				}
			}
        	
        });
        del_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(t2.getSelectedRow() >= 0 && t1.getSelectedRow() >=0) {
					if (selected_list instanceof ShoppingCart)
						Event.delItem(Integer.parseInt((String)t2.getValueAt(t2.getSelectedRow(), 1)), "ShoppingCart", (String) t1.getValueAt(t1.getSelectedRow(), 0));
					else 
						Event.delItem(Integer.parseInt((String)t2.getValueAt(t2.getSelectedRow(), 1)), "WishList", (String) t1.getValueAt(t1.getSelectedRow(), 0));
				}
				init_item_list();
				reinit_labels();
			}
        });
        order_cart.setEnabled(false);
        order_cart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Customer customer = Event.getCustomer((String) t1.getValueAt(t1.getSelectedRow(), 0));
				customer.setShoppingCart(new ShoppingCart(customer.getShoppingCart().getBudget()));
				selected_list = customer.getShoppingCart();
				init_item_list();
				reinit_labels();
			}
        });
        get_notifications.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(t1.getSelectedRow() >= 0) {
					Window.this.setVisible(false);
					new ShowNotifications((String) t1.getValueAt(t1.getSelectedRow(), 0),Window.this);
				}
			}
        	
        });
        suggest_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(t1.getSelectedRow() >= 0 ) {
					Item ret = Event.getItem((String) t1.getValueAt(t1.getSelectedRow(), 0));
					if(ret != null && Event.getCustomer((String) t1.getValueAt(t1.getSelectedRow(), 0)).getWishlist().getItemWithId(ret.getId()) == null) {
						reinit_labels();
						init_item_list();
        			}
					else {
						JOptionPane.showMessageDialog(null, "Nu s-a putut adauga elementul in shopping cart", "Eroare",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
        	
        });
	}
	public void setModelT2(String customer_name) {
		Customer customer = Event.getCustomer(customer_name);
		if(((String)list_select.getSelectedItem()).equals("Shopping Cart")) {
			selected_list = customer.getShoppingCart();
			order_cart.setEnabled(true);
		}
		else {
			selected_list = customer.getWishlist();
			order_cart.setEnabled(false);
		}
		init_item_list();
		reinit_labels();
	}
	private void init_customer_list() {
		for(Customer cust : (ArrayList<Customer>) Store.getInstance().getCustomers()) {
			String[] new_row= {cust.getName()};
			cust_model.addRow(new_row);
			if(selected_customer == null)
					selected_customer = cust;
			if(selected_list == null)
				selected_list = cust.getShoppingCart();
		}
		
	}
	public void reinit_labels() {
		if(t1.getSelectedRow() >= 0) {
		Customer customer = Event.getCustomer((String) t1.getValueAt(t1.getSelectedRow(), 0));
		
		if(selected_list instanceof ShoppingCart) 
			current_budget.setText("<html><p style=\"font-size:12px\">Available Budget: " + String.format("%.2f",customer.getShoppingCart().getBudget()) +" $</p></html>");
		else
			current_budget.setText("<html><p style=\"font-size:12px\">Available Budget: $$$</p></html>");
		
		list_total.setText("<html><p style=\"font-size:12px\">List Total: " + String.format("%.2f",selected_list.getTotalPrice()) + " $</p></html>");
		}}
	public void init_item_list() {
		item_model = new DefaultTableModel();
		item_model.addColumn("Name");
		item_model.addColumn("Item_Id");
		item_model.addColumn("Dept_Id");
		item_model.addColumn("Price");
		
		ListIterator<Item> itm_it = selected_list.listIterator();
		while(itm_it.hasNext()) {
			Item itm = itm_it.next();
			String[] new_row = {itm.getName(),((Integer)itm.getId()).toString(), ((Integer)itm.getDepartment().getId()).toString(),((Double)itm.getPrice()).toString()};
			item_model.addRow(new_row);
			
		}
		t2.setModel(item_model);
	}
	private void init_table_model() {
		modelt = new DefaultTableModel();
		modelt.addColumn("Name");
		modelt.addColumn("Item_Id");
		modelt.addColumn("Department_Id");
		modelt.addColumn("Price");
	}
	public void init_table() {
		init_table_model();
		table.setModel(modelt);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		
		ArrayList<Department> dept_list = Store.getInstance().getDepartments();
		ArrayList<Item> show_list = new ArrayList<>();
		ListIterator<Department> dept_it = dept_list.listIterator();
		while(dept_it.hasNext()) 
			show_list.addAll(dept_it.next().getItems());
		show_list.sort(selected_store_comparator);
		for(int i = 0; i < show_list.size(); i++) {
			String[] new_row = {show_list.get(i).getName(), ((Integer)show_list.get(i).getId()).toString()
					, ((Integer)show_list.get(i).getDepartment().getId()).toString()
					,((Double)show_list.get(i).getPrice()).toString()};
			modelt.addRow(new_row);
		}
		table.setModel(modelt);
	}
	private void ReadFiles() {
		//here the files are read
		Scanner sc = null;
		try {
		sc = new Scanner(store_file);
		String line = sc.nextLine();
		String[] sline;
		Store.getInstance().setName(line);
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			sline = line.split(";");
			Department current = null;
			if(sline[0].equals("BookDepartment")) {
				current = new BookDepartment(sline[0],Integer.parseInt(sline[1]));
				department_names[0] = sline[0];
			}
			if(sline[0].equals("MusicDepartment")) {
				current = new MusicDepartment(sline[0],Integer.parseInt(sline[1]));
				department_names[1] = sline[0];
			}
			if(sline[0].equals("VideoDepartment")) {
				current = new VideoDepartment(sline[0],Integer.parseInt(sline[1]));
				department_names[2] = sline[0];
			}
			if(sline[0].equals("SoftwareDepartment")) {
				current = new SoftwareDepartment(sline[0],Integer.parseInt(sline[1]));
				department_names[3] = sline[0];
			}
			
			
			Store.getInstance().addDepartment(current);
			
			int number_of_items = Integer.parseInt(sc.nextLine());
			while(number_of_items > 0) {
				sline = sc.nextLine().split(";");
				new Item(sline[0],Integer.parseInt(sline[1]),Double.parseDouble(sline[2]),current);
				number_of_items--;
			}
		}
		//aici citim userii
		sc.close();
		sc = new Scanner(customers_file);
		int number_of_people = Integer.parseInt(sc.nextLine());
		
		while(number_of_people > 0) {
			sline = sc.nextLine().split(";");
			Customer new_customer = new Customer(sline[0],Double.parseDouble(sline[1]),sline[2].charAt(0));
			Store.getInstance().enter(new_customer);
			number_of_people--;
		}
		//Here we call the main menu window
		SetSuperMarketEnviroment();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		finally {
			if(sc != null)
				sc.close();
		}
	}
	private class HomeScreenMouse implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getComponent() instanceof JTextField || e.getComponent() instanceof JLabel) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(Window.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            if(e.getComponent().equals(store_field) || e.getComponent().equals(store_label))  {
		            	store_file = file;
		            	store_field.setText(store_file.getName());
		            }
		            else {
		            	customers_file = file;
		            	customers_field.setText(customers_file.getName());
		            }
		        } 
			}
			else {
				ReadFiles();
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
	}
	private class SortSelectOrder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String value = (String) ((JComboBox<String>) arg0.getSource()).getSelectedItem();
			if(value.equals("A to Z")) {
				selected_store_comparator = c1;
			}
			if(value.equals("Cheap to Expensive")) {
				selected_store_comparator = c2;
			}
			if(value.equals("Expensive to Cheap")) {
				selected_store_comparator = c3;
			}
			init_table();
		}
	}
	private class Dept_buttons implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String selected_dept=null; Department current = null;
			if(arg0.getSource().equals( bookDepartment ))
				selected_dept = department_names[0];
			if(arg0.getSource().equals( musicDepartment ))
				selected_dept = department_names[1];
			if(arg0.getSource().equals( videoDepartment ))
				selected_dept = department_names[2];
			if(arg0.getSource().equals( softwareDepartment ))
				selected_dept = department_names[3];
			ListIterator<Department> dept_it = Store.getInstance().getDepartments().listIterator();
			while(dept_it.hasNext()) {
				current = dept_it.next();
				if(current.getName().equals(selected_dept))
					break;
			}
			//create new department window
			setVisible(false);
			new DisplayDepartment(selected_dept,current, Window.this);
			
		}
    }
	public void Restore() {
		instance.setVisible(true);
	}
	private class ProductButtons implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JButton source = (JButton) arg0.getSource();
			int row = table.getSelectedRow();
			if(source.equals(add_product)) {
				Window.this.setVisible(false);
				new AddProduct(department_names, Window.this);
			}
			if(source.equals(del_product)) {
				if(row != -1) {
					Event.delProduct(Integer.parseInt((String) table.getValueAt(row, 1)));
					init_table();
				}
			}
			if(source.equals(modify_product)) {
				if(row > -1) {
					Window.this.setVisible(false);
					new ModifyProduct(Window.this,row,table);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Window main = new Window("Supermarket");
		main.LoadFiles();
	}
}
