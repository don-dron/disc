package math.discrete.maxflow;

import math.discrete.core.Edge;
import math.discrete.core.Graph;
import math.discrete.core.Node;
import math.discrete.path.shortest.BelmanFord;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class EmondsCarp extends FlowFilling {
    public Graph maxFlowCalculate(Graph graph) {
        Graph residualNetwork = graph.buildResidualNetwork();
        while (flowFilling(residualNetwork)) ;
        return residualNetwork;
    }
}
