import java.io.IOException;

public class Solution {
    public static void main(String[] args) {
        for (int size = 500; size < 10000; size += 500) {
            System.out.println("Size: " + size);
            Graph graph = new Graph(size);
            for (int i = 0; i < 10; i++) {
                long graphStart = System.nanoTime();
                graph.createCompleteGraph();
                // graph.createCycleGraph();
                // graph.createUniformDistributionGraph(size * 200);
                // graph.createSkewedDistributionGraph(size * 200);
                // graph.createCustomDistributionGraph(size * 200);
                long graphEnd = System.nanoTime();
                System.out.print((graphEnd - graphStart)/1000 + " ");
            }
            System.out.println("\n============================\n");
        }

        int[] edges = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 512000};
        int size = 10000;
        Graph graph = new Graph(size);
        
        for (int E : edges) {
            System.out.println("Edges: " + E);
            for (int i = 0; i < 10; i++) {
                long graphStart = System.nanoTime();
                graph.createUniformDistributionGraph(E);
                // graph.createSkewedDistributionGraph(E);
                // graph.createCustomDistributionGraph(E);
                long graphEnd = System.nanoTime();
                System.out.println((graphEnd - graphStart)/1000 + " ");
            }
            System.out.println("\n============================\n");
        }

        try {
            graph.printDegreeDistribution("out.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // int size = 5;
        // Graph graph = new Graph(size);
        // graph.addEdge(0,1);
        // graph.addEdge(1, 2);

        // graph.addEdge(0, 1);
        // graph.addEdge(0, 2);
        // graph.addEdge(1, 3);
        // graph.addEdge(2, 3);
        // graph.addEdge(2, 4);
        // graph.addEdge(3, 4);
        // graph.printGraph();


        long orderingStart = System.nanoTime();
        graph.SLVO();
        graph.LLVO();
        graph.RNVO();
        graph.SODO();
        graph.LODO();
        graph.DFSO();
        long orderingEnd = System.nanoTime();
        System.out.print((orderingEnd - orderingStart)/1000 + " ");
        
        // graph.colorGraph();
        
        // System.out.println("\n\n============================\n\n");
        // System.out.println("Smallest Last Vertex Ordering:");
        // for (int i = 0; i < graph.ordering.size(); i++) {
        //     System.out.println("Vertex ID: " + graph.ordering.get(i).id + " Degree: " + graph.ordering.get(i).degree);
        // }
        // System.out.println("Graph Coloring:");
        // for (int i = 0; i < graph.vertices.size(); i++) {
        //     System.out.println("Vertex ID: " + graph.vertices.get(i).id + " Color: " + graph.vertices.get(i).color);
        // }

    }
}
