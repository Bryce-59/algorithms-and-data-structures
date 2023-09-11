import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Set;

import Stopwatch;

public class DataExperiments {

    public static final int[] SIZES = { 400, 800, 1600, 3200, 6400, 12800, 25600, 51200 };

    public static void main(String[] args) {
        System.out.println("LinkedList");
        LinkedListExperiment();
        
        System.out.println("TreeMap");
        TreeMapExperiment();
        
        System.out.println("TreeSet");
        TreeSetExperiment();
    }
    
    /**
     * Print out the results of the experiment.
     * @param time the map of test -> times
     */
    private static void printTime(Map<String, ArrayList<Double>> time) {
        for (String key : time.keySet()) {
            System.out.print("\t" + key);
        }
        System.out.println();

        for (int i = 0; i < SIZES.length; i++) {
            System.out.print(SIZES[i]);
            for (String key : time.keySet()) {
                System.out.print("\t" + time.get(key).get(i));
            }
            System.out.println();
        }
    }

    /**
     * Add a new time result to the map of results
     * 
     * @param time the map of results
     * @param key the test
     * @param value the time to add
     */
    private static void addTime(Map<String, ArrayList<Double>> time, String key, Double value) {
        ArrayList<Double> val = time.getOrDefault(key, new ArrayList<>());
        val.add(value);
        time.put(key, val);
    }

    /**
     * The ArrayList / Linked List experiment
     */
    public static void LinkedListExperiment() {
        java.util.ArrayList<Integer> expected = new java.util.ArrayList<>();
        LinkedList<Integer> actual = new LinkedList<>();

        /*
        * Java's ArrayList
        */

        listOperations(expected);

        /*
        * My LinkedList
        */

        listOperations(actual);
    }

    /**
     * Helper method for the LinkedListExperiment
     * @param list
     */
    private static void listOperations(List<Integer> list) {
        TreeMap<String, ArrayList<Double>> time = new TreeMap<>();
        for (int k = 0; k < SIZES.length; k++) {
            addTime(time, "Insert First", listInsertFirst(k, list));
            addTime(time, "Insert Back", listInsertBack(k, list));
            addTime(time, "Insert", listInsertArbitrary(k, list));
            addTime(time, "Search by Value", listSearchValue(k, list));
            addTime(time, "Search by Index", listSearchIndex(k, list));
            addTime(time, "Remove by Value", listRemoveValue(k, list));
            addTime(time, "Remove by Index", listRemoveIndex(k, list));
        }
        printTime(time);
    }

    /**
     * Test for the List "Insert First" operation
     */
    private static double listInsertFirst(int k, List<Integer> list) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            int val = (int) (SIZES[k] * Math.random());
            list.add(0, val);
        }
        return s.elapsedTime();
    }

    /**
     * Test for the List "Insert Back" operation
     */
    private static double listInsertBack(int k, List<Integer> list) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            int val = (int) (SIZES[k] * Math.random());
            list.add(list.size(), val);
        }
        return s.elapsedTime();
    }

    /**
     * Test for the List "Insert" operation
     */
    private static double listInsertArbitrary(int k, List<Integer> list) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            int val = (int) (SIZES[k] * Math.random());
            int pos = (int) (list.size() * Math.random());
            list.add(pos, val);
        }
        return s.elapsedTime();
    }

    /**
     * Test for the List "Search by Value" operation
     */
    private static double listSearchValue(int k, List<Integer> list) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            Integer search = (int) (SIZES[k] * Math.random());
            list.contains(search);
        }
        return s.elapsedTime();
    }

    /**
     * Test for the List "Search by Index" operation
     */
    private static double listSearchIndex(int k, List<Integer> list) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            int pos = (int) (list.size() * Math.random());
            list.contains(pos);
        }
        return s.elapsedTime();
    }

    /**
     * Test for the List "Remove by Value" operation
     */
    private static double listRemoveValue(int k, List<Integer> list) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            Integer search = (int) (SIZES[k] * Math.random());
            list.remove(search);
        }
        return s.elapsedTime();
    }

    /**
     * Test for the List "Remove by Index" operation
     */
    private static double listRemoveIndex(int k, List<Integer> list) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            int pos = (int) (list.size() * Math.random());
            list.get(pos);
        }
        return s.elapsedTime();
    }

    /**
     * The TreeMap experiment
     */
    public static void TreeMapExperiment() {
        java.util.TreeMap<Integer, Integer> expected = new java.util.TreeMap<>();
        TreeMap<Integer, Integer> actual = new TreeMap<>();

        /*
        * Java's TreeSet
        */
        
        mapOperations(expected);
        
        /*
        * My TreeSet
        */ 
            
        mapOperations(actual);

    }

    /**
     * Helper method for the TreeMapExperiment
     * @param list
     */
    private static void mapOperations(Map map) {
        TreeMap<String, ArrayList<Double>> time = new TreeMap<>();
        for (int k = 0; k < SIZES.length; k++) {
            addTime(time, "Put", mapPut(k, map));
            addTime(time, "Search", mapSearch(k, map));
            addTime(time, "Remove", mapRemove(k, map));
        }
        printTime(time);
    }

    /**
     * Test for the Map "Put" operation
     */
    private static double mapPut(int k, Map<Integer, Integer> map) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            int key = (int) (SIZES[k] * Math.random());
            int val = (int) (SIZES[k] * Math.random());
            map.put(key, val);
        }
        return s.elapsedTime();
    }

    /**
     * Test for the List "Search" operation
     */
    private static double mapSearch(int k, Map<Integer, Integer> map) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            int key = (int) (SIZES[k] * Math.random());
            map.containsKey(key);
        }
        return s.elapsedTime();
    }

    /**
     * Test for the Map "Remove" operation
     */
    private static double mapRemove(int k, Map<Integer, Integer> map) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            int key = (int) (SIZES[k] * Math.random());
            map.remove(key);
        }
        return s.elapsedTime();
    }

    /**
     * The TreeSet experiment
     */
    public static void TreeSetExperiment() {
        java.util.TreeSet<Integer> expected = new java.util.TreeSet<>();
        TreeSet<Integer> actual = new TreeSet<>();

        /*
        * Java's TreeSet
        */

        setOperations(expected);

        /*
        * My TreeSet
        */   
        
        setOperations(actual);
    }

    /**
     * Helper method for the TreeSetExperiment
     * @param list
     */
    private static void setOperations(Set set) {
        TreeMap<String, ArrayList<Double>> time = new TreeMap<>();
        for (int k = 0; k < SIZES.length; k++) {
            addTime(time, "Put", setPut(k, set));
            addTime(time, "Search", setSearch(k, set));
            addTime(time, "Remove", setRemove(k, set));
        }
        printTime(time);
    }

    /**
     * Test for the Set "Put" operation
     */
    private static double setPut(int k, Set<Integer> set) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            int key = (int) (SIZES[k] * Math.random());
            set.add(key);
        }
        return s.elapsedTime();
    }

    /**
     * Test for the Set "Search" operation
     */
    private static double setSearch(int k, Set<Integer> set) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            int key = (int) (SIZES[k] * Math.random());
            set.contains(key);
        }
        return s.elapsedTime();
    }

    /**
     * Test for the Set "Remove" operation
     */
    private static double setRemove(int k, Set<Integer> set) {
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < SIZES[k]; i++) {
            int key = (int) (SIZES[k] * Math.random());
            set.remove(key);
        }
        return s.elapsedTime();
    }
}
