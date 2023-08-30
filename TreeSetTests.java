import java.util.NavigableSet;

/**
 * A Test Harness for the TreeSet class.
 */
public class TreeSetTests {
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
        TreeSet<Integer> mapIS = new TreeSet<>();
        containsTests(mapIS, "TreeSet"); // contains
        NavigableSet<Integer> smapIS = (new TreeSet<Integer>()).subSet(mapIS.first(),
                true,
                mapIS.last(), true);
        containsTests(smapIS, "SubMap"); // contains -- submap

        getTests(); // get , get key

        TreeSet<String> mapSD1 = new TreeSet<>();
        TreeSet<String> mapSD2 = new TreeSet<>();
        addTests(mapSD1, mapSD2, "TreeSet"); // put
        NavigableSet<String> smapSD1 = (new TreeSet<String>()).subSet(mapSD1.first(), true, mapSD1.last(), true);
        NavigableSet<String> smapSD2 = (new TreeSet<String>()).subSet(mapSD1.first(), true,
                mapSD1.last(), true);
        addTests(smapSD1, smapSD2, "SubMap"); // put -- submap

        removeTests(); // clear, remove

        mapSD1 = new TreeSet<String>();
        sizeTests(mapSD1, "TreeSet"); // isEmpty, size
        smapSD1 = (new TreeSet<String>()).subSet(mapSD1.first(), true, mapSD1.last(),
                true);

        sizeTests(smapSD1, "SubMap"); // isEmpty, size -- submap

        redBlackTests();

        // descending tests
        NavigableSet<Integer> dmapIS = (new TreeSet<Integer>()).descendingSet();
        containsTests(dmapIS, "DescendingTreeSet"); // contains
        NavigableSet<Integer> dsmapIS = ((new TreeSet<Integer>()).descendingSet()).subSet(
                dmapIS.first(), true,
                dmapIS.last(), true);
        containsTests(dsmapIS, "DescendingSubMap"); // contains -- submap

        getTestsDescending(); // get , get key

        NavigableSet<String> dmapSD1 = (new TreeSet<String>()).descendingSet();
        NavigableSet<String> dmapSD2 = (new TreeSet<String>()).descendingSet();
        addTests(dmapSD1, dmapSD2, "DescendingTreeSet"); // put
        NavigableSet<String> dsmapSD1 = ((new TreeSet<String>()).descendingSet()).subSet(
                dmapSD1.first(), true,
                dmapSD1.last(), true);
        NavigableSet<String> dsmapSD2 = ((new TreeSet<String>()).descendingSet()).subSet(
                dmapSD1.first(), true,
                dmapSD1.last(), true);
        addTests(dsmapSD1, dsmapSD2, "DescendingSubMap"); // put -- submap

        removeTestsDescending(); // clear, remove

        dmapSD1 = (new TreeSet<String>()).descendingSet();
        sizeTests(dmapSD1, "DescendingTreeSet"); // isEmpty, size
        dsmapSD1 = ((new TreeSet<String>()).descendingSet()).subSet(dmapSD1.first(),
                true, dmapSD1.last(),
                true);
        sizeTests(dsmapSD1, "DescendingSubMap"); // isEmpty, size -- submap

        // iterator tests

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
    public static void containsTests(NavigableSet<Integer> map, String type) {
        Integer[] arr0 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };

        for (int i = 0; i < arr0.length; i++) {
            map.add(arr0[i]);
        }

        String expected = "" + true;
        String actual = "";
        for (int i = 0; i < arr0.length; i++) {
            actual = "" + map.contains(arr0[i]);
            printTest(expected, actual, type + "containsKey(Object key)");
        }

