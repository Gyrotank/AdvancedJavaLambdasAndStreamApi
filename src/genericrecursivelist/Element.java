package genericrecursivelist;

public class Element<T> {    
    private T data;    
    private Element<T> next;
    
    public Element() {}
    
    public Element(T data) {
        this.data = data;
    }
    
    //Adds element to the end of the list
    public void append(Element<T> el) {
        Element<T> p = this;
        
        while (p.next != null) {
            p = p.next;
        }
        
        p.next = el;
    }
    
    public void append(T data) {
        append(new Element<>(data));
    }
    
    //Adds element to the beginning of the list;
    //since 'this' is final, we can't write this = el;
    //so we have to return el, and in order for the method to have a desired effect, we
    //must use it like this: myList = myList.prepend(newElement);
    public Element<T> prepend(Element<T> el) {
        el.next = this;
        return el;
    }
    
    public Element<T> prepend(T data) {
        return prepend(new Element<>(data));
    }    
    
    //Inserts element at the specified position;
    //since we can add element to the head of the list, 
    //and we can't write this = el; because 'this' is final,
    //we have to return el, and in order for the method to have a desired effect, we
    //must use it like this: myList = myList.insert(newElement, targetPosition);
    public Element<T> insert(Element<T> el, int position) {
        if (position <= 0) {
            return prepend(el);
        }
        
        Element<T> p = this;        
        for (int i = 1; i < position && p.next != null; i++, p = p.next);
        
        if (p.next != null) {
            el.next = p.next;            
        }        
        p.next = el;
        
        return this;
    }
    
    public Element<T> insert(T data, int position) {
        return insert(new Element<>(data), position);
    }
    
    //Removes element at the specified position from the list;
    //since we can remove element from the head of the list, 
    //and we can't write this = this.next; because 'this' is final,
    //we have to return next, and in order for the method to have a desired effect, we
    //must use it like this: myList = myList.remove(targetPosition);
    public Element<T> remove(int position) {
        if (position <= 0) {
            return next;
        }
        
        Element<T> p = this;        
        for (int i = 1; i < position - 1 && p.next != null; i++, p = p.next);
        
        if (p.next != null) {
            Element<T> q = p.next.next;
            p.next.next = null;
            p.next = q;
        }            
            return this;        
    }
    
    public boolean contains(Element<T> el) {
        Element<T> p = this;
        
        while (p.next != null) {
            if (p.equals(el)) {
                return true;
            }
            
            p = p.next;
        }
        
        return p.equals(el);
    }
    
    public boolean contains(T data) {
        return contains(new Element<>(data));
    }
    
    public boolean equals(Object el) {
        if (el == null) {
            return false;
        }
        
        if (!(el instanceof Element<?>)) {
            return false;
        }
        
        if ((((Element<?>) el).data == null) && (this.data == null)) {
            return true;
        }
        
        if ((((Element<?>) el).data == null) || (this.data == null)) {
            return false;
        }
        
        return ((((Element<?>) el).data.equals(this.data)));
    }
    
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42; // any arbitrary constant will do
    }
    
    public String toString() {
        StringBuilder res = new StringBuilder();
        
        Element<T> p = this;
        res.append(p.data);
        
        while (p.next != null) {
            res.append("; ");
            p = p.next;
            res.append(p.data);
        }
        
        return res.toString();
    }

}
