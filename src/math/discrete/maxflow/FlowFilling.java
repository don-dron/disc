package math.discrete.maxflow;

import math.discrete.core.Edge;
import math.discrete.core.Graph;
import math.discrete.core.Node;
import math.discrete.path.BFS;
import math.discrete.path.shortest.BelmanFord;

import java.util.*;

public abstract class FlowFilling implements IMaxFlow {
    public int maximumCost = Integer.MAX_VALUE;
    public int currentCost;
    public boolean minCost;

    public abstract void maxFlowCalculate(Graph graph);

    public List<Node> findPath(Graph graph) {
        if (!minCost) {
            Map<Node, List<Node>> paths = new HashMap<>();
            for (Node node : graph.nodes) {
                paths.put(node, new ArrayList<>());
            }
            BFS bfs = new BFS();
            bfs.filter = edge -> edge.active;

            bfs.listener = (parent, current) -> {
                List<Node> currentPath = parent == null ? new ArrayList<>() : new ArrayList<>(paths.get(parent));
                if (parent != null) {
                    currentPath.add(parent);
                }
                paths.put(current, currentPath);
            };
            bfs.run(graph);

            for (Node node : graph.nodes) {
                paths.get(node).add(node);
                node.distance = paths.get(node).size();
            }

            return paths.get(graph.nodes.get(graph.nodes.size() - 1));
        } else {
            BelmanFord belmanFord = new BelmanFord();
            belmanFord.calculate(graph, graph.nodes.get(0));

            return belmanFord.paths.get(graph.nodes.get(graph.nodes.size() - 1));
        }
    }

    public List<Edge> nodesToEdgesPath(List<Node> shortestPath) {
        List<Edge> shortestPathEdges = new ArrayList<>();

        for (int i = 0; i < shortestPath.size() - 1; i++) {
            Node node = shortestPath.get(i);
            shortestPathEdges.add(node.neighbours.get(shortestPath.get(i + 1)));
        }
        return shortestPathEdges;
    }

    public boolean flowFilling(Graph residualNetwork) {
        residualNetwork.updateDeactivatedEdges(edge -> edge.residualCapacity == 0);

        List<Node> shortestPath = findPath(residualNetwork);

        if (!(shortestPath.contains(residualNetwork.nodes.get(residualNetwork.nodes.size() - 1))
                && shortestPath.contains(residualNetwork.nodes.get(0)))) {
            return false;
        }

        List<Edge> shortestPathEdges = nodesToEdgesPath(shortestPath);

        int minCapacity = Collections.min(shortestPathEdges, (first, second) ->
                first.residualCapacity - second.residualCapacity).residualCapacity;

        fillEdges(residualNetwork, shortestPathEdges, minCapacity);

        return true;
    }

    public void fillEdges(Graph residualNetwork, List<Edge> shortestPathEdges, int minCapacity) {
        for (Edge modifyEdge : shortestPathEdges) {
            Edge edge = modifyEdge;
            Edge mirrorEdge = residualNetwork.getEdges().stream().filter(edges -> edges.source.equals(modifyEdge.target) && edges.target.equals(modifyEdge.source)).findFirst().get();

            edge.flow = edge.flow + minCapacity;
            mirrorEdge.flow = -edge.flow;

            edge.residualCapacity = (edge.capacity - edge.flow);
            mirrorEdge.residualCapacity = (mirrorEdge.capacity - mirrorEdge.flow);
        }
    }
}
