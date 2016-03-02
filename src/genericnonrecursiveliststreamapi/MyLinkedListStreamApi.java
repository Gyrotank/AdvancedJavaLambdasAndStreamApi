package genericnonrecursiveliststreamapi;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MyLinkedListStreamApi<T> implements Iterable<T> {
	
	private int size;
	
	private Element<T> head;
	
	private static class Element<T> {		
		private T data;	
		private Element<T> next;
		
		public Element(T data) {
			this.data = data;
		}
		
		public boolean equals(Element<T> el) {
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
		
		public String toString() {
			return data.toString();
		}		
	}
	
	public Stream<T> stream() {
		return StreamSupport.stream(spliterator(), false);
	}
	
	public Spliterator<T> spliterator() {
		return new MLLSASpliterator<>(head, 0, size);
	}
	
	static class MLLSASpliterator<T> implements Spliterator<T> {		
		private Element<T> listPointer; // current element, advanced on split or traversal
		private int currIndex; // 0-based index of element pointed at by listPointer;
		private final int fence; // size of the list		

		MLLSASpliterator(Element<T> listPointer, int currIndex, int fence) {
			this.listPointer = listPointer;			
			this.currIndex = currIndex;
			this.fence = fence;
		}

		public void forEachRemaining(Consumer<? super T> action) {
			while (currIndex < fence) {
				action.accept(listPointer.data);
				listPointer = listPointer.next;
				currIndex++;
			}
		}

		public boolean tryAdvance(Consumer<? super T> action) {
			if (currIndex < fence) {
				action.accept(listPointer.data);
				listPointer = listPointer.next;
				currIndex++;
				return true;
			} else { 
				return false;
			}
		}

		public Spliterator<T> trySplit() {			
			Element<T> lop = listPointer;
			int lo = currIndex;
			int mid = ((lo + fence) >>> 1) & ~1; // force midpoint to be even
			if (lo < mid) {
				while (currIndex < mid) {
					listPointer = listPointer.next;
					currIndex++; // move this Spliterator's origin to the middle
				}
				return new MLLSASpliterator<>(lop, lo, mid);
			} else {       // too small to split
				return null;
			}
		}

		public long estimateSize() {
			return (long)(fence - currIndex);
		}

		public int characteristics() {
			return ORDERED | SIZED | SUBSIZED;
		}
	}	
	
	public MyLinkedListStreamApi() {
		size = 0;
	}
	
	public MyLinkedListStreamApi(T data) {
		this(new Element<>(data));
	}
	
	private MyLinkedListStreamApi(Element<T> head) {
		this.head = head;
		size = 1;
	}
	
	public MyLinkedListStreamApi(MyLinkedListStreamApi<T> list) {
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
		} else {		
			Element<T> p = head;		
			for (int i = 1; i < position - 1 && p.next != null; i++, p = p.next);
			
			Element<T> q = p.next.next;
			p.next.next = null;
			p.next = q;				
		}		
		size--;
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
	
	//toString using internal iterator with method reference
	//not good since we can't pass arguments and add dividers
	//have to use streaming and joining...
//	public String toString() {
//		if (size == 0) {
//			return "List is empty";
//		}
//
//		StringBuilder res = new StringBuilder();
//
//		this.forEach(res::append);
//
//		return res.toString();
//	}
	
	//toString using streaming, collecting and joining 
	public String toString() {
		if (size == 0) {
			return "List is empty";
		}

		StringBuilder res = new StringBuilder();

		res.append(this.stream().map(i -> String.valueOf(i))
				.collect(Collectors.joining("; ")));

		return res.toString();
	}
	
	@Override
	public Iterator<T> iterator() {		
		return new Iterator<T>() {
			
			private Element<T> p = head;

			@Override
			public boolean hasNext() {
				return (p != null);
			}

			@Override
			public T next() {
				T res = p.data;
				p = p.next;
				return res;
			}			
		};
	}
	
	@Override
	public void forEach(Consumer<? super T> action) {
		for (T t : this)
	         action.accept(t);
	}	
}
