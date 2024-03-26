import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {
    private String directory;
    private Map<Person, List<Document>> documents = new HashMap<>();

    public Repository(String directory) {
        this.directory = directory;
        loadDocuments();
    }

    public void loadDocuments() {
        File masterDirectory = new File(directory);
        File[] personDirectorires = masterDirectory.listFiles();
        if (personDirectorires != null) {
            for (File personDir : personDirectorires) {
                List<Document> personDocuments = new ArrayList<>();
                File[] files = personDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        String fileName = file.getName();
                        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
                        personDocuments.add(new Document(fileName, fileExtension));
                    }
                }
                /*
                Metoda .split("_") este folosită pentru a împărți șirul în substrings folosind separatorul "_". Rezultatul este un array de strings care conține părțile componente ale șirului inițial, separate de separatorul specificat.
                În cazul dat, .split("_") este folosit pentru a împărți numele directorului persoanei în două părți: numele și id-ul. De exemplu, dacă avem un director cu numele "Popescu_1001", .split("_") va împărți acest șir în două părți: "Popescu" și "1001".
                [1] indică faptul că ne interesează a doua parte din array-ul rezultat, adică id-ul persoanei. Astfel, .split("_")[1] returnează id-ul persoanei din numele directorului.*/
                documents.put(new Person(Integer.parseInt(personDir.getName().split("_")[1]), personDir.getName().split("_")[0]), personDocuments);
            }
        }
    }

    public void printMap() {
        for (Map.Entry<Person, List<Document>> entry : documents.entrySet()) {
            Person person = entry.getKey();
            List<Document> personDocuments = entry.getValue();

            System.out.println("Person: " + person.name() + " (ID: " + person.id() + ")");
            for (Document document : personDocuments) {
                System.out.println("  Document: " + document.name() + " (Format: " + document.format() + ")");
            }
        }
    }
}
