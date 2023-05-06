package code;
import java.io.FileWriter;
import java.io.IOException;

public class Graph {
    class Vertex {
        int id;
        int degree;
        int color;
        boolean[] neighbors;

        Vertex prev;
        Vertex next;

        public Vertex(int id, int size) {
            this.id = id;
            this.degree = 0;
            this.color = 0;
            this.neighbors = new boolean[size];
        }

        public void addNeighbor(int neighborId) {
            this.neighbors[neighborId] = true;
            this.degree++;
        }
    }
    
    Vertex[] vertices;
    Vertex[] ordering;
    int orderingCap = 0;
    
    int size = 0;
    int maxColor = 0;
    int maxDegree = 0;
    int terminalCliqueSize = 0;

    public Graph(int size) {
        this.size = size;
        vertices = new Vertex[size];
        for (int i = 0; i < size; i++) {
            vertices[i] = new Vertex(i, size);
        }
        ordering = new Vertex[size];
    }

    public void addEdge(int src, int dest) {
        Vertex srcVertex = vertices[src];
        Vertex destVertex = vertices[dest];

        srcVertex.addNeighbor(dest);
        destVertex.addNeighbor(src);
    }

    // Checks whether two vertices share an edge
    // Runs in O(1) - Constant
    public boolean hasEdge(int src, int dest) {
        return vertices[src].neighbors[dest] && vertices[dest].neighbors[src];
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
            addEdge(i, (i + 1) % size);
        }
    }

    // Print graph
    public void printGraph() {
        for (Vertex vertex : vertices) {
            System.out.print("Vertex " + vertex.id + ": ");

            boolean[] neighbors = vertex.neighbors;
            for (int neighborId = 0; neighborId < neighbors.length; neighborId++) {
                if (neighbors[neighborId]) {
                    System.out.print(neighborId + " ");
                }
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

    public void reverseOrdering() {
        int n = ordering.length;
        for(int i = 0; i < n / 2; i++) {
            swapOrderingElem(i, n - i - 1);
        }
    }

    // Implementing the Fisher-Yates Shuffle
    // Source: https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
    public void shuffleOrdering() {
        int n = ordering.length;
        for(int i = n - 1; i > 0; i--) {
            int j = Utility.getShuffledIndex(i);
            swapOrderingElem(i, j);
        }
    }

    // Swapping functionality abstracted to separate function to prevent code repition
    public void swapOrderingElem(int i, int j) {
        Vertex temp = ordering[i];
        ordering[i] = ordering[j];
        ordering[j] = temp;
    }

    // Smallest Last Vertex Ordering
    public void SLVO() {
        Vertex[] degrees = new Vertex[size];
        int[] newDegrees = new int[size];
        boolean[] deleted = new boolean[size];

        for (int i = 0; i < vertices.length; i++) {
            Vertex vertex = vertices[i];
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
            ordering[orderingCap] = currVertex;
            
            // Updates the degree of the vertex to reflect the degree on deletion
            ordering[orderingCap].degree = newDegrees[currVertex.id];
            orderingCap++;
            
            degrees[i] = currVertex.next;

            if (currVertex.next != null) {
                currVertex.next.prev = null;
            }

            currVertex.next = null;
            deleted[currVertex.id] = true;

            boolean[] neighbors = currVertex.neighbors;
            for (int neighborId = 0; neighborId < neighbors.length; neighborId++) {
                // Check if vertex with neighborId is a neighbor
                if (neighbors[neighborId]) {
                    if(deleted[neighborId] == true) {
                        continue;
                    }
                    Vertex neighbor = vertices[neighborId];
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
            }
            if (i == 0) {
                i--;
                continue;
            }
            i -= 2;
        }
        reverseOrdering();
    }

    // Largest Last Vertex Ordering
    public void LLVO() {
        SLVO();
        reverseOrdering();
    }

    // Random Vertex Ordering
    public void RNVO() {
        for(Vertex v: vertices) {
            ordering[orderingCap] = v;
            orderingCap++;
        }
        shuffleOrdering();
    }

    // Smallest Original Degree Ordering
    public void SODO() {
        Vertex[] degrees = new Vertex[size];

        for (int i = 0; i < vertices.length; i++) {
            Vertex vertex = vertices[i];
            if (degrees[vertex.degree] != null) {
                degrees[vertex.degree].prev = vertex;
                vertex.next = degrees[vertex.degree];
            }
            degrees[vertex.degree] = vertex;
        }

        for(int i = 0; i < degrees.length; i++) {
            Vertex curr = degrees[i];
            while (curr != null) {
                ordering[orderingCap] = curr;
                orderingCap++;
                curr = curr.next;
            }
        }
    }

    // Largest Original Degree Ordering
    public void LODO() {
        SODO();
        reverseOrdering();
    }

    // Depth-First Search Ordering
    public void DFSO() {
        boolean[] visited = new boolean[size];
        
        for (Vertex vertex : vertices) {
            if (!visited[vertex.id]) {
                dfs(vertex, visited);
            }
        }
        reverseOrdering();
    }

    public void dfs(Vertex vertex, boolean[] visited) {
        visited[vertex.id] = true;
        boolean[] neighbors = vertex.neighbors;
        for (int neighborId = 0; neighborId < neighbors.length; neighborId++) {
            if (neighbors[neighborId]) {
                Vertex neighbor = vertices[neighborId];
                if (!visited[neighborId]) {
                    dfs(neighbor, visited);
                }
            }
        }
        ordering[orderingCap] = vertex;
        orderingCap++;
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
        boolean[] neighbors = vertex.neighbors;
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i]) {
                colors[i] = vertices[i].color;
            }
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

    public void printGraphToFile(String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        fileWriter.write(size + "\n");
        for (Vertex vertex : vertices) {
            fileWriter.write(vertex.id + ": ");
            boolean[] neighbors = vertex.neighbors;
            for (int neighborId = 0; neighborId < neighbors.length; neighborId++) {
                if (neighbors[neighborId]) {
                    fileWriter.write(neighborId + " ");
                }
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

    public void printOrderToFile(String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        fileWriter.write("Largest Degree: " + maxDegree + "\n"); // Only for SLVO
        fileWriter.write("Terminal Clique: " + terminalCliqueSize + "\n"); // Only for SLVO
        for (Vertex vertex : ordering) {
            fileWriter.write("Vertex ID: " + vertex.id + " Degree: " + vertex.degree + "\n");
        }
        fileWriter.close();
    }

    public void printColorToFile(String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        fileWriter.write("Max Color: " + maxColor + "\n");
        for (Vertex vertex : vertices) {
            fileWriter.write("Vertex ID: " + vertex.id + " Degree: " + vertex.degree + " Color: " + vertex.color + "\n");
        }
        fileWriter.close();
    }
    
    // Only for SLVO
    public void getMaxDegree() {
        for (Vertex vertex : ordering) {
            if (maxDegree < vertex.degree) {
                maxDegree = vertex.degree;
            }
        }
    }

    // Only for SLVO
    public void getTerminalClique() {
        int longest = 1;
        int current = 1;
        for (int i = 1; i < ordering.length; i++) {
            if (ordering[i].degree > ordering[i - 1].degree && ordering[i].degree == ordering[i - 1].degree + 1) {
                current++;
            } else {
                if (current > longest) {
                    longest = current;
                }
                break;
            }
        }
        if (current > longest) {
            longest = current;
        }
        this.terminalCliqueSize = longest;       
    }
}