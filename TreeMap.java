import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeSet;

public class TreeMap<K extends Comparable<K>, V extends Comparable<V>> implements NavigableMap<K, V> {
    TreeMapEntry root;
    int size = 0;
    Comparator<? super K> comparator;

    @Override
    public K firstKey() {
        return firstEntry().getKey();
    }

    @Override
    public K lastKey() {
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

    @Override
    public int size() {
        return size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if ((root.getKey() == null && key == null) || root.getKey().equals(key)) {
            return true;
        }
        return getParent(key).getChild(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValueHelper(value, root);
    }

    private boolean containsValueHelper(Object value, TreeMapEntry current) {
        boolean ret = false;
        if (current != null) {
            ret |= containsValueHelper(value, current.getLeft());
            if (ret) {
                return true;
            }
            ret |= current.compareValue(value) == 0;
            if (ret) {
                return true;
            }
            ret |= containsValueHelper(value, current.getRight());
        }
        return ret;
    }

    public boolean containsValueHelper(TreeMapEntry cur, V value) {
        boolean ret = false;
        if (cur != null) {
            ret |= cur.getValue().equals(value);
            if (ret) {
                return true;
            }
            ret |= containsValueHelper(cur.getLeft(), value);
            if (ret) {
                return true;
            }
            ret |= containsValueHelper(cur.getLeft(), value);
        }
        return ret;
    }

    @Override
    public V get(Object key) {
        if ((root.getKey() == null && key == null) || root.getKey().equals(key)) {
            return root.getValue();
        }
        TreeMapEntry tmp = getParent(key);
        return tmp != null ? tmp.getChild(key).getValue() : null;
    }

    private TreeMapEntry getParent(Object o) {
        return getParent((K) o);
    }

    private TreeMapEntry getParent(K key) {
        TreeMapEntry tmp_p = null;
        TreeMapEntry tmp = root;

        while (tmp != null && !((tmp.getKey() == null && key == null) || tmp.getKey().equals(key))) {
            tmp_p = tmp;
            tmp = tmp.compareKey(key) < 0 ? tmp.getLeft() : tmp.getRight();
        }
        return tmp_p;
    }

    @Override
    public V put(K key, V value) {
        V ret = null;
        if (size() == 0) {
            root = new TreeMapEntry(key, value);
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
        }

        rebalance();

        return ret;
    }

    // TODO
    private void rebalance() {
    }

    @Override
    public V remove(Object key) {
        V ret = null;
        if (size() == 1) {
            ret = root.getValue();
            root = null;
            size--;
            return ret;
        }

        TreeMapEntry tmp_p = getParent(key);
        TreeMapEntry tmp = tmp_p.getChild(key);
        ret = tmp.getValue();
        tmp_p.remove(tmp);
        size--;

        rebalance();
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

    /*
     * The following methods are related to the NavigableMap<K, V> method
     */

    /**
     * Returns a key-value mapping associated with the least key greater than or
     * equal to the given key, or null if there is no such key.
     */
    public Map.Entry<K, V> ceilingEntry(K key) {
        return tailMap(key, true).firstEntry();
    }

    /**
     * Returns the least key greater than or equal to the given key, or null if
     * there is no such key.
     */
    public K ceilingKey(K key) {
        return ceilingEntry(key).getKey();
    }

    /**
     * Returns a reverse order NavigableSet view of the keys contained in this map.
     */
    public NavigableSet<K> descendingKeySet() {
        return navigableKeySet().descendingSet();
    }

    /**
     * Returns a reverse order view of the mappings contained in this map.
     */
    public NavigableMap<K, V> descendingMap() {
        return null; // TODO
    }

    /**
     * Returns a key-value mapping associated with the least key in this map, or
     * null if the map is empty.
     */
    public Map.Entry<K, V> firstEntry() {
        return size() == 1 ? root : firstParent().getLeft();
    }

    /*
     * Helper method to find the parent of the first entry
     */
    public TreeMapEntry firstParent() {
        TreeMapEntry tmp_p = null;
        TreeMapEntry tmp = root;
        while (tmp.hasLeft()) {
            tmp_p = tmp;
            tmp = tmp.getLeft();
        }
        return tmp_p;
    }

    /**
     * Returns a key-value mapping associated with the greatest key less than or
     * equal to the given key, or null if there is no such key.
     */
    public Map.Entry<K, V> floorEntry(K key) {
        return headMap(key, true).firstEntry();
    }

    /**
     * Returns the greatest key less than or equal to the given key, or null if
     * there is no such key.
     */
    public K floorKey(K key) {
        return floorEntry(key).getKey();
    }

    /**
     * Returns a view of the portion of this map whose keys are strictly less than
     * toKey.
     */
    public SortedMap<K, V> headMap(K toKey) {
        return headMap(toKey, false);
    }

    /**
     * Returns a view of the portion of this map whose keys are less than (or equal
     * to, if inclusive is true) toKey.
     */
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        return subMap(firstEntry().getKey(), true, toKey, inclusive);
    }

    /**
     * Returns a key-value mapping associated with the least key strictly greater
     * than the given key, or null if there is no such key.
     */
    public Map.Entry<K, V> higherEntry(K key) {
        return tailMap(key, false).firstEntry();
    }

    /**
     * Returns the least key strictly greater than the given key, or null if there
     * is no such key.
     */
    public K higherKey(K key) {
        return higherEntry(key).getKey();
    }

    /**
     * Returns a key-value mapping associated with the greatest key in this map, or
     * null if the map is empty.
     */
    public Map.Entry<K, V> lastEntry() {
        return size() == 1 ? root : lastParent().getRight();
    }

    /*
     * Helper method to find the parent of the last entry
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
     * Returns a key-value mapping associated with the greatest key strictly less
     * than the given key, or null if there is no such key.
     */
    public Map.Entry<K, V> lowerEntry(K key) {
        return headMap(key, false).firstEntry();
    }

    /**
     * Returns the greatest key strictly less than the given key, or null if there
     * is no such key.
     */
    public K lowerKey(K key) {
        return lowerEntry(key).getKey();
    }

    /**
     * Returns a NavigableSet view of the keys contained in this map.
     */
    public NavigableSet<K> navigableKeySet() {
        NavigableSet<K> ret = new TreeSet<K>();
        keySetHelper(ret, root);
        return ret;
    }

    /**
     * Removes and returns a key-value mapping associated with the least key in this
     * map, or null if the map is empty.
     */
    public Map.Entry<K, V> pollFirstEntry() {
        TreeMapEntry node = firstParent();
        TreeMapEntry ret = firstParent().getLeft();
        node.remove(ret);
        rebalance();
        return ret;
    }

    /**
     * Removes and returns a key-value mapping associated with the greatest key in
     * this map, or null if the map is empty.
     */
    public Map.Entry<K, V> pollLastEntry() {
        TreeMapEntry node = firstParent();
        TreeMapEntry ret = node.getRight();
        node.remove(ret);
        rebalance();
        return ret;
    }

    /**
     * Returns a view of the portion of this map whose keys range from fromKey to
     * toKey.
     */
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        return new SubTreeMap(fromKey, fromInclusive, toKey, toInclusive);
    }

    /**
     * Returns a view of the portion of this map whose keys range from fromKey,
     * inclusive, to toKey, exclusive.
     */
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return subMap(fromKey, true, toKey, false);
    }

