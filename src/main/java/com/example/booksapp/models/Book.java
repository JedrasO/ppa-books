package com.example.booksapp.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private int id;
    private String authorsName;
    private String authorsSurname;
    private String title;
    private int publishYear;
    private String publisher;
    private String description;

    public Book(int id, String authorsName, String authorsSurname, String title, int publishYear, String publisher, String description) {
        this.id = id;
        this.authorsName = authorsName;
        this.authorsSurname = authorsSurname;
        this.title = title;
        this.publishYear = publishYear;
        this.publisher = publisher;
        this.description = description;
    }

    public Book(int id) {
        this(id, "", "", "", 0, "", "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorsName() {
        return authorsName;
    }

    public void setAuthorsName(String authorsName) {
        this.authorsName = authorsName;
    }

    public String getAuthorsSurname() {
        return authorsSurname;
    }

    public void setAuthorsSurname(String authorsSurname) {
        this.authorsSurname = authorsSurname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + getId() +
                ", authorsName=" + getAuthorsName() +
                ", authorsSurname=" + getAuthorsSurname() +
                ", title=" + getTitle() +
                ", publishYear=" + getPublishYear() +
                ", publisher=" + getPublisher() +
                ", description=" + getDescription() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((Book)obj).getId();
    }
}
