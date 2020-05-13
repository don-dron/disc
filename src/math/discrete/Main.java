package math.discrete;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Main {
    public static void main(String[] args) {
        simpleMinCostMaxFlowTest1();
        simpleMinCostMaxFlowTest();
        maxFlowTest();
        maxFlowTest1();
        maxFlowTest2();
        maxFlowTest3();
        belmanFordTest();
    }

    public static void simpleMinCostMaxFlowTest1() {
        Graph graph = new Graph();

        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);

        Edge edge12 = new Edge(node1, node2);
        edge12.capacity = 4;
        edge12.cost = 2;
        graph.addEdge(edge12);

        Edge edge13 = new Edge(node1, node3);
        edge13.capacity = 2;
        edge13.cost = 2;
        graph.addEdge(edge13);

        Edge edge23 = new Edge(node2, node3);
        edge23.capacity = 2;
        edge23.cost = 1;
        graph.addEdge(edge23);

        Edge edge24 = new Edge(node2, node4);
        edge24.capacity = 3;
        edge24.cost = 3;
        graph.addEdge(edge24);

        Edge edge34 = new Edge(node3, node4);
        edge34.capacity = 5;
        edge34.cost = 1;
        graph.addEdge(edge34);

        new MinCostMaxFlow().minCostMaxFlowCalculate(graph);
        graph.zeroingFlows();
    }

    public static void simpleMinCostMaxFlowTest() {
        Graph graph = new Graph();

        Node nodes = new Node();
        graph.addNode(nodes);

        Node node1 = new Node();
        graph.addNode(node1);

        Node node2 = new Node();
        graph.addNode(node2);

        Node node3 = new Node();
        graph.addNode(node3);

        Node node4 = new Node();
        graph.addNode(node4);

        Node node5 = new Node();
        graph.addNode(node5);

        Node node6 = new Node();
        graph.addNode(node6);

        Node nodet = new Node();
        graph.addNode(nodet);

        Edge edgeS1 = new Edge(nodes, node1);
        edgeS1.capacity = 9;
        graph.addEdge(edgeS1);

        Edge edgeS3 = new Edge(nodes, node3);
        edgeS3.capacity = 3;
        graph.addEdge(edgeS3);

        Edge edgeS5 = new Edge(nodes, node5);
        edgeS5.capacity = 1;
        graph.addEdge(edgeS5);


        Edge edge13 = new Edge(node1, node3);
        edge13.capacity = 2;
        graph.addEdge(edge13);

        Edge edge12 = new Edge(node1, node2);
        edge12.capacity = 2;
        graph.addEdge(edge12);

        Edge edge14 = new Edge(node1, node4);
        edge14.capacity = 5;
        graph.addEdge(edge14);


        Edge edge2t = new Edge(node2, nodet);
        edge2t.capacity = 6;
        graph.addEdge(edge2t);


        Edge edge34 = new Edge(node3, node4);
        edge34.capacity = 2;
        graph.addEdge(edge34);

        Edge edge35 = new Edge(node3, node5);
        edge35.capacity = 7;
        graph.addEdge(edge35);


        Edge edge56 = new Edge(node5, node6);
        edge56.capacity = 9;
        graph.addEdge(edge56);


        Edge edge42 = new Edge(node4, node2);
        edge42.capacity = 1;
        graph.addEdge(edge42);

        Edge edge45 = new Edge(node4, node5);
        edge45.capacity = 1;
        graph.addEdge(edge45);

        Edge edge4T = new Edge(node4, nodet);
        edge4T.capacity = 2;
        graph.addEdge(edge4T);

        Edge edge6T = new Edge(node6, nodet);
        edge6T.capacity = 8;
        graph.addEdge(edge6T);

        Edge edge64 = new Edge(node6, node4);
        edge64.capacity = 3;
        graph.addEdge(edge64);


        String rightResult = "0 -> 1 9/9\n" +
                "0 -> 3 3/3\n" +
                "0 -> 5 1/1\n" +
                "1 -> 3 2/2\n" +
                "1 -> 2 2/2\n" +
                "1 -> 4 5/5\n" +
                "2 -> 7 3/6\n" +
                "3 -> 4 5/2\n" +
                "3 -> 5 6/7\n" +
                "5 -> 6 8/9\n" +
                "4 -> 2 1/1\n" +
                "4 -> 5 1/1\n" +
                "4 -> 7 2/2\n" +
                "6 -> 7 8/8\n" +
                "6 -> 4 0/3\n";


        new MinCostMaxFlow().minCostMaxFlowCalculate(graph);
        assertEquals(rightResult, graph.getFlows());

        graph.zeroingFlows();
    }

    public static void belmanFordTest() {
        Graph graph = new Graph();

        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        Node node5 = new Node();
        Node node6 = new Node();
        Node node7 = new Node();
        Node node8 = new Node();

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addNode(node6);
        graph.addNode(node7);
        graph.addNode(node8);

        Edge edge12 = new Edge(node1, node2);
        edge12.length = 4;
        graph.addEdge(edge12);

        Edge edge13 = new Edge(node1, node3);
        edge13.length = 4;
        graph.addEdge(edge13);

        Edge edge41 = new Edge(node4, node1);
        edge41.length = 3;
        graph.addEdge(edge41);

        Edge edge43 = new Edge(node4, node3);
        edge43.length = 2;
        graph.addEdge(edge43);

        Edge edge36 = new Edge(node3, node6);
        edge36.length = -2;
        graph.addEdge(edge36);

        Edge edge35 = new Edge(node3, node5);
        edge35.length = 4;
        graph.addEdge(edge35);

        Edge edge54 = new Edge(node5, node4);
        edge54.length = 1;
        graph.addEdge(edge54);

        Edge edge57 = new Edge(node5, node7);
        edge57.length = -2;
        graph.addEdge(edge57);

        Edge edge65 = new Edge(node6, node5);
        edge65.length = -3;
        graph.addEdge(edge65);

        Edge edge62 = new Edge(node6, node2);
        edge62.length = 3;
        graph.addEdge(edge62);

        Edge edge76 = new Edge(node7, node6);
        edge76.length = 2;
        graph.addEdge(edge76);

        Edge edge78 = new Edge(node7, node8);
        edge78.length = 2;
        graph.addEdge(edge78);

        Edge edge85 = new Edge(node8, node5);
        edge85.length = -2;
        graph.addEdge(edge85);


        List<Node> negativeCycle =
                new BelmanFord().calculate(graph, node1);
        int a = 1;
    }

    public static void maxFlowTest() {
        Graph graph = new Graph();

        Node nodes = new Node();
        graph.addNode(nodes);

        Node node1 = new Node();
        graph.addNode(node1);

        Node node2 = new Node();
        graph.addNode(node2);

        Node node3 = new Node();
        graph.addNode(node3);

        Node node4 = new Node();
        graph.addNode(node4);

        Node nodet = new Node();
        graph.addNode(nodet);

        Edge edgeS1 = new Edge(nodes, node1);
        edgeS1.capacity = 10;
        graph.addEdge(edgeS1);

        Edge edgeS2 = new Edge(nodes, node2);
        edgeS2.capacity = 10;
        graph.addEdge(edgeS2);

        Edge edge13 = new Edge(node1, node3);
        edge13.capacity = 4;
        graph.addEdge(edge13);

        Edge edge12 = new Edge(node1, node2);
        edge12.capacity = 2;
        graph.addEdge(edge12);

        Edge edge14 = new Edge(node1, node4);
        edge14.capacity = 8;
        graph.addEdge(edge14);

        Edge edge24 = new Edge(node2, node4);
        edge24.capacity = 9;
        graph.addEdge(edge24);

        Edge edge43 = new Edge(node4, node3);
        edge43.capacity = 6;
        graph.addEdge(edge43);

        Edge edge3T = new Edge(node3, nodet);
        edge3T.capacity = 10;
        graph.addEdge(edge3T);

        Edge edge4T = new Edge(node4, nodet);
        edge4T.capacity = 10;
        graph.addEdge(edge4T);

        String rightResult = "0 -> 1 10/10\n" +
                "0 -> 2 9/10\n" +
                "1 -> 3 4/4\n" +
                "1 -> 2 0/2\n" +
                "1 -> 4 6/8\n" +
                "2 -> 4 9/9\n" +
                "4 -> 3 5/6\n" +
                "3 -> 5 9/10\n" +
                "4 -> 5 10/10\n";

        new Diniz().maxFlowCalculate(graph);
        assertEquals(rightResult, graph.getFlows());

        graph.zeroingFlows();

        new EmondsCarp().maxFlowCalculate(graph);
        assertEquals(rightResult, graph.getFlows());
    }

    public static void maxFlowTest1() {
        Graph graph = new Graph();

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


        Edge edgeAB = new Edge(nodeA, nodeB);
        edgeAB.capacity = 3;
        graph.addEdge(edgeAB);

        Edge edgeAD = new Edge(nodeA, nodeD);
        edgeAD.capacity = 3;
        graph.addEdge(edgeAD);

        Edge edgeCA = new Edge(nodeC, nodeA);
        edgeCA.capacity = 3;
        graph.addEdge(edgeCA);

        Edge edgeBC = new Edge(nodeB, nodeC);
        edgeBC.capacity = 4;
        graph.addEdge(edgeBC);

        Edge edgeCD = new Edge(nodeC, nodeD);
        edgeCD.capacity = 1;
        graph.addEdge(edgeCD);

        Edge edgeCE = new Edge(nodeC, nodeE);
        edgeCE.capacity = 2;
        graph.addEdge(edgeCE);

        Edge edgeEB = new Edge(nodeE, nodeB);
        edgeEB.capacity = 1;
        graph.addEdge(edgeEB);

        Edge edgeDE = new Edge(nodeD, nodeE);
        edgeDE.capacity = 2;
        graph.addEdge(edgeDE);

        Edge edgeDF = new Edge(nodeD, nodeF);
        edgeDF.capacity = 6;
        graph.addEdge(edgeDF);

        Edge edgeFG = new Edge(nodeF, nodeG);
        edgeFG.capacity = 9;
        graph.addEdge(edgeFG);

        Edge edgeEG = new Edge(nodeE, nodeG);
        edgeEG.capacity = 1;
        graph.addEdge(edgeEG);


        String rightResult = "0 -> 1 3/3\n" +
                "0 -> 3 3/3\n" +
                "2 -> 0 0/3\n" +
                "1 -> 2 3/4\n" +
                "2 -> 3 1/1\n" +
                "2 -> 4 2/2\n" +
                "4 -> 1 0/1\n" +
                "3 -> 4 3/2\n" +
                "3 -> 5 5/6\n" +
                "5 -> 6 5/9\n" +
                "4 -> 6 1/1\n";

        new Diniz().maxFlowCalculate(graph);
        assertEquals(rightResult, graph.getFlows());

        graph.zeroingFlows();

        new EmondsCarp().maxFlowCalculate(graph);
        assertEquals(rightResult, graph.getFlows());
    }

    public static void maxFlowTest2() {
        Graph graph = new Graph();


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


        Edge edgeAB = new Edge(nodeA, nodeB);
        edgeAB.capacity = 20;
        graph.addEdge(edgeAB);

        Edge edgeAC = new Edge(nodeA, nodeC);
        edgeAC.capacity = 15;
        graph.addEdge(edgeAC);

        Edge edgeBD = new Edge(nodeB, nodeD);
        edgeBD.capacity = 4;
        graph.addEdge(edgeBD);

        Edge edgeCD = new Edge(nodeC, nodeD);
        edgeCD.capacity = 4;
        graph.addEdge(edgeCD);

        Edge edgeBE = new Edge(nodeB, nodeE);
        edgeBE.capacity = 21;
        graph.addEdge(edgeBE);

        Edge edgeCF = new Edge(nodeC, nodeF);
        edgeCF.capacity = 9;
        graph.addEdge(edgeCF);

        Edge edgeDE = new Edge(nodeD, nodeE);
        edgeDE.capacity = 2;
        graph.addEdge(edgeDE);

        Edge edgeDF = new Edge(nodeD, nodeF);
        edgeDF.capacity = 10;
        graph.addEdge(edgeDF);

        Edge edgeEG = new Edge(nodeE, nodeG);
        edgeEG.capacity = 24;
        graph.addEdge(edgeEG);

        Edge edgeFG = new Edge(nodeF, nodeG);
        edgeFG.capacity = 10;
        graph.addEdge(edgeFG);

        String rightResult = "0 -> 1 20/20\n" +
                "0 -> 2 12/15\n" +
                "1 -> 3 0/4\n" +
                "2 -> 3 3/4\n" +
                "1 -> 4 20/21\n" +
                "2 -> 5 9/9\n" +
                "3 -> 4 2/2\n" +
                "3 -> 5 1/10\n" +
                "4 -> 6 22/24\n" +
                "5 -> 6 10/10\n";
        new Diniz().maxFlowCalculate(graph);
        assertEquals(rightResult, graph.getFlows());

        graph.zeroingFlows();

        new EmondsCarp().maxFlowCalculate(graph);
        assertEquals(rightResult, graph.getFlows());
    }

    public static void maxFlowTest3() {
        Graph graph = new Graph();

        Node nodes = new Node();
        graph.addNode(nodes);

        Node node1 = new Node();
        graph.addNode(node1);

        Node node2 = new Node();
        graph.addNode(node2);

        Node node3 = new Node();
        graph.addNode(node3);

        Node node4 = new Node();
        graph.addNode(node4);

        Node node5 = new Node();
        graph.addNode(node5);

        Node node6 = new Node();
        graph.addNode(node6);

        Node nodet = new Node();
        graph.addNode(nodet);

        Edge edgeS1 = new Edge(nodes, node1);
        edgeS1.capacity = 9;
        graph.addEdge(edgeS1);

        Edge edgeS3 = new Edge(nodes, node3);
        edgeS3.capacity = 3;
        graph.addEdge(edgeS3);

        Edge edgeS5 = new Edge(nodes, node5);
        edgeS5.capacity = 1;
        graph.addEdge(edgeS5);


        Edge edge13 = new Edge(node1, node3);
        edge13.capacity = 2;
        graph.addEdge(edge13);

        Edge edge12 = new Edge(node1, node2);
        edge12.capacity = 2;
        graph.addEdge(edge12);

        Edge edge14 = new Edge(node1, node4);
        edge14.capacity = 5;
        graph.addEdge(edge14);


        Edge edge2t = new Edge(node2, nodet);
        edge2t.capacity = 6;
        graph.addEdge(edge2t);


        Edge edge34 = new Edge(node3, node4);
        edge34.capacity = 2;
        graph.addEdge(edge34);

        Edge edge35 = new Edge(node3, node5);
        edge35.capacity = 7;
        graph.addEdge(edge35);


        Edge edge56 = new Edge(node5, node6);
        edge56.capacity = 9;
        graph.addEdge(edge56);


        Edge edge42 = new Edge(node4, node2);
        edge42.capacity = 1;
        graph.addEdge(edge42);

        Edge edge45 = new Edge(node4, node5);
        edge45.capacity = 1;
        graph.addEdge(edge45);

        Edge edge4T = new Edge(node4, nodet);
        edge4T.capacity = 2;
        graph.addEdge(edge4T);

        Edge edge6T = new Edge(node6, nodet);
        edge6T.capacity = 8;
        graph.addEdge(edge6T);

        Edge edge64 = new Edge(node6, node4);
        edge64.capacity = 3;
        graph.addEdge(edge64);


        String rightResult = "0 -> 1 9/9\n" +
                "0 -> 3 3/3\n" +
                "0 -> 5 1/1\n" +
                "1 -> 3 2/2\n" +
                "1 -> 2 2/2\n" +
                "1 -> 4 5/5\n" +
                "2 -> 7 3/6\n" +
                "3 -> 4 5/2\n" +
                "3 -> 5 6/7\n" +
                "5 -> 6 8/9\n" +
                "4 -> 2 1/1\n" +
                "4 -> 5 1/1\n" +
                "4 -> 7 2/2\n" +
                "6 -> 7 8/8\n" +
                "6 -> 4 0/3\n";


        new Diniz().maxFlowCalculate(graph);
        assertEquals(rightResult, graph.getFlows());

        graph.zeroingFlows();

        new EmondsCarp().maxFlowCalculate(graph);
        assertEquals(rightResult, graph.getFlows());
    }
}
