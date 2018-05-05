package strategy;

import lists.Item;
import lists.WishList;

public class StrategyB implements Strategy{

	@Override
	public Item execute(WishList wishlist) {
		if(wishlist.isEmpty()) 
			return null;
		else {
			return wishlist.getItem(1);
		}
	}

	

}
