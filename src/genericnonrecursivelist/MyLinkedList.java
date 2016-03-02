package genericnonrecursivelist;

import java.util.NoSuchElementException;

public class MyLinkedList<T> {
	
	private static class Element<Q> {		
		private Q data;	
		private Element<Q> next;
		
		public Element(Q data) {
			this.data = data;
		}
		
		public boolean equals(Element<Q> el) {
			if (el == null) {
				return false;
			}
			
			if ((el.data == null) && (this.data == null)) {
				return true;
			}
			
			if ((el.data == null) || (this.data == null)) {
				return false;
			}
			
			return (el.data.equals(this.data));
		}
	}
	
	private Element<T> head;
	
	private int size;
	
	public MyLinkedList() {
		size = 0;
	}
	
	public MyLinkedList(T data) {
		this(new Element<>(data));
	}
	
	private MyLinkedList(Element<T> head) {
		this.head = head;
		size = 1;
	}
	
	public MyLinkedList(MyLinkedList<T> list) {
		this.head = list.head;
		this.size = list.size;
	}
	
	public int getSize() {
		return size;
	}
	
	public void prepend(T data) {
		prepend(new Element<>(data));
	}
	
	private void prepend(Element<T> el) {
		el.next = head;
		head = el;
		size++;
	}
	
	public void append(T data) {
		append(new Element<>(data));
	}
	
	private void append(Element<T> el) {
		if (size == 0) {
			prepend(el);
		} else {		
			Element<T> p = head;

			while (p.next != null) {
				p = p.next;
			}

			p.next = el;		
			size++;
		}
	}	
	
	public void insert(T data, int position) {
		insert(new Element<>(data), position);
	}
	
	private void insert(Element<T> el, int position) {
		if (size == 0 || position <= 0) {
			prepend(el);
		} else if (position >= size) {
			append(el);
		} else {		
			Element<T> p = head;		
			for (int i = 1; i < position; i++, p = p.next);

			el.next = p.next;
			p.next = el;
			size++;
		}
	}
	
	public void remove(int position) {
		if (size == 0 || position < 0 || position >= size)
			throw new NoSuchElementException();
		
		if (position == 0) {
			head = head.next;
			size--;
		} else {		
			Element<T> p = head;		
			for (int i = 1; i < position - 1 && p.next != null; i++, p = p.next);
			
			Element<T> q = p.next.next;
			p.next.next = null;
			p.next = q;
			size--;						
		}	
	}	
	
	public boolean contains(T data) {
		return contains(new Element<>(data));
	}
	
	private boolean contains(Element<T> el) {
		if (size == 0) {
			return false;
		}
		
		Element<T> p = head;
		
		while (p.next != null) {
			if (p.equals(el)) {
				return true;
			}
			
			p = p.next;
		}
		
		return p.equals(el);
	}
	
	public String toString() {
		if (size == 0) {
			return "List is empty";
		}
		
		StringBuilder res = new StringBuilder();
		
		Element<T> p = head;
		res.append(p.data);
		
		while (p.next != null) {
			res.append("; ");
			p = p.next;
			res.append(p.data);
		}
		
		return res.toString();
	}
}
