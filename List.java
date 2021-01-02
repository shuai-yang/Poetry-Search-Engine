
import java.util.NoSuchElementException;

public class List<T> {
    private class Node {
        private T data;
        private Node next;
        private Node prev;
        
        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
   
    private int length;
    private Node first;
    private Node last;
    private Node iterator;
   
    /****CONSTRUCTOR****/
   
    /**
     * Instantiates a new List with default values
     * @postcondition  A empty List is created
     */
    public List() {
    	first=last=null;
    	length = 0;
    	iterator = null;
    }
    
    /**
     * Instantiates a new List by copying another List
     * @param original the List to make a copy of
     * @postcondition a new List object, which is an identical
     * but separate copy of the List original
     */
    public List(List<T> original) {
        if (original == null) {
            return;
        }
        if (original.isEmpty()) {
            this.length = 0;
            this.first = null;
            this.last = null;
            this.iterator = null;
        } else {
            Node temp = original.first;
            while (temp != null) {
                this.addLast(temp.data); 
                temp = temp.next;
            }
            this.iterator = null;
        }  
    }
   
    /****ACCESSORS****/
   
    /**
     * Returns the value stored in the first node
     * @precondition !isEmpty()
     * @return the value stored at first node
     * @throws NoSuchElementException when List is empty
     */
    public T getFirst() throws NoSuchElementException{
    	if(isEmpty()){
    		throw new NoSuchElementException(
    				"getFirst(): List is empty. No data to access!"); 
    	} 
        return first.data;
    }
   
    /**
     * Returns the value stored in the last node
     * @precondition !isEmpty()
     * @return the value stored in the last node
     * @throws NoSuchElementException when List is empty
     */
    public T getLast() throws NoSuchElementException{
    	if(isEmpty()){
    		throw new NoSuchElementException(
    				"getLast(): List is empty. No data to access!"); 
    	}
        return last.data;
    }
   
    /**
     * Returns the current length of the list
     * @return the length of the list from 0 to n
     */
    public int getLength() {
        return length;
    }
   
    /**
     * Returns whether the list is currently empty
     * @return whether the list is empty
     */
    public boolean isEmpty() {
        return length==0;
    }
   
    /**
     * returns the element currently pointed at by the iterator
     * @precondition !offEnd()
     * @return the value pointed at by the iterator
     * @throws NullPointerException when the iterator is null
     */
    public T getIterator()  throws NullPointerException {
    	if(offEnd()) {throw new NullPointerException(
    			"getIterator(): iterator is off the end of the List and cannot be accessed");}
    	return iterator.data;
    }
    
    /**
     * return whether or not the iterator off the end of the List, i.e. is NULL
     * @return whether the iterator is off the end of the List
     */
    public boolean offEnd() {
    	return iterator==null;
    }
    
    /**
     * determines whether two Lists have the same data
     * in the same order
     * @param o the Object to compare to this List
     * @return whether the two Lists are equal
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
    	if(this == o) {
    		return true;
    	}else if(! (o instanceof List)) {
    		return false;
    	}else {
    		List<T> L =(List<T>) o;
    		if(this.length != L.length) {return false;}
    		else {
    			Node temp1 = this.first;
    			Node temp2 = L.first;
    			while(temp1 != null) {
    				if(!temp1.data.equals(temp2.data)){return false;}
    				temp1=temp1.next;
    				temp2=temp2.next;
    			}
    			return true;
    		}
    	}
    }
    
    /****MUTATORS****/
   
    /**
     * Creates a new first element
     * @param data the data to insert at the
     * front of the list
     * @postcondition A new first node is created
     */
    public void addFirst(T data) {
    	Node N = new Node(data);
    	if(isEmpty()){
    		first=last=N; 
    	}else{
    		N.next=first;
    		first.prev=N;
    		first = N;
    	}
    	length++;
    }
   
    /**
     * Creates a new last element
     * @param data the data to insert at the
     * end of the list
     * @postcondition A new last node is created
     */
    public void addLast(T data) {
    	Node N = new Node(data);
    	if(isEmpty()){
    		first=last=N; 
    	}else{
    		last.next=N;
    		N.prev = last;
    		last = N;
    	}
    	length++;
    }
   
    /**
    * removes the element at the front of the list
    * @precondition !isEmpty()
    * @postcondition the first element is removed; if first element is iterator, set iterator to be null
    * @throws NoSuchElementException when List is empty
    */
    public void removeFirst() throws NoSuchElementException{
    	if (isEmpty()) {
            throw new NoSuchElementException(
            		"removeFirst(): Cannot remove "+ "from an empty List!");
        }
    	if(iterator == first) {
    		iterator =null;
    	}
    	if (length == 1) {
        	first=last= null;
        } else {
        	first = first.next;
        	first.prev = null;
        }
        length--;
    }
   
