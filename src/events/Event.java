package events;

import java.util.ArrayList;
import java.util.ListIterator;

import customer.Customer;
import lists.Item;
import lists.ItemList;
import lists.ShoppingCart;
import lists.WishList;
import notification.Notification;
import store.Store;
import departments.Department;
public class Event {

	//this class implements Event types posibile in store interaction
	public static void addItem(int ItemId,String list_type,String customer_name) {
		ItemList list;
		Customer customer = getCustomer(customer_name);
		if(list_type.equals("ShoppingCart")) 
			list = customer.getShoppingCart();
		else
			list = customer.getWishlist();
		
		ListIterator<Department> dept_it = Store.getInstance().getDepartments().listIterator();
		while (dept_it.hasNext())
		{
			Department current_department = dept_it.next();
			Item current_item = current_department.getItem(ItemId);
			if(current_item != null) {
				if (list.add( new Item(current_item))) {
					if( list instanceof WishList) { 
						current_department.addObserver(customer);
					}
					else {
						current_department.enter(customer);
					}
				}
				return;
			}
		}
	}
	public static void delItem(int ItemId,String list_type,String customer_name) {
		ItemList list;
		Customer customer = getCustomer(customer_name);
		if(list_type.equals("ShoppingCart"))
			list = customer.getShoppingCart();
		else
			list = customer.getWishlist();
		Item item = list.getItemWithId(ItemId);
		if (item != null) {
			list.remove(item);
			if(list instanceof WishList) {
				boolean ok = true;
				ListIterator<Item> it = list.listIterator();
				while(it.hasNext()) {
					if(it.next().getDepartment().getId() == item.getDepartment().getId())
						ok = false;
				}
				if(ok)
					item.getDepartment().removeObserver(customer);
			}
		}
	}
	public static void addProduct(int DeptId, int ItemId, double Price, String name) {
		new Item(name,ItemId,Price,Store.getInstance().getDepartment(DeptId));
	}
	public static void modifyProduct(int DeptId, int ItemId, double Price) {
		Department dept = Store.getInstance().getDepartment(DeptId);
		Item item = dept.getItem(ItemId);
		item.setPrice(Price);
		dept.notifyAllObservers(new Notification(DeptId, ItemId, Notification.NotificationType.MODIFY));
	}
	public static void delProduct(int ItemId) {
		
		ListIterator<Department> dept_it = Store.getInstance().getDepartments().listIterator();
		while (dept_it.hasNext())
		{
			Department current_dept = dept_it.next();
			Item searched_item = current_dept.getItem(ItemId);
			if(searched_item != null) {
				current_dept.notifyAllObservers(new Notification(current_dept.getId(), ItemId, Notification.NotificationType.REMOVE));
				current_dept.getItems().remove(searched_item);
				return;
			}
		}
	}
	public static Item getItem(String customer_name) {
		Customer customer = getCustomer(customer_name);
		Item ret_item = customer.getWishlist().executeStrategy();
		if(ret_item != null) {
			addItem(ret_item.getId(),"ShoppingCart",customer_name);
			ret_item.getDepartment().enter(customer);
			if(customer.getShoppingCart().getItemWithId(ret_item.getId()) != null) {
				delItem(ret_item.getId(),"WishList",customer.getName());
			}
		}
		return ret_item;
	}
	public static ItemList getItems(String list_type, String customer_name) {
		Customer customer = getCustomer(customer_name);
		if(list_type.equals("ShoppingCart"))
			return customer.getShoppingCart();
		else
			return customer.getWishlist();
	}
	public static double getTotal(String list_type, String customer_name) {
		Customer customer = getCustomer(customer_name);
		if(list_type.equals("ShoppingCart"))
			return customer.getShoppingCart().getTotalPrice();
		else
			return customer.getWishlist().getTotalPrice();
	}
	public static void accept(int deptId, String customer_name) {
		Customer customer = getCustomer(customer_name);
		Store.getInstance().getDepartment(deptId).accept(customer.getShoppingCart());
		//create shopping cart copy so that accepted elements are still in sorted order
		ShoppingCart new_cart = new ShoppingCart(Double.MAX_VALUE);
		ListIterator<Item> it = customer.getShoppingCart().listIterator();
		while(it.hasNext()) {
			new_cart.add(new Item( it.next()));
		}
		new_cart.setBudget(customer.getShoppingCart().getBudget());
		customer.setShoppingCart(new_cart);
	}
	public static ArrayList<Customer> getObservers(int deptId) {
		ArrayList<Department> dept_list = Store.getInstance().getDepartments();
		for(int i = 0; i < dept_list.size(); i++)
			if(dept_list.get(i).getId() == deptId)
				return dept_list.get(i).getObservers();
		return null;
	}
	public static ArrayList<Notification> getNotifications(String customer_name) {
		Customer customer = getCustomer(customer_name);
		return customer.getNotification_list();
	}
	public static Customer getCustomer(String name) {
		ArrayList<Customer> customers_list = Store.getInstance().getCustomers();
		for(int i = 0; i < customers_list.size(); i++)
			if(name.equals(customers_list.get(i).getName()))
				return customers_list.get(i);
		return null;
	}
}
