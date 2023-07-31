import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class SmallTests {
    public static void main(String[] args) {
        addTests();
        iteratorTests();
        sizeTests();
        getTests();
        setTests();
        removeTests();
        indexOfTests();
        makeEmptyTests();
    }

    private static void addTests() {
        final int SIZE = 6;
        final int[] inputs = new int[SIZE];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = (int) (SIZE * Math.random());
        }
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            int[] check = new int[i + 1];
            for (int j = 0; j < check.length; j++) {
                check[j] = inputs[j];
            }
            System.out.print("addTest " + (i + 1) + ": ");

            list.add(inputs[i]);
            String expected = Arrays.toString(check);
            String actual = list.toString();
            printTest(expected, actual);
        }
    }

    private static void iteratorTests() {
        final int SIZE = 100;
        final int NUM_TRIALS = 3;
        final int[] inputs = new int[SIZE];
        for (int i = 0; i < NUM_TRIALS; i++) {
            LinkedList<Integer> list = new LinkedList<>();
            for (int j = 0; j < inputs.length; j++) {
                inputs[j] = (int) (SIZE * Math.random());
                list.add(inputs[j]);
            }

            Iterator<Integer> itr = list.iterator();
            int x = -1;
            System.out.print("iterator_hasNextTest " + (1 + i) + ": ");
            printTest(itr.hasNext(), true);
            while (itr.hasNext()) {
                x = itr.next();
                itr.remove();
            }
            System.out.print("iterator_nextTest " + (1 + i) + ": ");
            int expected = inputs[SIZE - 1];
            int actual = x;
            printTest(expected, actual);
            System.out.print("iterator_removeTest " + (1 + i) + ": ");
            printTest("[]", list.toString());
        }
    }

    private static void sizeTests() {
        final char[] inputs = new char[] { '@', '#', '$', '\n' };
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            list.add(inputs[i]);
            System.out.print("sizeTests " + (i + 1) + ": ");
            String expected = "" + (i + 1);
            String actual = "" + list.size();
            printTest(expected, actual);
        }
    }

    private static void indexOfTests() {
        // part 1
        String[] inputs = new String[] { "B", "R", "Y", "C", "E" };
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            list.add(inputs[i]);
        }
        for (int i = 0; i < inputs.length; i++) {
            System.out.print("indexOfTests 1." + (i + 1) + ": ");
            String expected = "" + i;
            String actual = "" + list.indexOf(inputs[i]);
            printTest(expected, actual);
        }

        // // part 2
        // inputs = new String[] { "Apples", "Oranges", "Apples", "Bananas", "Apples",
        // "Oranges" };
        // final int[] positions = new int[] { 1, 2, 3 };
        // final String[] checks = new String[] { "Apples", "Oranges", "Bananas" };
        // final int[] answers = new int[] { 2, 5, 3 };
        // list = new LinkedList<>();
        // for (int i = 0; i < inputs.length; i++) {
        // list.add(inputs[i]);
        // }
        // for (int i = 0; i < checks.length; i++) {
        // System.out.print("indexOfTests 2." + (i + 1) + ": ");
        // String expected = "" + answers[i];
        // String actual = "" + list.indexOf(checks[i], positions[i]);
        // printTest(expected, actual);
        // }
    }

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
            System.out.print("setTests " + (i + 1) + ": ");
            list.set(2 * i % list.size(), sets[i]);
            holder.set(2 * i % holder.size(), sets[i]);

            String expected = holder.toString();
            String actual = list.toString();
            printTest(expected, actual);
        }
    }

    private static void getTests() {
        final int[] inputs = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            list.add(inputs[i]);
        }
        for (int i = 0; i < inputs.length; i++) {
            System.out.print("getTests " + (i + 1) + ": ");
            String expected = "" + inputs[i];
            String actual = list.get(i).toString();
            printTest(expected, actual);
        }
    }

    private static void removeTests() {
        // part 1
        final String[] inputs = new String[] { ":O", ":)", ":|", ":{", ":(" };
        ArrayList<String> holder = new ArrayList<String>();
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            holder.add(inputs[i]);
            list.add(inputs[i]);
        }
        for (int i = 0; i < inputs.length; i++) {
            System.out.print("removeTests 1." + (i + 1) + ": ");
            int pos = (int) (Math.random() * holder.size());
            list.remove(pos);
            holder.remove(pos);
            String expected = holder.toString();
            String actual = list.toString();
            printTest(expected, actual);
        }

        // part 2
        holder = new ArrayList<>();
        list = new LinkedList<>();
        for (int i = 0; i < inputs.length; i++) {
            holder.add(inputs[i]);
            list.add(inputs[i]);
        }
        for (int i = 0; i < inputs.length; i++) {
            System.out.print("removeTests 2." + (i + 1) + ": ");
            int pos = (int) (Math.random() * holder.size());
            String s = holder.get(pos);
            list.remove(s);
            holder.remove(pos);
            String expected = holder.toString();
            String actual = list.toString();
            printTest(expected, actual);
        }
    }

    private static void makeEmptyTests() {
        final String[] inputs = new String[] { "I", "M", "S", "O", "T", "I", "R", "E", "D" };

        for (int i = 0; i < 3; i++) {
            LinkedList<String> list = new LinkedList<>();
            for (int j = i; j < inputs.length - i; j++) {
                list.add(inputs[j]);
            }
            System.out.print("makeEmptyTests " + (1 + i++) + ": ");
            list.clear();
            String expected = "[]";
            String actual = list.toString();
            printTest(expected, actual);
        }
    }

    private static void printTest(Object expected, Object actual) {
        if (expected.equals(actual)) {
            System.out.println("PASSED!");
        } else {
            System.out.println("FAILED!");
            System.out.println("\texpected: " + expected.toString());
            System.out.println("\tactual: " + actual.toString());
        }
    }
}
