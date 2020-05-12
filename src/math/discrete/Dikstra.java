package math.discrete;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Dikstra {
    public List<List<Node>> calculate(Graph graph, Node start) {
        List<Integer> distances = new ArrayList<>();
        Comparator<Node> comparator = (first, second) -> distances.get(graph.nodes.indexOf(first)) - distances.get(graph.nodes.indexOf(second));

        PriorityQueue<Node> queue = new PriorityQueue<>(graph.nodes.size(), comparator);

        List<List<Node>> paths = new ArrayList<>();

        for (Node node : graph.nodes) {
            if (node.equals(start)) {
                distances.add(0);
            } else {
                distances.add(Integer.MAX_VALUE / 2);
            }
            queue.add(node);
            paths.add(new ArrayList<>());
        }

        while (!queue.isEmpty()) {
            PriorityQueue<Node> nextQueue = new PriorityQueue<>(queue.size(), comparator);
            Node current = queue.poll();

            while (!queue.isEmpty()) {
                Node changed = queue.poll();

                Edge edge = current.neighbours.get(changed);

                if (edge != null) {
                    int edgeLength = edge.length;

                    // Ignore negative edges
                    if (edgeLength > 0) {
                        int changedIndex = graph.nodes.indexOf(changed);
                        int currentIndex = graph.nodes.indexOf(current);

                        int distanceSource = distances.get(currentIndex);
                        int distanceTarget = distances.get(changedIndex);

                        if (distanceTarget > distanceSource + edgeLength) {
                            distances.set(changedIndex, distanceSource + edgeLength);
                            paths.get(changedIndex).add(current);
                            List<Node> currentPath = new ArrayList<>(paths.get(currentIndex));
                            currentPath.add(current);
                            paths.set(changedIndex, currentPath);
                        }
                    }
                }

                nextQueue.add(changed);
            }

            queue = nextQueue;
        }

        for (int i = 0; i < paths.size(); i++) {
            graph.nodes.get(i).distance = paths.get(i).size();
            paths.get(i).add(graph.nodes.get(i));
        }

        return paths;
    }

    public List<Node> getShortestPath(Graph graph, Node source, Node target) {
        return calculate(graph, source).get(graph.nodes.indexOf(target));
    }
}
