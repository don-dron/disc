package math.discrete.path;

import math.discrete.core.Edge;
import math.discrete.core.Graph;
import math.discrete.core.Node;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BFS {
    public Map<Node, Edge> nodeToParent = new HashMap<>();
    public Filter filter;

    public interface Filter {
        public boolean accept(Edge edge);
    }

    public void run(Graph graph) {
        for (Node node : graph.nodes) {
            node.mark = 0;
        }
        ArrayDeque<Node> queue = new ArrayDeque<>();
        nodeToParent.put(graph.nodes.get(0), null);
        for (Node node : graph.nodes) {
            if (node.mark != 1) {
                node.mark = 1;
                queue.add(node);
                while (!queue.isEmpty()) {
                    Node current = queue.poll();
                    for (Map.Entry<Node, List<Edge>> neighbour : current.neighbours.entrySet()) {
                        for (Edge edge : neighbour.getValue()) {
                            if (neighbour.getKey().mark != 1 && (filter == null || filter.accept(edge))) {
                                neighbour.getKey().mark = 1;
                                queue.add(neighbour.getKey());
                                nodeToParent.put(neighbour.getKey(), edge);
                            }
                        }
                    }
                }
            }
        }
    }
}
