package genericnonrecursivelist;

import java.util.NoSuchElementException;

public class Tester {

	public static void main(String[] args) {
		MyLinkedList<Integer> myList = new MyLinkedList<>(1);
		
		myList.append(2);
		myList.append(null);
		myList.append(4);
		
		myList.prepend(10);
		myList.prepend(100);
		
		myList.insert(11, -5);
		myList.insert(22, 15);
		myList.insert(33, 2);
		
		System.out.println(myList);
		System.out.println(myList.contains(50));		
		System.out.println(myList.contains(null));
		System.out.println(myList.contains(1));
		
		try {
			myList.remove(-1);
		} catch (NoSuchElementException e) {
			System.out.println("Can't remove element from position -1");
		}
		try {
			myList.remove(15);
		} catch (NoSuchElementException e) {
			System.out.println("Can't remove element from position 15");
		}
		try {
			myList.remove(5);
		} catch (NoSuchElementException e) {
			System.out.println("Can't remove element from position 5");
		}
		
		System.out.println(myList);
		System.out.println(myList.contains(50));		
		System.out.println(myList.contains(null));
		System.out.println(myList.contains(1));
		
		MyLinkedList<Integer> myList2 = new MyLinkedList<>(10);
		System.out.println(myList);
		System.out.println(myList2);
	}
}