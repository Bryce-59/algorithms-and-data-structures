import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeSet;

public class TreeMap<K extends Comparable<K>, V extends Comparable<V>> implements NavigableMap<K, V> {
    TreeMapEntry root;
    int size = 0;
    Comparator<? super K> comparator;

    /**
     * Constructs an empty TreeMap
     */
    public TreeMap() {
    }

    /**
     * Constructs a TreeMap from the entries in the specified map
     * 
     * @param m the specified map
     */
    public TreeMap(Map<? extends K, ? extends V> m) {
        putAll(m);
    }

    /**
     * Returns the first key currently in this map.
     * 
     * @return the first key in the map
     * 
     * @exception NoSuchElementException if the map is empty
     */
    public K firstKey() {
        if (isEmpty()) {
            throw new NoSuchElementException("The map is empty.");
        }
        return firstEntry().getKey();
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * 
     * @return the last key in the map.
     * 
     * @exception NoSuchElementException if the map is empty
     */
    public K lastKey() {
        if (isEmpty()) {
            throw new NoSuchElementException("The map is empty.");
        }
        return lastEntry().getKey();
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * 
     * @return a Set view of keys.
     */
    public Set<K> keySet() {
        return navigableKeySet();
    }

    /**
     * Returns a Collection of the values contained in this map.
     * 
     * @return a Collection of values.
     */
    public Collection<V> values() {
        Collection<V> ret = new ArrayList<>();
        valuesHelper(ret, root);
        return ret;
    }

    /**
     * Returns a Set view of the entries contained in this map.
     * 
     * @return a Set view of entries.
     */
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> ret = new TreeSet<>();
        entrySetHelper(ret, root);
        return ret;
    }

    /**
     * Returns the number of key-value mappings in this map.
     * 
     * @return the size of the map
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this map contains no key-value mappings.
     * 
     * @return whether the map is empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     * 
     * @param key the object to find
     * @return true if the mapping contains the key.
     * 
     * @exception ClassCastException if key is of an inappopriate type.
     */
    public boolean containsKey(Object key) {
        if (isEmpty()) {
            return false;
        } else if (root.compareKey(key) == 0) {
            return true;
        }

        return getParent(key).getChild(key) != null;
    }

    /**
     * Returns true if this map maps one or more keys to the specified value.
     * 
     * @param value the object to find
     * @return true if the mapping contains the value
     * 
     * @exception ClassCastException if key is of an inappopriate type.
     */
    public boolean containsValue(Object value) {
        return containsValueHelper(value, root);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map
     * contains no mapping for the key.
     * 
     * @param key the key to reference
     * @return the value at the key, if it is exists
     * 
     * @exception ClassCastException if key is of an inappopriate type.
     */
    public V get(Object key) {
        if (root.compareKey(key) == 0) {
            return root.getValue();
        }
        TreeMapEntry tmp_p = getParent(key);
        TreeMapEntry tmp = tmp_p.getChild(key);
        return tmp != null ? tmp.getValue() : null;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * 
     * @param key   the key at which to store the value
     * @param value the value to be stored at the key
     * 
     * @return the value that was previously stored with key
     */
    public V put(K key, V value) {
        V ret = null;
        if (size() == 0) {
            root = new TreeMapEntry(key, value);
            size++;
            return ret;
        }

        TreeMapEntry tmp_p = getParent(key);
        TreeMapEntry tmp = tmp_p.getChild(key);
        if (tmp != null) {
            ret = tmp.getValue();
            tmp.setValue(value);
        } else {
            TreeMapEntry add = new TreeMapEntry(key, value);
            if (tmp_p.compareKey(key) > 0) {
                tmp_p.setLeft(add);
            } else {
                tmp_p.setRight(add);
            }
            size++;
        }

        rebalance();
        return ret;
    }

    // TODO
    private void rebalance() {
    }

    /**
     * Removes the mapping for this key from this TreeMap if present.
     * 
     * @param key the key to remove from the map
     * @return the value that was removed from the map
     * 
     * @exception ClassCastException if the key has an inappropriate type
     */
    public V remove(Object key) {
        V ret = null;
        printTree();
        if (isEmpty()) {
            return null;
        } else if (root.compareKey(key) == 0) {
            ret = root.getValue();
            rootRemove();
        } else {
            TreeMapEntry tmp_p = getParent(key);
            TreeMapEntry tmp = tmp_p.getChild(key);
            ret = tmp != null ? tmp.getValue() : null;
            if (tmp == null) {
                return null;
            }
            tmp_p.remove(tmp);
        }
        rebalance();
        size--;
        return ret;
    }

    /**
     * Copies all of the mappings from the specified map to this map.
     * 
     * @param m the map to copy from
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K k : m.keySet()) {
            put(k, m.get(k));
        }
    }

    /**
     * Removes all of the mappings from this map.
     */
    public void clear() {
        clearHelper(root);
        root = null;
        size = 0;
    }

    /*
     * The following methods are related to the NavigableMap<K, V> method
     */

    /**
     * Returns a key-value mapping associated with the least key greater than or
     * equal to the given key, or null if there is no such key.
     * 
     * @return the key-value mapping
     */
    public Map.Entry<K, V> ceilingEntry(K key) {
        return upEntry(key, true);
    }

    /**
     * Returns the least key greater than or equal to the given key, or null if
     * there is no such key.
     * 
     * @return the key
     */
    public K ceilingKey(K key) {
        Entry<K, V> e = ceilingEntry(key);
        return e == null ? root == null ? null : root.getKey() : e.getKey();
    }

    /**
     * Returns the comparator used to order the keys in this map, or null if this
     * map uses the natural ordering of its keys.
     * 
     * @return the comparator
     */
    public Comparator<? super K> comparator() {
        return comparator;
    }

    /**
     * Returns a reverse order NavigableSet view of the keys contained in this map.
     * 
     * @return the descending set of keys
     */
    public NavigableSet<K> descendingKeySet() {
        return navigableKeySet().descendingSet();
    }

    /**
     * Returns a reverse order view of the mappings contained in this map.
     * 
     * @return the reverse order view
     */
    public NavigableMap<K, V> descendingMap() {
        return new DescendingTreeMap(this);
    }

    /**
     * Returns a key-value mapping associated with the least key in this map, or
     * null if the map is empty.
     * 
     * @return the key-value mapping
     */
    public Map.Entry<K, V> firstEntry() {
        TreeMapEntry tmp_p = firstParent();
        return tmp_p == null ? root : tmp_p.getLeft();
    }

    /**
     * Returns a key-value mapping associated with the greatest key less than or
     * equal to the given key, or null if there is no such key.
     * 
     * @return the key-value mapping
     */
    public Map.Entry<K, V> floorEntry(K key) {
        return downEntry(key, true);
    }

    /**
     * Returns the greatest key less than or equal to the given key, or null if
     * there is no such key.
     * 
     * @return the key
     */
    public K floorKey(K key) {
        Entry<K, V> e = floorEntry(key);
        return e == null ? root == null ? null : root.getKey() : e.getKey();
    }

    /**
     * Returns a view of the portion of this map whose keys are strictly less than
     * toKey.
     * 
     * @param toKey the key at which to end
     * 
     * @return the sub-view of the map
     */
    public SortedMap<K, V> headMap(K toKey) {
        return headMap(toKey, false);
    }

    /**
     * Returns a view of the portion of this map whose keys are less than (or equal
     * to, if inclusive is true) toKey.
     * 
     * @param toKey     the key at which to end
     * @param inclusive whether or not to include toKey in the map
     * 
     * @return the sub-value mapping
     */
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        return subMap(firstEntry().getKey(), true, toKey, inclusive);
    }

    /**
     * Returns a key-value mapping associated with the least key strictly greater
     * than the given key, or null if there is no such key.
     * 
     * @return the key-value mapping
     */
    public Map.Entry<K, V> higherEntry(K key) {
        return upEntry(key, false);
    }

    /**
     * Returns the least key strictly greater than the given key, or null if there
     * is no such key.
     * 
     * @return the key
     */
    public K higherKey(K key) {
        Entry<K, V> e = higherEntry(key);
        return e == null ? root == null ? null : root.getKey() : e.getKey();
    }

    /**
     * Returns a key-value mapping associated with the greatest key in this map, or
     * null if the map is empty.
     * 
     * @return the key-value mapping
     */
    public Map.Entry<K, V> lastEntry() {
        return size() == 1 ? root : lastParent().getRight();
    }

    /**
     * Returns a key-value mapping associated with the greatest key strictly less
     * than the given key, or null if there is no such key.
     * 
     * @return the key-value mapping
     */
    public Map.Entry<K, V> lowerEntry(K key) {
        return downEntry(key, false);
    }

    /**
     * Returns the greatest key strictly less than the given key, or null if there
     * is no such key.
     * 
     * @return the key
     */
    public K lowerKey(K key) {
        Entry<K, V> e = lowerEntry(key);
        return e == null ? root == null ? null : root.getKey() : e.getKey();
    }

    /**
     * Returns a NavigableSet view of the keys contained in this map.
     * 
     * @return the key set
     */
    public NavigableSet<K> navigableKeySet() {
        NavigableSet<K> ret = new TreeSet<K>();
        keySetHelper(ret, root);
        return ret;
    }

    /**
     * Removes and returns a key-value mapping associated with the least key in this
     * map, or null if the map is empty.
     * 
     * @return the key-value mapping that was removed
     */
    public Map.Entry<K, V> pollFirstEntry() {
        if (isEmpty()) {
            return null;
        }

        TreeMapEntry node = firstParent();
        TreeMapEntry ret;
        if (node == null) {
            ret = root;
            rootRemove();
        } else {
            ret = node.getLeft();
            node.remove(ret);
        }
        size--;
        rebalance();
        return ret;
    }

    /**
     * Removes and returns a key-value mapping associated with the greatest key in
     * this map, or null if the map is empty.
     * 
     * @return the key-value mapping that was removed
     */
    public Map.Entry<K, V> pollLastEntry() {
        if (isEmpty()) {
            return null;
        }

        TreeMapEntry node = lastParent();
        TreeMapEntry ret;
        if (node == null) {
            ret = root;
            rootRemove();
        } else {
            ret = node.getRight();
            node.remove(ret);
        }
        size--;
        rebalance();
        return ret;
    }

    /**
     * Returns a view of the portion of this map whose keys range from fromKey to
     * toKey.
     * 
     * @param fromKey       the key from which to start
     * @param fromInclusive whether or not to include fromKey in the subMap
     * @param toKey         the key at which to end
     * @param toInclusive   whether or not to include toKey in the subMap
     * 
     * @return the sub-view of the map
     */
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        // return new SubTreeMap(this, fromKey, fromInclusive, toKey, toInclusive);
        return this;
    }

    /**
     * Returns a view of the portion of this map whose keys range from fromKey,
     * inclusive, to toKey, exclusive.
     * 
     * @param fromKey the key at which to start
     * @param toKey   the key at which to end
     * 
     * @return the sub-view of the map
     */
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return subMap(fromKey, true, toKey, false);
    }

