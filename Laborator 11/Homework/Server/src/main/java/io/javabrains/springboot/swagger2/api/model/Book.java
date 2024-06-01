package io.javabrains.springboot.swagger2.api.model;

public class Book {
    private int id;

    private String name;

    private String authors;

    private String genres;

    private int year;

    private int nrPages;


    public Book(int id, String name, String authors, String genres, int year, int nrPages) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.genres = genres;
        this.year = year;
        this.nrPages = nrPages;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNrPages() {
        return nrPages;
    }

    public void setNrPages(int nrPages) {
        this.nrPages = nrPages;
    }
}
