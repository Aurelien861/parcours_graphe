import java.util.ArrayList;
import java.util.List;

public class Network {

    private int nbRows;
    private int nbColumns;
    private List<List<BoxValue>> array;

    public Network(int nbRows, int nbColumns, List<List<BoxValue>> network) {
        this.nbRows = nbRows;
        this.nbColumns = nbColumns;
        this.array = network;
    }

    public Network(String path){
        Network network = FileService.createNetwork(path);
        assert network != null;
        this.copyNetwork(network);
    }

    public void copyNetwork(Network network) {
        this.nbColumns = network.nbColumns;
        this.nbRows = network.nbRows;
        this.array = new ArrayList<>();
        for (List<BoxValue> row : network.array) {
            List<BoxValue> newRow = new ArrayList<>(row);
            this.array.add(newRow);
        }
    }

    public int getNbRows() {
        return nbRows;
    }

    public int getNbColumns() {
        return nbColumns;
    }

    public List<List<BoxValue>> getArray() {
        return array;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for (List<BoxValue> row : this.array) {
            for (BoxValue boxValue : row) {
                str.append(boxValue.value).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }

}
