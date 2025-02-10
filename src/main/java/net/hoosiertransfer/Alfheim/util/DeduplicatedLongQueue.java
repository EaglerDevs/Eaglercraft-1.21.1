package net.eaglerdevs.modsAlfheim.util;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * A queue implementation for long values that are deduplicated on addition.
 * <p>
 * This is achieved by storing the values in a HashSet and a LinkedList.
 */
public final class DeduplicatedLongQueue {

    private final HashSet<Long> set;
    private final LinkedList<Long> queue;

    /**
     * Creates a new deduplicated queue with the given capacity.
     *
     * @param capacity The capacity of the deduplicated queue
     */
    public DeduplicatedLongQueue(final int capacity) {
        set = new HashSet<>(capacity);
        queue = new LinkedList<>();
    }

    /**
     * Adds a value to the queue.
     *
     * @param value The value to add to the queue
     */
    public void enqueue(final long value) {
        if (set.add(value))
            queue.addLast(value);
    }

    /**
     * Removes and returns the first value in the queue.
     *
     * @return The first value in the queue
     */
    public long dequeue() {
        Long value = queue.removeFirst();
        return value != null ? value : 0;
    }

    /**
     * Returns whether the queue is empty.
     *
     * @return {@code true} if the queue is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Clears the deduplication set.
     */
    public void clearSet() {
        set.clear();
    }
}