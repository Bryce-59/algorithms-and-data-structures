# About the Project

A repo for demonstrating various programming concepts on a deeper level, like data structures and sorting algorithms. This acts both as a rigorous exercise, and as a demonstration of competence.

- [x] Data Structures
    - [x] LinkedList
    - [x] TreeMap
    - [x] TreeSet
- [x] Graph Algorithms
    - [x] Dijkstra
- [x] Sort Algorithms
    - [x] Selection Sort
    - [x] Insertion Sort
    - [x] Bubble Sort
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


### TreeMap

The TreeMap class is a sorted mapping designed as a Red-Black Tree which implements the `NavigableMap` interface. All operations used to balance the Red-Black Tree perform in O(logN) time. 

- Put:                          O(logN)
- Search:                       O(logN)
- Remove:                       O(logN)

Test suite can be found in `TreeMapTests.java`.

### TreeSet

The TreeSet class is a sorted collection designed as a Red-Black Tree which implements the `NavigableSet` interface. It is heavily based on the TreeMap<K,V> implementation.

- Put:                          O(logN)
- Search:                       O(logN)
- Remove:                       O(logN)

Test suite can be found in `TreeSetTests.java`.

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