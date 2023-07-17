public class BubbleSort<E extends Comparable<E>> extends SortingAlgorithm<E> {
    public BubbleSort(E[] data) {
        super(data);
    }

    protected void tick() {
        int time = getTime();
        for (int i = 0; i < (sortedCopy.length - 1) - time; i++) {
            if (sortedCopy[i + 1].compareTo(sortedCopy[i]) < 0) {
                E tmp = sortedCopy[i];
                sortedCopy[i] = sortedCopy[i + 1];
                sortedCopy[i + 1] = tmp;
            }
        }
        super.tick();
    }
}