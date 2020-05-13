package math.discrete;

import java.util.ArrayList;
import java.util.List;

public class FlowUtils {
    public static List<Edge> nodesToEdgesPath(List<Node> shortestPath) {
        List<Edge> shortestPathEdges = new ArrayList<>();

        for (int i = 0; i < shortestPath.size() - 1; i++) {
            Node node = shortestPath.get(i);
            shortestPathEdges.add(node.neighbours.get(shortestPath.get(i + 1)));
        }
        return shortestPathEdges;
    }

}
