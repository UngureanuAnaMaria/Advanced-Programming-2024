package org.example;

public record Document(String name, String format) {
public Document(String name, String format) {
    this.name = name;
    this.format = format;
}

public String name() {
    return this.name;
}

public String format() {
    return this.format;
}
}
