package store;
import java.util.ArrayList;

import customer.Customer;
import departments.Department;
import lists.ShoppingCart;

public class Store {

	//We are using Singleton pattern
	static Store instance = null;
	String name;
	ArrayList<Department> StoreDepartments;
	ArrayList<Customer> StoreClients;
	private Store() {
		instance = this;
		StoreDepartments = new ArrayList<Department>();
		StoreClients = new ArrayList<Customer>();
		name = "DefaultStoreNamePleaseChange";
	}
	public static Store getInstance() {
		if (instance != null )
			return instance;
		else
			return new Store();
	}
	public void enter(Customer x) {
		StoreClients.add(x);
	}
	public void exit(Customer x) {
		StoreClients.remove(x);
	}
	public static ShoppingCart getShoppingCart(Double budget) {
		return new ShoppingCart(budget);
	}
	public ArrayList<Customer> getCustomers() {
		return StoreClients;
	}
	public ArrayList<Department> getDepartments() {
		return StoreDepartments;
	}
	public Department getDepartment(int Id) {
		for(int i = 0; i < StoreDepartments.size(); i++)
			if(StoreDepartments.get(i).getId() == Id)
				return StoreDepartments.get(i);
		return null;
	}
	public void addDepartment(Department new_department) {
		StoreDepartments.add(new_department);
	}
	public void setName(String name) {
		this.name =  name;
	}
}
