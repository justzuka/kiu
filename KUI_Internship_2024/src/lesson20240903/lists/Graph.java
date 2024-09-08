package lesson20240903.lists;
import java.util.*;

import java.util.*;

public class Graph<T> {
    private Map<T, List<T>> adjList;
    private T firstVertex = null;
    private T lastVertex = null;
    public Graph() {
        adjList = new HashMap<>();
    }

    public void addVertex(T vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(T source, T destination) {
        if (firstVertex == null) firstVertex = source;
        lastVertex = destination;
        adjList.putIfAbsent(source, new ArrayList<>());
        adjList.putIfAbsent(destination, new ArrayList<>());
        adjList.get(source).add(destination);
        adjList.get(destination).add(source);  // For undirected graph
    }

    public void addDirectedEdge(T source, T destination) {
        if (firstVertex == null) firstVertex = source;
        lastVertex = destination;
        adjList.putIfAbsent(source, new ArrayList<>());
        adjList.putIfAbsent(destination, new ArrayList<>());
        adjList.get(source).add(destination);
    }

    public void removeVertex(T vertex) {
        adjList.values().forEach(e -> e.remove(vertex));
        adjList.remove(vertex);
    }

    public void removeEdge(T source, T destination) {
        List<T> edgesFromSource = adjList.get(source);
        List<T> edgesFromDestination = adjList.get(destination);
        if (edgesFromSource != null) {
            edgesFromSource.remove(destination);
        }
        if (edgesFromDestination != null) {
            edgesFromDestination.remove(source);
        }
    }

    public Map<T, List<T>> getAdjacencyList() {
        return adjList;
    }

    public void printGraph() {
        for (var entry : adjList.entrySet()) {
            System.out.println("Vertex " + entry.getKey() + " is connected to: " + entry.getValue());
        }
    }

    public int getOutDegree(T vertex) {
        return adjList.containsKey(vertex) ? adjList.get(vertex).size() : 0;
    }

    public int getInDegree(T vertex) {
        int inDegree = 0;
        for (T key : adjList.keySet()) {
            List<T> neighbors = adjList.get(key);
            if (neighbors.contains(vertex)) {
                inDegree++;
            }
        }
        return inDegree;
    }

    public Set<T> getVertices() {
        return adjList.keySet();
    }

    public T getFirstVertex() {
        return firstVertex;
    }
    public T getLastVertex() {
        return lastVertex;
    }
}
