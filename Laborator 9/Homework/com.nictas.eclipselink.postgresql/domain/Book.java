package com.nictas.eclipselink.postgresql.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(name = "Book.findAll",
                query = "select e from Book e order by e.name"),
        @NamedQuery(name = "Book.findByName",
                query = "SELECT a FROM Book a WHERE a.name = :name"),
        @NamedQuery(name = "Book.findById",
                query = "SELECT a FROM Book a WHERE a.id = :id"),
        @NamedQuery(name = "Book.findByAuthor",
                query = "SELECT a FROM Book a WHERE a.author = :author"),
        @NamedQuery(name = "Book.findByGenre",
                query = "SELECT a FROM Book a WHERE a.genre = :genre"),
        @NamedQuery(name = "Book.findByYear",
                query = "SELECT a FROM Book a WHERE a.year = :year")
})
public class Book implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "id", strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authors", referencedColumnName = "name_author", nullable = false)
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genres", referencedColumnName = "name", nullable = false)
    private Genre genre;


    /*@Column(name = "authors")
    private String author;

    @Column(name="genres")
    private String genre;*/

    @Column(name = "year")
    private int year;

    @Column(name = "number_of_pages")
    private int nrPages;

    @Transient
    private String publishingHouse;

    public Book() {
        // Required by JPA.
    }

    /*public Book(String name, String author, String genre, int year, int nrPages, String publishingHouse) {
        this.name = name;
        this.author = author;
        //this.genre = genre;
        this.year = year;
        this.nrPages = nrPages;
        this.publishingHouse = publishingHouse;
    }*/

    public Book(String title, Author author, Genre genre, int year, int numberOfPages, String publishingHouse) {
        this.name = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.nrPages = numberOfPages;
        this.publishingHouse = publishingHouse;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
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

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", nrPages=" + nrPages +
                ", publishingHouse='" + publishingHouse + '\'' +
                '}';
    }
}
