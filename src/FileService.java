import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {


    public static Network createNetwork(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            List<List<BoxValue>> network = new ArrayList<>();

            // Lecture des dimensions du réseau
            int nbRows = scanner.nextInt();
            int nbCols = scanner.nextInt();
            scanner.nextLine(); // Pour consommer le retour à la ligne

            // Lecture du réseau ligne par ligne
            for (int i = 0; i < nbRows; i++) {
                List<BoxValue> row = new ArrayList<>();
                for (int j = 0; j < nbCols; j++) {
                    int value = scanner.nextInt();
                    row.add(BoxValue.fromInt(value));
                }
                network.add(row);
            }

            scanner.close();
            return new Network(nbRows, nbCols, network);
        } catch (FileNotFoundException e) {
            System.err.println("Le fichier spécifié n'a pas été trouvé : " + path);
            return null;
        }
    }

}
