package pl.walaniam.interview.domain;

import java.util.Objects;

public class Email {

    private final String id;
    private String conversationId;

    public Email(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return id.equals(email.id) &&
                Objects.equals(conversationId, email.conversationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, conversationId);
    }

    @Override
    public String toString() {
        return "Email{" +
                "id='" + id + '\'' +
                ", conversationId='" + conversationId + '\'' +
                '}';
    }
}
