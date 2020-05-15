package math.discrete.path.shortest;

import math.discrete.core.Edge;
import math.discrete.core.Graph;
import math.discrete.core.Node;

import java.util.*;

public class Dikstra {
    public Map<Node, List<Edge>> calculate(Graph graph, Node start) {
        List<Integer> distances = new ArrayList<>();
        Comparator<Node> comparator = (first, second) -> distances.get(graph.nodes.indexOf(first)) - distances.get(graph.nodes.indexOf(second));

        PriorityQueue<Node> queue = new PriorityQueue<>(graph.nodes.size(), comparator);

        Map<Node, List<Edge>> paths = new HashMap<>();

        for (Node node : graph.nodes) {
            if (node.equals(start)) {
                distances.add(0);
            } else {
                distances.add(Integer.MAX_VALUE / 2);
            }
            paths.put(node, new ArrayList<>());

            queue.add(node);
        }

        while (!queue.isEmpty()) {
            PriorityQueue<Node> nextQueue = new PriorityQueue<>(queue.size(), comparator);
            Node current = queue.poll();

            while (!queue.isEmpty()) {
                Node changed = queue.poll();

                if (current.neighbours.get(changed) != null) {
                    for (Edge edge : current.neighbours.get(changed)) {
                        if (edge.active) {
                            int edgeLength = edge.length;

                            int changedIndex = graph.nodes.indexOf(changed);
                            int currentIndex = graph.nodes.indexOf(current);

                            int distanceSource = distances.get(currentIndex);
                            int distanceTarget = distances.get(changedIndex);

                            if (distanceTarget > distanceSource + edgeLength) {
                                distances.set(changedIndex, distanceSource + edgeLength);

                                List<Edge> currentPath = new ArrayList<>(paths.get(current));
                                currentPath.add(edge);
                                paths.put(changed, currentPath);
                            }
                        }
                    }
                }

                nextQueue.add(changed);
            }

            queue = nextQueue;
        }

        for (int i = 0; i < paths.size(); i++) {
            List<Edge> shortestPathEdges = paths.get(graph.nodes.get(i));

            if (distances.get(i) == Integer.MAX_VALUE / 2) {
                shortestPathEdges = new ArrayList<>();
            }
            graph.nodes.get(i).distance = shortestPathEdges.stream().map(edge -> edge.length).reduce((a, b) -> a + b).orElse(-1);
        }


        return paths;
    }
}
