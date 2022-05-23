package com.example.booksapp;

import com.example.booksapp.dbtest.DbContext;
import com.example.booksapp.models.Book;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.tableview2.cell.TextField2TableCell;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    public TableView booksTable;
    @FXML
    public Button newBookButton;
    @FXML
    public Button saveButton;
    public TextField authorsNameInputBox;
    public TextField authorsSurnameInputBox;
    public TextField titleInputBox;
    public TextField publisherInputBox;
    public TextField descryptionInputBox;
    public Button filterButton;

    @FXML
    public void initialize() {
        TableColumn<Book, BooleanProperty> checkBoxColumn = new TableColumn<>();
        checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        checkBoxColumn.setCellFactory(p -> new CheckBoxTableCell<>());
        checkBoxColumn.setOnEditCommit(e -> e.getRowValue().setSelected(e.getNewValue().get()));

        TableColumn<Book, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");

        TableColumn<Book, String> authorsNameColumn = new TableColumn<>("Name");
        authorsNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorsName"));
        authorsNameColumn.setCellFactory(p -> new TextFieldTableCell<>(new DefaultStringConverter()));
        authorsNameColumn.setOnEditCommit(e -> e.getRowValue().setAuthorsName(e.getNewValue()));

        TableColumn<Book, String> authorsSurnameColumn = new TableColumn<>("Surname");
        authorsSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("authorsSurname"));
        authorsSurnameColumn.setCellFactory(p -> new TextFieldTableCell<>(new DefaultStringConverter()));
        authorsSurnameColumn.setOnEditCommit(e -> e.getRowValue().setAuthorsSurname(e.getNewValue()));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(p -> new TextFieldTableCell<>(new DefaultStringConverter()));
        titleColumn.setOnEditCommit(e -> e.getRowValue().setTitle(e.getNewValue()));

        TableColumn<Book, Integer> publishedColumn = new TableColumn<>("Published");
        publishedColumn.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        publishedColumn.setCellFactory(p -> new TextFieldTableCell<>(new IntegerStringConverter()));
        publishedColumn.setOnEditCommit(e -> e.getRowValue().setPublishYear(e.getNewValue()));

        TableColumn<Book, String> publisherColumn = new TableColumn<>("Published by");
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        publisherColumn.setCellFactory(p -> new TextFieldTableCell<>(new DefaultStringConverter()));
        publisherColumn.setOnEditCommit(e -> e.getRowValue().setPublisher(e.getNewValue()));

        TableColumn<Book, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(p -> new TextFieldTableCell<>(new DefaultStringConverter()));
        descriptionColumn.setOnEditCommit(e -> e.getRowValue().setDescription(e.getNewValue()));

        authorColumn.getColumns().addAll(authorsNameColumn, authorsSurnameColumn);
        booksTable.getColumns().addAll(checkBoxColumn, idColumn, authorColumn, titleColumn, publishedColumn, publisherColumn, descriptionColumn);
        refreshData();
    }

    public void save() {
        List<Book> booksFromDb = DbContext.getBooks();
        List<Book> books = new ArrayList(booksTable.getItems());

        List<Book> newBooks = books.stream().filter(book -> !booksFromDb.contains(book)).toList();
        if (newBooks.size() > 0)
            DbContext.addBooks(newBooks);
        DbContext.updateBooks(books);
        refreshData();
    }

    public void refreshData() {
        booksTable.setItems(FXCollections.observableArrayList(DbContext.getBooks()));
        booksTable.refresh();
    }

    public void addBook() {
        List<Book> data = new ArrayList(booksTable.getItems());
        data.add(new Book(data.stream().mapToInt(Book::getId).max().orElse(1) + 1));
        booksTable.setItems(FXCollections.observableArrayList(data));
    }

    public void deleteSelected() {
        System.out.println("delete button");
        DbContext.deleteBooks(booksTable.getItems().stream().filter(b -> ((Book)b).isSelected()).toList());
        refreshData();
    }

    public void filter() {
        // przjerzec wszystkie pola
        List<Book> filtered = DbContext.getBooksFiltered(authorsNameInputBox.getText(), authorsSurnameInputBox.getText(), titleInputBox.getText(), publisherInputBox.getText(), descryptionInputBox.getText());
        booksTable.setItems(FXCollections.observableArrayList(filtered));
        booksTable.refresh();
    }

}