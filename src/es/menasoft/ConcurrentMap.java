package es.menasoft;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * Example about how to use ConcurrentHashMap to solve the problem related with ConcurrentModificationException.
 *
 * Using ConcurrentHashMap is thread safe.
 */
public class ConcurrentMap {

    ConcurrentHashMap<String, String> cmap = new ConcurrentHashMap<>();

    public void createEntry(String key, String value) {
        System.out.println("Create Entry Start " + key);
        cmap.put(key, value);
        System.out.println("Create Entry Fin " + key);
    }

    public void updateEntry(String key, String value) {
        System.out.println("Update Entry Start " + key);
        cmap.put(key, value);
        System.out.println("Update Entry End " + key);
    }

    public void queryEntries() {
        cmap.entrySet().stream().forEach(System.out::println);
    }

    public static void main(String[] args) {
        final ConcurrentMap map = new ConcurrentMap();

        // Thread 1. Try to create entries
        new Thread(() -> {
            String key = "Thread1-";
            IntStream.range(0, Integer.MAX_VALUE).forEach(i -> map.createEntry(key + i, "Create Value"));
        }).start();

        // Thread 2. Try to create entries
        new Thread(() -> {
            String key = "Thread1-";
            IntStream.range(0, Integer.MAX_VALUE).forEach(i -> map.updateEntry(key + i, "Update Value"));
        }).start();

        new Thread(() -> {
            String key = "Thread3-";
            IntStream.range(0, Integer.MAX_VALUE).forEach(i -> map.queryEntries());
        }).start();
    }
}
