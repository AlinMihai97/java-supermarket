package strategy;

import lists.Item;
import lists.WishList;

public class StrategyC implements Strategy{

	@Override
	public Item execute(WishList wishlist) {
		return wishlist.getLast();
	}

	

}
