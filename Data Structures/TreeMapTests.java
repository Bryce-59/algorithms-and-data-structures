import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;

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

        // main tests
        // sub tests
        TreeMap<Integer, String> mapIS = new TreeMap<>();
        containsTests(mapIS, "TreeMap"); // contains
        NavigableMap<Integer, String> smapIS = (new TreeMap<Integer, String>()).subMap(mapIS.firstKey(), true,
                mapIS.lastKey(), true);
        containsTests(smapIS, "SubMap"); // contains -- submap

        getTests(); // get entry, get key

        TreeMap<String, Double> mapSD1 = new TreeMap<>();
        getValueTests(mapSD1, "TreeMap"); // get value
        NavigableMap<String, Double> smapSD1 = (new TreeMap<String, Double>()).subMap(mapSD1.firstKey(), true,
                mapSD1.lastKey(), true);
        getValueTests(smapSD1, "SubMap"); // get value -- submap

        mapSD1 = new TreeMap<>();
        TreeMap<String, Double> mapSD2 = new TreeMap<>();
        putTests(mapSD1, mapSD2, "TreeMap"); // put
        smapSD1 = (new TreeMap<String, Double>()).subMap(mapSD1.firstKey(), true, mapSD1.lastKey(), true);
        NavigableMap<String, Double> smapSD2 = (new TreeMap<String, Double>()).subMap(mapSD1.firstKey(), true,
                mapSD1.lastKey(), true);
        putTests(smapSD1, smapSD2, "SubMap"); // put -- submap

        setTests();
        removeTests(); // clear, remove

        mapSD1 = new TreeMap<String, Double>();
        sizeTests(mapSD1, "TreeMap"); // isEmpty, size
        smapSD1 = (new TreeMap<String, Double>()).subMap(mapSD1.firstKey(), true, mapSD1.lastKey(), true);
        sizeTests(smapSD1, "SubMap"); // isEmpty, size -- submap

        redBlackTests();

        // descending tests
        NavigableMap<Integer, String> dmapIS = (new TreeMap<Integer, String>()).descendingMap();
        containsTests(dmapIS, "DescendingTreeMap"); // contains
        NavigableMap<Integer, String> dsmapIS = ((new TreeMap<Integer, String>()).descendingMap()).subMap(
                dmapIS.firstKey(), true,
                dmapIS.lastKey(), true);
        containsTests(dsmapIS, "DescendingSubMap"); // contains -- submap

        getTestsDescending(); // get entry, get key

        NavigableMap<String, Double> dmapSD1 = (new TreeMap<String, Double>()).descendingMap();
        getValueTests(dmapSD1, "DescendingTreeMap"); // get value
        NavigableMap<String, Double> dsmapSD1 = ((new TreeMap<String, Double>()).descendingMap()).subMap(
                dmapSD1.firstKey(), true,
                dmapSD1.lastKey(), true);
        getValueTests(dsmapSD1, "DescendingSubMap"); // get value -- submap

        dmapSD1 = (new TreeMap<String, Double>()).descendingMap();
        NavigableMap<String, Double> dmapSD2 = (new TreeMap<String, Double>()).descendingMap();
        putTests(dmapSD1, dmapSD2, "DescendingTreeMap"); // put
        NavigableMap<String, Double> dsmapSD2 = ((new TreeMap<String, Double>()).descendingMap()).subMap(
                dmapSD1.firstKey(), true,
                dmapSD1.lastKey(), true);
        putTests(dsmapSD1, dsmapSD2, "DescendingSubMap"); // put -- submap

        removeTestsDescending(); // clear, remove

        dmapSD1 = (new TreeMap<String, Double>()).descendingMap();
        sizeTests(dmapSD1, "DescendingTreeMap"); // isEmpty, size
        dsmapSD1 = ((new TreeMap<String, Double>()).descendingMap()).subMap(dmapSD1.firstKey(), true, dmapSD1.lastKey(),
                true);
        sizeTests(dsmapSD1, "DescendingSubMap"); // isEmpty, size -- submap

        // comparator tests
        // null tests
        // sub tests

        System.out.println("Total tests: " + numTests);
        System.out.println("Failed tests: " + failedTests);
    }

    /**
     * Record the result of the test.
     * 
     * @param expected the expected result
     * @param actual   the actual result
     * @param test     a description of the test
     */
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
    public static void containsTests(NavigableMap<Integer, String> map, String type) {
        Integer[] arr0 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
        String[] arr1 = { "!", "@", "#", "$", "%", "^", "&", "*", "(", ")" };

        for (int i = 0; i < arr0.length; i++) {
            map.put(arr0[i], arr1[i]);
        }

        String expected = "" + true;
        String actual = "";
        for (int i = 0; i < arr0.length; i++) {
            actual = "" + map.containsKey(arr0[i]);
            printTest(expected, actual, type + "containsKey(Object key)");

            actual = "" + map.containsValue(arr1[i]);
            printTest(expected, actual, type + "containsValue(Object value)");

            actual = "" + map.get(arr0[i]).equals(arr1[i]);
            printTest(expected, actual, type + "get(Object key)");
        }

        expected = "" + false;
        actual = "" + map.containsKey(-19999);
        printTest(expected, actual, type + "containsKey(Object key)");

        actual = "" + map.containsValue("Teeth");
        printTest(expected, actual, type + "containsValue(Object value)");
    }

    /**
     * Tests for the accessor methods, including:
     * ceilingEntry()
     * ceilingKey()
     * firstEntry()
     * firstKey()
     * floorEntry()
     * floorKey()
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
    }

    /**
     * Tests for the accessor methods for a DescendingTreeMap, including:
     * ceilingEntry()
     * ceilingKey()
     * firstEntry()
     * firstKey()
     * floorEntry()
     * floorKey()
     * higherEntry()
     * higherKey()
     * lastEntry()
     * lastKey()
     * lowerEntry()
     * lowerKey()
     */
    private static void getTestsDescending() {
        getEntryTestsDescending();
        getKeyTestsDescending();
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
        String actual = tree.firstEntry() == null ? "null" : "" + tree.firstEntry().getKey();
        printTest(expected, actual, "TreeMap firstEntry()");

        expected = "512";
        actual = tree.lastEntry() == null ? "null" : "" + tree.lastEntry().getKey();
        printTest(expected, actual, "TreeMap lastEntry()");

        expected = "128";
        actual = tree.ceilingEntry(128) == null ? "null" : "" + tree.ceilingEntry(128).getKey();
        printTest(expected, actual, "TreeMap ceilingEntry(K key)");
        actual = tree.floorEntry(128) == null ? "null" : "" + tree.floorEntry(128).getKey();
        printTest(expected, actual, "TreeMap floorEntry(K key)");
        expected = "192";
        actual = tree.higherEntry(128) == null ? "null" : "" + tree.higherEntry(128).getKey();
        printTest(expected, actual, "TreeMap higherEntry(K key)");
        actual = tree.ceilingEntry(129) == null ? "null" : "" + tree.ceilingEntry(129).getKey();
        printTest(expected, actual, "TreeMap ceilingEntry(K key)");
        expected = "96";
        actual = tree.lowerEntry(128) == null ? "null" : "" + tree.lowerEntry(128).getKey();
        printTest(expected, actual, "TreeMap lowerEntry(K key)");
        actual = tree.floorEntry(127) == null ? "null" : "" + tree.floorEntry(127).getKey();
        printTest(expected, actual, "TreeMap floorEntry(K key)");
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
        printTest(expected, actual, "TreeMap firstKey()");

        expected = "512";
        actual = "" + tree.lastKey();
        printTest(expected, actual, "TreeMap lastKey()");

        expected = "128";
        actual = "" + tree.ceilingKey(128);
        printTest(expected, actual, "TreeMap ceilingKey(K key)");
        actual = "" + tree.floorKey(128);
        printTest(expected, actual, "TreeMap floorKey(K key)");
        expected = "192";
        actual = "" + tree.higherKey(128);
        printTest(expected, actual, "TreeMap higherKey(K key)");
        actual = "" + tree.ceilingKey(129);
        printTest(expected, actual, "TreeMap ceilingKey(K key)");
        expected = "96";
        actual = "" + tree.lowerKey(128);
        printTest(expected, actual, "TreeMap lowerKey(K key)");
        actual = "" + tree.floorKey(127);
        printTest(expected, actual, "TreeMap floorKey(K key)");
    }

    /**
     * Tests for the entry accessors for the DescendingTreeMap, including:
     * ceilingEntry()
     * firstEntry()
     * floorEntry()
     * higherEntry()
     * lastEntry()
     */
    private static void getEntryTestsDescending() {
        int[] arr0 = { 128, 64, 96, 32, 8, 16, 256, 512, 384, 192 };
        boolean[] arr1 = { true, true, false, true, true, true, true, true, false, false };

        NavigableMap<Integer, Boolean> tree = (new TreeMap<Integer, Boolean>()).descendingMap();
        for (int i = 0; i < arr0.length; i++) {
            tree.put(arr0[i], arr1[i]);
        }

        String expected = "8";
        String actual = tree.lastEntry() == null ? "null" : "" + tree.lastEntry().getKey();
        printTest(expected, actual, "DescedingTreeMap lastEntry()");

        expected = "512";
        actual = tree.firstEntry() == null ? "null" : "" + tree.firstEntry().getKey();
        printTest(expected, actual, "DescedingTreeMap firstEntry()");

        expected = "128";
        actual = tree.floorEntry(128) == null ? "null" : "" + tree.floorEntry(128).getKey();
        printTest(expected, actual, "DescedingTreeMap floorEntry(K key)");
        actual = tree.ceilingEntry(128) == null ? "null" : "" + tree.ceilingEntry(128).getKey();
        printTest(expected, actual, "DescedingTreeMap ceilingEntry(K key)");
        expected = "192";
        actual = tree.lowerEntry(128) == null ? "null" : "" + tree.lowerEntry(128).getKey();
        printTest(expected, actual, "DescedingTreeMap lowerEntry(K key)");
        actual = tree.floorEntry(129) == null ? "null" : "" + tree.floorEntry(129).getKey();
        printTest(expected, actual, "DescedingTreeMap floorEntry(K key)");
        expected = "96";
        actual = tree.higherEntry(128) == null ? "null" : "" + tree.higherEntry(128).getKey();
        printTest(expected, actual, "DescedingTreeMap higherEntry(K key)");
        actual = tree.ceilingEntry(127) == null ? "null" : "" + tree.ceilingEntry(127).getKey();
        printTest(expected, actual, "DescedingTreeMap ceilingEntry(K key)");
    }

    /**
     * Tests for the key accessors of a DescendingTreeMap, including:
     * ceilingKey(K key)
     * firstKey()
     * floorKey(K key)
     * higherKey(K key)
     * lastKey()
     */
    private static void getKeyTestsDescending() {
        int[] arr0 = { 128, 64, 96, 32, 8, 16, 256, 512, 384, 192 };
        boolean[] arr1 = { true, true, false, true, true, true, true, true, false, false };

        NavigableMap<Integer, Boolean> tree = (new TreeMap<Integer, Boolean>()).descendingMap();
        for (int i = 0; i < arr0.length; i++) {
            tree.put(arr0[i], arr1[i]);
        }

        String expected = "8";
        String actual = "" + tree.lastKey();
        printTest(expected, actual, "DescedingTreeMap lastEntry()");

        expected = "512";
        actual = "" + tree.firstKey();
        printTest(expected, actual, "DescedingTreeMap firstEntry()");

        expected = "128";
        actual = "" + tree.floorKey(128);
        printTest(expected, actual, "DescedingTreeMap floorEntry(K key)");
        actual = "" + tree.ceilingKey(128);
        printTest(expected, actual, "DescedingTreeMap ceilingEntry(K key)");
        expected = "192";
        actual = "" + tree.lowerKey(128);
        printTest(expected, actual, "DescedingTreeMap lowerEntry(K key)");
        actual = "" + tree.floorKey(129);
        printTest(expected, actual, "DescedingTreeMap floorEntry(K key)");
        expected = "96";
        actual = "" + tree.higherKey(128);
        printTest(expected, actual, "DescedingTreeMap higherEntry(K key)");
        actual = "" + tree.ceilingKey(127);
        printTest(expected, actual, "DescedingTreeMap ceilingEntry(K key)");
    }

    /**
     * Tests for the value accessors, including:
     * get(Object key)
     */
    private static void getValueTests(NavigableMap<String, Double> map, String type) {
        String[] arr0 = { "Bart", "Banana", "Coconut Creme Pie", "Advent", "Green Guitar", "Zzyzx",
                "Spanish Spaghetti" };
        double[] arr1 = { 4.0, 2.2, 3.1415, 12.25, 6.669, 10.5, 3.45 };

        for (int i = 0; i < arr0.length; i++) {
            map.put(arr0[i], arr1[i]);
        }

        for (int i = 0; i < arr0.length; i++) {
            String expected = "" + arr1[i];
            String actual = "" + map.get(arr0[i]);
            printTest(expected, actual, type + " get(Object key)");
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
    private static void subMapTests() {
        // TODO
    }

    /**
     * Tests for the put methods, including:
     * put(K key, V value)
     * putAll(Map<? extends K, ? extends V> m)
     */
    private static void putTests(NavigableMap<String, Double> putAll, NavigableMap<String, Double> reputAll,
            String type) {
        String[] arr0 = { "Bart", "Banana", "Coconut Creme Pie", "Advent", "Green Guitar", "Zzyzx",
                "Spanish Spaghetti" };
        double[] arr1 = { 4.0, 2.2, 3.1415, 12.25, 6.669, 10.5, 3.45 };

        for (int i = 0; i < arr0.length; i++) {
            putAll.put(arr0[i], arr1[i]);
        }

        reputAll.putAll(putAll);
        for (int i = 0; i < arr0.length; i++) {
            String expected = "" + arr1[i];
            String actual = "" + reputAll.get(arr0[i]);
            printTest(expected, actual, type + "putAll(Object key)");
        }

        reputAll.put(arr0[2], 0.0);
        String expected = "" + 0.0;
        String actual = "" + reputAll.get(arr0[2]);
        printTest(expected, actual, type + "put(Object key)");
    }

    /**
     * Tests to make sure the Red-Black tree properties are maintained.
     */
    private static void redBlackTests() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < 999; i++) {
            map.put(10 * i % 999, i * i);
            try {
                map.rebalanceVerify();
            } catch (Exception e) {
                printTest("true", "false", "Red-Black Properties -- Insertion");
            }
        }

        for (int i = 0; i < 100; i++) {
            map.remove(10 * i % 999);
            try {
                map.rebalanceVerify();
            } catch (Exception e) {
                printTest("false", "true", "Red-Black Properties -- Removal");
            }
        }
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
        printTest(expected, actual, "TreeMap isEmpty(Object key)");
        clone_clear.clear();
        expected = "" + true;
        actual = "" + clone_clear.isEmpty();
        printTest(expected, actual, "TreeMap clear()");

        // pollFirstEntry

        TreeMap<Integer, String> clone_poll = new TreeMap<>(map);
        TreeMap<Integer, String> clone_poll2 = new TreeMap<>(map);
        TreeMap<Integer, String> clone_remove = new TreeMap<>(map);
        expected = "" + true;
        actual = "" + clone_poll.containsKey(map.firstKey());
        printTest(expected, actual, "TreeMap containsKey(Object key)");
        actual = "" + clone_remove.containsKey(map.firstKey());
        printTest(expected, actual, "TreeMap containsKey(Object key)");

        expected = "" + map.firstKey();
        actual = "" + clone_poll.pollFirstEntry().getKey();
        printTest(expected, actual, "TreeMap pollFirstEntry()");
        expected = "" + map.get(map.firstKey());
        actual = "" + clone_remove.remove(map.firstKey());
        printTest(expected, actual, "TreeMap remove(Object key)");

        expected = "" + false;
        actual = "" + clone_poll.containsKey(map.firstKey());
        printTest(expected, actual, "TreeMap pollFirstEntry()");
        actual = "" + clone_remove.containsKey(map.firstKey());
        printTest(expected, actual, "TreeMap remove(Object key)");

        // pollLastEntry

        expected = "" + true;
        actual = "" + clone_poll2.containsKey(map.lastKey());
        printTest(expected, actual, "TreeMap pollLastEntry()");
        actual = "" + clone_remove.containsKey(map.lastKey());
        printTest(expected, actual, "TreeMap remove(Object key)");

        clone_poll2.pollLastEntry().getKey().equals(map.lastKey());
        clone_remove.remove(map.lastKey());

        expected = "" + false;
        actual = "" + clone_poll2.containsKey(map.lastKey());
        printTest(expected, actual, "TreeMap pollLastEntry()");
        actual = "" + clone_remove.containsKey(map.lastKey());
        printTest(expected, actual, "TreeMap remove(Object key)");
    }

    /**
     * Tests for the DescendingTreeSet removal methods, including:
     * clear()
     * remove(Object key)
     * pollFirstEntry()
     * pollLastEntry()
     */
    private static void removeTestsDescending() {
        int[] arr0 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
        String[] arr1 = { "!", "@", "#", "$", "%", "^", "&", "*", "(", ")" };

        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 0; i < arr0.length; i++) {
            map.put(arr0[i], arr1[i]);
        }

        // clear

        NavigableMap<Integer, String> clone_clear = (new TreeMap<>(map)).descendingMap();
        String expected = "" + false;
        String actual = "" + clone_clear.isEmpty();
        printTest(expected, actual, "DescendingTreeMap isEmpty(Object key)");
        clone_clear.clear();
        expected = "" + true;
        actual = "" + clone_clear.isEmpty();
        printTest(expected, actual, "DescendingTreeMap clear()");

        // pollFirstEntry

        NavigableMap<Integer, String> clone_poll = (new TreeMap<>(map)).descendingMap();
        NavigableMap<Integer, String> clone_poll2 = (new TreeMap<>(map)).descendingMap();
        NavigableMap<Integer, String> clone_remove = (new TreeMap<>(map)).descendingMap();
        expected = "" + true;
        actual = "" + clone_poll.containsKey(map.lastKey());
        printTest(expected, actual, "DescendingTreeMap containsKey(Object key)");
        actual = "" + clone_remove.containsKey(map.lastKey());
        printTest(expected, actual, "DescendingTreeMap containsKey(Object key)");

        expected = "" + map.lastKey();
        actual = "" + clone_poll.pollFirstEntry().getKey();
        printTest(expected, actual, "DescendingTreeMap pollFirstEntry()");
        expected = "" + map.get(map.lastKey());
        actual = "" + clone_remove.remove(map.lastKey());
        printTest(expected, actual, "DescendingTreeMap remove(Object key)");

        expected = "" + false;
        actual = "" + clone_poll.containsKey(map.lastKey());
        printTest(expected, actual, "DescendingTreeMap pollFirstEntry()");
        actual = "" + clone_remove.containsKey(map.lastKey());
        printTest(expected, actual, "DescendingTreeMap remove(Object key)");

        // pollLastEntry

        expected = "" + true;
        actual = "" + clone_poll2.containsKey(map.firstKey());
        printTest(expected, actual, "DescendingTreeMap pollLastEntry()");
        actual = "" + clone_remove.containsKey(map.firstKey());
        printTest(expected, actual, "DescendingTreeMap remove(Object key)");

        clone_poll2.pollLastEntry().getKey().equals(map.firstKey());
        clone_remove.remove(map.firstKey());

        expected = "" + false;
        actual = "" + clone_poll2.containsKey(map.firstKey());
        printTest(expected, actual, "DescendingTreeMap pollLastEntry()");
        actual = "" + clone_remove.containsKey(map.firstKey());
        printTest(expected, actual, "DescendingTreeMap remove(Object key)");
    }

    /**
     * Tests for the set view methods for a map, including:
     * descendingKeySet()
     * entrySet()
     * keySet()
     * navigableKeySet()
     * values()
     */
    private static void setTests() {
        String[] arr0 = { "Bart", "Banana", "Coconut Creme Pie", "Advent", "Green Guitar", "Zzyzx",
                "Spanish Spaghetti" };
        Double[] arr1 = { 4.0, 2.2, 3.1415, 12.25, 6.669, 10.5, 3.45 };

        NavigableMap<String, Double> map = new TreeMap<String, Double>();
        NavigableMap<String, Double> dmap = (new TreeMap<String, Double>()).descendingMap();
        for (int i = 0; i < arr0.length; i++) {
            map.put(arr0[i], arr1[i]);
            dmap.put(arr0[i], arr1[i]);
        }

        NavigableMap<String, Double> smap = new TreeMap<String, Double>().subMap(map.firstKey(), true, map.lastKey(),
                true);
        smap.putAll(map);

        List<String> l = Arrays.asList(arr0);
        Set<String> keyset = new TreeSet<>();
        keyset.addAll(l);

        Collections.reverse(l);
        Set<String> dkeyset = new TreeSet<>();
        dkeyset.addAll(l);

        Set<Double> valueSet = new TreeSet<>();
        valueSet.addAll(Arrays.asList(arr1));

        String expected = "true";
        String actual = "" + keyset.equals(map.keySet());
        printTest(expected, actual, "TreeMap keySet()");
        actual = "" + keyset.equals(map.descendingKeySet());
        printTest(expected, actual, "TreeMap descendingKeySet()");
        actual = "" + map.navigableKeySet().equals(map.keySet());
        printTest(expected, actual, "TreeMap navigableKeySet()");
        actual = "" + map.navigableKeySet().equals(map.descendingKeySet().descendingSet());
        printTest(expected, actual, "TreeMap descendingKeySet()");

        actual = "" + keyset.equals(smap.keySet());
        printTest(expected, actual, "SubTreeMap keySet()");
        actual = "" + keyset.equals(smap.descendingKeySet());
        printTest(expected, actual, "SubTreeMap descendingKeySet()");
        actual = "" + smap.navigableKeySet().equals(map.navigableKeySet());
        printTest(expected, actual, "SubTreeMap navigableKeySet()");
        actual = "" + smap.navigableKeySet().equals(smap.descendingKeySet().descendingSet());
        printTest(expected, actual, "SubTreeMap descendingKeySet()");

        actual = "" + dkeyset.equals(dmap.keySet());
        printTest(expected, actual, "DescendingTreeMap keySet()");
        actual = "" + dkeyset.equals(dmap.descendingKeySet());
        printTest(expected, actual, "DescendingTreeMap descendingKeySet()");
        actual = "" + dmap.navigableKeySet().equals(dmap.keySet());
        printTest(expected, actual, "DescendingTreeMap navigableKeySet()");
        actual = "" + dmap.navigableKeySet().equals(map.descendingKeySet().descendingSet());
        printTest(expected, actual, "DescendingTreeMap descendingKeySet()");

        actual = "" + dmap.navigableKeySet().equals(map.descendingKeySet());
        printTest(expected, actual, "DescendingTreeMap navigableKeySet()");
    }

    /**
     * Tests for the size methods, including:
     * isEmpty()
     * size()
     */
    private static void sizeTests(NavigableMap<String, Double> map, String type) {
        String[] arr0 = { "Bart", "Banana", "Coconut Creme Pie", "Advent", "Green Guitar", "Zzyzx",
                "Spanish Spaghetti" };
        double[] arr1 = { 4.0, 2.2, 3.1415, 12.25, 6.669, 10.5, 3.45 };

        String expected = "" + true;
        String actual = "" + map.isEmpty();
        printTest(expected, actual, type + " isEmpty()");
        for (int i = 0; i < arr0.length; i++) {
            map.put(arr0[i], arr1[i]);
            expected = "" + (i + 1);
            actual = "" + map.size();
            printTest(expected, actual, type + " size()");
        }

        expected = "" + false;
        actual = "" + map.isEmpty();
        printTest(expected, actual, type + " isEmpty()");
    }
}