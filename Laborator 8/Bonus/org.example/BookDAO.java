package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public void create(int year, String title, String authors, String genres) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO books (year, title, authors, genres) VALUES (?, ?, ?, ?)")) {
            pstmt.setInt(1, year);
            pstmt.setString(2, title);
            pstmt.setString(3, authors);
            pstmt.setString(4, genres);
            pstmt.executeUpdate();
        }
    }

    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select id from books where title='" + name + "'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
    }

    public String findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select title from books where id=" + id)) {
            return rs.next() ? rs.getString(1) : null;
        }
    }

    public void getAllBooks() throws SQLException {
        List<String> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String book = "Id: " + rs.getString("id") + ", Title: " +
                        rs.getString("title") + ", Authors: " +
                        rs.getString("authors") + ", Genres: " +
                        rs.getString("genres") + ", Year: " +
                        rs.getString("year") + "Number of pages: " +
                        rs.getString("number_of_pages") +"\n";

                books.add(book);
            }
        }
        System.out.println(books);
    }
}
