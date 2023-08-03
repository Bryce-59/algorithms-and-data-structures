import java.util.Collections;

public class SortingAlgorithms {

    public static <E extends Comparable<E>> int SelectionSort(E[] toSort) {
        int comparisons = 0;
        for (int k = 0; k  toSort.length;  k++) {
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
        for (int k = 0; k < toSort.length; k++) {
            int index = k;
            while (index >= 0) {
                if (toSort[index + 1].compareTo(toSort[index]) >= 0) {
                    break;
                }
                swap(toSort, index, index + 1);
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

        int middle = (end - start) / 2;

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

    public static <E extends Comparable<E>> int QuickSort(E[] toSort, int start, int end) {
        return QuickSortHelper(toSort, 0, toSort.length);
    }

    public static <E extends Comparable<E>> int QuickSortHelper(E[] toSort, int start, int end) {
        int comparisons = 0;

        E pivot = toSort[end - 1];

        int partition = start;
        for (int i = start; i < end - 1; i++) {
            if (toSort[partition].compareTo(pivot) < 0) {
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