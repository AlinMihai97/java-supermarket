package customer;

import java.util.ArrayList;
import departments.Department;
import lists.Item;
import lists.ShoppingCart;
import lists.WishList;
import notification.Notification;
import store.Store;

public class Customer implements Observer{

	//Data
	String name;
	char strategyType;
	ShoppingCart shpcart;
	WishList wishlist;
	ArrayList<Notification> notification_list;
	
	//Constructor
	public Customer(String name, double budget, char strategyType) {
		this.name = name;
		shpcart = Store.getShoppingCart(budget);
		wishlist = new WishList(strategyType);
		notification_list = new ArrayList<>();
	}
	//Getters
	public WishList getWishlist() {
		return wishlist;
	}
	public ArrayList<Notification> getNotification_list() {
		return notification_list;
	}
	public String getName() {
		return name;
	}
	public ShoppingCart getShoppingCart() {
		return shpcart;
	}
	
	//Update Oveservers
	@Override
	public void update(Notification notification) {
		notification_list.add(notification);
		if(notification.getNotificationType() == Notification.NotificationType.REMOVE) {
			Item itm_in_list = shpcart.getItemWithId(notification.getItem_id());
			if(itm_in_list != null)
				shpcart.remove(itm_in_list);
			itm_in_list = wishlist.getItemWithId(notification.getItem_id());
			if(itm_in_list != null)
				wishlist.remove(itm_in_list);
		}
		if(notification.getNotificationType() == Notification.NotificationType.MODIFY) {
			Department dpt = Store.getInstance().getDepartment(notification.getDepartment_id());
			Item modified_itm = dpt.getItem(notification.getItem_id());
			Item old_item = shpcart.getItemWithId(notification.getItem_id());
			
			if(old_item != null) {
				if (shpcart.getBudget() +old_item.getPrice() - modified_itm.getPrice() >= 0) {
					shpcart.remove(old_item);
					shpcart.add(new Item(modified_itm));
				}
				else {
					shpcart.remove(old_item);
				}
			}
			//Test if the modified item is in the wishlist
			old_item = wishlist.getItemWithId(notification.getItem_id());
			if(old_item != null) {
				wishlist.remove(old_item);
				wishlist.add(new Item(modified_itm));
			}
		}
	}
	public void setShoppingCart(ShoppingCart shp) {
		this.shpcart = shp;
	}
}
