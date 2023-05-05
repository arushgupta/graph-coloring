import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Graph {
    
    class Vertex {
        int id;
        int degree;
        int color;
        ArrayList<Integer> neighbors;

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
    }
    
    ArrayList<Vertex> vertices;
    ArrayList<Vertex> ordering;
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
            for (int neighborId : vertex.neighbors) {
                System.out.print(neighborId + " ");
            }
            System.out.println("Degree: " + vertex.degree + ", Color: " + vertex.color);
        }
    }
    
    // Create an Evenly Distributed Graph
    public void createUniformDistributionGraph(int E) {
        int maxEdges = size * (size - 1) / 2;

        if (E > maxEdges) {
            System.out.println("Too many edges");
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
        }
    }
    
    // Create a skewed graph
    public void createSkewedDistributionGraph(int E) {
        int maxEdges = size * (size - 1) / 2;

        if (E > maxEdges) {
            System.out.println("Too many edges");
            return;
        }
        
        for (int i = 0; i < E; i++) {
            int v1 = Utility.getSkewedNumber(size);
            int v2 = Utility.getSkewedNumber(size);

            if (v1 == v2 || hasEdge(v1, v2)) {
                i--;
                continue;
            }
            addEdge(v1, v2);
        }
    }

    // Create graph using custom distribution as described below:
    // Unlike the skewed distribution, the custom distribution favors
    // addition of vertices with a higher ID
    public void createCustomDistributionGraph(int E) {
        int maxEdges = size * (size - 1) / 2;

        if (E > maxEdges) {
            System.out.println("Too many edges");
            return;
        }

        for(int i = 0; i < E; i++) {
            int v1 = Utility.getCustomNumber(size);
            int v2 = Utility.getCustomNumber(size);
            
            if (v1 == v2 || hasEdge(v1, v2)) {
                i--;
                continue;
            }
            addEdge(v1, v2);
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

            Vertex currVertex = degrees[i];
            ordering.add(currVertex);
            // Updates the degree of the vertex to reflect the degree on deletion
            ordering.get(ordering.indexOf(currVertex)).degree = newDegrees[currVertex.id];
            degrees[i] = currVertex.next;

            if (currVertex.next != null) {
                currVertex.next.prev = null;
            }

            currVertex.next = null;
            deleted[currVertex.id] = true;

            for(int neighborId: currVertex.neighbors) {
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

    // Largest Last Vertex Ordering
    public void LLVO() {
        SLVO();
        Collections.reverse(ordering);
    }

    // Random Vertex Ordering
    public void RNVO() {
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

    // Depth-First Search Ordering
    public void DFSO() {
        boolean[] visited = new boolean[size];
        
        for (Vertex vertex : vertices) {
            if (!visited[vertex.id]) {
                dfs(vertex, visited);
            }
        }
        Collections.reverse(ordering);
    }

    public void dfs(Vertex vertex, boolean[] visited) {
        visited[vertex.id] = true;
        for (int neighborId : vertex.neighbors) {
            Vertex neighbor = vertices.get(neighborId);
            if (!visited[neighborId]) {
                dfs(neighbor, visited);
            }
        }
        ordering.add(vertex);
    }

    // Color the graph based on the ordering
    public void colorGraph() {
        for (Vertex vertex : ordering) {
            vertex.color = colorVertex(vertex);
            if (vertex.color > maxColor) {
                maxColor = vertex.color;
            }
        }
    }

    // Color individual vertices
    // Reduced vertex coloring to First Missing Positive Integer
    // Link to the problem: https://leetcode.com/problems/first-missing-positive/
    public int colorVertex(Vertex vertex) {
        int[] colors = new int[size];

        // Initialize list with current neighbor color
        for (int i = 0; i < vertex.neighbors.size(); i++) {
            int neighbor = vertex.neighbors.get(i);
            colors[i] = vertices.get(neighbor).color;
        }
        
        for (int i = 0; i < colors.length; i++) {
            int val = colors[i];
            while(0 < val && val <= colors.length && colors[val - 1] != val) {
                int temp = colors[i];
                colors[i] = colors[val - 1];
                colors[val - 1] = temp;
                val = colors[i];
            }
        }

        // Find the first gap in the sequence of colors
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] != i + 1) {
                return i + 1;
            }
        }

        // No gap was found, so get next color
        return colors.length + 1;
    }

    public void printDegreeDistribution(String filename) throws IOException {
        FileWriter fWriter = new FileWriter(filename);
        for (Vertex vertex : vertices) {
            fWriter.write(vertex.id + " " + vertex.degree + "\n");
        }
        fWriter.close();
    }
}