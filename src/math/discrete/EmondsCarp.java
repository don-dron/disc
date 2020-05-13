package math.discrete;

import java.util.Iterator;
import java.util.stream.Collectors;

public class EmondsCarp extends FlowFilling implements IMaxFlow {
//    public void setMinimumCost(int minimumCost) {
//        this.minimumCost = minimumCost;
//    }
    public void maxFlowCalculate(Graph graph) {
        Graph residualNetwork = graph.buildResidualNetwork();
        while (flowFilling(residualNetwork)) ;

        Iterator<Edge> iterator = graph.getEdgesIterator();
        for (Edge edge : residualNetwork.getEdges().stream().filter(edge -> edge.type == Edge.EdgeTypes.FORWARD).collect(Collectors.toList())) {
            iterator.next().flow = edge.flow;
        }
    }
}
