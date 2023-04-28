public class Solution {
    public static void main(String[] args) {
        int size = 5;
        int E = 8;
        Graph graph = new Graph(size);

        // graph.addEdge(0,1);
        // graph.addEdge(1, 2);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);

        // graph.colorGraph();
        // long startTime = System.currentTimeMillis();
        // graph.createCompleteGraph();
        // graph.createCycleGraph();
        // graph.createUniformDistGraph(E);
        // graph.createSkewedDistGraph(E);
        // graph.createMyDistGraph(E);
        // graph.LLDO();
        graph.printGraph();
        graph.SLVO();
        graph.colorGraph();
        System.out.println("\n\n============================\n\n");
        // System.out.println("Smallest Last Vertex Ordering:");
        // for (int i = 0; i < graph.ordering.size(); i++) {
        //     System.out.println("Vertex ID: " + graph.ordering.get(i).id + " Degree: " + graph.ordering.get(i).degree);
        // }
        System.out.println("Graph Coloring:");
        for (int i = 0; i < graph.vertices.size(); i++) {
            System.out.println("Vertex ID: " + graph.vertices.get(i).id + " Color: " + graph.vertices.get(i).color);
        }
        // long endTime = System.currentTimeMillis();

        // System.out.println("Time Taken: " + (endTime - startTime));

    }
}
