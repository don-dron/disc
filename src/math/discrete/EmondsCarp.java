package math.discrete;

import java.util.Iterator;
import java.util.stream.Collectors;

public class EmondsCarp {
    public void maxFlowCalculate(Graph graph) {
        graph.zeroingFlows();

        Graph residualNetwork = graph.buildResidualNetwork();
        while (new FlowFilling().flowFilling(residualNetwork)) ;

        Iterator<Edge> iterator = graph.getEdgesIterator();
        for (Edge edge : residualNetwork.getEdges().stream().filter(edge -> edge.type == Edge.EdgeTypes.FORWARD).collect(Collectors.toList())) {
            iterator.next().flow = edge.flow;
        }
    }
}
