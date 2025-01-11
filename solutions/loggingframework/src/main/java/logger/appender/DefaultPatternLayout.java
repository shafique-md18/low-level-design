package logger.appender;

import logger.LogMessage;

public class DefaultPatternLayout implements Layout {
    @Override
    public String format(LogMessage message) {
        return "[%s][%s]: %s".formatted(message.getLevel(), message.getTimestamp().toString(), message.getMessage());
    }
}
