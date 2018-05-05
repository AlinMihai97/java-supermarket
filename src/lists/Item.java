package lists;

import departments.Department;

public class Item {

	private int Id;
	private double price;
	private String name;
	Department department;
	public Item(String name, int Id, double price, Department department) {
		this.name = name;
		this.Id = Id;
		this.price = price;
		this.department = department;
		department.addItem(this);
	}
	public Item(Item x) {
		this.name = x.getName();
		this.price = x.getPrice();
		this.department = x.getDepartment();
		this.Id = x.getId();
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String toString() {
		return "" + name +";" + Id + ";" + String.format("%.2f", price);
	}
}
