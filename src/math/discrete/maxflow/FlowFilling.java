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

    public List<Edge> findPath(Graph graph) {
        if (!minCost) {
            BFS bfs = new BFS();
            bfs.filter = edge -> edge.active;

            bfs.run(graph);
            Map<Node, Edge> parents = bfs.nodeToParent;

            List<Edge> path = new ArrayList<>();
            Edge edge = null;
            Node current = graph.nodes.get(graph.nodes.size() - 1);
            while ((edge = parents.get(current)) != null) {
                path.add(edge);
                current = edge.source;
            }

            return path;
        } else {
            BelmanFord belmanFord = new BelmanFord();
            belmanFord.calculate(graph, graph.nodes.get(0));

            return belmanFord.paths.get(graph.nodes.get(graph.nodes.size() - 1));
        }
    }

    public boolean flowFilling(Graph residualNetwork) {
        residualNetwork.updateDeactivatedEdges(edge -> edge.residualCapacity == 0);

        List<Edge> shortestPathEdges = findPath(residualNetwork);

        if (!(shortestPathEdges.stream().anyMatch(edge -> edge.source.equals(residualNetwork.nodes.get(0))) &&
                shortestPathEdges.stream().anyMatch(edge -> edge.target.equals(residualNetwork.nodes.get(residualNetwork.nodes.size() - 1))))) {
            return false;
        }

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

    public int calculateNewMinCapacity(List<Edge> shortestPathEdges, int minCapacity) {
        while (maximumCost < currentCost + calculateCostDiff(shortestPathEdges, minCapacity)) {
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
