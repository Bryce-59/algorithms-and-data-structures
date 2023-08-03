
/*
Test results (Time in ms): 
_____________
Size            10000   20000   40000   80000

Arrays.sort()   12      11      17      235
Selection Sort  260     934     3704    29842
Insertion Sort  169     721     3078    11891
Bubble Sort     311     1581    18681   68131
Merge Sort      387     496     6320    14027
Quick Sort      4       3       28      38

*NOTE: Arrays.sort() uses the TimSort algorithm, 
which is an O(NlogN) variation of Merge Sort.


Test results (No. of comparisons):
_____________

Size            10000           20000           40000           80000

Selection Sort  49995000        199990000       799980000       3199960000
Insertion Sort  25107848        100274168       401317147       1600076473
Bubble Sort     49995000        199990000       799980000       3199960000
Merge Sort      120471          260912          561707          1204220
Quick Sort      155238          380244          737157          1592636
*/

import java.util.Arrays;

/**
 * A class used to implement and test some basic sorting algorithms.
 */
public class SortingAlgorithm {

    public static void main(String[] args) {
        final int[] SIZES = { 10000, 20000, 40000, 80000 };

        long[] builtTime = new long[SIZES.length];

        long[] selectionTime = new long[SIZES.length];
        long[] selectionComp = new long[SIZES.length];

        long[] insertionTime = new long[SIZES.length];
        long[] insertionComp = new long[SIZES.length];

        long[] bubbleTime = new long[SIZES.length];
        long[] bubbleComp = new long[SIZES.length];

        long[] mergeTime = new long[SIZES.length];
        long[] mergeComp = new long[SIZES.length];

        long[] quickTime = new long[SIZES.length];
        long[] quickComp = new long[SIZES.length];

        for (int i = 0; i < SIZES.length; i++) {
            Integer[] arr = new Integer[SIZES[i]];
            for (int r = 0; r < arr.length; r++) {
                arr[r] = (int) (Math.random() * SIZES[i]);
            }

            Integer[] expected = Arrays.copyOf(arr, arr.length);
            long startTime = System.currentTimeMillis();
            Arrays.sort(expected);
            long endTime = System.currentTimeMillis();
            builtTime[i] = endTime - startTime;

            Integer[] actual = Arrays.copyOf(arr, arr.length);
            startTime = System.currentTimeMillis();
            selectionComp[i] = SelectionSort(actual);
            endTime = System.currentTimeMillis();
            selectionTime[i] = endTime - startTime;

            if (!Arrays.equals(actual, expected)) {
                System.out.println("FAIL: Selection Sort");
            }

            actual = Arrays.copyOf(arr, arr.length);
            startTime = System.currentTimeMillis();
            insertionComp[i] = InsertionSort(actual);
            endTime = System.currentTimeMillis();
            insertionTime[i] = endTime - startTime;

            if (!Arrays.equals(actual, expected)) {
                System.out.println("FAIL: Insertion Sort");
            }

            actual = Arrays.copyOf(arr, arr.length);
            startTime = System.currentTimeMillis();
            bubbleComp[i] = BubbleSort(actual);
            endTime = System.currentTimeMillis();
            bubbleTime[i] = endTime - startTime;

            if (!Arrays.equals(actual, expected)) {
                System.out.println("FAIL: Bubble Sort");
            }

            actual = Arrays.copyOf(arr, arr.length);
            startTime = System.currentTimeMillis();
            mergeComp[i] = MergeSort(actual);
            endTime = System.currentTimeMillis();
            mergeTime[i] = endTime - startTime;

            if (!Arrays.equals(actual, expected)) {
                System.out.println("FAIL: Merge Sort");
            }

            actual = Arrays.copyOf(arr, arr.length);
            startTime = System.currentTimeMillis();
            quickComp[i] = QuickSort(actual);
            endTime = System.currentTimeMillis();
            quickTime[i] = endTime - startTime;

            if (!Arrays.equals(actual, expected)) {
                System.out.println("FAIL: Quick Sort");
            }
        }

        System.out.println("Test results (Time in ms): ");
        System.out.println("_____________");

        System.out.print("Sizes");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + SIZES[i]);
        }
        System.out.println();
        System.out.println();

        System.out.print("Arrays.sort()");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + builtTime[i]);
        }
        System.out.println();

        System.out.print("Selection Sort");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + selectionTime[i]);
        }
        System.out.println();

        System.out.print("Insertion Sort");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + insertionTime[i]);
        }
        System.out.println();

        System.out.print("Bubble Sort");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + bubbleTime[i]);
        }
        System.out.println();

        System.out.print("Merge Sort");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + mergeTime[i]);
        }
        System.out.println();

        System.out.print("Quick Sort");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + quickTime[i]);
        }
        System.out.println();
        System.out.println();

        System.out.println("Test results (No. of comparisons): ");
        System.out.println("_____________");
        System.out.println();

        System.out.print("Sizes");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + SIZES[i]);
        }
        System.out.println();
        System.out.println();

        System.out.print("Selection Sort");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + selectionComp[i]);
        }
        System.out.println();

        System.out.print("Insertion Sort");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + insertionComp[i]);
        }
        System.out.println();

        System.out.print("Bubble Sort");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + bubbleComp[i]);
        }
        System.out.println();

        System.out.print("Merge Sort");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + mergeComp[i]);
        }
        System.out.println();

        System.out.print("Quick Sort");
        for (int i = 0; i < SIZES.length; i++) {
            System.out.print("\t" + quickComp[i]);
        }
        System.out.println();

    }

    /**
     * A stable-state implementation of in-place Selection Sort.
     * Runs in O(N^2) time in the average case.
     * 
     * @param toSort the array to sort
     * @return the number of comparisons made
     */
    public static <E extends Comparable<E>> long SelectionSort(E[] toSort) {
        long comparisons = 0;
        for (int k = 0; k < toSort.length; k++) {
            int min_index = k;
            for (int i = k + 1; i < toSort.length; i++) {
                if (toSort[i].compareTo(toSort[min_index]) < 0) {
                }
                comparisons++;
            }
            swap(toSort, k, min_index);
        }

        return comparisons;
    }

    /**
     * A stable-state implementation of in-place Insertion Sort.
     * Runs in O(N^2) time in the average case and O(N) in the best case.
     * 
     * @param toSort the array to sort
     * @return the number of comparisons made
     */
    public static <E extends Comparable<E>> long InsertionSort(E[] toSort) {
        long comparisons = 0;
        for (int k = 1; k < toSort.length; k++) {
            int index = k;
            while (index > 0) {
                if (toSort[index - 1].compareTo(toSort[index]) < 0) {
                    break;
                }
                swap(toSort, index - 1, index);
                index--;
                comparisons++;
            }
        }
        return comparisons;
    }

    /**
     * A stable-state implementation of in-place Bubble Sort.
     * Runs in O(N^2) time in the average case and O(N) in the best case.
     * 
     * @param toSort the array to sort
     * @return the number of comparisons made
     */
    public static <E extends Comparable<E>> long BubbleSort(E[] toSort) {
        long comparisons = 0;
        for (int k = 0; k < toSort.length; k++) {
            boolean quit = true;
            for (int i = 0; i < (toSort.length - 1) - k; i++) {
                if (toSort[i + 1].compareTo(toSort[i]) < 0) {
                    swap(toSort, i, i + 1);
                    quit = false;
                }
                comparisons++;
            }

            if (quit) {
                break;
            }
        }
        return comparisons;

    }

    /**
     * A stable-state implementation of in-place Merge Sort.
     * Runs in O(N*log(N)) time in the average case.
     * 
     * @param toSort the array to sort
     * @return the number of comparisons made
     */
    public static <E extends Comparable<E>> long MergeSort(E[] toSort) {
        return MergeSortHelper(toSort, 0, toSort.length);
    }

    /*
     * Recursive helper method to MergeSort.
     */
    private static <E extends Comparable<E>> long MergeSortHelper(E[] toSort, int start, int end) {
        if (end - start <= 1) {
            return 0;
        }

        long comparisons = 0;

        int middle = (end + start) / 2;

        comparisons += MergeSortHelper(toSort, start, middle);
        comparisons += MergeSortHelper(toSort, middle, end);

        int i = start;
        int j = middle;
        while (i < j && j < end) {
            if (toSort[i].compareTo(toSort[j]) <= 0) {
                i++;
            } else {
                E tmp = toSort[j];
                int index = j;
                while (index != i) {
                    swap(toSort, index, index - 1);
                    index--;
                }
                toSort[i] = tmp;

                i++;
                j++;
            }
            comparisons++;
        }
        return comparisons;
    }

    /**
     * A stable-state implementation of in-place Quick Sort.
     * Runs in O(N*log(N)) time in the average case, and O(N^2) in the worst case.
     * 
     * @param toSort the array to sort
     * @return the number of comparisons made
     */
    public static <E extends Comparable<E>> long QuickSort(E[] toSort) {
        return QuickSortHelper(toSort, 0, toSort.length);
    }

    /*
     * Recursive helper method for QuickSort.
     */
    private static <E extends Comparable<E>> long QuickSortHelper(E[] toSort, int start, int end) {
        if (end - start <= 1) {
            return 0;
        }

        long comparisons = 0;

        E pivot = toSort[end - 1];

        int partition = start;
        for (int i = start; i < end - 1; i++) {
            if (toSort[i].compareTo(pivot) <= 0) {
                swap(toSort, partition, i);
                partition++;
            }
            comparisons++;
        }
        swap(toSort, partition, end - 1);

        comparisons += QuickSortHelper(toSort, start, partition);
        comparisons += QuickSortHelper(toSort, partition + 1, end);
        return comparisons;
    }

    /**
     * Helper method that swaps two elements in an array.
     * 
     * @param arr the array to reference
     * @param x   the first position to swap
     * @param y   the second position to swap
     */
    private static <E> void swap(E[] arr, int x, int y) {
        E tmp = arr[x];
        arr[x] = arr[y];
        arr[y] = tmp;
    }
}