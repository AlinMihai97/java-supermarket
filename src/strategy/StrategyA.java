package strategy;

import java.util.ListIterator;

import lists.Item;
import lists.WishList;

public class StrategyA implements Strategy{
	@Override
	public Item execute(WishList wishlist) {
		if(wishlist.isEmpty())
			return null;
		else {
			Item cheapest = null;
			ListIterator<Item> item_it = wishlist.listIterator();
			if(item_it.hasNext())
				cheapest = item_it.next();
			while(item_it.hasNext()) {
				Item nxt = item_it.next();
				if(nxt.getPrice() < cheapest.getPrice())
					cheapest = nxt;
			}
			return cheapest;
		}
		
	}
}
