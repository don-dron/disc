package math.discrete;

import java.util.*;
import java.util.stream.Collectors;

public class EmondsCarp {
    public void minCostMaxFlowCalculate(Graph graph) {
        graph.zeroingFlows();

        Graph residualNetwork = graph.buildResidualNetwork();

        for (; ; ) {
            for (int i = 0; i < residualNetwork.edges.size(); i += 1) {
                if (residualNetwork.edges.get(i).residualCapacity == 0) {
                    residualNetwork.edges.get(i).length = -1;
                } else {
                    residualNetwork.edges.get(i).length = 1;
                }
            }

            List<Node> shortestPath = new Dikstra().getShortestPath(residualNetwork, residualNetwork.nodes.get(0),
                    residualNetwork.nodes.get(residualNetwork.nodes.size() - 1));

            if (shortestPath.size() <= 1) {
                break;
            }

            List<Edge> shortestPathEdges = new ArrayList<>();

            for (int i = 0; i < shortestPath.size() - 1; i++) {
                Node node = shortestPath.get(i);
                shortestPathEdges.add(node.neighbours.get(shortestPath.get(i + 1)));
            }

            int minCapacity = Collections.min(shortestPathEdges, (first, second) ->
                    first.residualCapacity - second.residualCapacity).residualCapacity;

            for (Edge edge : shortestPathEdges) {
                Edge mirrorEdge = residualNetwork.edges.stream().filter(edges -> edges.source.equals(edge.target) && edges.target.equals(edge.source)).findFirst().get();

                edge.flow = edge.flow + minCapacity;
                mirrorEdge.flow = mirrorEdge.flow - minCapacity;

                if (edge.type == Edge.EdgeTypes.FORWARD) {
                    edge.residualCapacity = edge.capacity - edge.flow;
                    mirrorEdge.residualCapacity = edge.flow;
                } else {
                    mirrorEdge.residualCapacity = mirrorEdge.capacity - mirrorEdge.flow;
                    edge.residualCapacity = mirrorEdge.flow;
                }
            }
        }

        Iterator<Edge> iterator = graph.edges.iterator();
        for (Edge edge : residualNetwork.edges.stream().filter(edge -> edge.type == Edge.EdgeTypes.FORWARD).collect(Collectors.toList())) {
            iterator.next().flow = edge.flow;
        }
    }
}
