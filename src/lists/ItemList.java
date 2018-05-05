package lists;

import java.util.Collection;
import java.util.Comparator;
import java.util.ListIterator;

public abstract class ItemList {

	Comparator<Item> list_comparator;
	int size = 0;
	Node<Item> first = null, last = null;
	private static class Node<T> {
		T value;
		Node<T> next, previous;
		Node(T value) {
			this.value = value;
			this.next = this.previous = null;
		}
	}
	public boolean isEmpty(){
		return (size == 0);
	}
	public boolean add(Item element) {
		int compare_result;
		if( first == null && last ==null)
		{
			first = last = new Node<>(element);
			size = 1;
			return true;
		}
		else {
			
			Node<Item> p = first;
			while(p != null)
			{
				compare_result = list_comparator.compare(element, p.value);
				if(compare_result == 0)
					return false; //Item with same name and same price twice
				if(compare_result <= 0 )
				{
					Node<Item> new_node = new Node<>(element);
					new_node.next = p;
					new_node.previous = p.previous;
					if(p.previous != null) {
						p.previous.next = new_node; 
					}
					else {
						first = new_node;
					}
					p.previous = new_node;
					size++;
					
					
					return true;
				}
				else {
					p = p.next;
				}
			}
			//it reaches this point only if the added item should be last in the sorted order 
			Node<Item> new_final = new Node<>(element);
			new_final.next = null;
			last.next = new_final;
			new_final.previous = last;
			last = new_final;
			size++;
			return true;
		}
	}
	public boolean addAll(Collection<? extends Item> c) {
		if (c.isEmpty())
			return false;
		boolean ret_value = true;
		for(Item x : c){
			if (this.add(x) == false)
				ret_value = false;
		}
		return ret_value;
	}
	public Node<Item> getNode(int index) {
		if(index > size || index <= 0) {
			
			return null;
		}
		else {
			Node<Item> ret_val = first;
			if (index == 1) 
				return first;
			while (index > 1){
				ret_val =ret_val.next;
				index--;
			}
			return ret_val;
		}
	}
	public Item getItem(int index) {
		Node<Item> ret_val = this.getNode(index);
		if (ret_val != null) {
			
			return ret_val.value; }
		return null;
	}
	public int indexOf(Item item) {
		if (this.isEmpty() )
			return -1;
		int index = 1;
		Node<Item> p = first;
		while( p!=null && p.value.getId() != (item.getId())) {
			index++;
			p = p.next;
		}
		if(p!=null)
			return index;
		else 
			return -1;
	}
	public int indexOf(Node<Item> node) {
		return this.indexOf(node.value); //De schimbat aici daca se poate adauga acelasi element de mai multe ori
	}
	public boolean contains(Node<Item> node) {
		if(this.indexOf(node) != -1)
			return true;
		return false;
	}
	public boolean contains(Item item) {
		if(this.indexOf(item) != -1) 
			return true;
		return false;
	}
	public boolean remove(Item item) {
		if( this.isEmpty()) 
			return false;
		int location = this.indexOf(item);
		if(location == -1)
		{
			return false;
		}
		else {
			Node<Item> find_node = this.getNode(location);
			if(size == 1) {
				last = first = null;
				size = 0;
				return true;
			}
			if (location == 1) {
				first = find_node.next;
				first.previous = null;
				size--;
				return true;
			}
			if(location == size)
			{
				last = find_node.previous;
				last.next = null;
				size--;
				return true;
			}
			//this case if the element is somewhere in the middle
			find_node.previous.next = find_node.next;
			find_node.next.previous = find_node.previous;
			size--;
			return true;
		}
	}
	public boolean removeAll(Collection <? extends Item> c) {
		if(c.isEmpty())
			return false;
		boolean ret_value = true;
		for(Item x: c) {
			if(this.remove(x) == false)
				ret_value = false;
		}
		return ret_value;
	}
	public ListIterator<Item> listInterator(int index) {
		return new ItemIterator(index);
	}
	public ListIterator<Item> listIterator() {
		return new ItemIterator(0);
	}
	class ItemIterator implements ListIterator<Item> {
		int cursor; 

		Item last_ret_item = null;
		ItemIterator(int index) 
		{
			cursor = index;
		}
		@Override
		public boolean hasNext() {
			return cursor < ItemList.this.size;
		}

		@Override
		public boolean hasPrevious() {
			return cursor >= 1;
		}

		@Override
		public int nextIndex() {
			if(cursor == ItemList.this.size)
				return size;
			else return cursor +1;
		}
		@Override
		public int previousIndex() {
			if(cursor < 1)
				return -1;
			return cursor;
		}

		@Override
		public void remove() {
			if(last_ret_item != null) {
				ItemList.this.remove(last_ret_item);
				last_ret_item = null;
			}
		}
	
		@Override
		public void add(Item arg0) {
			if(this.hasNext()) {
				Item next_item = this.next();
				cursor--;
				ItemList.this.add(arg0);
				if (this.next() != next_item) {
					cursor++;
				}
			}
			else {
				ItemList.this.add((Item)arg0);
				cursor++;
			}
			
		}
		@Override
		public Item next() {
			cursor++;
			last_ret_item = ItemList.this.getItem(cursor);
			return last_ret_item;
		}
		@Override
		public Item previous() {
			cursor--;
			last_ret_item = ItemList.this.getItem(cursor+1);
			return last_ret_item;
		}
		@Override
		public void set(Item arg0) {
			if(last_ret_item != null) {
				last_ret_item.setDepartment(arg0.getDepartment());
				last_ret_item.setName(arg0.getName());
				last_ret_item.setPrice(arg0.getPrice());
				last_ret_item.setId(arg0.getId());
			}
		}
	}
	public String toString() {
		ListIterator<Item> it=  this.listIterator();
		String ret_string = "[";
		while(it.hasNext()) {
			Item x =it.next();
			ret_string += x.toString() + ", ";
		}
		if(ret_string.length() == 1) 
			return "[]";
		return ret_string.substring(0, ret_string.length()-2)+"]";
	}
	public Item getItemWithId(int Id) {
		ListIterator<Item> item_it = this.listIterator();
		while (item_it.hasNext()) {
			Item ret_value =item_it.next();
			if(Id == ret_value.getId())
				return ret_value;
		}
		return null;
	}
	public Double getTotalPrice() {
		ListIterator<Item> it = this.listIterator();
		double ret_value = 0;
		while(it.hasNext()) 
			ret_value +=  it.next().getPrice();
		return ret_value;
	}
}

