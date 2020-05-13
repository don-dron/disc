package math.discrete;

import java.util.*;

public class FlowFilling {
    public int maximumCost = Integer.MAX_VALUE;
    public int currentCost;

    public List<Node> findPath(Graph graph) {
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
    }

    public boolean flowFilling(Graph residualNetwork) {
        residualNetwork.updateDeactivatedEdges(edge -> edge.residualCapacity == 0);

        List<Node> shortestPath = findPath(residualNetwork);

        if (!(shortestPath.contains(residualNetwork.nodes.get(residualNetwork.nodes.size() - 1))
                && shortestPath.contains(residualNetwork.nodes.get(0)))) {
            return false;
        }

        List<Edge> shortestPathEdges = FlowUtils.nodesToEdgesPath(shortestPath);

        int minCapacity = Collections.min(shortestPathEdges, (first, second) ->
                first.residualCapacity - second.residualCapacity).residualCapacity;

        minCapacity = calculateNewMinCapacity(shortestPathEdges, minCapacity);

        if (minCapacity == 0) {
            return false;
        }
        currentCost = currentCost + calculateCostDiff(shortestPathEdges, minCapacity);

        fillEdges(residualNetwork, shortestPathEdges, minCapacity);

        return true;
    }

    public static void fillEdges(Graph residualNetwork, List<Edge> shortestPathEdges, int minCapacity) {
        for (Edge modifyEdge : shortestPathEdges) {
            Edge edge = modifyEdge;
            Edge mirrorEdge = residualNetwork.getEdges().stream().filter(edges -> edges.source.equals(modifyEdge.target) && edges.target.equals(modifyEdge.source)).findFirst().get();

            if (edge.type == Edge.EdgeTypes.BACKWARD) {
                Edge temp = edge;
                edge = mirrorEdge;
                mirrorEdge = temp;
            }

            edge.flow = edge.flow + minCapacity;
            mirrorEdge.flow = -edge.flow;

            edge.residualCapacity = edge.capacity - edge.flow;
            mirrorEdge.residualCapacity = mirrorEdge.capacity - mirrorEdge.flow;
        }
    }

    public int calculateNewMinCapacity(List<Edge> shortestPathEdges, int minCapacity) {
        int diffCost;
        while (maximumCost < currentCost + (diffCost = calculateCostDiff(shortestPathEdges, minCapacity))) {
            minCapacity--;
            if (minCapacity == 0) {
                return 0;
            }
        }
        return minCapacity;
    }

    public int calculateCostDiff(List<Edge> shortestPathEdges, int flow) {
        return shortestPathEdges.stream().map(edge -> flow * edge.cost).reduce((a, b) -> a + b).get();
    }
}
