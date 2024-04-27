package org.example;
import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String args[]) {
        try {
            var authorsDAO = new AuthorDAO();
            authorsDAO.create("William Shakespeare");
            Database.getConnection().commit();
            List<String> authors = authorsDAO.getAllAuthors();
            System.out.println("Authors:");
            for (String author : authors) {
                System.out.println(author);
            }
            System.out.println(authorsDAO.findByName("William Shakespeare"));
            Database.getConnection().close();
        } catch (SQLException e) {
            System.err.println(e);
            Database.rollback();
        }
    }
}
