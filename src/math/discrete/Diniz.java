package math.discrete;

import java.util.concurrent.Flow;
import java.util.stream.Collectors;

public class Diniz extends FlowFilling implements IMaxFlow {
    public boolean findBlockingFlow(Graph layoutNetwork, boolean withMinCost) {
        boolean flag = true;
        while (flowFilling(layoutNetwork)) {
            flag = false;
        }
        return flag;
    }

    public void maxFlowCalculate(Graph graph) {
        maxFlowCalculate(graph, false);
    }

    public void maxFlowCalculate(Graph graph, boolean withMinCost) {
        Graph residualNetwork;
        Graph layoutNetwork;
        while (true) {
            residualNetwork = graph.buildResidualNetwork();
            layoutNetwork = residualNetwork.buildLayoutNetwork();
            boolean flag = findBlockingFlow(layoutNetwork, withMinCost);
            if (flag) {
                break;
            }

            for (Edge edge : layoutNetwork.getEdges().stream().filter(edge -> edge.type == Edge.EdgeTypes.FORWARD).collect(Collectors.toList())) {
                graph.getEdges().get(edge.index).flow = edge.flow;
            }
        }
    }
}
