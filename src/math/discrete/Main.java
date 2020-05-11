package math.discrete;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
//
//        Node nodes = new Node();
//        graph.addNode(nodes);
//
//        Node node1 = new Node();
//        graph.addNode(node1);
//
//        Node node2 = new Node();
//        graph.addNode(node2);
//
//        Node node3 = new Node();
//        graph.addNode(node3);
//
//        Node node4 = new Node();
//        graph.addNode(node4);
//
//        Node nodet = new Node();
//        graph.addNode(nodet);
//
//        Edge edgeS1 = new Edge(nodes,node1);
//        edgeS1.capacity = 10;
//        graph.addEdge(edgeS1);
//
//        Edge  edgeS2 = new Edge(nodes,node2);
//        edgeS2.capacity = 10;
//        graph.addEdge(edgeS2);
//
//        Edge edge13 = new Edge(node1,node3);
//        edge13.capacity = 4;
//        graph.addEdge(edge13);
//
//        Edge edge12  = new Edge(node1,node2);
//        edge12.capacity = 2;
//        graph.addEdge(edge12);
//
//        Edge edge14  = new Edge(node1,node4);
//        edge14.capacity = 8;
//        graph.addEdge(edge14);
//
//        Edge edge24  = new Edge(node2,node4);
//        edge24.capacity = 9;
//        graph.addEdge(edge24);
//
//        Edge edge43  = new Edge(node4,node3);
//        edge43.capacity = 6;
//        graph.addEdge(edge43);
//
//        Edge edge3T  = new Edge(node3,nodet);
//        edge3T .capacity = 10;
//        graph.addEdge(edge3T);
//
//        Edge edge4T  = new Edge(node4,nodet);
//        edge4T.capacity = 10;
//        graph.addEdge(edge4T);
//
//        new Diniz().minCostMaxFlowCalculate(graph);
//
//        new EmondsCarp().minCostMaxFlowCalculate(graph);

        graph = new Graph();

        Node nodeA = new Node();
        Node nodeB = new Node();
        Node nodeC = new Node();
        Node nodeD = new Node();
        Node nodeE = new Node();
        Node nodeF = new Node();
        Node nodeG = new Node();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);
        graph.addNode(nodeG);


        Edge edgeAB = new Edge(nodeA,nodeB);
        edgeAB.capacity = 3;
        graph.addEdge(edgeAB);

        Edge edgeAD = new Edge(nodeA,nodeD);
        edgeAD.capacity = 3;
        graph.addEdge(edgeAD);

        Edge edgeCA = new Edge(nodeC,nodeA);
        edgeCA.capacity = 3;
        graph.addEdge(edgeCA);

        Edge edgeBC = new Edge(nodeB,nodeC);
        edgeBC.capacity = 4;
        graph.addEdge(edgeBC);

        Edge edgeCD = new Edge(nodeC,nodeD);
        edgeCD.capacity =1;
        graph.addEdge(edgeCD);

        Edge edgeCE = new Edge(nodeC,nodeE);
        edgeCE.capacity = 2;
        graph.addEdge(edgeCE);

        Edge edgeEB = new Edge(nodeE,nodeB);
        edgeEB.capacity =1;
        graph.addEdge(edgeEB);

        Edge edgeDE = new Edge(nodeD,nodeE);
        edgeDE.capacity = 2;
        graph.addEdge(edgeDE);

        Edge edgeDF = new Edge(nodeD,nodeF);
        edgeDF.capacity = 6;
        graph.addEdge(edgeDF);

        Edge edgeFG = new Edge(nodeF,nodeG);
        edgeFG.capacity =9;
        graph.addEdge(edgeFG);

        Edge edgeEG = new Edge(nodeE,nodeG);
        edgeEG.capacity = 1;
        graph.addEdge(edgeEG);


        new Diniz().minCostMaxFlowCalculate(graph);

        new EmondsCarp().minCostMaxFlowCalculate(graph);
    }
}
