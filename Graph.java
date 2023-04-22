import java.util.ArrayList;
import java.lang.Math;

public class Graph {
    private Edge[] edges = null;
    private Vertex[] vertices = null;
    private Vertex[] ordering = null;

    public int size = 0;
    public int maxColor = 0;
    
    private class Edge {
        int dest = -1;
        Edge next = null;
        
        public Edge(int d, Edge n) {
            dest = d;
            next = n;
        }
    }
    private class Vertex {
        int id = 0;
        int degree = 0;
        int color = 0;
    }


    

    // Instantiate an emptyGraph graph
    public Graph() { emptyGraph(); }

    // Build a graph based on vertices
    public void buildGraph(final int V) {
        edges = new Edge[V];
        vertices = new Vertex[V];
        for (int i = 0; i < V; i++) {
            vertices[i].id = i;
            edges[i] = null;
        }
        size = V;
    }

    // Clear the graph
    public void emptyGraph() {
        size = 0;
        maxColor = 0; 
        vertices = null;
        ordering = null;
        edges = null;
    }

    // Add an edge between two vertices
    public void addEdge(final int vertex1, final int vertex2) {
        Edge e = new Edge(vertex2, edges[vertex1]);
        edges[vertex1] = e;
        vertices[vertex1].degree++;
    }

    // Get an edge between two vertices
    public Edge getEdge(final int vertex1, final int vertex2) {
        Edge currEdge = edges[vertex1];
        while (currEdge != null) {
            if (currEdge.dest == vertex2) {
                return currEdge;
            }
            currEdge = currEdge.next;
        }
        return null;
    }

    // Create a complete graph
    public void createCompleteGraph() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if (i == j) {
                    continue;
                }
                addEdge(i, j);
            }
        }
    }

    // Create a cycle graph
    public void createCycleGraph() {
        for (int i = 0; i < size - 1; i++) {
            addEdge(i, i + 1);
            addEdge(i + 1, i);
        }
        addEdge(size - 1, 0);
        addEdge(0, size - 1);
    }

    // Create an Evenly Distributed Graph
    public void createEvenDistGraph(int E) {
        int maxEdges = size * (size - 1) / 2;

        if (E > maxEdges) {
            System.out.println("Evenly Dsitributed Graph can't have this many edges.");
            return;
        }

        for (int i = 0; i < E; i++) {
            int v1 = Utility.get_random_number(0, size);
            int v2 = Utility.get_random_number(0, size);

            if (v1 == v2 || v1 == size || v2 == size) {
                i--;
                continue;
            }
            addEdge(v1, v2);
            addEdge(v2, v1);
        }

    }

    public void createSkewedGraph(int E) {
        int maxEdges = size * (size - 1) / 2;

        if (E > maxEdges) {
            System.out.println("Skewed Graph cannot have this many edges.");
        }

        for (int i = 0; i < E/4; i++) {
            int v1 = 0;
            int v2 = 0;

        }

    }

    public static void main(String[] args) {
        final int size = 1000;
    
        Graph graph = new Graph();
    }
    
}
