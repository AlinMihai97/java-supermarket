package lists;

import java.util.Comparator;
import java.util.ListIterator;

import departments.BookDepartment;
import departments.MusicDepartment;
import departments.SoftwareDepartment;
import departments.VideoDepartment;

public class ShoppingCart extends ItemList implements Visitor{

	double budget;
	double initial_budget;
	public ShoppingCart(double budget) {
		initial_budget = this.budget = budget;
		list_comparator = new ShoppingCartComparator();
	}
	public boolean add(Item item) {
		if( budget - item.getPrice() >= 0) {
			boolean ret_value = super.add(item);
			if (ret_value == true) {
				budget -= item.getPrice();
			}
			return ret_value;
		}
		else {
			return false;
		}
	}
	public boolean remove(Item item) {
		boolean ret_value = super.remove(item);
		if(ret_value)
			budget += item.getPrice();
		return ret_value;
	}
	class ShoppingCartComparator implements Comparator<Item> {
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
		
	}
	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}
	@Override
	public void visit(BookDepartment bookDepartment) {
		ListIterator<Item> it = this.listIterator();
		while(it.hasNext()) {
			Item next_item = it.next();
			if(next_item.getDepartment().getId() == bookDepartment.getId()) {
				budget += 0.1 * next_item.getPrice();
				next_item.setPrice(next_item.getPrice()*0.9);
			}
		}
	}
	@Override
	public void visit(MusicDepartment musicDepartment) {
		ListIterator<Item> it = this.listIterator();
		double sum = 0;
		while(it.hasNext()) {
			Item next_item = it.next();
			if(next_item.getDepartment().getId() == musicDepartment.getId()) {
				sum+=next_item.getPrice();
			}
		}
		budget += 0.1*sum;
	}
	@Override
	public void visit(SoftwareDepartment softwareDepartment) {
		double cheapest = -1;
		ListIterator<Item> item_it = softwareDepartment.getItems().listIterator();
		if(item_it.hasNext())
			cheapest = item_it.next().getPrice();
		while(item_it.hasNext()) {
			Item nxt = item_it.next();
			if(nxt.getPrice() < cheapest)
				cheapest = nxt.getPrice();
		}
		if (budget >= cheapest) 
			return;
		ListIterator<Item> it = this.listIterator();
		while(it.hasNext()) {
			Item next_item = it.next();
			if(next_item.getDepartment().getId() == softwareDepartment.getId()) {
				budget += 0.2 * next_item.getPrice();
				next_item.setPrice(next_item.getPrice()*0.8);
			}
		}
	}
	@Override
	public void visit(VideoDepartment videoDepartment) {
		double product_total = 0;
		double most_expensive = -1;
		ListIterator<Item> item_it = videoDepartment.getItems().listIterator();
		if(item_it.hasNext())
			most_expensive = item_it.next().getPrice();
		while(item_it.hasNext()) {
			Item nxt = item_it.next();
			if(nxt.getPrice() > most_expensive)
				most_expensive = nxt.getPrice();
		}
		ListIterator<Item> it = this.listIterator();
		while(it.hasNext()) {
			Item next_item = (Item) it.next();
			if(next_item.getDepartment().getId() == videoDepartment.getId()) {
				product_total+=next_item.getPrice();
			}
		}
		if(product_total > most_expensive) {
			it = this.listIterator();
			while(it.hasNext()) {
				Item next_item = it.next();
				if(next_item.getDepartment().getId() == videoDepartment.getId()) {
					budget += 0.15 * next_item.getPrice();
					next_item.setPrice(next_item.getPrice()*0.85);
				}
			}
		}
		budget += 0.05 * product_total;
	}
}
