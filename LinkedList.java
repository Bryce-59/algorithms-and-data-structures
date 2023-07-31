import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.UnaryOperator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements List<E> {
    private class LinkedListNode {
        private E data;
        private LinkedListNode prev;
        private LinkedListNode next;

        public LinkedListNode() {
            this(null);
        }

        public LinkedListNode(E data) {
            this.data = data;
        }

        public E getData() {
            return data;
        }

        public LinkedListNode getNext() {
            return next;
        }

        public LinkedListNode getPrev() {
            return prev;
        }

        public void setData(E data) {
            this.data = data;
        }

        public void setNext(LinkedListNode next) {
            this.next = next;
        }

        public void setPrev(LinkedListNode prev) {
            this.prev = prev;
        }
    }

    LinkedListNode head;
    LinkedListNode foot;
    int size = 0;

    public LinkedList() {
        head = new LinkedListNode();
        foot = new LinkedListNode();

        head.setNext(foot);
        foot.setPrev(head);
    }

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

    // TODO
    public boolean equals(Object o) {
        return false;
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

        return getNode(index).getData();
    }

    // TODO
    private LinkedListNode getParent(int index) {
        LinkedListNode it = null;
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
        return it;
    }

    private LinkedListNode getNode(int index) {
        return getParent(index).getNext();
    }

    /**
     * Returns the index of the first occurrence of the specified element in this
     * list, or -1 if this list does not contain the element.
     * 
     * @param o the element that is to be tested
     * @return index of the first occurrence of the specified element if it exists,
     *         else -1
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

    // TODO
    public Iterator<E> iterator() {
        return listIterator();
    }

    // TODO
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        if (o != null) {
            Iterator<E> it = iterator();
            int i = 0;
            while (it.hasNext()) {
                if (it.next().equals(o)) {
                    lastIndex = i;
                } else {
                    i++;
                }
            }
        }
        return lastIndex;
    }

    private class LinkedListIterator implements ListIterator<E> {
        LinkedListNode prev;
        LinkedListNode to_remove;
        int index;

        public LinkedListIterator() {
            this(0);
        }

        public LinkedListIterator(int index) {
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

        public boolean hasNext() {
            return nextIndex() < size();
        }

        public boolean hasPrevious() {
            return previousIndex() >= 0;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next element.");
            }
            prev = prev.getNext();
            index += 1;

            to_remove = prev;
            return prev.getData();
        }

        public int nextIndex() {
            return index;
        }

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

        public int previousIndex() {
            return index - 1;
        }

        public void remove() {
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

        public void set(E e) {
            to_remove.setData(e);
        }
    }

    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    // TODO
    public ListIterator<E> listIterator(int index) {
        return new LinkedListIterator(index);

    }

    public E remove(int index) {
        ListIterator<E> it = listIterator(index);
        E ret = it.next();
        it.remove();
        return ret;
    }

    public boolean remove(Object o) {
        ListIterator<E> it = listIterator();
        while (it.hasNext()) {
            if (it.next().equals(o)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public boolean removeAll(Collection<?> c) {
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

    public boolean retainAll(Collection<?> c) {
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

    public E set(int index, E element) {
        ListIterator<E> it = listIterator(index);
        E ret = it.next();
        it.set(element);
        return ret;
    }

    public int size() {
        return size;
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    public Object[] toArray() {
        Object[] ret = new Object[size()];

        int i = 0;
        ListIterator<E> it = listIterator();
        while (it.hasNext()) {
            ret[i++] = it.next();
        }
        return ret;
    }

    public <T> T[] toArray(T[] a) {
        T[] ret = a;
        if (a.length < size()) {
            // ret = new T[size()];
        }

        int i = 0;
        ListIterator<E> it = listIterator();
        while (i < size() && it.hasNext()) {
            // ret[i++] = (T) it.next();
        }
        if (a.length > size()) {
            ret[size()] = null;
        }
        return ret;
    }

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

}