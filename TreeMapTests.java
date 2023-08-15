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

        System.out.println("Total tests: " + numTests);
        System.out.println("Failed tests: " + failedTests);
    }

    private static void printTest(Object expected, Object actual, String test) {
        if (!expected.equals(actual)) {
            if (VERBOSE) {
                System.out.println("FAILED! " + test + " test.");
                System.out.println("\texpected: " + expected.toString());
                System.out.println("\tactual: " + actual.toString());
            }
            failedTests++;
        }
        numTests++;
    }

    /**
     * Tests for the contains methods, including:
     * containsKey(Object o)
     * containsValue(Object o)
     */
    public static void containsTests() {
        Integer[] arr0 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
        String[] arr1 = { "!", "@", "#", "$", "%", "^", "&", "*", "(", ")" };

        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 0; i < arr0.length; i++) {
            map.put(arr0[i], arr1[i]);
        }

        String expected = "" + true;
        String actual = "";
        for (int i = 0; i < arr0.length; i++) {
            actual = "" + map.containsKey(arr0[i]);
            printTest(expected, actual, "containsKey(Object key)");

            actual = "" + map.containsValue(arr1[i]);
            printTest(expected, actual, "containsValue(Object value)");

            actual = "" + map.get(arr0[i]).equals(arr1[i]);
            printTest(expected, actual, "get(Object key)");
        }

        expected = "" + false;
        actual = "" + map.containsKey(-19999);
        printTest(expected, actual, "containsKey(Object key)");

        actual = "" + map.containsValue("Teeth");
        printTest(expected, actual, "containsValue(Object value)");
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
        int[] arr0 = { 128, 64, 96, 32, 8, 16, 256, 512, 384, 192 };
        boolean[] arr1 = { true, true, false, true, true, true, true, true, false, false };

        TreeMap<Integer, Boolean> tree = new TreeMap<>();
        for (int i = 0; i < arr0.length; i++) {
            tree.put(arr0[i], arr1[i]);
        }

        String expected = "8";
        String actual = "" + tree.firstEntry();
        printTest(expected, actual, "firstEntry()");

        expected = "512";
        actual = tree.lastEntry() == null ? "null" : "" + tree.lastEntry().getKey();
        printTest(expected, actual, "lastEntry()");

        expected = "128";
        actual = tree.ceilingEntry(128) == null ? "null" : "" + tree.ceilingEntry(128).getKey();
        printTest(expected, actual, "ceilingEntry(K key)");
        actual = tree.floorEntry(128) == null ? "null" : "" + tree.floorEntry(128).getKey();
        printTest(expected, actual, "floorEntry(K key)");
        expected = "192";
        actual = tree.higherEntry(128) == null ? "null" : "" + tree.higherEntry(128).getKey();
        printTest(expected, actual, "higherEntry(K key)");
        actual = tree.ceilingEntry(129) == null ? "null" : "" + tree.ceilingEntry(129).getKey();
        printTest(expected, actual, "ceilingEntry(K key)");
        expected = "96";
        actual = tree.lowerEntry(128) == null ? "null" : "" + tree.lowerEntry(128).getKey();
        printTest(expected, actual, "lowerEntry(K key)");
        actual = tree.floorEntry(128) == null ? "null" : "" + tree.floorEntry(128).getKey();
        printTest(expected, actual, "floorEntry(K key)");
    }

    /**
     * Tests for the key accessors, including:
     * ceilingKey(K key)
     * firstKey()
     * floorKey(K key)
     * higherKey(K key)
     * lastKey()
     */
    private static void getKeyTests() {
        int[] arr0 = { 128, 64, 96, 32, 8, 16, 256, 512, 384, 192 };
        boolean[] arr1 = { true, true, false, true, true, true, true, true, false, false };

        TreeMap<Integer, Boolean> tree = new TreeMap<>();
        for (int i = 0; i < arr0.length; i++) {
            tree.put(arr0[i], arr1[i]);
        }

        String expected = "8";
        String actual = "" + tree.firstKey();
        printTest(expected, actual, "firstKey()");

        expected = "512";
        actual = "" + tree.lastKey();
        printTest(expected, actual, "lastKey()");

        expected = "128";
        actual = "" + tree.ceilingKey(128);
        printTest(expected, actual, "ceilingKey(K key)");
        actual = "" + tree.floorKey(128);
        printTest(expected, actual, "floorKey(K key)");
        expected = "192";
        actual = "" + tree.higherKey(128);
        printTest(expected, actual, "higherKey(K key)");
        actual = "" + tree.ceilingKey(129);
        printTest(expected, actual, "ceilingKey(K key)");
        expected = "96";
        actual = "" + tree.lowerKey(128);
        printTest(expected, actual, "lowerKey(K key)");
        actual = "" + tree.floorKey(128);
        printTest(expected, actual, "floorKey(K key)");
    }

    /**
     * Tests for the value accessors, including:
     * get(Object key)
     */
    private static void getValueTests() {
        String[] arr0 = { "Bart", "Banana", "Coconut Creme Pie", "Advent", "Green Guitar", "Zzyzx",
                "Spanish Spaghetti" };
        double[] arr1 = { 4.0, 2.2, 3.1415, 12.25, 6.669, 10.5, 3.45 };

        TreeMap<String, Double> tree = new TreeMap<>();
        for (int i = 0; i < arr0.length; i++) {
            tree.put(arr0[i], arr1[i]);
        }

        for (int i = 0; i < arr0.length; i++) {
            String expected = "" + arr1[i];
            String actual = "" + tree.get(arr0[i]);
            printTest(expected, actual, "get(Object key)");
        }
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
     * Tests for the put methods, including:
     * put(K key, V value)
     * putAll(Map<? extends K, ? extends V> m)
     */
    private static void putTests() {
        String[] arr0 = { "Bart", "Banana", "Coconut Creme Pie", "Advent", "Green Guitar", "Zzyzx",
                "Spanish Spaghetti" };
        double[] arr1 = { 4.0, 2.2, 3.1415, 12.25, 6.669, 10.5, 3.45 };

        TreeMap<String, Double> putAll = new TreeMap<>();
        for (int i = 0; i < arr0.length; i++) {
            putAll.put(arr0[i], arr1[i]);
        }

        TreeMap<String, Double> tree = new TreeMap<>();
        tree.putAll(putAll);
        for (int i = 0; i < arr0.length; i++) {
            String expected = "" + arr1[i];
            String actual = "" + tree.get(arr0[i]);
            printTest(expected, actual, "putAll(Object key)");
        }

        tree.put(arr0[2], 0.0);
        String expected = "" + 0.0;
        String actual = "" + tree.get(arr0[2]);
        printTest(expected, actual, "put(Object key)");
    }

    /**
     * Tests for the removal methods, including:
     * clear()
     * remove(Object key)
     * pollFirstEntry()
     * pollLastEntry()
     */
    private static void removeTests() {
        int[] arr0 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
        String[] arr1 = { "!", "@", "#", "$", "%", "^", "&", "*", "(", ")" };

        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 0; i < arr0.length; i++) {
            map.put(arr0[i], arr1[i]);
        }

        // clear

        TreeMap<Integer, String> clone_clear = new TreeMap<>(map);
        String expected = "" + false;
        String actual = "" + clone_clear.isEmpty();
        printTest(expected, actual, "isEmpty(Object key)");
        clone_clear.clear();
        expected = "" + true;
        actual = "" + clone_clear.isEmpty();
        printTest(expected, actual, "clear()");

        // pollFirstEntry

        TreeMap<Integer, String> clone_poll = new TreeMap<>(map);
        TreeMap<Integer, String> clone_poll2 = new TreeMap<>(map);
        TreeMap<Integer, String> clone_remove = new TreeMap<>(map);
        expected = "" + true;
        actual = "" + clone_poll.containsKey(map.firstKey());
        printTest(expected, actual, "containsKey(Object key)");
        actual = "" + clone_remove.containsKey(map.firstKey());
        printTest(expected, actual, "containsKey(Object key)");

        expected = "" + map.firstKey();
        actual = "" + clone_poll.pollFirstEntry().getKey();
        printTest(expected, actual, "pollFirstEntry()");
        expected = "" + map.get(map.firstKey());
        actual = "" + clone_remove.remove(map.firstKey());
        printTest(expected, actual, "remove(Object key)");

        expected = "" + false;
        actual = "" + clone_poll.containsKey(map.firstKey());
        printTest(expected, actual, "pollFirstEntry()");
        actual = "" + clone_remove.containsKey(map.firstKey());
        printTest(expected, actual, "remove(Object key)");

        // pollLastEntry

        expected = "" + true;
        actual = "" + clone_poll2.containsKey(map.lastKey());
        printTest(expected, actual, "pollLastEntry()");
        actual = "" + clone_remove.containsKey(map.lastKey());
        printTest(expected, actual, "remove(Object key)");

        clone_poll2.pollLastEntry().getKey().equals(map.lastKey());
        clone_remove.remove(map.lastKey());

        expected = "" + false;
        actual = "" + clone_poll2.containsKey(map.lastKey());
        printTest(expected, actual, "pollLastEntry()");
        actual = "" + clone_remove.containsKey(map.lastKey());
        printTest(expected, actual, "remove(Object key)");
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
        String[] arr0 = { "Bart", "Banana", "Coconut Creme Pie", "Advent", "Green Guitar", "Zzyzx",
                "Spanish Spaghetti" };
        double[] arr1 = { 4.0, 2.2, 3.1415, 12.25, 6.669, 10.5, 3.45 };

        TreeMap<String, Double> tree = new TreeMap<>();
        String expected = "" + true;
        String actual = "" + tree.isEmpty();
        printTest(expected, actual, "isEmpty()");
        for (int i = 0; i < arr0.length; i++) {
            tree.put(arr0[i], arr1[i]);
            expected = "" + (i + 1);
            actual = "" + tree.size();
            printTest(expected, actual, "size()");
        }

        expected = "" + false;
        actual = "" + tree.isEmpty();
        printTest(expected, actual, "isEmpty()");

        tree.clear();

        expected = "" + true;
        actual = "" + tree.isEmpty();
        printTest(expected, actual, "isEmpty()");
    }
}