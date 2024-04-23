import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        String path = args[0];
        /*Network network = new Network(args[0]);
        System.out.println(network);*/
        /*Graph graph = new Graph(5);
        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 4, 7);

        System.out.println("Graph:");
        System.out.println(graph);*/
        Graph graph = new Graph(path);
        System.out.println(graph);
        System.out.println(LinearSolver.solveShortestPath(graph));
    }
}
