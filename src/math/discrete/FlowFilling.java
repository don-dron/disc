package math.discrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlowFilling {
    public boolean flowFilling(Graph residualNetwork) {
        residualNetwork.getEdges().stream().forEach(edge -> {
            if (edge.residualCapacity == 0) {
                edge.length = -1;
            } else {
                edge.length = 1;
            }
        });

        List<Node> shortestPath = new Dikstra().getShortestPath(residualNetwork, residualNetwork.nodes.get(0),
                residualNetwork.nodes.get(residualNetwork.nodes.size() - 1));

        if (!(shortestPath.contains(residualNetwork.nodes.get(residualNetwork.nodes.size() - 1))
                && shortestPath.contains(residualNetwork.nodes.get(0)))) {
            return false;
        }

        List<Edge> shortestPathEdges = new ArrayList<>();

        for (int i = 0; i < shortestPath.size() - 1; i++) {
            Node node = shortestPath.get(i);
            shortestPathEdges.add(node.neighbours.get(shortestPath.get(i + 1)));
        }

        int minCapacity = Collections.min(shortestPathEdges, (first, second) ->
                first.residualCapacity - second.residualCapacity).residualCapacity;

        for (Edge modifyEdge : shortestPathEdges) {
            Edge edge = modifyEdge;
            Edge mirrorEdge = residualNetwork.getEdges().stream().filter(edges -> edges.source.equals(modifyEdge.target) && edges.target.equals(modifyEdge.source)).findFirst().get();

            if (edge.type == Edge.EdgeTypes.BACKWARD) {
                Edge temp = edge;
                edge = mirrorEdge;
                mirrorEdge = temp;
            }

            edge.flow = edge.flow + minCapacity;
            mirrorEdge.flow = -edge.flow;

            edge.residualCapacity = edge.capacity - edge.flow;
            mirrorEdge.residualCapacity = mirrorEdge.capacity - mirrorEdge.flow;
        }
        return true;
    }
}
