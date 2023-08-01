import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;

public class TreeMap<K, V> implements NavigableMap<K, V> {
    private class TreeMapEntry implements Map.Entry<K, V> {
        public TreeMapEntry() {
            
        }

        public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K, V>> comparingByKey() {

        }

        

        
        
        public V getValue() {

            

        public V setValue(V value) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'setValue'");
       

     }
    }

    /**
     * 

        public Map.Entry<K, V> ceilingEntry(K key) {
            return null;
        }

        /**
         * Returns the least key greater than or equal to the given key, or null if
         * there is no such key.
         */

        }
            
        /

         */
         ublic NavigableSet<K> descendingKeySet() {
           
        }
            
        /

         */
         ublic NavigableMap<K, V> descendingMap() {
           
        }
            
        /

         * 
         */
         ublic Map.Entry<K, V> first
           
        }
            
        /

         * 
         */
         ublic Map.Entry<K, V> floorEntry(K key) {
           
        }
            
        /

         * 
         */
         ublic K floorKey(K key)
           
        }
            
        /

         * 
         */
         ublic So
           
        }
            
        /

         * 
         */
         ublic NavigableMap<K, V> headMap(K
           
        }
            
        /

         * 
         */
         ublic Map.Entry<K, V> higherEntry(K key) {
           
        }
            
        /

         * 
         */
         ublic K higherKey
           
        }
            
        /

         * 
         */
         ublic Map.Entry<K, V> lastE
           
        }
            
        /

         * 
         */
         ublic Map.Entry<K, V> lowerEntry(K key) {
           
        }
            
        /

         * 
         */
         ublic K lowerKey(
           
        }
            
        /

         */
         ublic NavigableSet<K> navigableKeySet() {
           
        }
            
        /

         * 
         */
         ublic Map.Entry<K, V> pollFirstEntr
           
        }
            
        /

         * 
         */
         ublic Map.Entry<K, V> pollLastEntry() {
           
        }
            
        /

         * 
         */
         ublic Na
           
        }
            
        /

         * 
         */
         ublic SortedMap<K, V> subMap(K fr
           
        }
            
        /

         * 
         */
         ublic SortedMap<K, 
           
        }
            
        /

         * 
         */
         ublic NavigableMap<K, V> tailMap(K fromKey
           
        }
            
        p

            throw new UnsupportedOperationException
            
            
        p

        }
            
        public K lastKey() {
        return lastEntry().getKey();
    }

    public Set<K> keySet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keySet'");
    }

        public Collection<V> values() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'values'");
        }

    public Set<Entry<K, V>> en

        }

    p

    

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsKey'");
    }


    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsValue'");
    }

    public V get(Object key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    public V put(K key, V value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }

    public V remove(Object key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putAll'");
    }

    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

}
