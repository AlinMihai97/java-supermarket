package strategy;

import lists.Item;
import lists.WishList;

public interface Strategy {

	public Item execute(WishList wishlist);
}
