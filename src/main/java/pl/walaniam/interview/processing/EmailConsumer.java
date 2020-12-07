package pl.walaniam.interview.processing;

import pl.walaniam.interview.domain.Email;

public interface EmailConsumer {

    void process(Email email);
}
