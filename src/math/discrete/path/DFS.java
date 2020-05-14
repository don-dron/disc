package math.discrete.path;

import math.discrete.core.Graph;
import math.discrete.core.Node;

import java.util.Stack;

public class DFS {
    public void run(Graph graph) {
        for (Node node : graph.nodes) {
            node.mark = 0;
        }

        Stack<Node> stack = new Stack<>();
        for (Node node : graph.nodes) {
            if (node.mark == 0) {
                stack.push(node);
                while (!stack.isEmpty()) {
                    Node current = stack.pop();

                    if (current.mark == 0) {
                        current.mark = -1;
                        stack.push(node);
                        for (Node neighbour : current.neighbours.keySet()) {
                            if (neighbour.mark == 0) {
                                stack.push(neighbour);
                            }
                        }
                    } else if (current.mark == -1) {
                        current.mark = 1;
                    }
                }
            }
        }

    }
}
