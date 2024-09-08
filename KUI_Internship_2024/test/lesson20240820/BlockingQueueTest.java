package lesson20240820;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class BlockingQueueTest {

    private static final int NUM_THREADS = 5;
    private static final int NUM_ITEMS = 100;

    @Test
    public void testBlockingQueueSemaphore() throws InterruptedException {
        BlockingQueueSemaphore<Integer> queue = new BlockingQueueSemaphore<>(10);
        testQueue(queue, "Semaphore");
    }

    @Test
    public void testBlockingQueueLock() throws InterruptedException {
        IBlockingQueue<Integer> queue = new BlockingQueueLock<>(10);
        testQueue(queue, "Lock");
    }

    private <T> void testQueue(IBlockingQueue<T> queue, String queueType) throws InterruptedException {
        List<T> producedItems = new ArrayList<>();
        List<T> consumedItems = new ArrayList<>();

        AtomicInteger itemCounter = new AtomicInteger(0);

        Runnable producer = () -> {
            for (int i = 0; i < NUM_ITEMS; i++) {
                try {
                    T item = (T) Integer.valueOf(itemCounter.incrementAndGet());
                    queue.put(item);
                    synchronized (producedItems) {
                        producedItems.add(item);
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable consumer = () -> {
            for (int i = 0; i < NUM_ITEMS; i++) {
                try {
                    T item = queue.get();
                    synchronized (consumedItems) {
                        consumedItems.add(item);
                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread[] producers = new Thread[NUM_THREADS];
        Thread[] consumers = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            producers[i] = new Thread(producer);
            consumers[i] = new Thread(consumer);
        }

        for (Thread producerThread : producers) {
            producerThread.start();
        }

        for (Thread consumerThread : consumers) {
            consumerThread.start();
        }

        for (Thread producerThread : producers) {
            producerThread.join();
        }

        for (Thread consumerThread : consumers) {
            consumerThread.join();
        }

        synchronized (producedItems) {
            synchronized (consumedItems) {
                assertEquals(queueType + " Queue Test Failed: Item counts do not match", producedItems.size(), consumedItems.size());
                assertTrue(queueType + " Queue Test Failed: Items lost or duplicated", producedItems.containsAll(consumedItems) && consumedItems.containsAll(producedItems));
            }
        }
    }
}
