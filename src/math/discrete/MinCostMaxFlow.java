package math.discrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinCostMaxFlow {
    public IMaxFlow maxFlow = new Diniz();
    public MinCostMaxFlowResidualNetworkBuilder residualNetworkBuilder = new MinCostMaxFlowResidualNetworkBuilder();

    public void minCostMaxFlowCalculate(Graph graph) {
        graph.zeroingFlows();

        maxFlow.setMinimumCost(4);
        maxFlow.maxFlowCalculate(graph);
        Graph network = residualNetworkBuilder.build(graph);

        List<Node> negativeCycle;

        while ((negativeCycle = new BelmanFord().calculate(network, network.nodes.get(0))).size() > 1) {
            List<Edge> shortestPathEdges = new ArrayList<>();

            for (int j = 0; j < negativeCycle.size() - 1; j++) {
                Node node = negativeCycle.get(j);
                shortestPathEdges.add(node.neighbours.get(negativeCycle.get(j + 1)));
            }

            int minCapacity = Collections.min(shortestPathEdges, (first, second) ->
                    first.residualCapacity - second.residualCapacity).residualCapacity;

            for (Edge modifyEdge : shortestPathEdges) {
                Edge edge = modifyEdge;
                Edge mirrorEdge = network.getEdges().stream().filter(edges -> edges.source.equals(modifyEdge.target) && edges.target.equals(modifyEdge.source)).findFirst().get();

                if (edge.type == Edge.EdgeTypes.BACKWARD) {
                    Edge temp = edge;
                    edge = mirrorEdge;
                    mirrorEdge = temp;
                }

                edge.flow = edge.flow + minCapacity;
                mirrorEdge.flow = -edge.flow;

                edge.residualCapacity = edge.cost * (edge.capacity - edge.flow);
                mirrorEdge.residualCapacity = edge.cost * (mirrorEdge.capacity - mirrorEdge.flow);
            }

            network.getEdges().stream().forEach(edge -> {
                if (edge.residualCapacity == 0) {
                    edge.active = false;
                } else {
                    edge.active = true;
                }
            });
        }
    }
}
