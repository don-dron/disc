package math.discrete;

import java.util.*;
import java.util.stream.Collectors;

public class EmondsCarp {
    public void minCostMaxFlowCalculate(Graph graph) {
        graph.zeroingFlows();

        Map<Edge, Edge> mirrorEdges = new HashMap<>();

        Graph residualNetwork = graph.buildResidualNetwork();

        for (int i = 0; i < residualNetwork.edges.size(); i += 2) {
            residualNetwork.edges.get(i).length = 1;
            residualNetwork.edges.get(i + 1).length = 1;
            mirrorEdges.put(residualNetwork.edges.get(i), residualNetwork.edges.get(i + 1));
            mirrorEdges.put(residualNetwork.edges.get(i + 1), residualNetwork.edges.get(i));
        }

        for (; ; ) {

            List<Node> shortestPath = new Dikstra().getShortestPath(residualNetwork, residualNetwork.nodes.get(0),
                    residualNetwork.nodes.get(residualNetwork.nodes.size() - 1));

            if (shortestPath.size() <= 1) {
                break;
            }

            System.out.println(shortestPath.stream().map(node -> Integer.toString(residualNetwork.nodes.indexOf(node))).collect(Collectors.joining(", ")));

            List<Edge> shortestPathEdges = new ArrayList<>();

            for (int i = 0; i < shortestPath.size() - 1; i++) {
                Node node = shortestPath.get(i);
                shortestPathEdges.add(node.neighbours.get(shortestPath.get(i + 1)));
            }

            int minCapacity = Collections.min(shortestPathEdges, new Comparator<Edge>() {
                @Override
                public int compare(Edge first, Edge second) {
                    return first.capacity - second.capacity;
                }
            }).capacity;

            for (Edge modifyEdge : shortestPathEdges) {
                Edge mirrorEdge = mirrorEdges.get(modifyEdge);
                modifyEdge.flow = modifyEdge.flow + minCapacity;
                mirrorEdge.flow = mirrorEdge.flow - minCapacity;

                modifyEdge.capacity = modifyEdge.capacity-modifyEdge.flow;
                mirrorEdge.capacity = modifyEdge.flow;

                if (modifyEdge.capacity - modifyEdge.flow == 0 && residualNetwork.edges.contains(modifyEdge)) {
                    residualNetwork.edges.remove(modifyEdge);
                    modifyEdge.source.neighbours.remove(modifyEdge.target);
                } else if (modifyEdge.capacity - modifyEdge.flow != 0 && !residualNetwork.edges.contains(modifyEdge)) {
                    residualNetwork.edges.add(modifyEdge);
                    modifyEdge.source.neighbours.put(modifyEdge.target, modifyEdge);
                }

                if (mirrorEdge.capacity - mirrorEdge.flow == 0 && residualNetwork.edges.contains(mirrorEdge)) {
                    residualNetwork.edges.remove(mirrorEdge);
                    mirrorEdge.source.neighbours.remove(mirrorEdge.target);
                } else if (mirrorEdge.capacity - mirrorEdge.flow != 0 && !residualNetwork.edges.contains(mirrorEdge)) {
                    residualNetwork.edges.add(mirrorEdge);
                    mirrorEdge.source.neighbours.put(mirrorEdge.target, mirrorEdge);
                }
            }
        }

        graph.edges.stream().forEach(edge -> System.out.println(edge.flow));
    }
}
