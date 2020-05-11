package math.discrete;

import java.util.stream.Collectors;

public class Diniz {
    public void minCostMaxFlowCalculate(Graph graph) {
        graph.zeroingFlows();

        Graph residualNetwork = graph.buildResidualNetwork();
        Graph layoutNetwork = residualNetwork.buildLayoutNetwork();

        System.out.println(layoutNetwork.nodes.stream().map(node->Integer.toString(node.distance)).collect(Collectors.joining(", ")));
    }
}
