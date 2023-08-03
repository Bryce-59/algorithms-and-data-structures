import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * An ordered collection implemented as a Doubly-Linked List.
 * 
 * Most operations run in O(n) time, with the exception of addFirst(),
 * addLast(), removeFirst(), and removeLast(), which run in O(1) time.
 * 
 * Documentation is largely taken from the documentation for the
 * List<E> and Deque<E> interfaces.
 * https://docs.oracle.com/javase/8/docs/api/java/util/List.html
 * https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html
 */
public class LinkedList<E> implements List<E>, Deque<E> {

    LinkedListNode head;
    LinkedListNode foot;
    int size = 0;

    /**
     * Construct a new, empty Linked List
     */
    public LinkedList() {
        head = new LinkedListNode();
        foot = new LinkedListNode();

        head.setNext(foot);
        foot.setPrev(head);
    }

    /**
     * Construct a view of a Linked List starting from h and leading to f, with s
     * representing the size. If this does not represent a well-formed Linked List
     * then the behavior is undefined.
     * 
     * @param h the head of the new list
     * @param f the foot of the new list
     * @param s the size of the new list
     */
    private LinkedList(LinkedListNode h, LinkedListNode f, int s) {
        head = h;
        foot = f;
        size = s;
    }

    /*
     * 
     * The following functions are part of the List interface.
     * 
     */

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be inserted
     * @return true
     *
     * @exception NullPointerException if element is null
     */
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException("LinkedList may not contain null elements.");
        }

        this.add(size(), e);
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this list. Shifts
     * the element currently at that position and any subsequent elements to the
     * right.
     * 
     * @param element element to be inserted
     * 
     * @exception IndexOutOfBoundsException if index is out of range (index < 0 ||
     *                                      index > size())
     * @exception NullPointerException      if element is null
     */
    public void add(int index, E element) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Tried to add " + element + " at index " + index + ".");
        } else if (element == null) {
            throw new NullPointerException("LinkedList may not contain null elements.");
        }
        listIterator(index).add(element);
    }

    /**
     * Appends all of the elements in the specified collection to the end of this
     * list, in the order that they are returned by the specified collection's
     * iterator.
     * 
     * The behavior of this operation is undefined if the specified collection is
     * modified while the operation is in progress.
     *
     * @param c collection containing elements to be added to this list
     * @return true if this list changed as a result of the call
     *
     * @exception NullPointerException if element is null or if the collection is
     *                                 null
     */
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException("Collection may not be null.");
        }
        return this.addAll(size(), c);
    }

    /**
     * Inserts all of the elements in the specified collection into this list at the
     * specified position, in the order that they are returned by the specified
     * collection's iterator.
     * 
     * The behavior of this operation is undefined if the specified collection is
     * modified while the operation is in progress.
     *
     * @param index index at which to insert the first element from the specified
     *              collection
     * @param c     collection containing elements to be added to this list
     * @return true if this list changed as a result of the call
     *
     * @exception IndexOutOfBoundsException if index is out of range (index < 0 ||
     *                                      index > size())
     * @exception NullPointerException      if element is null or if the collection
     *                                      is null
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Tried to add element at index " + index + ".");
        } else if (c == null) {
            throw new NullPointerException("Collection may not be null.");
        }

        ListIterator<E> it = listIterator(index);
        for (E element : c) {
            if (element == null) {
                throw new NullPointerException("LinkedList may not contain null elements.");
            }
            it.add(element);
        }
        return !c.isEmpty();
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    /**
     * Returns true if this list contains the specified element.
     * 
     * @param o element that is to be tested
     * @return true if element is present, else false
     * 
     * @exception NullPointerException if element is null
     */
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException("LinkedList may not contain null elements.");
        }
        return indexOf(o) != -1;
    }

    /**
     * Returns true if this list contains all of the elements of the specified
     * collection.
     * 
     * @param c collection containing elements that are to be tests
     * @return true if all elements are present
     * 
     * @exception NullPointerException if element is null or if the collection is
     *                                 null
     */
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection may not be null.");
        }

        Iterator<?> it = c.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the element at the specified position in this list.
     * 
     * @param index index of the element to return
     * @return element at the specified position
     * 
     * @exception IndexOutOfBoundsException if index is out of range (index < 0 ||
     *                                      index > size())
     */
    public E get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index may not be " + index + " on a LinkedList of size " + size());
        }

        return listIterator(index).next();
    }

    /**
     * Returns the index of the first occurrence of the specified element in this
     * list, or -1 if this list does not contain the element.
     * 
     * @param o the element that is to be tested
     * @return index of the first occurrence of the specified element if it exists,
     *         else -1
     * 
     * @exception NullPointerException if element is null
     */
    public int indexOf(Object o) {
        if (o == null) {
            throw new NullPointerException("LinkedList may not contain null elements");
        }

        Iterator<E> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().equals(o)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Returns true if this list contains no elements.
     * 
     * @return true if this list contains no elements.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * 
     * @return the iterator
     */
    public Iterator<E> iterator() {
        return listIterator();
    }

    /**
     * Returns the index of the last occurrence of the specified element in this
     * list, or -1 if this list does not contain the element.
     * 
     * @param o the element that is to be tested
     * @return index of the first occurrence of the specified element if it exists,
     *         else -1
     * 
     * @exception NullPointerException if element is null
     */
    public int lastIndexOf(Object o) {
        if (o == null) {
            throw new NullPointerException("LinkedList may not contain null elements");
        }

        ListIterator<E> it = listIterator(size());
        int i = size() - 1;
        while (it.hasPrevious()) {
            if (it.previous().equals(o)) {
                return i;
            }
            i--;
        }
        return -1;
    }

    /**
     * Returns a list iterator over the elements in this list
     * 
     * @return a list iterator
     */
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    /**
     * Returns a list iterator over the elements in this list,
     * starting at the specified position in the list. The specified index indicates
     * the first element that would be returned by an initial call to next.
     * 
     * @param index the index at which to start iterating
     * @return a list iterator that starts at the specified position
     * 
     * @exception IndexOutOfBoundsException if index is out of range (index < 0 ||
     *                                      index >= size())
     */
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index may not be " + index + " on a LinkedList of size " + size());
        }

        return new LinkedListIterator(index);
    }

    /**
     * Removes the element at the specified position in this list.
     * 
     * @index the index of the element to be removed
     * @return the element previously at that position
     * 
     * @exception IndexOutOfBounds if index is out of range (index < 0 ||
     *                             index >= size())
     */
    public E remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index may not be " + index + " on a LinkedList of size " + size());
        }

        ListIterator<E> it = listIterator(index);
        E ret = it.next();
        it.remove();
        return ret;
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it
     * is present.
     * 
     * @param o the object to remove
     * @return whether or not an instance of the object was present in the list
     * 
     * @exception NullPointerException if object is null
     */
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException("LinkedList may not contain null elements");
        }

        ListIterator<E> it = listIterator();
        while (it.hasNext()) {
            if (it.next().equals(o)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection.
     * 
     * @param c collection containing elements to be removed from this list
     * @return whether the list changed as a result of the operation
     * 
     * @exception NullPointerException if the collection is null
     */
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection may not be null");
        }

        boolean ret = false;
        ListIterator<E> it = listIterator();
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
                ret = true;
            }
        }
        return ret;
    }

    /**
     * Retains only the elements in this list that are contained in the specified
     * collection.
     * 
     * @param c collection containing elements to be retained in this list
     * @return whether the list changed as a result of the operation
     * 
     * @exception NullPointerException if the collection is null
     */
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection may not be null");
        }

        boolean ret = false;
        ListIterator<E> it = listIterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                ret = true;
            }
        }
        return ret;
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     * 
     * @param index   index of the element to replace
     * @param element element to be stored at specific position
     * @return the element that was replaced
     * 
     * @exception IndexOutOfBoundsException if index is out of range (index < 0 ||
     *                                      index >= size())
     * @exception NullPointerException      if element is null
     */
    public E set(int index, E element) {
        ListIterator<E> it = listIterator(index);
        E ret = it.next();
        it.set(element);
        return ret;
    }

    /**
     * Returns the number of elements in this list.
     * 
     * @return the number of elements in this list.
     */
    public int size() {
        return size;
    }

    /**
     * Returns a view of the portion of this list between the specified fromIndex,
     * inclusive, and toIndex, exclusive.
     * 
     * The semantics of the list returned by this method become undefined if the
     * backing list is structurally modified in any way other than
     * via the returned list.
     * 
     * @param toIndex the index to start from (inclusive)
     * @param toIndex the index to end at (exclusive)
     * 
     * @return the subList
     * 
     * @exception IndexOutofBoundsException if index is out of range (fromIndex < 0
     *                                      || fromIndex > toIndex || toIndex >
     *                                      size)
     */
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size) {
            throw new IndexOutOfBoundsException(
                    fromIndex + " and " + toIndex + " are illegal indicides for a list of size " + size());
        } else if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException(
                    "first argument " + fromIndex + " must be less or equal to second argument " + toIndex);
        }

        LinkedListNode h = head;
        for (int i = 0; i < fromIndex; i++) {
            h = h.getNext();
        }
        LinkedListNode f = h;
        for (int i = fromIndex; i <= toIndex; i++) {
            f = f.getNext();
        }

        return new LinkedList<E>(h, f, toIndex - fromIndex);
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element).
     * 
     * @return an array containing all of the elements in this list
     */
    public Object[] toArray() {
        Object[] ret = new Object[size()];

        int i = 0;
        ListIterator<E> it = listIterator();
        while (it.hasNext()) {
            ret[i++] = it.next();
        }
        return ret;
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence. The runtime type of the returned array is that of the specified
     * array.
     * 
     * @param a the array which should be used
     * @return an array containing all of the elements in this list
     * 
     * @exception NullPointerException if array is null.
     * @exception ArrayStoreException  if T is not a supertype of E
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        T[] ret = a;
        if (a.length < size()) {
            ret = (T[]) Arrays.copyOf(a, size, a.getClass());
        }

        int i = 0;
        ListIterator<E> it = listIterator();
        try {
            while (i < size() && it.hasNext()) {
                ret[i++] = (T) it.next();
            }
        } catch (ClassCastException e) {
            throw new ArrayStoreException("T is not a supertype of E");
        }
        if (a.length > size()) {
            ret[size()] = null;
        }
        return ret;
    }

    /*
     * 
     * The following functions are part of the Deque<E> interface.
     * 
     */

    /**
     * Inserts the specified element at the front of this list.
     * 
     * @param e
     */
    public void addFirst(E e) {
        if (e == null) {
            throw new NullPointerException("LinkedList may not contain null elements.");
        }
        this.add(0, e);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be inserted
     *
     * @exception NullPointerException if element is null
     */
    public void addLast(E e) {
        add(e);
    }

    /**
     * Returns an iterator over the elements in this list in reverse sequential
     * order.
     * 
     * @return an iterator in reverse sequential order
     */
    public Iterator<E> descendingIterator() {
        return new LinkedListDescendingIterator();
    }

    /**
     * Retrieves, but does not remove, the first element of this list.
     * 
     * @return the head of the list
     * 
     * @exception NoSuchElementException if list is empty
     */
    public E element() {
        return getFirst();
    }

    /**
     * Retrieves, but does not remove, the first element of this list.
     * 
     * @return the head of the list
     * 
     * @exception NoSuchElementException if list is empty
     */
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("LinkedList is empty");
        }
        return get(0);
    }

    /**
     * Retrieves, but does not remove, the last element of this list.
     * 
     * @return the last element of the list
     * 
     * @exception NoSuchElementException if list is empty
     */
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("LinkedList is empty");
        }
        return get(size() - 1);
    }

    /**
     * Inserts the specified element into the queue represented by this list.
     * 
     * @param e the element to be added to the list
     * @return true
     * 
     * @exception NullPointerException if element is null
     */
    public boolean offer(E e) {
        return offerLast(e);
    }

    /**
     * Inserts the specified element at the front of this list.
     * 
     * @param e the element to be added to the list
     * @return true
     * 
     * @exception NullPointerException if element is null
     */
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    /**
     * Inserts the specified element at the end of this list.
     * 
     * @param e
     * @return true
     * 
     * @exception NullPointerException if element is null
     */
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    /**
     * Retrieves, but does not remove, the head of the queue represented by this
     * list, or returns null if this list is empty.
     * 
     * @return the head of the list
     */
    public E peek() {
        return peekFirst();
    }

    /**
     * Retrieves, but does not remove, the first element of this list, or returns
     * null if this list is empty.
     * 
     * @return the first element of this list, or null if it is empty
     */
    public E peekFirst() {
        if (isEmpty()) {
            return null;
        }
        return getFirst();
    }

    /**
     * Retrieves, but does not remove, the last element of this list, or returns
     * null if this list is empty.
     * 
     * @return the last element of this list, or null if it is empty
     */
    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return getLast();
    }

    /**
     * Retrieves and removes the head of the queue represented by this list, or
     * returns null if this list is empty.
     * 
     * @return the element that was removed, or null if it is empty
     */
    public E poll() {
        return pollFirst();
    }

    /**
     * Retrieves and removes the first element of this list, or returns null if
     * this list is empty.
     * 
     * @return the element that was removed, or null if it is empty
     */
    public E pollFirst() {
        if (isEmpty()) {
            return null;
        }
        return removeFirst();
    }

    /**
     * Retrieves and removes the last element of this list, or returns null if
     * this list is empty.
     * 
     * @return the element that was removed, or null if it is empty
     */
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }
        return removeLast();
    }

    /**
     * Pops an element from the stack represented by this list.
     * 
     * @return the element that was popped
     * 
     * @exception NoSuchElementException if list is empty
     */
    public E pop() {
        return removeFirst();
    }

    /**
     * Pushes an element onto the stack represented by this list.
     * 
     * @param e the element to be added
     * 
     * @exception NullPointerException if element is null
     */
    public void push(E e) {
        addFirst(e);
    }

    /**
     * Retrieves and removes the head of this list.
     * 
     * @return the element previously at the head of this list.
     * 
     * @exception NoSuchElementException if list is empty
     */
    public E remove() {
        return removeFirst();
    }

    /**
     * Retrieves and removes the first element of this list.
     * 
     * @return the element previously at the front
     * 
     * @exception NoSuchElementException if list is empty
     */
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("This list is empty.");
        }
        return remove(0);
    }

    /**
     * Removes the first occurrence of the specified element from this list.
     * 
     * @param o the element to be removed
     * @return whether the element was present in the list
     */
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    /**
     * Removes the last occurrence of the specified element from this list.
     * 
     * @param o the element to be removed
     * @return whether the element was present in the list
     */
    public boolean removeLastOccurrence(Object o) {
        int i = lastIndexOf(o);
        boolean ret = i != -1;
        if (ret) {
            remove(i);
        }
        return ret;
    }

    /**
     * Retrieves and removes the last element of this list.
     * 
     * @return the element previously at the back
     * 
     * @exception NoSuchElementException if list is empty
     */
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("This list is empty.");
        }
        return remove(size() - 1);
    }

    /*
     * The following functions Override functions from the Object class.
     */

    /**
     * Indicates whether some other object is "equal to" this one.
     * 
     * @param o the object to compare
     * @return true if equal
     */
    public boolean equals(Object o) {
        if (o instanceof LinkedList) {
            LinkedList<?> lhs = (LinkedList<?>) o;
            Iterator<E> it = iterator();
            Iterator<?> ot = lhs.iterator();

            while (it.hasNext() && ot.hasNext()) {
                if (!it.next().equals(ot.next())) {
                    return false;
                }
            }
            return (!it.hasNext() && !ot.hasNext());
        }
        return false;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return string representation
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<E> it = iterator();

        sb.append("[");
        if (it.hasNext()) {
            sb.append(it.next());
        }
        while (it.hasNext()) {
            sb.append(", " + it.next());
        }
        sb.append("]");

        return sb.toString();
    }

    /**
     * A doubly-linked node class used for the internal storage of the list.
     */
    private class LinkedListNode {
        private E data;
        private LinkedListNode prev;
        private LinkedListNode next;

        /**
         * Constructs a new, unlinked node, with no internal data.
         */
        public LinkedListNode() {
            this(null);
        }

        /**
         * Constructs a new, unlinked node, with some internal data.
         * 
         * @param data the internal data to store
         */
        public LinkedListNode(E data) {
            this.data = data;
        }

        /**
         * Returns the element stored in this node.
         * 
         * @return the element
         */
        public E getData() {
            return data;
        }

        /**
         * Returns the LinkedListNode this node leads forward to.
         * 
         * @return the next node
         */
        public LinkedListNode getNext() {
            return next;
        }

        /**
         * Returns the LinkedListNode this node leads backward to.
         * 
         * @return the previous node
         */
        public LinkedListNode getPrev() {
            return prev;
        }

        /**
         * Replaces the element stored in this LinkedListNode with the specified
         * element.
         * 
         * @param data the element to store
         */
        public void setData(E data) {
            this.data = data;
        }

        /**
         * Replaces the LinkedListNode this node leads forward to with the specified
         * node.
         * 
         * @param next the node to store as the next node
         */
        public void setNext(LinkedListNode next) {
            this.next = next;
        }

        /**
         * Replaces the LinkedListNode this node leads backward to with the specified
         * node.
         * 
         * @param prev the node to store as the previous node
         */
        public void setPrev(LinkedListNode prev) {
            this.prev = prev;
        }
    }

    /**
     * A class used to iterate through the list
     */
    private class LinkedListIterator implements ListIterator<E> {
        LinkedListNode prev;
        LinkedListNode to_remove;
        int index;

        /**
         * Constructs a new LinkedListIterator from the position provided as a
         * parameter.
         * 
         * @param index the position to start from
         * 
         * @exception IndexOutOfBoundsException if index is out of range
         */
        public LinkedListIterator(int index) {
            if (index < 0 || index > size()) {
                throw new IndexOutOfBoundsException("Iterator cannot start from " + index);
            }

            LinkedListNode it = null;

            if (index == 0) {
                prev = head;
                to_remove = null;
            }

            else {

                if (index <= size() / 2) {
                    int i = 0;
                    it = head;
                    while (i < index) {
                        it = it.getNext();
                        i++;
                    }
                } else {
                    int i = size();
                    it = foot;
                    while (i >= index) {
                        it = it.getPrev();
                        i--;
                    }
                }
                prev = it;
                to_remove = it;
            }
            this.index = index;
        }

        /**
         * Inserts the specified element into the list.
         * 
         * @param e the element to be added
         */
        public void add(E e) {
            LinkedListNode add = new LinkedListNode(e);

            prev.getNext().setPrev(add);
            add.setNext(prev.getNext());
            prev.setNext(add);
            add.setPrev(prev);
            to_remove = null;

            index++;
            size++;
        }

        /**
         * Returns true if this list iterator has more elements when traversing the list
         * in the forward direction.
         * 
         * @return whether the list iterator has a next element
         */
        public boolean hasNext() {
            return nextIndex() < size();
        }

        /**
         * Returns true if this list iterator has more elements when traversing the list
         * in the reverse direction.
         * 
         * @return whether the list iterator has a previous element
         */
        public boolean hasPrevious() {
            return previousIndex() >= 0;
        }

        /**
         * Returns the next element in the list and advances the cursor position.
         * 
         * @return the next element in the list
         * 
         * @exception NoSuchElementException if the iteration has no next element
         */
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next element.");
            }
            prev = prev.getNext();
            index += 1;

            to_remove = prev;
            return prev.getData();
        }

        /**
         * Returns the index of the element that would be returned by a call to next().
         * 
         * @return the index of the element that would be returned by next(), or size()
         *         if three is no next element.
         */
        public int nextIndex() {
            return index;
        }

        /**
         * Returns the previous element in the list and moves the cursor position
         * backwards.
         * 
         * @return the previous element in the list.
         * 
         * @exception NoSuchElementException if there is no previous element.
         */
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("There is no previous element.");
            }
            to_remove = prev;
            E ret = prev.getData();

            prev = prev.getPrev();
            index -= 1;

            return ret;
        }

        /**
         * Returns the index of the element that would be returned by a subsequent call
         * to previous().
         * 
         * @return the index of the element that would be returned by prev(), or -1 if
         *         there is no previous element.
         */
        public int previousIndex() {
            return index - 1;
        }

        /**
         * Removes from the list the last element that was returned by next() or
         * previous()
         * 
         * @exception IllegalStateException if neither next nor previous have been
         *                                  called, or remove or add have been called
         *                                  after the last call to next or previous
         * 
         */
        public void remove() {
            if (to_remove == null) {
                throw new IllegalStateException(
                        "remove() may only be called once per call to next() or prev(), and only if add() has not been called after the last call to next or previous");
            }

            if (prev == to_remove) {
                prev = to_remove.getPrev();
            }

            to_remove.getPrev().setNext(to_remove.getNext());
            to_remove.getNext().setPrev(to_remove.getPrev());
            to_remove.setPrev(null);
            to_remove.setNext(null);
            to_remove = null;
            index--;

            size--;
        }

        /**
         * Replaces the last element returned by next() or previous() with the specified
         * element.
         * 
         * @param e the element to be stored
         * 
         * @exception IllegalStateException if neither next nor previous have been
         *                                  called, or remove or add have been called
         *                                  after the last call to next or previous
         */
        public void set(E e) {
            if (to_remove == null) {
                throw new IllegalStateException(
                        "remove() may only be called once per call to next() or prev(), and only if add() has not been called after the last call to next or previous");
            }

            to_remove.setData(e);
        }
    }

    /**
     * A wrapper class for LinkedListIterator used to iterate backwards through the
     * list
     */
    private class LinkedListDescendingIterator implements Iterator<E> {
        LinkedListIterator it;

        /** Construct a new iterator, starting from the back of the list */
        public LinkedListDescendingIterator() {
            it = new LinkedListIterator(size());
        }

        /**
         * Returns true if this list iterator has more elements when traversing the
         * list.
         * 
         * @return whether the list iterator has a next element
         */
        public boolean hasNext() {
            return it.hasPrevious();
        }

        /**
         * Returns the next element in the list and advances the cursor position.
         * 
         * @return the next element in the list
         * 
         * @exception NoSuchElementException if the iteration has no next element
         */
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next element.");
            }
            return it.previous();
        }

        /**
         * Removes from the list the last element that was returned by next()
         * 
         * @exception IllegalStateException if neither next nor previous have been
         *                                  called, or remove or add have been called
         *                                  after the last call to next or previous
         * 
         */
        public void remove() {
            it.remove();
        }
    }

}