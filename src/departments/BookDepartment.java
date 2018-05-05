package departments;

import java.util.ArrayList;

import lists.ShoppingCart;

public class BookDepartment extends Department{

	public BookDepartment(String name,int Id) {
		this.name = name;
		observers = new ArrayList<>();
		bought_at_least_one = new ArrayList<>();
		available_items = new ArrayList<>();
		this.Id = Id;
	}

	@Override
	public void accept(ShoppingCart shpcart) {
		shpcart.visit(this);
	}

}
