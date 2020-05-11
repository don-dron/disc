package math.discrete;

import java.util.*;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

public class Diniz {
    public void minCostMaxFlowCalculate(Graph graph) {
        graph.zeroingFlows();
        Graph residualNetwork;
        Graph layoutNetwork;
        for (; ; ) {
            residualNetwork = graph.buildResidualNetwork();
            layoutNetwork = residualNetwork.buildLayoutNetwork();
            boolean flag = findBlockingFlow(layoutNetwork);
            if (flag) {
                break;
            }

            for (Edge edge : layoutNetwork.getEdges().stream().filter(edge -> edge.type == Edge.EdgeTypes.FORWARD).collect(Collectors.toList())) {
                graph.getEdges().get(edge.index).flow = edge.flow;
            }

        }
        System.out.println(graph.getFlows());
    }

    public boolean findBlockingFlow(Graph layoutNetwork) {
        boolean flag = true;
        for (; FlowFilling.flowFilling(layoutNetwork); ) {
            layoutNetwork.getEdges().stream().forEach(edge -> {
                if (edge.residualCapacity == 0) {
                    edge.length = -1;
                } else {
                    edge.length = 1;
                }
            });

            List<Node> shortestPath = new Dikstra().getShortestPath(layoutNetwork, layoutNetwork.nodes.get(0),
                    layoutNetwork.nodes.get(layoutNetwork.nodes.size() - 1));

            if (!(shortestPath.contains(layoutNetwork.nodes.get(layoutNetwork.nodes.size() - 1)) && shortestPath.contains(layoutNetwork.nodes.get(0)))) {
                return flag;
            }

            List<Edge> shortestPathEdges = new ArrayList<>();

            for (int i = 0; i < shortestPath.size() - 1; i++) {
                Node node = shortestPath.get(i);
                shortestPathEdges.add(node.neighbours.get(shortestPath.get(i + 1)));
            }

            int minCapacity = Collections.min(shortestPathEdges, (first, second) ->
                    first.residualCapacity - second.residualCapacity).residualCapacity;

            for (Edge edge : shortestPathEdges) {
                Edge mirrorEdge = layoutNetwork.getEdges().stream().filter(edges -> edges.source.equals(edge.target)
                        && edges.target.equals(edge.source)).findFirst().orElse(null);

                edge.flow = edge.flow + minCapacity;

                if (edge.type == Edge.EdgeTypes.FORWARD) {
                    edge.residualCapacity = edge.capacity - edge.flow;
                } else {
                    edge.residualCapacity = mirrorEdge.flow;
                }

                if (mirrorEdge != null) {
                    mirrorEdge.flow = mirrorEdge.flow - minCapacity;
                    if (edge.type == Edge.EdgeTypes.FORWARD) {
                        mirrorEdge.residualCapacity = edge.flow;
                    } else {
                        mirrorEdge.residualCapacity = mirrorEdge.capacity - mirrorEdge.flow;
                    }
                }
            }
            flag = false;
        }
    }
}
