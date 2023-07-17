public class InsertionSort<E extends Comparable<E>> extends SortingAlgorithm<E> {
    public InsertionSort(E[] data) {
        super(data);
    }

    protected void tick() {
        int i = getTime();
        while (i >= 0 && sortedCopy[i + 1].compareTo(sortedCopy[i]) < 0) {
            E tmp = sortedCopy[i];
            sortedCopy[i] = sortedCopy[i + 1];
            sortedCopy[i + 1] = tmp;
            i--;
        }
        super.tick();
    }
}