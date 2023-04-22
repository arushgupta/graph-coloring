

// public static void RunWithTimings(final int x) {
//     Graph graph = new Graph();

//     System.out.println("Establish:         " + TimeClass(graph, Graph::Establish, x) + "ms");
//     System.out.println("Distribution:      " + TimeClass(graph, Graph::CreateModifiedNormalDistribution, x * 200) + "ms");
//     System.out.println("Order:             " + TimeClass(graph, Graph::SmallestLastVertexOrder, null) + "ms");
//     System.out.println("Color Graph:       " + TimeClass(graph, Graph::ColorGraph, null) + "ms");
//     System.out.println("Colors used:       " + graph.maxColor);
// }

// public static void main(String[] args) {
//     final int size = 1000;

//     Graph graph = new Graph();
//     graph.Establish(size);
//     graph.CreateModifiedNormalDistribution(size * 200);
//     graph.SmallestLastVertexOrder();
//     graph.ColorGraph();
//     graph.Print();

//     System.out.println("Colors used: " + graph.maxColor);
// }
