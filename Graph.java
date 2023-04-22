import java.util.ArrayList;
import java.util.List;

public class Graph {
    
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

    public Graph(int size) {
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

    // Checks whether two vertices share an edge
    // Runs in O(1) - Constant
    // https://www.baeldung.com/java-collections-complexity
    public boolean hasEdge(int src, int dest) {
        return vertices.get(src).neighbors.contains(dest) && vertices.get(dest).neighbors.contains(src);
    }

    // Create a complete graph
    public void createCompleteGraph() {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                addEdge(i, j);
            }
        }
    }
    
    // Create a cycle graph
    public void createCycleGraph() {
        for (int i = 0; i < size; i++) {
            addEdge(i, (i+1) % size);
        }
    }

    // Print graph
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
    public void createUniformDistGraph(int E) {
        int maxEdges = size * (size - 1) / 2;

        if (E > maxEdges) {
            System.out.println("Evenly Dsitributed Graph can't have this many edges.");
            return;
        }

        for (int i = 0; i < E; i++) {
            int v1 = Utility.getRandomNumber(0, size - 1);
            int v2 = Utility.getRandomNumber(0, size - 1);

            if (v1 == v2 || v1 == size || v2 == size || hasEdge(v1, v2)) {
                i--;
                continue;
            }
            addEdge(v1, v2);
            addEdge(v2, v1);
        }
    }
    
    // Create a skewed graph
    public void createSkewedDistGraph(int E) {
        int maxEdges = size * (size - 1) / 2;

        if (E > maxEdges) {
            System.out.println("Too many edges");
            return;
        }
        // 
        for (int i = 0; i < E; i++) {
            int v1 = Utility.getSkewedNumber(0, size - 1);
            int v2 = Utility.getSkewedNumber(0, size - 1);

            if (v1 == v2 || hasEdge(v1, v2)) {
                i--;
                continue;
            }
            addEdge(v1, v2);
            addEdge(v2, v1);
        }
    }

    // Create graph based on my distribution as described below:
    // Two-third of the edges are between 20% of the vertices
    // A third of the edges are between 80% of the vertices
    public void createMyDistGraph(int E) {
        int maxEdges = size * (size - 1) / 2;

        if (E > maxEdges) {
            System.out.println("Skewed Graph can't have this many edges.");
            return;
        }

        int lightPart = (int)(size/20);
        int twoThird = (int)(2*E/3);
        
        for (int i = 0; i < twoThird; i++) {
            int v1 = Utility.getRandomNumber(0, lightPart - 1);
            int v2 = Utility.getRandomNumber(0, lightPart - 1);
            
            if (v1 == v2 || hasEdge(v1, v2)) {
                i--;
                continue;
            }
            addEdge(v1, v2);
            addEdge(v2, v1);
        }
        
        int oneThird = (int)(E/3);
        for (int i = 0; i < oneThird; i++) {
            int v1 = Utility.getRandomNumber(lightPart, size - 1);
            int v2 = Utility.getRandomNumber(lightPart, size - 1);

            if (v1 == v2 || hasEdge(v1, v2)) {
                i--;
                continue;
            }
            addEdge(v1, v2);
            addEdge(v2, v1);
        }
    }
}
