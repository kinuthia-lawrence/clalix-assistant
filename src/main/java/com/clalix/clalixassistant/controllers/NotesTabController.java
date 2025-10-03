package com.clalix.clalixassistant.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class NotesTabController {
    @FXML private TextArea notesInput;
    @FXML private Button summarizeButton;
    @FXML private ChoiceBox<String> modeChoice;
    @FXML private Button exportButton;
    @FXML private Button copyButton;
    @FXML private Button highlightButton;
    @FXML private TextArea summaryOutput;
    @FXML private Label summaryMeta;

    @FXML
    public void initialize() {
        modeChoice.getItems().addAll("Concise", "Detailed", "Action Items");
        // TODO: Add summarize, export, copy, highlight logic
    }
}


