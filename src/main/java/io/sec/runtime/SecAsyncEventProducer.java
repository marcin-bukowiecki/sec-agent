package io.sec.runtime;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.concurrent.ThreadFactory;

/**
 * @author Marcin Bukowiecki
 */
public class SecAsyncEventProducer implements SecProducer {

    private final ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;

    private final WaitStrategy waitStrategy = new BusySpinWaitStrategy();

    private final SecAsyncEventConsumer secAsyncEventConsumer;

    private final Disruptor<StringPushEvent> disruptor = new Disruptor<>(
            StringPushEvent.EVENT_FACTORY,
            1024,
            threadFactory,
            ProducerType.MULTI,
            waitStrategy);

    private RingBuffer<StringPushEvent> eventRingBuffer;

    public SecAsyncEventProducer(@NotNull SecAsyncEventConsumer secAsyncEventConsumer) {
        this.secAsyncEventConsumer = secAsyncEventConsumer;
    }

    @Override
    public @NotNull SecConsumer getConsumer() {
        return secAsyncEventConsumer;
    }

    public void start() {
        if (eventRingBuffer != null) {
            throw new IllegalStateException("Disruptor already started");
        }
        disruptor.handleEventsWith(secAsyncEventConsumer);
        eventRingBuffer = disruptor.start();
    }

    public void publishEvent(@NotNull String value) {
        final long seqID = eventRingBuffer.next();
        final StringPushEvent event = eventRingBuffer.get(seqID);
        event.setValue(value);
        event.setThreadWeakReference(new WeakReference<>(Thread.currentThread()));
        eventRingBuffer.publish(seqID);
    }

    public @NotNull Disruptor<StringPushEvent> getDisruptor() {
        return disruptor;
    }
}
