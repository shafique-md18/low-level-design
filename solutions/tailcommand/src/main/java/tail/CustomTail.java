package tail;

import java.io.IOException;
import java.io.RandomAccessFile;

public class CustomTail {
    public static void printLastNLines(String filePath, int n) {

    }

    private static long printLastNLinesInternal(String filePath, int n) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            // Start from the end of file
            long fileLength = file.length(); // gives the length in bytes

            if (fileLength == 0) {
                return 0;
            }

            // Position starts at last byte
            long pos = fileLength - 1;
            int newLineCount = 0;

            while (pos >= 0) {
                file.seek(pos);
                char currChar = (char) file.read();

                if (currChar == '\n') {
                    newLineCount++;
                    if (newLineCount > n) {
                        break;
                    }
                }
                pos--;
            }


            file.seek(Math.max(0, pos + 1));
            String line;
            while ((line = file.readLine()) != null) {
                System.out.println(line);
            }

            return file.getFilePointer();
        }
    }

    public static void followMode(String filePath, int n) throws IOException {
        // Print first n lines initially
        long pointer = printLastNLinesInternal(filePath, n);

        // Then start following the file
        System.out.println("Following file for any changes: ");
        followFile(filePath, pointer);
    }

    private static void followFile(String filePath, long initialPosition) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            long filePointer = initialPosition;

            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(1000);

                    long length = file.length();
                    if (length < filePointer) {
                        System.out.println("File was truncated, resetting pointer");
                        filePointer = 0;
                    } else if (length > filePointer) {
                        file.seek(filePointer);
                        String line;
                        while ((line = file.readLine()) != null) {
                            System.out.println(line);
                            filePointer = file.getFilePointer();
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Follow mode interrupted");
                    break;
                }
            }
        }
    }
}
