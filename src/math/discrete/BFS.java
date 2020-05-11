package math.discrete;

import java.util.Map;
import java.util.PriorityQueue;

public class BFS {

    public Listener listener;

    public void run(Graph graph, Node start) {
        for (Node node : graph.nodes) {
            node.mark = 0;
        }

        PriorityQueue<Node> queue = new PriorityQueue<>();

        for (Node node : graph.nodes) {
            if (node.mark != 1) {
                node.mark = 1;
                queue.add(node);

                while (!queue.isEmpty()) {
                    Node current = queue.poll();

                    if(listener!=null){
                        listener.action(current);
                    }

                    for (Map.Entry<Node, Edge> neighbour : current.neighbours.entrySet()) {
                        if (neighbour.getKey().mark != 1) {
                            neighbour.getKey().mark = 1;
                            queue.add(neighbour.getKey());
                        }
                    }
                }
            }
        }
    }
}
