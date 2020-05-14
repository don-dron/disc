package math.discrete.path.shortest;

import math.discrete.core.Edge;
import math.discrete.core.Graph;
import math.discrete.core.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BelmanFord {
    public Map<Node, List<Node>> paths = new HashMap<>();
    public List<Node> cycleNodes = new ArrayList<>();

    public boolean calculate(Graph graph, Node start) {
        paths.clear();
        cycleNodes.clear();

        graph.nodes.forEach(node -> {
            if (node.equals(start)) {
                node.distance = 0;
            } else {
                node.distance = Integer.MAX_VALUE / 2;
            }
            paths.put(node, new ArrayList<>());
        });

        int i = 1;
        int size = graph.nodes.size();
        while (i < size) {
            for (Edge edge : graph.getEdges().stream().filter(edge -> edge.active).collect(Collectors.toList())) {
                int sum = edge.source.distance + edge.length;
                if (edge.target.distance > sum) {
                    edge.target.distance = sum;

                    List<Node> currentPath = new ArrayList<>(paths.get(edge.source));
                    currentPath.add(edge.source);
                    paths.put(edge.target, currentPath);
                }
            }
            i++;
        }
        graph.nodes.forEach(node -> {
            paths.get(node).add(node);
        });

        List<Node> negativeNodes = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            int sum = edge.source.distance + edge.length;
            if (edge.target.distance > sum) {
                negativeNodes.add(edge.target);
            }
        }

        if (negativeNodes.isEmpty()) {
            return false;
        } else {
            List<Node> path = paths.get(negativeNodes.get(0));
            for (Node node : path) {
                if (path.stream().filter(current -> current.equals(node)).count() > 1 && !cycleNodes.contains(node)) {
                    cycleNodes.add(node);
                }
            }

            return cycleNodes.size() <= 1;
        }
    }
}