        expected = "" + false;
        actual = "" + map.contains(-19999);
        printTest(expected, actual, type + "containsKey(Object key)");
    }

    /**
     * Tests for the accessors, including:
     * ceiling()
     * first()
     * floor()
     * higher()
     * last()
     */
    private static void getTests() {
        int[] arr0 = { 128, 64, 96, 32, 8, 16, 256, 512, 384, 192 };

        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 0; i < arr0.length; i++) {
            tree.add(arr0[i]);
        }

        String expected = "8";
        String actual = "" + tree.first();
        printTest(expected, actual, "TreeSet first()");

        expected = "512";
        actual = "" + tree.last();
        printTest(expected, actual, "TreeSet last()");

        expected = "128";
        actual = "" + tree.ceiling(128);
        printTest(expected, actual, "TreeSet ceiling(K key)");
        actual = "" + tree.floor(128);
        printTest(expected, actual, "TreeSet floor(K key)");
        expected = "192";
        actual = "" + tree.higher(128);
        printTest(expected, actual, "TreeSet higher(K key)");
        actual = "" + tree.ceiling(129);
        printTest(expected, actual, "TreeSet ceiling(K key)");
        expected = "96";
        actual = "" + tree.lower(128);
        printTest(expected, actual, "TreeSet lower(K key)");
        actual = "" + tree.floor(127);
        printTest(expected, actual, "TreeSet floor(K key)");
    }

    /**
     * Tests for the accessors for the DescendingTreeSet, including:
     * ceiling()
     * first()
     * floor()
     * higher()
     * last()
     */
    private static void getTestsDescending() {
        int[] arr0 = { 128, 64, 96, 32, 8, 16, 256, 512, 384, 192 };

        NavigableSet<Integer> tree = (new TreeSet<Integer>()).descendingSet();
        for (int i = 0; i < arr0.length; i++) {
            tree.add(arr0[i]);
        }

        String expected = "8";
        String actual = "" + tree.last();
        printTest(expected, actual, "DescendingTreeSet last()");

        expected = "512";
        actual = "" + tree.first();
        printTest(expected, actual, "DescendingTreeSet first()");

        expected = "128";
        actual = "" + tree.ceiling(128);
        printTest(expected, actual, "DescendingTreeSet ceiling(K key)");
        actual = "" + tree.floor(128);
        printTest(expected, actual, "DescendingTreeSet floor(K key)");
        expected = "192";
        actual = "" + tree.lower(128);
        printTest(expected, actual, "DescendingTreeSet lower(K key)");
        actual = "" + tree.floor(129);
        printTest(expected, actual, "DescendingTreeSet floor(K key)");
        expected = "96";
        actual = "" + tree.higher(128);
        printTest(expected, actual, "DescendingTreeSet higher(K key)");
        actual = "" + tree.ceiling(127);
        printTest(expected, actual, "DescendingTreeSet ceiling(K key)");
    }

    /**
     * Tests for the put methods, including:
     * add(E e)
     * addAll(Set<? extends E> m)
     */
    private static void addTests(NavigableSet<String> addAll, NavigableSet<String> readdAll,
            String type) {

        String[] arr0 = { "Bart", "Banana", "Coconut Creme Pie", "Advent", "Green Guitar", "Zzyzx",
                "Spanish Spaghetti" };

        for (int i = 0; i < arr0.length; i++) {
            addAll.add(arr0[i]);
        }

        readdAll.addAll(addAll);
        for (int i = 0; i < arr0.length; i++) {
            String expected = "true";
            String actual = "" + readdAll.contains(arr0[i]);
            printTest(expected, actual, type + " addAll(Object key)");
        }

        String expected = "false";
        String actual = "" + readdAll.add(arr0[2]);
        printTest(expected, actual, type + " add(Object key)");
    }

    /**
     * Tests to make sure the Red-Black tree properties are maintained.
     */
    private static void redBlackTests() {
        TreeSet<Integer> map = new TreeSet<>();
        for (int i = 0; i < 999; i++) {
            map.add(10 * i % 999);
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
     * pollFirst()
     * pollLast()
     */
    private static void removeTests() {
        Character[] arr1 = { '!', '@', '#', '$', '%', '^', '&', '*', '(', ')' };

        TreeSet<Character> map = new TreeSet<>();
        for (int i = 0; i < arr1.length; i++) {
            map.add(arr1[i]);
        }

        // clear

        TreeSet<Character> clone_clear = new TreeSet<>();
        clone_clear.addAll(map);
        String expected = "" + false;
        String actual = "" + clone_clear.isEmpty();
        printTest(expected, actual, "TreeSet isEmpty(Object key)");
        clone_clear.clear();
        expected = "" + true;
        actual = "" + clone_clear.isEmpty();
        printTest(expected, actual, "TreeSet clear()");

        // pollFirst

        TreeSet<Character> clone_poll = new TreeSet<>();
        clone_poll.addAll(map);
        TreeSet<Character> clone_poll2 = new TreeSet<>();
        clone_poll2.addAll(map);
        TreeSet<Character> clone_remove = new TreeSet<>();
        clone_remove.addAll(map);
        expected = "" + true;
        actual = "" + clone_poll.contains(map.first());
        printTest(expected, actual, "TreeSet containsKey(Object key)");
        actual = "" + clone_remove.contains(map.first());
        printTest(expected, actual, "TreeSet containsKey(Object key)");

        expected = "" + map.first();
        actual = "" + clone_poll.pollFirst();
        printTest(expected, actual, "TreeSet pollFirst()");
        expected = "" + true;
        actual = "" + clone_remove.remove(map.first());
        printTest(expected, actual, "TreeSet remove(Object key)");

        expected = "" + false;
        actual = "" + clone_poll.contains(map.first());
        printTest(expected, actual, "TreeSet pollFirst()");
        actual = "" + clone_remove.contains(map.first());
        printTest(expected, actual, "TreeSet remove(Object key)");

        // pollLast

        expected = "" + true;
        actual = "" + clone_poll2.contains(map.last());
        printTest(expected, actual, "TreeSet pollLast()");
        actual = "" + clone_remove.contains(map.last());
        printTest(expected, actual, "TreeSet remove(Object key)");

        clone_poll2.pollLast().equals(map.last());
        clone_remove.remove(map.last());

        expected = "" + false;
        actual = "" + clone_poll2.contains(map.last());
        printTest(expected, actual, "TreeSet pollLast()");
        actual = "" + clone_remove.contains(map.last());
        printTest(expected, actual, "TreeSet remove(Object key)");
    }

    /**
     * Tests for the DescendingTreeSet removal methods, including:
     * clear()
     * remove(Object key)
     * pollFirst()
     * pollLast()
     */
    private static void removeTestsDescending() {
        Character[] arr1 = { '!', '@', '#', '$', '%', '^', '&', '*', '(', ')' };

        TreeSet<Character> map = new TreeSet<>();
        for (int i = 0; i < arr1.length; i++) {
            map.add(arr1[i]);
        }

        // clear

        NavigableSet<Character> clone_clear = (new TreeSet<Character>()).descendingSet();
        clone_clear.addAll(map);
        String expected = "" + false;
        String actual = "" + clone_clear.isEmpty();
        printTest(expected, actual, "DescendingTreeSet isEmpty(Object key)");
        clone_clear.clear();
        expected = "" + true;
        actual = "" + clone_clear.isEmpty();
        printTest(expected, actual, "DescendingTreeSet clear()");

        // pollLast

        NavigableSet<Character> clone_poll = (new TreeSet<Character>()).descendingSet();
        clone_poll.addAll(map);
        NavigableSet<Character> clone_poll2 = (new TreeSet<Character>()).descendingSet();
        clone_poll2.addAll(map);
        NavigableSet<Character> clone_remove = (new TreeSet<Character>()).descendingSet();
        clone_remove.addAll(map);
        expected = "" + true;
        actual = "" + clone_poll.contains(map.first());
        printTest(expected, actual, "DescendingTreeSet containsKey(Object key)");
        actual = "" + clone_remove.contains(map.first());
        printTest(expected, actual, "DescendingTreeSet containsKey(Object key)");

        expected = "" + map.first();
        actual = "" + clone_poll.pollLast();
        printTest(expected, actual, "DescendingTreeSet pollLast()");
        expected = "" + true;
        actual = "" + clone_remove.remove(map.first());
        printTest(expected, actual, "DescendingTreeSet remove(Object key)");

        expected = "" + false;
        actual = "" + clone_poll.contains(map.first());
        printTest(expected, actual, "DescendingTreeSet pollLast()");
        actual = "" + clone_remove.contains(map.first());
        printTest(expected, actual, "DescendingTreeSet remove(Object key)");

        // pollFirst

        expected = "" + true;
        actual = "" + clone_poll2.contains(map.last());
        printTest(expected, actual, "DescendingTreeSet contains(Object key)");
        actual = "" + clone_remove.contains(map.last());
        printTest(expected, actual, "DescendingTreeSet contains(Object key)");

        clone_poll2.pollFirst().equals(map.last());
        clone_remove.remove(map.last());

        expected = "" + false;
        actual = "" + clone_poll2.contains(map.last());
        printTest(expected, actual, "DescendingTreeSet pollFirst()");
        actual = "" + clone_remove.contains(map.last());
        printTest(expected, actual, "DescendingTreeSet remove(Object key)");
    }

    /**
     * Tests for the size methods, including:
     * isEmpty()
     * size()
     */
    private static void sizeTests(NavigableSet<String> map, String type) {
        String[] arr0 = { "Bart", "Banana", "Coconut Creme Pie", "Advent", "Green Guitar", "Zzyzx",
                "Spanish Spaghetti" };

        String expected = "" + true;
        String actual = "" + map.isEmpty();
        printTest(expected, actual, type + " isEmpty()");
        for (int i = 0; i < arr0.length; i++) {
            map.add(arr0[i]);
            expected = "" + (i + 1);
            actual = "" + map.size();
            printTest(expected, actual, type + " size()");
        }

        expected = "" + false;
        actual = "" + map.isEmpty();
        printTest(expected, actual, type + " isEmpty()");
    }
}