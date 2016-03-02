package basicrecursivelist;

public class Tester {

	public static void main(String[] args) {
		Element myList = new Element(1);
		
		myList.append(2);
		myList.append(new Element());
		myList.append(new Element(4));
		
		myList = myList.prepend(10);
		myList = myList.prepend(new Element(100));
		
		myList = myList.insert(11, -5);
		myList = myList.insert(22, 15);
		myList = myList.insert(new Element(33), 2);
		
		System.out.println(myList);
		System.out.println(myList.contains(50));		
		System.out.println(myList.contains(new Element()));
		System.out.println(myList.contains(1));
		
		myList = myList.remove(-1);
		myList = myList.remove(15);
		myList = myList.remove(4);
		
		System.out.println(myList);
		System.out.println(myList.contains(50));		
		System.out.println(myList.contains(new Element()));
		System.out.println(myList.contains(1));
	}
}