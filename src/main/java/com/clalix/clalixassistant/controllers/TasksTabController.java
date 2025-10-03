package com.clalix.clalixassistant.controllers;

import com.clalix.clalixassistant.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TasksTabController {
    @FXML private TextField titleField;
    @FXML private DatePicker deadlinePicker;
    @FXML private ComboBox<String> priorityBox;
    @FXML private Button addTaskButton;
    @FXML private TableView<Task> tasksTable;
    @FXML private TableColumn<Task, String> titleColumn;
    @FXML private TableColumn<Task, String> deadlineColumn;
    @FXML private TableColumn<Task, String> priorityColumn;
    @FXML private TableColumn<Task, String> statusColumn;
    @FXML private TableColumn<Task, Void> actionsColumn;
    @FXML private VBox previewPanel;

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tasksTable.setItems(tasks);
        priorityBox.getItems().addAll("High", "Medium", "Low");
        // TODO: Set up columns, add/edit/delete logic, preview panel, AI subtasks
    }
}