    /**
     * removes the element at the last of the list
     * @precondition !isEmpty()
     * @postcondition the last element is removed; if last element is iterator, set iterator to be null
     * @throws NoSuchElementException when List is empty
     */
    public void removeLast() throws NoSuchElementException{
    	if(isEmpty()) {
   		 throw new NoSuchElementException(
   				 "removeLast(): list is empty. Nothing to remove.");
        } 
    	if(iterator==last) {
    		iterator = null;
    	}
    	if (length == 1) { 
        	first=last= null;
        } else {
            last = last.prev;
            last.next = null;
        }
        length--;
    }
    
    /** 
     * moves the iterator to the start of the list  
     * @postcondition iterator moved to the start of the list; if list is empty, iterator=first=null
     **/
    public void placeIterator() {
    	iterator = first;
    }
    
    /**
     * remove the element currently pointed to by the iterator
     * @precondition !offEnd() 
     * @postcondition the element currently pointed to by the iterator is removed; iterator points to null;
     * @throws NullPointerException when precondition is violate
     */
    public void removeIterator() throws NullPointerException{
    	if(offEnd()) {
    		throw new NullPointerException(
    				"removeIterator(): iterator is off the end of the List. "
    				+"Nothing to remove");
    	}
    	if(iterator==first) {removeFirst();}
    	else if(iterator==last) {removeLast();}
    	else {
    		iterator.prev.next=iterator.next;
    		iterator.next.prev=iterator.prev;
    		iterator = null;
    		length--;
    	}
    }

    /**
     * inserts new data in the List after the iterator
     * @param data the new data to insert
     * @precondition: !offEnd()   
     * @throws NullPointerException when offEnd() 
     * @postcondition insert an element after the node currently pointed to by the iterator
     */
    public void addIterator(T data) throws NullPointerException {
    	Node N = new Node(data);
    	if(offEnd()) {throw new NullPointerException(
    			"addIterator(T data):  iterator is off the end of the List. Cannot add.");}
    	if(iterator==last) {addLast(data);}
    	else {
    		N.prev = iterator;
    		N.next = iterator.next;
    		N.prev.next = N; 
    		N.next.prev = N; 
    		length++;
    	}
    }
    
    /**
     * moves the iterator up by one node
     * @precondition !offEnd()
     * @throws NullPointerException when the iterator is off the end of the list
     */
    public void advanceIterator()  throws NullPointerException{
    	if(offEnd()) {throw new NullPointerException(
    			"advanceIterator(): iterator is off the end of the List and cannot advance");}
    	iterator = iterator.next;
    }
    
    /**
     * moves the iterator down by one node
     * @precondition !offEnd()
     * @throws NullPointerException when the iterator is off the end of the list
     */
    public void reverseIterator() throws NullPointerException{
    	if(offEnd()) {throw new NullPointerException(
    			"reverseIterator(): iterator is off the end of the List and cannot reverse");}
    	iterator = iterator.prev;
    }
    
    /****ADDITIONAL OPERATIONS****/
   
    /**
     * list each value on the list 
     * At the end of the List a new line
     * @return the List as a String for display
     */
    @Override public String toString() {
    	String result = "";
        Node temp = first;
        while(temp != null) {
        	result += temp.data;
            temp = temp.next;
        }
        return result;
    }
    /**
     * print the contents of the linked list to the screen in the format 
     * #. <element> followed by a newline
     * @throws NoSuchElementException when the list is empty
     */
    public void printNumberedList() throws NoSuchElementException{
    	if(isEmpty()) {throw new NoSuchElementException(
    			"printNumberedList(): this is a empty list. Nothing to print.");}
      	Node temp = first;
      	int i=0;
    	while(temp!=null) {
      		System.out.println(++i +". "+temp.data);
      		temp=temp.next;
      	}
    }
    
    /********** For Lab 5 **********/
    
    /**
    * Points the iterator at first
    * and then advances it to the
    * specified index
    * @param index the index where
    * the iterator should be placed
    * @precondition 0 < index <= length
    * @throws IndexOutOfBoundsException
    * when precondition is violated
    */
    public void iteratorToIndex(int index) throws IndexOutOfBoundsException{	
       if(index<0 || index >=length) {throw new 
    	   IndexOutOfBoundsException("iteratorToIndex(): index is out of bounds");}
       else {
    	   placeIterator();
    	   while(index>0) {advanceIterator(); index--;}  
       }
    }

    /**
    * Searches the List for the specified
    * value using the linear  search algorithm
    * @param value the value to search for
    * @return the location of value in the
    * List or -1 to indicate not found
    * Note that if the List is empty we will
    * consider the element to be not found
    * post: position of the iterator remains
    * unchanged
    */
    public int linearSearch(T value) {
    	Node N = first;
    	int i=0;
    	while(N!=null) {
    		i++;
    		if(N.data.equals(value)) {return i;}
    		N = N.next;
    	}
        return -1;
    }
}