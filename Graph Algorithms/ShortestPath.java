import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * A class to calculate the shortest path between nodes of a positively 
 * weighted graph, represented by an adjacency matrix or adjacency list, 
 * as specified by the Dijkstra algorithm.
 */
public class ShortestPath {
	private final static double DOUBLE_DEFAULT = 0.0;
	private final static int INTEGER_DEFAULT = 0;
	
    /***
     * Performs Dijkstra's Algorithm on an adjacency matrix with 
     * non-Integer weights.
     * 
     * @param graph the adjacency matrix
     * @param src the index of the source vertex
     * @return an ArrayList containing vertex objects which contain
     * the shortest distance and the path to the source node  
     * 
     * @exception NullPointerException if graph is null
     * @exception IllegalArgumentException is src is invalid
     */
    public static ArrayList<Vertex<Integer, Double>> dijkstraDouble(Double[][] graph, int src) {
        if (graph == null) {
            throw new NullPointerException("graph cannot be null");
        } else if (src < 0 || src > graph.length) {
            throw new IllegalArgumentException("source node must be a valid index");
        }

        SumFunction sum = (p1, p2) -> {
            return Double.sum(p1.doubleValue(), p2.doubleValue());
        };
        return ShortestPath.<Double>dijkstra(graph, src, Double.MAX_VALUE, DOUBLE_DEFAULT, sum);
    }

    /***
     * Performs Dijkstra's Algorithm on an adjacency matrix with 
     * non-Integer weights.
     * 
     * @param graph the adjacency matrix
     * @param src the index of the source vertex
     * @return an ArrayList containing vertex objects which contain
     * the shortest distance and the path to the source node  
     * 
     * @exception NullPointerException if graph is null
     * @exception IllegalArgumentException is src is invalid
     */
    public static ArrayList<Vertex<Integer, Double>> dijkstraDouble(double[][] graph, int src) {
        return ShortestPath.dijkstraDouble(toDouble(graph), src);
    }
    
    /***
     * Performs Dijkstra's Algorithm on an adjacency list with 
     * non-Integer weights.
     * 
     * @param <T> the label type
     * @param graph the adjacency list
     * @param src the label of the source vertex
     * @return a Map that maps labels to vertex objects which contain
     * the shortest distance and the path to the source node  
     * 
     * @exception NullPointerException if graph is null
     * @exception IllegalArgumentException is src is invalid
     */
    public static <T> Map<T, Vertex<T, Double>> dijkstraDouble(Map<T, Map<T, Double>> graph, T src) {
        if (graph == null) {
            throw new NullPointerException("graph cannot be null");
        } else if (!graph.containsKey(src)) {
            throw new IllegalArgumentException("source node must be a valid node");
        }

        SumFunction sum = (p1, p2) -> {
            return Double.sum(p1.doubleValue(), p2.doubleValue());
        };
        return ShortestPath.<T, Double>dijkstra(graph, src, Double.MAX_VALUE, DOUBLE_DEFAULT, sum);
    }
    
    /***
     * Performs Dijkstra's Algorithm on an adjacency matrix with 
     * non-Integer weights.
     * 
     * @param graph the adjacency matrix
     * @param src the index of the source vertex
     * @return an ArrayList containing vertex objects which contain
     * the shortest distance and the path to the source node  
     * 
     * @exception NullPointerException if graph is null
     * @exception IllegalArgumentException is src is invalid
     */
    public static ArrayList<Vertex<Integer, Integer>> dijkstraInt(Integer[][] graph, int src) {
        if (graph == null) {
            throw new NullPointerException("graph cannot be null");
        } else if (src < 0 || src > graph.length) {
            throw new IllegalArgumentException("source node must be a valid index");
        }

        SumFunction sum = (p1, p2) -> {
            return Integer.sum(p1.intValue(), p2.intValue());
        };
        return ShortestPath.<Integer>dijkstra(graph, src, Integer.MAX_VALUE, INTEGER_DEFAULT, sum);
    }    
    
    /***
     * Performs Dijkstra's Algorithm on an adjacency matrix with 
     * non-Integer weights.
     * 
     * @param graph the adjacency matrix
     * @param src the index of the source vertex
     * @return an ArrayList containing vertex objects which contain
     * the shortest distance and the path to the source node  
     * 
     * @exception NullPointerException if graph is null
     * @exception IllegalArgumentException is src is invalid
     */
    public static ArrayList<Vertex<Integer, Integer>> dijkstraInt(int[][] graph, int src) {
        return ShortestPath.dijkstraInt(toInteger(graph), src);
    }

