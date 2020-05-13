package math.discrete;

import java.util.concurrent.Flow;
import java.util.stream.Collectors;

public class Diniz extends FlowFilling implements IMaxFlow {
    public boolean findBlockingFlow(Graph layoutNetwork) {
        boolean flag = true;
        while (flowFilling(layoutNetwork)) {
            flag = false;
        }
        return flag;
    }
//
//    public void setMinimumCost(int minimumCost) {
//        this.minimumCost = minimumCost;
//    }

    public void maxFlowCalculate(Graph graph) {
        Graph residualNetwork;
        Graph layoutNetwork;
        while (true) {
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
    }
}
