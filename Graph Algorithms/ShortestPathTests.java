import java.util.ArrayList;
import java.util.Map;

/**
 * A Test Harness for the ShortestPath class.
 */
public class ShortestPathTests {
    public static void main(String[] args) {
        int[][] iMatrix = {
                /* 0 */ { 0, 0, 3, 2, 0 },
                /* 1 */ { 0, 0, 0, 6, 1 },
                /* 2 */ { 3, 0, 0, 2, 5 },
                /* 3 */ { 2, 6, 2, 0, 0 },
                /* 4 */ { 0, 1, 5, 0, 0 } };

        String[] labels = new String[iMatrix.length];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = getLabel(i);
        }
        
        Map<String, Map<String, Integer>> iList = ShortestPath.<String>toAdjList(iMatrix, labels);
        ArrayList<ShortestPath.Vertex<Integer, Integer>> retMat = ShortestPath.dijkstraInt(iMatrix, 0);
        for (ShortestPath.Vertex<Integer, Integer> v : retMat) {
        	System.out.println(v);
        }
        
        System.out.println();
        
        Map<String, ShortestPath.Vertex<String, Integer>> retLis = ShortestPath.<String>dijkstraInt(iList, getLabel(0));
        for (String key : retLis.keySet()) {
        	System.out.println(retLis.get(key));
        }
    }

    private static String getLabel(int i) {
        return "" + ((char) ((i % 26) + 'A')) + i;
    }

}