    /***
     * Performs Dijkstra's Algorithm on an adjacency list with 
     * Integer weights.
     * 
     * @param <T> the label type
     * @param graph the adjacency list
     * @param src the label of the source vertex
     * @return a Map that maps labels to vertex objects which contain
     * the shortest distance and the path to the source node  
     * 
     * @exception NullPointerException if graph is null
     * @exception IllegalArgumentException is src is invalid
     */
    public static <T> Map<T, Vertex<T, Integer>> dijkstraInt(Map<T, Map<T, Integer>> graph, T src) {
        if (graph == null) {
            throw new NullPointerException("graph cannot be null");
        } else if (!graph.containsKey(src)) {
            throw new IllegalArgumentException("source node must be a valid node");
        }

        SumFunction sum = (p1, p2) -> {
            return Integer.sum(p1.intValue(), p2.intValue());
        };
        return ShortestPath.<T, Integer>dijkstra(graph, src, Integer.MAX_VALUE, INTEGER_DEFAULT, sum);
    }

    /**
     * Converts an adjacency matrix to an adjacency list.
     * 
     * @param <T> the label type
     * @param graph the adjacency matrix
     * @param labels the labels to be used in the adjacency list
     * @return the adjacency list
     * 
     * @exception NullPointerException if any parameters are null
     * @exception IllegalArgumentException is labels is an invalid size
     */
    public static <T> Map<T, Map<T, Double>> toAdjList(Double[][] graph, T[] labels) {
        return ShortestPath.<T, Double>toAdjList(graph, labels, 0.0);
    }

    /**
     * Converts an adjacency matrix to an adjacency list.
     * 
     * @param <T> the label type
     * @param graph the adjacency matrix
     * @param labels the labels to be used in the adjacency list
     * @return the adjacency list
     * 
     * @exception NullPointerException if any parameters are null
     * @exception IllegalArgumentException is labels is an invalid size
     */
    public static <T> Map<T, Map<T, Integer>> toAdjList(Integer[][] graph, T[] labels) {
        return ShortestPath.<T, Integer>toAdjList(graph, labels, 0);
    }

    /**
     * Converts an adjacency matrix to an adjacency list.
     * 
     * @param <T> the label type
     * @param graph the adjacency matrix
     * @param labels the labels to be used in the adjacency list
     * @return the adjacency list
     * 
     * @exception NullPointerException if any parameters are null
     * @exception IllegalArgumentException is labels is an invalid size
     */
    public static <T> Map<T, Map<T, Double>> toAdjList(double[][] graph, T[] labels) {
        return ShortestPath.<T>toAdjList(toDouble(graph), labels);
    }

    /**
     * Converts an adjacency matrix to an adjacency list.
     * 
     * @param <T> the label type
     * @param graph the adjacency matrix
     * @param labels the labels to be used in the adjacency list
     * @return the adjacency list
     * 
     * @exception NullPointerException if any parameters are null
     * @exception IllegalArgumentException is labels is an invalid size
     */
    public static <T> Map<T, Map<T, Integer>> toAdjList(int[][] graph, T[] labels) {
        return ShortestPath.<T>toAdjList(toInteger(graph), labels);
    }

    /***
     * Performs Dijkstra's Algorithm on an adjacency matrix.
     * 
     * @param <W> the type of the edge weights 
     * @param graph the adjacency matrix
     * @param src the label of the source vertex
     * @param maxVal the value to initialize distances with
     * @param defaultVal the value to initialize the source with
     * @param func the sum function
     * @return an ArrayList containing vertex objects which contain
     * the shortest distance and the path to the source node  
     */
    private static <W extends Number & Comparable<W>> ArrayList<Vertex<Integer, W>> dijkstra(W[][] graph, int src, W maxVal, W defaultVal, SumFunction func) {
        ArrayList<Vertex<Integer, W>> vertices = new ArrayList<>();
        PriorityQueue<Vertex<Integer, W>> unvisited = new PriorityQueue<>();
        for (int key = 0; key < graph.length; key++) {
            Vertex<Integer, W> v = new Vertex<>(key);
            v.setDistance(maxVal);
            if (key == src) {
                v.setDistance(defaultVal);
            }
            vertices.add(v);
            unvisited.add(v);
        }

        while (!unvisited.isEmpty()) {
            Vertex<Integer, W> u = unvisited.poll();

            int name = u.getLabel();
            for (int key = 0; key < graph.length; key++) {
                if (!graph[name][key].equals(defaultVal)) {
                    Vertex<Integer, W> v = vertices.get(key);
                    @SuppressWarnings("unchecked")
                    W dist = (W) func.sum(u.getDistance(), graph[name][key]);
                    if (dist.compareTo(v.getDistance()) < 0) {
                        unvisited.remove(v);
                        v.setDistance(dist);
                        unvisited.add(v);
                        v.setPrevious(u);
                    }
                }
            }
        }

        return vertices;
    }