    /**
     * Returns a view of the portion of this map whose keys are greater than or
     * equal to fromKey.
     * 
     * @param fromKey the key at which to start
     * @return the sub-view of the map
     */
    public SortedMap<K, V> tailMap(K fromKey) {
        return tailMap(fromKey, true);
    }

    /**
     * Returns a view of the portion of this map whose keys are greater than (or
     * equal to, if inclusive is true) fromKey.
     * 
     * @param fromKey   key at which to start
     * @param inclusive whether ot not to include fromKey in the map
     * @return the sub-view of the map
     */
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return subMap(fromKey, inclusive, lastEntry().getKey(), true);
    }

    /*
     * Private helper methods
     */

    /**
     * Recursive helper method for clear()
     * 
     * @param current the node to clear
     */
    private void clearHelper(TreeMapEntry current) {
        if (current != null) {
            clearHelper(current.getLeft());
            clearHelper(current.getRight());

            current.setLeft(null);
            current.setRight(null);
        }
    }

    /**
     * Recursive helper method for containsVale()
     * 
     * @param value   the object to find
     * @param current the root of the current sub-tree to check
     * @return true if value is found in the relevant subtree
     * 
     * @exception ClassCastException if key is of an inappopriate type.
     */
    private boolean containsValueHelper(Object value, TreeMapEntry current) {
        boolean ret = false;
        if (current != null) {
            ret |= containsValueHelper(value, current.getLeft());
            if (ret) {
                return true;
            }
            ret |= current.getValue() != null ? current.getValue().equals(value) : value == null;
            if (ret) {
                return true;
            }
            ret |= containsValueHelper(value, current.getRight());
        }
        return ret;
    }

    /**
     * A helper method to find an entry with a key less than or equal to the
     * parameter.
     * 
     * @param key       the key to reference
     * @param inclusive whether the selected key can be the parameter
     * @return an entry that is less than or equal to the parameter
     */
    private Map.Entry<K, V> downEntry(K key, boolean inclusive) {
        TreeMapEntry save = null;
        TreeMapEntry tmp_p = null;
        TreeMapEntry tmp = root;
        while (tmp != null) {
            if (tmp.compareKey(key) > 0) {
                tmp_p = tmp;
                tmp = tmp.getLeft();
            } else if (tmp.compareKey(key) < 0) {
                save = tmp_p;
                tmp_p = tmp;
                tmp = tmp.getRight();
            } else {
                if (inclusive) {
                    return tmp;
                }

                if (!tmp.hasLeft()) {
                    return save;
                }

                tmp = tmp.getLeft();
                while (tmp.hasRight()) {
                    tmp = tmp.getRight();
                }
                return tmp;
            }
        }
        return tmp_p.compareKey(key) < 0 ? tmp_p : save;
    }

    /**
     * Recursive helper method for entrySet().
     * 
     * @param set     the set to add entries to.
     * @param current the current Entry to evaluate from.
     */
    private void entrySetHelper(Set<Entry<K, V>> set, TreeMapEntry current) {
        if (current != null) {
            entrySetHelper(set, current.getLeft());
            set.add(current);
            entrySetHelper(set, current.getRight());
        }
    }

    /**
     * Method to help the SubTree inner class create an entrySet.
     * Creates an entrySet within a specified range.
     * 
     * @param fromKey       the key from which to start
     * @param fromInclusive whether or not to include fromKey in the subMap
     * @param toKey         the key at which to end
     * @param toInclusive   whether or not to include toKey in the subMap
     * 
     * @return the set of entries within a specified range
     */
    protected Set<Entry<K, V>> entrySetInRange(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        Set<Entry<K, V>> ret = new TreeSet<>();
        entrySetInRangeHelper(ret, root, fromKey, fromInclusive, toKey, toInclusive);
        return ret;
    }

    /**
     * Helper method to create a set of entries within a specified range
     * @param set           the set to add entries to.
     * @param current       the current Entry to evaluate from.
     * @param fromKey       the key from which to start
     * @param fromInclusive whether or not to include fromKey in the subMap
     * @param toKey         the key at which to end
     * @param toInclusive   whether or not to include toKey in the subMap
     */
    private void entrySetInRangeHelper(Set<Entry<K, V>> set, TreeMapEntry current, K fromKey, boolean fromInclusive,
            K toKey, boolean toInclusive) {

        if (current != null) {
            if (current.compareKey(fromKey) > 0) {
                entrySetInRangeHelper(set, current.getLeft(), fromKey, fromInclusive, toKey, toInclusive);
            }

            boolean isFrom = current.compareKey(fromKey) == 0;
            boolean isTo = current.compareKey(toKey) == 0;
            if ((!isFrom || (isFrom && fromInclusive)) && (!isTo || (isTo && toInclusive))) {
                set.add(current);
            }

            if (current.compareKey(toKey) < 0) {
                entrySetInRangeHelper(set, current.getRight(), fromKey, fromInclusive, toKey, toInclusive);
            }
        }

    /**
     * Helper method to find the parent of the first entry
     * 
     * @return the parent of the key-value mapping
     */
    private TreeMapEntry firstParent() {
        TreeMapEntry tmp_p = null;
        if (!isEmpty()) {
            TreeMapEntry tmp = root;
            while (tmp.hasLeft()) {
                tmp_p = tmp;
                tmp = tmp.getLeft();
            }
        }
        return tmp_p;
    }

    /**
     * Overloaded helper function which finds the parent entry for a given key
     * 
     * @param o the object to reference
     * @return the entry which represents the parent entry
     * 
     * @exception ClassCastException if key is of an inappropriate type.
     */
    @SuppressWarnings("unchecked")
    private TreeMapEntry getParent(Object o) {
        try {
            return getParent((K) o);
        } catch (ClassCastException e) {
            throw new ClassCastException(o.toString() + "is not an appropriate key for this map.");
        }

    }

    /**
     * Overloaded helper function which finds the parent entry for a given key
     * 
     * @param o the object to reference
     * @return the entry which represents the parent entry
     */
    private TreeMapEntry getParent(K key) {
        TreeMapEntry tmp_p = null;
        TreeMapEntry tmp = root;

        while (tmp != null && (tmp.compareKey(key) != 0)) {
            tmp_p = tmp;
            tmp = tmp.compareKey(key) > 0 ? tmp.getLeft() : tmp.getRight();
        }
        return tmp_p;
    }

    /**
     * Recursive helper method for keySet().
     * 
     * @param set     the set to add keys to.
     * @param current the current Entry to evaluate from.
     */
    private void keySetHelper(Set<K> set, TreeMapEntry current) {
        if (current != null) {
            keySetHelper(set, current.getLeft());
            set.add(current.getKey());
            keySetHelper(set, current.getRight());
        }
    }

    /**
     * Helper method to find the parent of the last entry
     * 
     * @return the parent entry
     */
    private TreeMapEntry lastParent() {
        TreeMapEntry tmp_p = null;
        TreeMapEntry tmp = root;
        while (tmp.hasRight()) {
            tmp_p = tmp;
            tmp = tmp.getRight();
        }
        return tmp_p;
    }

    /**
     * Prints out a visualization of the tree. Used for debugging purposes.
     */
    protected void printTree() {
        printTree(root, "");
    }

    /**
     * Recursive helper method to visualize a tree.
     */
    private void printTree(TreeMapEntry n, String spaces) {
        if (n != null) {
            printTree(n.getRight(), spaces + "    ");
            System.out.println("(" + n.getKey() + ", " + n.getValue() + ")");
            printTree(n.getLeft(), spaces + "    ");
        }
    }

    /**
     * Helper method to remove and replace the root of the tree.
     */
    private void rootRemove() {
        TreeMapEntry tmp = new TreeMapEntry(null, null);
        tmp.setLeft(root);

        tmp.remove(root);
        root = tmp.getLeft();

        tmp.setLeft(null);
        tmp = null;
    }

    /**
     * A helper method to find an entry with a key greater than or equal to the
     * parameter.
     * 
     * @param key       the key to reference
     * @param inclusive whether the selected key can be the parameter
     * @return an entry that is greater than or equal to the parameter
     */
    private Map.Entry<K, V> upEntry(K key, boolean inclusive) {
        TreeMapEntry save = null;
        TreeMapEntry tmp_p = null;
        TreeMapEntry tmp = root;
        while (tmp != null) {
            if (tmp.compareKey(key) < 0) {
                tmp_p = tmp;
                tmp = tmp.getRight();
            } else if (tmp.compareKey(key) > 0) {
                save = tmp_p;
                tmp_p = tmp;
                tmp = tmp.getLeft();
            } else {
                if (inclusive) {
                    return tmp;
                }

                if (!tmp.hasRight()) {
                    return save;
                }

                tmp = tmp.getRight();
                while (tmp.hasLeft()) {
                    tmp = tmp.getLeft();
                }
                return tmp;
            }
        }
        return tmp_p.compareKey(key) > 0 ? tmp_p : save;
    }

    /**
     * Recursive helper method for values().
     * 
     * @param set     the collection to add values to.
     * @param current the current Entry to evaluate from.
     */
    private void valuesHelper(Collection<V> set, TreeMapEntry current) {
        if (current != null) {
            valuesHelper(set, current.getLeft());
            set.add(current.getValue());
            valuesHelper(set, current.getRight());
        }
    }

    /*
     * Private inner classes
     */

    /**
     * An inner class to represent an entry of the TreeMap
     */
    private class TreeMapEntry implements Map.Entry<K, V> {
        K key;
        V value;
        TreeMapEntry left;
        TreeMapEntry right;

        /**
         * @param key   the key
         * @param value the value
         */
        public TreeMapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns a comparator that compares Map.Entry in natural order on key.
         * 
         * @return the natural comparator
         */
        public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K, V>> comparingByKey() {
            return (o1, o2) -> o1.getKey().compareTo(o2.getKey());
        }

        /**
         * Returns a comparator that compares Map.Entry by key using the given
         * Comparator.
         * 
         * @param cmp the comparator to use
         * @return the comparator
         */
        public static <K, V> Comparator<Map.Entry<K, V>> comparingByKey(Comparator<? super K> cmp) {
            return (o1, o2) -> cmp.compare(o1.getKey(), o2.getKey());
        }

        /**
         * Returns a comparator that compares Map.Entry in natural order on value.
         * 
         * @return the natural comparator
         */
        public static <K, V extends Comparable<? super V>> Comparator<Map.Entry<K, V>> comparingByValue() {
            return (o1, o2) -> o1.getValue().compareTo(o2.getValue());
        }

        /**
         * Returns a comparator that compares Map.Entry by value using the given
         * Comparator.
         * 
         * @param cmp the comparator to use
         * @return the comparator
         */
        public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
            return (o1, o2) -> cmp.compare(o1.getValue(), o2.getValue());
        }

        /**
         * Compares the specified object with this entry for equality.
         * 
         * @param o the object to compare
         * @return whether the object is equal
         */
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }

            try {
                TreeMapEntry rhs = (TreeMapEntry) o;
                return compareKey(rhs.getKey()) == 0 && this.getValue() == null ? rhs.getValue() == null
                        : this.getValue().equals(rhs.getValue());
            } catch (ClassCastException e) {
            }
            return false;
        }

        /**
         * Returns the hash code value for this map entry.
         * 
         * @return the hashcode, which is defined as the exclusive disjunction of the
         *         hashcodes of the key and value.
         */
        public int hashCode() {
            return this.getKey() == null ? 0
                    : (this.getKey().hashCode() ^ (this.getValue() == null ? 0 : this.getValue().hashCode()));
        }

        /**
         * Checks if a left child exists.
         * 
         * @return if left child exists
         */
        public boolean hasLeft() {
            return left != null;
        }

        /**
         * Checks if a right child exists.
         * 
         * @return if right child exists
         */
        public boolean hasRight() {
            return right != null;
        }

        /**
         * Returns the key corresponding to this entry.
         * 
         * @return key
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the left child of this entry.
         * 
         * @return the left child
         */
        protected TreeMapEntry getLeft() {
            return left;
        }

        /**
         * Returns the right child of this entry.
         * 
         * @return the right child
         */
        protected TreeMapEntry getRight() {
            return right;
        }

        /**
         * Returns the value corresponding to this entry.
         * 
         * @return value
         */
        public V getValue() {
            return value;
        }

        /**
         * Replaces the left child of this entry.
         * 
         * @param left the new left child to store
         * @return the left child
         */
        protected TreeMapEntry setLeft(TreeMapEntry left) {
            TreeMapEntry ret = this.left;
            this.left = left;
            return ret;
        }

        /**
         * Replaces the right child of this entry.
         * 
         * @param the new right child to store
         * @return the right child
         */
        protected TreeMapEntry setRight(TreeMapEntry right) {
            TreeMapEntry ret = this.right;
            this.right = right;
            return ret;
        }

        /**
         * Replaces the value corresponding to this entry with the specified value.
         * 
         * @param value the new value to store
         * @return the previous value
         */
        public V setValue(V value) {
            V ret = this.value;
            this.value = value;
            return ret;
        }

        /**
         * Remove a child from this TreeMapEntry, and adjust its other children
         * accordingly.
         * 
         * @param child the child to remove.
         */
        protected void remove(TreeMapEntry child) {
            if (getChild(child.key) == null) {
                throw new IllegalArgumentException("Cannot remove child that does not exist.");
            }

            if (child.hasLeft() && child.hasRight()) {
                TreeMapEntry tmp_p = child;
                TreeMapEntry tmp = child.getLeft();
                while (tmp.hasRight()) {
                    tmp_p = tmp;
                    tmp = tmp.getRight();
                }
                key = tmp.getKey();
                value = tmp.getValue();
                tmp_p.remove(tmp);
            } else {
                if (child.hasLeft()) {
                    if (getLeft() != null && getLeft().equals(child)) {
                        setLeft(child.getLeft());
                    } else {
                        setRight(child.getLeft());
                    }
                    child.setLeft(null);
                } else if (child.hasRight()) {
                    if (getLeft() != null && getLeft().equals(child)) {
                        setLeft(child.getRight());
                    } else {
                        setRight(child.getRight());
                    }
                    child.setRight(null);
                } else {
                    if (getLeft() != null && getLeft().equals(child)) {
                        setLeft(null);
                    } else {
                        setRight(null);
                    }
                }
                size--;
            }
        }

        /**
         * Return the child of the entry with a specific key, if one exists.
         * 
         * @param key the key to reference
         * @return the child if it exists, else null
         */

        protected TreeMapEntry getChild(K key) {
            if (hasLeft() && getLeft().compareKey(key) == 0) {
                return getLeft();
            } else if (hasRight() && getRight().compareKey(key) == 0) {
                return getRight();
            }
            return null;
        }

        /**
         * Return the child of the entry with a specific key, if one exists.
         * Overloaded method for getChild.
         * 
         * @param o the key to reference
         * @return the child if it exists, else null
         */
        protected TreeMapEntry getChild(Object o) {
            return getChild((K) o);
        }

        /**
         * Compare this entry to an object based on its key.
         * 
         * @param o
         * @return
         */
        protected int compareKey(Object o) {
            if (getKey() == null && o == null) {
                return 0;
            }

            K k = (K) o;

            if (comparator != null) {
                return comparator.compare(getKey(), k);
            }

            return getKey().compareTo(k);
        }
    }

    /*
     * Special view classes
     */

    /**
     * A private inner class that returns a returns a sub-view of the mappings
     * contained in the outer class map.
     * 
     * Changes in the SubTreeMap will be reflected in the associated TreeMap and
     * vice versa.
     * 
     * Generally wraps the methods from the outer class, but makes checks on the
     * range in certain instances.
     */
    private class SubTreeMap implements NavigableMap<K, V> {
        TreeMap<K, V> treeMap;

        K fromKey;
        boolean fromInclusive;
        K toKey;
        boolean toInclusive;

        public SubTreeMap(TreeMap<K, V> treeMap, K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            if (compareKey(toKey, fromKey) < 0) {
                throw new IllegalArgumentException("invalid range");
            }

            this.treeMap = treeMap;
            this.fromKey = fromKey;
            this.fromInclusive = fromInclusive;
            this.toKey = toKey;
            this.toInclusive = toInclusive;
        }

        private boolean inBounds(Object key) {
            K k = (K) key;

            if (fromInclusive ? compareKey(k, fromKey) < 0 : comparator().compare(k, fromKey) <= 0) {
                return false;
            } else if (toInclusive ? compareKey(toKey, k) <= 0 : compareKey(toKey, k) < 0) {
                return false;
            }
            return true;
        }

        private int compareKey(K lhs, K rhs) {
            if (lhs == null && rhs == null) {
                return 0;
            }
            if (comparator() != null) {
                return comparator().compare(lhs, rhs);
            }
            return lhs.compareTo(rhs);
        }

        @Override
        public Comparator<? super K> comparator() {
            return treeMap.comparator();
        }

        @Override
        public K firstKey() {
            return firstEntry().getKey();
        }

        @Override
        public K lastKey() {
            return lastEntry().getKey();
        }

        @Override
        public Set<K> keySet() {
            return navigableKeySet();
        }

        @Override
        public Collection<V> values() {
            ArrayList<V> ret = new ArrayList<>();
            for (Entry<K, V> e : entrySet()) {
                ret.add(e.getValue());
            }
            return ret;
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            return treeMap.entrySetInRange(fromKey, fromInclusive, toKey, toInclusive);
        }

        @Override
        public int size() {
            return keySet().size();
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        @Override
        public boolean containsKey(Object key) {
            if (!inBounds(key)) {
                return false;
            }
            return treeMap.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return values().contains(value);
        }

        @Override
        public V get(Object key) {
            if (!inBounds(key)) {
                return null;
            }

            return treeMap.get(key);
        }

        @Override
        public V put(K key, V value) {
            if (!inBounds(key)) {
                throw new IllegalArgumentException("Out-of-bounds key: " + key.toString());
            }

            return treeMap.put(key, value);
        }

        @Override
        public V remove(Object key) {
            if (!inBounds(key)) {
                return null;
            }

            return treeMap.remove(key);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            for (K k : m.keySet()) {
                if (!inBounds(k)) {
                    throw new IllegalArgumentException("Out-of-bounds key: " + k.toString());
                }
            }
            treeMap.putAll(m);

        }

        @Override
        public void clear() {
            for (K k : keySet()) {
                treeMap.remove(k);
            }
        }

        @Override
        public Entry<K, V> lowerEntry(K key) {
            if (!inBounds(key)) {
                if (compareKey(toKey, key) < 0) {
                    return lastEntry();
                } else {
                    return null;
                }
            }
            return treeMap.lowerEntry(key);
        }

        @Override
        public K lowerKey(K key) {
            return lowerEntry(key).getKey();
        }

        @Override
        public Entry<K, V> floorEntry(K key) {
            if (!inBounds(key)) {
                if (compareKey(toKey, key) <= 0) {
                    return lastEntry();
                } else {
                    return null;
                }
            }
            return treeMap.floorEntry(key);
        }

        @Override
        public K floorKey(K key) {
            return floorEntry(key).getKey();
        }

        @Override
        public Entry<K, V> ceilingEntry(K key) {
            if (!inBounds(key)) {
                if (compareKey(key, fromKey) <= 0) {
                    return firstEntry();
                } else {
                    return null;
                }
            }
            return treeMap.ceilingEntry(key);
        }

        @Override
        public K ceilingKey(K key) {
            return ceilingEntry(key).getKey();
        }

        @Override
        public Entry<K, V> higherEntry(K key) {
            if (!inBounds(key)) {
                if (compareKey(key, fromKey) < 0) {
                    return firstEntry();
                } else {
                    return null;
                }
            }
            return treeMap.higherEntry(key);
        }

        @Override
        public K higherKey(K key) {
            return higherEntry(key).getKey();
        }

        @Override
        public Entry<K, V> firstEntry() {
            return fromInclusive ? treeMap.ceilingEntry(fromKey) : treeMap.higherEntry(fromKey);
        }

        @Override
        public Entry<K, V> lastEntry() {
            return toInclusive ? treeMap.floorEntry(toKey) : treeMap.lowerEntry(toKey);
        }

        @Override
        public Entry<K, V> pollFirstEntry() {
            Entry<K, V> ret = firstEntry();
            remove(ret.getKey());
            return ret;
        }

        @Override
        public Entry<K, V> pollLastEntry() {
            Entry<K, V> ret = lastEntry();
            remove(ret.getKey());
            return ret;
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            return new DescendingTreeMap(this);
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            NavigableSet<K> ret = new TreeSet();
            Set<Entry<K, V>> tmp = entrySetInRange(fromKey, fromInclusive, toKey, toInclusive);
            for (Entry<K, V> e : tmp) {
                ret.add(e.getKey());
            }
            return ret;
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return navigableKeySet().descendingSet();
        }

        @Override
        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            if (!inBounds(fromKey) || !inBounds(toKey)) {
                throw new IllegalArgumentException("Out-of-bounds key");
            }

            return new SubTreeMap(treeMap, fromKey, fromInclusive, toKey, toInclusive);
        }

        @Override
        public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            return subMap(firstKey(), true, toKey, inclusive);
        }

        @Override
        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            return subMap(fromKey, inclusive, lastKey(), true);
        }

        @Override
        public SortedMap<K, V> subMap(K fromKey, K toKey) {
            return subMap(fromKey, true, toKey, false);
        }

        @Override
        public SortedMap<K, V> headMap(K toKey) {
            return headMap(toKey, false);
        }

        @Override
        public SortedMap<K, V> tailMap(K fromKey) {
            return tailMap(fromKey, true);
        }

    }

    /**
     * A private inner class that returns a returns a reverse order view of the
     * mappings contained in the outer class map.
     * 
     * Changes in the DescendingTreeMap will be reflected in the associated
     * NavigableMap and vice versa.
     * 
     * Generally wraps the methods from the outer class, but reverses their calls in
     * certain instances.
     */
    private class DescendingTreeMap implements NavigableMap<K, V> {
        NavigableMap<K, V> treeMap;

        public DescendingTreeMap(NavigableMap<K, V> treeMap) {
            this.treeMap = treeMap;
        }

        @Override
        public Comparator<? super K> comparator() {
            return treeMap.comparator().reversed();
        }

        @Override
        public K firstKey() {
            return treeMap.lastKey();
        }

        @Override
        public K lastKey() {
            return treeMap.firstKey();
        }

        @Override
        public Set<K> keySet() {
            return navigableKeySet();
        }

        @Override
        public Collection<V> values() {
            ArrayList<V> ret = new ArrayList<V>(treeMap.values());
            Collections.reverse(ret);
            return ret;
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            ArrayList<Entry<K, V>> ret = new ArrayList<Entry<K, V>>(treeMap.entrySet());
            Collections.reverse(ret);
            return new TreeSet<Entry<K, V>>(ret);
        }

        @Override
        public int size() {
            return treeMap.size();
        }

        @Override
        public boolean isEmpty() {
            return treeMap.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return treeMap.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return treeMap.containsValue(value);
        }

        @Override
        public V get(Object key) {
            return treeMap.get(key);
        }

        @Override
        public V put(K key, V value) {
            return treeMap.put(key, value);
        }

        @Override
        public V remove(Object key) {
            return treeMap.remove(key);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            treeMap.putAll(m);
        }

        @Override
        public void clear() {
            treeMap.clear();
        }

        @Override
        public Entry<K, V> lowerEntry(K key) {
            return treeMap.higherEntry(key);
        }

        @Override
        public K lowerKey(K key) {
            return treeMap.higherKey(key);
        }

        @Override
        public Entry<K, V> floorEntry(K key) {
            return treeMap.ceilingEntry(key);
        }

        @Override
        public K floorKey(K key) {
            return treeMap.ceilingKey(key);
        }

        @Override
        public Entry<K, V> ceilingEntry(K key) {
            return treeMap.floorEntry(key);
        }

        @Override
        public K ceilingKey(K key) {
            return treeMap.floorKey(key);
        }

        @Override
        public Entry<K, V> higherEntry(K key) {
            return treeMap.lowerEntry(key);
        }

        @Override
        public K higherKey(K key) {
            return treeMap.lowerKey(key);
        }

        @Override
        public Entry<K, V> firstEntry() {
            return treeMap.lastEntry();
        }

        @Override
        public Entry<K, V> lastEntry() {
            return treeMap.firstEntry();
        }

        @Override
        public Entry<K, V> pollFirstEntry() {
            return treeMap.pollLastEntry();
        }

        @Override
        public Entry<K, V> pollLastEntry() {
            return treeMap.pollFirstEntry();
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            return treeMap;
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return treeMap.descendingKeySet();
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return treeMap.navigableKeySet();
        }

        @Override
        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            return new DescendingTreeMap(treeMap.subMap(fromKey, fromInclusive, toKey, toInclusive));
        }

        @Override
        public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            return subMap(firstKey(), true, toKey, inclusive);
        }

        @Override
        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            return subMap(fromKey, inclusive, lastKey(), true);
        }

        @Override
        public SortedMap<K, V> subMap(K fromKey, K toKey) {
            return subMap(fromKey, true, toKey, false);
        }

        @Override
        public SortedMap<K, V> headMap(K toKey) {
            return headMap(toKey, false);
        }

        @Override
        public SortedMap<K, V> tailMap(K fromKey) {
            return tailMap(fromKey, true);
        }
    }
}