    /**
     * Returns a view of the portion of this map whose keys are greater than or
     * equal to fromKey.
     */
    public SortedMap<K, V> tailMap(K fromKey) {
        return tailMap(fromKey, true);
    }

    /**
     * Returns a view of the portion of this map whose keys are greater than (or
     * equal to, if inclusive is true) fromKey.
     */
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return subMap(fromKey, inclusive, lastEntry().getKey(), true);
    }

    private class SubTreeMap extends TreeMap<K, V> {
        public SubTreeMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        }
    }

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
        public boolean equals(Object o) {
            if (o != null && o instanceof TreeMapEntry) {
                TreeMapEntry rhs = (TreeMapEntry) o;
                return this.getKey().equals(rhs.getKey()) && this.getValue() == null ? rhs.getValue() == null
                        : this.getValue().equals(rhs.getValue());
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
            if (getChild(child) == null) {
                throw new IllegalArgumentException("Cannot remove child that does not exist.");
            }

            if (child.hasLeft() && child.hasRight()) {
                TreeMapEntry tmp_p = child;
                TreeMapEntry tmp = child.getLeft();
                while (tmp.hasRight()) {
                    tmp_p = tmp;
                    tmp = child.getRight();
                }
                key = tmp.getKey();
                value = tmp.getValue();
                tmp_p.remove(tmp);
            } else if (child.hasLeft()) {
                if (getLeft().equals(child)) {
                    setLeft(child.getLeft());
                } else {
                    setRight(child.getLeft());
                }
                child.setLeft(null);
            } else if (child.hasRight()) {
                if (getLeft().equals(child)) {
                    setLeft(child.getRight());
                } else {
                    setRight(child.getRight());
                }
                child.setRight(null);
            } else {
                if (getLeft().equals(child)) {
                    setLeft(null);
                } else {
                    setRight(null);
                }
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

        protected int compareValue(Object o) {
            return 0;
        }
    }

    @Override
    public Comparator<? super K> comparator() {
        return comparator;
    }
}