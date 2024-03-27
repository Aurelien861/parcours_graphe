import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int nbVertices;
    private final List<List<AbstractMap.SimpleEntry<Integer, Double>>> adjacencyList;

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
                    if(j < nbColumns - 1 && network.getArray().get(i).get(j + 1) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum + 1, 1);                      // droite
                    }
                    if(j < nbColumns - 1 && i < nbRows - 1 && network.getArray().get(i + 1).get(j + 1) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum + nbColumns + 1, Math.sqrt(2));      // bas droite
                    }
                    if(i < nbRows - 1 && network.getArray().get(i + 1).get(j) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum + nbColumns, 1);              // bas
                    }
                    if(i < nbRows - 1 && j > 0 && network.getArray().get(i + 1).get(j - 1) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum + nbColumns - 1, Math.sqrt(2));      // bas gauche
                    }
                    if(j > 0 && network.getArray().get(i).get(j - 1) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum - 1, 1);                      // gauche
                    }
                    if(j > 0 && i > 0 && network.getArray().get(i - 1).get(j - 1) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum - nbColumns - 1, Math.sqrt(2));      // haut gauche
                    }
                    if(i > 0 && network.getArray().get(i - 1).get(j) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum - nbColumns, 1);              // haut
                    }
                    if(i > 0 && j < nbColumns - 1 && network.getArray().get(i - 1).get(j + 1) != BoxValue.OBSTACLE) {
                        this.addEdge(vertexNum, vertexNum - nbColumns + 1, Math.sqrt(2));      // haut droite
                    }
                }
            }
        }
    }

    public void addEdge(int u, int v, double weight) {
        if( u >= 0 && u < this.nbVertices && v >= 0 && v < this.nbVertices && weight >= 0 ) {
            boolean edgeExists = false;
            for (AbstractMap.SimpleEntry<Integer, Double> entry : adjacencyList.get(u)) {
                if (entry.getKey() == v) {
                    edgeExists = true;
                    break;
                }
            }

            // Ajouter l'arête si elle n'existe pas déjà
            if (!edgeExists) {
                adjacencyList.get(u).add(new AbstractMap.SimpleEntry<>(v, weight));
                adjacencyList.get(v).add(new AbstractMap.SimpleEntry<>(u, weight)); // Pour les graphes non orientés, les poids sont symétriques
            }
        }
    }

    public List<AbstractMap.SimpleEntry<Integer, Double>> getNeighbors(int vertex) {
        return adjacencyList.get(vertex);
    }

    public int getNbVertices() {
        return nbVertices;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nbVertices; i++) {
            sb.append("Vertex ").append(i).append(": ");
            for (AbstractMap.SimpleEntry<Integer, Double> pair : adjacencyList.get(i)) {
                int neighbor = pair.getKey();
                double weight = pair.getValue();
                sb.append("(").append(neighbor).append(", ");
                sb.append(String.format("%.2f", weight)).append("), "); // Formater le poids avec 2 chiffres après la virgule
            }
            sb.deleteCharAt(sb.length() - 1); // Supprimer la virgule finale
            sb.deleteCharAt(sb.length() - 1); // Supprimer l'espace final
            sb.append("\n");
        }
        return sb.toString();
    }
}
