package com.clalix.clalixassistant.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController {
    @FXML
    private TabPane mainTabPane;
    @FXML
    private Tab chatTab;
    @FXML
    private Tab tasksTab;
    @FXML
    private Tab notesTab;
    @FXML
    private VBox profilePanel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label lastSavedLabel;
    @FXML
    private Label progressSpinner;
    @FXML
    private HBox globalSearch;
    @FXML
    private Button settingsButton;

    @FXML
    public void initialize() {
        try {
            Parent chatContent = FXMLLoader.load(getClass().getResource("/fxml/chat-tab.fxml"));
            chatTab.setContent(chatContent);
            Parent tasksContent = FXMLLoader.load(getClass().getResource("/fxml/tasks-tab.fxml"));
            tasksTab.setContent(tasksContent);
            Parent notesContent = FXMLLoader.load(getClass().getResource("/fxml/notes-tab.fxml"));
            notesTab.setContent(notesContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
