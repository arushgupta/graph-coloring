import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        // for (int size = 500; size < 10000; size += 500) {
        //     System.out.println("Size: " + size);
        //     Graph graph = new Graph(size);
        //     for (int i = 0; i < 10; i++) {
        //         long graphStart = System.nanoTime();
        //         graph.createCompleteGraph();
        //         // graph.createCycleGraph();
        //         // graph.createUniformDistributionGraph(size * 200);
        //         // graph.createSkewedDistributionGraph(size * 200);
        //         // graph.createCustomDistributionGraph(size * 200);
        //         long graphEnd = System.nanoTime();
        //         System.out.print((graphEnd - graphStart)/1000 + " ");
        //     }
        //     System.out.println("\n============================\n");
        // }

        // int[] edges = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 512000};
        // int size = 10000;
        // Graph graph = new Graph(size);
        
        // for (int E : edges) {
        //     System.out.println("Edges: " + E);
        //     for (int i = 0; i < 10; i++) {
        //         long graphStart = System.nanoTime();
        //         graph.createUniformDistributionGraph(E);
        //         // graph.createSkewedDistributionGraph(E);
        //         // graph.createCustomDistributionGraph(E);
        //         long graphEnd = System.nanoTime();
        //         System.out.println((graphEnd - graphStart)/1000 + " ");
        //     }
        //     System.out.println("\n============================\n");
        // }

        // int size = 5;
        // Graph graph = new Graph(size);
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
        //     graph.printDegreeDistribution("out.txt");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        
        // try {
        //     graph.printGraphToFile("graph.txt");
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
        //     graph.printOrderToFile("order.txt");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // graph.colorGraph();
        
        // try {
        //     graph.printColorToFile("color.txt");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // System.out.println("\n\n============================\n\n");
        // // System.out.println("Graph Coloring:");
        // for (int i = 0; i < graph.vertices.size(); i++) {
        //     System.out.println("Vertex ID: " + graph.vertices.get(i).id + " Degree: " + graph.ordering.get(i).degree + " Color: " + graph.vertices.get(i).color);
        // }

        Solution s = new Solution();
        ArrayList<String> fileGraph = new ArrayList<>();
        try {
            fileGraph = s.readGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graph graph = s.createGraph(fileGraph);
        graph.printGraph();
    }

    public ArrayList<String> readGraph() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("graph.txt"));

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
