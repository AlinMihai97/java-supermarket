package lists;

import java.util.ArrayList;
import java.util.Comparator;

import strategy.Strategy;
import strategy.StrategyA;
import strategy.StrategyB;
import strategy.StrategyC;

public class WishList extends ItemList{
	ArrayList<Item> adding_order;
	Strategy str;
	public WishList(char strategyType) {
		list_comparator = new WishListComparator();
		adding_order = new ArrayList<Item>();
		switch(strategyType) {
			case 'A' :
				str = new StrategyA();
				break;
			case 'B' :
				str = new StrategyB();
				break;
			case 'C' :
				str = new StrategyC();
				break;
		}
	}
	public boolean add(Item item) {
		adding_order.add(item); 
		return super.add(item);
	}
	public boolean remove(Item item) {
		adding_order.remove(item);
		return super.remove(item);
	}
	public Item getLast() {
		if(adding_order.isEmpty())
			return null;
		else {
			return adding_order.get(adding_order.size()-1);
		}
	}
	class WishListComparator implements Comparator<Item> {
		@Override
		public int compare(Item arg0, Item arg1) {
			return arg0.getName().compareTo(arg1.getName());
		}	
	}
	public Item executeStrategy() {
		
		return str.execute(this);
	}
}
