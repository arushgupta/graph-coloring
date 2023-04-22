import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class myGraph {
    
    class Vertex {
        int id;
        int degree;
        int color;
        List<Integer> neighbors;

        public Vertex(int id, int size) {
            this.id = id;
            this.degree = 0;
            this.color = -1;
            this.neighbors = new ArrayList<Integer>(size);
        }

        public void addNeighbor(int neighborId) {
            neighbors.add(neighborId);
            degree++;
        }

        public List<Integer> getNeighbors() {
            return neighbors;
        }
    }
    
    List<Vertex> vertices;
    int size = 0;
    int maxColor = 0;

    public myGraph(int size) {
        this.size = size;
        vertices = new ArrayList<Vertex>(size);
        for (int i = 0; i < size; i++) {
            vertices.add(i, new Vertex(i, size));
        }
    }

    public void addEdge(int src, int dest) {
        Vertex srcVertex = vertices.get(src);
        Vertex destVertex = vertices.get(dest);

        srcVertex.addNeighbor(dest);
        destVertex.addNeighbor(src);
    }

    public void createCompleteGraph() {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                addEdge(i, j);
            }
        }
    }
    
    public void createCycleGraph() {
        for (int i = 0; i < size; i++) {
            addEdge(i, (i+1) % size);
        }
    }

    public void printGraph() {
        for (Vertex vertex : vertices) {
            System.out.print("Vertex " + vertex.id + ": ");
            for (int neighborId : vertex.getNeighbors()) {
                System.out.print(neighborId + " ");
            }
            System.out.println("Degree: " + vertex.degree + ", Color: " + vertex.color);
        }
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

    public static void main(String[] args) {
        int size = 1000;
    
        myGraph graph = new myGraph(size);
        // graph.createCompleteGraph();
        // graph.createCycleGraph();
        int E = 200;
        graph.createEvenDistGraph(E);

        graph.printGraph();

        

    }


    // public void createSkewedGraph(int E) {
    //     int maxEdges = size * (size - 1) / 2;

    //     if (E > maxEdges) {
    //         System.out.println("Skewed Graph cannot have this many edges.");
    //     }

    //     for (int i = 0; i < E/4; i++) {
    //         int v1 = 0;
    //         int v2 = 0;

    //     }

    // }
    
}
