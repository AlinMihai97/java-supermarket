package departments;

import java.util.ArrayList;
import java.util.ListIterator;

import customer.Customer;
import lists.Item;
import lists.ShoppingCart;
import notification.Notification;
import store.Store;

public abstract class Department implements Subject{

	
	String name;
	ArrayList<Customer> observers; //We implement the Observer pattern
	ArrayList<Item> available_items;
	ArrayList<Customer> bought_at_least_one;
	int Id;
	public void enter(Customer customer) {
		if(!bought_at_least_one.contains(customer))
			bought_at_least_one.add(customer);
	}
	public void exit(Customer customer) {
		if(observers.contains(customer))
			this.removeObserver(customer);
	}
	public ArrayList<Customer> getCustomers() {
		return bought_at_least_one;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return Id;
	}
	public void addItem(Item item) {
		ListIterator<Item> it = available_items.listIterator();
		while(it.hasNext())
			if(it.next().getId() == item.getId())
				return;
		available_items.add(item);
		this.notifyAllObservers(new Notification(this.getId(), item.getId(), Notification.NotificationType.ADD));
	}
	public ArrayList<Item> getItems() {
		return available_items;
	}
	@Override
	public void addObserver(Customer customer) {
		if(!observers.contains(customer)) 
			observers.add(customer);
	}
	@Override
	public void removeObserver(Customer customer) {
		observers.remove(customer);
	}
	@Override
	public void notifyAllObservers(Notification notification) {
		for(int i = observers.size()-1; i >=0; i--) {
			observers.get(i).update(notification);
			//for remove notification
			if(notification.getNotificationType() == Notification.NotificationType.REMOVE) {
				boolean ok = true;
				ListIterator<Item> it = observers.get(i).getWishlist().listIterator();
				while(it.hasNext()) {
					Item test = it.next();
					if((test).getDepartment().getId() == this.Id)
						ok = false;
				}
				if(ok) {
					removeObserver(observers.get(i));
				}
			}
		}
	}
	public abstract void accept(ShoppingCart shpcart);
	
	public Item getItem(int Id) {
		for(int i = 0; i < available_items.size(); i++)
			if(available_items.get(i).getId() == Id)
				return available_items.get(i);
		return null;
	}
	public ArrayList<Customer> getObservers() {
		return observers;
	}
	public String getMostExpensive() {
		double greatest = -1;
		Item p = null;
		for(Item x : available_items) {
			if(x.getPrice() > greatest) {
				greatest = x.getPrice();
				p = x;
			}
		}
		return p.getName();
	}
	public String getMostWished() {
		Item selected = null;
		int count;
		double greatest = 0;
		for(Item x: available_items) {
			count  =0;
			for(Customer y : (ArrayList<Customer>) Store.getInstance().getCustomers()) {
				ListIterator<Item> item_it = y.getWishlist().listIterator();
				if(item_it.hasNext()) {
					Item z = item_it.next();
					if(z.getId() == x.getId()) {
						count++;
					}
				}
			}
			if( count > greatest )  {
				greatest = count;
				selected = x;
			}
		}
		if(selected != null)
			return selected.getName();
		else 
			return "Error: No element wanted";
	}
}
