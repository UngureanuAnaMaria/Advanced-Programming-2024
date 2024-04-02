package org.example;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface Command {
    void execute() throws IOException, InvalidCommandException, InvalidDataException, TemplateException;
}
