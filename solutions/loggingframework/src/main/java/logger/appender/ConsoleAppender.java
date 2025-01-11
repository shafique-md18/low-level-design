package logger.appender;

import logger.LogMessage;

public class ConsoleAppender implements Appender {
    private final Layout layout;
    public ConsoleAppender(Layout layout) {
        this.layout = layout;
    }
    @Override
    public void append(LogMessage message) {
        System.out.println(layout.format(message));
    }

    @Override
    public void close() {
        // pass
    }

}
