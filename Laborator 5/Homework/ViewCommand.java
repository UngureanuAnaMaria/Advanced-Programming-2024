package org.example;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ViewCommand implements Command {
    private final String documentPath;

    public ViewCommand(String documentPath) {
        this.documentPath = documentPath;
    }

    @Override
    public void execute() throws IOException, InvalidCommandException, InvalidDataException {
        File file = new File(documentPath);
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Opening document " + documentPath + " is not supported.");
            }
        } catch (IOException e) {
            System.err.println("Error opening document: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
