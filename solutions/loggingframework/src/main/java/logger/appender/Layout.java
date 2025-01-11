package logger.appender;

import logger.LogMessage;

public interface Layout {
    public String format(LogMessage message);
}
