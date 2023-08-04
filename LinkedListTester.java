import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Test Harness for the LinkedList class.
 */
public class LinkedListTester {
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

        addTests();
        containsTests();
        equalsTests();
        getTests();
        indexOfTests();
        iteratorTests();
        removeTests();
        setTests();
        sizeTests();
        subListTests();
        toArrayTests();
        toStringTests();

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
     * Tests for the additive methods, including:
     * add(E e)
     * add(int index, E element)
     * addAll(Collection<? extends E> c)
     * addAll(int index, Collection<? extends E> c)
     * addFirst(E e)
     * addLast(E e)
     * offer(E e)
     * offerFirst(E e)
     * offerLast(E e)
     * push(E e)
     */
    private static void addTests() {
        addAllTests();
        addFirstTests();
        addLastTests();
        insertTests();
    }

    /**
     * Tests for methods that add to the front, including:
     * addFirst(E e)
     * offerFirst(E e)
     * push(E e)
     */
    private static void addFirstTests() {
        final String[] inputs = new String[] { "Bears", "Tigers", "Lions" };
        for (int i = 0; i < inputs.length; i++) {
            LinkedList<String> list_addFirst = new LinkedList<>();
            LinkedList<String> list_offerFirst = new LinkedList<>();
            LinkedList<String> list_push = new LinkedList<>();
            list_addFirst.addFirst(inputs[i]);
            list_offerFirst.offerFirst(inputs[i]);
            list_push.push(inputs[i]);
            String expected = inputs[i];
            String actual_addFirst = list_addFirst.get(0).toString();
            String actual_offerFirst = list_addFirst.get(0).toString();
            String actual_push = list_addFirst.get(0).toString();

            printTest(expected, actual_addFirst, "addFirst()");
            printTest(expected, actual_offerFirst, "offerFirst()");
            printTest(expected, actual_push, "push()");
        }
    }

    /**
     * Tests for methods that add to the back, including:
     * add(E e)
     * addLast(E e)
     * offer(E e)
     * offerLast(E e)
     */
    private static void addLastTests() {
        final int SIZE = 6;
        final int[] inputs = new int[SIZE];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = (int) (SIZE * Math.random());
        }
        LinkedList<Integer> list_add = new LinkedList<>();
        LinkedList<Integer> list_addLast = new LinkedList<>();
        LinkedList<Integer> list_offer = new LinkedList<>();
        LinkedList<Integer> list_offerLast = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            int[] check = new int[i + 1];
            for (int j = 0; j < check.length; j++) {
                check[j] = inputs[j];
            }

