package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main app = new Main();
        app.testRepo();
        //app.testLoadView();
    }

    private void testRepo() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.print("> ");
        String input = scanner.nextLine().trim();
        //System.out.println(input);

        var repo = new Repository(input);
        //C:\\Users\\anaun\\OneDrive\\Desktop\\Lab5
        // var doc = repo.findDocument("...");
        repo.loadDocuments();
        repo.printMap();

        while (running) {
            System.out.print("> ");
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue; //skip empty lines
            }

            switch (input) {
                case "exit":
                    running = false;
                    break;
            }

            if (running) {
                String[] file = input.split("-");
                String command = file[0];

                switch (command) {
                    case "view":
                        Command viewCommand = new ViewCommand(file[1]);
                        try {
                            viewCommand.execute();
                            System.out.println("Document opened successfully.");
                        } catch (Exception e) {
                            System.err.println("Error opening document: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    case "report":
                        Command reportCommand = new ReportCommand(repo);
                        try {
                            reportCommand.execute();
                            System.out.println("Report created successfully.");
                        } catch (Exception e) {
                            System.err.println("Error creating report: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    case "export":
                        Command exportCommand = new ExportCommand(repo, file[1]);
                        //"output.json"
                        try {
                            exportCommand.execute();
                            System.out.println("Repository exported to JSON file successfully.");
                        } catch (Exception e) {
                            System.err.println("Error export: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    default:
                        System.out.println("Unknown command: " + command);
                        break;
                }
            }

        }
    }
}
