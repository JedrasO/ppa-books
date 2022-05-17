package com.example.booksapp.dbtest;


import com.example.booksapp.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbContext {
    private static final String url = "jdbc:sqlserver://localhost:57462;database=ppa;encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30";

    static {
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean tableExistsSQL(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});

        return resultSet.next();
    }

    public static List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, "SA", "MyPass@word")) {
            String sql = """
                    SELECT [Id], [Authors_Name], [Authors_Surname], [Title], [Publish_Year], [Publisher], [Description]
                    FROM [Book];
                    """;
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String authorsName = resultSet.getString(2);
                String authorsSurname = resultSet.getString(3);
                String title = resultSet.getString(4);
                int publishYear = resultSet.getInt(5);
                String publisher = resultSet.getString(6);
                String description = resultSet.getString(7);
                books.add(new Book(id, authorsName, authorsSurname, title, publishYear, publisher, description));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }


    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, "SA", "MyPass@word")) {
            System.out.println("connected");
            System.out.println("check");
            System.out.println("check v2");
            if (!tableExistsSQL(connection, "Book")) {
                String createSql = """
                    CREATE TABLE Book(
                        Id INT IDENTITY PRIMARY KEY,
                        Authors_Name VARCHAR(40),
                        Authors_Surname VARCHAR(50),
                        Title VARCHAR(100),
                        Publish_Year INT,
                        Publisher VARCHAR(100),
                        Description VARCHAR(255)
                    )
                    """;

                connection.createStatement().executeUpdate(createSql);
                System.out.println("table created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBooks(List<Book> books) {
        try (Connection connection = DriverManager.getConnection(url, "SA", "MyPass@word")) {
            String createSql = """
                UPDATE Book
                SET Authors_Name = ?,
                    Authors_Surname = ?,
                    Title = ?,
                    Publish_Year = ?,
                    Publisher = ?,
                    Description = ?
                WHERE [Id] = ?;
                """;

            PreparedStatement statement = connection.prepareStatement(createSql);
            for (Book book: books){
                statement.setString(1, book.getAuthorsName());
                statement.setString(2, book.getAuthorsSurname());
                statement.setString(3, book.getTitle());
                statement.setInt(4, book.getPublishYear());
                statement.setString(5, book.getPublisher());
                statement.setString(6, book.getDescription());
                statement.setInt(7, book.getId());

                statement.executeUpdate();
            }
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addBooks(List<Book> newBooks) {
        try (Connection connection = DriverManager.getConnection(url, "SA", "MyPass@word")) {
            String createSql = """
                INSERT INTO Book(Authors_Name, Authors_Surname, Title, Publish_Year, Publisher, Description)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

            PreparedStatement statement = connection.prepareStatement(createSql);

            for (Book book: newBooks) {
                statement.setString(1, book.getAuthorsName());
                statement.setString(2, book.getAuthorsSurname());
                statement.setString(3, book.getTitle());
                statement.setInt(4, book.getPublishYear());
                statement.setString(5, book.getPublisher());
                statement.setString(6, book.getDescription());
                statement.executeUpdate();
            }
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteBooks(List<Book> books) {
        try (Connection connection = DriverManager.getConnection(url, "SA", "MyPass@word")) {
            String createSql = """
                DELETE FROM Book
                WHERE [Id] = ?
                """;

            PreparedStatement statement = connection.prepareStatement(createSql);

            for (Book book: books) {
                statement.setInt(1, book.getId());
                statement.executeUpdate();
            }
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