            list_add.add(inputs[i]);
            list_addLast.add(inputs[i]);
            list_offer.add(inputs[i]);
            list_offerLast.add(inputs[i]);
            String expected = Arrays.toString(check);
            String actual_add = list_add.toString();
            String actual_addLast = list_add.toString();
            String actual_offer = list_add.toString();
            String actual_offerLast = list_add.toString();
            printTest(expected, actual_add, "add(E e)");
            printTest(expected, actual_addLast, "addLast(E e)");
            printTest(expected, actual_offer, "offer(E e)");
            printTest(expected, actual_offerLast, "offerLast(E e)");
        }
    }

    /**
     * Tests for methods that add to an arbitrary location in the list, including:
     * add(int index, E element)
     */
    private static void insertTests() {
        final String[] inputs = new String[] { "Red", "Yellow", "Blue" };
        final ArrayList<String> holder = new ArrayList<String>();
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            holder.add(inputs[i]);
            list.add(inputs[i]);
        }

        final String[] inserts = new String[] { "Orange", "Green", "Purple" };
        for (int i = 0; i < inserts.length; i++) {
            list.add(2 * i % list.size(), inserts[i]);
            holder.add(2 * i % holder.size(), inserts[i]);

            String expected = holder.toString();
            String actual = list.toString();
            printTest(expected, actual, "add(int index, E element)");
        }
    }

    /**
     * Tests for methods that add to an arbitrary location in the list, including:
     * addAll(Collection<? extends E> c)
     * addAll(int index, Collection<? extends E> c)
     */
    private static void addAllTests() {
        final int SIZE = 6;
        ArrayList<Integer> inputs = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            inputs.add((int) (SIZE * Math.random()));
        }
        LinkedList<Integer> list = new LinkedList<>();
        list.addAll(inputs);

        String expected = inputs.toString();
        String actual = list.toString();
        printTest(expected, actual, "addAll(Collection<? Extends E> c)");

        ArrayList<Integer> inputs2 = new ArrayList<>();
        inputs2.addAll(inputs);
        inputs2.addAll(1, inputs);
        list.addAll(1, inputs);

        expected = inputs2.toString();
        actual = list.toString();
        printTest(expected, actual, "addAll(int index, Collection<? extends E> c)");
    }

    /**
     * Tests for methods that check if a list has an element, including:
     * contains(Object o)
     * containsAll(Collection<?> c)
     */
    private static void containsTests() {
        String[] inputs = new String[] { "M", "Y", "C", "R", "O", "F", "T" };
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            list.add(inputs[i]);
        }
        for (int i = 0; i < inputs.length; i++) {
            String expected = "" + true;
            String actual = "" + list.contains(inputs[i]);
            printTest(expected, actual, "contains(Object o)");
        }

        String expected = "" + false;
        String actual = "" + list.contains("Q");
        printTest(expected, actual, "contains(Object o)");
        actual = "" + list.contains(2);
        printTest(expected, actual, "contains(Object o)");

        ArrayList<String> inputs2 = new ArrayList<>(Arrays.asList(inputs));

        expected = "" + true;
        actual = "" + list.containsAll(inputs2);
        printTest(expected, actual, "containsAll(Object o)");
        expected = "" + true;
        actual = "" + list.containsAll(inputs2.subList(1, inputs2.size() - 2));
        printTest(expected, actual, "containsAll(Object o)");

        inputs2.add("Ratatouille");
        expected = "" + false;
        actual = "" + list.containsAll(inputs2);
        printTest(expected, actual, "containsAll(Object o)");
    }

    /**
     * Tests for the equals method, including:
     * equals(Object o)
     */
    private static void equalsTests() {
        final int SIZE = (int) (100 * Math.random());
        final int NUM_TRIALS = 3;
        final int[] inputs = new int[SIZE];
        for (int i = 0; i < NUM_TRIALS; i++) {
            LinkedList<Integer> list = new LinkedList<>();
            LinkedList<Integer> list2 = new LinkedList<>();
            for (int j = 0; j < inputs.length; j++) {
                inputs[j] = (int) (SIZE * Math.random());
                list.addLast(inputs[j]);
                list2.addLast(inputs[j]);
            }
            boolean expected = true;
            boolean actual = list.equals(list2);
            printTest(expected, actual, "equals(Object o)");
        }
        LinkedList<Integer> list = new LinkedList<>();
        for (int j = 0; j < inputs.length; j++) {
            inputs[j] = (int) (SIZE * Math.random());
            list.addLast(inputs[j]);
        }
        boolean expected = false;
        boolean actual = list.equals(inputs);
        printTest(expected, actual, "equals(Object o)");
    }

    /**
     * Tests for accessor methods, including:
     * element()
     * get(int index)
     * getFirst()
     * getLast()
     * peek()
     * peekFirst()
     * peekLast()
     */
    private static void getTests() {
        // get(int index)
        final int[] inputs = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            list.add(inputs[i]);
        }
        for (int i = 0; i < inputs.length; i++) {
            String expected = "" + inputs[i];
            String actual = list.get(i).toString();
            printTest(expected, actual, "get(int index)");
        }

        // getFirst()
        // peek()
        // peekFirst()
        // element()

        String expected = "" + inputs[0];
        String actual_get = "" + list.get(0);
        String actual_getFirst = "" + list.getFirst();
        String actual_peek = "" + list.peek();
        String actual_peekFirst = "" + list.peekFirst();
        String actual_element = "" + list.element();
        printTest(expected, actual_get, "get(int index)");
        printTest(expected, actual_getFirst, "getFirst()");
        printTest(expected, actual_peek, "peek()");
        printTest(expected, actual_peekFirst, "peekFirst()");
        printTest(expected, actual_element, "element()");

        // getLast()
        // peekLast()

        expected = "" + inputs[inputs.length - 1];
        String actual_get2 = "" + list.get(list.size() - 1);
        String actual_getLast = "" + list.getLast();
        String actual_peekLast = "" + list.peekLast();
        printTest(expected, actual_get2, "get(int index)");
        printTest(expected, actual_getLast, "getLast()");
        printTest(expected, actual_peekLast, "peekLast()");

        // test functionality with empty lists

        LinkedList<Integer> list2 = new LinkedList<>();
        try {
            list2.element();
            if (VERBOSE) {
                System.out.println("element() should return NoSuchElementException");
            }
            printTest(true, false, "element()");
        } catch (NoSuchElementException e) {
            printTest(true, true, "element()");
        }

        try {
            Integer i = list2.peek();
            printTest(true, i == null, "peek()");
            i = list2.peekLast();
            printTest(true, i == null, "peekLast()");
        } catch (NoSuchElementException e) {
            if (VERBOSE) {
                System.out.println("peek() should not return any errors");
            }
            printTest(true, false, "peek()");
        }

    }

    /**
     * Tests for the indexOf methods, including:
     * indexOf(Object o)
     * lastIndexOf(Object o)
     */
    private static void indexOfTests() {
        // part 1
        String[] inputs = new String[] { "M", "Y", "C", "R", "O", "F", "T" };
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            list.add(inputs[i]);
        }
        for (int i = 0; i < inputs.length; i++) {
            String expected = "" + i;
            String actual_first = "" + list.indexOf(inputs[i]);
            String actual_last = "" + list.lastIndexOf(inputs[i]);
            printTest(expected, actual_first, "indexOf(Object o)");
            printTest(expected, actual_last, "indexOf(Object o)");
        }

        // part 2
        inputs = new String[] { "Apples", "Oranges", "Apples", "Bananas", "Apples", "Oranges" };

        final String[] checks = new String[] { "Apples", "Oranges", "Bananas" };
        final int[] answers_first = new int[] { 0, 1, 3 };
        final int[] answers_last = new int[] { 4, 5, 3 };
        list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            list.add(inputs[i]);
        }

        for (int i = 0; i < checks.length; i++) {
            int expected_first = answers_first[i];
            int expected_last = answers_last[i];
            int actual_first = list.indexOf(checks[i]);
            int actual_last = list.lastIndexOf(checks[i]);
            printTest(expected_first, actual_first, "indexOf(Object o)");
            printTest(expected_last, actual_last, "lastIndexOf(Object o)");
        }
    }

    /**
     * Tests for methods involving iterator classes, including:
     * descendingIterator()
     * listIterator()
     * listIterator(int index)
     * iterator()
     */
    private static void iteratorTests() {
        final int SIZE = 10;
        final int NUM_TRIALS = 3;
        final int[] inputs = new int[SIZE];
        for (int i = 0; i < NUM_TRIALS; i++) {
            LinkedList<Integer> list = new LinkedList<>();
            LinkedList<Integer> list2 = new LinkedList<>();
            for (int j = 0; j < inputs.length; j++) {
                inputs[j] = (int) (SIZE * Math.random());
                list.addLast(inputs[j]);
                list2.addLast(inputs[j]);
            }

            Iterator<Integer> itr = list.iterator();
            ListIterator<Integer> itr_list = list2.listIterator();
            int x = -1;
            int y = -1;
            printTest(itr.hasNext(), true, "iterator.hasNext()");
            printTest(itr_list.hasNext(), true, "listIterator.hasNext()");
            int index = 0;
            while (itr.hasNext() && itr_list.hasNext()) {
                x = itr.next();
                y = itr_list.next();
                itr.remove();
                itr_list.remove();

                int expected = inputs[index];
                int actual = x;
                int actual_list = y;
                printTest(expected, actual, "iterator.next()");
                printTest(expected, actual_list, "listIterator.next()");
                index++;
            }
            printTest("[]", list.toString(), "iterator.remove()");
            printTest("[]", list2.toString(), "iterator.remove()");

            list = new LinkedList<>();
            list2 = new LinkedList<>();
            for (int j = 0; j < inputs.length; j++) {
                inputs[j] = (int) (SIZE * Math.random());
                list.addLast(inputs[j]);
                list2.addLast(inputs[j]);
            }

            itr = list.descendingIterator();
            itr_list = list2.listIterator(list2.size());
            printTest(itr.hasNext(), true, "descendingIterator.hasNext()");
            printTest(itr_list.hasPrevious(), true, "listIterator.hasPrevious()");
            index = 0;
            while (itr.hasNext() && itr_list.hasPrevious()) {
                x = itr.next();
                y = itr_list.previous();

                itr.remove();
                itr_list.remove();

                int expected = inputs[SIZE - 1 - index];
                int actual = x;
                int actual_list = y;
                printTest(expected, actual, "descendingIterator.next()");
                printTest(expected, actual_list, "listIterator.next()");
                index++;
            }
            printTest(itr.hasNext(), false, "descendingIterator.hasNext()");
            printTest(itr_list.hasPrevious(), false, "listIterator.hasPrevious()");
            printTest("[]", list.toString(), "descendingIterator.remove()");
            printTest("[]", list2.toString(), "listIterator.remove()");
        }
    }

    /**
     * Tests for the removal methods, including:
     * remove()
     * remove(int index)
     * remove(Object o)
     * removeAll(Collection<?> c)
     * removeFirst()
     * removeLast()
     * removeFirstOccurance()
     * removeLastOccurance()
     * retainAll(Collection<?> c)
     * poll()
     * pollFirst()
     * pollLast()
     * pop()
     * addLast(E e)
     * offer(E e)
     * offerFirst(E e)
     * offerLast(E e)
     * push(E e)
     */
    private static void removeTests() {
        clearTests();
        removeAnyTests();
        removeFirstTests();
        removeLastTests();
        removeOccuranceTest();

    }

    /**
     * Tests for methods that completely clear a list, including:
     * clear()
     */
    private static void clearTests() {
        // clear
        final String[] inputs = new String[] { "I", "M", "S", "O", "T", "I", "R", "E", "D" };

        for (int i = 0; i < 3; i++) {
            LinkedList<String> list = new LinkedList<>();
            for (int j = i; j < inputs.length - i; j++) {
                list.add(inputs[j]);
            }
            list.clear();
            String expected = "[]";
            String actual = list.toString();
            printTest(expected, actual, "clear()");
        }
    }

    /**
     * Tests for methods that remove from an arbitraty index, including:
     * remove(index i)
     */
    private static void removeAnyTests() {
        final String[] inputs = new String[] { ":O", ":)", ":|", ":{", ":(" };
        ArrayList<String> holder = new ArrayList<String>();
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            holder.add(inputs[i]);
            list.add(inputs[i]);
        }
        for (int i = 0; i < inputs.length; i++) {
            int pos = (int) (Math.random() * holder.size());
            list.remove(pos);
            holder.remove(pos);
            String expected = holder.toString();
            String actual = list.toString();
            printTest(expected, actual, "remove(index i)");
        }
    }

    /**
     * Tests for methods that remove from the front, including:
     * remove()
     * remove(int index)
     * removeFirst()
     * poll()
     * pollFirst()
     */
    private static void removeFirstTests() {
        final String[] inputs = new String[] { "A", "B", "C", "D" };
        LinkedList<String> list_removeFirst = new LinkedList<>();
        LinkedList<String> list_remove = new LinkedList<>();
        LinkedList<String> list_removei = new LinkedList<>();
        LinkedList<String> list_poll = new LinkedList<>();
        LinkedList<String> list_pollFirst = new LinkedList<>();
        LinkedList<String> list_pop = new LinkedList<>();

        for (int i = 0; i < inputs.length; i++) {
            list_removeFirst.add(inputs[i]);
            list_remove.add(inputs[i]);
            list_removei.add(inputs[i]);
            list_poll.add(inputs[i]);
            list_pollFirst.add(inputs[i]);
            list_pop.add(inputs[i]);
        }

        for (int i = 0; i < inputs.length; i++) {
            list_removeFirst.removeFirst();
            list_remove.remove();
            list_removei.remove(0);
            list_poll.poll();
            list_pollFirst.pollFirst();
            list_pop.pop();

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            if (i < inputs.length - 1) {
                sb.append(inputs[i + 1]);
            }
            for (int j = i + 2; j < inputs.length; j++) {
                sb.append(", ");
                sb.append(inputs[j]);
            }
            sb.append("]");
            String expected = sb.toString();
            String actual = list_removeFirst.toString();
            printTest(expected, actual, "removeFirst()");
            actual = list_remove.toString();
            printTest(expected, actual, "remove()");
            actual = list_removei.toString();
            printTest(expected, actual, "remove(int index)");
            actual = list_poll.toString();
            printTest(expected, actual, "poll()");
            actual = list_pollFirst.toString();
            printTest(expected, actual, "pollFirst()");
            actual = list_pop.toString();
            printTest(expected, actual, "pop()");
        }

        // test functionality with empty lists

        LinkedList<Integer> list2 = new LinkedList<>();
        try {
            list2.remove();
            printTest(true, false, "remove()");
        } catch (NoSuchElementException e) {
            printTest(true, true, "remove()");
        }

        try {
            Integer i = list2.poll();
            printTest(true, i == null, "poll()");
        } catch (NoSuchElementException e) {
            if (VERBOSE) {
                System.out.print("poll() should not return any errors: ");
            }
            printTest(true, false, "poll()");
        }
    }

    /**
     * Tests for methods that remove from the back, including:
     * removeLast()
     * pollLast()
     */
    private static void removeLastTests() {
        final char[] inputs = new char[] { 'E', 'F', 'G', 'H' };
        LinkedList<Character> list_removeLast = new LinkedList<>();
        LinkedList<Character> list_pollLast = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            list_removeLast.add(inputs[i]);
            list_pollLast.add(inputs[i]);
        }

        for (int i = 0; i < inputs.length; i++) {
            list_removeLast.removeLast();
            list_pollLast.pollLast();

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            if (i < inputs.length - 1) {
                sb.append(inputs[0]);
            }
            for (int j = 1; j < inputs.length - 1 - i; j++) {
                sb.append(", ");
                sb.append(inputs[j]);
            }
            sb.append("]");
            String expected = sb.toString();
            String actual_removeLast = list_removeLast.toString();
            String actual_pollLast = list_pollLast.toString();
            printTest(expected, actual_removeLast, "removeLast()");
            printTest(expected, actual_pollLast, "pollLast()");
        }
    }

    /**
     * Tests for methods that remove a specific object from a list, including:
     * remove(Object o)
     * removeFirstOccurance(Object o)
     * removeLastOccurance(Object o)
     */
    private static void removeOccuranceTest() {
        final String[] inputs = new String[] { ":O", ":)", ":|", ":{", ":(" };
        ArrayList<String> holder = new ArrayList<>();
        LinkedList<String> list_remove = new LinkedList<>();
        LinkedList<String> list_removeFirstOccurance = new LinkedList<>();
        LinkedList<String> list_removeLastOccurance = new LinkedList<>();

        for (int i = 0; i < inputs.length; i++) {
            holder.add(inputs[i]);
            list_remove.add(inputs[i]);
            list_removeFirstOccurance.add(inputs[i]);
            list_removeLastOccurance.add(inputs[i]);
        }
        for (int i = 0; i < inputs.length; i++) {
            int pos = (int) (Math.random() * holder.size());
            String s = holder.get(pos);
            list_remove.remove(s);
            list_removeFirstOccurance.removeFirstOccurrence(s);
            list_removeLastOccurance.removeLastOccurrence(s);
            holder.remove(pos);
            String expected = holder.toString();
            String actual_remove = list_remove.toString();
            String actual_removeFirstOccurance = list_removeFirstOccurance.toString();
            String actual_removeLastOccurance = list_removeLastOccurance.toString();
            printTest(expected, actual_remove, "remove(Object o)");
            printTest(expected, actual_removeFirstOccurance, "removeFirstOccurance(Object o)");
            printTest(expected, actual_removeLastOccurance, "removeLastOccurance(Object o)");
        }
    }

    /**
     * Tests for mutator methods, including:
     * set(int index, E element)
     */
    private static void setTests() {
        final int[] inputs = new int[] { 1990, 2010 };
        final ArrayList<Integer> holder = new ArrayList<Integer>();
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            holder.add(inputs[i]);
            list.add(inputs[i]);
        }

        final int[] sets = new int[] { 1980, 2000, 2020 };
        for (int i = 0; i < sets.length; i++) {
            list.set(2 * i % list.size(), sets[i]);
            holder.set(2 * i % holder.size(), sets[i]);

            String expected = holder.toString();
            String actual = list.toString();
            printTest(expected, actual, "set(int index, E element)");
        }
    }

    /**
     * Tests for size methods, including:
     * isEmpty()
     * size()
     */
    private static void sizeTests() {
        // size()
        final char[] inputs = new char[] { '@', '#', '$', '\n' };
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            list.add(inputs[i]);
            String expected = "" + (i + 1);
            String actual = "" + list.size();
            printTest(expected, actual, "size()");
        }

        // isEmpty()
        LinkedList<Character> emptylist = new LinkedList<>();
        String expected = "" + true;
        String actual = "" + emptylist.isEmpty();
        printTest(expected, actual, "isEmpty()");

        emptylist.add('?');

        expected = "" + false;
        actual = "" + emptylist.isEmpty();
        printTest(expected, actual, "isEmpty()");

        emptylist.remove();

        expected = "" + true;
        actual = "" + emptylist.isEmpty();
        printTest(expected, actual, "isEmpty()");
    }

    /**
     * Tests for sublist methods, including:
     * subList()
     */
    private static void subListTests() {
        final int[] inputs = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            list.add(inputs[i]);
        }
        String expected = "[1, 2, 3]";
        String actual = list.subList(0, 3).toString();
        printTest(expected, actual, "subList(int fromIndex, int toIndex)");
        expected = "[9, 0]";
        actual = list.subList(8, 10).toString();
        printTest(expected, actual, "subList(int fromIndex, int toIndex)");
        expected = "[4, 5, 6, 7]";
        actual = list.subList(3, 7).toString();
        printTest(expected, actual, "subList(int fromIndex, int toIndex)");
    }

    /**
     * Tests for toArray methods, including:
     * toArray()
     * toArray(T[] a)
     */
    private static void toArrayTests() {
        final char[] inputs = new char[] { '@', '#', '$', '\n' };
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            list.add(inputs[i]);
        }
        String expected = "" + Arrays.toString(inputs);
        String actual = "" + Arrays.toString(list.toArray());
        printTest(expected, actual, "iterator.toArray()");
        actual = "" + Arrays.toString(list.toArray(new Character[0]));
        printTest(expected, actual, "iterator.toArray(T[] a)");
    }

    /**
     * Tests for toString methods, including:
     * toString()
     */
    private static void toStringTests() {
        // toString()

        final char[] inputs = new char[] { '@', 'a', 'A', '4', '^' };
        ArrayList<Character> holder = new ArrayList<>();
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            holder.add(0, inputs[i]);
            list.addFirst(inputs[i]);
            String expected = holder.toString();
            String actual = list.toString();
            printTest(expected, actual, "toString()");
        }
    }
}