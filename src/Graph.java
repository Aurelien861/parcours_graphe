import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int nbVertices;
    private final List<List<Integer>> adjacencyList;

    private int source;

    private int destination;

    public Graph(int nbVertices) {
        this.nbVertices = nbVertices;
        this.adjacencyList = new ArrayList<>();
        for (int i = 0; i < nbVertices; i++) {
            this.adjacencyList.add(new ArrayList<>());
        }
    }

    public Graph(String path) {
        Network network = new Network(path);
        int nbColumns = network.getNbColumns();
        int nbRows = network.getNbRows();
        this.nbVertices = nbColumns * nbRows;
        this.adjacencyList = new ArrayList<>();
        for (int i = 0; i < nbVertices; i++) {
            this.adjacencyList.add(new ArrayList<>());
        }
        this.addEdgesFromNetwork(network);
    }

    private void addEdgesFromNetwork(Network network) {
        System.out.println(network);
        int nbColumns = network.getNbColumns();
        int nbRows = network.getNbRows();
        for (int i = 0; i < nbRows; i++) {
            for(int j = 0; j < nbColumns; j++) {
                int vertexNum = i * nbColumns + j;
                if(network.getArray().get(i).get(j) != BoxValue.OBSTACLE) {
                    if(network.getArray().get(i).get(j) == BoxValue.START) {
                        this.source = vertexNum;
                    } else if(network.getArray().get(i).get(j) == BoxValue.END) {
                        this.destination = vertexNum;
                    }
                    if(j < nbColumns - 1 && network.getArray().get(i).get(j + 1) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum + 1);                      // droite
                    }
                    if(j < nbColumns - 1 && i < nbRows - 1 && network.getArray().get(i + 1).get(j + 1) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum + nbColumns + 1);      // bas droite
                    }
                    if(i < nbRows - 1 && network.getArray().get(i + 1).get(j) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum + nbColumns);              // bas
                    }
                    if(i < nbRows - 1 && j > 0 && network.getArray().get(i + 1).get(j - 1) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum + nbColumns - 1);      // bas gauche
                    }
                    if(j > 0 && network.getArray().get(i).get(j - 1) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum - 1);                      // gauche
                    }
                    if(j > 0 && i > 0 && network.getArray().get(i - 1).get(j - 1) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum - nbColumns - 1);      // haut gauche
                    }
                    if(i > 0 && network.getArray().get(i - 1).get(j) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum - nbColumns);              // haut
                    }
                    if(i > 0 && j < nbColumns - 1 && network.getArray().get(i - 1).get(j + 1) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum - nbColumns + 1);      // haut droite
                    }
                }
            }
        }
    }

    public void addEdge(int u, int v) {
        if( u >= 0 && u < this.nbVertices && v >= 0 && v < this.nbVertices ) {
            boolean edgeExists = false;
            for (Integer entry : adjacencyList.get(u)) {
                if (entry == v) {
                    edgeExists = true;
                    break;
                }
            }

            // Ajouter l'arête si elle n'existe pas déjà
            if (!edgeExists) {
                adjacencyList.get(u).add(v);
                adjacencyList.get(v).add(u); // Pour les graphes non orientés, les poids sont symétriques
            }
        }
    }

    public int getNbVertices() {
        return nbVertices;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public List<List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nbVertices; i++) {
            sb.append("Vertex ").append(i).append(": ");
            for (Integer vertice : adjacencyList.get(i)) {
                int neighbor = vertice;
                sb.append(" ").append(neighbor).append(" ");
            }
            sb.deleteCharAt(sb.length() - 1); // Supprimer la virgule finale
            sb.deleteCharAt(sb.length() - 1); // Supprimer l'espace final
            sb.append("\n");
        }
        return sb.toString();
    }
}
