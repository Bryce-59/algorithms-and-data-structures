import java.util.Arrays;

public abstract class SortingAlgorithm<E extends Comparable<E>> {
    private E[] data;

    protected E[] sortedCopy;
    private int time = 0;

    public SortingAlgorithm(E[] data) {
        this.data = Arrays.copyOf(data, data.length);
        reset();
    }

    protected int getTime() {
        return time;
    }

    protected void reset() {
        this.sortedCopy = Arrays.copyOf(data, data.length);
        time = 0;
    }

    public E[] sort() {
        for (int i = time; i < sortedCopy.length; i++) {
            tick();
        }
        return toArray();
    }

    protected void tick() {
        time++;
    }

    private E[] toArray() {
        return Arrays.copyOf(sortedCopy, sortedCopy.length);
    }
}