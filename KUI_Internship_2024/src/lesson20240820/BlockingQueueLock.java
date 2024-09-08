package lesson20240820;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueLock<T> implements IBlockingQueue<T> {

    private final Queue<T> items = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private final int capacity;

    public BlockingQueueLock(int capacity) {
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            while (items.size() == capacity) {
                notFull.await(); // Wait if the queue is full
            }
            items.add(item);
            notEmpty.signal(); // Signal that the queue is not empty
        } finally {
            lock.unlock();
        }
    }

    public T get() throws InterruptedException {
        lock.lock();
        try {
            while (items.isEmpty()) {
                notEmpty.await(); // Wait if the queue is empty
            }
            T item = items.poll();
            notFull.signal(); // Signal that the queue has space
            return item;
        } finally {
            lock.unlock();
        }
    }
}
