import hashmap.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 1000; i++) {
            map.put(i, i);
        }

        System.out.println(map.getSize());

        for (int i = 512; i < 1000; i++) {
            map.remove(i);
        }

        System.out.println(map.getSize());
    }
}
