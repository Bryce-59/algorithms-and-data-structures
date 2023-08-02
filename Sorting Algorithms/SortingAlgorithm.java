/*
 *   Test results (ms):
 *   SIZE            10000   20000   40000   80000 
 *   Arrays.sort()   12      11      29      245
 *   Selection Sort  266     885     3938    17836
 *   Insertion Sort  197     651     8338    11861
 *   Bubble Sort     297     1609    17094   26847
 *   Merge Sort      308     779     5910    8087
 *   Quick Sort      6       5       28      13
 * 
 */

import java.util.Arrays;

public class SortingAlgorithm {

    public static void main(String[] args) {
        final int[] SIZES = { 10000, 20000, 40000, 80000 };

        long[] builtTime = new long[SIZES.length];

        long[] selectionTime = new long[SIZES.length];
        int[] selectionComp = new int[SIZES.length];

        long[] insertionTime = new long[SIZES.length];
        int[] insertionComp = new int[SIZES.length];

        long[] bubbleTime = new long[SIZES.length];
        int[] bubbleComp = new int[SIZES.length];

        long[] mergeTime = new long[SIZES.length];
        int[] mergeComp = new int[SIZES.length];

        long[] quickTime = new long[SIZES.length];
        int[] quickComp = new int[SIZES.length];

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

        System.out.println("Test results: ");
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

    }

    public static <E extends Comparable<E>> int SelectionSort(E[] toSort) {
        int comparisons = 0;
        for (int k = 0; k < toSort.length; k++) {
            int min_index = k;
            for (int i = k + 1; i < toSort.length; i++) {
                if (toSort[i].compareTo(toSort[min_index]) < 0) {
                    min_index = i;
                }
                comparisons++;
            }
            swap(toSort, k, min_index);
        }

        return comparisons;
    }

    public static <E extends Comparable<E>> int InsertionSort(E[] toSort) {
        int comparisons = 0;
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

    public static <E extends Comparable<E>> int BubbleSort(E[] toSort) {
        int comparisons = 0;
        for (int k = 0; k < toSort.length; k++) {
            for (int i = 0; i < (toSort.length - 1) - k; i++) {
                if (toSort[i + 1].compareTo(toSort[i]) < 0) {
                    swap(toSort, i, i + 1);
                }
                comparisons++;
            }
        }
        return comparisons;
    }

    public static <E extends Comparable<E>> int MergeSort(E[] toSort) {
        return MergeSortHelper(toSort, 0, toSort.length);
    }

    private static <E extends Comparable<E>> int MergeSortHelper(E[] toSort, int start, int end) {
        if (end - start <= 1) {
            return 0;
        }

        int comparisons = 0;

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

    public static <E extends Comparable<E>> int QuickSort(E[] toSort) {
        return QuickSortHelper(toSort, 0, toSort.length);
    }

    public static <E extends Comparable<E>> int QuickSortHelper(E[] toSort, int start, int end) {
        if (end - start <= 1) {
            return 0;
        }

        int comparisons = 0;

        E pivot = toSort[end - 1];

        int partition = start;
        for (int i = start; i < end - 1; i++) {
            if (toSort[i].compareTo(pivot) < 0) {
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

    private static <E> void swap(E[] arr, int x, int y) {
        E tmp = arr[x];
        arr[x] = arr[y];
        arr[y] = tmp;
    }
}