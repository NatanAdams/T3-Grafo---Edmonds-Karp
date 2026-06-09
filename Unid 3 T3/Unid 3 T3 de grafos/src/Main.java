import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        int[][] originalEdges = new int[m][2];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            originalEdges[i][0] = u;
            originalEdges[i][1] = v;

            Edge e1 = new Edge(u, v, 1);
            Edge e2 = new Edge(v, u, 1);

            e1.reverse = e2;
            e2.reverse = e1;

            graph.get(u).add(e1);
            graph.get(v).add(e2);
        }

        MaxFlowSolver solver = new MaxFlowSolver(n, graph);

        int minStreetsToClose = solver.edmondsKarp(1, n);
        System.out.println(minStreetsToClose);

        boolean[] visited = solver.findReachableNodes(1);

        for (int i = 0; i < m; i++) {
            int u = originalEdges[i][0];
            int v = originalEdges[i][1];

            if ((visited[u] && !visited[v]) || (!visited[u] && visited[v])) {
                System.out.println(u + " " + v);
            }
        }
    }
}