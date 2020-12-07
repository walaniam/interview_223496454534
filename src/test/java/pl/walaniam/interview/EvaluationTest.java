package pl.walaniam.interview;

import org.junit.After;
import org.junit.Test;
import pl.walaniam.interview.domain.Conversation;
import pl.walaniam.interview.exercise.ConversationProcessor;
import pl.walaniam.interview.processing.EmailGenerator;

import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EvaluationTest {

    private static final int TOTAL_EMAIL_COUNT = 500_000;
    private static final int TOTAL_CONVERSATION_COUNT = 5000;
    private static final int EXPECTED_MAILS_IN_CONVERSATION = 100;

    private final ConversationProcessor underTest = new ConversationProcessor();

    private final EmailGenerator emailGenerator = new EmailGenerator(
            underTest,
            TOTAL_EMAIL_COUNT,
            TOTAL_CONVERSATION_COUNT
    );

    @Test
    public void testMe() {

        emailGenerator.runGenerator().join();

        IntStream.range(0, TOTAL_CONVERSATION_COUNT)
                .mapToObj(Conversation::new)
                .forEach(conversation -> assertThat(underTest.conversationSizeOf(conversation))
                        .isEqualTo(EXPECTED_MAILS_IN_CONVERSATION)
                );
    }

    @After
    public void after() {
        emailGenerator.destroy();
    }
}
