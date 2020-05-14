package math.discrete.path;

import math.discrete.core.Edge;
import math.discrete.core.Graph;
import math.discrete.core.Node;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class BFS {
    public Map<Node, Node> nodeToParent = new HashMap<>();
    public Listener listener;
    public Filter filter;

    public interface  Filter{
        public boolean accept(Edge edge);
    }
    public interface Listener {
        public void action(Node parent, Node current);
    }

    public void run(Graph graph) {
        for (Node node : graph.nodes) {
            node.mark = 0;
        }
        ArrayDeque<Node> queue = new ArrayDeque<>();

        for (Node node : graph.nodes) {
            if (node.mark != 1) {
                node.mark = 1;
                queue.add(node);

                while (!queue.isEmpty()) {
                    Node current = queue.poll();

                    if (listener != null) {
                        listener.action(nodeToParent.get(current), current);
                    }

                    for (Map.Entry<Node, Edge> neighbour : current.neighbours.entrySet()) {
                        if (neighbour.getKey().mark != 1 && (filter == null || filter.accept(neighbour.getValue()))) {
                            neighbour.getKey().mark = 1;
                            queue.add(neighbour.getKey());
                            nodeToParent.put(neighbour.getKey(), current);
                        }
                    }
                }
            }
        }
    }
}
