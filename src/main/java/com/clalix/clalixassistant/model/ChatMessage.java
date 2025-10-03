package com.clalix.clalixassistant.model;


import javafx.beans.property.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

public class ChatMessage {
    public enum Role { USER, ASSISTANT, SYSTEM }

    private final String id;
    private final StringProperty content = new SimpleStringProperty();
    private final ObjectProperty<Role> role = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> timestamp = new SimpleObjectProperty<>();
    private final BooleanProperty edited = new SimpleBooleanProperty(false);

    // Add fields for token count, status, actions, and AI metadata
    private final IntegerProperty tokenCount = new SimpleIntegerProperty(0);
    private final StringProperty status = new SimpleStringProperty("");
    private final Map<String, Object> aiMetadata = new HashMap<>();

    // Add actions (e.g., thumbs up/down, copy, save)
    private final BooleanProperty liked = new SimpleBooleanProperty(false);
    private final BooleanProperty disliked = new SimpleBooleanProperty(false);
    private final BooleanProperty copied = new SimpleBooleanProperty(false);
    private final BooleanProperty saved = new SimpleBooleanProperty(false);

    // Constructors
    public ChatMessage(Role role, String content) {
        this.id = UUID.randomUUID().toString();
        this.role.set(role);
        this.content.set(content);
        this.timestamp.set(LocalDateTime.now());
    }

    public ChatMessage(String id, Role role, String content, LocalDateTime timestamp) {
        this.id = id == null ? UUID.randomUUID().toString() : id;
        this.role.set(role);
        this.content.set(content);
        this.timestamp.set(timestamp == null ? LocalDateTime.now() : timestamp);
    }

    // Properties & accessors
    public String getId() { return id; }

    public String getContent() { return content.get(); }
    public void setContent(String c) { content.set(c); edited.set(true); }
    public StringProperty contentProperty() { return content; }

    public Role getRole() { return role.get(); }
    public void setRole(Role r) { role.set(r); }
    public ObjectProperty<Role> roleProperty() { return role; }

    public LocalDateTime getTimestamp() { return timestamp.get(); }
    public void setTimestamp(LocalDateTime t) { timestamp.set(t); }
    public ObjectProperty<LocalDateTime> timestampProperty() { return timestamp; }

    public boolean isEdited() { return edited.get(); }
    public BooleanProperty editedProperty() { return edited; }

    public int getTokenCount() { return tokenCount.get(); }
    public void setTokenCount(int count) { tokenCount.set(count); }
    public IntegerProperty tokenCountProperty() { return tokenCount; }

    public String getStatus() { return status.get(); }
    public void setStatus(String s) { status.set(s); }
    public StringProperty statusProperty() { return status; }

    public Map<String, Object> getAiMetadata() { return aiMetadata; }
    public void setAiMetadata(Map<String, Object> meta) {
        aiMetadata.clear();
        if (meta != null) aiMetadata.putAll(meta);
    }

    public boolean isLiked() { return liked.get(); }
    public void setLiked(boolean v) { liked.set(v); }
    public BooleanProperty likedProperty() { return liked; }

    public boolean isDisliked() { return disliked.get(); }
    public void setDisliked(boolean v) { disliked.set(v); }
    public BooleanProperty dislikedProperty() { return disliked; }

    public boolean isCopied() { return copied.get(); }
    public void setCopied(boolean v) { copied.set(v); }
    public BooleanProperty copiedProperty() { return copied; }

    public boolean isSaved() { return saved.get(); }
    public void setSaved(boolean v) { saved.set(v); }
    public BooleanProperty savedProperty() { return saved; }

    // Convenience formatter
    public String getFormattedTime() {
        LocalDateTime t = getTimestamp();
        return t == null ? "" : t.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // Convert to a Map that matches OpenAI / Gemini message shape:
    public Map<String, String> toApiMessage() {
        Map<String, String> map = new HashMap<>();
        String roleStr = switch (getRole()) {
            case USER -> "user";
            case ASSISTANT -> "assistant";
            case SYSTEM -> "system";
        };
        map.put("role", roleStr);
        map.put("content", getContent());
        return map;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s (tokens: %d, status: %s)", getFormattedTime(), getRole(), getContent(), getTokenCount(), getStatus());
    }
}
