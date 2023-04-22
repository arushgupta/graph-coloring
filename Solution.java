public class Solution {
    public static void main(String[] args) {
        int size = 10000;
        int E = 200;
        Graph graph = new Graph(size);

        long startTime = System.currentTimeMillis();
        // graph.createCompleteGraph();
        // graph.createCycleGraph();
        // graph.createUniformDistGraph(E);
        graph.createSkewedDistGraph(size * E);
        // graph.createMyDistGraph(E);
        long endTime = System.currentTimeMillis();

        // graph.printGraph();
        System.out.println("Time Taken: " + (endTime - startTime));

    }
}
