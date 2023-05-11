package code;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        // Generating a cycle graph with 1000 vertices
        int size = 1000;
        Graph genGraph = new Graph(size);
        genGraph.createCycleGraph();
        
        // Printing graph to file
        try {
            genGraph.printGraphToFile("code/static/graph.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reading graph from file
        Solution s = new Solution();
        ArrayList<String> fileGraph = new ArrayList<>();
        try {
            fileGraph = s.readGraph("code/static/graph.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Initialize the graph read from file and print it
        Graph graph = s.createGraph(fileGraph);
        graph.printGraph();
        
        // Perform Smallest Last Vertex Ordering
        graph.SLVO();

        // Get Max Degree after deletion and the terminal clique of the graph
        // after performing SLVO
        graph.getMaxDegree();
        graph.getTerminalClique();

        // Color the graph based on SLVO
        graph.colorGraph();

        // Print the ordering data to file
        try {
            graph.printOrderToFile("code/static/order.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the coloring data to file
        try {
            graph.printColorToFile("code/static/color.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> readGraph(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));

        String firstLine = in.readLine();
        int size = Integer.parseInt(firstLine.trim());
        ArrayList<String> lines = new ArrayList<String>(size);
        String line;
        while ((line = in.readLine()) != null) {
            lines.add(line);
        }
        in.close();

        System.out.println(lines.toString());
        return lines;
    }

    public Graph createGraph(ArrayList<String> graphData) {
        Graph graph = new Graph(graphData.size());

        for (String line : graphData) {
            String[] vList = line.split(":");
            int vertex = Integer.parseInt(vList[0]);
            String nStr = vList[vList.length - 1].trim();
            String[] neighbors = nStr.split(" ");
            for (String c : neighbors) {
                int neighbor = Integer.parseInt(c);
                if (!graph.hasEdge(neighbor, vertex)) {
                    graph.addEdge(vertex, neighbor);
                }
            }
        }

        return graph;
    }
}


// Commented out code for reference
// Used for generating graphs in the project report
        // for (int size = 500; size < 5500; size += 500) {
        //     int E = 500 * 200;
        //     System.out.println("Vertices: " + size + " Edges: " + E);
        //     int color = 0;
        //     for (int i = 0; i < 10; i++) {
        //         Graph graph = new Graph(size);
        //         graph.createCompleteGraph();
        //         // graph.createCycleGraph();
        //         // graph.createUniformDistributionGraph(E);
        //         // graph.createSkewedDistributionGraph(E);
        //         // graph.createCustomDistributionGraph(E);
        //         graph.SLVO();
        //         // graph.LLVO();
        //         // graph.SODO();
        //         // graph.LODO();
        //         // graph.RNVO();
        //         // graph.DFSO();
        //         long graphStart = System.nanoTime();
        //         graph.colorGraph();
        //         long graphEnd = System.nanoTime();
        //         System.out.print((graphEnd - graphStart)/1000000 + " ");
        //         // System.out.print(graph.maxColor + " ");
        //     }
        //     System.out.println("\n============================\n");
        // }

        // int[] edges = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 256000, 512000, 1024000, 2048000, 4096000, 8192000, 16384000, 32768000};
        // int size = 10000;
        // // graph.createCustomDistributionGraph(size * 200);
        // // graph.createSkewedDistributionGraph(size * 200);
        
        // for (int E : edges) {
        //     System.out.println("Vertices: " + size + " Edges: " + E);
        //     for (int i = 0; i < 10; i++) {
        //         Graph graph = new Graph(size);
        //         graph.createUniformDistributionGraph(E);
        //         // graph.createSkewedDistributionGraph(E);
        //         // graph.createCustomDistributionGraph(E);
        //         long graphStart = System.nanoTime();
        //         // graph.SLVO();
        //         // graph.LLVO();
        //         // graph.SODO();
        //         // graph.LODO();
        //         // graph.RNVO();
        //         long graphEnd = System.nanoTime();
        //         System.out.print((graphEnd - graphStart)/1000 + " ");
        //     }
        //     System.out.println("\n============================\n");
        // }

        // int size = 5;
        // int size = 10000;
        // Graph graph = new Graph(size);
        // // graph.createUniformDistributionGraph(size * 200);
        // int E = (int)(0.5 * (size * (size - 1)/2));
        // graph.createUniformDistributionGraph(size * 200);
        // graph.getMaxDegree();
        // graph.addEdge(0,1);
        // graph.addEdge(1, 2);

        // graph.addEdge(0, 1);
        // graph.addEdge(0, 2);
        // graph.addEdge(0, 3);
        // graph.addEdge(1, 2);
        // graph.addEdge(1, 3);
        // graph.addEdge(2, 3);
        // graph.addEdge(2, 4);
        // graph.addEdge(3, 4);
        // graph.createCompleteGraph();
        // graph.createCycleGraph();
        // graph.createUniformDistributionGraph(size * 200);
        // graph.createSkewedDistributionGraph(size * 200);
        // graph.createCustomDistributionGraph(size * 200);
        // graph.printGraph();
        // try {
        //     graph.printDegreeDistribution("code/static/out.txt");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        
        // try {
        //     graph.printGraphToFile("code/static/graph.txt");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // long orderingStart = System.nanoTime();
        // graph.SLVO();
        // graph.LLVO();
        // graph.RNVO();
        // graph.SODO();
        // graph.LODO();
        // graph.DFSO();
        // long orderingEnd = System.nanoTime();
        // System.out.print((orderingEnd - orderingStart)/1000 + " ");
        
        // System.out.println("\n\n============================\n\n");
        // System.out.println("Smallest Last Vertex Ordering:");
        // for (int i = 0; i < graph.ordering.length; i++) {
        //     System.out.println("Vertex ID: " + graph.ordering[i].id + " Degree: " + graph.ordering[i].degree);
        // }
        // graph.getMaxDegree();
        // graph.getTerminalClique();
        // try {
        //     graph.printOrderToFile("code/static/order.txt");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // graph.colorGraph();
        
        // try {
        //     graph.printColorToFile("code/static/color.txt");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // try {
        //     graph.printOrderColor("code/static/out.txt");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // try {
        //     graph.printOrderDegree("out.txt");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // System.out.println("\n\n============================\n\n");
        // // System.out.println("Graph Coloring:");
        // for (int i = 0; i < graph.vertices.size(); i++) {
        //     System.out.println("Vertex ID: " + graph.vertices.get(i).id + " Degree: " + graph.ordering.get(i).degree + " Color: " + graph.vertices.get(i).color);
        // }

        // Solution s = new Solution();
        // ArrayList<String> fileGraph = new ArrayList<>();
        // try {
        //     fileGraph = s.readGraph();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // Graph graph = s.createGraph(fileGraph);
        // graph.printGraph();