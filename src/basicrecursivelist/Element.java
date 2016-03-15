package basicrecursivelist;

public class Element {    
    private Integer data;    
    private Element next;
    
    public Element() {}
    
    public Element(Integer data) {
        this.data = data;
    }
    
    //Adds element to the end of the list
    public void append(Element el) {
        Element p = this;
        
        while (p.next != null) {
            p = p.next;
        }
        
        p.next = el;
    }
    
    public void append(Integer data) {
        append(new Element(data));
    }
    
    //Adds element to the beginning of the list;
    //since 'this' is final, we can't write this = el;
    //so we have to return el, and in order for the method to have a desired effect, we
    //must use it like this: myList = myList.prepend(newElement);
    public Element prepend(Element el) {
        el.next = this;
        return el;
    }
    
    public Element prepend(Integer data) {
        return prepend(new Element(data));
    }    
    
    //Inserts element at the specified position;
    //since we can add element to the head of the list, 
    //and we can't write this = el; because 'this' is final,
    //we have to return el, and in order for the method to have a desired effect, we
    //must use it like this: myList = myList.insert(newElement, targetPosition);
    public Element insert(Element el, int position) {
        if (position <= 0) {
            return prepend(el);
        }
        
        Element p = this;        
        for (int i = 1; i < position && p.next != null; i++, p = p.next);
        
        if (p.next != null) {
            el.next = p.next;            
        }        
        p.next = el;
        
        return this;
    }
    
    public Element insert(Integer data, int position) {
        return insert(new Element(data), position);
    }
    
    //Removes element at the specified position from the list;
    //since we can remove element from the head of the list, 
    //and we can't write this = this.next; because 'this' is final,
    //we have to return next, and in order for the method to have a desired effect, we
    //must use it like this: myList = myList.remove(targetPosition);
    public Element remove(int position) {
        if (position <= 0) {
            return next;
        }
        
        Element p = this;        
        for (int i = 1; i < position - 1 && p.next != null; i++, p = p.next);
        
        if (p.next != null) {
            Element q = p.next.next;
            p.next.next = null;
            p.next = q;
        }            
            return this;        
    }
    
    public boolean contains(Element el) {
        Element p = this;
        
        while (p.next != null) {
            if (p.equals(el)) {
                return true;
            }
            
            p = p.next;
        }
        
        return p.equals(el);
    }
    
    public boolean contains(Integer data) {
        return contains(new Element(data));
    }
    
    public boolean equals(Element el) {
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
        StringBuilder res = new StringBuilder();
        
        Element p = this;
        res.append(p.data);
        
        while (p.next != null) {
            res.append("; ");
            p = p.next;
            res.append(p.data);
        }
        
        return res.toString();
    }
    
}
