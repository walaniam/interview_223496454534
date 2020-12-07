package pl.walaniam.interview.processing;

import pl.walaniam.interview.domain.Conversation;
import pl.walaniam.interview.domain.Email;
import pl.walaniam.interview.processing.EmailConsumer;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.unmodifiableList;

public class EmailGenerator {

    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger();
    private static final int CONCURRENCY = 4;

    private final ExecutorService executorService = Executors.newFixedThreadPool(
            CONCURRENCY,
            runnable -> new Thread(runnable, "generator-" + THREAD_COUNTER.getAndIncrement())
    );
    private final AtomicInteger emailIdSequence = new AtomicInteger();

    private final EmailConsumer consumer;
    private final int emailsCount;
    private final ConversationGenerator conversationGenerator;

    public EmailGenerator(EmailConsumer consumer, int emailsCount, int conversationsCount) {
        this.consumer = consumer;
        this.emailsCount = emailsCount;
        this.conversationGenerator = new ConversationGenerator(conversationsCount);
    }

    public CompletableFuture<Void> runGenerator() {

        CompletableFuture<Void>[] futures = IntStream.range(0, CONCURRENCY)
                .mapToObj(index -> CompletableFuture.runAsync(notifyConsumer(index), executorService))
                .toArray(size -> new CompletableFuture[size]);

        return CompletableFuture.allOf(futures);
    }

    private Runnable notifyConsumer(int index) {
        return () -> {
            System.out.println("Running async with index=" + index);
            Email email;
            while ((email = nextEmail()) != null) {
                consumer.process(email);
            }
            System.out.println("Completed async with index=" + index);
        };
    }

    private Email nextEmail() {

        int nextId = emailIdSequence.incrementAndGet();

        if (nextId > emailsCount) {
            return null;
        }

        return new Email(nextId, conversationGenerator.nextConversation());
    }

    public void destroy() {
        executorService.shutdown();
    }

    private class ConversationGenerator {

        private final List<Conversation> conversations;
        private int position = -1;

        public ConversationGenerator(int conversationsCount) {
            this.conversations = unmodifiableList(IntStream.range(0, conversationsCount)
                    .mapToObj(Conversation::new)
                    .collect(Collectors.toList())
            );
            System.out.println("Conversations count: " + conversations.size());
        }

        public Conversation nextConversation() {
            int nextPosition;
            synchronized (this) {
                position += 1;
                if (position > conversations.size() - 1) {
                    position = 0;
                }
                nextPosition = position;
            }
            return conversations.get(nextPosition);
        }
    }
}
