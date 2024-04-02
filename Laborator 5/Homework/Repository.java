package org.example;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

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

    public Map<Person, List<String>> readAbilitiesFromExcel(File excelFile) throws IOException{
        Map<Person, List<String>> personAbilitiesMap = new HashMap<>();

        FileInputStream fis = new FileInputStream(excelFile);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();

        // Till there is an element condition holds true
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                Cell idCell = row.getCell(0);
                Cell abilitiesCell = row.getCell(1);

                if (idCell != null && abilitiesCell != null) {
                    int personId = (int) idCell.getNumericCellValue(); // Assuming person ID is numeric
                    String abilitiesString = abilitiesCell.getStringCellValue();
                    List<String> abilities = Arrays.asList(abilitiesString.split(",")); // Assuming abilities are comma-separated

                    Person person = findPersonById(personId); // You need to implement this method
                    if (person != null) {
                        personAbilitiesMap.putIfAbsent(person, new ArrayList<>());
                        personAbilitiesMap.get(person).addAll(abilities);
                    }
                }
            }
        }
        /*
        for (Row row : sheet) {
            Cell idCell = row.getCell(0); // Assuming person ID is in the first column
            Cell abilitiesCell = row.getCell(1); // Assuming abilities are in the second column

            if (idCell != null && abilitiesCell != null) {
                int personId = (int) idCell.getNumericCellValue(); // Assuming person ID is numeric
                String abilitiesString = abilitiesCell.getStringCellValue();
                List<String> abilities = Arrays.asList(abilitiesString.split(",")); // Assuming abilities are comma-separated

                Person person = findPersonById(personId); // You need to implement this method
                if (person != null) {
                    personAbilitiesMap.putIfAbsent(person, new ArrayList<>());
                    personAbilitiesMap.get(person).addAll(abilities);
                }
            }
        }*/

        workbook.close();
        fis.close();
        return personAbilitiesMap;
    }


    public Person findPersonById(int id) {
        for (Person person : documents.keySet()) {
            if (person.id() == id) {
            return person;
            }
        }
         return null; // Person not found
    }
    public Map<Person, List<Document>> getDocuments() {
        return documents;
    }

    public List<Document> getListDocuments() {
        List<Document> allDocuments = new ArrayList<>();
        for (List<Document> personDocuments : documents.values()) {
            allDocuments.addAll(personDocuments);
        }
        return allDocuments;
    }
}

