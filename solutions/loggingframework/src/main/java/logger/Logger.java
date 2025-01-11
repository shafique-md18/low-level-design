package logger;

import logger.appender.Appender;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Logger {
    private final LoggerConfig config;
    private final BlockingQueue<LogMessage> queue;
    private final BlockingQueue<LogMessage> deadLetterQueue;
    private final Thread loggingThread;

    public Logger(LoggerConfig config) {
        this.config = config;
        this.queue = new LinkedBlockingQueue<>();
        this.deadLetterQueue = new LinkedBlockingQueue<>();
        this.loggingThread = new Thread(this::processLogs);
        this.loggingThread.start();
    }

    private void processLogs() {
        while (true) {
            LogMessage message = null;
            try {
                message = queue.take();
                if (message.getLevel().ordinal() >= config.getLevel().ordinal()) {
                    for (Appender appender : config.getAppenderList()) {
                        appender.append(message);
                    }
                }
            } catch (InterruptedException | IOException e) {
                if (message != null) {
                    deadLetterQueue.add(message);
                }
            }
        }
    }

    public void log(LogMessage message) throws IOException {
        // Blocking Queue is already thread safe
        try {
            this.queue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void debug(String message) throws IOException {
        log(new LogMessage(LogLevel.DEBUG, Instant.now(), message));
    }

    public void info(String message) throws IOException {
        log(new LogMessage(LogLevel.INFO, Instant.now(), message));
    }

    public void warn(String message) throws IOException {
        log(new LogMessage(LogLevel.WARN, Instant.now(), message));
    }

    public void error(String message) throws IOException {
        log(new LogMessage(LogLevel.ERROR, Instant.now(), message));
    }
}
