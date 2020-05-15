package math.discrete.maxflow;

import math.discrete.core.Edge;
import math.discrete.core.Graph;

import java.util.stream.Collectors;

public class Diniz extends FlowFilling {
    public boolean findBlockingFlow(Graph layoutNetwork) {
        boolean flag = true;
        while (flowFilling(layoutNetwork)) {
            flag = false;
        }
        return flag;
    }

    public Graph maxFlowCalculate(Graph graph) {
        Graph copyGraph = new Graph(graph);

        Graph residualNetwork;
        Graph layoutNetwork;
        while (true) {
            residualNetwork = copyGraph.buildResidualNetwork();
            layoutNetwork = residualNetwork.buildLayoutNetwork();
            boolean flag = findBlockingFlow(layoutNetwork);
            if (flag) {
                break;
            }

            for (Edge edge : layoutNetwork.getEdges().stream().filter(edge -> edge.type == Edge.EdgeTypes.FORWARD).collect(Collectors.toList())) {
                copyGraph.getEdges().get(edge.index).flow = edge.flow;
            }
        }
        residualNetwork = copyGraph.buildResidualNetwork();

        return residualNetwork;
    }
}
