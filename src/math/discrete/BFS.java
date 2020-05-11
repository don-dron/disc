package math.discrete;

import java.util.*;

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

    public void run(Graph graph, Node start) {
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
