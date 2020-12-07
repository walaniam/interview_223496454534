package pl.walaniam.interview.exercise;

import pl.walaniam.interview.domain.Conversation;
import pl.walaniam.interview.domain.Email;
import pl.walaniam.interview.processing.EmailConsumer;

public class ConversationProcessor implements EmailConsumer {

    public void process(Email email) {
        throw new RuntimeException("Missing implementation");
    }

    public int conversationSizeOf(Conversation conversation) {
        throw new RuntimeException("Missing implementation");
    }
}
