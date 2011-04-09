package cmuHCI.WalkyScotty.util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.AbstractQueue;

/**
 * An unbounded priority {@linkplain Queue queue}. This queue orders elements
 * according to an order specified at construction time, which is specified
 * either according to their <i>natural order</i> (see {@link Comparable}), or
 * according to a {@link java.util.Comparator}, depending on which constructor
 * is used. A priority queue does not permit <tt>null</tt> elements. A
 * priority queue relying on natural ordering also does not permit insertion of
 * non-comparable objects (doing so may result in <tt>ClassCastException</tt>).
 * <p>
 * The <em>head</em> of this queue is the <em>least</em> element with
 * respect to the specified ordering. If multiple elements are tied for least
 * value, the head is one of those elements -- ties are broken arbitrarily. The
 * queue retrieval operations <tt>poll</tt>, <tt>remove</tt>,
 * <tt>peek</tt>, and <tt>element</tt> access the element at the head of
 * the queue.
 * <p>
 * A priority queue is unbounded, but has an internal <i>capacity</i> governing
 * the size of an array used to store the elements on the queue. It is always at
 * least as large as the queue size. As elements are added to a priority queue,
 * its capacity grows automatically. The details of the growth policy are not
 * specified.
 * <p>
 * This class and its iterator implement all of the <em>optional</em> methods
 * of the {@link Collection} and {@link Iterator} interfaces. The Iterator
 * provided in method {@link #iterator()} is <em>not</em> guaranteed to
 * traverse the elements of the MyPriorityQueue in any particular order. If you
 * need ordered traversal, consider using <tt>Arrays.sort(pq.toArray())</tt>.
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> Multiple
 * threads should not access a <tt>MyPriorityQueue</tt> instance concurrently if
 * any of the threads modifies the list structurally. Instead, use the
 * thread-safe {@link java.util.concurrent.PriorityBlockingQueue} class.
 * <p>
 * Implementation note: this implementation provides O(log(n)) time for the
 * insertion methods (<tt>offer</tt>, <tt>poll</tt>, <tt>remove()</tt>
 * and <tt>add</tt>) methods; linear time for the <tt>remove(Object)</tt>
 * and <tt>contains(Object)</tt> methods; and constant time for the retrieval
 * methods (<tt>peek</tt>, <tt>element</tt>, and <tt>size</tt>).
 */
public class MyPriorityQueue<T> extends AbstractQueue<T> {

	private static final int DEFAULT_INITIAL_CAPACITY = 11;
	private T[] heap;
	private Comparator<T> hComparator;
	private int size = 0;

	/**
	 * Creates a <tt>MyPriorityQueue</tt> with the default initial capacity (11)
	 * that orders its elements according to their natural ordering (using
	 * <tt>Comparable</tt>).
	 */
	public MyPriorityQueue ()
	{
		heap = (T[])new Object[DEFAULT_INITIAL_CAPACITY + 1]; //+ 1 for 1 based index
	}

	/**
	 * Creates a <tt>MyPriorityQueue</tt> with the specified initial capacity
	 * that orders its elements according to their natural ordering (using
	 * <tt>Comparable</tt>).
	 * 
	 * @param initialCapacity
	 *            the initial capacity for this priority queue.
	 * @throws IllegalArgumentException
	 *             if <tt>initialCapacity</tt> is less than 1
	 */
	public MyPriorityQueue (int initialCapacity)
	{
		if(initialCapacity < 1)
			throw new IllegalArgumentException();
			
		heap = (T[])new Object[initialCapacity + 1];
	}

	/**
	 * Creates a <tt>MyPriorityQueue</tt> with the specified initial capacity
	 * that orders its elements according to the specified comparator.
	 * 
	 * @param initialCapacity
	 *            the initial capacity for this priority queue.
	 * @param comparator
	 *            the comparator used to order this priority queue. If
	 *            <tt>null</tt> then the order depends on the elements'
	 *            natural ordering.
	 * @throws IllegalArgumentException
	 *             if <tt>initialCapacity</tt> is less than 1
	 */
	public MyPriorityQueue (int initialCapacity, Comparator<? super T> comparator)
	{
		if(initialCapacity<1)
			throw new IllegalArgumentException();
		
		if(comparator!=null)
			hComparator = (Comparator<T>) comparator; 
		
		heap = (T[])new Object[initialCapacity + 1];
	}
	
	/**
	 * Creates a <tt>MyPriorityQueue</tt> that orders its elements according to the specified comparator.
	 * 
	 * @param comparator
	 *            the comparator used to order this priority queue. If
	 *            <tt>null</tt> then the order depends on the elements'
	 *            natural ordering.
	 */
	public MyPriorityQueue (Comparator<? super T> comparator)
	{
		if(comparator!=null)
			hComparator = (Comparator<T>) comparator; 
		
		heap = (T[])new Object[DEFAULT_INITIAL_CAPACITY + 1];
	}


