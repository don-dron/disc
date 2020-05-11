package math.discrete;

import java.util.Iterator;

public class Edge {
    public Graph graph;
    public Node source;
    public Node target;
    public EdgeTypes type = EdgeTypes.FORWARD;

    public int flow;
    public int capacity;
    public int length=1;
    public int index;
    public int residualCapacity;

    public Edge(Node source, Node target) {
        this.source = source;
        this.target = target;

        source.neighbours.put(target, this);
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

        source.neighbours.put(target, this);
    }

    public String toString() {
        return "Edge\n" + graph.nodes.indexOf(source) + " -> " + graph.nodes.indexOf(target) + " " + "Flow: " + flow +
                " Capacity: " + capacity + " Length: " + length + " ResidualCapacity: " + residualCapacity;
    }

    public enum EdgeTypes {
        FORWARD, DUPLEX, BACKWARD
    }
}
