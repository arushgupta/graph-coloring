import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Graph {
    
    class Vertex {
        int id;
        int degree;
        int color;
        List<Integer> neighbors;

        Vertex prev;
        Vertex next;

        public Vertex(int id, int size) {
            this.id = id;
            this.degree = 0;
            this.color = 0;
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
    List<Vertex> ordering;
    int size = 0;
    int maxColor = 0;

    public Graph(int size) {
        this.size = size;
        vertices = new ArrayList<Vertex>(size);
        for (int i = 0; i < size; i++) {
            vertices.add(i, new Vertex(i, size));
        }
        ordering = new ArrayList<Vertex>(size);
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

    // Smallest Last Vertex Ordering
    public void SLVO() {
        Vertex[] degrees = new Vertex[size];
        int[] newDegrees = new int[size];
        boolean[] deleted = new boolean[size];

        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertex = vertices.get(i);
            if (degrees[vertex.degree] != null) {
                degrees[vertex.degree].prev = vertex;
                vertex.next = degrees[vertex.degree];
            }
            degrees[vertex.degree] = vertex;
            newDegrees[i] = vertex.degree;
        }
        
        for(int i = 0; i < degrees.length; i++) {
            if(degrees[i] == null) {
                continue;
            }
            Vertex temp = degrees[i];
            ordering.add(temp);
            degrees[i] = temp.next;

            if (temp.next != null) {
                temp.next.prev = null;
            }

            temp.next = null;
            deleted[temp.id] = true;

            for(int neighborId: temp.neighbors) {
                if(deleted[neighborId] == true) {
                    continue;
                }
                Vertex neighbor = vertices.get(neighborId);
                if(neighbor.next != null) {
                    neighbor.next.prev = neighbor.prev;
                }
                if (neighbor.prev != null) {
                    neighbor.prev.next = neighbor.next;
                } else {
                    degrees[newDegrees[neighborId]] = neighbor.next;
                }
                newDegrees[neighborId] -= 1;
                neighbor.prev = null;
                neighbor.next = degrees[newDegrees[neighborId]];

                if (degrees[newDegrees[neighborId]] != null) {
                    degrees[newDegrees[neighborId]].prev = neighbor;
                }
                degrees[newDegrees[neighborId]] = neighbor;
            }
            if (i == 0) {
                i--;
                continue;
            }
            i -= 2;
        }
        Collections.reverse(ordering);
    }

    // Largest Last Degree Ordering
    public void LLDO() {
        SLVO();
        Collections.reverse(ordering);
    }

    // Random Vertex Ordering
    public void randomOrder() {
        for(Vertex v: vertices) {
            ordering.add(v);
        }
        Collections.shuffle(ordering);
    }

    // Smallest Original Degree Ordering
    public void SODO() {
        Vertex[] degrees = new Vertex[size];

        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertex = vertices.get(i);
            if (degrees[vertex.degree] != null) {
                degrees[vertex.degree].prev = vertex;
                vertex.next = degrees[vertex.degree];
            }
            degrees[vertex.degree] = vertex;
        }

        for(int i = 0; i < degrees.length; i++) {
            Vertex curr = degrees[i];
            while (curr != null) {
                ordering.add(curr);
                curr = curr.next;
            }
        }
    }

    // Largest Original Degree Ordering
    public void LODO() {
        SODO();
        Collections.reverse(ordering);
    }

    public void colorGraph() {
        for (Vertex vertex : ordering) {
            vertex.color = colorVertex(vertex);
            if (vertex.color > maxColor) {
                maxColor = vertex.color;
            }
        }
    }

    public int colorVertex(Vertex vertex) {
        int[] colors = new int[size];

        for (int i = 0; i < vertex.neighbors.size(); i++) {
            int neighbor = vertex.neighbors.get(i);
            colors[i] = neighbor;
        }
        
        int num = 0;
        for (int i = 0; i < colors.length; i++) {
            num = colors[i];
            while(0 < num && num <= colors.length && colors[num - 1] != num) {
                int temp = colors[i];
                colors[i] = colors[num - 1];
                colors[num - 1] = temp;
                num = colors[i];
            }
        }
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] != i + 1) {
                return i + 1;
            }
        }
        return colors.length + 1;
    }
}