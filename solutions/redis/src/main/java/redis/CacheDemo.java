package redis;

import redis.factory.CacheFactory;
import redis.pair.Pair;

import java.util.Arrays;
import java.util.List;

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

        Cache<String, List<Pair<String, String>>> objectCache = new CacheFactory<String, List<Pair<String, String>>>().defaultCache(5);
        objectCache.put("sde_bootcamp", Arrays.asList(new Pair<>("title", "SDE-Bootcamp"), new Pair<>("price", "30000.00")));
        objectCache.put("sde_kickstart", Arrays.asList(new Pair<>("title", "SDE-Kickstart"), new Pair<>("price", "12000.00")));

        objectCache.keys().forEach(key -> System.out.println(objectCache.get(key)));
    }
}
