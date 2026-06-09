public class Edge {
    public int from;
    public int to;
    public int capacity;
    public int flow;
    public Edge reverse;

    public Edge(int from, int to, int capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;
    }

    public int remainingCapacity() {
        return capacity - flow;
    }

    public void augmentFlow(int bottleNeck) {
        flow += bottleNeck;
        reverse.flow -= bottleNeck;
    }
}