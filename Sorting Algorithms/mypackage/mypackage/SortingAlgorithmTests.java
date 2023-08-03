package mypackage;

import java.util.Arrays;

public class SortingAlgorithmsTests {
    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (1000 * Math.random());
        }

        int[] b = Arrays.copyOf(arr, arr.length);
        System.out.println("Built-in");
        Arrays.sort(b);

        SortingAlgorithms s = new SortingAlgorithms();

        int[] a = Arrays.copyOf(arr, arr.length);
        System.out.println("Selection: " + SortingAlgorithms.SelectionSort(a));
        System.out.println(Arrays.toString(a));

        a = Arrays.copyOf(arr, arr.length);
        System.out.println("Insertion: " + SortingAlgorithms.InsertionSort(a));
        System.out.println(Arrays.toString(a));

        a = Arrays.copyOf(arr, arr.length);
        System.out.println("Bubble: " + SortingAlgorithms.BubbleSort(a));
        System.out.println(Arrays.toString(a));

        a = Arrays.copyOf(arr, arr.length);
        System.out.println("Merge: " + SortingAlgorithms.MergeSort(a));
        System.out.println(Arrays.toString(a));

        a = Arrays.copyOf(arr, arr.length);
        System.out.println("Quick: " + SortingAlgorithms.QuickSort(a));
        System.out.println(Arrays.toString(a));

    }
}