package math.discrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlowFilling {
//    public int minimumCost = Integer.MAX_VALUE;
//    public int currentCost;

    public boolean flowFilling(Graph residualNetwork) {
        residualNetwork.getEdges().stream().forEach(edge -> {
            if (edge.residualCapacity == 0) {
                edge.active = false;
            } else {
                edge.active = true;
            }
        });

        List<Node> shortestPath = new Dikstra().getShortestPath(residualNetwork, residualNetwork.nodes.get(0),
                residualNetwork.nodes.get(residualNetwork.nodes.size() - 1));

        if (!(shortestPath.contains(residualNetwork.nodes.get(residualNetwork.nodes.size() - 1))
                && shortestPath.contains(residualNetwork.nodes.get(0)))) {
            return false;
        }

        List<Edge> shortestPathEdges = new ArrayList<>();

        for (int i = 0; i < shortestPath.size() - 1; i++) {
            Node node = shortestPath.get(i);
            shortestPathEdges.add(node.neighbours.get(shortestPath.get(i + 1)));
        }

        int minCapacity = Collections.min(shortestPathEdges, (first, second) ->
                first.residualCapacity - second.residualCapacity).residualCapacity;
//
//
//        int diffCost;
//        while (minimumCost < currentCost + (diffCost = calculateCostDiff(shortestPathEdges, minCapacity))) {
//            minCapacity--;
//
//            if (minCapacity == 0) {
//                return false;
//            }
//        }
//
//        currentCost = currentCost + diffCost;

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
        return true;
    }

    public int calculateCostDiff(List<Edge> shortestPathEdges, int flow) {
        return shortestPathEdges.stream().map(edge -> flow * edge.cost).reduce((a, b) -> a + b).get();
    }
}
