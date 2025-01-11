package logger;

import java.time.Instant;

public class LogMessage {
    private final LogLevel level;
    private final Instant timestamp;
    private final String message;

    public LogMessage(LogLevel level, Instant timestamp, String message) {
        this.level = level;
        this.timestamp = timestamp;
        this.message = message;
    }

    public LogLevel getLevel() {
        return level;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
