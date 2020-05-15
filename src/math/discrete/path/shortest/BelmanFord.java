package math.discrete.path.shortest;

import math.discrete.core.Edge;
import math.discrete.core.Graph;
import math.discrete.core.Node;

import java.util.*;
import java.util.stream.Collectors;

public class BelmanFord {
    public Map<Node, Edge> parents = new HashMap<>();
    public Map<Node, List<Edge>> paths = new HashMap<>();
    public List<Edge> cycleEdges = new ArrayList<>();

    public boolean calculate(Graph graph, Node start) {
        paths.clear();
        cycleEdges.clear();
        parents.clear();

        graph.nodes.forEach(node -> {
            if (node.equals(start)) {
                node.distance = 0;
            } else {
                node.distance = Integer.MAX_VALUE / 2;
            }
            paths.put(node, new ArrayList<>());
        });

        Node cycleNode = null;
        int i = 0;
        int size = graph.nodes.size();
        while (i < size) {
            cycleNode = null;
            for (Edge edge : graph.getEdges().stream().filter(edge -> edge.active).collect(Collectors.toList())) {
                int sum = edge.source.distance + edge.length;
                if (edge.active && edge.target.distance > sum) {
                    edge.target.distance = sum;

                    cycleNode = edge.target;
                    List<Edge> currentPath = new ArrayList<>(paths.get(edge.source));
                    currentPath.add(edge);
                    paths.put(edge.target, currentPath);

                    parents.put(edge.target, edge);
                }
            }
            i++;
        }

        if (cycleNode == null) {
            return false;
        } else {
            Node startCycle = cycleNode;

            for (int j = 0; j < graph.nodes.size(); j++) {
                startCycle = parents.get(startCycle).source;
            }

            List<Edge> cycle = new ArrayList<>();
            Edge edge = null;
            for (Node current = startCycle; ; current =(edge = parents.get(current)).source) {
                if (current.equals(startCycle) && cycle.size() > 1) {
                    break;
                }
                cycle.add(edge);
            }
            Collections.reverse(cycle);

            cycleEdges = cycle;

            return true;
        }
    }
}
