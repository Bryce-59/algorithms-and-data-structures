import java.util.Random;

public class BogoSort<E extends Comparable<E>> extends SortingAlgorithm<E> {
    public BogoSort(E[] data) {
        super(data);
    }

    protected void tick() {
        Random r = new Random();

        for (int i = 0; i < sortedCopy.length; i++) {
            int index = r.nextInt(sortedCopy.length);

            E tmp = sortedCopy[index];
            sortedCopy[index] = sortedCopy[i];
            sortedCopy[i] = tmp;
        }
        super.tick();
    }
}