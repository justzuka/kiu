package project_assembly;

import lesson20240903.lists.Graph;

import java.util.*;

public class EulerianPathApproach {

    public static <T> List<T> findEulerianPath(Graph<T> graph) {
        T startVertex = graph.getFirstVertex();
        T endVertex = graph.getLastVertex();

        Stack<T> stack = new Stack<>();
        List<T> path = new ArrayList<>();

        stack.push(startVertex);

        while (!stack.isEmpty()) {
            T current = stack.peek();
            if (graph.getOutDegree(current) == 0) {
                path.add(stack.pop());
            } else {
                List<T> neighbors = graph.getAdjacencyList().get(current);
                T next = neighbors.remove(0);
                stack.push(next);
            }
        }

        Collections.reverse(path);
        return path;
    }

    // Function to reconstruct the original string from the Eulerian Path
    public static String reconstructStringFromPath(List<String> eulerianPath) {
        StringBuilder reconstructed = new StringBuilder(eulerianPath.get(0));

        // Each subsequent k-mer shares k-1 characters with the previous one
        for (int i = 1; i < eulerianPath.size(); i++) {
            String nextKmer = eulerianPath.get(i);
            reconstructed.append(nextKmer.charAt(nextKmer.length() - 1));
        }

        return reconstructed.toString();
    }
}
