# About the Project

A repo for demonstrating various programming concepts on a deeper level, like data structures and sorting algorithms. This acts both as a rigorous exercise, and as a demonstration of competence.

- [x] Data Structures
    - [x] LinkedList
    - [x] TreeMap
    - [x] TreeSet
- [x] Graph Algorithms
    - [x] Shortest Path
        - [x] Dijkstra
- [x] Sort Algorithms
    - [x] Iterative Sort
        - [x] Selection Sort
        - [x] Insertion Sort
        - [x] Bubble Sort
    - [x] Recursive Sort
        - [x] Merge Sort
        - [x] Quick Sort

## Data Structures

### LinkedList

The LinkedList class is an ordered collection designed as a doubly linked list which implements the `List` and `Deque` interfaces.

- Insert at First/Last Index:   O(1)
- Insert at Aribtrary Index:    O(N)
- Search by Value:              O(N)
- Search by Index:              O(N)
- Remove by Value:              O(N)
- Remove by Index:              O(N)

Test suite can be found in `LinkedListTests.java`.

#### Experiment

I tested several operations between my implementation and the standard library's implementation, the results of which are seen below. The experiment code can be found in `DataExperiments.java`.

`java.util.ArrayList`:
```
        Insert  Insert Back     Insert First
400     0.0     0.0             0.001
800     0.0     0.0             0.0
1600    0.0     0.001           0.001
3200    0.002   0.0             0.002
6400    0.008   0.001           0.009
12800   0.023   0.002           0.028 
25600   0.099   0.003           0.11

        Remove (Index)  Remove (Value)  Search (Index)  Search (Value)
400     0.0             0.004           0.003           0.004
800     0.0             0.007           0.002           0.009
1600    0.0             0.005           0.007           0.003
3200    0.001           0.014           0.029           0.01
6400    0.001           0.063           0.123           0.034
12800   0.002           0.249           0.538           0.146
25600   0.002           1.154           2.262          0.627
```

My LinkedList implementation:
```
        Insert  Insert Back     Insert First
400     0.0     0.0             0.001
800     0.002   0.0             0.0
1600    0.006   0.001           0.0
3200    0.025   0.0             0.001
6400    0.1     0.0             0.001
12800   0.533   0.0             0.001 
25600   2.907   0.001           0.0

        Remove (Index)  Remove (Value)  Search (Index)  Search (Value)
400     0.0             0.002           0.002           0.003
800     0.002           0.005           0.005           0.002
1600    0.006           0.01            0.028           0.009
3200    0.028           0.044           0.119           0.034
6400    0.127           0.223           0.575           0.153
12800   0.659           1.035           2.887           0.769
25600   3.518           5.196           16.597          5.17
```

In general, my LinkedList was significantly slower than the standard library's ArrayList across all operations (except Insert Back and Insert Front). This is likely because the standard library's ArrayList is implemented as a dynamic array and not as a doubly-linked list, which has an O(1) search instead of O(N).

### TreeMap

The TreeMap class is a sorted mapping designed as a Red-Black Tree which implements the `NavigableMap` interface. All operations used to balance the Red-Black Tree perform in O(logN) time. 

- Put:                          O(logN)
- Search:                       O(logN)
- Remove:                       O(logN)

Test suite can be found in `TreeMapTests.java`.

#### Experiment

I tested several operations between my implementation and the standard library's implementation, the results of which are seen below. The experiment code can be found in `DataExperiments.java`.

`java.util.TreeMap`:
```
        Put     Remove  Search
1600    0.0     0.0     0.0
3200    0.0     0.0     0.0
6400    0.0     0.0     0.0
12800   0.017   0.005   0.006
25600   0.009   0.015   0.014
51200   0.024   0.019   0.016
102400  0.048   0.046   0.036
204800  0.067   0.067   0.05
409600  0.216   0.166   0.15
```

My TreeMap implementation:
```
        Put     Remove  Search
1600    0.0     0.0     0.0
3200    0.0     0.0     0.0
6400    0.0     0.0     0.0
12800   0.0     0.016   0.0
25600   0.0     0.0     0.0
51200   0.02    0.012   0.016
102400  0.018   0.034   0.032
204800  0.069   0.084   0.08
409600  0.145   0.161   0.156
```

In general, there was a negligible difference between the results, which implies that my implementation is highly effecient.

### TreeSet

The TreeSet class is a sorted collection designed as a Red-Black Tree which implements the `NavigableSet` interface. It is heavily based on the TreeMap<K,V> implementation.

- Put:                          O(logN)
- Search:                       O(logN)
- Remove:                       O(logN)

Test suite can be found in `TreeSetTests.java`.

#### Experiment

I tested several operations between my implementation and the standard library's implementation, the results of which are seen below. The experiment code can be found in `DataExperiments.java`.

`java.util.TreeSet`:
```
        Put     Remove  Search
1600    0.001   0.0     0.0
3200    0.0     0.0     0.0
6400    0.0     0.0     0.0
12800   0.0     0.002   0.012
25600   0.002   0.016   0.0
51200   0.001   0.015   0.017
102400  0.03    0.034   0.019
204800  0.083   0.08    0.083
409600  0.21    0.227   0.214
```

My TreeSet implementation:
```
        Put     Remove  Search
1600    0.0     0.0     0.0
3200    0.0     0.0     0.0
6400    0.0     0.0     0.0
12800   0.0     0.0     0.0
25600   0.02    0.0     0.0
51200   0.014   0.005   0.015
102400  0.032   0.012   0.034
204800  0.079   0.077   0.062
409600  0.161   0.226   0.181
```

In general, there was a negligible difference between the results, which implies that my implementation is highly effecient.

## Graph Algorithms

### Dijkstra

The ShortestPath class can be used to perform Dijkstra's Algorithm on graphs represented as adjacency matrices or adjacency lists.

Test suite can be found in `ShortestPathTests.java`.


## Sorting Algorithms

Stable in-place algorithms were implemented for 3 iterative methods (Selection, Insertion, Bubble) and 2 recursive methods (Merge, Quick). As an experiment, these methods were tested against the basic Java implementation, `Arrays.sort()`, which uses the an O(NlogN) variant of Merge Sort called "TimSort":

```Test results (Time in ms): 
_____________
Size            10000   20000   40000   80000
 
Arrays.sort()   12      11      17      235
Selection Sort  260     934     3704    29842
Insertion Sort  169     721     3078    11891
Bubble Sort     311     1581    18681   68131
Merge Sort      387     496     6320    14027
Quick Sort      4       3       28      38
```

In general, Arrays.sort() outperformed all algorithms but Quick Sort.


The number of comparisons that were required to sort the data was also recorded for each algorithm:

```Test results (No. of comparisons):
_____________

Size            10000           20000           40000           80000

Selection Sort  49995000        199990000       799980000       3199960000
Insertion Sort  25107848        100274168       401317147       1600076473
Bubble Sort     49995000        199990000       799980000       3199960000
Merge Sort      120471          260912          561707          1204220
Quick Sort      155238          380244          737157          1592636
```

It is notable that Bubble Sort has much slower performance than Selection Sort, despite having the same number of comparisons. This is likely because it has a much higher number of swaps. 

Test suite can be found in `SortingAlgorithmTests.java`.