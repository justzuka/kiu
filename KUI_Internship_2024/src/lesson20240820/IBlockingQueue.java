package lesson20240820;

public interface IBlockingQueue<T> {

    // Adds an item to the queue, blocking if necessary
    void put(T item) throws InterruptedException;

    // Retrieves and removes an item from the queue, blocking if necessary
    T get() throws InterruptedException;

}