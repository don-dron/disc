package math.discrete;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {
    public static boolean DEBUG = false;
    public List<Node> nodes = new ArrayList<>();
    public List<Edge> edges = new ArrayList<>();

    public Graph() {

    }

    public void addNode(Node node) {
        node.graph = this;
        nodes.add(node);
    }

    public void addEdge(Edge edge) {
        edge.graph = this;
        edges.add(edge);
    }

    public void zeroingFlows() {
        edges.stream().forEach(edge -> edge.flow = 0);
    }

    public Graph buildResidualNetwork() {
        Graph network = new Graph();

        for (Node node : nodes) {
            Node newNode = new Node(node);
            newNode.neighbours.clear();
            network.addNode(newNode);
        }

        for (Edge edge : edges.stream().filter(edge -> edge.capacity > edge.flow).collect(Collectors.toList())) {
            Node source = network.nodes.get(nodes.indexOf(edge.source));
            Node target = network.nodes.get(nodes.indexOf(edge.target));

            Edge newEdge = new Edge(source, target);
            newEdge.capacity = edge.capacity - edge.flow;
            network.addEdge(newEdge);

            newEdge = new Edge(target, source);
            newEdge.capacity = edge.flow;
            newEdge.type = Edge.EdgeTypes.BACKWARD;
            network.addEdge(newEdge);
        }

        if (DEBUG) {
            network.edges.forEach(System.out::println);
        }

        return network;
    }
}
