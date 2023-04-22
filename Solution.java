public class Solution {
    public static void main(String[] args) {
        int size = 10000;
            Graph graph = new Graph(size);
            // graph.createCompleteGraph();
            // graph.createCycleGraph();
            int E = 200;
            // graph.createUniformDistGraph(E);
            graph.createSkewedDistGraph(E);
            // graph.createMyDistGraph(E);
            graph.printGraph();
    }
}