    /***
     * Performs Dijkstra's Algorithm on an adjacency list.
     * 
     * @param <T> the label type
     * @param <W> the type of the edge weights 
     * @param graph the adjacency matrix
     * @param src the label of the source vertex
     * @param maxVal the value to initialize distances with
     * @param defaultVal the value that represents a non-edge
     * @param func the sum function
     * @return a Map that maps labels to vertex objects which contain
     * the shortest distance and the path to the source node  
     */
    private static <T, W extends Number & Comparable<W>> Map<T, Vertex<T, W>> dijkstra(Map<T, Map<T, W>> graph, T src, W maxVal, W defaultVal,
            SumFunction func) {
        HashMap<T, Vertex<T, W>> vertices = new HashMap<>();
        PriorityQueue<Vertex<T, W>> unvisited = new PriorityQueue<>();
        for (T key : graph.keySet()) {
            Vertex<T, W> v = new Vertex<>(key);

            v.setDistance(maxVal);
            if (key.equals(src)) {
                v.setDistance(defaultVal);
            }
            vertices.put(key, v);
            unvisited.add(v);
        }

        while (!unvisited.isEmpty()) {
            Vertex<T, W> u = unvisited.poll();

            T name = u.getLabel();
            for (T key : graph.get(name).keySet()) {
                if (!graph.get(name).getOrDefault(key, defaultVal).equals(defaultVal)) {
                    Vertex<T, W> v = vertices.get(key);
                    @SuppressWarnings("unchecked")
                    W dist = (W) func.sum(u.getDistance(), graph.get(name).get(key));
                    if (dist.compareTo(v.getDistance()) < 0) {
                        unvisited.remove(v);
                        v.setDistance(dist);
                        unvisited.add(v);
                        v.setPrevious(u);
                    }
                }
            }
        }
        return vertices;
    }

    /**
     * Converts an adjacency matrix to an adjacency list.
     * 
     * @param <T> the label type
     * @param <W> the type of the edge weight
     * @param matrix the adjacency matrix
     * @param labels the labels to be used in the adjacency list
     * @param defaultValue the value that represents a non-edge
     * @return the adjacency list
     * 
     * @exception NullPointerException if any parameters are null
     * @exception IllegalArgumentException is labels is an invalid size
     */
    private static <T, W extends Number & Comparable<W>> Map<T, Map<T, W>> toAdjList(W[][] matrix, T[] labels, W defaultValue) {
        if (matrix == null || labels == null) {
            throw new NullPointerException("parameters cannot be null");
        }
        else if (matrix.length != labels.length) {
            throw new IllegalArgumentException("labels and graph must be the same size");
        }
        
        Map<T, Map<T, W>> ret = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            Map<T, W> tmp = new HashMap<>();
            for (int j = 0; j < matrix[0].length; j++) {
                if (!matrix[i][j].equals(defaultValue)) {
                    T label = labels[j];
                    tmp.put(label, matrix[i][j]);
                }
            }
            T label = labels[i];
            ret.put(label, tmp);
        }
        return ret;
    }

    /**
     * A private helper method to convert an array of 
     * primitive types to an array of wrapper types. 
     * 
     * @param arr the array of primitives
     * @return the array of wrappers
     */
    private static Double[][] toDouble(double[][] arr) {
        Double[][] ret = new Double[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                ret[i][j] = arr[i][j];
            }
        }
        return ret;
    }

    /**
     * A private helper method to convert an array of 
     * primitive types to an array of wrapper types. 
     * 
     * @param arr the array of primitives
     * @return the array of wrappers
     */
    private static Integer[][] toInteger(int[][] arr) {
        Integer[][] ret = new Integer[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                ret[i][j] = arr[i][j];
            }
        }
        return ret;
    }

    /**
     * A private helper interface which allows an additive function to be passed as a parameter.
     */
    private interface SumFunction {
        Number sum(Number param1, Number param2);
    }

    /**
     * A class to represent a vertex and store its shortest path.
     */
    public static class Vertex<T, W extends Comparable<W>> implements Comparable<Vertex<T, W>> {
        private T label;
        private W distance;
        private Vertex<T, W> previous;

        public Vertex(T label) {
            this.label = label;
            this.distance = null;
            this.previous = null;
        }

        public W getDistance() {
            return distance;
        }

        public T getLabel() {
            return label;
        }

        public String getPath() {
            String ret = label.toString();
            if (this.getPrevious() != null) {
                ret += " <- " + previous.getPath();
            }
            return ret;
        }

        public Vertex<T, W> getPrevious() {
            return previous;
        }

        private void setDistance(W distance) {
            this.distance = distance;
        }

        private void setPrevious(Vertex<T, W> previous) {
            this.previous = previous;
        }

        public int compareTo(Vertex<T, W> other) {
            return this.getDistance().compareTo(other.getDistance());
        }

        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("Label: " + label);
            result.append(" Distance: " + distance);
            result.append(" Path: " + getPath());
            return result.toString();
        }
    }
}