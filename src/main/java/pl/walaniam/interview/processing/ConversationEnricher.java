package pl.walaniam.interview.processing;

import pl.walaniam.interview.domain.Conversation;
import pl.walaniam.interview.domain.Email;

public interface ConversationEnricher {

    void process(Email email);

    int conversationSizeOf(Conversation conversation);
}
