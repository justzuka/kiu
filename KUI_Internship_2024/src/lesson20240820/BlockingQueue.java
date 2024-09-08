package lesson20240820;

import java.util.ArrayList;
import java.util.List;

public class BlockingQueue<T> {

	private final List<T> items = new ArrayList<>();
	private final Object mutex = new Object();

	public void put(T item) {
		synchronized (mutex) {
			items.add(item);
			mutex.notify(); // Notify waiting threads that an item has been added
		}
	}

	public T get() {
		synchronized (mutex) {
			while (items.isEmpty()) {
				try {
					mutex.wait(); // Wait until an item is available
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt(); // Restore interrupted state
				}
			}
			return items.remove(0); // Return the first item from the list
		}
	}
}
