package lesson20240820;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BlockingQueueSemaphore<T> implements IBlockingQueue<T>{

    private final Queue<T> items = new LinkedList<>();
    private final Semaphore availableItems = new Semaphore(0);
    private final Semaphore availableSpaces;

    public BlockingQueueSemaphore(int capacity) {
        availableSpaces = new Semaphore(capacity);
    }

    public void put(T item) throws InterruptedException {
        availableSpaces.acquire(); // Acquire space before adding an item
        synchronized (this) {
            items.add(item);
        }
        availableItems.release(); // Release an available item
    }

    public T get() throws InterruptedException {
        availableItems.acquire(); // Acquire an item before retrieving
        synchronized (this) {
            T item = items.poll();
            availableSpaces.release(); // Release space after retrieving an item
            return item;
        }
    }
}
