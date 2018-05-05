package events;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

import customer.Customer;
import departments.BookDepartment;
import departments.Department;
import departments.MusicDepartment;
import departments.SoftwareDepartment;
import departments.VideoDepartment;
import lists.Item;
import notification.Notification;
import store.Store;

public class Test {

	public static void main(String[] args) {
		//This class is used for checker testing
		Scanner sc;
		File store = new File("store.txt");
		File customers = new File("customers.txt");
		File events = new File("events.txt");
		
		try {
			//aici citim magazinul
			sc = new Scanner(store);
			String line = sc.nextLine();
			String[] sline;
			Store.getInstance().setName(line);
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				sline = line.split(";");
				Department current = null;
				if(sline[0].equals("BookDepartment")) 
					current = new BookDepartment(sline[0],Integer.parseInt(sline[1]));
				if(sline[0].equals("MusicDepartment")) 
					current = new MusicDepartment(sline[0],Integer.parseInt(sline[1]));
				if(sline[0].equals("VideoDepartment")) 
					current = new VideoDepartment(sline[0],Integer.parseInt(sline[1]));
				if(sline[0].equals("SoftwareDepartment")) 
					current = new SoftwareDepartment(sline[0],Integer.parseInt(sline[1]));
				
				Store.getInstance().addDepartment(current);
				
				int number_of_items = Integer.parseInt(sc.nextLine());
				while(number_of_items > 0) {
					sline = sc.nextLine().split(";");
					new Item(sline[0],Integer.parseInt(sline[1]),Double.parseDouble(sline[2]),current);
					number_of_items--;
				}
			}
			//aici citim userii
			sc = new Scanner(customers);
			int number_of_people = Integer.parseInt(sc.nextLine());
			
			while(number_of_people > 0) {
				sline = sc.nextLine().split(";");
				Customer new_customer = new Customer(sline[0],Double.parseDouble(sline[1]),sline[2].charAt(0));
				Store.getInstance().enter(new_customer);
				number_of_people--;
			}
			//acum citim evenimente si incercam rularea doamne ajuta
			sc = new Scanner(events);
			int number_of_events = Integer.parseInt(sc.nextLine());
			while(number_of_events > 0) {
				sline = sc.nextLine().split(";");
				if(sline[0].equals("addItem")) {
					Event.addItem(Integer.parseInt(sline[1]), sline[2], sline[3]);
				}
				if(sline[0].equals("delItem")) {
					Event.delItem(Integer.parseInt(sline[1]), sline[2], sline[3]);
				}
				if(sline[0].equals("addProduct")) {
					Event.addProduct(Integer.parseInt(sline[1]), Integer.parseInt(sline[2]), Double.parseDouble(sline[3]),sline[4]);
				}
				if(sline[0].equals("modifyProduct")) {
					Event.modifyProduct(Integer.parseInt(sline[1]), Integer.parseInt(sline[2]), Double.parseDouble(sline[3]));
				}
				if(sline[0].equals("delProduct")) {
					Event.delProduct(Integer.parseInt(sline[1]));
				}
				if(sline[0].equals("getItem")) {
					Item ret_val = Event.getItem(sline[1]);
					if(ret_val != null)
						System.out.println(ret_val);
				}
				if(sline[0].equals("getItems")) {
					System.out.println(Event.getItems(sline[1], sline[2]));
				}
				if(sline[0].equals("getTotal")) {
					System.out.println(String.format("%.2f",Event.getTotal(sline[1], sline[2])));
				}
				if(sline[0].equals("accept")) {
					Event.accept(Integer.parseInt(sline[1]), sline[2]);
				}
				if(sline[0].equals("getObservers")) {
					ArrayList<Customer> obs_list = Event.getObservers(Integer.parseInt(sline[1]));
					if(obs_list.isEmpty())
						System.out.println("[]");
					else {
						ListIterator<Customer> obs_it = obs_list.listIterator();
						String print_str = "[";
						while(obs_it.hasNext()) {
							print_str += obs_it.next().getName();
							print_str += ", ";
						}
						System.out.println(print_str.substring(0, print_str.length()-2)+"]");
				
					}
				}
				if(sline[0].equals("getNotifications")) {
					ArrayList<Notification> not_list = Event.getNotifications(sline[1]);
					if(not_list.isEmpty())
						System.out.println("[]");
					else {
						ListIterator<Notification> not_it = not_list.listIterator();
						String print_str = "[";
						while(not_it.hasNext()) {
							Notification x =not_it.next();
							print_str += x.getNotificationType() + ";" + x.getItem_id() + ";" + x.getDepartment_id() + ", ";
						}
						System.out.println(print_str.substring(0, print_str.length()-2)+"]");
				
					}
				}
				number_of_events--;
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
