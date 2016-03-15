package genericnonrecursivelistlambda;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class MyLinkedListLambda<T> implements Iterable<T> {
    
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
        
        public String toString() {
            return data.toString();
        }        
    }
    
    private Element<T> head;
    
    private int size;
    
    public MyLinkedListLambda() {
        size = 0;
    }
    
    public MyLinkedListLambda(T data) {
        this(new Element<>(data));
    }
    
    private MyLinkedListLambda(Element<T> head) {
        this.head = head;
        size = 1;
    }
    
    public MyLinkedListLambda(MyLinkedListLambda<T> list) {
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
    
    //toString() using explicit external iterator
//    public String toString() {
//        if (size == 0) {
//            return "List is empty";
//        }
//        
//        Iterator<T> outputIterator = iterator();
//        
//        StringBuilder res = new StringBuilder();        
//        
//        while (outputIterator.hasNext()) {
//            res.append(outputIterator.next());
//            res.append("; ");
//        }
//        
//        return res.toString();
//    }
    
    //toString() using implicit external iterator
//    public String toString() {
//        if (size == 0) {
//            return "List is empty";
//        }
//        
//        StringBuilder res = new StringBuilder();
//        for (T data : this) {
//            res.append(data + "; ");
//        }
//        return res.toString();
//    }
    
    //toString using internal iterator
//    public String toString() {
//        if (size == 0) {
//            return "List is empty";
//        }
//        
//        StringBuilder res = new StringBuilder();
//        
//        this.forEach(new Consumer<T>() {
//            public void accept(final T data) {
//                res.append(data + "; ");
//            }
//        });
//        return res.toString();
//    }
    
    //toString using internal iterator with lambda expression
    public String toString() {
        if (size == 0) {
            return "List is empty";
        }

        StringBuilder res = new StringBuilder();

        this.forEach(data ->
            res.append(data + "; "));
        
        return res.toString();
    }
    
    //toString using internal iterator with method reference
    //not good since we can't pass arguments and add dividers
    //have to use streaming and joining, more on this some other time...
//    public String toString() {
//        if (size == 0) {
//            return "List is empty";
//        }
//
//        StringBuilder res = new StringBuilder();
//
//        this.forEach(res::append);
//
//        return res.toString();
//    }    
    
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
