package math.discrete.maxflow;

import math.discrete.core.Edge;
import math.discrete.core.Graph;
import math.discrete.core.Node;
import math.discrete.path.shortest.BelmanFord;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class EmondsCarp extends FlowFilling {
    public void maxFlowCalculate(Graph graph) {
        Graph residualNetwork = graph.buildResidualNetwork();
        while (flowFilling(residualNetwork)) ;

        Iterator<Edge> iterator = graph.getEdgesIterator();
        for (Edge edge : residualNetwork.getEdges().stream().filter(edge -> edge.type == Edge.EdgeTypes.FORWARD).collect(Collectors.toList())) {
            iterator.next().flow = edge.flow;
        }
    }
}
