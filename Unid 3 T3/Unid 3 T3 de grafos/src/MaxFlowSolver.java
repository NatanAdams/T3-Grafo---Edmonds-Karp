import java.util.*;

public class MaxFlowSolver {
    private final int n;
    private final List<List<Edge>> graph;

    public MaxFlowSolver(int n, List<List<Edge>> graph) {
        this.n = n;
        this.graph = graph;
    }

    public int edmondsKarp(int source, int sink) {
        int maxFlow = 0;

        while (true) {
            Edge[] parentEdge = new Edge[n + 1];
            Queue<Integer> queue = new LinkedList<>();
            queue.add(source);

            while (!queue.isEmpty() && parentEdge[sink] == null) {
                int curr = queue.poll();

                for (Edge edge : graph.get(curr)) {
                    if (parentEdge[edge.to] == null && edge.to != source && edge.remainingCapacity() > 0) {
                        parentEdge[edge.to] = edge;
                        queue.add(edge.to);
                    }
                }
            }

            if (parentEdge[sink] == null) {
                break;
            }

            int bottleNeck = Integer.MAX_VALUE;
            for (Edge edge = parentEdge[sink]; edge != null; edge = parentEdge[edge.from]) {
                bottleNeck = Math.min(bottleNeck, edge.remainingCapacity());
            }

            for (Edge edge = parentEdge[sink]; edge != null; edge = parentEdge[edge.from]) {
                edge.augmentFlow(bottleNeck);
            }

            maxFlow += bottleNeck;
        }

        return maxFlow;
    }

    public boolean[] findReachableNodes(int source) {
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (Edge edge : graph.get(curr)) {
                if (!visited[edge.to] && edge.remainingCapacity() > 0) {
                    visited[edge.to] = true;
                    queue.add(edge.to);
                }
            }
        }
        return visited;
    }
}