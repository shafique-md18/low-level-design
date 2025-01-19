import tail.CustomTail;

import java.io.*;

public class CustomTailDemo {
    public static void main(String[] args) throws IOException {
        String testFile = "test.txt";

        createFile(testFile);

        System.out.println("Last 10 lines of the file:");
        CustomTail.printLastNLines(testFile, 10);

        System.out.println("Test: Follow mode");
        Thread followThread = new Thread(() -> {
            try {
                CustomTail.followMode(testFile, 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        followThread.start();

        // Simulate file updates
        try {
            Thread.sleep(2000);
            appendToFile(testFile, "New line added at: " + System.currentTimeMillis());
            Thread.sleep(2000);
            appendToFile(testFile, "Another new line at: " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void createFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < 100; i++) {
                writer.write("Line " + i + "\n");
            }
        }
    }

    private static void readFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    private static void appendToFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content + "\n");
        }
    }
}
