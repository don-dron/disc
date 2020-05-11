package math.discrete;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    public static boolean DEBUG = false;
    public List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    public Graph() {

    }

    public Iterator<Edge> getEdgesIterator() {
        return edges.iterator();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public String getFlows() {
        return edges.stream().map(edge -> nodes.indexOf(edge.source) +
                " -> " + nodes.indexOf(edge.target) + " " + edge.flow +
                "/" + edge.capacity).collect(Collectors.joining("\n")) + "\n";
    }

    public void addNode(Node node) {
        node.graph = this;
        nodes.add(node);
    }

    public void addEdge(Edge edge) {
        edge.graph = this;
        edge.index = edges.size();
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

        for (Edge edge : edges) {
            Node source = network.nodes.get(nodes.indexOf(edge.source));
            Node target = network.nodes.get(nodes.indexOf(edge.target));

            Edge newEdge = new Edge(source, target);
            newEdge.flow = edge.flow;
            newEdge.capacity = edge.capacity;
            newEdge.residualCapacity = edge.capacity - edge.flow;
            network.addEdge(newEdge);
            newEdge.index = edge.index;

            newEdge = new Edge(target, source);
            newEdge.flow = edge.flow;
            newEdge.capacity = edge.capacity;
            newEdge.residualCapacity = edge.flow;
            newEdge.type = Edge.EdgeTypes.BACKWARD;
            network.addEdge(newEdge);
            newEdge.index = edge.index;
        }

        if (DEBUG) {
            network.edges.forEach(System.out::println);
        }

        network.getEdges().stream().forEach(edge -> {
            if (edge.residualCapacity == 0) {
                edge.length = -1;
            } else {
                edge.length = 1;
            }
        });
        return network;
    }

    public Graph buildLayoutNetwork() {
        Graph network = new Graph();

        Map<Node, List<Node>> paths = new HashMap<>();
        for (Node node : nodes) {
            paths.put(node, new ArrayList<>());
        }
        BFS bfs = new BFS();
        bfs.filter = edge -> edge.length > 0;

        bfs.listener = (parent, current) -> {
            List<Node> lastPath = parent == null ? new ArrayList<>() : new ArrayList<>(paths.get(parent));
            paths.remove(current);
            if (parent != null) {
                lastPath.add(parent);
            }
            paths.put(current, lastPath);
        };
        bfs.run(this, nodes.get(0));

        for (Node node : nodes) {
            node.distance = paths.get(node).size() + 1;
        }

        for (Node node : nodes) {
            Node newNode = new Node(node);
            newNode.neighbours.clear();
            network.addNode(newNode);
        }

        for (Edge edge : edges) {
            Node source = network.nodes.get(nodes.indexOf(edge.source));
            Node target = network.nodes.get(nodes.indexOf(edge.target));

            if (edge.source.distance != -1 && edge.target.distance != -1 && edge.type == Edge.EdgeTypes.FORWARD) {
                Edge newEdge = new Edge(source, target);
                addEdgeToLayoutNetwork(network, newEdge, edge);

                Edge newMirror = new Edge(target, source);
                addEdgeToLayoutNetwork(network, newMirror, edges.stream().filter(edg -> edg.source.equals(edge.target) && edg.target.equals(edge.source)).findFirst().get());
            }
        }
        network.getEdges().stream().forEach(edge -> {
            if (edge.residualCapacity == 0 || edge.source.distance < edge.target.distance) {
                edge.length = -1;
            } else {
                edge.length = 1;
            }
        });

        return network;
    }

    public void addEdgeToLayoutNetwork(Graph network, Edge newEdge, Edge edge) {
        network.addEdge(newEdge);
        newEdge.length = edge.length;
        newEdge.flow = edge.flow;
        newEdge.capacity = edge.capacity;
        newEdge.residualCapacity = edge.residualCapacity;
        newEdge.type = edge.type;
        newEdge.index = edge.index;
    }
}