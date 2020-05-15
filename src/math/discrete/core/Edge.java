package math.discrete.core;

import java.util.ArrayList;
import java.util.List;

public class Edge {
    public Graph graph;
    public Node source;
    public Node target;
    public EdgeTypes type = EdgeTypes.FORWARD;

    public boolean active = true;
    public int flow;
    public int capacity;
    public int length = 1;
    public int index;
    public int residualCapacity;
    public int cost = 1;

    public Edge(Node source, Node target) {
        this.source = source;
        this.target = target;


        List<Edge> edges =
                source.neighbours.get(target);
        if (edges == null) {
            edges = new ArrayList<>();
            source.neighbours.put(target, edges);
        }
        edges.add(this);
    }


    public Edge(Edge edge) {
        this.source = edge.source;
        this.target = edge.target;
        this.flow = edge.flow;
        this.capacity = edge.capacity;
        this.length = edge.length;
        this.type = edge.type;
        this.residualCapacity = edge.residualCapacity;
        this.graph = edge.graph;
        this.index = edge.index;
        this.cost = edge.cost;

        List<Edge> edges =
                source.neighbours.get(target);
        if (edges == null) {
            edges = new ArrayList<>();
            source.neighbours.put(target, edges);
        }
        edges.add(this);
    }

    public String toString() {
        return "Edge\n" + graph.nodes.indexOf(source) + " -> " + graph.nodes.indexOf(target) + " " + "Flow: " + flow +
                " Capacity: " + capacity + " Length: " + length + " ResidualCapacity: " + residualCapacity + " Cost: " + cost;
    }

    public enum EdgeTypes {
        FORWARD, DUPLEX, BACKWARD
    }
}
