import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        int size = 5;
        int E = 200;
        Graph graph = new Graph(size);

        // graph.addEdge(0,1);
        // graph.addEdge(1, 2);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);

        graph.SLVO();
        graph.colorGraph();

        // long startTime = System.currentTimeMillis();
        // graph.createCompleteGraph();
        // graph.createCycleGraph();
        // graph.createUniformDistGraph(E);
        // graph.createSkewedDistGraph(size * E);
        // graph.createMyDistGraph(E);
        // long endTime = System.currentTimeMillis();

        graph.printGraph();
        // System.out.println("Time Taken: " + (endTime - startTime));

    }
}
