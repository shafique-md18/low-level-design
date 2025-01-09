import hashmap.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();

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
