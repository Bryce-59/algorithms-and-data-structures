/**
 * A Test Harness for the TreeMap class.
 */
public class TreeMapTests {
    static boolean VERBOSE = true;
    static int numTests = 0;
    static int failedTests = 0;

    /**
     * The main method runs the tests.
     * 
     * @param args Not used
     */
    public static void main(String[] args) {
        numTests = 0;
        failedTests = 0;

        containsTests();
        getTests();
        mapTests();
        putTests();
        removeTests();
        setTests();
        sizeTests();
    }

    /**
     * Tests for the contains methods, including:
     * containsKey(Object o)
     * containsValue(Object o)
     */
    public static void containsTests() {
        // TODO
    }

    /**
     * Tests for the accessor methods, including:
     * ceilingEntry()
     * ceilingKey()
     * firstEntry()
     * firstKey()
     * floorEntry()
     * floorKey()
     * get(Object key)
     * higherEntry()
     * higherKey()
     * lastEntry()
     * lastKey()
     * lowerEntry()
     * lowerKey()
     */
    private static void getTests() {
        getEntryTests();
        getKeyTests();
        getValueTests();
    }

    /**
     * Tests for the entry accessors, including:
     * ceilingEntry()
     * firstEntry()
     * floorEntry()
     * higherEntry()
     * lastEntry()
     */
    private static void getEntryTests() {
        // TODO
    }

    /**
     * Tests for the key accessors, including:
     * ceilingKey()
     * firstKey()
     * floorKey()
     * higherKey()
     * lastKey()
     */
    private static void getKeyTests() {
        // TODO
    }

    /**
     * Tests for the value accessors, including:
     * get(Object key)
     */
    private static void getValueTests() {
        // TODO
    }

    /**
     * Tests for the map view methods, including:
     * descendingMap()
     * headMap(K toKey)
     * headMap(K toKey, boolean inclusive)
     * subMap(K fromKey, K toKey)
     * subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive)
     * tailMap(K fromKey)
     * tailMap(K fromKey, boolean inclusive)
     */
    private static void mapTests() {
        // TODO
    }

    /**
     * Tests for the removal methods, including:
     * put(K key, V value)
     * putAll(Map<? extends K, ? extends V> m)
     */
    private static void putTests() {
        // TODO
    }

    /**
     * Tests for the removal methods, including:
     * clear()
     * remove(Object key)
     * pollFirstEntry()
     * pollLastEntry()
     */
    private static void removeTests() {
        // TODO
    }

    /**
     * Tests for the set view methods, including:
     * descendingKeySet()
     * entrySet()
     * keySet()
     * navigableKeySet()
     * values()
     */
    private static void setTests() {
        // TODO
    }

    /**
     * Tests for the size methods, including:
     * isEmpty()
     * size()
     */
    private static void sizeTests() {
        // TODO
    }
}