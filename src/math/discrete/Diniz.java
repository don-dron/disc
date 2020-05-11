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
        for (; !FlowFilling.flowFilling(layoutNetwork); ) {
            flag = false;
        }
        return flag;
    }
}
