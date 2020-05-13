package math.discrete;

public class MinCostMaxFlowResidualNetworkBuilder {
    public Graph build(Graph graph) {
        Graph network = new Graph();

        for (Node node : graph.nodes) {
            Node newNode = new Node(node);
            newNode.neighbours.clear();
            network.addNode(newNode);
        }

        for (Edge edge : graph.getEdges()) {
            Node source = network.nodes.get(graph.nodes.indexOf(edge.source));
            Node target = network.nodes.get(graph.nodes.indexOf(edge.target));

            Edge newEdge = new Edge(source, target);
            newEdge.flow = edge.flow;
            newEdge.capacity = edge.capacity;
            newEdge.cost = edge.cost;
            newEdge.residualCapacity = newEdge.cost * (newEdge.capacity - newEdge.flow);
            network.addEdge(newEdge);
            newEdge.index = edge.index;

            newEdge = new Edge(target, source);
            newEdge.flow = -edge.flow;
            newEdge.capacity = 0;
            newEdge.cost = -edge.cost;
            newEdge.residualCapacity = newEdge.cost * (newEdge.capacity - newEdge.flow);
            newEdge.type = Edge.EdgeTypes.BACKWARD;
            network.addEdge(newEdge);
            newEdge.index = edge.index;
        }

        network.getEdges().stream().forEach(edge -> {
            edge.length = edge.cost;
            if (edge.residualCapacity == 0) {
                edge.active = false;
            } else {
                edge.active = true;
            }
        });
        return network;
    }
}
