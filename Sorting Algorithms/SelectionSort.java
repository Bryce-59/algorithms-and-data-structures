import java.util.Arrays;

public class SelectionSort<E extends Comparable<E>> {
    E[] data;

    E[] sortedCopy;
    int time = 0;

    public SelectionSort(E[] data) {
        this.data = Arrays.copyOf(data, data.length);
        reset();
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
        int min = time;
        for (int i = min; i < sortedCopy.length; i++) {
            if (sortedCopy[i].compareTo(sortedCopy[min]) < 0) {
                min = i;
            }
        }

        E tmp = sortedCopy[time];
        sortedCopy[time] = sortedCopy[min];
        sortedCopy[min] = tmp;

        time++;
    }

    protected E[] toArray() {
        return Arrays.copyOf(sortedCopy, sortedCopy.length);
    }
}