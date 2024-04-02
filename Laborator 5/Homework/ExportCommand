package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.awt.*;
public class ExportCommand implements Command {
    private final Repository repository;
    private final String exportPath;

    public ExportCommand(Repository repository, String exportPath) {
        this.repository = repository;
        this.exportPath = exportPath;
    }

    @Override
    public void execute() throws IOException, InvalidCommandException, InvalidDataException {
        ObjectMapper objectMapper = new ObjectMapper();
        File outputFile = new File(exportPath);

        try {
            objectMapper.writeValue(outputFile, repository.getDocuments());
            System.out.println("Repository exported to JSON file: " + exportPath);

            File exportFile = new File(exportPath);
            Desktop.getDesktop().open(exportFile);

        } catch (IOException e) {
            System.err.println("Error exporting repository to JSON file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