	/**
	 * Creates a <tt>MyPriorityQueue</tt> containing the elements in the
	 * specified collection. The priority queue has an initial capacity of 110%
	 * of the size of the specified collection or 1 if the collection is empty.
	 * If the specified collection is an instance of a
	 * {@link java.util.SortedSet} or is another <tt>MyPriorityQueue</tt>, the
	 * priority queue will be sorted according to the same comparator, or
	 * according to its elements' natural order if the collection is sorted
	 * according to its elements' natural order. Otherwise, the priority queue
	 * is ordered according to its elements' natural order.
	 * 
	 * @param c
	 *            the collection whose elements are to be placed into this
	 *            priority queue.
	 * @throws ClassCastException
	 *             if elements of the specified collection cannot be compared to
	 *             one another according to the priority queue's ordering.
	 * @throws NullPointerException
	 *             if <tt>c</tt> or any element within it is <tt>null</tt>
	 */
	public MyPriorityQueue (Collection<? extends T> c)
	{
		int colSize = c.size();
		
		if(colSize <1)
			colSize = 1;
		
		if(c instanceof java.util.SortedSet)
			hComparator = ((java.util.SortedSet)c).comparator();
		if(c instanceof MyPriorityQueue)
			hComparator = ((MyPriorityQueue)c).comparator();
		
		heap = (T[])new Object[DEFAULT_INITIAL_CAPACITY + 1];
		
		for(T x : c){
			offer(x);
		}
	}

	/**
	 * Returns the comparator used to order this collection, or <tt>null</tt>
	 * if this collection is sorted according to its elements natural ordering
	 * (using <tt>Comparable</tt>).
	 * 
	 * @return the comparator used to order this collection, or <tt>null</tt>
	 *         if this collection is sorted according to its elements natural
	 *         ordering.
	 */
	public Comparator<? super T> comparator ()
	{
		return hComparator;
	}

	// See superclass docs
	@Override
    public int size ()
	{
		return size;
	}

	/**
	 * Inserts the specified element into this priority queue.
	 * 
	 * @return <tt>true</tt>
	 * @throws ClassCastException
	 *             if the specified element cannot be compared with elements
	 *             currently in the priority queue according to the priority
	 *             queue's ordering.
	 * @throws NullPointerException
	 *             if the specified element is <tt>null</tt>.
	 */
	public boolean offer (T o)
	{
		if(o==null)
			throw new NullPointerException();

		if(size +1 == heap.length)
			resize();
		
		int pos = ++size;
		int test = pos/2;
		
		if(hComparator!=null){
			for(; pos>1 && hComparator.compare(o, heap[pos/2])<0; pos/=2){
				heap[pos] = heap[pos/2];
			}
		}
		else
		{
			for(; pos>1 && ((Comparable<T>)o).compareTo(heap[pos/2])<0; pos/=2){
				heap[pos] = heap[pos/2];
			}
		}
		
		
		heap[pos] = o;
		
		return true;
	}

	/**
	 * 
	 */
	public T poll ()
	{
		if(size()< 1)
			return null;
		
		T value = heap[1];
		heap[1] = heap[size--];
		heap[size + 1] = null;
		percolateDown(1);

		
		return value;
	}

	/**
	 * Returns the element at top of priority queue
	 */
	public T peek ()
	{
		if(size()<1)
			return null;
		return heap[1];
	}
	
	
	public int get(T elem){
		int i=1;
		
		while(i > heap.length){
			if(heap[i].equals(elem))
				return i;
		}
		
		return -1;
	}
	
	public T get(int i){
		if(i>= heap.length || i <1)
			return null;
		
		return heap[i];
	}
	
	public void bubbleUp(int startPos){
		while(startPos >0){	
			if(hComparator.compare((heap[startPos]), heap[startPos/2]) <0)
			{
				T x = heap[startPos];
				heap[startPos] = heap[startPos/2];
				heap[startPos/2] = x;
				startPos = startPos/2;
			}
			else
				break;
		}
	}
	
	private void percolateDown(int startPos){
		int child;
		T tmp = heap[startPos];
		
		if(hComparator!=null){ //need to check whether to use natural ordering or not
			for(; startPos*2 <= size; startPos = child){
				child = startPos*2;
				
				if(child!=size && hComparator.compare(heap[child+1],(heap[child])) <0)
					child++;//select "smaller" child in this case
				
				if(hComparator.compare(heap[child],tmp) <0)
					heap[startPos] = heap[child];
				else
					break;
			}
		}
		else{
			for(; startPos*2 <= size; startPos = child){
				child = startPos*2;
				
				if(child!=size && ((Comparable<T>)heap[child+1]).compareTo(heap[child]) <0)
					child++;//select "smaller" child in this case
				
				if(((Comparable<T>)heap[child]).compareTo(tmp) <0)
					heap[startPos] = heap[child];
				else
					break;
			}
		}
		heap[startPos] = tmp;
	}

	private void resize(){
		T[] newHeap = (T[])(new Object[heap.length*2]);
		
		for(int i=0; i<heap.length; i++){
			newHeap[i] = heap[i];
		}
		
		heap = newHeap;
	}
	
	/**
	 * Returns an iterator over the elements in this queue. The iterator does
	 * not return the elements in any particular order. The iterator does not
	 * support the remove operation.
	 * 
	 * @return an iterator over the elements in this queue.
	 */
	@Override
    public Iterator<T> iterator ()
	{
		return new QueueIterator<T>();
	}	
	
	public class QueueIterator<T> implements Iterator<T>{
		private int currentPos = 0;
		
		public boolean hasNext() {
			return currentPos + 1 <= size;
		}

		
		public T next() {
			if(!hasNext() || heap[currentPos+1]==null)
				throw new NoSuchElementException();
			
			currentPos++;
			
			return (T) heap[currentPos];
		}

		
		public void remove() {
			throw new UnsupportedOperationException();
			
		}
		
	}
}
