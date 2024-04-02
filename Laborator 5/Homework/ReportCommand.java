package org.example;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Map;
import freemarker.template.*;

public class ReportCommand implements Command {
    private final Repository repository;

    public ReportCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() throws IOException, InvalidCommandException, InvalidDataException, TemplateException {
        /*Map<Person, List<Document>> data = repository.getDocuments();

        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><head><title>Repository Report</title></head><body>");
        htmlContent.append("<h1>Repository Report</h1>");

        for (Map.Entry<Person, List<Document>> entry : data.entrySet()) {
            Person person = entry.getKey();
            List<Document> documents = entry.getValue();

            htmlContent.append("<h2>").append(person.name()).append(" (ID: ").append(person.id()).append(")</h2>");
            htmlContent.append("<ul>");

            for (Document document : documents) {
                htmlContent.append("<li>").append(document.name()).append(" (Format: ").append(document.format()).append(")</li>");
            }

            htmlContent.append("</ul>");
        }

        htmlContent.append("</body></html>");

        File reportFile = new File("report.html");
        try (FileWriter writer = new FileWriter(reportFile)) {
            writer.write(htmlContent.toString());
            Desktop.getDesktop().open(new File("report.html"));
        }
/*

        Configuration config = new Configuration(Configuration.VERSION_2_3_32);
        try {
            config.setClassForTemplateLoading(ReportCommand.class, "/templates");

            Template template = config.getTemplate("report_template.ftl");

            List<Document> docs = repository.getListDocuments();

            List<Map<String, Object>> docDataList = new ArrayList<>();
            for(Document doc : docs) {
                Map<String, Object> docData = new HashMap<>();
                docData.put("name", doc.name());
                docData.put("format", doc.format());
                docDataList.add(docData);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("documents", docDataList);

            StringWriter out = new StringWriter();
            template.process(data, out);

            String html = out.toString();

            File outputFile = new File("report.html");
            try(PrintWriter htmlReport = new PrintWriter(outputFile)) {
                htmlReport.print(html);
            }

            String path = outputFile.getAbsolutePath();
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + path);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}

