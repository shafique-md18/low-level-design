package logger.appender;

import logger.LogMessage;
import java.io.FileWriter;
import java.io.IOException;

public class FileAppender implements Appender {
    private final FileWriter fileWriter;
    private final Layout layout;

    public FileAppender(Layout layout, String filePath) throws IOException {
        this.layout = layout;
        this.fileWriter = new FileWriter(filePath, true);
    }

    @Override
    public void append(LogMessage message) throws IOException {
        fileWriter.write(layout.format(message) + "\n");
        fileWriter.flush();
    }

    @Override
    public void close() throws IOException {
        fileWriter.close();
    }
}
