package math.discrete;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BelmanFord {
    public List<Node> calculate(Graph graph, Node start) {

        List<List<Node>> paths = new ArrayList<>();
        graph.nodes.forEach(node -> {
            if (node.equals(start)) {
                node.distance = 0;
            } else {
                node.distance = Integer.MAX_VALUE / 2;
            }
            paths.add(new ArrayList<>());
        });

        int i = 1;
        int size = graph.nodes.size();
        while (i < size) {
            for (Edge edge : graph.getEdges().stream().filter(edge -> edge.active).collect(Collectors.toList())) {
                int sum = edge.source.distance + edge.length;
                if (edge.target.distance > sum) {
                    edge.target.distance = sum;

                    int changedIndex = graph.nodes.indexOf(edge.target);
                    int currentIndex = graph.nodes.indexOf(edge.source);

                    List<Node> currentPath = new ArrayList<>(paths.get(currentIndex));
                    currentPath.add(edge.source);
                    paths.set(changedIndex, currentPath);
                }
            }
            i++;
        }

        List<Node> negativeNodes = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            int sum = edge.source.distance + edge.length;
            if (edge.target.distance > sum) {
                negativeNodes.add(edge.target);
            }
        }

        if (negativeNodes.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<Node> cycleNodes = new ArrayList<>();
            List<Node> path = paths.get(graph.nodes.indexOf(negativeNodes.get(0)));

            for (Node node : path) {
                if (path.stream().filter(current -> current.equals(node)).count() > 1 && !cycleNodes.contains(node)) {
                    cycleNodes.add(node);
                }
            }

            return cycleNodes;
        }
    }
}
