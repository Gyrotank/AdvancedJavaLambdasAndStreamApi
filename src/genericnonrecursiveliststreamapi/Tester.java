package genericnonrecursiveliststreamapi;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Tester {
    
    public static Predicate<Integer> isOddNumber() {
        return i -> i != null && i%2 == 1;
    }
    
    //n -> number -> number != null && number%n == 0;
    public static final Function<Integer, Predicate<Integer>> isDivisibleByN = (Integer n) -> {
        Predicate<Integer> checkDivisibility = (Integer number) -> 
            number != null && number%n == 0;
        return checkDivisibility;
    };
    
    public static Optional<Integer> pickNumberDivisibleByN(MyLinkedListStreamApi<Integer> list, Integer n) {
        return list.stream().filter(isDivisibleByN.apply(n)).findFirst();
    }

    public static void main(String[] args) {
        MyLinkedListStreamApi<Integer> myList = new MyLinkedListStreamApi<>(1);
        
        myList.append(2);
        myList.append(null);
        myList.append(4);
        
        myList.prepend(10);
        myList.prepend(100);
        
        myList.insert(11, -5);
        myList.insert(22, 15);
        myList.insert(333, 2);
        
        myList.forEach(i -> System.out.print(i + "; "));
        System.out.println();
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
        
        System.out.println(myList.stream().map(i -> String.valueOf(i))
                .collect(Collectors.joining("; ")));
        System.out.println(myList.contains(50));        
        System.out.println(myList.contains(null));
        System.out.println(myList.contains(1));
        
        MyLinkedListStreamApi<Integer> myList2 = new MyLinkedListStreamApi<>(10);
        System.out.println(myList.stream().map(i -> String.valueOf(i))
                .collect(Collectors.joining("; ")));
        System.out.println(myList2.stream().map(i -> String.valueOf(i))
                .collect(Collectors.joining("; ")));
        
        /*=====================================================*/
        /*=====================================================*/
        /*=====================================================*/
        
        System.out.println("========= Stream API magic (filters, predicates, functions, "
                + "optionals, maps and reduces) =========");
        
        System.out.println(myList.stream().map(i -> String.valueOf(i))
                .collect(Collectors.joining("; ")));
        
        //String.valueOf(i + 1) -> NullPointerException
        //String.valueOf(i != null ? i + 1 : i) causes NPE due to autoboxing
        //see http://stackoverflow.com/questions/8098953/tricky-ternary-operator-in-java-autoboxing
        System.out.println(myList.stream().map(i -> String.valueOf(i) + 1)
                .collect(Collectors.joining("; ")));
        
        System.out.println(myList.stream().filter(i -> i != null).map(i -> String.valueOf(i + 1))
                .collect(Collectors.joining("; ")));
        
        //Filtering
        System.out.println("Quantity of even numbers: "
                + myList.stream().filter(i -> i != null && i%2 == 0).count());        
        System.out.println("Quantity of odd numbers: "
                + myList.stream().filter(isOddNumber()).count());        
        System.out.println("Quantity of numbers divisible by 11: "
                + myList.stream().filter(isDivisibleByN.apply(11)).count());
        
        //Optional
        System.out.println("First number divisible by 10: "
                + pickNumberDivisibleByN(myList, 10).orElse(-1));        
        System.out.println("First number divisible by 15: "
                + pickNumberDivisibleByN(myList, 15).orElse(-1));
        
        //Map-Reduce
        System.out.println("Quantity of digits in all non-null numbers: "
                + myList.stream().filter(i -> i != null).mapToInt(i -> i.toString().length()).sum());
        System.out.println("The max non-null number: " 
                + myList.stream().filter(i -> i != null)
                    .reduce(Integer.MIN_VALUE, (n1, n2) -> n1 >= n2 ? n1 : n2));
        MyLinkedListStreamApi<Integer> myList3 = new MyLinkedListStreamApi<>();
        System.out.println("The max non-null number in empty list: " 
                + myList3.stream().filter(i -> i != null)
                    .reduce(Integer.MIN_VALUE, (n1, n2) -> n1 >= n2 ? n1 : n2));
    }
}