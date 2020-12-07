package pl.walaniam.interview.domain;

import java.util.Objects;

public class Email {

    private final int id;
    private final Conversation conversation;

    public Email(int id, Conversation conversation) {
        this.id = id;
        this.conversation = conversation;
    }

    public int getId() {
        return id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return id == email.id &&
                conversation.equals(email.conversation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, conversation);
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", conversation=" + conversation +
                '}';
    }
}
