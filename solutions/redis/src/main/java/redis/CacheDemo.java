package redis;

public class CacheDemo {
    public static void main(String[] args) {
        Cache<Integer, Integer> cache = new CacheFactory<Integer, Integer>().defaultCache(5);

        for (int i = 0; i < 20; i++) {
            cache.put(i, i);
        }

        for (int i = 0; i < 20; i++) {
            if (cache.get(i) != null) {
                System.out.println("Fetched: " + cache.get(i));
            }
        }
    }
}
