package math.discrete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Node {
    public Graph graph;
    public NodeTypes type = NodeTypes.NONE;
    public Map<Node,Edge> neighbours = new HashMap<>();

    public Node(){

    }

    public Node(Node node){
        this.graph = node.graph;
        this.type = node.type;
        this.neighbours = new HashMap<>(node.neighbours);
    }

    public String toString(){
        return graph.nodes.indexOf(this) + "\n\n"+ "Neighbours: " + neighbours.entrySet().stream().map(node->Integer.toString(graph.nodes.indexOf(node.getKey())))
                .collect(Collectors.joining(", "));
    }

    public enum NodeTypes {
        NONE, SOURCE, TARGET
    }
}
