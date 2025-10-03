package com.clalix.clalixassistant.controllers;

import com.clalix.clalixassistant.service.GenerateTextFromTextInput;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.Node;
import com.clalix.clalixassistant.model.ChatMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

public class ChatTabController {
    @FXML
    private ListView<ChatMessage> messagesList;
    @FXML
    private TextArea inputArea;
    @FXML
    private Button sendButton;
    @FXML
    private Button suggestButton;
    @FXML
    private Button attachButton;
    @FXML
    private Button emojiButton;

    private final ObservableList<ChatMessage> messages = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        messagesList.setItems(messages);
        messagesList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(ChatMessage m, boolean empty) {
                super.updateItem(m, empty);
                if (empty || m == null) {
                    setGraphic(null);
                } else {
                    HBox root = new HBox(8);
                    root.setPadding(new javafx.geometry.Insets(4, 12, 4, 12));
                    VBox bubbleBox = new VBox(2);
                    bubbleBox.setMaxWidth(520);
                    Label msgLabel = new Label(m.getContent());
                    msgLabel.setWrapText(true);
                    msgLabel.getStyleClass().add("message-bubble");

                    // Fancy tick icon
                    ImageView tickIcon = new javafx.scene.image.ImageView(getClass().getResource("/images/tick.png").toExternalForm());
                    tickIcon.setFitHeight(20);
                    tickIcon.setFitWidth(20);
                    tickIcon.setPreserveRatio(true);
                    tickIcon.setSmooth(true);
                    tickIcon.setStyle("-fx-effect: dropshadow(gaussian, #2B6CB0, 4, 0.3, 0, 1); -fx-padding: 0 0 0 8;");

                    if (m.getRole() == ChatMessage.Role.USER) {
                        root.setAlignment(Pos.CENTER_RIGHT);
                        msgLabel.getStyleClass().add("user");
                        Label initials = new Label("U");
                        initials.setTextFill(Color.WHITE);
                        // Add tick icon as child of bubbleBox for user
                        bubbleBox.getChildren().addAll(msgLabel, tickIcon);
                        root.getChildren().addAll(bubbleBox);
                    } else {
                        root.setAlignment(Pos.CENTER_LEFT);
                        msgLabel.getStyleClass().add("assistant");
                        Circle avatar = new Circle(18, Color.web("#e6eef8"));
                        Label initials = new Label("A");
                        avatar.setStroke(Color.WHITE);
                        // Add tick icon as child of bubbleBox for assistant (if needed)
                        bubbleBox.getChildren().addAll(msgLabel);
                        root.getChildren().addAll(avatar, bubbleBox);
                    }
                    Label meta = new Label(m.getFormattedTime());
                    meta.setStyle("-fx-font-size: 10; -fx-text-fill: #888;");
                    bubbleBox.getChildren().add(meta);
                    setGraphic(root);
                }
            }
        });
        // Auto-scroll to bottom on new message
        messages.addListener((javafx.collections.ListChangeListener<ChatMessage>) c -> {
            if (!messages.isEmpty()) {
                Platform.runLater(() -> messagesList.scrollTo(messages.size() - 1));
            }
        });
        sendButton.setOnAction(e -> sendMessage());
        inputArea.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> {
                    if (e.isShiftDown()) {
                        inputArea.appendText("\n");
                    } else if (e.isControlDown()) {
                        sendMessage();
                        e.consume();
                    } else {
                        sendMessage();
                        e.consume();
                    }
                }
                case SHIFT -> {
                    // do nothing
                }

            }
        });
    }

    private void sendMessage() {
        String text = inputArea.getText().trim();
        if (!text.isEmpty()) {
            ChatMessage userMsg = new ChatMessage(ChatMessage.Role.USER, text);
            messages.add(userMsg);
            inputArea.clear();
            // Show typing indicator (optional UI logic)
            // Add a temporary system message or indicator if needed
            // Run AI call in background
            new Thread(() -> {
                String aiResponse = GenerateTextFromTextInput.generateText(text);
                Platform.runLater(() -> {
                    ChatMessage aiMsg = new ChatMessage(ChatMessage.Role.ASSISTANT, aiResponse);
                    messages.add(aiMsg);
                    // Hide typing indicator if used
                });
            }).start();
        }
    }

}
